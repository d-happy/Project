<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- bootstrap_cdn.jsp -->
<%@ include file="include/bootstrap_cdn.jsp" %>
<title>Model2 게시판 - 글 상세보기</title>
</head>
<body>
<div class="container-fluid">

<!-- paging_form -->
<%@ include file="include/paging_form.jsp" %>

	<!-- content -->
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<form role="form" id="frmContent">
				<div class="form-group">
					<label for="b_title" style="float:left;">제목</label> 
					<input type="text" class="form-control" id="b_title" name="b_title" 
						value="${boardVo.b_title}" readonly/>
				</div>
				<div class="form-group">
					<label for="m_id" style="float:left;">작성자</label> 
					<input type="text" class="form-control" id="m_id" name="m_id" 
						value="${boardVo.m_id}"readonly/>
				</div>
				<div class="form-group">
					<label for="b_date" style="float:left;">날짜</label> 
					<input type="text" class="form-control" id="b_date" name="b_date" 
						value="${boardVo.b_date}" readonly/>
				</div>
				<div class="form-group">
					<label for="b_content">내용</label> 
					<textarea class="form-control" id="b_content" name="b_content" 
						readonly>${boardVo.b_content}</textarea>
				</div>
				<a id="btnList" type="button" class="btn btn-outline-success btn-sm" 
					href="/list.my">목록</a>
				<button id="btnUpdate" type="button" class="btn btn-warning btn-sm">수정</button>
				<button id="btnUpdateFinish" type="button" class="btn btn-warning btn-sm"
					style="display:none;">수정완료</button>
				<button id="btnDelete" type="button" class="btn btn-danger btn-sm">삭제</button>
			</form>
		</div>
		<div class="col-md-3"></div>
	</div>
	<!-- //content -->
	
</div>
</body>
<script type="text/javascript">
$(function() {
	
	var message = "${sessionScope.message}";
	if (message == "update_success") {
		alert("수정 완료");
	}
	
	$("#btnList").click(function(e){
		e.preventDefault();
		var href=$(this).attr("href");
		$("#frmPaging > input[name='b_no']").remove();
		$("#frmPaging").attr("action", href);
		$("#frmPaging").submit();
	});
	
	$("#btnUpdate").click(function() {
		$(this).hide();
		$("#btnUpdateFinish").show();
		$("#b_title").attr("readonly", false);
		$("#b_content").attr("readonly", false);
	});
	
	$("#btnUpdateFinish").click(function() {
		$("#frmPaging > input").prependTo("#frmContent");
		$("#frmContent").attr("action", "/update.my");
		$("#frmContent").submit();
	});
	
	$("#btnDelete").click(function() {
		$("#frmPaging > input").prependTo("#frmContent");
		$("#frmContent").attr("action", "/delete.my");
		$("#frmContent").submit();
	});
	
});
</script>
</html>
<% session.removeAttribute("message"); %>