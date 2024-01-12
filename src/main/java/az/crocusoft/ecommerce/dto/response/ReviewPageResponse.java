package az.crocusoft.ecommerce.dto.response;

import java.util.List;

public record ReviewPageResponse(
        List<ReviewResponse> reviewResponseList,
        int totalPages,
        long totalElements,
        boolean hasNext
) {
}
