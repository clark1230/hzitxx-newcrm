package com.hzitshop.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 吕游
 * @company 合众艾特
 * @create 2017-09-18 16:29
 * @description
 */
@Configuration
@Component
@ConfigurationProperties(ignoreInvalidFields = true)
@PropertySource("classpath:config/channel.properties")
public class ChannelPropertiesVo {
    private Integer zhilian;
    private Integer qiancheng;
    private Integer tongcheng;
    private Integer rencai;
    private Integer zhonghua;
    private Integer ganji;

    public ChannelPropertiesVo() {
    }

    public Integer getZhilian() {
        return zhilian;
    }

    public void setZhilian(Integer zhilian) {
        this.zhilian = zhilian;
    }

    public Integer getQiancheng() {
        return qiancheng;
    }

    public void setQiancheng(Integer qiancheng) {
        this.qiancheng = qiancheng;
    }

    public Integer getTongcheng() {
        return tongcheng;
    }

    public void setTongcheng(Integer tongcheng) {
        this.tongcheng = tongcheng;
    }

    public Integer getRencai() {
        return rencai;
    }

    public void setRencai(Integer rencai) {
        this.rencai = rencai;
    }

    public Integer getZhonghua() {
        return zhonghua;
    }

    public void setZhonghua(Integer zhonghua) {
        this.zhonghua = zhonghua;
    }

    public Integer getGanji() {
        return ganji;
    }

    public void setGanji(Integer ganji) {
        this.ganji = ganji;
    }
}
