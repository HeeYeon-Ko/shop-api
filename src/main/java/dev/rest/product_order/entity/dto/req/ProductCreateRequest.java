package dev.rest.product_order.entity.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

// 상품 등록 요청 DTO
@Builder
public record ProductCreateRequest(
        @NotBlank
        String name,           // 상품명

        @Size(max = 1000)
        String description,    // 상품 설명

        @Min(value = 0)
        Double price,          // 가격

        @Min(value = 1)
        Integer stock,         // 재고

        @NotBlank
        String category        // 카테고리
) {
}
