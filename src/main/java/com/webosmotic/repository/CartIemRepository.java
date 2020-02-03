package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.CartItem;

public interface CartIemRepository extends JpaRepository<CartItem, Long>{
	
	

}
