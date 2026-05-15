/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DbConnect;
import dao.TextTradeDao;

@WebServlet("/Providerconpservlet")
public class Providerconpservlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String tradeSelect = req.getParameter("tradeSelect");
        if (tradeSelect == null) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "取引が選択されていません");
            return;
        }

        int tradingNumber;
        try {
            tradingNumber = Integer.parseInt(tradeSelect);
        } catch (NumberFormatException e) {
            throw new ServletException("不正な取引番号です", e);
        }

        try (Connection conn = DbConnect.getConnection()) {

            TextTradeDao tradeDao = new TextTradeDao();

            boolean updated = tradeDao.completeTrade(tradingNumber);

            if (!updated) {
                res.sendError(HttpServletResponse.SC_CONFLICT, "取引を完了できませんでした");
                return;
            }

            // 完了後は一覧に戻す
            res.sendRedirect("TransactionProviderservlet");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

