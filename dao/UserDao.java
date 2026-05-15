/*
 * 処理概要: DAOとしてデータベースに対する検索・登録・更新・削除処理を担当します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDao {

    private final Connection conn;

    public UserDao() throws SQLException {
        this.conn = DbConnect.getConnection();
    }

    /** INSERT */
    public int insert(User user) throws SQLException {
        String sql = """
            INSERT INTO users (
                password, role, full_name, email_address,
                phone_number, users_photo, status_id
            ) VALUES (?, ?, ?, ?, ?, ?, 1)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getRole());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmailAddress());
            ps.setString(5, user.getPhoneNumber());
            ps.setString(6, user.getUsersPhoto());
            return ps.executeUpdate();
        }
    }

    /** UPDATE */
    public int updateUser(User user) throws SQLException {
        String sql = """
            UPDATE users SET
                password = ?, role = ?, full_name = ?, email_address = ?,
                phone_number = ?, users_photo = ?, status_id = ?
            WHERE user_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getRole());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmailAddress());
            ps.setString(5, user.getPhoneNumber());
            ps.setString(6, user.getUsersPhoto());
            ps.setInt(7, user.getStatusId());
            ps.setInt(8, user.getUserId());
            return ps.executeUpdate();
        }
    }

    /** DELETE（CASCADE 前提で1行のみ） */
    public int deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try {
            conn.setAutoCommit(false);

            int result;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                result = ps.executeUpdate();
            }

            conn.commit();
            return result;

        } catch (SQLException e) {
            conn.rollback();
            throw e;

        } finally {
            conn.setAutoCommit(true);
        }
    }

    /** 全件取得 */
    public List<User> findAll() throws SQLException {
        String sql = """
            SELECT user_id, password, role, full_name, email_address,
                   phone_number, users_photo, status_id
            FROM users
        """;

        List<User> list = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapUser(rs));
            }
        }
        return list;
    }

    /** ログイン認証 */
    /** ログイン認証 */
    public User userLogin(String name, String pwd) throws SQLException {
        String sql = "SELECT user_id, password, role, full_name, email_address, " +
                     "phone_number, users_photo, status_id FROM users WHERE full_name = ? AND password = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, pwd);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapUser(rs) : null;  // ユーザーが存在すればUserオブジェクトを返す
            }
        }
    }


    /** 1件取得 */
    public User findById(int userId) throws SQLException {
        String sql = """
            SELECT user_id, password, role, full_name, email_address,
                   phone_number, users_photo, status_id
            FROM users WHERE user_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapUser(rs) : null;
            }
        }
    }

    /**
     * 本人確認用 証明画像パス取得（最新1件）
     */
    public String findIdentityImagePathByUserId(int userId) throws SQLException {

        String sql = """
            SELECT identity_photo
            FROM user_identity_verification
            WHERE user_id = ?
            ORDER BY submitted_at DESC
            LIMIT 1
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("identity_photo");
                }
            }
        }
        return null; // 未提出
    }

    /** 本人確認画像 登録 or 更新 */
    public void upsertIdentityImage(int userId, String imagePath) throws SQLException {

        // 既にレコードがあるか確認
        String checkSql = """
            SELECT verification_id
            FROM user_identity_verification
            WHERE user_id = ?
        """;

        try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
            checkPs.setInt(1, userId);

            try (ResultSet rs = checkPs.executeQuery()) {

                if (rs.next()) {
                    // 既存 → UPDATE（再申請なので status は inactive）
                    String updateSql = """
                        UPDATE user_identity_verification
                        SET identity_photo = ?,
                            status_id = 1,
                            submitted_at = CURRENT_TIMESTAMP,
                            verified_at = NULL
                        WHERE user_id = ?
                    """;

                    try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                        ps.setString(1, imagePath);
                        ps.setInt(2, userId);
                        ps.executeUpdate();
                    }

                } else {
                    // 新規 → INSERT
                    String insertSql = """
                        INSERT INTO user_identity_verification
                            (user_id, identity_photo, status_id)
                        VALUES (?, ?, 1)
                    """;

                    try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                        ps.setInt(1, userId);
                        ps.setString(2, imagePath);
                        ps.executeUpdate();
                    }
                }
            }
        }
    }

    /** ResultSet → User オブジェクト変換 */
    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setFullName(rs.getString("full_name"));
        user.setEmailAddress(rs.getString("email_address"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setUsersPhoto(rs.getString("users_photo"));
        user.setStatusId(rs.getInt("status_id"));
        return user;
    }
}

