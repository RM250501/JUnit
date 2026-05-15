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

import model.Provision;
import model.ProvisionView;

public class ProvisionDao {
	private Connection conn;

	public ProvisionDao() throws SQLException {
		this.conn = DbConnect.getConnection();
	}

	// --- 挿入・更新・削除 ---

	public int insert(Provision pi) throws SQLException {
		String sql = "INSERT INTO provision_info (user_id, textbook_number, subjects_number, textbook_detail, textbook_photo) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pst.setInt(1, pi.getUserId());
			pst.setInt(2, pi.getTextbookNumber());
			pst.setInt(3, pi.getSubjectsNumber());
			pst.setString(4, pi.getTextbookDetail());
			pst.setString(5, pi.getTextbookPhoto());
			pst.executeUpdate();
			try (ResultSet rs = pst.getGeneratedKeys()) {
				if (rs.next())
					return rs.getInt(1);
			}
		}
		return -1;
	}

	public Provision findById(int provisionNumber) throws SQLException {
		String sql = "SELECT * FROM provision_info WHERE provision_number=?";
		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, provisionNumber);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next())
					return mapToEntity(rs);
			}
		}
		return null;
	}

	public boolean update(Provision pi) throws SQLException {
		String sql = "UPDATE provision_info SET user_id=?, textbook_number=?, subjects_number=?, textbook_detail=?, textbook_photo=? WHERE provision_number=?";
		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, pi.getUserId());
			pst.setInt(2, pi.getTextbookNumber());
			pst.setInt(3, pi.getSubjectsNumber());
			pst.setString(4, pi.getTextbookDetail());
			pst.setString(5, pi.getTextbookPhoto());
			pst.setInt(6, pi.getProvisionNumber());
			return pst.executeUpdate() > 0;
		}
	}

	public boolean delete(int provisionNumber) throws SQLException {
		String sql = "DELETE FROM provision_info WHERE provision_number=?";
		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, provisionNumber);
			return pst.executeUpdate() > 0;
		}
	}

	// --- ProvisionView (表示用モデル) を返すメソッド ---

	/**
	 * 受領した教科書の提供者情報を取得 (ProvisionListServlet用)
	 * カラム数が多いこちらのSQLを mapToView の基準とします。
	 */
	public List<ProvisionView> findinfoByUserId(int userId) throws SQLException {
		List<ProvisionView> list = new ArrayList<>();

		String sql = """
				SELECT
				    tr.trading_number AS provision_number,
				    u.full_name AS providerName,
				    u.email_address AS providerEmail,
				    u.users_photo AS providerPhoto,
				    t.textbook_name AS textbookName,
				    sub.subjects_name AS subjectsName,
				    tr.trading_datetime AS tradingDatetime,
				    st.status_name AS statusName,
				    p.textbook_photo AS textbookPhoto,
				    p.textbook_detail AS TextbookDetail
				FROM textbook_trading_info tr
				JOIN suggested_info s ON tr.suggested_number = s.suggested_number
				JOIN provision_info p ON tr.provision_number = p.provision_number
				JOIN users u ON p.user_id = u.user_id
				JOIN textbooks_info t ON p.textbook_number = t.textbook_number
				JOIN subjects_info sub ON p.subjects_number = sub.subjects_number
				JOIN status_table st ON tr.status_id = st.status_id
				WHERE s.user_id = ?
				ORDER BY tr.trading_datetime DESC
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

	/**
	 * 自分が提供している教科書一覧を取得 (GiveHomeServlet用)
	 * こちらは専用のマッピングメソッド mapToSimpleView を使用します。
	 */
	public List<ProvisionView> findByUserId(int userId) throws SQLException {
		List<ProvisionView> list = new ArrayList<>();

		String sql = """
				SELECT
				    p.provision_number,
				    t.textbook_name,
				    p.textbook_detail,
				    p.textbook_photo,
				    s.subjects_name
				FROM provision_info p
				JOIN textbooks_info t ON p.textbook_number = t.textbook_number
				JOIN subjects_info s ON p.subjects_number = s.subjects_number
				WHERE p.user_id = ?
				ORDER BY p.provision_number DESC
				""";

		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			pst.setInt(1, userId);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					list.add(mapToSimpleView(rs));
				}
			}
		}
		return list;
	}

	// --- マッピングメソッド ---

	/**
	 * 全フィールドを持つSQL用のマッピング
	 */
	private ProvisionView mapToView(ResultSet rs) throws SQLException {
		ProvisionView p = new ProvisionView();
		p.setProvisionNumber(rs.getInt("provision_number"));
		p.setProviderName(rs.getString("providerName"));
		p.setProviderEmail(rs.getString("providerEmail"));
		p.setProviderPhoto(rs.getString("providerPhoto"));
		p.setTextbookName(rs.getString("textbookName"));
		p.setSubjectsName(rs.getString("subjectsName"));
		p.setTradingDatetime(rs.getTimestamp("tradingDatetime"));
		p.setStatusName(rs.getString("statusName"));
		p.setTextbookPhoto(rs.getString("textbookPhoto"));
		p.setTextbookDetail(rs.getString("TextbookDetail"));
		return p;
	}

	/**
	 * 自分の出品一覧など、シンプルなSQL用のマッピング
	 */
	private ProvisionView mapToSimpleView(ResultSet rs) throws SQLException {
		ProvisionView p = new ProvisionView();
		p.setProvisionNumber(rs.getInt("provision_number"));
		p.setTextbookName(rs.getString("textbook_name"));
		p.setTextbookDetail(rs.getString("textbook_detail"));
		p.setTextbookPhoto(rs.getString("textbook_photo"));
		p.setSubjectsName(rs.getString("subjects_name"));
		return p;
	}

	private Provision mapToEntity(ResultSet rs) throws SQLException {
		Provision pi = new Provision();
		pi.setProvisionNumber(rs.getInt("provision_number"));
		pi.setUserId(rs.getInt("user_id"));
		pi.setTextbookNumber(rs.getInt("textbook_number"));
		pi.setSubjectsNumber(rs.getInt("subjects_number"));
		pi.setTextbookDetail(rs.getString("textbook_detail"));
		pi.setTextbookPhoto(rs.getString("textbook_photo"));
		return pi;
	}

	public List<Provision> findAll() throws SQLException {
	    List<Provision> provisions = new ArrayList<>();

	    // テーブルの全カラムを取得
	    String sql = "SELECT * FROM provision_info";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                // 既存の mapToEntity メソッドを呼び出す
	                provisions.add(mapToEntity(rs));
	            }
	        }
	    }
	    return provisions;
	}

	public List<Provision> searchByKeyword(String keyword) throws SQLException {
	    List<Provision> list = new ArrayList<>();

	    // 科目名(subjects_name) または 教科書名(textbook_name) にキーワードが含まれるか検索
	    String sql = """
	            SELECT p.*
	            FROM provision_info p
	            JOIN subjects_info s ON p.subjects_number = s.subjects_number
	            JOIN textbooks_info t ON p.textbook_number = t.textbook_number
	            WHERE s.subjects_name LIKE ? OR t.textbook_name LIKE ?
	            """;

	    try (PreparedStatement pst = conn.prepareStatement(sql)) {
	        // 部分一致検索のために % でキーワードを囲む
	        String likeKeyword = "%" + keyword + "%";
	        pst.setString(1, likeKeyword);
	        pst.setString(2, likeKeyword);

	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                // 基本的なEntityマッピングを使用してリストに追加
	                list.add(mapToEntity(rs));
	            }
	        }
	    }
	    return list;
	}
	
	/**
	 * 受領した（希望を出した）教科書の詳細情報と提供者の情報を取得する
	 * * @param userId ログインユーザー（受領側）のID
	 * @return 結合された情報のリスト
	 */
	public List<ProvisionView> findProviderinfoByUserId(int userId) throws SQLException {
	    List<ProvisionView> list = new ArrayList<>();

	    // SQL文の構築（多数側のテーブル構造に合わせた正確なJOIN）
	    String sql = """
	            SELECT
	                tr.trading_number,
	                u.full_name,
	                u.email_address,
	                u.users_photo,
	                t.textbook_name,
	                sub.subjects_name,
	                tr.trading_datetime,
	                st.status_name,
	                p.textbook_photo,
	                p.textbook_detail
	            FROM textbook_trading_info tr
	            JOIN suggested_info s ON tr.suggested_number = s.suggested_number
	            JOIN provision_info p ON tr.provision_number = p.provision_number
	            JOIN users u ON p.user_id = u.user_id
	            JOIN textbooks_info t ON p.textbook_number = t.textbook_number
	            JOIN subjects_info sub ON p.subjects_number = sub.subjects_number
	            JOIN status_table st ON tr.status_id = st.status_id
	            WHERE s.user_id = ?
	            ORDER BY tr.trading_datetime DESC
	            """;

	    try (PreparedStatement pst = conn.prepareStatement(sql)) {
	        pst.setInt(1, userId);
	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                list.add(mapToViewProvider(rs));
	            }
	        }
	    }
	    return list;
	}

	/**
	 * findinfoByUserId 専用のマッピングメソッド
	 */
	private ProvisionView mapToViewProvider(ResultSet rs) throws SQLException {
	    ProvisionView p = new ProvisionView();
	    // エイリアスをあえて使わず、DBのカラム名で直接取得することでミスを防ぐ
	    p.setProvisionNumber(rs.getInt("trading_number"));
	    p.setProviderName(rs.getString("full_name"));
	    p.setProviderEmail(rs.getString("email_address"));
	    p.setProviderPhoto(rs.getString("users_photo"));
	    p.setTextbookName(rs.getString("textbook_name"));
	    p.setSubjectsName(rs.getString("subjects_name"));
	    p.setTradingDatetime(rs.getTimestamp("trading_datetime"));
	    p.setStatusName(rs.getString("status_name"));
	    p.setTextbookPhoto(rs.getString("textbook_photo"));
	    p.setTextbookDetail(rs.getString("textbook_detail"));
	    return p;
	}
}
