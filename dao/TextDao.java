/*
 * 処理概要: DAOとしてデータベースに対する検索・登録・更新・削除処理を担当します。
 * 主な処理コード: searchByKeyword()
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Status;
import model.Textbook;

public class TextDao {
    private Connection conn;

    // DbConnectから接続を取得する
    public TextDao() throws SQLException {
        this.conn = DbConnect.getConnection();
    }

    public void insert(Textbook text) throws SQLException {
        String sql = "INSERT INTO textbooks_info(textbook_name, status_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, text.getTextbookName());
            ps.setInt(2, text.getStatusId());
            ps.executeUpdate();
        }
    }
    
    public void update(Textbook text) throws SQLException {
        String sql = "UPDATE textbooks_info SET textbook_name=?, status_id=? WHERE textbook_number=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, text.getTextbookName());
            ps.setInt(2, text.getStatusId());
            ps.setInt(3, text.getTextbookNumber());
            ps.executeUpdate();
        }
    }

    public void delete(int textbookNumber) throws SQLException {
        String sql = "DELETE FROM textbooks_info WHERE textbook_number=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, textbookNumber);
            ps.executeUpdate();
        }
    }

    public Textbook findById(int textbookNumber) throws SQLException {
        String sql = "SELECT * FROM textbooks_info WHERE textbook_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, textbookNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Textbook> findAll() throws SQLException {
        List<Textbook> list = new ArrayList<>();
        String sql = "SELECT * FROM textbooks_info";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Textbook text = new Textbook();
                text.setTextbookNumber(rs.getInt("textbook_number"));
                text.setTextbookName(rs.getString("textbook_name"));
                text.setStatusId(rs.getInt("status_id"));
                list.add(text);
            }
        }
        return list;
    }

    public List<Textbook> searchByKeyword(String keyword) {
        List<Textbook> textbooks = new ArrayList<>();

        String sql = "SELECT textbook_number, textbook_name FROM textbooks_info "
                   + "WHERE textbook_name LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Textbook t = new Textbook();
                t.setTextbookNumber(rs.getInt("textbook_number"));
                t.setTextbookName(rs.getString("textbook_name"));
                textbooks.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return textbooks;
    }
    
    public List<Textbook> findPublicAll() throws SQLException {
        String sql = "SELECT * FROM textbooks_info WHERE status_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Status.TEXTBOOK_AVAILABLE);
            ResultSet rs = ps.executeQuery();

            List<Textbook> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    /** 管理者用 */
    public List<Textbook> findAllAdmin() throws SQLException {
        String sql = "SELECT * FROM textbooks_info";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Textbook> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }
    }

    /** 非公開にする（取引完了時） */
    public boolean makeUnavailable(int textbookNumber) throws SQLException {
        String sql = "UPDATE textbooks_info SET status_id = ? WHERE textbook_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Status.TEXTBOOK_UNAVAILABLE);
            ps.setInt(2, textbookNumber);
            return ps.executeUpdate() == 1;
        }
    }


    private Textbook map(ResultSet rs) throws SQLException {
        Textbook t = new Textbook();
        t.setTextbookNumber(rs.getInt("textbook_number"));
        t.setTextbookName(rs.getString("textbook_name"));
        t.setStatusId(rs.getInt("status_id"));
        return t;
    }
}


