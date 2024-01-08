package az.crocusoft.ecommerce.service.Impl;

import az.crocusoft.ecommerce.constants.PaginationConstants;
import az.crocusoft.ecommerce.dto.request.ReviewRequest;
import az.crocusoft.ecommerce.dto.response.ReviewPageResponse;
import az.crocusoft.ecommerce.dto.response.ReviewResponse;
import az.crocusoft.ecommerce.exception.ResourceNotFoundException;
import az.crocusoft.ecommerce.exception.UserNotFoundException;
import az.crocusoft.ecommerce.model.User;
import az.crocusoft.ecommerce.model.product.Image;
import az.crocusoft.ecommerce.model.product.Product;
import az.crocusoft.ecommerce.model.product.Review;
import az.crocusoft.ecommerce.repository.ProductRepository;
import az.crocusoft.ecommerce.repository.ReviewRepository;
import az.crocusoft.ecommerce.repository.UserRepository;
import az.crocusoft.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final ProductRepository productRepository;
    private static final String REVIEW_IMAGES_FOLDER_NAME = "Review-images";

    @Override
    @Transactional
    public void addReview(ReviewRequest reviewRequest,
                          List<MultipartFile> images,
                          Long userId) throws IOException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Product product = productRepository.findById(reviewRequest.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Set<Image> reviewImages = new HashSet<>();

        for (MultipartFile image : images) {
            String uploadedImageURL = fileService.uploadImage(image, REVIEW_IMAGES_FOLDER_NAME);
            Image uploadedImage = new Image(uploadedImageURL);
            reviewImages.add(uploadedImage);
        }

        Review review = Review.builder()
                .approved(false)
                .product(product)
                .comment(reviewRequest.comment())
                .rating(reviewRequest.rating())
                .user(user)
                .images(reviewImages)
                .createdAt(new Date())
                .build();
        reviewRepository.save(review);
        log.info("Review added successfully by user with id: {}", userId);
    }

    @Override
    public ReviewPageResponse getReviews(Long productId, int pageNumber,
                                         int pageSize, String sortOrder) {

        String sortBy = PaginationConstants.REVIEW_SORT_BY;
        List<String> orders = Arrays.asList(PaginationConstants.orders);
        if (!orders.contains(sortOrder.toUpperCase())) {
            sortOrder = PaginationConstants.SORT_ORDER;
        }

        pageNumber = Math.max(pageNumber, Integer.parseInt(PaginationConstants.PAGE_NUMBER));
        pageSize = pageSize < 1 ? Integer.parseInt(PaginationConstants.DEFAULT_PAGE_SIZE) : pageSize;
        pageSize = Math.min(pageSize, Integer.parseInt(PaginationConstants.MAX_PAGE_SIZE));

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest
                .of(pageNumber - 1, pageSize, sort);

        Page<Review> reviewPage = reviewRepository
                .findAllByApprovedTrue(productId, pageable);

        List<ReviewResponse> reviewResponseList = reviewPage
                .getContent()
                .stream()
                .map(this::mapReviewToReviewResponse)
                .toList();

        return new ReviewPageResponse(reviewResponseList,
                reviewPage.getTotalPages(),
                reviewPage.getTotalElements(),
                reviewPage.hasNext());
    }

    private ReviewResponse mapReviewToReviewResponse(Review review) {
        return ReviewResponse.builder()
                .comment(review.getComment())
                .rating(review.getRating())
                .username(review.getUser().getUsername())
                .createdAt(review.getCreatedAt())
                .imageUrls(review.getImages()
                        .stream()
                        .map(image -> fileService
                                .getFullImagePath(image.getImageUrl()))
                        .collect(Collectors.toSet()))
                .build();
    }

}
