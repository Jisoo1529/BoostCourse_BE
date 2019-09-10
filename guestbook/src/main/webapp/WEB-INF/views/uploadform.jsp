<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>uploadform</title>
</head>
<body>
	<h1>Upload</h1>
	<br>
	<br>
	<form method = "post" action = "upload" enctype = "multipart/form-data">
		file:<input type = "file" name = "file"><br> <input type = "submit">
	</form>
</body>
</html>