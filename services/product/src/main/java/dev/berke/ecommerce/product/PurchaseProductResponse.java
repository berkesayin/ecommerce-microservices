package dev.berke.ecommerce.product;

import java.math.BigDecimal;

public record PurchaseProductResponse(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
