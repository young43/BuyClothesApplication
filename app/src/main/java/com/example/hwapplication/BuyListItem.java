package com.example.hwapplication;

public class BuyListItem {
    private String titleStr;
    private String descStr;
    private String cntStr;

    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setCntStr(String cnt) {
        cntStr = cnt ;
    }

    public String getTitle() {
        return this.titleStr;
    }
    public String getDesc() {
        return this.descStr;
    }
    public String getCntStr() { return this.cntStr; }
}
