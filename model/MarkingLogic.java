/*
 * 処理概要: 業務ロジックを実行し、DAOやモデルを組み合わせてユースケース処理を行います。
 * 主な処理コード: registerSuggestion()
 */
package model;

import java.sql.SQLException;

import dao.SuggestedInfoDao;

public class MarkingLogic {

    /**
     * 希望登録（User が Textbook を希望）
     * @param userId ユーザID
     * @param provisionNumber 教科書番号
     * @return 登録成功なら true、重複 or 失敗なら false
     */
    public boolean registerSuggestion(int userId, int provisionNumber) {
        try {
            SuggestedInfoDao dao = new SuggestedInfoDao();

            // ★ 重複チェック
            if (dao.exists(userId, provisionNumber)) {
                // すでに登録済み
                return false;
            }

            SuggestedInfo su = new SuggestedInfo();
            su.setUserId(userId);
            su.setProvisionNumber(provisionNumber);

            int result = dao.insert(su);

            return result > 0;  // 1行追加されたら成功

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

