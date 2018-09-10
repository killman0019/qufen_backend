package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.qfindex.QfIndex;
import com.tzg.entitys.kff.qfindex.QfindexResponse;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFQfIndexService")
@Transactional(rollbackFor = Exception.class)
public class QfIndexService {

	@Autowired
	private com.tzg.entitys.kff.qfindex.QfIndexMapper QfIndexMapper;
	@Autowired
	private SystemParamService systemParamService;

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

	public void updateSetAll(Integer userId) {
		QfIndexMapper.updateSetAll(userId);
	}

	/**
	 * 
	* @Title: getMember 
	* @Description: TODO <获得用户的会员中心>
	* @author zhangdd <方法创建作者>
	* @create 下午5:30:00
	* @param @param userId
	* @param @return <参数说明>
	* @return QfindexResponse 
	* @throws 
	* @update 下午5:30:00
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public QfindexResponse getMember(Integer userId) {

		QfindexResponse qfindexResponse = new QfindexResponse();
		if (userId != null) {

		}

		QfIndex qfindex = QfIndexMapper.findByUserId(userId);
		if (qfindex != null) {
			qfindexResponse.setStatusHierarchyType(qfindex.getStatusHierarchyType());// 区分指数
			SystemParam sys = systemParamService.findByCode(sysGlobals.INVA_EACH_AWARD);
			SystemParam sharePostSys = systemParamService.findByCode(sysGlobals.SHARE_POST_AWARD_TOKEN);
			SystemParam praiseAwardSys = systemParamService.findByCode(sysGlobals.YX_PRAISE_TOKEN_TO_PRAYSER);
			SystemParam commentAwardSys = systemParamService.findByCode(sysGlobals.COMMENT_AWARD_TOKEN);
			qfindexResponse.setInvaEachAward(new BigDecimal(sys.getVcParamValue()));// 每邀请一个用户获得奖励
			qfindexResponse.setSharePostAward(Double.valueOf(sharePostSys.getVcParamValue()));// 每次分享获得奖励

			qfindexResponse.setPraiseAward(Double.valueOf(praiseAwardSys.getVcParamValue()));// 进行点赞给点赞人的奖励
			qfindexResponse.setCommentAward(Double.valueOf(commentAwardSys.getVcParamValue()));// 给予评论人的奖励

		}

		return qfindexResponse;
	}
}
