/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.ProvisionDao;
import dao.SuggestedInfoDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Textinfodelete
 */
@WebServlet("/Textinfodelete")
public class Textinfodelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Textinfodelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String provision_id = request.getParameter("selectedId");
		if (provision_id == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("GiveHomeServlet");
	    	dispatcher.forward(request, response);
		}
		int pronumber = Integer.parseInt(provision_id);
		
		try {
			ProvisionDao prodao = new ProvisionDao();
			SuggestedInfoDao sugdao = new SuggestedInfoDao();
			prodao.delete(pronumber);
			sugdao.deleteByprovider_id(pronumber);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			
			
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("GiveHomeServlet");
    	dispatcher.forward(request, response);
	}
}

