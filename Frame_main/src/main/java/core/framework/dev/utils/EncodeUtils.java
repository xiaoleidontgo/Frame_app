package core.framework.dev.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by lyh on 2016/11/21.
 */

public class EncodeUtils {

    /**
     * 将数据格式转化为UTF-8
     *
     * @param utfString
     * @return
     */
    public String convert(String utfString) {
        String str = null;
        try {
            str = new String(utfString.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utfString;
    }
}
