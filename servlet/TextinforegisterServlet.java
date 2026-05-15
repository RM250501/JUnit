/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.ProvisionDao;
import dao.SubjectDao;
import dao.TextDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Provision;
import model.Subject;
import model.Textbook;
import model.User;

/**
 * Servlet implementation class TextinforegisterServlet
 */
@WebServlet("/TextinforegisterServlet")
@MultipartConfig 
public class TextinforegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TextinforegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect("LoginServlet");
            return;
        }
		// TODO Auto-generated method stub
		try {
			TextDao text = new TextDao();
			SubjectDao sub = new SubjectDao();
			List<Textbook> textbook = text.findAll();
			List<Subject> subject = sub.findAll();
			request.setAttribute("Textbook", textbook);
			request.setAttribute("Subject", subject);
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/textinforegister.jsp");
	    	dispatcher.forward(request, response);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Provision pi = new Provision();
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("loginUser");
        if (user != null) {
        	pi.setUserId(user.getUserId()); 
        }
        System.out.println("登録するユーザーID: " + pi.getUserId());
        String bookNameStr = request.getParameter("textbooknumber");
        if (bookNameStr != null && !bookNameStr.isEmpty()) {
            pi.setTextbookNumber(Integer.parseInt(bookNameStr));
        }

        String subjectStr = request.getParameter("subjectnumber");
        System.out.println(subjectStr);
        if (subjectStr != null && !subjectStr.isEmpty()) {
            pi.setSubjectsNumber(Integer.parseInt(subjectStr));
        }

        String detail = request.getParameter("condition"); 
        pi.setTextbookDetail(detail);
        
        // ------------------
        String photo =request.getParameter("textbookphoto");
        pi.setTextbookPhoto(photo);
        ProvisionDao pro;
		try {
			pro = new ProvisionDao();
			pro.insert(pi);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("GiveHomeServlet");
    	dispatcher.forward(request, response);
        
	}

}

