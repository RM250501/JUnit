/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.SearchResult;
import model.TextSearchLogic;

@WebServlet("/TextsSearchServlet")
public class TextsSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TextsSearchServlet() {
        super();
    }

    /** GET: 全件検索 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TextSearchLogic logic = new TextSearchLogic();
        SearchResult result = null;

        try {
            result = logic.execute(null); // 全件取得
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // result が null の場合は空の SearchResult を作る
        if (result == null) {
            result = new SearchResult();
        }

        // JSP が必要とする属性をすべてセット
        request.setAttribute("result", result);
        request.setAttribute("subjectList", result.getSubjectList() != null ? result.getSubjectList() : new ArrayList<>());
        request.setAttribute("textbookList", result.getTextbookList() != null ? result.getTextbookList() : new ArrayList<>());
        request.setAttribute("provisionList", result.getProvisionList() != null ? result.getProvisionList() : new ArrayList<>());

        request.getRequestDispatcher("WEB-INF/jsp/textsearch.jsp").forward(request, response);
    }



    /** POST: キーワード検索 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String keyword = request.getParameter("keyword");

        TextSearchLogic logic = new TextSearchLogic();
        SearchResult result = null;

        try {
            result = logic.execute(keyword); // キーワード検索
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // result が null の場合は空の SearchResult を作る
        if (result == null) {
            result = new SearchResult();
        }

        // JSP に渡す
        request.setAttribute("result", result);
        request.setAttribute("subjectList", result.getSubjectList() != null ? result.getSubjectList() : new ArrayList<>());
        request.setAttribute("textbookList", result.getTextbookList() != null ? result.getTextbookList() : new ArrayList<>());
        request.setAttribute("provisionList", result.getProvisionList() != null ? result.getProvisionList() : new ArrayList<>());

        request.getRequestDispatcher("WEB-INF/jsp/textsearch.jsp").forward(request, response);
    }

}

