package com.lanyuan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanyuan.entity.Account;
import com.lanyuan.entity.Resources;
import com.lanyuan.service.AccountService;
import com.lanyuan.service.ResourcesService;
import com.lanyuan.util.Common;
import com.lanyuan.util.Md5Tool;
import com.lanyuan.util.PropertiesUtils;
import com.lanyuan.util.TreeObject;
import com.lanyuan.util.TreeUtil;

/**
 * 进行管理后台框架界面的类
 * @author lanyuan
 * 2013-11-19
 * @Email: mmm333zzz520@163.com
 * @version 1.0v
 */
@Controller
@RequestMapping ("/")
public class BackgroundController
{
	@Autowired
	private AccountService accountService;
	@Autowired
	private AuthenticationManager myAuthenticationManager;
	
	@Autowired
	private ResourcesService resourcesService;
	/**
	 * @return
	 */
	@RequestMapping ("login")
	public String login(Model model,HttpServletRequest request)
	{
		//重新登录时销毁该用户的Session
		Object o = request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if(null != o){
			request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
		}
		return Common.BACKGROUND_PATH+"/framework/login";
	}
	
	@RequestMapping ("loginCheck")
	@ResponseBody
	public Map<String, Object> loginCheck(String username,String password){
		Map<String, Object> map = new HashMap<String, Object>();
			Account account = new Account();
			account.setAccountName(username);
			account.setPassword(Md5Tool.getMd5(password));
			// 验证用户账号与密码是否正确
			Account users = this.accountService.countAccount(account);
			map.put("error", "0");
			if (users == null) {
				map.put("error", "用户或密码不正确！");
			}else if (users != null && Common.isEmpty(users.getAccountName())) {
				map.put("error", "用户或密码不正确！");
			}
			return map;
	}
	
	@RequestMapping ("submitlogin")
	public String submitlogin(String username,String password,HttpServletRequest request){
		try {
			if (!request.getMethod().equals("POST")) {
				request.setAttribute("error","支持POST方法提交！");
			}
			if (Common.isEmpty(username) || Common.isEmpty(password)) {
				request.setAttribute("error","用户名或密码不能为空！");
				return Common.BACKGROUND_PATH+"/framework/login";
			}
			// 验证用户账号与密码是否正确
			Account users = this.accountService.querySingleAccount(username);
			if (users == null) {
				request.setAttribute("error", "用户或密码不正确！");
			    return Common.BACKGROUND_PATH+"/framework/login";
			}
			else if (users != null && Common.isEmpty(users.getAccountName()) && !Md5Tool.getMd5(password).equals(users.getPassword())){
				request.setAttribute("error", "用户或密码不正确！");
			    return Common.BACKGROUND_PATH+"/framework/login";
			}
			Authentication authentication = myAuthenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username,users.getPassword()));
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			HttpSession session = request.getSession(true);  
		    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);  
		    // 当验证都通过后，把用户信息放在session里
			request.getSession().setAttribute("userSession", users);
			request.getSession().setAttribute("userSessionId", users.getId());
			request.removeAttribute("error");
		} catch (AuthenticationException ae) {  
			request.setAttribute("error", "登录异常，请联系管理员！");
		    return Common.BACKGROUND_PATH+"/framework/login";
		}
		return "redirect:index.html";
	}
	
	/**
	 * @return
	 */
	@RequestMapping ("index")
	public String index(Model model,Resources resources,HttpServletRequest request)
	{
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
		return Common.BACKGROUND_PATH+"/framework/index";
	}
	@RequestMapping ("menu")
	public String menu(Model model)
	{
		return Common.BACKGROUND_PATH+"/framework/menu";
	}
	
	/**
	 * 获取某个用户的权限资源
	 * @author lanyuan
	 * Email：mmm333zzz520@163.com
	 * date：2014-3-4
	 * @param request
	 * @return
	 */
	@RequestMapping ("findAuthority")
	@ResponseBody
	public List<String> findAuthority(HttpServletRequest request){
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");   
		List<GrantedAuthority> authorities = (List<GrantedAuthority>)securityContextImpl.getAuthentication().getAuthorities();   
		List<String> strings = new ArrayList<String>();
		for (GrantedAuthority grantedAuthority : authorities) {   

			strings.add(grantedAuthority.getAuthority());   

		}  
		return strings;
	}

}
