package com.webosmotic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webosmotic.Enum.RoleType;
import com.webosmotic.entity.Role;
import com.webosmotic.repository.RoleRepository;

/**
 * Service class for the user role
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	/*
	 * Method to find the Role object for the given role name .
	 * @Param String roleName
	 * @Return Role object
	 */
	@Override
	public Role getRoleByName(RoleType name) {
		return roleRepository.findByName(name);
	}
}
