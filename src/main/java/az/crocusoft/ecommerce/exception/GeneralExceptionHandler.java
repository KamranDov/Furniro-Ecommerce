package az.crocusoft.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ExceptionHandler(ProductNotExistsException.class)
    public ExceptionResponse handleProductNotExists(ProductNotExistsException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.NOT_FOUND.value()
                , HttpStatus.NOT_FOUND
                , exception.getMessage());
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ExceptionResponse handleCartItemNotFound(CartItemNotFoundException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.NOT_FOUND.value()
                , HttpStatus.NOT_FOUND
                , exception.getMessage());
    }

    @ExceptionHandler(CartItemOwnershipException.class)
    public ExceptionResponse handleCartItemOwnership(CartItemOwnershipException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.FORBIDDEN.value()
                , HttpStatus.FORBIDDEN
                , exception.getMessage());
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionResponse handleCartItemOwnership(UserNotFoundException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.NOT_FOUND.value()
                , HttpStatus.NOT_FOUND
                , exception.getMessage());
    }
    @ExceptionHandler(WishListNotFoundException.class)
    public ExceptionResponse handleCartItemOwnership(WishListNotFoundException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.NOT_FOUND.value()
                , HttpStatus.NOT_FOUND
                , exception.getMessage());
    }
    @ExceptionHandler(CartNotFoundException.class)
    public ExceptionResponse handleCartItemOwnership(CartNotFoundException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.NOT_FOUND.value()
                , HttpStatus.NOT_FOUND
                , exception.getMessage());
    }
}

