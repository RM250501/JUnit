/*
 * 処理概要: 業務ロジックを実行し、DAOやモデルを組み合わせてユースケース処理を行います。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package model;

import java.sql.SQLException;
import java.util.List;

import dao.UserDao;

public class AdminListLogic {
	public List<User> execute() throws SQLException{
		UserDao dao = new UserDao();
		List<User> AdminInfoList = dao.findAll();
		return AdminInfoList;
		
	}
}

