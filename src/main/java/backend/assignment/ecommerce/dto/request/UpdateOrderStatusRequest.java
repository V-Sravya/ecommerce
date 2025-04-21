package backend.assignment.ecommerce.dto.request;

import backend.assignment.ecommerce.entity.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    private OrderStatus status;
} 