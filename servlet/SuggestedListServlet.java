/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.SuggestedListLogic;
import model.SuggestedTextbookView;
import model.User;

@WebServlet("/SuggestedListServlet")
public class SuggestedListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッション取得
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        int userId = loginUser.getUserId(); 

        SuggestedListLogic logic = new SuggestedListLogic();
        List<SuggestedTextbookView> result;

        try {
            result = logic.execute(userId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        request.setAttribute("suggestedList", result);
        request.getRequestDispatcher("WEB-INF/jsp/markedTextbook.jsp")
               .forward(request, response);
    }
}

