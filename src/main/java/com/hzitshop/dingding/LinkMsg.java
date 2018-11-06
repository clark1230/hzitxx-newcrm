package com.hzitshop.dingding;

import lombok.Data;

@Data
public class LinkMsg {
    private String messageUrl;
    private String picUrl;
    private String title;
    private String text;

    public LinkMsg() {
    }

    public LinkMsg(String messageUrl, String picUrl, String title, String text) {
        this.messageUrl = messageUrl;
        this.picUrl = picUrl;
        this.title = title;
        this.text = text;
    }
}
