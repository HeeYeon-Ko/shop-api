package dev.rest.product_order.entity.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductUpdateRequest {
    @NotBlank
    private String name;

    @Size(max = 1000)
    private String description;

    @Min(value = 0)
    private Double price;

    @Min(value = 1)
    private Integer stock;

    @NotBlank
    private String category;

    public ProductUpdateRequest(String name, String description, Double price, Integer stock, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }
}
