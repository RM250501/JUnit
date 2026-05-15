/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.ProfileDeleteLogic;
import model.User;

@WebServlet("/Profile_DeleteServlet")
public class Profile_DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッション取得
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 削除処理
        ProfileDeleteLogic logic = null;
		try {
			logic = new ProfileDeleteLogic();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        boolean result = false;
		try {
			result = logic.delete(loginUser.getUserId());
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

        if (result) {
            // セッション破棄（重要）
            session.invalidate();
            response.sendRedirect("User_LoginServlet");
        } else {
            request.setAttribute("errorMsg", "プロフィールの削除に失敗しました");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }
}

