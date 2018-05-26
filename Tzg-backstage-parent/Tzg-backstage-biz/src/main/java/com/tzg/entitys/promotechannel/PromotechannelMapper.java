package com.tzg.entitys.promotechannel;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface PromotechannelMapper extends BaseMapper<Promotechannel, java.lang.Integer> {

	public Promotechannel findPromoteByType(Promotechannel promotechannel) throws Exception;
}
