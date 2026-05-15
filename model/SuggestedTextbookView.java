/*
 * 処理概要: 画面表示向けのデータを保持するビュー用モデルです。
 * 主な処理コード: getSuggestedNumber(), setSuggestedNumber(), getRequesterUserId(), setRequesterUserId()
 */
package model;

public class SuggestedTextbookView {

    private int suggestedNumber;

    private int requesterUserId;

    private int provisionNumber;
    private String textbookPhoto;

    private int textbookNumber;
    private String textbookName;

    private int providerUserId;
    private String providerName;
    private String providerPhoto;

    public int getSuggestedNumber() {
        return suggestedNumber;
    }
    public void setSuggestedNumber(int suggestedNumber) {
        this.suggestedNumber = suggestedNumber;
    }

    public int getRequesterUserId() {
        return requesterUserId;
    }
    public void setRequesterUserId(int requesterUserId) {
        this.requesterUserId = requesterUserId;
    }

    public int getProvisionNumber() {
        return provisionNumber;
    }
    public void setProvisionNumber(int provisionNumber) {
        this.provisionNumber = provisionNumber;
    }

    public String getTextbookPhoto() {
        return textbookPhoto;
    }
    public void setTextbookPhoto(String textbookPhoto) {
        this.textbookPhoto = textbookPhoto;
    }

    public int getTextbookNumber() {
        return textbookNumber;
    }
    public void setTextbookNumber(int textbookNumber) {
        this.textbookNumber = textbookNumber;
    }

    public String getTextbookName() {
        return textbookName;
    }
    public void setTextbookName(String textbookName) {
        this.textbookName = textbookName;
    }

    public int getProviderUserId() {
        return providerUserId;
    }
    public void setProviderUserId(int providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderPhoto() {
        return providerPhoto;
    }
    public void setProviderPhoto(String providerPhoto) {
        this.providerPhoto = providerPhoto;
    }
}

