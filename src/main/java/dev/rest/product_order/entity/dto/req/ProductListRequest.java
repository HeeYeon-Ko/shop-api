package dev.rest.product_order.entity.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductListRequest {
    private String category;
    private int page;
    private int size;

    public ProductListRequest(String category, int page, int size) {
        this.category = category;
        this.page = page;
        this.size = size;
    }
}
