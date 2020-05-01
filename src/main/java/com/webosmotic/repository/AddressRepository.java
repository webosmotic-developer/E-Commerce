package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
