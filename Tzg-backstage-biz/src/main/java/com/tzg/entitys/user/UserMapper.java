package com.tzg.entitys.user;
import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface UserMapper extends BaseMapper<User, java.lang.Integer> {

	User findByMobile(String mobile);	

}
