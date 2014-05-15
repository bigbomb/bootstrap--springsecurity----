package com.lanyuan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanyuan.entity.Dic;
import com.lanyuan.entity.Resources;
import com.lanyuan.entity.DicType;
import com.lanyuan.pulgin.mybatis.plugin.PageView;
import com.lanyuan.service.DicTypeService;
import com.lanyuan.service.ResourcesService;
import com.lanyuan.util.Common;
import com.lanyuan.util.PropertiesUtils;

/**
 * 
 * @author lanyuan
 * 2013-11-19
 * @Email: mmm333zzz520@163.com
 * @version 1.0v
 */
@Controller
@RequestMapping("/background/dicType/")
public class DicTypeController extends BaseController{
	@Inject
	private DicTypeService dicTypeService;
	
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
		
		return Common.BACKGROUND_PATH+"/dicType/list";
	}
	/**
	 * @param model
	 * 存放返回界面的model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("query")
	public PageView query(DicType dicType,String pageNow,String pagesize) {
		pageView = dicTypeService.query(getPageView(pageNow,pagesize), dicType);
		return pageView;
	}
	
	/**
	 * 获取未科组匹配账号
	 * @param account
	 * @param pageNow
	 * @param pagesize
	 * @return
	 */
//	@ResponseBody
//	@RequestMapping("queryNoMatch")
//	public PageView queryNoMatch(DicType dicType,String pageNow,String pagesize) {
//		pageView = dicTypeService.queryNoMatch(dicType, getPageView(pageNow,pagesize));
//		return pageView;
//	}
	
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
	public Map<String, Object> add(DicType dicType) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dicTypeService.add(dicType);
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
		return Common.BACKGROUND_PATH+"/dicType/add";
	}
	/**
	 * 跑到新增界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("editUI")
	public String editUI(Model model,String id) {
		DicType dicType = dicTypeService.getById(id);
		model.addAttribute("dicType", dicType);
		return Common.BACKGROUND_PATH+"/dicType/edit";
	}
	
	/**
	 * @author lanyuan
	 * Email：mmm333zzz520@163.com
	 * date：2014-2-19
	 * @param DicType dicType
	 * @return
	 */
	@RequestMapping("isExist")
	@ResponseBody
	public boolean isExist(DicType dicType){
		DicType dt = dicTypeService.isExist(dicType);
		if(dt == null){
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
					dicTypeService.delete(string);
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
//	@ResponseBody
//	@RequestMapping("updateState")
//	public Map<String, Object> updateState(Model model, String ids,String state) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			String id[] = ids.split(",");
//			for (String string : id) {
//				if(!Common.isEmpty(string)){
//					Account account = new Account();
//					account.setId(Integer.parseInt(string));
//					account.setState(state);
//					accountService.update(account);
//				}
//			}
//			map.put("flag", "true");
//		} catch (Exception e) {
//			map.put("flag", "false");
//		}
//		return map;
//	}
	/**
	 * 更新类型
	 * 
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("update")
	public Map<String, Object> update(Model model, DicType dicType) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dicTypeService.update(dicType);
			map.put("flag", "true");
		} catch (Exception e) {
			map.put("flag", "false");
		}
		return map;
	}
	@ResponseBody
	@RequestMapping("findDicType")
	public List<DicType> findDicType(Model model, DicType dicType) {
		return dicTypeService.queryAll(dicType);
	}
	@ResponseBody
	@RequestMapping(value ="listDicType", method=RequestMethod.GET)
	public JSONObject listDicType(Model model, DicType dicType,@RequestParam(value = "sEcho") int sEcho,  @RequestParam(value = "iDisplayStart") int start, 
            @RequestParam(value = "iDisplayLength") int pageSize) {
		dicType.setStart(start);
		dicType.setEnd(pageSize);
		List<DicType> dicList =	dicTypeService.queryAll(dicType);
		long dicLIstCount = dicTypeService.count(dicType);
		JSONObject jsonObject = new JSONObject(); 
        String aaData=JSONArray.fromObject(dicList).toString(); 
        jsonObject.put("sEcho", sEcho);
        jsonObject.put("iTotalRecords", String.valueOf(dicLIstCount));
        jsonObject.put("iTotalDisplayRecords", String.valueOf(dicLIstCount));
        jsonObject.put("aaData", aaData);
        return jsonObject;
	}
	@ResponseBody
	@RequestMapping("findDicTypeDetail")
	public Map<String, Object> findDicTypeDetail(Model model, DicType dicType) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
		    DicType qdicType = dicTypeService.queryById(dicType);
		    map.put("description", qdicType.getDescription());
		    map.put("id", qdicType.getId());
		} catch (Exception e) {
			
		}
		return map;
		
	}
}