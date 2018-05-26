package com.tzg.entitys.auditinformation;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.tzg.entitys.BaseMapper;

/**
 * 
 * @Description：项目或标的审核信息Mapper
 * @author wxg
 * @Date 2014-12-15
 *
 */
@Component
public interface AuditinformationMapper extends BaseMapper<Auditinformation, Serializable>{
	
	/**
	 * 根据iSourceD，查询当前审核等级
	 * @param iSourceD
	 * @return
	 * @throws Exception
	 */
	public Auditinformation queryRecentAuditLevel(Integer iSourceD) throws Exception;
	
	
	

}
