<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- bootstrap_cdn.jsp -->
<%@ include file="include/bootstrap_cdn.jsp" %>
<title>Model2 게시판</title>
</head>
<body>	
<div class="container-fluid">

<!-- paging_form -->
<%@ include file="include/paging_form.jsp" %>
	
	<!-- jumbotron -->
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8">
			<div class="jumbotron">
				<h2>Model2 게시판</h2>
				<p>MVC 패턴으로 제작한 게시판입니다.</p>
				<a type="button" class="btn btn-outline-success btn-sm" href="/write.my">글 작성</a>
			</div>
		</div>
		<div class="col-md-2"></div>
	</div>
	<!--// jumbotron -->
	
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<!-- search -->
			<div style="float:left;" class="col-xs-4">
				<form id="frmSearch" action="/list.my" method="get" style="float:left;">
					<select id="searchType" name="searchType">
						<option 
						<c:if test="${pagingDto.searchType == 'b_title'}">selected</c:if>
						value="b_title">제목</option>
						<option 
						<c:if test="${pagingDto.searchType == 'b_content'}">selected</c:if>
						value="b_content">내용</option>
						<option 
						<c:if test="${pagingDto.searchType == 'm_id'}">selected</c:if>
						value="m_id">글쓴이</option>
					</select>
					<input id="keyword" name="keyword" value="${pagingDto.keyword}"/>
					<button id="btnSearch" type="submit">검색</button>
				</form>
			</div>
			<!--// search -->
			<!-- perPage-->
			<div style="float:right;">
				<select id="perPage">
					<c:forEach var="p" begin="5" end="30" step="5">
					<option value="${p}"
					<c:if test="${pagingDto.perPage == p}">selected</c:if>
					>${p}줄 보기</option>
					</c:forEach>
				</select>
			</div>
			<!--// perPage-->
		</div>
		<div class="col-md-3"></div>
	</div><br/>
	
	<!-- table -->
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<table class="table">
				<thead>
					<tr>
						<th>글번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>날짜</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="boardVo" items="${boardList}">
					<tr>
						<td>${boardVo.b_no}</td>
						<td><a class="contentAnchor" href="#" data-bno="${boardVo.b_no}">${boardVo.b_title}</a></td>
						<td>${boardVo.m_id}</td>
						<td>${boardVo.b_date}</td>
						<td>${boardVo.b_view}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="col-md-3"></div>
	</div>
	<!--// table -->
	
	<!-- paging -->
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<nav>
				<ul class="pagination justify-content-center">
					<li class="page-item">
						<c:if test="${pagingDto.startPage - 1 != 0}">
						<a class="page-link" href="${pagingDto.startPage - 1}">이전</a>
						</c:if>
					</li>
					<c:forEach var="i" begin="${pagingDto.startPage}" end="${pagingDto.endPage}">
					<li class="page-item
						<c:if test="${pagingDto.page == i}">
							active
						</c:if>
						">
						<a class="page-link" href="${i}">${i}</a>
					</li>
					</c:forEach>
					<li class="page-item">
						<c:if test="${pagingDto.totalPage > pagingDto.endPage}">
						<a class="page-link" href="${pagingDto.endPage + 1}">다음</a>
						</c:if>
					</li>
				</ul>
			</nav>
		</div>
		<div class="col-md-3"></div>
	</div>
	<!--// paging -->
	
</div>
</body>
<script>
$(function() {
	
	var message = "${sessionScope.message}";
	if (message == "write_success") {
		alert("글쓰기 완료");
	} else if (message == "delete_success") {
		alert("삭제 완료");
	}
	
	$(".page-link").click(function(e) {
		e.preventDefault();
		var page = $(this).attr("href");
		$("#frmPaging > input[name='page']").val(page);
		$("#frmPaging").submit();
	});
	
	$("#perPage").change(function() {
		var perPage = $(this).val();
		$("#frmPaging > input[name='perPage']").val(perPage);
		$("#frmPaging > input[name='page']").val(1);
		$("#frmPaging").submit();
	});
	
	$(".contentAnchor").click(function(e) {
		e.preventDefault();
		var b_no = $(this).attr("data-bno");
		$("#frmPaging > input[name='b_no']").val(b_no);
		$("#frmPaging").attr("action", "content.my");
		$("#frmPaging").submit();
	});
	
});
</script>
</html>
<% session.removeAttribute("message"); %>