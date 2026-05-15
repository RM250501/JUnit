/*
 * 処理概要: ドメインデータの保持・受け渡しを行うモデルクラスです。
 * 主な処理コード: getUserId(), setUserId(), getPassword(), setPassword()
 */
package model;

import java.io.Serializable;

/**
 * usersテーブルに対応するエンティティクラス
 * 管理者・一般ユーザなどを含む全ユーザ情報を保持する。
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ユーザID（PK） */
    private int userId;

    /** パスワード */
    private String password;

    /** 区分（管理者／一般など） */
    private String role;

    /** 氏名 */
    private String fullName;

    /** メールアドレス */
    private String emailAddress;

    /** 電話番号 */
    private String phoneNumber;

    /** 本人写真（ファイルパスまたはURL） */
    private String usersPhoto;

    /** ステータスID（FK） */
    private int statusId;
    

    // --- コンストラクタ ---
    public User() {}

    public User(int userId, String password, String role, String fullName, 
                String emailAddress, String phoneNumber, 
                String usersPhoto, int statusId) {
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.usersPhoto = usersPhoto;
        this.statusId = statusId;
    }

    // --- Getter / Setter ---
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsersPhoto() {
        return usersPhoto;
    }

    public void setUsersPhoto(String usersPhoto) {
        this.usersPhoto = usersPhoto;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    // --- デバッグ・表示用 ---
    @Override
    public String toString() {
        return "User [userId=" + userId
                + ", password=" + password
                + ", role=" + role
                + ", fullName=" + fullName
                + ", emailAddress=" + emailAddress
                + ", phoneNumber=" + phoneNumber
                + ", usersPhoto=" + usersPhoto
                + ", statusId=" + statusId + "]";
    }
    
    /**
     * ユーザが管理者かどうかを判定する
     * @return true: 管理者, false: 一般ユーザ
     */
    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(this.role);
    }

    
}


