package dev.rest.product_order.entity;

import dev.rest.product_order.entity.dto.req.ProductUpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 상품 ID

    private String name;  // 상품명
    private String description;  // 상품 설명
    private Double price;  // 가격
    private Integer stock;  // 재고
    private String category;  // 카테고리
    private Long userId;

    // 편의 메서드
    public void updateProduct(ProductUpdateRequest productRequest) {
        this.name =  productRequest.getName();
        this.description = productRequest.getDescription();
        this.price = productRequest.getPrice();
        this.stock = productRequest.getStock();
        this.category = productRequest.getCategory();
    }
}
