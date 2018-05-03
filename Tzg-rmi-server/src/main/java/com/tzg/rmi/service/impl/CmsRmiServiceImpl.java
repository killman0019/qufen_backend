package com.tzg.rmi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.common.service.help.HelpcategyService;
import com.tzg.common.service.help.HelpdetailService;
import com.tzg.common.service.index.focus.FocusService;
import com.tzg.common.service.index.friendlylink.FriendlylinkService;
import com.tzg.common.service.index.institution.InstitutionService;
import com.tzg.common.service.index.mediareports.MediareportsService;
import com.tzg.common.service.index.news.NewsService;
import com.tzg.common.service.index.notice.NoticeService;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.utils.StringUtil;
import com.tzg.entitys.appversion.Appversion;
import com.tzg.entitys.appwelcomeimage.Appwelcomeimage;
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
import com.tzg.rmi.service.CmsRmiService;

public class CmsRmiServiceImpl implements CmsRmiService {

	@Autowired
	private FocusService focusService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NewsService newsService;
	@Autowired
	private InstitutionService institutionService;
	@Autowired
	private MediareportsService mediareportsService;
	@Autowired
	private FriendlylinkService friendlylinkService;
	@Autowired
	private HelpcategyService helpcategyService;
	@Autowired
	private HelpdetailService helpdetailService;
	@Autowired
	private SystemParamService systemParamService;

	@Autowired
	private RedisService redisService;
	
	@Override
	public List<Focus> findWapIndexList(){
		return focusService.findWapIndexList();
	}

	@Override
	public List<Focus> findWebIndexList() {
		return focusService.findWebIndexList();
	}

	@Override
	public Notice findNoticeById(Integer noticeId) throws Exception {
		return noticeService.findById(noticeId);
	}

	@Override
	public PageResult<Notice> findNoticePage(PaginationQuery query)
			throws Exception {
		return noticeService.findPage(query);
	}
	
	@Override
	public List<Notice> findNoticeList(String type){
		return noticeService.findIndexList(type);
	}
	
	@Override
	public List<News> findNewsIndexList(){
		return newsService.findIndexList();
	}

	@Override
	public News findNewsById(Integer newsId) throws Exception {
		return newsService.findById(newsId);
	}

	@Override
	public PageResult<News> findNewsPage(PaginationQuery query) throws Exception {
		return newsService.findPage(query);
	}
	
	@Override
	public List<Institution> findIndexInstitutionList(){
		return institutionService.findIndexList();
	}

	@Override
	public PageResult<Institution> findInstitutionPage(PaginationQuery query)
			throws Exception {
		return institutionService.findPage(query);
	}
	
	@Override
	public List<Friendlylink> findIndexFriendlylinkList(){
		return friendlylinkService.findIndexList();
	}
	
	@Override
	public List<Mediareports> findIndexMediareportsList(){
		return mediareportsService.findIndexList();
	}

	@Override
	public PageResult<Helpcategy> findHelpcategyPage(PaginationQuery query) throws Exception {
		return helpcategyService.findPage(query);
	}

	@Override
	public PageResult<Helpdetail> findHelpdetailPage(PaginationQuery query) throws Exception {
		return helpdetailService.findPage(query);
	}

	@Override
	public List<Focus> findFocus(Integer istate,Integer itype,Integer startRecord,Integer endRecord) throws Exception{
		return focusService.findFocus(istate,itype, startRecord, endRecord);
	}
	
	@Override
	public List<Focus> findFocusByQuery(PaginationQuery query) throws Exception {
		return focusService.findFocusByQuery(query);

	}

	
}
