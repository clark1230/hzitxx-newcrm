package com.hzitshop.vo;

public class FilterDataCount {
    private int total;//总数据
    private int invalidNum;//无效数据

    public FilterDataCount() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getInvalidNum() {
        return invalidNum;
    }

    public void setInvalidNum(int invalidNum) {
        this.invalidNum = invalidNum;
    }

    @Override
    public String toString() {
        return "FilterDataCount{" +
                "total=" + total +
                ", invalidNum=" + invalidNum +
                '}';
    }
}
