/*
 * 処理概要: 業務ロジックを実行し、DAOやモデルを組み合わせてユースケース処理を行います。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package model;

import java.sql.SQLException;

import dao.UserDao;

public class LoginLogic {

    private UserDao userDao;

    public LoginLogic() throws Exception {
        this.userDao = new UserDao();
    }
    
    public User execute(String name, String password) throws SQLException {
        User user = userDao.userLogin(name, password);
        return user; // 見つからなければ null
    }

}

