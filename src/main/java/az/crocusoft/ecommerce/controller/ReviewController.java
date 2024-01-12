package az.crocusoft.ecommerce.controller;

import az.crocusoft.ecommerce.constants.PaginationConstants;
import az.crocusoft.ecommerce.dto.request.ProductVariationRequest;
import az.crocusoft.ecommerce.dto.request.ReviewRequest;
import az.crocusoft.ecommerce.dto.response.ProductPageResponse;
import az.crocusoft.ecommerce.dto.response.ReviewPageResponse;
import az.crocusoft.ecommerce.service.AuthenticationService;
import az.crocusoft.ecommerce.service.ReviewService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final AuthenticationService authenticationService;

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addReview (
            ReviewRequest reviewRequest,
            @RequestParam(value = "images", required = false) List<MultipartFile> images) {

        Long userId = authenticationService.getSignedInUser().getId();

        try {
            reviewService.addReview(reviewRequest, images, userId);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while adding images");
        }
    }

    @GetMapping()
    public ResponseEntity<ReviewPageResponse> getReviews(
            @RequestParam(name = "productId") Long productId,
            @RequestParam(name = "pageNumber", defaultValue = PaginationConstants.PAGE_NUMBER) Integer page,
            @RequestParam(name = "pageSize", defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(name = "sortOrder", defaultValue = PaginationConstants.SORT_ORDER) String sortOrder) {


        return ResponseEntity.ok(reviewService.getReviews(productId, page, size, sortOrder));
    }

}
