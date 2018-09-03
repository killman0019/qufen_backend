package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.qfindex.QfIndex;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFQfIndexService")
@Transactional
public class QfIndexService {

	@Autowired
	private com.tzg.entitys.kff.qfindex.QfIndexMapper QfIndexMapper;

	@Transactional(readOnly = true)
	public QfIndex findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return QfIndexMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		QfIndexMapper.deleteById(id);
	}

	public void save(QfIndex qfIndex) throws RestServiceException {
		QfIndexMapper.save(qfIndex);
	}

	public void update(QfIndex qfIndex) throws RestServiceException {
		if (qfIndex.getQfIndexId() == null) {
			throw new RestServiceException("id不能为空");
		}
		QfIndexMapper.update(qfIndex);
	}

	@Transactional(readOnly = true)
	public PageResult<QfIndex> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<QfIndex> result = null;
		try {
			Integer count = QfIndexMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<QfIndex> list = QfIndexMapper.findPage(query.getQueryData());
				result = new PageResult<QfIndex>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public QfIndex findByUserId(Integer userid) throws RestServiceException {
		if (userid == null) {
			throw new RestServiceException("id不能为空");
		}
		return QfIndexMapper.findByUserId(userid);
	}

	public void updateYxPraise(Integer userid) throws RestServiceException {
		if (userid == null) {
			throw new RestServiceException("用户id不能为空");
		}

		QfIndexMapper.updateYxPraise(userid);
	}

	public void updateYxPraiseValid() {
		QfIndexMapper.updateYxAll();
	}

	public List<QfIndex> findByUserIds(Map<String, Object> qfUsersMap) {

		return QfIndexMapper.findByUserIds(qfUsersMap);
	}

	public void updateYxSharePost(Integer shareUserId) {
		// TODO 分享奖励发放成功, 有效分享次数减少
		QfIndexMapper.updateyxSharePost(shareUserId);
	}

	public void updateYXcomment(Integer userId) {
		// TODO 分享成功,有效评论次数减少
		QfIndexMapper.updateYXcomment(userId);
	}

	public void updateSetYxPraise(Integer userId) {
		// TODO 仅仅更新有效赞
		QfIndexMapper.updateSetYxPraise(userId);
	}

	public void updateSetYxComment(Integer userId) {
		// TODO 仅仅更新有效评论
		QfIndexMapper.updateSetYxComment(userId);

	}

	public void updateSetSharePost(Integer userId) {
		// TODO 仅仅更新有效评论
		QfIndexMapper.updateSetSharePost(userId);

	}

}
