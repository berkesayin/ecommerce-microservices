package dev.berke.ecommerce.payment;

import dev.berke.ecommerce.customer.CustomerResponse;
import dev.berke.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}