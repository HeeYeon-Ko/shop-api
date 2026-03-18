package dev.rest.product_order.entity.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

@Builder
@Getter
public class ProductDetailResponse extends RepresentationModel<ProductDetailResponse> {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private Long userId;
    private Map<String, String> _links;

    public ProductDetailResponse(Long id, String name, String description, Double price, Integer stock,
                                 String category, Long userId, Map<String, String> _links) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.userId = userId;
        this._links = _links;
    }
}
