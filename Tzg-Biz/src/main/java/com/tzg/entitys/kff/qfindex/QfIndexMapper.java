package com.tzg.entitys.kff.qfindex;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface QfIndexMapper extends BaseMapper<QfIndex, java.lang.Integer> {

	QfIndex findByUserId(Integer userid);

	void updateYxPraise(Integer userId);

	void updateYxPraiseValid();

	List<QfIndex> findByUserIds(Map<String, Object> qfUsersMap);

	void updateyxSharePost(Integer userId);

	void updateYxAll();

	void updateYXcomment(Integer userId);

	void updateSetYxPraise(Integer userId);

	void updateSetYxComment(Integer userId);

	void updateSetSharePost(Integer userId);

	void updateSetAll(Integer userId);

	void updatePushEvaCount(Integer userId);

	void updateReadingDegr(Integer userId);

}
