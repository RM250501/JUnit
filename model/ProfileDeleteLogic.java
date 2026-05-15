/*
 * 処理概要: 業務ロジックを実行し、DAOやモデルを組み合わせてユースケース処理を行います。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package model;

import java.sql.SQLException;
import java.util.List;

import dao.UserDao;

public class ProfileDeleteLogic {

    private UserDao dao;

    public ProfileDeleteLogic() throws SQLException {
        dao = new UserDao();
    }

    // 管理者IDから1件取得
    public User findById(int userId) throws SQLException {
        return dao.findById(userId);
    }

    // 管理者削除
    public boolean delete(int userId) throws SQLException {

        dao.deleteUser(userId);
		return true;
    }


    // 全件取得
    public List<User> findAll() throws SQLException {
        return dao.findAll();
    }
}

