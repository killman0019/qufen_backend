package com.tzg.wap.controller.h5;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.entitys.kff.mobileversionupdate.Mobileversionupdate;
import com.tzg.entitys.kff.mobileversionupdate.VersionUpgradeResponse;
import com.tzg.entitys.kff.notice.KFFNotice;
import com.tzg.entitys.kff.suggest.SuggestRequest;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.rest.constant.RestRequestHead;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.utils.HttpRequestDeviceUtils;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.SystemParamRmiService;

@Controller(value = "KFFSystemController")
@RequestMapping("/kff/system")
public class SystemController extends BaseController {
	private static Logger log = Logger.getLogger(SystemController.class);
	@Autowired
	private KFFRmiService kffRmiService;

	// @Autowired
	// private SystemParamRmiService systemParamRmiService;
	// @Autowired
	// private RedisService redisService;

	/**
	 * 
	 * @Title: upgrade
	 * @Description: 获取升级提示语和升级链接
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/upgrade", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity upgrade(HttpServletRequest request, RegisterRequest registerRequest) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			RestRequestHead requestHead = HttpRequestDeviceUtils.getUserAgent(request);
			Integer platformType = requestHead.getPlatform();
			String currentVer = requestHead.getAppVersion();
			Mobileversionupdate dto = kffRmiService.selectLastVersionByType(platformType);
			VersionUpgradeResponse response = new VersionUpgradeResponse();

			if (dto != null) {
				String newVer = dto.getBaseVersion() + "." + dto.getAlphaVersion() + "." + dto.getBetaVersion();
				String forceVer = dto.getFbaseVersion() + "." + dto.getFalphaVersion() + "." + dto.getFbetaVersion();
				String upgradeUrl = dto.getUpgradeUrl();
				String guideUrl = dto.getGuideUrl();
				String upExplain = dto.getUpexplain().replaceAll("-", "\r\n");
				// 是否需要更新
				if (currentVer.compareToIgnoreCase(newVer) >= 0) {
					response.setUpgrade(0);
				} else {
					response.setUpgrade(1);
					response.setVer(newVer);
					response.setUpExplain(upExplain);
					// 判断需要强制更新还是普通更新
					if (currentVer.compareToIgnoreCase(forceVer) > 0) {
						response.setForce(0);
						response.setUpgradeUrl(upgradeUrl);
						response.setGuideUrl(guideUrl);
					} else {
						response.setForce(1);
						response.setUpgradeUrl(upgradeUrl);
						response.setGuideUrl(guideUrl);
					}
				}
			}
			bre.setData(response);

		} catch (RestServiceException e) {
			logger.error("SystemController upgrade:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("SystemController upgrade:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * @Title: notice
	 * @Description: 获取系统提示公告
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/notice", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity notice(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			KFFNotice dto = kffRmiService.selectLatestNotice();
			bre.setData(dto);

		} catch (RestServiceException e) {
			logger.error("SystemController notice:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("SystemController notice:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: submitSuggest
	 * @Description: 提交意见反馈
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/submitSuggest", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity submitSuggest(HttpServletRequest request, HttpServletResponse response, SuggestRequest suggestRequest) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = suggestRequest.getToken();
			if (StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			Integer userId = null;
			try {
				userId = AccountTokenUtil.decodeAccountToken(token);
			} catch (Exception e) {
				logger.error("submitSuggest decodeAccountToken error:{}", e);
				return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR, e.getMessage());
			}
			if (userId == null) {
				throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
			}
			suggestRequest.setCreateUserId(userId);
			kffRmiService.submitSuggest(suggestRequest);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("SystemController submitSuggest:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("SystemController submitSuggest:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
