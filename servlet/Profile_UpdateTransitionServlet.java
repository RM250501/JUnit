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

import model.User; // 実際のUserクラスのパッケージ名に合わせてください

@WebServlet("/Profile_UpdateTransitionServlet")
public class Profile_UpdateTransitionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. セッションから現在のログイン情報を取得
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // 2. ログインチェック（未ログインならログイン画面へ戻す）
        if (loginUser == null) {
            response.sendRedirect("WEB-INF/jsp/user_Login.jsp");
            return;
        }

        // 3. 更新画面で使うためにリクエストスコープにセット
        // (セッションの値を直接使っても良いですが、リクエストに詰め直すのが一般的です)
        request.setAttribute("loginUser", loginUser);

        // 4. 更新用JSPへフォワード
        request.getRequestDispatcher("WEB-INF/jsp/ProfileUpdate.jsp").forward(request, response);
    }
}
