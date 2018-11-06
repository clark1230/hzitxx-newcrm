package com.hzitshop.entity;

import  java.io.Serializable;
/**
 * <p>
 * 
 * </p>
 *
 * @author xianyaoji
 * @since 2018-02-26
 */
public class Settings implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 键
     */
    private String key;
    /**
     * 值
     */
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
 
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
 


    @Override
    public String toString() {
        return "Settings{" +
        "id=" + id +
        ", name=" + name +
        ", key=" + key +
        ", value=" + value +
        "}";
    }
}