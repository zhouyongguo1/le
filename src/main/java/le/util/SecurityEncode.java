package le.util;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

public class SecurityEncode {
    
    public static String encoderByMd5(String str) {
        try {
            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            // 加密后的字符串
            return base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (Exception ex) {
            return "";
        }
    }

}
