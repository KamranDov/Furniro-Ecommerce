package az.crocusoft.ecommerce.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long orderItemId;
    private Long productId;
    private Long orderId;
    private Integer quantity;
    private Double totalAmount;

}
