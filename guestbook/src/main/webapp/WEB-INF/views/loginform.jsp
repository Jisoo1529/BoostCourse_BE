<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<h1>������ �α���</h1>
<br><br>
${errorMessage}<br> <!-- flashmap���� �� ���� ��µǴ� ��ġ -->

<form method="post" action ="login">
	��ȣ: <input type="password" name="passwd"><br>
	<input type="submit">
</form>
</body>
</html>