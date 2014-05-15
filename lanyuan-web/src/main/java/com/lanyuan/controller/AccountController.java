package com.lanyuan.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanyuan.entity.Account;
import com.lanyuan.entity.Dic;
import com.lanyuan.entity.Resources;
import com.lanyuan.pulgin.mybatis.plugin.PageView;
import com.lanyuan.service.AccountService;
import com.lanyuan.service.ResourcesService;
import com.lanyuan.util.Common;
import com.lanyuan.util.Md5Tool;
import com.lanyuan.util.POIUtils;
import com.lanyuan.util.PropertiesUtils;

/**
 * 
 * @author lanyuan
 * 2013-11-19
 * @Email: mmm333zzz520@163.com
 * @version 1.0v
 */
@Controller
@RequestMapping("/background/account/")
public class AccountController extends BaseController{
	@Inject
	private AccountService accountService;
	@Inject
	private ResourcesService resourcesService;
	
	@RequestMapping("list")
	public String list(Model model, Resources resources,HttpServletRequest request) {
		
		List<Resources> rs;
		if (PropertiesUtils.findPropertiesKey("rootName").equals(Common.findAuthenticatedUsername()))
		{
		    rs =resourcesService.queryAll(resources);
		}
		else
		{
			rs =resourcesService.findAccountResourcess(Common.findUserSessionId(request));
		}
		model.addAttribute("resourceslists", rs);
		
		return Common.BACKGROUND_PATH+"/account/list";
	}
	/**
	 * @param model
	 * 存放返回界面的model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("query")
	public PageView query(Account account,String pageNow,String pagesize) {
		pageView = accountService.query(getPageView(pageNow,pagesize), account);
		return pageView;
	}
	@RequestMapping("exportExcel")
	public void exportExcel(HttpServletResponse response,Account account) {
		 List<Account> acs =accountService.queryAll(account);
		POIUtils.exportToExcel(response, "账号报表", acs, Account.class, "账号", acs.size());
	}
	/**
	 * 保存数据
	 * 
	 * @param model
	 * @param videoType
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("add")
	@ResponseBody
	public Map<String, Object> add(Account account) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			account.setPassword(Md5Tool.getMd5(account.getPassword()));
			accountService.add(account);
			map.put("flag", "true");
		} catch (Exception e) {
			map.put("flag", "false");
		}
		return map;
	}

	
	/**
	 * 跑到新增界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI() {
		return Common.BACKGROUND_PATH+"/account/add";
	}
	
	/**
	 * 账号角色页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("accRole")
	public String accRole(Model model,String accountName,String roleName) {

		try {
			accountName=java.net.URLDecoder.decode(accountName,"UTF-8");  
			roleName= java.net.URLDecoder.decode(roleName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		} 
		model.addAttribute("accountName", accountName);
		model.addAttribute("roleName", roleName);
		
		return Common.BACKGROUND_PATH+"/account/acc_role";
	}
	
	/**
	 * 跑到新增界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("editUI")
	public String editUI(Model model,String accountId) {
		Account account = accountService.getById(accountId);
		model.addAttribute("account", account);
		return Common.BACKGROUND_PATH+"/account/edit";
	}
	
	/**
	 * 验证账号是否存在
	 * @author lanyuan
	 * Email：mmm333zzz520@163.com
	 * date：2014-2-19
	 * @param name
	 * @return
	 */
	@RequestMapping("isExist")
	@ResponseBody
	public boolean isExist(String name){
		Account account = accountService.isExist(name);
		if(account == null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 删除
	 * 
	 * @param model
	 * @param videoTypeId
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("deleteById")
	public Map<String, Object> deleteById(Model model, String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String id[] = ids.split(",");
			for (String string : id) {
				if(!Common.isEmpty(string)){
				accountService.delete(string);
				}
			}
			map.put("flag", "true");
		} catch (Exception e) {
			map.put("flag", "false");
		}
		return map;
	}
	/**
	 * 删除
	 * 
	 * @param model
	 * @param videoTypeId
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("updateState")
	public Map<String, Object> updateState(Model model, String ids,String state) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String id[] = ids.split(",");
			for (String string : id) {
				if(!Common.isEmpty(string)){
					Account account = new Account();
					account.setId(Integer.parseInt(string));
					account.setState(state);
					accountService.update(account);
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
	public Map<String, Object> update(Model model, Account account) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
	
			account.setPassword(Md5Tool.getMd5(account.getPassword()));
			accountService.update(account);
			map.put("flag", "true");
		} catch (Exception e) {
			map.put("flag", "false");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value ="findAccount", method=RequestMethod.GET)
	public JSONObject findDic(Model model, Account account,@RequestParam(value = "sEcho") int sEcho,  @RequestParam(value = "iDisplayStart") int start, 
            @RequestParam(value = "iDisplayLength") int pageSize) {
		account.setStart(start);
		account.setEnd(pageSize);
		List<Account> dicList =	accountService.queryAll(account);
		long dicLIstCount = accountService.count(account);
		JSONObject jsonObject = new JSONObject(); 
        String aaData=JSONArray.fromObject(dicList).toString(); 
        jsonObject.put("sEcho", sEcho);
        jsonObject.put("iTotalRecords", String.valueOf(dicLIstCount));
        jsonObject.put("iTotalDisplayRecords", String.valueOf(dicLIstCount));
        jsonObject.put("aaData", aaData);
        return jsonObject;
	}
}