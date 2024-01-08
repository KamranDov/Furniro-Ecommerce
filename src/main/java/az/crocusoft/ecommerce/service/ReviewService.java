package az.crocusoft.ecommerce.service;

import az.crocusoft.ecommerce.dto.request.ReviewRequest;
import az.crocusoft.ecommerce.dto.response.ReviewPageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReviewService {
    void addReview(ReviewRequest reviewRequest,
                   List<MultipartFile> images,
                   Long userId) throws IOException;

    ReviewPageResponse getReviews(Long productId, int pageNumber,
                                  int pageSize, String sortOrder);

}
