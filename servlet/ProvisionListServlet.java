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
import model.ProvisionListLogic;
import model.ProvisionView;
import model.User;

@WebServlet("/provisionList")
public class ProvisionListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login"); // パスは環境に合わせて調整
            return;
        }

        try {
            ProvisionListLogic logic = new ProvisionListLogic();
            List<ProvisionView> provisionList = logic.execute(loginUser.getUserId());

            // JSPに渡す名前を "provisionList" で確定
            request.setAttribute("provisionList", provisionList);

            // デバッグ用：中身が空でないか確認
            //System.out.println("取得件数: " + (provisionList != null ? provisionList.size() : "null"));

            request.getRequestDispatcher("WEB-INF/jsp/provisionList.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

