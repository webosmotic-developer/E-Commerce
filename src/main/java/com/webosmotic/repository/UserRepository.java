package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByUsername(String userName);

	User findByUsernameAndEmail(String username, String email);

}
