package com.finzy_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    // 소셜 로그인 타입 (APPLE, KAKAO)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    // 소셜 로그인 고유 ID
    @Column(nullable = false)
    private String socialId;

    // 프로필 이미지 URL
    private String profileImageUrl;

    // 관심 카테고리 (콤마 구분)
    private String categories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum SocialType {
        APPLE, KAKAO
    }
}