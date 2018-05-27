package com.tzg.entitys.kff.message;

import com.tzg.common.base.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface KFFMessageMapper extends BaseMapper<KFFMessage, java.lang.Integer> {

	void deleteAllMessages(Integer userId);	

}
