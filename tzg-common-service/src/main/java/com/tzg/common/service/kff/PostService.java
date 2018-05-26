package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostMapper;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFPostService")
@Transactional
public class PostService {

	@Autowired
	private PostMapper postMapper;

	@Transactional(readOnly = true)
	public Post findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return postMapper.findById(id);
	}

	public Post findByUUID(String uuid) {
		if (uuid == null) {
			throw new RestServiceException("uuid不能为空");
		}
		return postMapper.findByUUID(uuid);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		postMapper.deleteById(id);
	}

	public void save(Post post) throws RestServiceException {
		postMapper.save(post);
	}

	public void update(Post post) throws RestServiceException {
		if (post.getPostId() == null) {
			throw new RestServiceException("id不能为空");
		}
		postMapper.update(post);
	}

	@Transactional(readOnly = true)
	public PageResult<Post> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Post> result = null;
		try {
			Integer count = postMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				query.addQueryData("status", "1");

				List<Post> list = postMapper.findPage(query.getQueryData());
				result = new PageResult<Post>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Post> findProjectActiveUsers(Map<String, Object> map) {

		return postMapper.findProjectActiveUsers(map);
	}

	public void updateDonateNum(Integer postId) {
		postMapper.updateDonateNum(postId);

	}

	public void increasePraiseNum(Integer postId) {
		postMapper.increasePraiseNum(postId);

	}

	public void decreasePraiseNum(Integer postId) {
		postMapper.decreasePraiseNum(postId);
	}

	public void increaseCollectNum(Integer postId) {

		postMapper.increaseCollectNum(postId);
	}

	public void decreaseCollectNum(Integer postId) {
		postMapper.decreaseCollectNum(postId);

	}


}
