/*
 * 処理概要: 画面表示向けのデータを保持するビュー用モデルです。
 * 主な処理コード: getProvisionNumber(), setProvisionNumber(), getProviderName(), setProviderName()
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProvisionView implements Serializable {
    private int provisionNumber;    // 取引（提供）番号
    private String providerName;    // 提供者氏名
    private String providerEmail;   // 提供者連絡先
    private String providerPhoto;   // 提供者顔写真
    private String textbookName;    // 教科書名
    private String subjectsName;    // 科目名
    private Timestamp tradingDatetime; // 取引日時
    private String statusName;      // 取引状態
    private String textbookPhoto;   // 教科書写真
    private String textbookDetail;  // 教科書詳細

    public ProvisionView() {}

    // ゲッター・セッター (省略)
    public int getProvisionNumber() { return provisionNumber; }
    public void setProvisionNumber(int provisionNumber) { this.provisionNumber = provisionNumber; }
    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }
    public String getProviderEmail() { return providerEmail; }
    public void setProviderEmail(String providerEmail) { this.providerEmail = providerEmail; }
    public String getProviderPhoto() { return providerPhoto; }
    public void setProviderPhoto(String providerPhoto) { this.providerPhoto = providerPhoto; }
    public String getTextbookName() { return textbookName; }
    public void setTextbookName(String textbookName) { this.textbookName = textbookName; }
    public String getSubjectsName() { return subjectsName; }
    public void setSubjectsName(String subjectsName) { this.subjectsName = subjectsName; }
    public Timestamp getTradingDatetime() { return tradingDatetime; }
    public void setTradingDatetime(Timestamp tradingDatetime) { this.tradingDatetime = tradingDatetime; }
    public String getStatusName() { return statusName; }
    public void setStatusName(String statusName) { this.statusName = statusName; }
    public String getTextbookPhoto() { return textbookPhoto; }
    public void setTextbookPhoto(String textbookPhoto) { this.textbookPhoto = textbookPhoto; }
    public String getTextbookDetail() { return textbookDetail; }
    public void setTextbookDetail(String textbookDetail) { this.textbookDetail = textbookDetail; }

    @Override
    public String toString() {
        return "ProvisionView [provisionNumber=" + provisionNumber + ", providerName=" + providerName + ", textbookName=" + textbookName + "]";
    }
}
