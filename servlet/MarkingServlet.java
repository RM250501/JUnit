/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MarkingLogic;
import model.User;

@WebServlet("/marking")
public class MarkingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        int userId = loginUser.getUserId();
        // ★ 複数チェック対応
        String[] provisionNumbers = request.getParameterValues("provisionNumber");

        MarkingLogic logic = new MarkingLogic();

        if (provisionNumbers != null) {
            for (String pNum : provisionNumbers) {
                int provisionNumber = Integer.parseInt(pNum);
                logic.registerSuggestion(userId, provisionNumber);
            }
        }

        response.sendRedirect(request.getContextPath() + "/TextsSearchServlet");
    }

}

