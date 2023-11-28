package az.crocusoft.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private Integer cid;
    private String comment;
    private Date commentDate;
    private UserDto user;
}
