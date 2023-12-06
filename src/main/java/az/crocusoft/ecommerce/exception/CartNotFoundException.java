package az.crocusoft.ecommerce.exception;

public class CartNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    String resourceName;
    String field;
    String fieldName;
    Integer fieldId;






    public CartNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s: %d", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = Math.toIntExact(fieldId);
    }
}
