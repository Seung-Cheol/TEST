<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="board_ex.model.*, board_ex.service.*"%>
<%@ page import="java.util.List"%>

<%  //웹브라우저가 게시글 목록을 캐싱할 경우 새로운 글이 추가되더라도 새글이 목록에 안 보일 수 있기 때문에 설정
	response.setHeader("Pragma","No-cache");		// HTTP 1.0 version
	response.setHeader("Cache-Control","no-cache");	// HTTP 1.1 version
	response.setHeader("Cache-Control","no-store"); // 일부 파이어폭스 버스 관련
	response.setDateHeader("Expires", 1L);			// 현재 시간 이전으로 만료일을 지정함으로써 응답결과가 캐쉬되지 않도록 설정
%>

<%	
	// 한 페이지당 보여줄 페이지 개수
	int pageBlock = 5;	
	// 페이지 링크를 클릭한 번호 // 현재페이지
	String pNum = request.getParameter("page");
	if(pNum==null){
		pNum = "1";
	}
	// 현재페이지 인트형 // 파라매터로 보낼 변수
	int currentPage = Integer.parseInt(pNum); // 6
	
	// 리스트서비스객체생성
	ListArticleService service = ListArticleService.getInstance();
	
	//총 데이터 개수
	int count = BoardDao.getInstance().getTotalCount();
	
	// 총 페이지 수
	int pageCount = service.getTotalCount();
	
	
	List <BoardVO> mList = null;
	if(count>0){
		mList = service.getArticleList(currentPage);
	}
	
 	
	
%>

<HTML>
<head>
<title>게시글 목록</title>
<style>
	body > table {
		 width:1080px; 
		 height: 540px"
	}
	
	#Theader{
		text-align: center;
	}
</style>
</head>

<BODY>

	<h3>게시판 목록</h3>

	<table border="1" bordercolor="darkblue" >
		<tr id="Theader">
			<td>번호</td>
			<!-- 넘길거 -->
			<td>제 목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수</td>
		</tr>

		<% if( mList.isEmpty() ) { %>
		<tr>
			<td colspan="5">등록된 게시물이 없습니다.</td>
		</tr>
		<% } else { %>
		<!--  여기에 목록 출력하기  -->
		<% for(BoardVO vo : mList){ %>
		<tr>
			<td><%=vo.getSeq() %></td>
			<td><a href="BoardView.jsp?seq=<%=vo.getSeq()%>"><%=vo.getTitle() %></a></td>
			<!-- 클릭시 BoardView.jsp 로 이동  -->
			<td><%=vo.getWriter() %></td>
			<td><%=vo.getRegdate() %></td>
			<td><%=vo.getCnt() %></td>
		</tr>

		<% }  %>
		<!-- end for -->
		<%} %>
		<!-- end if -->
		<tr>
			<td colspan="5" style="align: right"><a
				href="BoardInputForm.jsp">글쓰기</a></td>
		</tr>
		<tr>
			<td colspan="5" align="center">
				<!-- 페이징 처리 --> 
		<% //	레코드 개수가 0보다 크면
				if(count>0){
			//	화면에 표시될 시작,끝 페이지 구하는 식
					int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
					int endPage = startPage+pageBlock-1;
			//	끝페이지가 총 페이지 개수보다 크다면 끝페이지가 총 개수페이지		
					if(endPage>pageCount){
						endPage=pageCount;
					}
			
					if(startPage>pageBlock){%>
					 <a href="BoardList.jsp?page=<%=startPage-pageBlock%>">[이전]</a>
				 <%}%> <!-- if end --> 
				<% for(int i = startPage; i<=endPage && i>=startPage; i++){ %>
						<a href="BoardList.jsp?page=<%=i%>"><%=i %></a> 
				<%} %><!-- for end -->
					<%if(pageCount>endPage){ %>
						<a href="BoardList.jsp?page=<%=startPage+pageBlock%>">[다음]</a>
					<%} %> <!-- end if -->
				
				
				<%} %><!-- top if end -->





			</td>
		</tr>
	</table>



</BODY>
</HTML>
