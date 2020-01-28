package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.Role;
import com.webosmotic.entity.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(RoleType name);
	

}
