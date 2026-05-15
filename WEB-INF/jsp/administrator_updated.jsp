<%--
  処理概要: 画面表示とユーザー操作の受付を行い、必要に応じてJavaScriptで表示制御します。
  主な処理コード: function setupRealTimeUpdate() / event onclick, addEventListener
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理者情報更新画面</title>
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/administrator_updated.css"> 
</head>
<body>
    
    <div class="container-wrapper">
        <jsp:include page="nav.jsp" />

        <main class="content-wrapper">
            <form action="AdminUpdateServlet" method="post" enctype="multipart/form-data" id="updateForm">
                
                <!-- 選択した管理者IDを隠しフィールドで送信 -->
                <input type="hidden" name="managerId" value="${admin.userId}">

                <div class="update-section">
                    <div class="update-form">
                        <h2>管理者情報更新</h2>
                        
                        <div class="form-row">
                            <label for="name">氏名:</label>
                            <input type="text" id="input-name" name="name" value="${admin.fullName}">
                        </div>
                        <div class="form-row">
                            <label for="managerId">管理者ID:</label>
                            <span id="input-managerId-display" class="display-value">${admin.userId}</span>
                        </div>
                        <div class="form-row">
                            <label for="role">権限:</label>
                            <input type="text" id="input-role" name="role" value="${admin.role}">
                        </div>
                        <div class="form-row">
                            <label for="password">パスワード:</label>
                            <input type="password" id="input-password" name="password" value="">
                        </div>
                        <div class="form-row file-upload">
                            <label for="userImage">本人画像 (選択):</label>
                            <input type="file" id="userImage" name="userImage" accept="image/*">
                        </div>
                        <div class="form-row file-upload">
                            <label for="studentCardImage">証明画像 (選択):</label>
                            <input type="file" id="studentCardImage" name="studentCardImage" accept="image/*">
                        </div>

                        <button type="submit" class="button update-button">更新</button>
                        <button type="button" class="button cancel-button" onclick="history.back()">中止</button>
                    </div>

                    <div class="update-comparison">
                        <div class="comparison-card1">
                            <h3>管理者情報(更新前)</h3>
                            <p>氏名: ${admin.fullName}</p>
                            <p>管理者ID: ${admin.userId}</p>
                            <p>権限: ${admin.role}</p>
                            <p>パスワード: ********</p>
                        </div>
                        
                        <div class="arrow">→</div>
                        
                        <div class="comparison-card1">
                            <h3>管理者情報(更新後)</h3>
                            <p>氏名: <span id="output-name"></span></p>
                            <p>管理者ID: <span id="output-managerId"></span></p>
                            <p>権限: <span id="output-role"></span></p>
                            <p>パスワード: <span id="output-password"></span></p>
                        </div>
                    </div>
                </div>
            </form>
            
            <div class="back-button-container">
             <form action="AdminServlet" method="post"><button class="button back-to-list-button">一覧に戻る</button></form>
            </div>
        </main>
    </div>

    <script>
        function setupRealTimeUpdate(inputId, outputId, isPassword = false) {
            const inputElement = document.getElementById(inputId);
            const outputElement = document.getElementById(outputId);
            if (!inputElement || !outputElement) return;

            // 初期値設定
            let initialValue = inputElement.value;
            outputElement.textContent = isPassword && initialValue.length > 0 ? '*'.repeat(initialValue.length) : initialValue;

            inputElement.addEventListener('input', function() {
                let displayValue = this.value;
                if (isPassword) {
                    displayValue = displayValue.length > 0 ? '*'.repeat(displayValue.length) : '';
                }
                outputElement.textContent = displayValue;
            });

            inputElement.dispatchEvent(new Event('input'));
        }

        document.addEventListener('DOMContentLoaded', function() {
            setupRealTimeUpdate('input-name', 'output-name');
            setupRealTimeUpdate('input-role', 'output-role');
            setupRealTimeUpdate('input-password', 'output-password', true);
            document.getElementById('output-managerId').textContent = document.getElementById('input-managerId-display').textContent;
        });
    </script>
</body>
</html>

