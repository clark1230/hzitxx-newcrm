package com.hzitshop.entity;

import java.util.List;

/**
 * "code": 0,
 "msg": "",
 "count": 1000,
 */

/**
 * layui表格数据的封装
 * @param <T>
 */
public class LayuiData<T> {
    private int code;
    private String msg ;
    private int count;
    private List<T> data;

    public LayuiData() {
    }

    public LayuiData(int code, String msg, int count, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data){
        this.data=data;
    }

    @Override
    public String toString() {
        return "LayuiData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
