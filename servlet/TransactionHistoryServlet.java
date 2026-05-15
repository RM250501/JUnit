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

import model.TransactionHistoryLogic;
import model.TransactionView;
import model.User;

@WebServlet("/transactionHistory")
public class TransactionHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // ログインチェック
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "WEB-INF/jsp/login.jsp");
            return;
        }

        try {
            TransactionHistoryLogic logic = new TransactionHistoryLogic();
            List<TransactionView> transactionList =
                    logic.execute(loginUser.getUserId());

            //System.out.print(loginUser.getUserId());
            request.setAttribute("transactionList", transactionList);

            //System.out.println(transactionList);
            
            request.getRequestDispatcher(
                    "WEB-INF/jsp/transactionHistory.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

