<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" 
	href="<c:url value='/resources/css/mainstyle.css'/>"/>
<script type="text/javascript" 
	src="<c:url value='/resources/js/jquery-3.6.0.min.js'/>"></script>
<script type="text/javascript">
	$(function(){
		$('#btUse').click(function(){
			$(opener.document).find('#userid').val('${param.userid}');
			$(opener.document).find('#chkId').val('Y');
			
			self.close();
		});
	});
</script>
</head>
<body>

<h1>아이디 중복검사</h1>
<form name="frmDup" method="post" action="<c:url value='/member/checkUserid'/>">
	<label for="">아이디</label>
	<input type="hidden" name="type" value="${param.type }">
	<input type="text" name="userid" id="userid" value="${param.userid }">
	<input type="submit" value="아이디 확인">
	
	<c:if test="${result == UNUSABLE_ID }">
		<p>이미 등록된 아이디입니다. 다른 아이디를 입력하세요</p>
	</c:if>
	<c:if test="${result == USABLE_ID }">
		<input type="button" value="사용하기" id="btUse">
		<p>사용가능한 아이디입니다. [사용하기] 버튼을 클릭하세요</p>
	</c:if>
</form>
</body>
</html>