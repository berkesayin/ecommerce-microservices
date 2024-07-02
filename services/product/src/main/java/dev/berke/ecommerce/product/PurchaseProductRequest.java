package dev.berke.ecommerce.product;

import jakarta.validation.constraints.NotNull;

public record PurchaseProductRequest(
        @NotNull(message = "Product is mandatory")
        Integer productId,

        @NotNull(message = "Quantity is mandatory")
        double quantity
) {
}
