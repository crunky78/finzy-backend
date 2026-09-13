package com.finzy_backend.service;

import com.finzy_backend.dto.request.ReviewRequest;
import com.finzy_backend.dto.response.ReviewResponse;
import com.finzy_backend.entity.Review;
import com.finzy_backend.entity.User;
import com.finzy_backend.repository.ReviewRepository;
import com.finzy_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    // 리뷰 작성
    @Transactional
    public ReviewResponse createReview(Long userId, ReviewRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없어요"));

        Review review = Review.builder()
                .user(user)
                .productName(request.getProductName())
                .category(request.getCategory())
                .price(request.getPrice())
                .store(request.getStore())
                .stars(request.getStars())
                .pros(request.getPros())
                .cons(request.getCons())
                .comment(request.getComment())
                .verificationType(request.getVerificationType())
                .verificationImageUrl(request.getVerificationImageUrl())
                .isVerified(request.getVerificationType() != null)
                .isPublic(request.getIsPublic() != null ? request.getIsPublic() : true)
                .build();

        return ReviewResponse.from(reviewRepository.save(review));
    }

    // 전체 피드
    public Page<ReviewResponse> getFeed(int page, int size, String category) {
        Pageable pageable = PageRequest.of(page, size);

        if (category != null && !category.equals("ALL")) {
            Review.Category cat = Review.Category.valueOf(category);
            return reviewRepository
                    .findByIsPublicTrueAndCategoryOrderByCreatedAtDesc(cat, pageable)
                    .map(ReviewResponse::from);
        }

        return reviewRepository
                .findByIsPublicTrueOrderByCreatedAtDesc(pageable)
                .map(ReviewResponse::from);
    }

    // 상품명 검색
    public Page<ReviewResponse> searchReviews(String keyword, boolean verifiedOnly, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (verifiedOnly) {
            return reviewRepository
                    .findByIsPublicTrueAndIsVerifiedTrueAndProductNameContainingIgnoreCaseOrderByCreatedAtDesc(
                            keyword, pageable)
                    .map(ReviewResponse::from);
        }

        return reviewRepository
                .findByIsPublicTrueAndProductNameContainingIgnoreCaseOrderByCreatedAtDesc(
                        keyword, pageable)
                .map(ReviewResponse::from);
    }

    // 리뷰 상세
    public ReviewResponse getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없어요"));
        return ReviewResponse.from(review);
    }

    // 내 리뷰 목록
    public List<ReviewResponse> getMyReviews(Long userId) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());
    }

    // 랭킹
    public List<ReviewResponse> getRanking(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return reviewRepository.findTopRankedProducts(pageable)
                .stream()
                .map(row -> {
                    String productName = (String) row[0];
                    List<Review> reviews = reviewRepository
                            .findByIsPublicTrueAndProductNameContainingIgnoreCaseOrderByCreatedAtDesc(productName);
                    if (reviews.isEmpty()) return null;
                    return ReviewResponse.from(reviews.get(0));
                })
                .filter(r -> r != null)
                .collect(Collectors.toList());
    }

    // 좋아요
    @Transactional
    public ReviewResponse likeReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없어요"));
        review.setLikeCount(review.getLikeCount() + 1);
        return ReviewResponse.from(reviewRepository.save(review));
    }

    // 나도 샀어요
    @Transactional
    public ReviewResponse meBuyReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없어요"));
        review.setMeBuyCount(review.getMeBuyCount() + 1);
        return ReviewResponse.from(reviewRepository.save(review));
    }
}