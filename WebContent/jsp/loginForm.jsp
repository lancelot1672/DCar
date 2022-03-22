<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    %>
    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../css/member.css">
<meta charset="UTF-8">
<title>DCar Login</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	sessionStorage.setItem("contextPath", "${pageContext.request.contextPath}");
	function getContextPath(){
		return sessionStorage.getItem("contextPath");			
	}
	function fn_loginCheck(){		// 로그인 버튼 눌렀을 시
		var user_id = document.loginfrm.user_id.value;
		var user_pw = document.loginfrm.user_pw.value;
		
		$.ajax({
			type : "post",
			async : false,
			url : "http://localhost:8080/DCar/loginCheck",
			dataType : "text",
			data : {user_id : user_id, user_pw : user_pw},
			success : function (result){
				console.log(result);
				if(result == 'login_O'){
					location.href = getContextPath() + '/jsp/mainPage.jsp';
				}else{
					alert("아이디 비밀번호 확인");
				}
			}
		});
	}
</script>
</head>
<body>
	<div class="n-member-area">	
		<div class="">
			<h2 class="common-layout__sc-wllrag-0 fuSBOR">로그인</h2>
		</div>
		<form method="post" name="loginfrm" class="login-form">
			<div class="Form-set">
			<label for="id" class="n-form-label">
				아이디
			</label>

			<input type="text" class="n-input input" id="user_id" name="user_id" placeholder="아이디를 입력해 주세요.">
			</div>
			
			<div class="Form-set">
				<label for="pw" class="n-form-label">
					비밀번호
				</label>

				<input type="password" class="n-input input" id="user_pw" name="user_pw" placeholder="비밀번호를 입력해 주세요.">

			</div>
			<button type="button" class="n-btn btn-md btn-accent" id="add" onClick="fn_loginCheck()">로그인</button>
			<div class="n-member-find">
				<a href="${contextPath}">아이디 찾기</a><br>
				<a href="${contextPath}">비밀번호 찾기</a><br>
			</div>
			<a href="${contextPath}/dcar.do/addMemberForm">회원가입</a><br>
		</form>
	
	</div> 
</body>
</html>