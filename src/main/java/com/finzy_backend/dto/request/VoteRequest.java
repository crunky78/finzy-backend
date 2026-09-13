package com.finzy_backend.dto.request;

import com.finzy_backend.entity.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteRequest {

    @NotBlank(message = "상품명을 입력해주세요")
    private String productName;

    @NotBlank(message = "고민 내용을 입력해주세요")
    private String content;

    private Review.Category category;
    private Integer price;
    private String store;
    private Boolean isPublic = true;
}