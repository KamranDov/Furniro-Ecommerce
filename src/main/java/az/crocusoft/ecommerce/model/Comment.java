package az.crocusoft.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private String comment;
    private LocalDate commentDate;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Blog blogPost;

    @OneToOne
    @JoinColumn(name = "uid")
    private User user;
}