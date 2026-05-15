/*
 * 処理概要: ドメインデータの保持・受け渡しを行うモデルクラスです。
 * 主な処理コード: getSuggestedNumber(), setSuggestedNumber(), getUserId(), setUserId()
 */
package model;

public class SuggestedInfo {
    private int suggestedNumber;
    private int userId;
    private int provisionNumber;

    public int getSuggestedNumber() { return suggestedNumber; }
    public void setSuggestedNumber(int suggestedNumber) { this.suggestedNumber = suggestedNumber; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProvisionNumber() { return provisionNumber; }
    public void setProvisionNumber(int provisionNumber) { this.provisionNumber = provisionNumber; }
}

