package com.tzg.service.news;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.news.News;
import com.tzg.entitys.news.NewsMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class NewsService extends BaseService {

	@Autowired
	private NewsMapper newsMapper;

	@Transactional(readOnly = true)
	public News findById(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		return newsMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		newsMapper.deleteById(id);
	}

	public void save(News news) throws Exception {
		if (news.getItype() == null) {
			throw new Exception("类型不能为空！");
		}
		if (StringUtils.isBlank(news.getVcTitle())) {
			throw new Exception("标题不能为空！");
		}
		if (StringUtils.isBlank(news.getVcSubtitle())) {
			throw new Exception("副标题不能为空！");
		}
		if (StringUtils.isBlank(news.getVcSource())) {
			throw new Exception("新闻来源不能为空！");
		}
		if (StringUtils.isBlank(news.getVcOrgLink())) {
			throw new Exception("原文链接不能为空！");
		}
		if (StringUtils.isBlank(news.getVcImgUrl())) {
			throw new Exception("文档图片地址不能为空！");
		}
		if (StringUtils.isBlank(news.getVcSummary())) {
			throw new Exception("新闻概括不能为空！");
		}
		if (StringUtils.isBlank(news.getVcContent())) {
			throw new Exception("新闻内容不能为空！");
		}
		if (StringUtils.isBlank(news.getVcKey())) {
			throw new Exception("新闻关键字不能为空！");
		}
		if (news.getIhomedisplay() == null) {
			throw new Exception("是否首页显示不能为空！");
		}
		news.setDtCreate(new Date());
		news.setIstate(1);
		if(news.getIhomesort() != null){
			newsMapper.updateIHomeSort(news);
		}
		newsMapper.save(news);
		
	}

	public void update(News news) throws Exception {
		if (news.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (news.getItype() == null) {
			throw new Exception("类型不能为空！");
		}
		if (StringUtils.isBlank(news.getVcTitle())) {
			throw new Exception("标题不能为空！");
		}
		if (StringUtils.isBlank(news.getVcSubtitle())) {
			throw new Exception("副标题不能为空！");
		}
		if (StringUtils.isBlank(news.getVcSource())) {
			throw new Exception("新闻来源不能为空！");
		}
		if (StringUtils.isBlank(news.getVcOrgLink())) {
			throw new Exception("原文链接不能为空！");
		}
		if (StringUtils.isBlank(news.getVcImgUrl())) {
			throw new Exception("文档图片地址不能为空！");
		}
		if (StringUtils.isBlank(news.getVcSummary())) {
			throw new Exception("新闻概括不能为空！");
		}
		if (StringUtils.isBlank(news.getVcContent())) {
			throw new Exception("新闻内容不能为空！");
		}
		if (StringUtils.isBlank(news.getVcKey())) {
			throw new Exception("新闻关键字不能为空！");
		}
		if (news.getIhomedisplay() == null) {
			throw new Exception("是否首页显示不能为空！");
		}
		News info = newsMapper.findById(news.getId());
		if(news.getIhomesort() != null){
			if(info.getIhomesort() == null || news.getIhomesort() != info.getIhomesort()){
				newsMapper.updateIHomeSort(news);
			}
		}
		
		newsMapper.update(news);
		
	}

	@Transactional(readOnly = true)
	public PageResult<News> findPage(PaginationQuery query) throws Exception {
		PageResult<News> result = null;
		try {
			Integer count = newsMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",
						Integer.toString(query.getRowsPerPage()));
				List<News> list = newsMapper.findPage(query.getQueryData());
				result = new PageResult<News>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 新闻动态发布
	 * 
	 * @param id
	 * @throws Exception
	 */
	public synchronized void publish(Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		News news = newsMapper.findById(id);
		if (news == null) {
			throw new Exception("该记录已经不存在！");
		}
		if (news.getIstate() != 1) {
			throw new Exception("该状态下的记录不允许执行发布！");
		}
		news.setIstate(2);
		news.setDtPublish(new Date());
		newsMapper.update(news);
	}

	/**
	 * 撤销新闻动态发布
	 * 
	 * @param id
	 * @throws Exception
	 */
	public synchronized void undopublish(Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		News news = newsMapper.findById(id);
		if (news == null) {
			throw new Exception("该记录已经不存在！");
		}
		if (news.getIstate() != 2) {
			throw new Exception("该状态下的记录不允许执行撤销发布！");
		}
		news.setIstate(1);
		news.setDtPublish(new Date());
		newsMapper.update(news);
	}
	


}
