package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findbyCreatedBy(Long id);

}
