/*
 * 処理概要: 画面表示向けのデータを保持するビュー用モデルです。
 * 主な処理コード: getTradingNumber(), setTradingNumber(), getTextbookName(), setTextbookName()
 */
package model;

import java.sql.Timestamp;

public class TransactionView {

    private int tradingNumber;
    private String textbookName;
    private String subjectsName;
    private String providerName;
    private Timestamp tradingDatetime;
    private String statusName;
    private String comment;
    private String textbookPhoto;

    // getter / setter
    public int getTradingNumber() { return tradingNumber; }
    public void setTradingNumber(int tradingNumber) { this.tradingNumber = tradingNumber; }

    public String getTextbookName() { return textbookName; }
    public void setTextbookName(String textbookName) { this.textbookName = textbookName; }

    public String getSubjectsName() { return subjectsName; }
    public void setSubjectsName(String subjectsName) { this.subjectsName = subjectsName; }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public Timestamp getTradingDatetime() { return tradingDatetime; }
    public void setTradingDatetime(Timestamp tradingDatetime) { this.tradingDatetime = tradingDatetime; }

    public String getStatusName() { return statusName; }
    public void setStatusName(String statusName) { this.statusName = statusName; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getTextbookPhoto() { return textbookPhoto; }
    public void setTextbookPhoto(String textbookPhoto) { this.textbookPhoto = textbookPhoto; }
}

