package dev.rest.product_order.entity.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

@Getter
@Builder
public class ProductResponse extends RepresentationModel<ProductResponse>{
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private Long userId;

    public ProductResponse(Long id, String name, String description, Double price, Integer stock, String category, Long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.userId = userId;
    }
}
