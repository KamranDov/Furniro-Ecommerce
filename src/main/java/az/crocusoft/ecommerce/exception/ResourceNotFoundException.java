package az.crocusoft.ecommerce.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    String resourceName;
    String field;
    String fieldName;
    Integer fieldId;






    public ResourceNotFoundException(String resourceName, String field, Integer fieldId) {
        super(String.format("%s not found with %s: %d", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }
}
