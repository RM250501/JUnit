/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.LoginLogic;
import model.User;

@WebServlet("/User_LoginServlet")
public class User_LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ログイン画面へフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_Login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("full_name");
        String password = request.getParameter("password");

        LoginLogic loginLogic;
        try {
            loginLogic = new LoginLogic();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        try {
            User loginUser = loginLogic.execute(fullName, password);

            if (loginUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", loginUser);

                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/Home.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("loginError", "氏名またはパスワードが違います");
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/user_Login.jsp");
                dispatcher.forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

