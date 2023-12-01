package az.crocusoft.ecommerce.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException ex)
    {
        return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage(),ex.getTimestamp(),ex.getStatus(),ex.getStatus().value()),ex.getStatus());
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
}
