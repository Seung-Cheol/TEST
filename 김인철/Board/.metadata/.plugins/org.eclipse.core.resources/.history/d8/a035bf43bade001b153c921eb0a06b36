<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board_ex.model.*,board_ex.service.*" %>

<%
	// 1. 삭제할 레코드의 게시글번호와 비밀번호를 넘겨받기
	String seq = request.getParameter("seq");
String password = request.getParameter("password");

	// 2. Service에 delete() 호출
	DeleteArticleService dao = DeleteArticleService.getInstance();
	
	int result =  dao.delete(seq, password); 
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
<title> 게시글 삭제 </title>
</head>
<body>
<!-- navbar------------------------------------------------------------------- -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark ml-auto mb-4" >
<div class="float-center">
    	<a class="navbar-brand text-warning float-center">게시물 삭제 </a>

</div>

      </nav>
 <!-- navbar------------------------------------------------------------------ -->


	<% if( result != 0) { %>
	<div class="container w-75 text-center mb-4">
	<span class="jb-x-large ">게시글이 삭제되었습니다.</span> <br/>
	<input class="w-100 btn btn-warning mt-4" type="button" value="창닫기" onClick="window.close()">
	
	</div>
	<% } else { %>
			<div class="container w-75 text-center mb-4">
	<span class="jb-x-large ">삭제에 실패하였습니다.</span> <br/>
		<input class="w-100 btn btn-warning mt-4" type="button" value="창닫기" onClick="window.close()">
	
	</div>
	<% } %>
	<br/><br/>
	
</body>
</html>