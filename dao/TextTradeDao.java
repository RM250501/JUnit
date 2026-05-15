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

import model.Status;
import model.TextTrade;
import model.TradePartnerView;

public class TextTradeDao {

	private Connection conn;

	// ★ ProvisionDao と同じ方式
	public TextTradeDao() throws SQLException {
		this.conn = DbConnect.getConnection();
	}

	public TextTrade findById(int tradingNumber) throws SQLException {
		String sql = "SELECT * FROM textbook_trading_info WHERE trading_number = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, tradingNumber);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? map(rs) : null;
			}
		}
	}

	/** ongoing → completed のみ許可 */
	public boolean completeTrade(int tradingNumber) throws SQLException {
		String sql = """
				    UPDATE textbook_trading_info
				    SET status_id = ?
				    WHERE trading_number = ?
				      AND status_id = ?
				""";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, Status.TRADE_COMPLETED);
			ps.setInt(2, tradingNumber);
			ps.setInt(3, Status.TRADE_ONGOING);
			return ps.executeUpdate() == 1;
		}
	}

	public List<TradePartnerView> findOngoingByProvider(int providerUserId)
			throws SQLException {

		List<TradePartnerView> list = new ArrayList<>();

		String sql = """
				        	    SELECT
				  tt.trading_number,
				  tt.trading_datetime,
				  st.status_name,

				  u.user_id        AS requester_user_id,
				  u.full_name      AS requester_name,
				  u.users_photo    AS requester_photo,
				  u.email_address  AS requester_email,

				  t.textbook_name

				FROM textbook_trading_info tt

				JOIN suggested_info si
				  ON tt.suggested_number = si.suggested_number

				JOIN users u
				  ON si.user_id = u.user_id

				JOIN textbooks_info t
				  ON tt.textbook_number = t.textbook_number

				JOIN status_table st
				  ON tt.status_id = st.status_id

				WHERE tt.status_id = 3   -- ongoing
				  AND tt.provision_number IN (
				      SELECT provision_number
				      FROM provision_info
				      WHERE user_id = ?
				  );

				        	""";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, providerUserId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					TradePartnerView v = new TradePartnerView();

					v.setTradingNumber(rs.getInt("trading_number"));
					v.setTextbookName(rs.getString("textbook_name"));

					v.setUserId(rs.getInt("requester_user_id"));
					v.setFullName(rs.getString("requester_name"));
					v.setEmailAddress(rs.getString("requester_email"));

					list.add(v);
				}

			}
		}
		return list;
	}

	private TextTrade map(ResultSet rs) throws SQLException {
		TextTrade t = new TextTrade();
		t.setTradingNumber(rs.getInt("trading_number"));
		t.setTextbookNumber(rs.getInt("textbook_number"));
		t.setSuggestedNumber(rs.getInt("suggested_number"));
		t.setFullName(rs.getString("full_name"));
		t.setStatusId(rs.getInt("status_id"));
		return t;
	}
}

