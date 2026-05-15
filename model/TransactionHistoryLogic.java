/*
 * 処理概要: 業務ロジックを実行し、DAOやモデルを組み合わせてユースケース処理を行います。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package model;

import java.sql.SQLException;
import java.util.List;

import dao.TransactionDao;

public class TransactionHistoryLogic {

    public List<TransactionView> execute(int userId) throws SQLException {

        TransactionDao dao = new TransactionDao();
        return dao.findByRecipientUserId(userId);
    }
}

