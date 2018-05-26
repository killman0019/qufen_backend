package com.tzg.wap.utils;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.tzg.common.utils.TzgConstant;

public class CSRFUtil {
    
    public static String getTokenForSession(HttpSession session) {
        String token = null;
        synchronized (session) {
            token = (String) session.getAttribute(TzgConstant.CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
            if (null == token) {
                token = UUID.randomUUID().toString().replace("-", "");
                session.setAttribute(TzgConstant.CSRF_TOKEN_FOR_SESSION_ATTR_NAME, token);
            }
        }
        return token;
    }
}
