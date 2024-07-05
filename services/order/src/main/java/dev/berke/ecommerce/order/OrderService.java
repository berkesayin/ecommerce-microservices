package dev.berke.ecommerce.order;

import dev.berke.ecommerce.customer.CustomerClient;
import dev.berke.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerClient customerClient;

    public Integer createOrder(OrderRequest orderRequest) {

        // check the customer (use OpenFeign)
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        // purchase the products (use product service, and RestTemplate)


        // persist order (save order object)


        // persist orderlines (save orderlines)


        // payment process


        // send the order confirmation (notification service, use kafka)


        return null;
    }
}
