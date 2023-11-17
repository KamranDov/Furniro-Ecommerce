package az.crocusoft.ecommerce.dto;

import az.crocusoft.ecommerce.model.customer.Customer;
import az.crocusoft.ecommerce.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;
    private String comment;
    private Integer rating;
    private Date createdAt;
    private String customerName;
    private String customerLastName;
}
