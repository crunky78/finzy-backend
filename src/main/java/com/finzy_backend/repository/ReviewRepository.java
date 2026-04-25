package com.finzy_backend.repository;

import com.finzy_backend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 전체 피드 (공개 + 최신순)
    Page<Review> findByIsPublicTrueOrderByCreatedAtDesc(Pageable pageable);

    // 카테고리별 피드
    Page<Review> findByIsPublicTrueAndCategoryOrderByCreatedAtDesc(
            Review.Category category, Pageable pageable);

    // 상품명 검색
    Page<Review> findByIsPublicTrueAndProductNameContainingIgnoreCaseOrderByCreatedAtDesc(
            String productName, Pageable pageable);

    // 내 리뷰 목록
    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);

    // 같은 상품 리뷰 모아보기
    List<Review> findByIsPublicTrueAndProductNameContainingIgnoreCaseOrderByCreatedAtDesc(
            String productName);

    // 랭킹 - 인증된 리뷰 중 별점 높은 상품
    @Query("SELECT r.productName, AVG(r.stars) as avgStars, COUNT(r) as reviewCount " +
            "FROM Review r " +
            "WHERE r.isPublic = true AND r.isVerified = true " +
            "GROUP BY r.productName " +
            "HAVING COUNT(r) >= 3 " +
            "ORDER BY avgStars DESC")
    List<Object[]> findTopRankedProducts(Pageable pageable);

    // 인증된 리뷰만 검색
    Page<Review> findByIsPublicTrueAndIsVerifiedTrueAndProductNameContainingIgnoreCaseOrderByCreatedAtDesc(
            String productName, Pageable pageable);
}