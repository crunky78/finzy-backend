package com.finzy_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 상품명
    @Column(nullable = false)
    private String productName;

    // 카테고리
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    // 가격
    private Integer price;

    // 구매처
    private String store;

    // 별점 (1~5)
    @Column(nullable = false)
    private Integer stars;

    // 장점 (필수)
    @Column(nullable = false, length = 500)
    private String pros;

    // 단점 (필수)
    @Column(nullable = false, length = 500)
    private String cons;

    // 한줄 코멘트
    @Column(length = 200)
    private String comment;

    // 인증 타입
    @Enumerated(EnumType.STRING)
    private VerificationType verificationType;

    // 인증 이미지 URL (카드명세서 등)
    private String verificationImageUrl;

    // 인증 여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean isVerified = false;

    // 공개 여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean isPublic = true;

    // 좋아요 수
    @Column(nullable = false)
    @Builder.Default
    private Integer likeCount = 0;

    // 댓글 수
    @Column(nullable = false)
    @Builder.Default
    private Integer commentCount = 0;

    // 도움됐어요 수
    @Column(nullable = false)
    @Builder.Default
    private Integer helpfulCount = 0;

    // 나도샀어요 수
    @Column(nullable = false)
    @Builder.Default
    private Integer meBuyCount = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum Category {
        BEAUTY, ELECTRONICS, FOOD, FASHION,
        INTERIOR, PET, BOOK, SPORTS, HOBBY
    }

    public enum VerificationType {
        CARD,       // 💳 카드명세서
        ORDER,      // 📦 주문번호
        WAYBILL,    // 🚚 운송장
        GIFT,       // 🎁 선물받음
        BRAND       // 📢 브랜드증정
    }
}