package com.webosmotic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.ProductSummary;

public interface ProductSummaryRepository extends JpaRepository<ProductSummary, Long>{

	Optional<ProductSummary> findByProductId(Long id);

}
