package com.finzy_backend.controller;

import com.finzy_backend.dto.request.VoteRequest;
import com.finzy_backend.dto.response.VoteResponse;
import com.finzy_backend.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    // 투표 게시글 작성
    @PostMapping
    public ResponseEntity<VoteResponse> createVote(
            @RequestParam Long userId,
            @Valid @RequestBody VoteRequest request) {
        return ResponseEntity.ok(voteService.createVote(userId, request));
    }

    // 투표하기
    @PostMapping("/{id}/vote")
    public ResponseEntity<VoteResponse> doVote(
            @PathVariable Long id,
            @RequestParam boolean isBuy) {
        return ResponseEntity.ok(voteService.doVote(id, isBuy));
    }

    // 피드
    @GetMapping
    public ResponseEntity<Page<VoteResponse>> getVoteFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(voteService.getVoteFeed(page, size));
    }

    // 상세
    @GetMapping("/{id}")
    public ResponseEntity<VoteResponse> getVote(@PathVariable Long id) {
        return ResponseEntity.ok(voteService.getVote(id));
    }
}