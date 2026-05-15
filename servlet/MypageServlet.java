/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;
import java.io.IOException;
import java.sql.SQLException;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MypageLogic;
import model.User;

@WebServlet("/MypageServlet")
public class MypageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッション取得（なければ null）
        HttpSession session = request.getSession(false);
        User loginUser = (session != null)
                ? (User) session.getAttribute("loginUser")
                : null;

        // ① ログインチェック
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/user_Login.jsp");
            return;
        }

        // ② DAO生成
        UserDao dao;
        try {
            dao = new UserDao();
        } catch (SQLException e) {
            throw new ServletException("DB接続に失敗しました", e);
        }

        // ③ 本人確認画像パス取得
        String identityImagePath;
        try {
            identityImagePath = dao.findIdentityImagePathByUserId(loginUser.getUserId());
        } catch (SQLException e) {
            throw new ServletException("本人確認画像の取得に失敗しました", e);
        }

        request.setAttribute("identityImagePath", identityImagePath);

        // ④ マイページ用情報セット
        MypageLogic logic = new MypageLogic();
        logic.setMypageInfo(loginUser, request);

        // ⑤ 画面遷移
        request.getRequestDispatcher("/WEB-INF/jsp/Profile.jsp")
               .forward(request, response);
    }
}

