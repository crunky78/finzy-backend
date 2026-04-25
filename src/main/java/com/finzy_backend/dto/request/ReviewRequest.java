package com.finzy_backend.dto.request;

import com.finzy_backend.entity.Review;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequest {

    @NotBlank(message = "상품명을 입력해주세요")
    private String productName;

    @NotNull(message = "카테고리를 선택해주세요")
    private Review.Category category;

    private Integer price;

    private String store;

    @NotNull(message = "별점을 선택해주세요")
    @Min(value = 1, message = "별점은 1점 이상이어야 해요")
    @Max(value = 5, message = "별점은 5점 이하여야 해요")
    private Integer stars;

    @NotBlank(message = "장점을 입력해주세요")
    private String pros;

    @NotBlank(message = "단점을 입력해주세요")
    private String cons;

    private String comment;

    private Review.VerificationType verificationType;

    private String verificationImageUrl;

    private Boolean isPublic = true;
}