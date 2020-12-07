<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="include/bootstrap_cdn.jsp" %>
<title>Model2_Project - List</title>
<script type="text/javascript">
$(function() {
	$(".page-link").click(function(e) {
		e.preventDefault();
		var page = $(this).attr("href"); 
		$("#frmPaging > input[name=page]").val(page);
		$("#frmPaging").submit();
	});
});
</script>
</head>
<body>
<div class="container-fluid">
	<!-- Paging form -->
	<form id="frmPaging" action="list.my" method="get">
		<input type="hidden" name="page"/>
		<input type="hidden" name="perPage" value="${pagingDto.perPage}"/>
	</form>
	<!-- // Paging form -->

	<!-- 상단 배너 -->
	<div class="row">
		<div class="col-md-12">
			<div style="background : MintCream" class="jumbotron">
				<h2 class="font-weight-bold" style="color : MediumSeaGreen;">모델 2 게시판</h2>
				<p class="text-muted">MVC 패턴을 이용한 게시판</p>
			</div>
		</div>
	</div>
	<!-- // 상단 배너 -->
	
	<!-- 글 목록 -->
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8">
			<table class="table">
				<thead>
					<tr>
						<th class="text-muted">글번호</th>
						<th class="text-muted">제목</th>
						<th class="text-muted">아이디</th>
						<th class="text-muted">조회수</th>
						<th class="text-muted">날짜</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="boardVo" items="${list}">
					<tr>
						<td class="text-muted">${boardVo.b_no}</td>
						<td class="text-muted">${boardVo.b_title}</td>
						<td class="text-muted">${boardVo.m_id}</td>
						<td class="text-muted">${boardVo.b_readcount}</td>
						<td class="text-muted">${boardVo.b_date}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="col-md-2"></div>
	</div>
	<!-- // 글 목록 -->
	
	<!-- 페이징 -->
	<div class="row">
		<div class="col-md-2">
		</div>
		<div class="col-md-8">
			<nav>
				<ul class="pagination justify-content-center">
					<c:if test="${pagingDto.startPage >= 10}">
					<li class="page-item">
						<a class="page-link" href="${pagingDto.startPage-1}">이전</a>
					</li>
					</c:if>
					<c:forEach var="i" begin="${pagingDto.startPage}" end="${pagingDto.endPage}">
					<li class="page-item
						<c:if test="${i == pagingDto.page}"> active</c:if>
					">
						<a class="page-link" href="${i}">${i}</a>
					</li>
					</c:forEach>
					<c:if test="${pagingDto.endPage < pagingDto.totalPage}">
					<li class="page-item">
						<a class="page-link" href="${pagingDto.endPage+1}">다음</a>
					</li>
					</c:if>
				</ul>
			</nav>
		</div>
		<div class="col-md-2">
		</div>
	</div>
	<!-- // 페이징 -->
</div>
</body>
</html>