/*
 * 処理概要: ドメインデータの保持・受け渡しを行うモデルクラスです。
 * 主な処理コード: getSubjectsNumber(), setSubjectsNumber(), getTextbookNumber(), setTextbookNumber()
 */
package model;

public class SubjectTextbook {
    private int subjectsNumber;
    private int textbookNumber;

    public int getSubjectsNumber() { return subjectsNumber; }
    public void setSubjectsNumber(int subjectsNumber) { this.subjectsNumber = subjectsNumber; }

    public int getTextbookNumber() { return textbookNumber; }
    public void setTextbookNumber(int textbookNumber) { this.textbookNumber = textbookNumber; }
}

