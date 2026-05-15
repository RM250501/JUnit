/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.AdminListDeleteLogic;
import model.User;

@WebServlet("/AdminDeleteServlet")
public class AdminDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminDeleteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String userIdStr = request.getParameter("userId");
            if (userIdStr == null || userIdStr.isEmpty()) {
                throw new ServletException("管理者IDが指定されていません");
            }

            int userId = Integer.parseInt(userIdStr);

            AdminListDeleteLogic logic = new AdminListDeleteLogic();
            User admin = logic.findById(userId);

            if (admin == null) {
                throw new ServletException("指定された管理者は存在しません");
            }

            request.setAttribute("admin", admin);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/administrator_Delete.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("DB接続中にエラーが発生しました", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String idStr = request.getParameter("managerId");
            if (idStr == null || idStr.isEmpty()) {
                throw new ServletException("削除対象の管理者IDが送信されていません");
            }
            int userId = Integer.parseInt(idStr);

            AdminListDeleteLogic logic = new AdminListDeleteLogic();
            logic.delete(userId);

            List<User> adminList = logic.findAll();
            request.setAttribute("AdminInfoList", adminList);

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/administrator_List.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("DB削除中にエラーが発生しました", e);
        }
    }
}



