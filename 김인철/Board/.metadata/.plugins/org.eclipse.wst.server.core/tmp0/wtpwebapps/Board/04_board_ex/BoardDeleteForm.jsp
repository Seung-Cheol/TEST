<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board_ex.service.*, board_ex.model.*" %>
<%
String seq = request.getParameter("seq");
	
%>      
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
	<style>
      .jb-xx-small { font-size: xx-small; }
      .jb-x-small { font-size: x-small; }
      .jb-small { font-size: small; }
      .jb-medium { font-size: medium; }
      .jb-large { font-size: large; }
      .jb-x-large { font-size: x-large; }
      .jb-xx-large { font-size: xx-large; }
    </style>
<title> 게시글 삭제하기 </title>
</head>
<body>
<!-- navbar------------------------------------------------------------------- -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark ml-auto mb-4" >
<div class="float-center">
    	<a class="navbar-brand text-warning float-center">게시물 삭제 </a>

</div>

      </nav>
 <!-- navbar------------------------------------------------------------------ -->
<form method="post" action="BoardDelete.jsp">
<div class="container w-75">
	<span class="jb-x-large ">해당 게시글의 암호를 입력하세요.</span> <br/>
	<input class="w-100 text-center mb-4" type="password" name="password">
	<!-- 2. 게시글번호를 다음 페이지로 넘기기 위해 hidden 태그로 지정 -->
	<input type="hidden" name="seq" value=<%=seq %>>
	<hr>
	<input class=" w-100 btn btn-warning" type="submit" value="삭제하기">
	</div>
</form>

</body>
</html>