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

import model.AdminSearchListLogic;
import model.User;

/**
 * Servlet implementation class AdminActionServlet
 */
@WebServlet("/AdminActionServlet")
public class AdminActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

	    String action = request.getParameter("action");
	    String selected = request.getParameter("selectedAdmin");

	    if (selected == null && !action.equals("register")) {
	        throw new ServletException("管理者が選択されていません");
	    }

	    if (action.equals("update")) {
	    	int userId = Integer.parseInt(selected);
	    	AdminSearchListLogic AdminSearchListLogic = new AdminSearchListLogic();
	    	User adminInfo = null;
			try {
				adminInfo = AdminSearchListLogic.getAdminById(userId);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			System.out.print(adminInfo);
	    	request.setAttribute("admin", adminInfo);
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/administrator_updated.jsp");
	    	dispatcher.forward(request, response);
	    }

	    else if (action.equals("delete")) {
	        int userId = Integer.parseInt(selected);
	        AdminSearchListLogic AdminSearchListLogic = new AdminSearchListLogic();
	    	User adminInfo = null;
			try {
				adminInfo = AdminSearchListLogic.getAdminById(userId);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			System.out.print(adminInfo);
	    	request.setAttribute("admin", adminInfo);
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/administrator_Delete.jsp");
	    	dispatcher.forward(request, response);
	    }

	    else if (action.equals("register")) {
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/administrator_registration.jsp");
	        dispatcher.forward(request, response);
	    }
	}

}

