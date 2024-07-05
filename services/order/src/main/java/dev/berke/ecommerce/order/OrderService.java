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
        // check the customer ==> we will use OpenFeign
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        // purchase the products ==> using the product service (we wil use REST template)
        this.productClient.purchaseProducts(orderRequest.products());

        // persist order
        var order = this.orderRepository.save(orderMapper.toOrder(orderRequest));

        // persist order lines: like purchasing or saving orderlines
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

        // start payment process (this is after we create payment service)


        // send the order confirmation ==> to notification service (we don't have yet, we will use kafka)

        return null;
    }
}
