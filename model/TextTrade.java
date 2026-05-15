/*
 * 処理概要: ドメインデータの保持・受け渡しを行うモデルクラスです。
 * 主な処理コード: getFullName(), setFullName(), getTradingNumber(), setTradingNumber()
 */
package model;

import java.sql.Timestamp;

public class TextTrade {

    private int tradingNumber;
    private int provisionNumber;
    private int suggestedNumber;
    private int textbookNumber;
    private String comment;
    private Timestamp tradingDatetime;

    // ★ status は int で持つ（DBと一致）
    private int statusId;

    // 表示用（JOIN結果）
    private String emailAddress;
    private String textbookName;

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // --- getter / setter ---

    public int getTradingNumber() {
        return tradingNumber;
    }
    public void setTradingNumber(int tradingNumber) {
        this.tradingNumber = tradingNumber;
    }

    public int getProvisionNumber() {
        return provisionNumber;
    }
    public void setProvisionNumber(int provisionNumber) {
        this.provisionNumber = provisionNumber;
    }

    public int getSuggestedNumber() {
        return suggestedNumber;
    }
    public void setSuggestedNumber(int suggestedNumber) {
        this.suggestedNumber = suggestedNumber;
    }

    public int getTextbookNumber() {
        return textbookNumber;
    }
    public void setTextbookNumber(int textbookNumber) {
        this.textbookNumber = textbookNumber;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTradingDatetime() {
        return tradingDatetime;
    }
    public void setTradingDatetime(Timestamp tradingDatetime) {
        this.tradingDatetime = tradingDatetime;
    }

    public int getStatusId() {
        return statusId;
    }
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTextbookName() {
        return textbookName;
    }
    public void setTextbookName(String textbookName) {
        this.textbookName = textbookName;
    }
}

