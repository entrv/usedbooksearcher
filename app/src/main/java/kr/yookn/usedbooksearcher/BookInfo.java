package kr.yookn.usedbooksearcher;

/**
 * Created by entrv on 2017-06-24.
 */

public class BookInfo {
    String sitename;
    String orginalMoney;
    String topMoney;
    String middleMoney;
    String lowMoney;

    public BookInfo(String sitename, String orginalMoney, String topMoney, String middleMoney, String lowMoney) {
        this.sitename = sitename;
        this.orginalMoney = orginalMoney;
        this.topMoney = topMoney;
        this.middleMoney = middleMoney;
        this.lowMoney = lowMoney;
    }

    public String getOrginalMoney() {
        return orginalMoney;
    }

    public void setOrginalMoney(String orginalMoney) {
        this.orginalMoney = orginalMoney;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getTopMoney() {
        return topMoney;
    }

    public void setTopMoney(String topMoney) {
        this.topMoney = topMoney;
    }

    public String getMiddleMoney() {
        return middleMoney;
    }

    public void setMiddleMoney(String middleMoney) {
        this.middleMoney = middleMoney;
    }

    public String getLowMoney() {
        return lowMoney;
    }

    public void setLowMoney(String lowMoney) {
        this.lowMoney = lowMoney;
    }

    public BookInfo(String sitename, String topMoney, String middleMoney, String lowMoney) {
        this.sitename = sitename;
        this.topMoney = topMoney;
        this.middleMoney = middleMoney;
        this.lowMoney = lowMoney;
    }
}
