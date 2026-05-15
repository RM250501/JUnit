/*
 * 処理概要: ドメインデータの保持・受け渡しを行うモデルクラスです。
 * 主な処理コード: getSubjectList(), setSubjectList(), getTextbookList(), setTextbookList()
 */
package model;

import java.util.List;

public class SearchResult {

    private List<Subject> subjectList;
    private List<Textbook> textbookList;
    private List<SubjectTextbook> relationList;
    private List<Provision> provisionList; // 追加

    // 既存の getter/setter
    public List<Subject> getSubjectList() { return subjectList; }
    public void setSubjectList(List<Subject> subjectList) { this.subjectList = subjectList; }

    public List<Textbook> getTextbookList() { return textbookList; }
    public void setTextbookList(List<Textbook> textbookList) { this.textbookList = textbookList; }

    public List<SubjectTextbook> getRelationList() { return relationList; }
    public void setRelationList(List<SubjectTextbook> relationList) { this.relationList = relationList; }

    // Provision 用 getter/setter 追加
    public List<Provision> getProvisionList() { return provisionList; }
    public void setProvisionList(List<Provision> provisionList) { this.provisionList = provisionList; }
}

