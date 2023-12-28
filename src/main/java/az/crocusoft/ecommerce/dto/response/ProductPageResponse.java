package az.crocusoft.ecommerce.dto.response;

import org.hibernate.sql.ast.tree.predicate.BooleanExpressionPredicate;

import java.util.List;

public record ProductPageResponse(
        List<ProductResponse> productResponseList,
        int totalPages,
        long TotalElements,
        boolean hasNext
) {
}
