/*
 * 処理概要: 画面表示向けのデータを保持するビュー用モデルです。
 * 主な処理コード: getTradingNumber(), setTradingNumber(), getTextbookName(), setTextbookName()
 */
package model;

public class TradePartnerView {
    private int tradingNumber;
    private String textbookName;

    // 取引相手（希望者）
    private int userId;
    private String fullName;
    private String emailAddress;
	public int getTradingNumber() {
		return tradingNumber;
	}
	public void setTradingNumber(int tradingNumber) {
		this.tradingNumber = tradingNumber;
	}
	public String getTextbookName() {
		return textbookName;
	}
	public void setTextbookName(String textbookName) {
		this.textbookName = textbookName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

    // getter / setter
}

