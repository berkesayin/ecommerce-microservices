package dev.berke.ecommerce.kafka;

import dev.berke.ecommerce.customer.CustomerResponse;
import dev.berke.ecommerce.order.PaymentMethod;
import dev.berke.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
