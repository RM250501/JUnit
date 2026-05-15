<%--
  処理概要: 画面表示とユーザー操作の受付を行い、必要に応じてJavaScriptで表示制御します。
  主な処理コード: function toggleActionButtons() / event onclick
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者情報一覧画面</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/Administrator_List.css">
    <script>
        // 管理者選択時にボタンを有効化/無効化
        function toggleActionButtons() {
            const selectedAdmin = document.querySelector('input[name="selectedAdmin"]:checked');
            const updateButton = document.getElementById('updateButton');
            const deleteButton = document.getElementById('deleteButton');

            // ラジオボタンが選択されている場合にボタンを有効化
            if (selectedAdmin) {
                updateButton.disabled = false;
                deleteButton.disabled = false;
            } else {
                updateButton.disabled = true;
                deleteButton.disabled = true;
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <jsp:include page="nav.jsp" />

        <h3>管理者一覧</h3>

        <!-- 管理者一覧テーブル -->
        <form action="AdminActionServlet" method="post">
            <table>
                <thead>
                    <tr>
                        <th>選択</th>
                        <th>管理者名</th>
                        <th>管理者ID</th>
                        <th>メールアドレス</th>
                        <th>電話番号</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- JSTLやfor文で繰り返し表示する部分 -->
                    <% List<User> list = (List<User>) request.getAttribute("AdminInfoList");
                       if (list != null) {
                           for (User admin : list) {
                               if ("admin".equals(admin.getRole())) {
                    %>
                            <tr>
                                <td><input type="radio" name="selectedAdmin" value="<%= admin.getUserId() %>" onclick="toggleActionButtons()"></td>
                                <td><%= admin.getFullName() %></td>
                                <td><%= admin.getUserId() %></td>
                                <td><%= admin.getEmailAddress() %></td>
                                <td><%= admin.getPhoneNumber() %></td>
                            </tr>
                    <%       }
                           }  
                       } %>
                </tbody>
            </table>

            <!-- ボタン -->
            <div class="button-area">
                <button type="submit" id="registerButton" name="action" value="register">登録</button>
                <button type="submit" id="updateButton" name="action" value="update" disabled>更新</button>
                <button type="submit" id="deleteButton" name="action" value="delete" disabled>削除</button>
            </div>
        </form>
    </div>
</body>
</html>

