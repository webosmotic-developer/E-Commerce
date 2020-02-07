package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findByCreatedBy(Long id);

	Cart findByCreatedByAndId(Long id, Long cartId);

	//Cart findbyCreatedBy(Long id);

}
