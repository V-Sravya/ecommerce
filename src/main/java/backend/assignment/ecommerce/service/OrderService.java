package backend.assignment.ecommerce.service;

import backend.assignment.ecommerce.dto.OrderDto;
import backend.assignment.ecommerce.dto.OrderItemDto;
import backend.assignment.ecommerce.dto.ProductDto;
import backend.assignment.ecommerce.dto.request.UpdateOrderStatusRequest;
import backend.assignment.ecommerce.entity.*;
import backend.assignment.ecommerce.exception.ResourceNotFoundException;
import backend.assignment.ecommerce.repository.CartRepository;
import backend.assignment.ecommerce.repository.OrderRepository;
import backend.assignment.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Transactional
    public OrderDto placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // Mock payment process
        boolean paymentSuccessful = processPayment(cart);
        if (!paymentSuccessful) {
            throw new RuntimeException("Payment failed");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(calculateTotalAmount(cart));

        // Convert cart items to order items
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getPrice());
                    return orderItem;
                })
                .toList();

        order.setItems(orderItems);

        // Clear the cart
        cart.getItems().clear();
        cartRepository.save(cart);

        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    public List<OrderDto> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return orderRepository.findByUserOrderByOrderDateDesc(user)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional
    public OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        order.setStatus(request.getStatus());
        Order updatedOrder = orderRepository.save(order);
        return convertToDto(updatedOrder);
    }

    private boolean processPayment(Cart cart) {
        // Mock payment process - always return true for demo
        return true;
    }

    private BigDecimal calculateTotalAmount(Cart cart) {
        return cart.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setItems(order.getItems().stream()
                .map(this::convertToOrderItemDto)
                .toList());
        return dto;
    }

    private OrderItemDto convertToOrderItemDto(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        
        ProductDto productDto = new ProductDto();
        productDto.setId(item.getProduct().getId());
        productDto.setName(item.getProduct().getName());
        productDto.setPrice(item.getProduct().getPrice());
        dto.setProduct(productDto);
        
        return dto;
    }
} 