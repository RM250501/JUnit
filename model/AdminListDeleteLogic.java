/*
 * 処理概要: 業務ロジックを実行し、DAOやモデルを組み合わせてユースケース処理を行います。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package model;

import java.sql.SQLException;
import java.util.List;

import dao.UserDao;

public class AdminListDeleteLogic {

    private UserDao dao;

    public AdminListDeleteLogic() throws SQLException {
        dao = new UserDao();
    }

    // 管理者IDから1件取得
    public User findById(int userId) throws SQLException {
        return dao.findById(userId);
    }

    // 管理者削除
    public void delete(int userId) throws SQLException {

        dao.deleteUser(userId);
    }


    // 全件取得
    public List<User> findAll() throws SQLException {
        return dao.findAll();
    }
}

