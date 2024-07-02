package dev.berke.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findProductById(
            @PathVariable("product-id") Integer productId
    ) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }
}
