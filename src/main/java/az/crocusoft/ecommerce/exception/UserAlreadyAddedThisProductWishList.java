package az.crocusoft.ecommerce.exception;

public class UserAlreadyAddedThisProductWishList extends  RuntimeException {
    public UserAlreadyAddedThisProductWishList(String message) {
        super(message);
    }
}