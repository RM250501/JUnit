/*
 * 処理概要: DAOとしてデータベースに対する検索・登録・更新・削除処理を担当します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.TransactionView;

public class TransactionDao {

    private Connection conn;

    public TransactionDao() throws SQLException {
        this.conn = DbConnect.getConnection();
    }

    /**
     * 受領者ユーザーIDに紐づく取引履歴一覧を取得
     */
    public List<TransactionView> findByRecipientUserId(int userId) throws SQLException {

        List<TransactionView> list = new ArrayList<>();

        String sql = """
            SELECT
                tti.trading_number,
                tb.textbook_name,
                sj.subjects_name,
                u.full_name AS provider_name,
                tti.trading_datetime,
                st.status_name,
                tti.comment,
                pi.textbook_photo
            FROM textbook_trading_info tti
            JOIN provision_info pi
              ON tti.provision_number = pi.provision_number
            JOIN textbooks_info tb
              ON tti.textbook_number = tb.textbook_number
            JOIN subjects_info sj
              ON pi.subjects_number = sj.subjects_number
            JOIN suggested_info si
              ON tti.suggested_number = si.suggested_number
            JOIN users u
              ON pi.user_id = u.user_id
            JOIN status_table st
              ON tti.status_id = st.status_id
            WHERE si.user_id = ?
            ORDER BY tti.trading_datetime DESC
        """;

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(mapToView(rs));
                }
            }
        }

        return list;
    }

    private TransactionView mapToView(ResultSet rs) throws SQLException {

        TransactionView tv = new TransactionView();
        tv.setTradingNumber(rs.getInt("trading_number"));
        tv.setTextbookName(rs.getString("textbook_name"));
        tv.setSubjectsName(rs.getString("subjects_name"));
        tv.setProviderName(rs.getString("provider_name"));
        tv.setTradingDatetime(rs.getTimestamp("trading_datetime"));
        tv.setStatusName(rs.getString("status_name"));
        tv.setComment(rs.getString("comment"));
        tv.setTextbookPhoto(rs.getString("textbook_photo"));

        return tv;
    }
}

