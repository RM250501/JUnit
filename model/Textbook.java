/*
 * 処理概要: ドメインデータの保持・受け渡しを行うモデルクラスです。
 * 主な処理コード: getTextbookNumber(), setTextbookNumber(), getTextbookName(), setTextbookName()
 */
package model;

public class Textbook {
    private int textbookNumber;
    private String textbookName;
    private int statusId;

    public int getTextbookNumber() { return textbookNumber; }
    public void setTextbookNumber(int textbookNumber) { this.textbookNumber = textbookNumber; }

    public String getTextbookName() { return textbookName; }
    public void setTextbookName(String textbookName) { this.textbookName = textbookName; }

    public int getStatusId() { return statusId; }
    public void setStatusId(int statusId) { this.statusId = statusId; }
}

