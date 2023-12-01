package az.crocusoft.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Double totalAmount;
    private LocalDate orderDate;
    private Long userId;
    private String orderStatus;
    private AddressDto addressDto;

}
