<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../inc/adminTop.jsp" %>
<script type="text/javascript">
	$(function(){
		$('form[name=frm1]').submit(function(){
			//유효성 검사
			if($('#productName').val().length<1){
				alert("상품명을 입력하세요");
				$('#productName').focus();
				return false;
			}else if($('#categoryNo').val().length<1){
				alert("카테고리를 선택하세요");
				$('#categoryNo').focus();
				return false;
			}else if($('#imageUpload').val().length<1){
				alert("업드드할 이미지를 선택하세요");
				$('#imageUpload').focus();
				return false;
			}
			if($('#sellPrice').val()==''){
				$('#sellPrice').val('0');
			}
			if($('#mileage').val()==''){
				$('#mileage').val('0');
			}
		});
	});
</script>

<div class="divForm">
<form name="frm1" method="post" action="<c:url value='/admin/product/productWrite'/>"
	enctype="multipart/form-data">
	<fieldset>
		<legend>상품 등록</legend>
        <div>
        	<label for="productName">상품명</label>
        	<input type ="text" name="productName" id="productName" size="50">
        </div>
        <div>
        	<label for="categoryNo">카테고리</label>
            <select name="categoryNo" id="categoryNo" title="카테고리">
            	<option value="">선택하세요</option>
            	<!-- 반복문 시작 -->	
            	  	<c:forEach var="vo" items="${list }">
            	  		<option value="${vo.categoryNo }">${vo.categoryName }</option>
            	  	</c:forEach>
 				<!-- 반복문 끝 -->	         
            </select>            
        </div>
	    <div>
	        <label for="imageUpload">상품이미지</label>        
	        <input type ="file" name="imageUpload" id="imageUpload">
		</div>            
	    <div>
	        <label for="sellPrice">가격</label>
	        <input type ="text" name="sellPrice" id="sellPrice" >
	    </div>    
	    <div>
	        <label for="explains">요약설명</label>
	        <textarea cols="84" rows="5" name="explains" id="explains"></textarea>
	    </div>
	    <div>
	        <label for="description">상세설명</label>
	        <textarea cols="84" rows="5" name="description" id="description"></textarea>
	    </div>
	    <div>
	        <label for="mileage">적립금</label>
	        <input type ="text" name="mileage" id="mileage">
	    </div>
	    <div>
	        <label for="company">제조회사</label>
	        <input type ="text" name="company" id="company">
	    </div>
	    <p class="center">
	       <input type ="submit" value="등록">
	       <input type ="reset"  value="취소" >
	    </div>
	</fieldset>
</form>
</div>

<%@ include file="../../inc/adminBottom.jsp"%>
