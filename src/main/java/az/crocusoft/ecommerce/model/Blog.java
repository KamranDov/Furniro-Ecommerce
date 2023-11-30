package az.crocusoft.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    private String title;
    private Date date;
    @Column(columnDefinition = "text")
    private String content;
    private String imageName;

    @ManyToOne
    @JoinColumn(name = "cid")
    private BlogCategory category;



}
