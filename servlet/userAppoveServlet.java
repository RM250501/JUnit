/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;

/**
 * Servlet implementation class userAppoveServlet
 */
@WebServlet("/userAppoveServlet")
public class userAppoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public userAppoveServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		// 2. actionの判定（nullチェックを先に行う）
		if (action == null) {
		    action = "none"; // または空文字 "" など
		}

		
		String selected = request.getParameter("studentId");
		int userId = Integer.parseInt(selected);
		if (action.equals("approve")) {
			try {
				UserDao dao = new UserDao();
				User userInfo = dao.findById(userId);
				System.out.print(userInfo);
				userInfo.setStatusId(2);
				dao.updateUser(userInfo);
				List<User> UserList = dao.findAll();
				List<String> identId = new ArrayList<>();
				
				for (User user : UserList) {
					int id = user.getUserId();
					String Identity = dao.findIdentityImagePathByUserId(id);
					identId.add(Identity);
				}
				request.setAttribute("UserInfoList", UserList);
				request.setAttribute("identId", identId);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userList.jsp");
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		} else if (action.equals("reject")) {
			
			try {
				UserDao dao = new UserDao();
				List<User> UserList = dao.findAll();
				List<String> identId = new ArrayList<>();
				
				for (User user : UserList) {
					int id = user.getUserId();
					String Identity = dao.findIdentityImagePathByUserId(id);
					identId.add(Identity);
				}
				request.setAttribute("UserInfoList", UserList);
				request.setAttribute("identId", identId);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userList.jsp");
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
		} else {

			try {
				UserDao dao = new UserDao();
				User userInfo = dao.findById(userId);
				List<String> identId = new ArrayList<>();
				
				if (userInfo != null) {
					int id = userInfo.getUserId();
					String Identity = dao.findIdentityImagePathByUserId(id);
					identId.add(Identity);
				}
				System.out.print(userInfo);
				request.setAttribute("user", userInfo);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userApprove.jsp");
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

}

