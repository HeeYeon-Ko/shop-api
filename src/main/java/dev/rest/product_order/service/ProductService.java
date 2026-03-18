package dev.rest.product_order.service;

import dev.rest.product_order.entity.Product;
import dev.rest.product_order.entity.dto.req.ProductCreateRequest;
import dev.rest.product_order.entity.dto.req.ProductUpdateRequest;
import dev.rest.product_order.entity.dto.res.ProductDetailResponse;
import dev.rest.product_order.entity.dto.res.ProductListResponse;
import dev.rest.product_order.entity.dto.res.ProductResponse;
import dev.rest.product_order.repository.ProductRepository;
import dev.rest.product_order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 상품 등록
    public ProductResponse createProduct(ProductCreateRequest productRequest){
        // 인증 후 수정
        Long userId = 1L; // 1번 유저

        // 유저 정보 조회
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 요청 DTO -> 엔티티
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .category(productRequest.category())
                .userId(1L)
                .build();

        // 상품 등록
        Product savedProduct = productRepository.save(product);

        // 엔티티 -> 응답 DTO
        return ProductResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .price(savedProduct.getPrice())
                .stock(savedProduct.getStock())
                .category(savedProduct.getCategory())
                .userId(savedProduct.getUserId())
                .build();
    }

    // 상품 전체 조회
    public ProductListResponse getProducts(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products;

        // 카테고리 필터링 적용
        if (category != null && !category.isEmpty()) {
            products = productRepository.findByCategory(category, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }

        // Product -> ProductResponse 변환
        List<ProductResponse> productResponses = products.getContent().stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());

        // 페이지네이션 링크 생성
        Map<String, String> links = generatePaginationLinks(page, size, products);

        return new ProductListResponse(productResponses, links);
    }

    // 상품 상세 조회
    public ProductDetailResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductDetailResponse productDetailResponse = convertToProductDetailResponse(product);
        //log.debug("productDetailResponse: {}", productDetailResponse);
        return productDetailResponse;
    }

    // 상품 수정
    public ProductDetailResponse updateProduct(Long id, ProductUpdateRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.updateProduct(productRequest);
        productRepository.save(product);
        return convertToProductDetailResponse(product);
    }

    // Product -> ProductResponse 변환
    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .userId(product.getUserId())
                .build();
    }

    // Product -> ProductDetailResponse 변환
    private ProductDetailResponse convertToProductDetailResponse(Product product) {
        Map<String, String> links = new HashMap<>();
        links.put("self", "/api/products/" + product.getId());
        links.put("profile", "/swagger-ui/index.html");

        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .userId(product.getUserId())
                ._links(links)
                .build();
    }

    // 페이징 처리용 링크 생성
    private Map<String, String> generatePaginationLinks(int page, int size, Page<Product> products) {
        Map<String, String> links = new HashMap<>();
        links.put("self", "/api/products?page=" + page + "&size=" + size);

        if (products.hasNext()) {
            links.put("next", "/api/products?page=" + (page + 1) + "&size=" + size);
        }

        if (page > 0) {
            links.put("prev", "/api/products?page=" + (page - 1) + "&size=" + size);
        }

        return links;
    }

}
