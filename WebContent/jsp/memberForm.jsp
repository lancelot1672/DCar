<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    %>
    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../css/member.css">
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	sessionStorage.setItem("contextPath", "${pageContext.request.contextPath}");
	function getContextPath(){
		return sessionStorage.getItem("contextPath");			
	}
	function check_pw(){
		var addMember = document.addMember;
		var pw1 = document.getElementById('user_pw').value;
		var pw2 = document.getElementById('user_pw2').value;
		
		if(pw1 != pw2 && pw1 != "" && pw2 != ""){
			alert("비밀번호 확인 요망!");
		}else{
			addMember.method="post";

			addMember.action = getContextPath() + "/dcar.do/addMember";
			addMember.submit();
		}
	}
	function idCheck(){
		var _id = $("#user_id").val();

		if(_id == ''){
			alert("ID를 입력하세요");
			return;
		}
		$.ajax({
			type : "post",
			async : true,
			url : "http://localhost:8080/DCar/validationCheck",
			dataType : "text",
			data : { id : _id},
			success : function(data, textStatus){
				if(data == 'usable'){
					$('#message').text("사용할 수 있는 ID입니다.");
				}else{
					$('#message').text("사용할 수 없는 ID입니다.");
				}
			}
		});
	}
</script>
<style>
	.join-Form { margin:0 auto;}
	.n-input {ox-sizing: border-box;
    box-sizing: border-box!important;
    width: 80%;
    height: 50px;
    padding: 0 9px 0 9px;
    border: 1px solid #e5e5e5;
    background-color: #fff;
    font-size: 15px;
    transition: border .2s ease-in-out;
    -webkit-appearance: none; }
    
    input, select, textarea {
    border: 0;
    border-radius: 0;
    font-family: inherit;
    color: #000;
    vertical-align: middle;
    outline: 0;
    }
	div { display : block;}
	
	.n-btn {
    display: block;
    box-sizing: border-box;
    height: 50px;
    padding: 1px 10px 0 10px;
    border: 1px solid #000;
    font-size: 14px;
    
    line-height: 47px;
    color: #fff;
    text-align: center;
    }
}
</style>
</head>
<body>
	<form method="post" name="addMember" encType="utf-8" >
	
	<div class="n-member-area">
		<div class="Form-set">
			<label for="id" class="n-form-label">
				아이디
			</label>
			<br>
			<input type="text" class="n-input input" id="user_id" name="user_id" 
				placeholder="아이디 입력 (5~15)자" minlength="5" maxlength="15">
			<button type="button" class="n-btn btn-md btn-accent" onClick="idCheck()">중복확인</button>
			<div id="message"></div>

		</div>
		<div class="Form-set">
			<label for="pw" class="n-form-label">
				비밀번호
			</label>
			<br>
			<input type="password" class="n-input" id="user_pw" name="user_pw" 
				placeholder="비밀번호(숫자,영문,특수문자 조합 최소 8문자)" minlength="8">
		</div>
		<div class="Form-set">
			<input type="password" class="n-input" id="user_pw2" name="user_pw2" 
				placeholder="비밀번호 확인" minlength="8">
		</div>
		<div class="Form-set">
			<label for="id" class="n-form-label">
				이름
			</label>
			<br>
			<input type="text" class="n-input" id="user_name" name="user_name" 
				placeholder="이름">
			
			<br>
		</div>
		<div class="Form-set">
			<label for="id" class="n-form-label">
				연락처
			</label>
			<br>
			<input type="text" class="n-input" id="phone" name="phone" 
				placeholder="010-0000-0000 (- 포함)" maxlength="13">
			<br>
		</div>
		<div class="Form-set">
			<label for="id" class="n-form-label">
				생년월일
			</label>
			<br>
			<input type="text" class="n-input" id="birth" name="birth" 
				placeholder="생년월일 8자리(ex. 19970903)" minlength="8" maxlength="8">

		</div>
		<div class="Form-set">
			<label for="id" class="n-form-label">
				이메일
			</label>
			<br>
			<input type="text" class="n-input" id="email" name="email" 
				placeholder="E-mail">

		</div>
		<div class="add_button">
			<button type="submit" class="n-btn btn-md btn-accent" id="add" onClick="check_pw()">회원가입</button>
		</div>
	</div>
	</form>
</body>
</html>