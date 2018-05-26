package com.tzg.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.leopard.resource.Resource;
import com.tzg.service.resource.ResourceService;

/**
 * @Description:系统权限控制的资源表action
 * @author wxg
 * @Date: 2014-12-02
 * 
 */
@Controller
@RequestMapping("/resources")
public class ResourceController extends BaseController {

	private static Logger log = Logger.getLogger(ResourceController.class);

	@Autowired
	private ResourceService resourceService;

	/**
	 * 跳转到记录新增页面
	 * 
	 * @param iParentId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(String iParentId, Model model) {
		if (StringUtils.isBlank(iParentId)) {
			iParentId = "0";
		}
		model.addAttribute("iParentId", iParentId);
		return "/resources/new";
	}

	/**
	 * 记录新增
	 * 
	 * @return
	 */
	@RequestMapping(value = "/save")
	public String save(@ModelAttribute("resource") Resource resource,
			Model model) {
		try {
			resourceService.save(resource);
			model.addAttribute(SUCCESS, true);
			return "redirect:/resources";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", resource);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/resources/new";
		}
	}

	/**
	 * 跳转到编辑界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String edit(String id, Model model) {
		Resource resource = null;
		try {
			resource = resourceService.findById(Integer.parseInt(id));
			model.addAttribute("resource", resource);
			return "/resources/update";
		} catch (Exception e) {
			log.error(e);
			return "/resources/update";
		}
	}

	/**
	 * 记录更新
	 * 
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(int id, @ModelAttribute Resource resource, Model model) {
		resource.setId(id);
		try {
			resourceService.update(resource);
			return "redirect:/resources";
		} catch (Exception e) {
			log.error("资源更新记录异常", e);
			model.addAttribute("resource", resource);
			return "/resources/update";
		}
	}

	/**
	 * 记录删除
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model) {
		try {
			resourceService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/resources";
		} catch (Exception e) {
			log.error("资源删除记录异常", e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, "[删除失败]:" + e.getMessage());
			return "redirect:/resources";
		}
	}

	/**
	 * 分页列表展示
	 * 
	 * @param pageIndex
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = "vcName", required = false, defaultValue = "") String vcName,
			@RequestParam(value = "iType", required = false, defaultValue = "") String iType,
			String iParentId, Model model) {
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("vcName", vcName);
		query.addQueryData("iType", iType);
		query.addQueryData("iParentId", iParentId);
		query.setPageIndex(pageIndex);
		PageResult<Resource> resourcePage = resourceService
				.queryResourceListByPage(query);
		List<Resource> parentLists = resourceService.queryParentVcNameAll();
		List<Resource> firstLevelLists = resourceService
				.queryFirstLevelResourceAll();
		model.addAttribute("result", resourcePage);
		model.addAttribute("vcName", vcName);
		model.addAttribute("parentLists", parentLists);
		model.addAttribute("iType", iType);
		model.addAttribute("firstLevelLists", firstLevelLists);
		model.addAttribute("iParentId", iParentId);
		return "/resources/list";
	}

	@RequestMapping(value = "/jsondata",produces="text/html;charset=UTF-8")//,produces="text/html;charset=UTF-8"
	@ResponseBody
	public String jsondata(String vcName, String iType, String iParentId,Model model) {
		Map<String, String> map = new HashMap<String, String>();
		if (null != vcName && !"".equals(vcName)) {
			map.put("vcName", vcName);
		}
		if (null != iType && !"".equals(iType)) {
			map.put("iType", iType);
		}
		if (null != iParentId && !"".equals(iParentId)) {
			map.put("iParentId", iParentId);
		}
		List<Resource> list = resourceService.queryResourcesAllList(map);
		 
//		String str="";
//		try {
//			str = new String(createTreeJson(list).getBytes("utf-8"),"ISO-8859-1");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return str;
		return createTreeJson(list);
	}

	private List<Resource> createTree(List<Resource> list) {
		List<Resource> treelist = new ArrayList<Resource>();
		for (int i = 0; i < list.size(); i++) {
			Resource resource = list.get(i);
			if (resource.getiParentId() == 0) {
				createBranchList(list, resource);
				treelist.add(resource);

			}
		}
		return treelist;
	}

	private void createBranchList(List<Resource> list, Resource currentNode) {
		/*
		 * 将javabean对象解析成为JSON对象
		 */
		List<Resource> childArray = new ArrayList<Resource>();
		/*
		 * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
		 * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该 节点放入当前节点的子节点列表中
		 */
		for (int i = 0; i < list.size(); i++) {
			Resource newNode = list.get(i);
			if (newNode.getiParentId() != null
					&& newNode.getiParentId().compareTo(currentNode.getId()) == 0) {
				createBranchList(list, newNode);
				childArray.add(newNode);
			}
		}

		/*
		 * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
		 */
		if (!childArray.isEmpty()) {
			currentNode.setChildren(childArray);
		}

	}

	private String createTreeJson(List<Resource> list) {
		JSONArray rootArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Resource resource = list.get(i);
			if (resource.getiParentId() == 0) {
				JSONObject rootObj = createBranch(list, resource);
				rootArray.add(rootObj);
			}
		}
		return rootArray.toString();
	}

	private JSONObject createBranch(List<Resource> list, Resource currentNode) {
		/*
		 * 将javabean对象解析成为JSON对象
		 */
		JSONObject currentObj = JSONObject.fromObject(currentNode);
		JSONArray childArray = new JSONArray();
		/*
		 * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
		 * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该 节点放入当前节点的子节点列表中
		 */
		for (int i = 0; i < list.size(); i++) {
			Resource newNode = list.get(i);
			if (newNode.getiParentId() != null
					&& newNode.getiParentId().compareTo(currentNode.getId()) == 0) {
				JSONObject childObj = createBranch(list, newNode);
				childArray.add(childObj);
			}
		}

		/*
		 * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
		 */
		if (!childArray.isEmpty()) {
			currentObj.put("children", childArray);
		}

		return currentObj;
	}

}
