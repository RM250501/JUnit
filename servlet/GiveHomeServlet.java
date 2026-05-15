/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.GiveHomeLogic;
import model.ProvisionView;
import model.User;

@WebServlet("/GiveHomeServlet")
public class GiveHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションからログインユーザ取得
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        try {
            GiveHomeLogic logic = new GiveHomeLogic();
            List<ProvisionView> provisionList =
                    logic.getMyProvisionList(loginUser.getUserId());

            request.setAttribute("provisionList", provisionList);

        } catch (Exception e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/giveHome.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // POSTでもGETと同じ処理を行う
        doGet(request, response);
    }
}

