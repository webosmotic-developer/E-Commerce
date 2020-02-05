package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
