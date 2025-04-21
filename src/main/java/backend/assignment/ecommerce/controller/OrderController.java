package backend.assignment.ecommerce.controller;

import backend.assignment.ecommerce.dto.ApiResponse;
import backend.assignment.ecommerce.dto.OrderDto;
import backend.assignment.ecommerce.dto.request.UpdateOrderStatusRequest;
import backend.assignment.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}/place")
    public ResponseEntity<ApiResponse<OrderDto>> placeOrder(@PathVariable Long userId) {
        OrderDto order = orderService.placeOrder(userId);
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getUserOrders(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderDto>> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        OrderDto order = orderService.updateOrderStatus(orderId, request);
        return ResponseEntity.ok(ApiResponse.success(order));
    }
} 