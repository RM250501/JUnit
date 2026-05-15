/*
 * 処理概要: ドメインデータの保持・受け渡しを行うモデルクラスです。
 * 主な処理コード: getSubjectsNumber(), setSubjectsNumber(), getSubjectsName(), setSubjectsName()
 */
package model;

public class Subject {
    private int subjectsNumber;
    private String subjectsName;
    private String subjectsDetail;
    private int statusId;

    public int getSubjectsNumber() { return subjectsNumber; }
    public void setSubjectsNumber(int subjectsNumber) { this.subjectsNumber = subjectsNumber; }

    public String getSubjectsName() { return subjectsName; }
    public void setSubjectsName(String subjectsName) { this.subjectsName = subjectsName; }

    public String getSubjectsDetail() { return subjectsDetail; }
    public void setSubjectsDetail(String subjectsDetail) { this.subjectsDetail = subjectsDetail; }

    public int getStatusId() { return statusId; }
    public void setStatusId(int statusId) { this.statusId = statusId; }
}

