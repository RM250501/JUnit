/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.TextTradeDao;
import model.TradePartnerView;
import model.User;

@WebServlet("/TransactionProviderservlet")
public class TransactionProviderservlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String to = request.getParameter("email");
		String subject = "教科書取引について";//件名
		String body = request.getParameter("body");//内容
		String encsubject = URLEncoder.encode(subject, StandardCharsets.UTF_8);
		String encbody = URLEncoder.encode(body, StandardCharsets.UTF_8);

		String mailto = "mailto:" + to + "?subject=" + encsubject + "&body=" + encbody;

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println("<script>");
		response.getWriter().println("window.location.href='" + mailto + "';");
		response.getWriter().println("setTimeout(function() { window.location.href = 'TransactionProviderservlet'; }, 2000);");
		response.getWriter().println("</script>");

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");

	    try {
	        // ★ ログインユーザ（provider）
	        User loginUser = (User) request.getSession().getAttribute("loginUser");
	        int providerId = loginUser.getUserId();

	        TextTradeDao dao = new TextTradeDao();
	        List<TradePartnerView> tradeList = dao.findOngoingByProvider(providerId);

	        request.setAttribute("tradeList", tradeList);
	        request.getRequestDispatcher("WEB-INF/jsp/Provider.jsp")
	               .forward(request, response);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        request.setAttribute("errorMessage", "データ取得エラー");
	        request.getRequestDispatcher("WEB-INF/jsp/Provider.jsp")
	               .forward(request, response);
	    }
	}

}

