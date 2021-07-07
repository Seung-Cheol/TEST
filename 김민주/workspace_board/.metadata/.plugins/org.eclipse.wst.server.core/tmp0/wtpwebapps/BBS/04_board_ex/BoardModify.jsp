<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board_ex.model.*,board_ex.service.*" %>
<%@ page import="java.io.PrintWriter" %>

<%
	// 0. 넘겨받는 데이타의 한글 처리
	request.setCharacterEncoding("utf-8");
%>

<!-- 1. 이전 화면의 입력값을 넘겨받아 BoardRec 객체의 각 멤버변수로 지정 -->
<jsp:useBean id="rec" class="board_ex.model.BoardVO">
<jsp:setProperty name="rec" property="*"></jsp:setProperty>
</jsp:useBean>

<%
	// 2. Service에 update() 호출하여 레코드 수정
	int rs = ModifyArticleService.getInstance().update(rec);
// 	System.out.println(rec.getPass());	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판글수정</title>
</head>
<body>

<%  // 게시글 수정이 성공적으로 되었다면 그 해당 게시글을 보여주는 페이지로 이동하고
	int result = 0;
	if(rs == 1){
		response.sendRedirect("BoardList.jsp");
	}else{
    // 그렇지 않다면, "암호가 잘못 입력되었습니다"를 출력
			PrintWriter script = response.getWriter();
			script.println("<script>");
	 		script.println("alert('비밀번호를 확인해주세요')");
			script.println("history.back()");
			script.println("</script>");
	}
	
%>

</body>
</html>