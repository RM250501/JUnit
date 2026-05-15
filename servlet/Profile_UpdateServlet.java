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

@WebServlet("/User_UpdateServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,       // 5MB
        maxRequestSize = 1024 * 1024 * 10   // 10MB
)
public class Profile_UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Profile_UpdateServlet() {
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
            String emailAddress = request.getParameter("emailAddress");
            String phoneNumber = request.getParameter("phoneNumber");

            /* ===============================
             * ② DB から既存データを取得
             * =============================== */
            UserDao dao = new UserDao();
            User oldUser = dao.findById(userId);
            if (oldUser == null) {
                throw new RuntimeException("該当ユーザーが存在しません (userId=" + userId + ")");
            }

            /* ===============================
             * ③ 画像アップロード処理（upload/userに保存）
             * =============================== */
            String uploadPath = getServletContext().getRealPath("upload/user");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs(); // 親フォルダも作成

            // 本人画像（usersPhoto）
            String newUsersPhoto = saveFile(request.getPart("userImage"), uploadPath);
            String usersPhoto = (newUsersPhoto != null) ? newUsersPhoto : oldUser.getUsersPhoto();

         // 証明画像（本人確認用）
            String newIdentityImage =
                    saveFile(request.getPart("studentCardImage"), uploadPath);

            if (newIdentityImage != null) {
                // user_identity_verification テーブルへ保存（承認待ち）
                dao.upsertIdentityImage(userId, newIdentityImage);
            }


            /* ===============================
             * ④ 未入力項目は既存値を使用
             * =============================== */
            if (fullName == null || fullName.isEmpty()) fullName = oldUser.getFullName();
            if (role == null || role.isEmpty()) role = oldUser.getRole();
            if (password == null || password.isEmpty()) password = oldUser.getPassword();
            if (emailAddress == null || emailAddress.isEmpty()) emailAddress = oldUser.getEmailAddress();
            if (phoneNumber == null || phoneNumber.isEmpty()) phoneNumber = oldUser.getPhoneNumber();

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

         // セッションの loginUser を更新
            request.getSession().setAttribute("loginUser", updatedUser);

            // 全ユーザーリストを JSP に渡す
            List<User> userList = dao.findAll();
            request.setAttribute("UserInfoList", userList);
            if (newIdentityImage != null) {
                request.setAttribute("identityImagePath", newIdentityImage);
            } else {
                // DBから最新の証明画像パスを取得してセットする処理
                 String currentPath = dao.findIdentityImagePathByUserId(userId); 
                 request.setAttribute("identityImagePath", currentPath);
            }

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/Profile.jsp");
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

        // DBに保存する値は upload/user/xxx.png の形式
        return "upload/user/" + fileName;
    }
}

