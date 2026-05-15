/*
 * 処理概要: 業務ロジックを実行し、DAOやモデルを組み合わせてユースケース処理を行います。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.ProvisionDao;
import dao.SubjectDao;
import dao.TextDao;

public class TextSearchLogic {

    public SearchResult execute(String keyword) throws SQLException {
        SearchResult result = new SearchResult();

        SubjectDao sDao = new SubjectDao();
        TextDao tDao = new TextDao();
        ProvisionDao pDao = new ProvisionDao();

        List<Provision> provisions;
        if (keyword == null || keyword.isBlank()) {
            provisions = pDao.findAll();
        } else {
            provisions = pDao.searchByKeyword(keyword);
        }
        if (provisions == null) provisions = new ArrayList<>();

        // Provisionに紐づく科目を重複なしで取得
        Map<Integer, Subject> subjectMap = new LinkedHashMap<>();
        List<Textbook> textbooks = new ArrayList<>();

        for (Provision p : provisions) {
            int subjectId = p.getSubjectsNumber();
            if (!subjectMap.containsKey(subjectId)) {
                Subject s = sDao.findById(subjectId);
                if (s != null) subjectMap.put(subjectId, s);
            }

            Textbook t = tDao.findById(p.getTextbookNumber());
            if (t != null && !textbooks.contains(t)) textbooks.add(t);
        }

        result.setProvisionList(provisions);
        result.setSubjectList(new ArrayList<>(subjectMap.values())); // 重複なし科目
        result.setTextbookList(textbooks);

        return result;
    }
}

