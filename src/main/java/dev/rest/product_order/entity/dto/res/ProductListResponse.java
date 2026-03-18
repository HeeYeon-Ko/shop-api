package dev.rest.product_order.entity.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class ProductListResponse extends RepresentationModel<ProductListResponse> {
    private List<ProductResponse> products;
    private Map<String, String> _links;

    public ProductListResponse(List<ProductResponse> products, Map<String, String> _links) {
        this.products = products;
        this._links = _links;
    }
}
