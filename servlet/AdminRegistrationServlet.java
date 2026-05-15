/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: destroy()
 */
package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import model.AdminListRegistrationLogic;
import model.User;

@WebServlet("/AdminRegistrationServlet")
@MultipartConfig
public class AdminRegistrationServlet extends HttpServlet {

	private AdminListRegistrationLogic userLogic;

	@Override
	public void init() throws ServletException {
		try {
			userLogic = new AdminListRegistrationLogic();
		} catch (SQLException e) {
			throw new ServletException("DB接続エラー", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String fullName = request.getParameter("namae");
		String role = request.getParameter("role");
		String emailAddress = request.getParameter("emailAddress");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");

		// 画像処理
		Part imagePart = request.getPart("userImage");
		String fileName = Paths.get(imagePart.getSubmittedFileName())
				.getFileName().toString();

		String uploadDir = getServletContext().getRealPath("/upload/user");
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String savePath = uploadDir + File.separator + fileName;
		imagePart.write(savePath);

		// DBに保存するパス
		String imagePath = "upload/user/" + fileName;

		User user = new User();
		user.setFullName(fullName);
		user.setRole(role);
		user.setEmailAddress(emailAddress);
		user.setPassword(password);
		user.setPhoneNumber(phoneNumber);
		user.setUsersPhoto(imagePath);

		boolean registrationSuccess = userLogic.registerManager(user);

		if (registrationSuccess) {
			response.sendRedirect("AdminServlet");
		} else {
			response.sendRedirect("WEB-INF/jsp/administrator_registration.jsp");
		}
	}

	@Override
	public void destroy() {
		// リソースのクリーンアップ（特にDB接続のクローズ処理）
		try {
			if (userLogic != null) {
				userLogic = null; // Logicのクリーンアップ（自動で不要なリソースが解放されることが多い）
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

