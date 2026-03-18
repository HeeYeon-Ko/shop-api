package dev.rest.product_order.controller;

import dev.rest.product_order.entity.dto.req.ProductCreateRequest;
import dev.rest.product_order.entity.dto.req.ProductUpdateRequest;
import dev.rest.product_order.entity.dto.res.ProductDetailResponse;
import dev.rest.product_order.entity.dto.res.ProductListResponse;
import dev.rest.product_order.entity.dto.res.ProductResponse;
import dev.rest.product_order.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping
    @Operation(summary = "상품 등록")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductCreateRequest productRequest
    ){
        ProductResponse response = productService.createProduct(productRequest);

        // HATEOAS 링크 추가
        response.add(linkTo(methodOn(ProductController.class).getProductById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(ProductController.class).getProducts(null, 0, 10)).withRel("list-products").withType("PUT"));
        response.add(linkTo(methodOn(ProductController.class).updateProduct(response.getId(), new ProductUpdateRequest(
                response.getName(),
                response.getDescription(),
                response.getPrice(),
                response.getStock(),
                response.getCategory()
        ))).withRel("update-product").withType("PUT"));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 상품 전체 조회
    @GetMapping
    @Operation(summary = "상품 전체 조회")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        ProductListResponse productListResponse = productService.getProducts(category, page, size);

        // HATEOAS 링크 추가
        productListResponse.getProducts().forEach(product ->
                product.add(linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel())
        );
        productListResponse.add(linkTo(methodOn(ProductController.class).getProducts(category, page, size)).withSelfRel());

        return new ResponseEntity<>(productListResponse, HttpStatus.OK);
    }

    // 상품 상세 조회
    @GetMapping("/{id}")
    @Operation(summary = "상품 상세 조회")
    public ResponseEntity<ProductDetailResponse> getProductById(@PathVariable Long id) {
        ProductDetailResponse productDetailResponse = productService.getProductById(id);

        // HATEOAS 링크 추가
        productDetailResponse.add(linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel());
        productDetailResponse.add(linkTo(methodOn(ProductController.class).updateProduct(id, null)).withRel("update-product").withType("PUT"));

        return new ResponseEntity<>(productDetailResponse, HttpStatus.OK);
    }

    // 상품 수정
    @PutMapping("/{id}")
    @Operation(summary = "상품 수정")
    public ResponseEntity<ProductDetailResponse> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest productRequest) {
        ProductDetailResponse updatedProductResponse = productService.updateProduct(id, productRequest);

        // HATEOAS 링크 추가
        updatedProductResponse.add(linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel());
        updatedProductResponse.add(linkTo(methodOn(ProductController.class).getProducts(null, 0, 10)).withRel("list-products"));

        return new ResponseEntity<>(updatedProductResponse, HttpStatus.OK);
    }

}
