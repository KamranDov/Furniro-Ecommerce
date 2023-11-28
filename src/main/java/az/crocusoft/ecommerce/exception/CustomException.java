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
    private static final long serialVersionUID = 1L;
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;

    public CustomException(String message,HttpStatus status)
    {
        this.message=message;
        this.timestamp=LocalDateTime.now();
        this.status=status;
    }
}
