package com.tzg.entitys.loginaccount;

import java.io.Serializable;

/**
 * 用户前台注册
 * @author fallenLeaves
 */
public class RegisterRequest implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 395533430212236869L;

	/**
     * 手机号码
     */
    private String phoneNumber;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 确认密码
     */
    private String confirmPassword;
    
    /**
     * 手机动态验证码
     */
    private String dynamicVerifyCode;

    /**
     * 是否同意协议
     */
    private String agreeProtocol;
    
    /**
     * 注册来源
     */
    private String registerSource;
    
    /**
     * 注册设备
     */
    private String registerDevice;
    
    /**
     * 推荐码
     */ 	
	private String inviteCode;
	
	private String deviceId;
	
	private String deviceType;
	
	/**注册成功后的跳转地址*/
	private String toURL;
	
	/**
	 * 签名串
	 */
	private String sign;
	
	/**
	 * 用户昵称
	 */
	private String username;
	
	/**
	 * 用户个推手机唯一编码
	 */
	private String clientId;
	
    public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDynamicVerifyCode() {
        return dynamicVerifyCode;
    }

    public void setDynamicVerifyCode(String dynamicVerifyCode) {
        this.dynamicVerifyCode = dynamicVerifyCode;
    }

    public String getAgreeProtocol() {
        return agreeProtocol;
    }

    public void setAgreeProtocol(String agreeProtocol) {
        this.agreeProtocol = agreeProtocol;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
    }

	public String getRegisterDevice() {
		return registerDevice;
	}

	public void setRegisterDevice(String registerDevice) {
		this.registerDevice = registerDevice;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getToURL() {
		return toURL;
	}

	public void setToURL(String toURL) {
		this.toURL = toURL;
	}

	
	
	
    
}
