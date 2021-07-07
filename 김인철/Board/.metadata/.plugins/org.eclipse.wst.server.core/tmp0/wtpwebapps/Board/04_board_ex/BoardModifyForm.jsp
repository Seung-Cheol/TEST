<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board_ex.service.*, board_ex.model.*" %>
<%
// 1. 수정할 레코드의 게시글번호를 넘거받기
	String seq = request.getParameter("seq");
	// 2. Service에 getArticleById()함수를 호출하여 그 게시글번호의 레코드를 검색
	ViewArticleService view = ViewArticleService.getInstance();
	BoardVO rec = view.getArticleById(seq);
	
%>   
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<title>게시글 수정하기</title>
</head>
 <body>

<jsp:include page="navbar.jsp" />
	<div class="container w-75 mt-5">
		<form name='frm' method='post' action="BoardModify.jsp?seq=<%=seq%>">
			<table class="table">
				<thead>
					<tr>
						<th class="text-center" colspan="2"><span class="display-4">글
								수정하기</span></th>
					</tr>
				</thead>
				<tbody>

					<tr class="no-border">
						<td colspan="2"><span class="text-center">제목  </span><input value=<%= rec.getTitle() %> class=" w-75 float-right" type='text' name='title'></td>
					</tr>

					<tr style="height: 300px">
						<td colspan="2"><textarea  style="height: 300px" class="w-100" name='content'><%=rec.getContent() %></textarea></td>
					</tr>
					<tr>
						<td colspan="2" class="float-right">비밀번호 : <input value=<%=rec.getPass() %> type='password' name='pass'></td>

					</tr>
					<tr>
						<td colspan="2">
						<input class="btn btn-warning float-right ml-4" type='reset' value='초기화'>
						<input class="btn btn-warning float-right ml-4" type='submit' value='작성'> 
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>







</body>
</html>