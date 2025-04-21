package backend.assignment.ecommerce.controller;

import backend.assignment.ecommerce.dto.ApiResponse;
import backend.assignment.ecommerce.dto.CartDto;
import backend.assignment.ecommerce.dto.request.AddToCartRequest;
import backend.assignment.ecommerce.dto.request.UpdateCartItemRequest;
import backend.assignment.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<ApiResponse<CartDto>> addToCart(
            @PathVariable Long userId,
            @Valid @RequestBody AddToCartRequest request) {
        CartDto cart = cartService.addToCart(userId, request);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    @PutMapping("/{userId}/update/{productId}")
    public ResponseEntity<ApiResponse<CartDto>> updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        CartDto cart = cartService.updateCartItem(userId, productId, request);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeFromCart(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartDto>> getCart(@PathVariable Long userId) {
        CartDto cart = cartService.getCart(userId);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }
} 