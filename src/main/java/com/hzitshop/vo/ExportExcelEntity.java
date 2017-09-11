package com.hzitshop.vo;

/**
 * Created by xianyaoji on 2017/3/28.
 */
public class ExportExcelEntity {
    private String date;  //日期
    private String companyId;   //公司
    private String userId;            //咨询师编号
    private String userName;//咨询师名字
    private String customerState;//学员状态
    public ExportExcelEntity(){
        
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    @Override
    public String toString() {
        return "ExportExcelEntity{" +
                "date='" + date + '\'' +
                ", companyId='" + companyId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
