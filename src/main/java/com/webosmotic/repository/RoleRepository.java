package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.Enum.RoleType;
import com.webosmotic.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(RoleType name);

}
