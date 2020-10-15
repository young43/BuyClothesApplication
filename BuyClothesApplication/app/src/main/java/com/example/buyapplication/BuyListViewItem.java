package com.example.buyapplication;

public class BuyListViewItem {
    private String titleStr;
    private String descStr;
    private String cntStr;
    private String pricestr;

    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setCntStr(String cnt) {
        cntStr = cnt ;
    }
    public void setPricestr(String price) {
        pricestr = price ;
    }

    public String getTitle() {
        return this.titleStr;
    }
    public String getDesc() {
        return this.descStr;
    }
    public String getCntStr() { return this.cntStr; }
    public String getPriceStr(){ return this.pricestr; }
}
