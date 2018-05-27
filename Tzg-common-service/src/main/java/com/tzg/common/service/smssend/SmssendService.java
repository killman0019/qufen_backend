package com.tzg.common.service.smssend;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import jxl.common.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.common.service.smsrecord.SmsrecordService;
import com.tzg.common.service.smstemplate.SmstemplateService;
import com.tzg.common.utils.Assert;
import com.tzg.common.utils.EnumConstant.SmsBuss;
import com.tzg.common.utils.rest.RestConstants;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.entitys.smssend.Smssend;
import com.tzg.entitys.smssend.SmssendMapper;
import com.tzg.entitys.smstemplate.Smstemplate;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
public class SmssendService  {
	private Logger logger = Logger.getLogger(SmssendService.class);
	
	private final static SimpleDateFormat        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

	@Autowired
	private SmssendMapper smssendMapper;
	@Autowired
    private SmstemplateService smstemplateService;
	@Autowired
	private SmsrecordService smsrecordService;
	@Autowired
	private RedisService redisService;
	
	public void save(Smssend smssend) throws Exception{
		smssendMapper.save(smssend);
	}
	   
    public Smssend findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return smssendMapper.findById(id);
    }
	
    /**
     * 获取短信内容
     * @param vcParamCode
     * @param objects
     * @return
     * @throws Exception
     */
	public String getSmsContent(Integer smstemplateType, Object... objects)
			throws Exception {
		if(smstemplateType == null){
			return null;
		}
		Smstemplate smstemplate = smstemplateService.findByType(smstemplateType);
		if(null == smstemplate){
			return null;
		}
		String template = smstemplate.getVcTemplate();
		return String.format(template, objects);
	}
    
	
	/**
	 * 保存短信记录
	 * @param vcPhone
	 * @param vcContent
	 * @param itype 1 -- 通知； 2 -- 验证码。
	 * @throws Exception
	 */
	public void save(String vcPhone,String vcContent,Integer itype) throws Exception {	
		if(StringUtils.isBlank(vcPhone)){
			throw new RestServiceException("手机号码不能为空");
		}
		if(StringUtils.isBlank(vcContent)){
			throw new RestServiceException("短信内容不能为空");
		}
		if(itype == null){
			throw new RestServiceException("短信类型不能为空");
		}
		Smssend smssend = new Smssend();
		smssend.setVcPhone(vcPhone);
		smssend.setVcContent(vcContent);
		smssend.setItype(itype);
		if(itype.intValue()==2){ 
			smssend.setIpriority(1);
		}else{
			smssend.setIpriority(2);
		}
		smssend.setDtCreate(new Date());
		smssend.setVcMemo("{"+"\"templateId\":"+""+",\"paramValue\":"+""+"}");
		smssendMapper.save(smssend);
	}
	
	/**
	 * 保存短信记录
	 * @param vcPhone
	 * @param vcContent
	 * @param itype 1 -- 通知； 2 -- 验证码。
	 * @throws Exception
	 */
	public void save(String vcPhone,String vcContent,Integer itype,Integer templateId,String paramValue) throws Exception {	
		if(StringUtils.isBlank(vcPhone)){
			throw new RestServiceException("手机号码不能为空");
		}
		if(StringUtils.isBlank(vcContent)){
			throw new RestServiceException("短信内容不能为空");
		}
		if(itype == null){
			throw new RestServiceException("短信类型不能为空");
		}
		Smssend smssend = new Smssend();
		smssend.setVcPhone(vcPhone);
		smssend.setVcContent(vcContent);
		smssend.setItype(itype);
		if(itype.intValue()==2){ 
			smssend.setIpriority(1);
		}else{
			smssend.setIpriority(2);
		}
		smssend.setDtCreate(new Date());
		smssend.setVcMemo("{"+"\"templateId\":"+templateId+",\"paramValue\":"+paramValue+"}");
		smssendMapper.save(smssend);
	}
	
	/**
	 * 发送验证码类短信
	 * @param phoneNumber
	 * @param dynamicValidateCode
	 * @param bus
	 * @throws Exception
	 */
	public void sendMSG(String phoneNumber, String dynamicValidateCode, String bus) throws Exception{
		String vcContent = dynamicValidateCode;
		Integer smstemplateType = SmsBuss.getSmsBuss(bus).getSmstemplateType();
		String smsContent = this.getSmsContent(smstemplateType, dynamicValidateCode);
		if(smsContent != null){
			vcContent = smsContent;
		}
		this.save(phoneNumber,vcContent,2,smstemplateType,dynamicValidateCode);
	}
	
	/**
	 * 发送短信(指定用户ID)
	 * @param loginAccountId
	 * @param smstemplateType
	 * @param objects
	 * @throws Exception
	 */
	public void sendMSGByLoginAccountId(Integer loginAccountId, Integer smstemplateType, Object...objects) throws Exception{
		String vcContent = "";
		String smsContent = this.getSmsContent(smstemplateType, objects);
		if(smsContent != null){
			vcContent = smsContent;
		}
		Loginaccount loginaccount = null;//loginaccountService.findById(loginAccountId);
		this.save(loginaccount.getVcPhone(),vcContent,1,smstemplateType,objects.toString());
	}
	
	/**
	 * 发送短信(指定模版内号码)
	 * @param smstemplateType
	 * @param objects
	 * @throws Exception
	 */
	public void sendMSGByLoginAccountId(Integer smstemplateType, Object...objects) throws Exception{
		Assert.isNull(smstemplateType, "模版类型不能为空");
		Smstemplate smstemplate = smstemplateService.findByType(smstemplateType);
		Assert.isNull(smstemplate, "未找到相关模版");
		String template = smstemplate.getVcTemplate();
		String vcContent = String.format(template, objects);
		String[] phoneNumberArr = smstemplate.getVcPhones().split(";");
		for(String phoneNumber : phoneNumberArr){
			this.save(phoneNumber,vcContent,1,Integer.parseInt(template),String.valueOf(objects));
		}
	}
	
	/**
	 * 根据类型查询待发送的短信
	 * @param iType
	 * @return
	 */
	public List<Smssend> findSmsForSendByType(Integer iType) {
		return smssendMapper.getAllByType(iType);
	}
	
	public List<Smssend> findPage(PaginationQuery query) {
		return smssendMapper.findPage(query.getQueryData());
	}
	
	public int verifyForRest(String module,String phone,String code) throws Exception{
		try {
			String cacheCode = this.redisService.get(new StringBuffer(RestConstants.key_rest)
        	.append(module).append(phone).toString());
			if (!StringUtils.isEmpty(cacheCode)) {
				if(!cacheCode.equalsIgnoreCase(code)){
					 return 1;
				}
		    }else{
		    	return 2;
		    }
			this.redisService.del(new StringBuffer(RestConstants.key_rest).append(module).append(phone).toString());
		} catch (TimeoutException e) {
			logger.error(e);
		} catch (CacheException e) {
			logger.error(e);
		}

		return 0;
	}
	
	/**
	 * 发送催缴和通知风控
	 * 
	 * @param repayrecordList
	 * @param smstemplate
	 * @param ifDate
	 * @param controlDepartment
	 * @throws Exception
	 */
	@Transactional
    public void sendSms(List<Map<String, Object>> repayrecordList, Smstemplate smstemplate, boolean ifDate, Integer controlDepartment) throws Exception {
        if(repayrecordList  == null || repayrecordList.size()==0){
        	return ;
        }
		Smstemplate smstemplate_controlDepartment = null;
        if (controlDepartment != null) {
            smstemplate_controlDepartment = smstemplateService.findByType(controlDepartment);
        }
        Map<String, Object> map = repayrecordList.get(0);
        String phone = (String) map.get("vcPhone");
        String name = (String) map.get("vcName");
        BigDecimal totalAmt = (BigDecimal) map.get("numTotalRepayAmt");
        String dtRepay = simpleDateFormat.format((Date) map.get("dtRepay"));
        String subjectName = (String) map.get("vcSubjectName");
        String vcCurrentPeriod = "";
        Integer period = (Integer) map.get("numCurrentPeriod");
        if (period == 0) vcCurrentPeriod = "募集期";
        else vcCurrentPeriod = "第" + period + "期";
        String smsMessage = null;
        if (repayrecordList.size() == 1) {
            if (ifDate) smsMessage = String.format(smstemplate.getVcTemplate(), subjectName, vcCurrentPeriod, totalAmt, dtRepay);
            else smsMessage = String.format(smstemplate.getVcTemplate(), subjectName, vcCurrentPeriod, totalAmt);
            this.save(phone, smsMessage, 1);
            if (controlDepartment != null) {
                String[] controlPhones = smstemplate_controlDepartment.getVcPhones().split(";");
                if (controlDepartment == 18) smsMessage = String.format(smstemplate_controlDepartment.getVcTemplate(), name, subjectName, vcCurrentPeriod, totalAmt, dtRepay);
                if (controlDepartment == 19) smsMessage = String.format(smstemplate_controlDepartment.getVcTemplate(), name, subjectName, vcCurrentPeriod, totalAmt);
                for (String s : controlPhones)
                    this.save(s, smsMessage, 1);
            }
            return;
        }
        for (int i = 1; i < repayrecordList.size(); i++) {
            try {
            	map = repayrecordList.get(i);
            	if (!subjectName.equalsIgnoreCase((String) map.get("vcSubjectName"))) {
                    // 不同标的，发送
                    if (ifDate) smsMessage = String.format(smstemplate.getVcTemplate(), subjectName, vcCurrentPeriod, totalAmt, dtRepay);
                    else smsMessage = String.format(smstemplate.getVcTemplate(), subjectName, vcCurrentPeriod, totalAmt);
                    this.save(phone, smsMessage, 1);
                    if (controlDepartment != null) {
                        String[] controlPhones = smstemplate_controlDepartment.getVcPhones().split(";");
                        if (controlDepartment == 18) smsMessage = String.format(smstemplate_controlDepartment.getVcTemplate(), name, subjectName, vcCurrentPeriod, totalAmt,
                                                                                dtRepay);
                        if (controlDepartment == 19) smsMessage = String.format(smstemplate_controlDepartment.getVcTemplate(), name, subjectName, vcCurrentPeriod, totalAmt);
                        for (String s : controlPhones)
                            this.save(s, smsMessage, 1);
                    }
                    phone = (String) map.get("vcPhone");
                    totalAmt = (BigDecimal) map.get("numTotalRepayAmt");
                    subjectName = (String) map.get("vcSubjectName");
                    name = (String) map.get("vcName");
                    vcCurrentPeriod = "";
                    period = (Integer) map.get("numCurrentPeriod");
                    if (period == 0) vcCurrentPeriod = "募集期";
                    else vcCurrentPeriod = "第" + period + "期";
                } else {
                    totalAmt = totalAmt.add((BigDecimal) map.get("numTotalRepayAmt"));
                    period = (Integer) map.get("numCurrentPeriod");
                    if (period == 0) vcCurrentPeriod = vcCurrentPeriod + ",募集期";
                    else vcCurrentPeriod = vcCurrentPeriod + ",第" + period + "期";
                    subjectName = (String) map.get("vcSubjectName");
                    phone = (String) map.get("vcPhone");
                    name = (String) map.get("vcName");
                }

                if (i == repayrecordList.size() - 1) {
                    // 最后一条，直接发送
                    if (ifDate) smsMessage = String.format(smstemplate.getVcTemplate(), subjectName, vcCurrentPeriod, totalAmt, dtRepay);
                    else smsMessage = String.format(smstemplate.getVcTemplate(), subjectName, vcCurrentPeriod, totalAmt);
                    this.save(phone, smsMessage, 1);
                    if (controlDepartment != null) {
                        String[] controlPhones = smstemplate_controlDepartment.getVcPhones().split(";");
                        if (controlDepartment == 18) smsMessage = String.format(smstemplate_controlDepartment.getVcTemplate(), name, subjectName, vcCurrentPeriod, totalAmt,
                                                                                dtRepay);
                        if (controlDepartment == 19) smsMessage = String.format(smstemplate_controlDepartment.getVcTemplate(), name, subjectName, vcCurrentPeriod, totalAmt);
                        for (String s : controlPhones) {
                            this.save(s, smsMessage, 1);
                        }
                    }
                }
            } catch (Exception ex) {
                throw ex;
            }
        }

    }
	/**
	 * Description: 根据短信发送名称和时间范围查询发送记录
	 * @author:     linjie 
	 * Create at:   2015年12月30日 上午10:44:11  
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Integer getSendSmsByContent(Map<String,String> param) throws Exception{
	    Integer res= smssendMapper.getSendSmsByContent(param);
	    return res;
	}
}
