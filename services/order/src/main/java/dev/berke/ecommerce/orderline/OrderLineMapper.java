package dev.berke.ecommerce.orderline;

import dev.berke.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .id(orderLineRequest.id())
                .order(
                        Order.builder()
                                .id(orderLineRequest.orderId())
                                .build()
                )
                .productId(orderLineRequest.productId())
                .quantity(orderLineRequest.quantity())
                .build();
    }
}
