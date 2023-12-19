package az.crocusoft.ecommerce.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}