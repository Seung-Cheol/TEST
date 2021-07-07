<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="board_ex.model.*, board_ex.service.*" %>
<%@ page import="java.util.*" %>

<%  //웹브라우저가 게시글 목록을 캐싱할 경우 새로운 글이 추가되더라도 새글이 목록에 안 보일 수 있기 때문에 설정
	response.setHeader("Pragma","No-cache");		// HTTP 1.0 version
	response.setHeader("Cache-Control","no-cache");	// HTTP 1.1 version
	response.setHeader("Cache-Control","no-store"); // 일부 파이어폭스 버스 관련
	response.setDateHeader("Expires", 1L);			// 현재 시간 이전으로 만료일을 지정함으로써 응답결과가 캐쉬되지 않도록 설정
%>

<%
ListArticleService service = ListArticleService.getInstance();

// 페이지 별 메세지 레코드 검색
int start=0;
int length=5;
int group=5;
int seq = (request.getParameter("seq")==null) ? 0 : Integer.parseInt(request.getParameter("seq"));
start = (request.getParameter("seq")==null) ? 0 : length*Integer.parseInt(request.getParameter("seq"));

int totalCount = service.getTotalCount();



// 화면에 띄울 페이지 번호 수 출력
int pagenum = (totalCount%length==0) ? totalCount/length : totalCount/length+1;

// 그룹 개수
int headnum = (pagenum%group==0) ? pagenum/group-1 : pagenum/group;


ArrayList<BoardVO> mList =  service.getPageList(start, length);

 int pageHeader = (seq%group!=0 || (request.getParameter("seq")==null) || seq==0 ) ? seq/group : seq/group-1;

%>
 <script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script>
$(function(){
	
var pg = $(".pg");
	pg.css("cursor","pointer").click(function(){
		var seq = $(this).attr('id');
		location.href='BoardList.jsp?seq='+seq;
	})

var pre = $(".pre");
var next = $(".next");

next.css("cursor","pointer").click(function(){
	var header = $(this).attr('id');
	location.href='BoardList.jsp?seq='+header*5;
})

pre.css("cursor","pointer").click(function(){
	var header = $(this).attr('id');
	location.href='BoardList.jsp?seq='+header*5;
})
	

	
})
</script>
	
<HTML>
<head>
<!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
        integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">


<title> 게시글 목록 </title>
</head>

<BODY>

<jsp:include page="navbar.jsp"/>
<div class="mt-5 ml-5 mr-5" > 

	<table class="table table-hover table-bordered " >
	<thead>
	<tr class="text-center">
		<th> 글번호 </th>
		<th> 제 목 </th>
		<th> 작성자 </th>
		<th> 작성일 </th>
		<th> 조회수 </th>
	</tr>
	</thead>
	<tbody>
	<% if( mList.isEmpty() ) { %>
		<tr><td colspan="5"> 등록된 게시물이 없습니다. </td></tr>
	<% } else { %>
	<%
		for(int i=0;i<mList.size();i++)
		{
			%>
			<tr>
			<td class="text-center"><%=mList.get(i).getSeq() %></td>
			<td><a href="BoardView.jsp?seq=<%=mList.get(i).getSeq() %>"><%=mList.get(i).getTitle() %></a></td>
			<td class="text-center"><%=mList.get(i).getWriter() %></td>
			<td class="text-center"><%=mList.get(i).getRegdate() %></td>
			<td class="text-center"><%=mList.get(i).getCnt() %></td>
			<%
		}
	
	%>
	
	<% }  %>
	</tbody>	
	</table>
	<nav aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
  <%	if(seq==0){
	  %>
	  <li class="page-item disabled">
      <a id="<%=pageHeader%>" class="page-link pre">Previous</a>
    </li>
	  <%
	  
  }else{
	  %>
	  <li class="page-item">
      <a id="<%=pageHeader%>" class="page-link pre">Previous</a>
    </li>
	  <%
  }
	  %>
 
    
    <% for(int i=group*(seq/group);i<group*(seq/group)+pagenum && i<group*(seq/group+1);i++){
    	
    	if(i<pagenum){
    	%>
    	  <li class="page-item"><a class="pg page-link" id=<%=i %>><%= (i+1) %></a></li>
    	<%
    	}
    }
    	%>
  

	  

  <% if(seq/5==headnum){
	  %>
	  
	  <li class="page-item disabled">
      <a id="<%=pageHeader+1%>" class="page-link next" >Next</a>
    </li>
	  <% 
  }else{
	  %>
	  <li class="page-item">
      <a id="<%=pageHeader+1%>" class="page-link next" >Next</a>
    </li>
	  <%
  }
	  %>
	  
    
    
  </ul>
</nav>
	
	
	
	
	
	<input type="button" value="글쓰기" onclick="location.href='BoardInputForm.jsp'" class="btn btn-warning float-right">
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
</BODY>
</HTML>
