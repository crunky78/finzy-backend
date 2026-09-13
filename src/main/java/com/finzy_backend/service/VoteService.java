package com.finzy_backend.service;

import com.finzy_backend.dto.request.VoteRequest;
import com.finzy_backend.dto.response.VoteResponse;
import com.finzy_backend.entity.Vote;
import com.finzy_backend.entity.User;
import com.finzy_backend.repository.VoteRepository;
import com.finzy_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    // 투표 게시글 작성
    @Transactional
    public VoteResponse createVote(Long userId, VoteRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없어요"));

        Vote vote = Vote.builder()
                .user(user)
                .productName(request.getProductName())
                .content(request.getContent())
                .category(request.getCategory())
                .price(request.getPrice())
                .store(request.getStore())
                .isPublic(request.getIsPublic() != null ? request.getIsPublic() : true)
                .build();

        return VoteResponse.from(voteRepository.save(vote));
    }

    // 투표하기 (살아요/말아요)
    @Transactional
    public VoteResponse doVote(Long voteId, boolean isBuy) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new RuntimeException("투표를 찾을 수 없어요"));

        if (vote.getIsClosed()) {
            throw new RuntimeException("이미 마감된 투표예요");
        }

        if (isBuy) {
            vote.setBuyCount(vote.getBuyCount() + 1);
        } else {
            vote.setNotBuyCount(vote.getNotBuyCount() + 1);
        }

        return VoteResponse.from(voteRepository.save(vote));
    }

    // 피드 조회
    public Page<VoteResponse> getVoteFeed(int page, int size) {
        return voteRepository
                .findByIsPublicTrueOrderByCreatedAtDesc(PageRequest.of(page, size))
                .map(VoteResponse::from);
    }

    // 상세 조회
    public VoteResponse getVote(Long voteId) {
        return VoteResponse.from(voteRepository.findById(voteId)
                .orElseThrow(() -> new RuntimeException("투표를 찾을 수 없어요")));
    }
}