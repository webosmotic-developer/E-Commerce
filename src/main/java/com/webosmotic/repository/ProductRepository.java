package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
