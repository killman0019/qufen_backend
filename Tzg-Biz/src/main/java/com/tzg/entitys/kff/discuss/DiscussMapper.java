package com.tzg.entitys.kff.discuss;

import com.tzg.common.base.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussMapper extends BaseMapper<Discuss, java.lang.Integer> {

	Discuss findByPostId(Integer postId);	

}
