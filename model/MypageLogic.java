/*
 * 処理概要: 業務ロジックを実行し、DAOやモデルを組み合わせてユースケース処理を行います。
 * 主な処理コード: setMypageInfo()
 */
package model;

import dao.UserDao;
import jakarta.servlet.http.HttpServletRequest;

public class MypageLogic {

    public void setMypageInfo(User loginUser, HttpServletRequest request) {

        try {
            UserDao userDao = new UserDao();

            // 証明画像パス取得
            String identityImagePath =
                    userDao.findIdentityImagePathByUserId(loginUser.getUserId());

            request.setAttribute("identityImagePath", identityImagePath);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("identityImagePath", null);
        }
    }
}

