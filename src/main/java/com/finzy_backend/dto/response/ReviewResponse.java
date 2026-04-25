package com.finzy_backend.dto.response;

import com.finzy_backend.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponse {

    private Long id;
    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private String productName;
    private String category;
    private Integer price;
    private String store;
    private Integer stars;
    private String pros;
    private String cons;
    private String comment;
    private String verificationType;
    private Boolean isVerified;
    private Integer likeCount;
    private Integer commentCount;
    private Integer helpfulCount;
    private Integer meBuyCount;
    private LocalDateTime createdAt;

    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .nickname(review.getUser().getNickname())
                .profileImageUrl(review.getUser().getProfileImageUrl())
                .productName(review.getProductName())
                .category(review.getCategory().name())
                .price(review.getPrice())
                .store(review.getStore())
                .stars(review.getStars())
                .pros(review.getPros())
                .cons(review.getCons())
                .comment(review.getComment())
                .verificationType(review.getVerificationType() != null ?
                        review.getVerificationType().name() : null)
                .isVerified(review.getIsVerified())
                .likeCount(review.getLikeCount())
                .commentCount(review.getCommentCount())
                .helpfulCount(review.getHelpfulCount())
                .meBuyCount(review.getMeBuyCount())
                .createdAt(review.getCreatedAt())
                .build();
    }
}