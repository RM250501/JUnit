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

import model.SuggestedInfo;

public class SuggestedInfoDao {
    private Connection conn;

    public SuggestedInfoDao() throws SQLException {
        this.conn = DbConnect.getConnection();
    }

    public int insert(SuggestedInfo si) throws SQLException {
        String sql = "INSERT INTO suggested_info (user_id, provision_number) VALUES (?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, si.getUserId());
            pst.setInt(2, si.getProvisionNumber());
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if(rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public SuggestedInfo findById(int suggestedNumber) throws SQLException {
        String sql = "SELECT * FROM suggested_info WHERE suggested_number=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, suggestedNumber);
            try (ResultSet rs = pst.executeQuery()) {
                if(rs.next()) return mapToEntity(rs);
            }
        }
        return null;
    }

    public List<SuggestedInfo> findAll() throws SQLException {
        List<SuggestedInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM suggested_info ORDER BY suggested_number DESC";
        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while(rs.next()) list.add(mapToEntity(rs));
        }
        return list;
    }

    public boolean update(SuggestedInfo si) throws SQLException {
        String sql = "UPDATE suggested_info SET user_id=?, provision_number=? WHERE suggested_number=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, si.getUserId());
            pst.setInt(2, si.getProvisionNumber());
            pst.setInt(3, si.getSuggestedNumber());
            return pst.executeUpdate() > 0;
        }
    }

    public boolean delete(int suggestedNumber) throws SQLException {
        String sql = "DELETE FROM suggested_info WHERE suggested_number=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, suggestedNumber);
            return pst.executeUpdate() > 0;
        }
    }

    private SuggestedInfo mapToEntity(ResultSet rs) throws SQLException {
        SuggestedInfo si = new SuggestedInfo();
        si.setSuggestedNumber(rs.getInt("suggested_number"));
        si.setUserId(rs.getInt("user_id"));
        si.setProvisionNumber(rs.getInt("provision_number"));
        return si;
    }
    
    public boolean exists(int userId, int provisionNumber) throws SQLException {
        String sql = "SELECT 1 FROM suggested_info WHERE user_id = ? AND provision_number = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, provisionNumber);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // 1件でもあれば true
            }
        }
    }
    
    public boolean deleteByprovider_id(int provider_id) throws SQLException {
        String sql = "DELETE FROM suggested_info WHERE provision_number=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, provider_id);
            return pst.executeUpdate() > 0;
        }
    }


}

