package com.lanyuan.service;

import com.lanyuan.base.BaseService;
import com.lanyuan.entity.Account;
import com.lanyuan.pulgin.mybatis.plugin.PageView;



public interface AccountService extends BaseService<Account>{
	public Account querySingleAccount(String accountName);
	public Account isExist(String accountName);
	/**
	 * 验证用户登陆
	 * @author lanyuan
	 * Email：mmm333zzz520@163.com
	 * date：2014-2-25
	 * @param Account account
	 * @return
	 */
	public Account countAccount(Account account);
	
	/**
	 * 获取所有未匹配科组账号
	 * @param account
	 * @param pageView
	 * @return
	 */
	public PageView queryNoMatch(Account account,PageView pageView);
}
