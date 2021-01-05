<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- bootstrap_cdn.jsp -->
<%@ include file="include/bootstrap_cdn.jsp" %>
<title>Model2 게시판 - 글 작성</title>
</head>
<body>
<div class="container-fluid">

<!-- paging_form -->
<%@ include file="include/paging_form.jsp" %>

	<!-- wirite -->
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8">
			<form role="form" id="frmWrite" action="/writeRun.my" method="post">
				<div class="form-group">
					<label for="b_title" style="float:left;">제목</label> 
					<input type="text" class="form-control" id="b_title" name="b_title"/>
				</div>
				<div class="form-group">
					<label for="m_id" style="float:left;">작성자</label> 
					<input type="text" class="form-control" id="m_id" name="m_id"/>
				</div>
				<div class="form-group">
					<label for="b_content">내용</label> 
					<textarea class="form-control" id="b_content" name="b_content"></textarea>
				</div>
				<a id="btnList" type="button" class="btn btn-outline-success btn-sm" 
					href="/list.my">목록</a>
				<button id="btnWrite" type="submit" class="btn btn-primary btn-sm">작성완료</button>
			</form>
		</div>
		<div class="col-md-2"></div>
	</div>
	<!--// wirite -->
	
</div>
</body>
<script type="text/javascript">
$(function() {
	
	$("#btnList").click(function(e){
		e.preventDefault();
		var href=$(this).attr("href");
		$("#frmPaging").attr("action", href);
		$("#frmPaging").submit();
	});
	
});
</script>
</html>