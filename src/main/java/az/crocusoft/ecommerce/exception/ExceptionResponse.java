package az.crocusoft.ecommerce.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponse(
        LocalDateTime timestamp,
        Integer statusCode,
        HttpStatus error,
        String message ) {

}
