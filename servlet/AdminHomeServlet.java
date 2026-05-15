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

import model.User;

@WebServlet("/AdminHomeServlet")
public class AdminHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/User_LoginServlet");
            return;
        }

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/User_LoginServlet");
            return;
        }

        if (!loginUser.isAdmin()) {
            session.setAttribute("errorMsg", "管理者権限がありません。");
            response.sendRedirect(request.getContextPath() + "/User_LoginServlet");
            return;
        }
        // 管理者ホームJSPへ遷移
        request.getRequestDispatcher("WEB-INF/jsp/admini.jsp")
               .forward(request, response);
    }
}

