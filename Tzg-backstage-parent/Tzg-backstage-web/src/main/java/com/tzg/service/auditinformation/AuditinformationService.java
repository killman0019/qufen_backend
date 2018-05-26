package com.tzg.service.auditinformation;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.core.utils.HttpSessionUtil;
import com.tzg.entitys.auditinformation.Auditinformation;
import com.tzg.entitys.auditinformation.AuditinformationMapper;
import com.tzg.entitys.leopard.console.ConsoleLoginAccount;
import com.tzg.service.consoleLoginAccount.ConsoleLoginAccountService;

/**
 * 
 * @Description：项目或标的审核信息Service
 * @author wxg
 * @Date 2014-12-15
 * 
 */
@Service
@Transactional
public class AuditinformationService {

	@Autowired
	private AuditinformationMapper auditinformationMapper;
	
	@Autowired
	private ConsoleLoginAccountService consoleLoginAccountService;

	/**
	 * 记录新增
	 * @param iSourceD
	 * 						审核记录主键
	 * @param vcApproveIdea
	 * 					审核等级
	 * @param iState
	 * 				审核状态
	 * @param iType
	 * 				审核类别
	 * @throws Exception
	 */
	public void save(String iSourceD,String vcApproveLevel,String vcApproveIdea,String iState,Integer iType) throws Exception {
		Auditinformation audit = new Auditinformation();
		if (StringUtils.isNotBlank(iSourceD)) {
			audit.setiSourceD(Integer.parseInt(iSourceD));
		}
		ConsoleLoginAccount account = HttpSessionUtil.getLoginSession(); //获取登录用户信息
		audit.setVcApproveLevel(vcApproveLevel);
		audit.setVcApproverName(account.getVcLoginName());
		audit.setDtApprove(new Date());
		if (StringUtils.isNotBlank(iState)) {
			audit.setiState(Integer.parseInt(iState));
		}
		audit.setVcApproveIdea(vcApproveIdea);
		audit.setDtCreate(new Date());
		audit.setiType(iType);
		auditinformationMapper.save(audit);
	}

	/**
	 * 根据iSourceD，查询最近一次的审核等级
	 * 
	 * @param iSourceD
	 * @return
	 * @throws Exception
	 */
	public Auditinformation queryRecentAuditLevel(Integer iSourceD)
			throws Exception {
		Auditinformation audit = null;
		audit = auditinformationMapper.queryRecentAuditLevel(iSourceD);
		return audit;
	}
	
	@Transactional(readOnly=true)
    public Auditinformation findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return auditinformationMapper.findById(id);
    }
	
	@Transactional(readOnly=true)
	public PageResult<Auditinformation> findPage(PaginationQuery query) throws Exception {
		PageResult<Auditinformation> result = null;
		try {
			Integer count = auditinformationMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Auditinformation> list = auditinformationMapper.findPage(query.getQueryData());
				result = new PageResult<Auditinformation>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
