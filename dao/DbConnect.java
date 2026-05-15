/*
 * 処理概要: データベース接続の生成・取得を行う共通基盤です。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB接続ユーティリティ
 * MariaDBへの接続を統一して提供するクラス
 */
public class DbConnect {

    // DB接続情報（必要に応じて変更）
    private static final String URL = "jdbc:mariadb://localhost/testdb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "mysql";

    static {
        try {
            // MariaDB JDBC ドライバをロード
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MariaDB JDBC Driver が見つかりません", e);
        }
    }

    /**
     * DB接続を取得する
     * @return Connectionオブジェクト
     * @throws SQLException DB接続エラー
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASS);
    }
}

