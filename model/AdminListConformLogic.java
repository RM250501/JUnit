/*
 * 処理概要: 業務ロジックを実行し、DAOやモデルを組み合わせてユースケース処理を行います。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
/**
 * 
 */
package model;

import java.sql.SQLException;

import dao.UserDao;

/**
 * 
 */
public class AdminListConformLogic {
	public void execute(User adminInfo) throws SQLException{
		UserDao dao = new UserDao();
		dao.updateUser(adminInfo);
	}
}

