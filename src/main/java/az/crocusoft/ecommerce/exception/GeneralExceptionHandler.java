package az.crocusoft.ecommerce.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class
GeneralExceptionHandler {

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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionResponse handleEntityNotFound(ResourceNotFoundException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.BAD_REQUEST.value()
                , HttpStatus.BAD_REQUEST
                , exception.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ExceptionResponse handleCustomException(CustomException ex) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.BAD_REQUEST.value()
                , HttpStatus.BAD_REQUEST
                , ex.getMessage()
        );
    }
    @ExceptionHandler(UserAlreadyAddedThisProductWishList.class)
    public ExceptionResponse handleProductStockQuantityNotFound(UserAlreadyAddedThisProductWishList ex) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.BAD_REQUEST.value()
                , HttpStatus.BAD_REQUEST
                , ex.getMessage()
        );
    }
    @ExceptionHandler(StockQuantityControlException.class)
    public ExceptionResponse handleProductStockQuantityNotFound(StockQuantityControlException ex) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.BAD_REQUEST.value()
                , HttpStatus.BAD_REQUEST
                , ex.getMessage()
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HashMap<Object,Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletResponse response)
    {
        HashMap<Object,Object> map=new HashMap<>();
        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("statusCode",HttpStatus.BAD_REQUEST.value());

        HashMap<Object,Object> errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((e)->errors.put(((FieldError)e).getField(), e.getDefaultMessage()));
        map.put("errors",errors);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return map;
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ExceptionResponse handleInsufficientStockException(InsufficientStockException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.NOT_FOUND.value()
                , HttpStatus.NOT_FOUND
                , exception.getMessage());
    }
    @ExceptionHandler(BlockedUserException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleBlockedUserException(BlockedUserException exception) {
        return new ExceptionResponse(
                LocalDateTime.now()
                , HttpStatus.FORBIDDEN.value()
                , HttpStatus.FORBIDDEN
                , exception.getMessage());
    }
}

