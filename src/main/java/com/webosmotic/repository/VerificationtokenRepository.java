package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webosmotic.Enum.TokenType;
import com.webosmotic.entity.VerificationToken;

public interface VerificationtokenRepository extends JpaRepository<VerificationToken, Long> {
	
	@Query(value = "Select v from VerificationToken v where v.token = :token and v.tokenType = :type")
	VerificationToken getByToken(@Param("token") String token, @Param("type") TokenType type);
	
	

}
