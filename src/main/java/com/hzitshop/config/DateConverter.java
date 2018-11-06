package com.hzitshop.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 需要把字符串转换为Date对象
 * simple introduction
 *
 * <p>detailed comment
 * @author xianyaoji 2017年8月25日
 * @see
 * @since 1.0
 */
@Component
public class DateConverter  implements Converter<String,Date> {
    @Override
    public Date convert(String source) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if(!"".equals(source)){
                return sdf.parse(source);//调用parse方法把字符串转换为Date对象
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
