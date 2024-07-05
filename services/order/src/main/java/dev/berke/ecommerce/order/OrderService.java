package dev.berke.ecommerce.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Integer createOrder(OrderRequest orderRequest) {
        // check the customer (use OpenFeign)


        // purchase the products (use product service, and RestTemplate)


        // persist order (save order object)


        // persist orderlines (save orderlines)


        // payment process


        // send the order confirmation (notification service, use kafka)


        return null;
    }
}
