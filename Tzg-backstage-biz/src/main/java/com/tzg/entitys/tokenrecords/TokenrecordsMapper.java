package com.tzg.entitys.tokenrecords;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface TokenrecordsMapper extends BaseMapper<Tokenrecords, java.lang.Integer> {

	Float findUserSumRewardToken();	

}
