package com.webosmotic.service;

import com.webosmotic.entity.Role;
import com.webosmotic.entity.RoleType;

public interface RoleService {
	
	Role getRoleByName(RoleType name);
}
