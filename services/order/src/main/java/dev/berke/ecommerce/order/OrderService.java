package dev.berke.ecommerce.order;

import dev.berke.ecommerce.customer.CustomerClient;
import dev.berke.ecommerce.exception.BusinessException;
import dev.berke.ecommerce.orderline.OrderLineRequest;
import dev.berke.ecommerce.orderline.OrderLineService;
import dev.berke.ecommerce.product.ProductClient;
import dev.berke.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;


    public Integer createOrder(OrderRequest orderRequest) {

        // check the customer (customer service, use OpenFeign)
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        // purchase the products (product service, use RestTemplate)
        this.productClient.purchaseProducts(orderRequest.products());

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

        // payment process


        // send the order confirmation (notification service, use kafka)


        return null;
    }
}
