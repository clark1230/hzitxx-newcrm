package com.hzitshop.vo;

import java.util.List;

/**
 * {
 "code": 0
 ,"msg": ""
 ,"data": {
 "src": "http://cdn.abc.com/123.jpg"
 }
 }
 * Created by xianyaoji on 2017/3/8.
 */
public class FileUpload {
    private int code =0;
    private String msg;
    private PathData data;
     public FileUpload(){
         
     }
    

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PathData getData() {
        return data;
    }

    public void setData(PathData data) {
        this.data = data;
    }
}
