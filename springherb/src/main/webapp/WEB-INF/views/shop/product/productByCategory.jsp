<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../inc/top.jsp"%>
<style type="text/css">
.pdEvent{		
		clear:both;
		padding: 20px 0 10px 0;
	}
	.spEvent{
		margin: 0 0 0 10px;
		font-size: 1.5em;
		font-weight: bold;
	}
.divPd {
	float: left;
	padding: 20px;
	margin: 0 10px 10px 0;
	width: 140px;
	text-align: center;
	border: 1px solid silver;
}

.line {
	border: 1px solid silver;
	padding: 20px;
}
.divOuter{
	width: 780px;
	text-align: center;
}
</style>
<p class="pdEvent">
	<img src="${pageContext.request.contextPath }/resources/images/dotLong4.JPG"
		align="absmiddle">
	<span class="spEvent">${param.categoryName }</span>
</p>
<div class="divOuter">
	<c:if test="${empty list }">
		<div class="line">상품이 없습니다.</div>
	</c:if>
	<c:if test="${!empty list }">
		<c:forEach var="vo" items="${list }">
			<div class="divPd">
			<a href="<c:url value='/shop/product/productDetail?productNo=${vo.productNo }'/>">
				<img src="${pageContext.request.contextPath }/pd_images/${vo.imageUrl}" 
					alt="${vo.productName }">
			</a>
				<br>
				${vo.productName }<br>
				<fmt:formatNumber value="${vo.sellPrice }" pattern="#,###"/>원
			</div>
		</c:forEach>
	</c:if>
</div>
<%@ include file="../../inc/bottom.jsp"%>