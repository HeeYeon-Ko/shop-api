package dev.rest.product_order.repository;

import dev.rest.product_order.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 카테고리별로 상품 조회
    List<Product> findByCategory(String category);

    // 페이징을 지원하는 카테고리별 상품 조회
    Page<Product> findByCategory(String category, Pageable pageable);
}
