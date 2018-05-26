package com.tzg.service.smssend;

import java.util.Date;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.EnumConstant.SmsBuss;
import com.tzg.entitys.smssend.Smssend;
import com.tzg.entitys.smssend.SmssendMapper;
import com.tzg.entitys.smstemplate.Smstemplate;
import com.tzg.entitys.smstemplate.SmstemplateMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class SmssendService extends BaseService {

	@Autowired
	private SmssendMapper smssendMapper;
	@Autowired
    private SmstemplateMapper smstemplateMapper;
	   
	@Transactional(readOnly=true)
    public Smssend findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return smssendMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        smssendMapper.deleteById(id);
    }
	
    /**
     * 获取短信内容
     * @param vcParamCode
     * @param objects
     * @return
     * @throws Exception
     */
	public String getSmsContent(Integer type, Object... objects)
			throws Exception {
		if(type == null){
			return null;
		}
		Smstemplate smstemplate = smstemplateMapper.findByType(type);
		if(null == smstemplate){
			return null;
		}
		String template = smstemplate.getVcTemplate();
		return String.format(template, objects);
	}
    
	public void sendMSGForAuidt(String bus,Object... objects) throws Exception{
		Integer smstemplateType = SmsBuss.getSmsBuss(bus).getSmstemplateType();
		Smstemplate smstemplate = smstemplateMapper.findByType(smstemplateType);
		if(null == smstemplate||smstemplate.getIstate().equals(2)){
			return ;
		}
		String template = smstemplate.getVcTemplate();
		if(StringUtils.isNotBlank(template)){
			String smsContent =  String.format(template, objects);
			String phones = smstemplate.getVcPhones();
			if(StringUtils.isNotBlank(phones)){
				String[] phonea = phones.split(";");
				for(String phone:phonea){
					this.save(phone,smsContent,2);
				}
			}
		}
	}
	
	public void sendMSG(String phoneNumber, String dynamicValidateCode, String bus) throws Exception{
		String vcContent = dynamicValidateCode;
		Integer smstemplateType = SmsBuss.getSmsBuss(bus).getSmstemplateType();
		String smsContent = this.getSmsContent(smstemplateType, dynamicValidateCode);
		if(smsContent != null){
			vcContent = smsContent;
		}
		this.save(phoneNumber,vcContent,2);
	}
	
	/**
	 * 保存短信记录
	 * @param vcPhone
	 * @param vcContent
	 * @param itype
	 * @throws Exception
	 */
	public void save(String vcPhone,String vcContent,Integer itype) throws Exception {	
		if(StringUtils.isBlank(vcPhone)){
			throw new Exception("手机号码不能为空");
		}
		if(StringUtils.isBlank(vcContent)){
			throw new Exception("短信内容不能为空");
		}
		if(itype == null){
			throw new Exception("短信类型不能为空");
		}
		Smssend smssend = new Smssend();
		smssend.setVcPhone(vcPhone);
		smssend.setVcContent(vcContent);
		smssend.setItype(itype);
		if(itype.intValue()==2){ //目前业务，仅验证码优先发送
			smssend.setIpriority(1);
		}else{
			smssend.setIpriority(2);
		}
		smssend.setDtCreate(new Date());
		smssendMapper.save(smssend);
	}
	
	
	public void update(Smssend smssend) throws Exception {	
		if(smssend.getId() == null){
			throw new Exception("id不能为空");
		}
		smssendMapper.update(smssend);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Smssend> findPage(PaginationQuery query) throws Exception {
		PageResult<Smssend> result = null;
		try {
			Integer count = smssendMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Smssend> list = smssendMapper.findPage(query.getQueryData());
				result = new PageResult<Smssend>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
