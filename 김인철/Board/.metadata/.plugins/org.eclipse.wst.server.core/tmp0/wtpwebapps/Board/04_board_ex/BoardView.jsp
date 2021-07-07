<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="board_ex.service.*, board_ex.model.*" %>
<%
	// 게시글번호 넘겨받아
	String seq = request.getParameter("seq");
	// 서비스의 함수를 호출하여 해당 BoardVO를 넘겨받는다.
	ViewArticleService dao = ViewArticleService.getInstance();
	BoardVO vo = dao.getArticleByIds(seq);
	
	
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
        integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<style>
      .jb-xx-small { font-size: xx-small; }
      .jb-x-small { font-size: x-small; }
      .jb-small { font-size: small; }
      .jb-medium { font-size: medium; }
      .jb-large { font-size: large; }
      .jb-x-large { font-size: x-large; }
      .jb-xx-large { font-size: xx-large; }
    </style>
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
    <script>
$(function(){
var del = $("#del");
	
	del.css("cursor", "pointer").click(function(){
		var seq = $(this).attr('name');
		
		window.open("BoardDeleteForm.jsp?seq="+seq,"","width=550, height=350");
	});
})
</script>

<title> 게시글 보기 </title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container w-75 mt-5">
	<table class="table" >
	<thead>
	<tr >
		<th class="text-center" colspan="2"><span class="display-4"><%= vo.getTitle() %></span></th>
	</tr>
	</thead>
	<tr class="text-right no-border jb-x-small">
		<td colspan="2"> 작성자 : <%= vo.getWriter() %> | 작성일자 : <%= vo.getRegdate() %> </td>
	</tr>

	<tr style="height:300px">
		<td colspan="2" ><%= vo.getContent() %></td>
	</tr>
	<tr>
		<td colspan="2"> 조회수 : <%= vo.getCnt() %></td>
		
	</tr>
	<tr>
		<td colspan="2">
			<input type="button" value="목록보기" onclick="location.href='BoardList.jsp'" class="btn btn-warning float-right ml-4">
			<input type="button" value="수정하기" onclick="location.href='BoardModifyForm.jsp?seq=<%=seq%>'" class="btn btn-warning float-right ml-4">
			<input id="del" name=<%=seq%> type="button" value="삭제하기" class="btn btn-warning float-right ml-4">		
		</td>
	</tr>
	</table>
	</div>
<!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>

</body>
</html>