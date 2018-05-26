package com.tzg.service.registemessage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.core.utils.DateUtil;
import com.tzg.entitys.registemessage.Registemessage;
import com.tzg.entitys.registemessage.RegistemessageMapper;
import com.tzg.entitys.subject.Subject;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class RegistemessageService extends BaseService {

	@Autowired
	private RegistemessageMapper registemessageMapper;	
	   
	@Transactional(readOnly=true)
    public Registemessage findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return registemessageMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        registemessageMapper.deleteById(id);
    }
	
	public void save(Registemessage registemessage) throws Exception {
		registemessage.setDtCreate(new Date());
		registemessage.setIstate(1);
		registemessageMapper.save(registemessage);
	}
	
	public void update(Registemessage registemessage) throws Exception {	
		if(registemessage.getId() == null){
			throw new Exception("id不能为空");
		}
		registemessageMapper.update(registemessage);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Registemessage> findPage(PaginationQuery query) throws Exception {
		PageResult<Registemessage> result = null;
		try {
			Integer count = registemessageMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Registemessage> list = registemessageMapper.findPage(query.getQueryData());
				result = new PageResult<Registemessage>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void saveOrUpdateVerify(Registemessage registemessage) throws Exception {
		
		if (StringUtils.isBlank(registemessage.getVcContent())) {
			throw new Exception("注册提示语内容不能为空！");
		}
		if (StringUtils.isBlank(registemessage.getDtStartTimeStr())) {
			throw new Exception("有效期起始时间不能为空！");
		}
		if (StringUtils.isBlank(registemessage.getDtEndTimeStr())) {
			throw new Exception("有效期截止时间不能为空！");
		}
		if (DateUtil.compareDateTime(registemessage.getDtStartTimeStr(),
				registemessage.getDtEndTimeStr()) >= 0) {
			throw new Exception("有效期起始时间[" + registemessage.getDtStartTimeStr()
					+ "]应小于有效期截止时间[" + registemessage.getDtEndTimeStr() + "]！");
		}
		List<Registemessage> list = registemessageMapper.getRegisteList();
		if(list!=null){
			boolean legal=true;
			for (int i = 0; i < list.size(); i++) {
				String dtStart = list.get(i).getDtStartTimeStr();
				String dtEnd = list.get(i).getDtEndTimeStr();
				if (list.get(i).getIstate() == 1) {
					if (DateUtil.compareDateTime(dtEnd,registemessage.getDtStartTimeStr()) >= 0&&DateUtil.compareDateTime(registemessage.getDtStartTimeStr(),dtStart) >= 0) {
						legal = false;
					}
					if (DateUtil.compareDateTime(registemessage.getDtEndTimeStr(),dtStart) >= 0&& DateUtil.compareDateTime(dtEnd,registemessage.getDtEndTimeStr()) >= 0) {
						legal = false;
					}
					if (DateUtil.compareDateTime(registemessage.getDtStartTimeStr(),dtStart) >= 0&& DateUtil.compareDateTime(dtEnd,registemessage.getDtEndTimeStr()) >= 0) {
						legal = false;
					}
					if (DateUtil.compareDateTime(dtStart,registemessage.getDtStartTimeStr()) >= 0&& DateUtil.compareDateTime(registemessage.getDtEndTimeStr(),dtEnd) >= 0) {
						legal = false;
					}
					if(legal==false){
						break;
					}
				}}
			if(legal==false){
				throw new Exception("有效期限与已存在的记录有重合时间段");
			}
		}
	}
}
