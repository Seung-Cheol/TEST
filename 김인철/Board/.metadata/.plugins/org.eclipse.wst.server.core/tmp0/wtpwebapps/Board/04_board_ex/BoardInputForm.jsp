<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Required meta tags -->
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">
<title>게시판 글쓰기</title>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		// 작성 버튼이 눌렸을 때
		$('input[value="작성"]').click(function() {
			var frm = $('form[name="frm"]');
			// 폼태그의 action 속성을 'BoardSave.jsp'
			frm.attr('action', 'BoardSave.jsp');
			// 폼태그의 submit() 호출
			frm.submit();

			// 각 input 태그에 name 부여
		});
	});
</script>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="container w-75 mt-5">
		<form name='frm' method='post'>
			<table class="table">
				<thead>
					<tr>
						<th class="text-center" colspan="2"><span class="display-4">글
								작성하기</span></th>
					</tr>
				</thead>
				<tbody>
					<tr class="no-border">
						<td colspan="2"><span class="text-center">작성자  </span> <input class=" w-75 float-right" type='text' name='writer'></td>
					</tr>
					<tr class="no-border">
						<td colspan="2"><span class="text-center">제목  </span><input class=" w-75 float-right" type='text' name='title'></td>
					</tr>

					<tr style="height: 300px">
						<td colspan="2"><textarea  style="height: 300px" class="w-100" name='content'></textarea></td>
					</tr>
					<tr>
						<td colspan="2" class="float-right">비밀번호 : <input type='password' name='pass'></td>

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