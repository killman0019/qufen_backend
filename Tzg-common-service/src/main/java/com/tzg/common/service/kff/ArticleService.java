package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.article.Article;
import com.tzg.entitys.kff.article.ArticleMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFArticleService")
@Transactional
public class ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Transactional(readOnly = true)
	public Article findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return articleMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		articleMapper.deleteById(id);
	}

	public void save(Article article) throws RestServiceException {
		articleMapper.save(article);
	}

	public void update(Article article) throws RestServiceException {
		if (article.getArticleId() == null) {
			throw new RestServiceException("id不能为空");
		}
		articleMapper.update(article);
	}

	@Transactional(readOnly = true)
	public PageResult<Article> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Article> result = null;
		try {
			Integer count = articleMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Article> list = articleMapper.findPage(query.getQueryData());
				result = new PageResult<Article>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Article findByPostId(Integer postId) throws RestServiceException {
		return articleMapper.findByPostId(postId);
	}

	public Article selectArticleByPostId(Integer postId) {

		return articleMapper.findByPostId(postId);
	}

}
