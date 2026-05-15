/*
 * 処理概要: 業務ロジックを実行し、DAOやモデルを組み合わせてユースケース処理を行います。
 * 主な処理コード: registerManager()
 */
package model;

import java.sql.SQLException;

import dao.UserDao;

public class AdminListRegistrationLogic {
    
    private UserDao userDao;

    public AdminListRegistrationLogic() throws SQLException {
        this.userDao = new UserDao();
    }

    /**
     * 管理者情報の登録処理
     */
    public boolean registerManager(User user) {
        try {
            int result = userDao.insert(user);
            return result > 0;  // 登録成功した場合は true を返す
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // エラーが発生した場合は false を返す
        }
    }

    // 他のビジネスロジック処理もここに追加できます。
}



