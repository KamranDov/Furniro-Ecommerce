package az.crocusoft.ecommerce.exception;

public class ProductNotExistsException extends RuntimeException{
    public ProductNotExistsException(String message) {
        super(message);
    }
}

