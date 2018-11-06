package com.hzitshop.vo.employeevo;

public class EmployeeVoNameId {
    private String userId;
    private String name;
    private String templet;
    private String checkbox;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTemplet() {
        return templet;
    }

    public void setTemplet(String templet) {
        this.templet = templet;
    }

    public String getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    @Override
    public String toString() {
        return "EmployeeVoNameId{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
