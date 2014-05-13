package com.lanyuan.service;


import com.lanyuan.base.BaseService;
import com.lanyuan.entity.RoleAccount;
import com.lanyuan.entity.Roles;

public interface RolesService extends BaseService<Roles>{
	public Roles isExist(String name);
	public Roles findbyAccountRole(String accountId);
	public void addAccRole(RoleAccount roleAccount);
}
