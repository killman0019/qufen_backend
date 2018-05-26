package com.tzg.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.core.utils.HttpSessionUtil;
import com.tzg.core.utils.LogFactory;
import com.tzg.entitys.leopard.console.ConsoleLoginAccount;
import com.tzg.entitys.leopard.resource.Resource;
import com.tzg.service.consoleLoginAccount.ConsoleLoginAccountService;
import com.tzg.service.uploadfile.UploadfileService;


/**
 * Created by cfour on 11/25/14.
 */
@Controller
public class IndexController extends BaseController{

    @Autowired
    private ConsoleLoginAccountService consoleLoginAccountService;

    @Autowired
	private UploadfileService uploadfileService;
    
    @RequestMapping(value="/")
    public String index(){
         return "/index";
    }
    
    @RequestMapping(value="/main")
    public String main(){
         return "/main";
    }

    @RequestMapping(value="/nopermission")
    public String nopermission(){
        return "/nopermission";
    }

    @RequestMapping(value = "/login")
    public String login(String loginname,
                        String password,
                        String dynamicValidateCode,
                        HttpServletRequest request,Model model){
        if("".equals(loginname) || "".equals(password)){
            model.addAttribute(MESSAGE, "用户名或密码不能为空");
            return "/index";
        }
        if(StringUtils.isBlank(dynamicValidateCode)){
        	model.addAttribute(MESSAGE, "手机动态码不能为空");
            return "/index";
        }
        if(StringUtil.sql_Injection(loginname)){
        	model.addAttribute(MESSAGE, "用户名格式错误");
            return "/index";
        }
        ConsoleLoginAccount consoleLoginAccount =  consoleLoginAccountService.getLoginAccount(loginname);
        if(consoleLoginAccount == null){
        	model.addAttribute(MESSAGE, "用户不存在");
            return "/index";
        }
        if(consoleLoginAccount.getiValid()!=1){
    		model.addAttribute(MESSAGE, "该用户已失效，暂时无法登录");
            return "/index";
    	}
    	if(!HttpSessionUtil.verifyDynamicValidateCode(consoleLoginAccount.getVcPhone(), dynamicValidateCode)){
    		model.addAttribute(MESSAGE, "手机动态码错误");
            return "/index";
    	}
    	HttpSessionUtil.removeDynamicValidateCodeSession();
    	
        if(!SHAUtil.encode(password).equals(consoleLoginAccount.getVcLoginPassword())){
            model.addAttribute(MESSAGE, "密码错误");
            return "/index";
        }
        //加载菜单资源
        List<Resource> menuItem;
        if (consoleLoginAccount.getVcLoginName().equals("root")){
            menuItem = consoleLoginAccountService.getMenuByAccountId(0);
        }else{
            menuItem = consoleLoginAccountService.getMenuByAccountId(consoleLoginAccount.getId());
        }
    	request.getSession().setAttribute("menuItem", menuItem);
    	//用户Session
    	HttpSessionUtil.setLoginSession(consoleLoginAccount);
    	//用户权限列表
    	List<String> resources = consoleLoginAccountService.getResourceByAccount(consoleLoginAccount.getId());
    	List<String> list = new ArrayList<String>();
    	for(String res : resources){
    		for(String url:res.split(";")){
    			list.add(url);
    		}
        }
    	HttpSessionUtil.setResourceSession(list);
        LogFactory.logMessage("登陆");
        return "redirect:/main";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,Model model){
        LogFactory.logMessage("退出系统");
    	HttpSessionUtil.removeLoginSession();
    	return "/index";
    	
    }
    @RequestMapping("/baseaccount")
    public String baseaccount(){
        ConsoleLoginAccount account = new ConsoleLoginAccount();
        account.setVcLoginPassword(SHAUtil.encode("123456"));
        account.setVcLoginName("root");
        account.setVcRealName("root");
        account.setDtCreate(new Date());
        try {
			consoleLoginAccountService.save(account);
		} catch (Exception e) {
            LogFactory.logError("新增root失败",e);
		}
        return "/main";

    }
    
    

}
