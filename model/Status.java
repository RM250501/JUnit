/*
 * 処理概要: ドメインデータの保持・受け渡しを行うモデルクラスです。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package model;

public final class Status {

    // users
    public static final int USER_INACTIVE = 1;
    public static final int USER_ACTIVE = 2;

    // trading
    public static final int TRADE_ONGOING = 3;
    public static final int TRADE_COMPLETED = 4;
    public static final int TRADE_CANCELED = 5;

    // messages
    public static final int MESSAGE_UNSENT = 6;
    public static final int MESSAGE_SENT = 7;

    // textbooks
    public static final int TEXTBOOK_AVAILABLE = 8;
    public static final int TEXTBOOK_UNAVAILABLE = 9;

    private Status() {}
}

