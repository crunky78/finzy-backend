package com.finzy_backend.controller;

import com.finzy_backend.dto.request.ReviewRequest;
import com.finzy_backend.dto.response.ReviewResponse;
import com.finzy_backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    // TODO: 나중에 JWT에서 userId 추출하도록 변경
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @RequestParam Long userId,
            @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.createReview(userId, request));
    }

    // 전체 피드
    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(reviewService.getFeed(page, size, category));
    }

    // 상품 검색
    @GetMapping("/search")
    public ResponseEntity<Page<ReviewResponse>> searchReviews(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "false") boolean verifiedOnly,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reviewService.searchReviews(keyword, verifiedOnly, page, size));
    }

    // 리뷰 상세
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReview(id));
    }

    // 내 리뷰
    @GetMapping("/my")
    public ResponseEntity<List<ReviewResponse>> getMyReviews(@RequestParam Long userId) {
        return ResponseEntity.ok(reviewService.getMyReviews(userId));
    }

    // 랭킹
    @GetMapping("/ranking")
    public ResponseEntity<List<ReviewResponse>> getRanking(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(reviewService.getRanking(limit));
    }
}