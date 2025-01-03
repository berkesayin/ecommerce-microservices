package dev.berke.ecommerce.order;

import dev.berke.ecommerce.customer.CustomerClient;
import dev.berke.ecommerce.exception.BusinessException;
import dev.berke.ecommerce.kafka.OrderConfirmation;
import dev.berke.ecommerce.kafka.OrderProducer;
import dev.berke.ecommerce.orderline.OrderLineRequest;
import dev.berke.ecommerce.orderline.OrderLineService;
import dev.berke.ecommerce.payment.PaymentClient;
import dev.berke.ecommerce.payment.PaymentRequest;
import dev.berke.ecommerce.product.ProductClient;
import dev.berke.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;


    public Integer createOrder(OrderRequest orderRequest) {

        // check the customer (customer service, use OpenFeign)
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        // purchase the products (product service, use RestTemplate)
        var purchasedProducts = this.productClient.purchaseProducts(orderRequest.products());

        // persist order (save order object)
        var order = this.orderRepository.save(orderMapper.toOrder(orderRequest));

        // persist orderlines (save orderlines)
        for (PurchaseRequest purchaseRequest: orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null, // id of the orderline
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // start payment process
        var paymentRequest = new PaymentRequest(
            orderRequest.amount(),
            orderRequest.paymentMethod(),
            order.getId(),
            order.getReference(),
            customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        // send the order confirmation (notification service, use kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", orderId)));
    }
}
