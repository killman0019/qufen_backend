package com.tzg.common.service.systemParam;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.redis.RedisService;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.leopard.system.SystemParamMapper;

/**
 * Created by cfour on 12/3/14.
 */
@Service
public class SystemParamService {

	@Autowired
	private RedisService redisService;
	@Autowired
	private SystemParamMapper systemParamMapper;

	public SystemParam findById(int id) throws Exception {
		SystemParam systemParam = null;
		Object object = this.redisService.getObj("paramId" + String.valueOf(id));
		if (object instanceof SystemParam) {
			systemParam = (SystemParam) object;
		} else {
			systemParam = systemParamMapper.findById(id);
			this.redisService.putObj("paramId" + String.valueOf(id), systemParam, 60 * 60);
		}
		return systemParam;
	}

	public SystemParam findByCode(String code) {
		SystemParam systemParam = null;
		try {
			Object object = this.redisService.getObj("paramCode" + code);
			if (object instanceof SystemParam) {
				systemParam = (SystemParam) object;
			} else {
				systemParam = systemParamMapper.findByCode(code);
				this.redisService.putObj("paramCode" + code, systemParam, 60 * 60);
			}
		} catch (Exception e) {
			systemParam = systemParamMapper.findByCode(code);
		}
		return systemParam;
	}

	/**
	 * 图片资源地址
	 * 
	 * @return
	 */
	private static String staticUploadUrl = null;

	public String getstaticUploadUrl() {
		if (StringUtils.isBlank(staticUploadUrl)) {
			SystemParam systemParam = systemParamMapper.findByCode("sys_static_file_server_url");
			if (systemParam != null) {
				staticUploadUrl = systemParam.getVcParamValue();
			}
		}
		return staticUploadUrl;
	}

	/**
	 * wap 访问地址
	 * 
	 * @return
	 */
	private static String wapPath = null;

	public String getWapPath() {
		if (StringUtils.isBlank(wapPath)) {
			SystemParam systemParam = systemParamMapper.findByCode("wap_address");
			if (systemParam != null) {
				wapPath = systemParam.getVcParamValue();
			}
		}
		return wapPath;
	}

	/**
	 * web 访问地址
	 * 
	 * @return
	 */
	private static String webPath = null;

	public String getWebPath() {
		if (StringUtils.isBlank(webPath)) {
			SystemParam systemParam = systemParamMapper.findByCode("web_address");
			if (systemParam != null) {
				webPath = systemParam.getVcParamValue();
			}
		}
		return webPath;
	}

	/**
	 * 银行卡支付服务协议地址
	 * 
	 * @return
	 */
	private static String bankCardPaymentProtocol = null;

	public String getBankCardPaymentProtocol() {
		if (StringUtils.isBlank(bankCardPaymentProtocol)) {
			SystemParam systemParam = systemParamMapper.findByCode("bank_card_payment_protocol");
			if (systemParam != null) {
				bankCardPaymentProtocol = systemParam.getVcParamValue();
			}
		}
		return bankCardPaymentProtocol;
	}

	public List<SystemParam> findByMap(Map<String, Object> sysMap) {
		// TODO map 查询
		return null;
	}

}
