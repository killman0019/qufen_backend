package com.tzg.rest.controller.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.content.Navigation;
import com.tzg.entitys.kff.content.NavigationProject;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.NavigationProjectRmiService;
import com.tzg.rmi.service.NavigationRmiService;

@Controller
@RequestMapping("/app/navigation")
public class NavigationController extends BaseController {
	private static Logger log = Logger.getLogger(NavigationController.class);
	
	@Autowired
	private NavigationRmiService navigationRmiService;
	@Autowired
	private NavigationProjectRmiService navigationProjectRmiService;
	
	/** 
	* @Title: getNavigtionAndProject 
	* @Description: TODO <APP导航分类和导航项目>
	* @author linj <方法创建作者>
	* @create 下午7:51:12
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午7:51:12
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value="/getNavigtionAndProject",method = {RequestMethod.POST,RequestMethod.GET})
	public BaseResponseEntity getNavigtionAndProject(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			Map<String,Object> seMap = new HashMap<String,Object>();
			seMap.put("isShow", sysGlobals.ENABLE);
			List<Navigation> navis = navigationRmiService.findListByAttr(seMap);
			if(navis.isEmpty()) {
				bre.setData(map);
				bre.setCode(RestErrorCode.NO_DATA_MSG.getValue());
				bre.setMsg(RestErrorCode.NO_DATA_MSG.getErrorReason());
				return bre;
			}
			for (Navigation navigation : navis) {
				seMap.clear();
				seMap.put("navigationId", navigation.getId());
				List<NavigationProject> naviprs = navigationProjectRmiService.findListByAttr(seMap);
				if(!naviprs.isEmpty()) {
					navigation.setNavigationProjects(naviprs);
				}
			}
            map.put("navigations", navis);
            bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("FollowController saveFollow:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("FollowController saveFollow:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
}


