package com.finzy_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {

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

    // 고민 내용
    @Column(nullable = false, length = 500)
    private String content;

    // 카테고리
    @Enumerated(EnumType.STRING)
    private Review.Category category;

    // 가격
    private Integer price;

    // 구매처
    private String store;

    // 살아요 수
    @Column(nullable = false)
    @Builder.Default
    private Integer buyCount = 0;

    // 말아요 수
    @Column(nullable = false)
    @Builder.Default
    private Integer notBuyCount = 0;

    // 마감 여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean isClosed = false;

    // 공개 여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean isPublic = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}