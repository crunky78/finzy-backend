package com.finzy_backend.dto.response;

import com.finzy_backend.entity.Vote;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VoteResponse {

    private Long id;
    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private String productName;
    private String content;
    private String category;
    private Integer price;
    private String store;
    private Integer buyCount;
    private Integer notBuyCount;
    private Integer totalCount;
    private Double buyPercent;
    private Boolean isClosed;
    private LocalDateTime createdAt;

    public static VoteResponse from(Vote vote) {
        int total = vote.getBuyCount() + vote.getNotBuyCount();
        double buyPct = total > 0 ? (double) vote.getBuyCount() / total * 100 : 0;

        return VoteResponse.builder()
                .id(vote.getId())
                .userId(vote.getUser().getId())
                .nickname(vote.getUser().getNickname())
                .profileImageUrl(vote.getUser().getProfileImageUrl())
                .productName(vote.getProductName())
                .content(vote.getContent())
                .category(vote.getCategory() != null ? vote.getCategory().name() : null)
                .price(vote.getPrice())
                .store(vote.getStore())
                .buyCount(vote.getBuyCount())
                .notBuyCount(vote.getNotBuyCount())
                .totalCount(total)
                .buyPercent(Math.round(buyPct * 10.0) / 10.0)
                .isClosed(vote.getIsClosed())
                .createdAt(vote.getCreatedAt())
                .build();
    }
}