package org.tzg.common;

import com.tzg.common.utils.Assert;
import com.tzg.common.utils.RegexUtil;

public class RegexUtilTest {
    public static void main(String[] args) throws Exception {
        for (int i = 100; i < 200; i++) {
            String phone = i + "12345678";
            try {
                Assert.isTrue(!RegexUtil.match(phone, RegexUtil.PHONEREGEX),  phone);
            } catch (Exception e) {
              System.out.println(e.getLocalizedMessage());;
            }
        }
    }
}
