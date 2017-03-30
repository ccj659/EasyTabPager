package com.ccj.tabpager.bean;



public class DemoModel {

    public String content;

    /**
     * 这个model中决定数据类型的字段
     */
    public int type;


    @Override
    public String toString() {
        return "DemoModel{" +
                "content='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}
