<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
.divNotice {
	width: 310px
}

.divNotice div table {
	width: 300px
}

.divNotice div span {
	padding: 0 0 0 160px;
}

.divNotice div .img1 {
	width: 310px;
	height: 6px
}
.divNotice div p{
	padding:5px 0;
}
</style>
<div class="divNotice">
	<div>
		<img src="${pageContext.request.contextPath }/resources/images/notice2.JPG"
			alt="공지사항"> 
			<span>
			<a href="<c:url value='/board/list.do'/>"> 
			<img src="${pageContext.request.contextPath }/resources/images/more.JPG" border="0"
				alt="more - 더보기">
			</a>
			</span>
	</div>
	<div>
		<img src="${pageContext.request.contextPath }/resources/images/Line.JPG" alt="line 이미지"
			class="img1">
	</div>
	<div>
		<!-- 공지사항 -->
		<c:if test="${empty list }">
			<p>공지사항이 없습니다.</p>
		</c:if>
		<c:if test="${!empty list }">
			<c:forEach var="vo" items="${list }">
			<p>
				<img src="${pageContext.request.contextPath }/resources/images/dot.JPG">
				<a href="<c:url value='/board/detail.do?no=${vo.no}'/>">
					${vo.title}
				</a>
				</p>
			</c:forEach>
		</c:if>
	</div>
</div>