/*
 * 処理概要: ドメインデータの保持・受け渡しを行うモデルクラスです。
 * 主な処理コード: getProvisionNumber(), setProvisionNumber(), getUserId(), setUserId()
 */
package model;

public class Provision {
    private int provisionNumber;
    private int userId;
    private int textbookNumber;
    private int subjectsNumber;
    private String textbookDetail;
    private String textbookPhoto;

    public int getProvisionNumber() { return provisionNumber; }
    public void setProvisionNumber(int provisionNumber) { this.provisionNumber = provisionNumber; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getTextbookNumber() { return textbookNumber; }
    public void setTextbookNumber(int textbookNumber) { this.textbookNumber = textbookNumber; }

    public int getSubjectsNumber() { return subjectsNumber; }
    public void setSubjectsNumber(int subjectsNumber) { this.subjectsNumber = subjectsNumber; }

    public String getTextbookDetail() { return textbookDetail; }
    public void setTextbookDetail(String textbookDetail) { this.textbookDetail = textbookDetail; }

    public String getTextbookPhoto() { return textbookPhoto; }
    public void setTextbookPhoto(String textbookPhoto) { this.textbookPhoto = textbookPhoto; }
}

