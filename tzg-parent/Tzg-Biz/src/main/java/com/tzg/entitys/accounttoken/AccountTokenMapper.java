package com.tzg.entitys.accounttoken;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface AccountTokenMapper extends BaseMapper<AccountToken, Serializable> {

	/**
	 * 根据终端唯一标识获取信息
	 * 
	 * @param secId
	 *            终端唯一标识
	 * @return
	 */
	public List<AccountToken> findBySecId(Map<String, Object> map);

}
