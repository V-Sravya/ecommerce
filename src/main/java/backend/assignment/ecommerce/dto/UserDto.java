package backend.assignment.ecommerce.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
}

@Data
class CreateUserRequest {
    private String name;
    private String email;
    private String phone;
}

@Data
class UpdateUserRequest {
    private String name;
    private String phone;
} 