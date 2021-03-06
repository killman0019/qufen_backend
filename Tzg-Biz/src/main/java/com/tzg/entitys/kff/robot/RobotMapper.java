package com.tzg.entitys.kff.robot;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface RobotMapper extends BaseMapper<Robot, java.lang.Integer> {

	Robot findByUserId(Integer commentUserId);

	List<Robot> findByMap(Map<String, Object> map);

}