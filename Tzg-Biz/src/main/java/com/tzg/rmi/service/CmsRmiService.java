package com.tzg.rmi.service;

import java.util.List;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.appversion.Appversion;
import com.tzg.entitys.community.Community;
import com.tzg.entitys.dayinvest.DayInvest;
import com.tzg.entitys.focus.Focus;
import com.tzg.entitys.friendlylink.Friendlylink;
import com.tzg.entitys.helpcategy.Helpcategy;
import com.tzg.entitys.helpdetail.Helpdetail;
import com.tzg.entitys.institution.Institution;
import com.tzg.entitys.mediareports.Mediareports;
import com.tzg.entitys.news.News;
import com.tzg.entitys.notice.Notice;

public interface CmsRmiService {

	/**
	 * 
	* @Title: findWapIndexList 
	* @Description: 查找首页焦点图  
	* @param @return    
	* @return List<Focus>
	* @see    
	* @throws
	 */
	public List<Focus> findWapIndexList();
	/**
	 * 
	* @Title: findWebIndexList 
	* @Description: 查找首页焦点图  
	* @param @return    
	* @return List<Focus>
	* @see    
	* @throws
	 */
	public List<Focus> findWebIndexList();
	
	/**
	 * 平台公告ById
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Notice findNoticeById(Integer noticeId) throws Exception;
	/**
	 * 
	* @Title: findNoticeList 
	* @Description: 查找首页还款&平台公告 
	* @param @param type
	* @param @return    
	* @return List<Notice>
	* @see    
	* @throws
	 */
	public List<Notice> findNoticeList(String type);
	public PageResult<Notice> findNoticePage(PaginationQuery query) throws Exception;
	
	/**
	 * 获取新闻信息ById
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public News findNewsById(Integer newsId) throws Exception;
	
	/**
	 * 
	* @Title: findNewsIndexList 
	* @Description: 首页新闻 
	* @param @return    
	* @return List<News>
	* @see    
	* @throws
	 */
	public List<News> findNewsIndexList();
	public PageResult<News> findNewsPage(PaginationQuery query) throws Exception;
	/**
	 * 
	* @Title: findIndexInstitutionList 
	* @Description: 首页机构
	* @param @return    
	* @return List<Institution>
	* @see    
	* @throws
	 */
	public List<Institution> findIndexInstitutionList();
	public PageResult<Institution> findInstitutionPage(PaginationQuery query) throws Exception;
	/**
	 * 
	* @Title: findIndexFriendlylinkList 
	* @Description: 首页友情链接  
	* @param @return    
	* @return List<Friendlylink>
	* @see    
	* @throws
	 */
	public List<Friendlylink> findIndexFriendlylinkList();
	/**
	 * 
	* @Title: findIndexMediareportsList 
	* @Description: 首页媒体报道 
	* @param @return    
	* @return List<Mediareports>
	* @see    
	* @throws
	 */
	public List<Mediareports> findIndexMediareportsList();
	
	/**
	 * 帮助中心
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public PageResult<Helpcategy> findHelpcategyPage(PaginationQuery query) throws Exception;
	
	/**
	 * 帮助中心详情
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public PageResult<Helpdetail> findHelpdetailPage(PaginationQuery query) throws Exception;
	/**
	 * 
	* @Title: findFocus 
	* @Description: TODO  
	* @param @param istate
	* @param @param itype
	* @param @param startRecord
	* @param @param endRecord
	* @param @return
	* @param @throws Exception    
	* @return List<Focus>
	* @see    
	* @throws
	 */
	public List<Focus> findFocus(Integer istate,Integer itype,Integer startRecord,Integer endRecord) throws Exception;
	
	/**
	 * 获取帮助信息集合
	 * 
	 * @param query 
	 * 			查询对象
	 * @return
	 * @throws Exception
	 */
	public List<Focus> findFocusByQuery(PaginationQuery query) throws Exception;
	

}
