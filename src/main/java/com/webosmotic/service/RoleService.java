package com.webosmotic.service;

import com.webosmotic.Enum.RoleType;
import com.webosmotic.entity.Role;

public interface RoleService {
	
	Role getRoleByName(RoleType name);
}
