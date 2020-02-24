package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

}
