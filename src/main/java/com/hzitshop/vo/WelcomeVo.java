package com.hzitshop.vo;

public class WelcomeVo {
    private int total;
    private int recordNum;
    private int dailyNum;

    public WelcomeVo() {
    }

    public WelcomeVo(int total, int recordNum, int dailyNum) {
        this.total = total;
        this.recordNum = recordNum;
        this.dailyNum = dailyNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    public int getDailyNum() {
        return dailyNum;
    }

    public void setDailyNum(int dailyNum) {
        this.dailyNum = dailyNum;
    }
}
