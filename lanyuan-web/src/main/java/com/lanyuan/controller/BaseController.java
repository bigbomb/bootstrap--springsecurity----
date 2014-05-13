package com.lanyuan.controller;
import com.lanyuan.pulgin.mybatis.plugin.PageView;
import com.lanyuan.util.Common;

/**
 * 
 * @author lanyuan
 * Email：mmm333zzz520@163.com
 * date：2014-2-17
 */
public class BaseController {
	PageView pageView = null;
	public PageView getPageView(String pageNow,String pagesize) {
		if (Common.isEmpty(pageNow)) {
			pageView = new PageView(1);
		} else {
			pageView = new PageView(Integer.parseInt(pageNow));
		}
		if (Common.isEmpty(pagesize)) {
			pagesize = "10";
		} 
		pageView.setPageSize(Integer.parseInt(pagesize));
		return pageView;
	}
}