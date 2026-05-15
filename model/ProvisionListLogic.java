/*
 * 処理概要: 業務ロジックを実行し、DAOやモデルを組み合わせてユースケース処理を行います。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package model;

import java.sql.SQLException;
import java.util.List;
import dao.ProvisionDao;

public class ProvisionListLogic {
    public List<ProvisionView> execute(int userId) throws SQLException {
        ProvisionDao dao = new ProvisionDao();
        // 新しく作成した提供者情報取得メソッドを呼び出す
        return dao.findProviderinfoByUserId(userId);
    }
}
