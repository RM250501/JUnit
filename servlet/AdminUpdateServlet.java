/*
 * 処理概要: ServletとしてHTTPリクエストを受け取り、入力値を解釈して業務ロジックを呼び出し、遷移先を決定します。
 * 主な処理コード: クラス内のメソッド実装（doGet/doPost/execute系処理など）
 */
package servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import dao.UserDao;
import model.User;

@WebServlet("/AdminUpdateServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,       // 5MB
        maxRequestSize = 1024 * 1024 * 10   // 10MB
)
public class AdminUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminUpdateServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            /* ===============================
             * ① パラメータ取得
             * =============================== */
            String idStr = request.getParameter("managerId");
            if (idStr == null || idStr.isEmpty()) {
                throw new RuntimeException("managerId が送信されていません。");
            }
            int userId = Integer.parseInt(idStr);

            String fullName = request.getParameter("name");
            String role = request.getParameter("role");
            String password = request.getParameter("password");

            /* ===============================
             * ② DB から既存データを取得
             * =============================== */
            UserDao dao = new UserDao();
            User oldUser = dao.findById(userId);
            if (oldUser == null) {
                throw new RuntimeException("該当ユーザーが存在しません (userId=" + userId + ")");
            }

            /* ===============================
             * ③ 画像アップロード処理
             * =============================== */
            String uploadPath = getServletContext().getRealPath("upload");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            // 本人画像（usersPhoto）
            String newUsersPhoto = saveFile(request.getPart("userImage"), uploadPath);
            String usersPhoto = (newUsersPhoto != null) ? newUsersPhoto : oldUser.getUsersPhoto();

            // 証明画像（studentCardImage）は保存のみ、DBには入れない
            saveFile(request.getPart("studentCardImage"), uploadPath);

            /* ===============================
             * ④ 未入力項目は既存値を使用
             * =============================== */
            if (fullName == null || fullName.isEmpty()) fullName = oldUser.getFullName();
            if (role == null || role.isEmpty()) role = oldUser.getRole();
            if (password == null || password.isEmpty()) password = oldUser.getPassword();

            String emailAddress = oldUser.getEmailAddress();
            String phoneNumber = oldUser.getPhoneNumber();
            int statusId = oldUser.getStatusId();

            /* ===============================
             * ⑤ 更新用 User オブジェクト生成
             * =============================== */
            User updatedUser = new User(
                    userId,
                    password,
                    role,
                    fullName,
                    emailAddress,
                    phoneNumber,
                    usersPhoto,
                    statusId
            );

            /* ===============================
             * ⑥ DB 更新
             * =============================== */
            dao.updateUser(updatedUser);

            /* ===============================
             * ⑦ 更新後の全管理者リストを取得して JSP に渡す
             * =============================== */
            List<User> adminList = dao.findAll();
            request.setAttribute("AdminInfoList", adminList);

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/administrator_List.jsp");
            rd.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("DB 更新中にエラーが発生しました");
        }
    }

    /** ファイルアップロード補助メソッド */
    private String saveFile(Part part, String uploadPath) throws IOException {
        if (part == null || part.getSubmittedFileName() == null || part.getSubmittedFileName().isEmpty()) {
            return null; // 未選択
        }

        String fileName = System.currentTimeMillis() + "_" + part.getSubmittedFileName();
        part.write(uploadPath + File.separator + fileName);
        return fileName;
    }
}


