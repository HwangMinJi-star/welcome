<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>zipcode.jsp</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/mainstyle.css'/>"/>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.6.0.min.js'/>"></script>
<script type="text/javascript">
	function setZipcode(zipcode, address){
		$(opener.document).find('#zipcode').val(zipcode);
		$(opener.document).find('input[name=address]').val(address);
		
		self.close();		
	}
</script>
</head>
<body>
	<h2>우편번호 검색</h2><br><p>	찾고 싶으신 주소의 동(읍/면)을 입력하세요</p>
	<form name="frmZipcode" method="post" action="<c:url value='/zipcode/zipcode'/>">
		<label for="dong">지역명 : </label>
		<input type="text" name="dong" id="dong" 
			style="ime-mode:active" value="${param.dong }">
		<input type="submit" id="submit" value="찾기">
	</form>
	<br>
		<c:if test="${list !=null }">
		<table style="width:470px" class="box2" 
		summary="우편번호 검색 결과에 관한 표로써, 우편번호, 주소에 대한 정보를 제공합니다.">		
			<colgroup>
				<col style="width:20%" />
				<col style="width:*" />				
			</colgroup>
			<thead>
			  <tr><th scope="col">우편번호</th><th scope="col">주소</th></tr>
			</thead>
			<tbody>
				<c:if test="${empty list }">
					<tr>
						<td colspan="2" class="align_center">데이터가 없습니다.</td>
					</tr>
				</c:if>
					<c:if test="${!empty list }">
						<!-- 반복 시작 -->
						<c:forEach var="vo" items="${list }">
							<c:set var="address" value="${vo.sido} ${vo.gugun} ${vo.dong}"/>
							
							<c:if test="${ vo.endbunji !=null}">
								<c:set var="bunji" value="${vo.startbunji } ~ ${vo.endbunji }"/>
							</c:if>
							<c:if test="${ vo.endbunji ==null}">
								<c:set var="bunji" value="${vo.startbunji }"/>
							</c:if>
							<tr>
								<td>${vo.zipcode}</td>
								<td>
									<a href="#" 
				onclick="setZipcode('${vo.zipcode}','${address}')">
										${address} ${bunji}
									</a>
								</td>						
							</tr>
						</c:forEach>
						<!-- 반복 끝 -->
				</c:if>
			</tbody>
		</table>
		</c:if>
</body>
</html>