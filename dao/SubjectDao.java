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

import model.Subject;

public class SubjectDao {
    private Connection conn;

    public SubjectDao() throws SQLException {
        this.conn = DbConnect.getConnection();
    }

    public void insert(Subject subject) throws SQLException {
        String sql = "INSERT INTO subjects_info(subjects_name, subjects_detail, status_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, subject.getSubjectsName());
            ps.setString(2, subject.getSubjectsDetail());
            ps.setInt(3, subject.getStatusId());
            ps.executeUpdate();
        }
    }

    public void update(Subject subject) throws SQLException {
        String sql = "UPDATE subjects_info SET subjects_name=?, subjects_detail=?, status_id=? WHERE subjects_number=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, subject.getSubjectsName());
            ps.setString(2, subject.getSubjectsDetail());
            ps.setInt(3, subject.getStatusId());
            ps.setInt(4, subject.getSubjectsNumber());
            ps.executeUpdate();
        }
    }

    public void delete(int subjectsNumber) throws SQLException {
        String sql = "DELETE FROM subjects_info WHERE subjects_number=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, subjectsNumber);
            ps.executeUpdate();
        }
    }

    public Subject findById(int subjectsNumber) throws SQLException {
        String sql = "SELECT * FROM subjects_info WHERE subjects_number=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, subjectsNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Subject subject = new Subject();
                    subject.setSubjectsNumber(rs.getInt("subjects_number"));
                    subject.setSubjectsName(rs.getString("subjects_name"));
                    subject.setSubjectsDetail(rs.getString("subjects_detail"));
                    subject.setStatusId(rs.getInt("status_id"));
                    return subject;
                }
            }
        }
        return null;
    }

    public List<Subject> findAll() throws SQLException {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subjects_info";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectsNumber(rs.getInt("subjects_number"));
                subject.setSubjectsName(rs.getString("subjects_name"));
                subject.setSubjectsDetail(rs.getString("subjects_detail"));
                subject.setStatusId(rs.getInt("status_id"));
                list.add(subject);
            }
        }
        return list;
    }

    public List<Subject> searchByKeyword(String keyword) {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT subjects_number, subjects_name, subjects_detail, status_id "
                   + "FROM subjects_info "
                   + "WHERE subjects_name LIKE ? OR subjects_detail LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            String likeKeyword = "%" + keyword + "%";
            ps.setString(1, likeKeyword);
            ps.setString(2, likeKeyword);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setSubjectsNumber(rs.getInt("subjects_number"));
                    subject.setSubjectsName(rs.getString("subjects_name"));
                    subject.setSubjectsDetail(rs.getString("subjects_detail"));
                    subject.setStatusId(rs.getInt("status_id"));
                    subjects.add(subject);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

}

