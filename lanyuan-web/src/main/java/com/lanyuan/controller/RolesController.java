package com.lanyuan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanyuan.entity.Resources;
import com.lanyuan.entity.RoleAccount;
import com.lanyuan.entity.Roles;
import com.lanyuan.pulgin.mybatis.plugin.PageView;
import com.lanyuan.service.RolesService;
import com.lanyuan.util.Common;

/**
 * 
 * @author bigbomb
 * @date 2014-2-19
 * @Email: 76169099@qq.com
 * @version 1.0b
 */
@Controller
@RequestMapping("/background/role/")
public class RolesController extends BaseController{
	@Inject
	private RolesService roleService;
	
	@RequestMapping("list")
	public String list(Model model, Resources menu, String pageNow) {
		return Common.BACKGROUND_PATH+"/role/list";
	}
	/**
	 * @param role
	 * @param pageNow
	 * @param pagesize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("query")
	public PageView query(Roles role,String pageNow,String pagesize) {
		pageView = roleService.query(getPageView(pageNow,pagesize), role);
		return pageView;
	}
	
	@ResponseBody
	@RequestMapping("queryAll")
	public Map<String, Object> queryAll(Roles role) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roles",  roleService.queryAll(role));
		return map;
	}
	
	/**
	 * 添加角色
	 * 
	 * @param Map
	 * @param role
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public Map<String, Object> add(Roles role) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			roleService.add(role);
			map.put("flag", "true");
		} catch (Exception e) {
			map.put("flag", "false");
		}
		return map;
	}

	
	/**
	 * 跑到新增界面
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI() {
		return Common.BACKGROUND_PATH+"/role/add";
	}
	/**
	 * 跑到新增界面
	 * 
	 * @param model
	 * @param roleId
	 * @return 
	 */
	@RequestMapping("editUI")
	public String editUI(Model model,String roleId) {
		Roles role = roleService.getById(roleId);
		model.addAttribute("role", role);
		return Common.BACKGROUND_PATH+"/role/edit";
	}
	
	/**
	 * 验证账号是否存在
	 * @param name
	 * @return
	 */
	@RequestMapping("isExist")
	@ResponseBody
	public boolean isExist(String name){
		Roles role = roleService.isExist(name);
		if(role == null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 删除
	 * 
	 * @param model
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteById")
	public Map<String, Object> deleteById(Model model, String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String id[] = ids.split(",");
			for (String string : id) {
				if(!Common.isEmpty(string)){
					roleService.delete(string);
				}
			}
			map.put("flag", "true");
		} catch (Exception e) {
			map.put("flag", "false");
		}
		return map;
	}
	
	/**
	 * 更新类型
	 * 
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("update")
	public Map<String, Object> update(Model model, Roles role) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			roleService.update(role);
			map.put("flag", "true");
		} catch (Exception e) {
			map.put("flag", "false");
		}
		return map;
	}
	
	
	/**
	 * 根据账号查角色
	 * 
	 * @param model
	 * @param accountId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findbyAccountRole")
	public Roles findbyAccountRole(Model model, String accountId) {
		return roleService.findbyAccountRole(accountId);
	}
	
	/**
	 * 给账号分配角色
	 * 
	 * @param model
	 * @param roleAccount
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addAccRole")
	public Map<String, Object> addAccRole(Model model, RoleAccount roleAccount ) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			roleService.addAccRole(roleAccount);
			map.put("flag", "true");
		} catch (Exception e) {
			map.put("flag", "false");
		}
		return map;
	}
	
}