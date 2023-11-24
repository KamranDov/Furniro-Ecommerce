package az.crocusoft.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(MailSenderException.class)
    public ExceptionResponse handleMailSender(MailSenderException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.INTERNAL_SERVER_ERROR.value()
                , HttpStatus.INTERNAL_SERVER_ERROR
                , exception.getMessage());
    }
}
