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

import model.SuggestedTextbookView;

public class SuggestedTextbookDao {

    private Connection conn;

    public SuggestedTextbookDao() throws SQLException {
        this.conn = DbConnect.getConnection();
    }

    /**
     * ログインユーザがマーキングした教科書一覧
     */
    public List<SuggestedTextbookView> findByRequesterUserId(int userId)
            throws SQLException {

        List<SuggestedTextbookView> list = new ArrayList<>();

        String sql = """
            SELECT
              s.user_id            AS requester_user_id,
              p.provision_number,
              p.textbook_photo,
              t.textbook_number,
              t.textbook_name,
              u.user_id            AS provider_user_id,
              u.full_name          AS provider_name,
              u.users_photo        AS provider_photo,
              s.suggested_number
            FROM suggested_info s
            JOIN provision_info p
              ON s.provision_number = p.provision_number
            JOIN textbooks_info t
              ON p.textbook_number = t.textbook_number
            JOIN users u
              ON p.user_id = u.user_id
            WHERE s.user_id = ?
            ORDER BY s.suggested_number DESC
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapToView(rs));
                }
            }
        }
        return list;
    }

    private SuggestedTextbookView mapToView(ResultSet rs)
            throws SQLException {

        SuggestedTextbookView v = new SuggestedTextbookView();

        v.setSuggestedNumber(rs.getInt("suggested_number"));
        v.setRequesterUserId(rs.getInt("requester_user_id"));

        v.setProvisionNumber(rs.getInt("provision_number"));
        v.setTextbookPhoto(rs.getString("textbook_photo"));

        v.setTextbookNumber(rs.getInt("textbook_number"));
        v.setTextbookName(rs.getString("textbook_name"));

        v.setProviderUserId(rs.getInt("provider_user_id"));
        v.setProviderName(rs.getString("provider_name"));
        v.setProviderPhoto(rs.getString("provider_photo"));

        return v;
    }
}

