<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    import="java.util.*, model.*"
    import="controller.DateCalculate"
    import="java.text.DecimalFormat"
    %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
	
	D_Member dm = (D_Member) session.getAttribute("member");
	D_Car dcar = (D_Car) request.getAttribute("car");
	String date_start = (String) request.getAttribute("date_start");
	String date_end = (String) request.getAttribute("date_end");
	
	// 금액 형식 지정
	DecimalFormat formatter = new DecimalFormat("###,###");

	
	// 날짜 계산, 금액 계산
	DateCalculate dateC = new DateCalculate();
	int total_date = dateC.DateCalculate(date_start, date_end);
	int total_price = total_date * Integer.parseInt(dcar.getC_price());
	System.out.println("total_price" + total_price);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약 확인</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
sessionStorage.setItem("contextPath", "${pageContext.request.contextPath}");
function getContextPath(){
	return sessionStorage.getItem("contextPath");			
}

function setPrice(price){
	var id = document.querySelector('#price');
	var last_price_id = document.querySelector('#last_price');
	var html = price.toLocaleString() + "원";
	id.innerHTML = html;
	last_price_id.innerHTML = price.toLocaleString() + "원";
}
function test(c_number, c_model, c_address, user_id, date_start, date_end, total_price){
	var c_number = c_number;
	var c_model = c_model;
	
	var book = document.book;
	var insurance = document.getElementsByName('insurance');
	
 	console.log("total_price" + insurance[0].value);	 
	
 	console.log(c_number);
 	console.log(c_model);
 	console.log(c_address);
 	console.log(user_id);
 	
 	$.ajax({
		type : "post",
		async : true,
		url : "http://localhost:8080/DCar/bookCar",
		dataType : "text",
		data : {c_number : c_number, c_model : c_model, 
			c_address : c_address, user_id : user_id, date_start : date_start, date_end : date_end, total_price : total_price, insurance : insurance[0].value},
		success : function(result){
			console.log(result);
			if(result == 'success'){
				console.log("됐다야 ㅋㅋ");
				alert("예약이 완료되었습니다.");
		
			}else{
				
			}
		}
	});
	
	book.method = "post";
	book.action = getContextPath() + "/jsp/mainPage.jsp";
	book.submit();
}
	$(function(){
		// 라디오버튼 클릭시 이벤트 발생
		$("input:radio[name=insurance]").click(function(){
			if($("input[name=insurance]:checked").val() == "10000"){
				var total_price = "<%= total_price %>";
				console.log(10000 + parseInt(total_price));
				setPrice(10000 + parseInt(total_price));
			}else if($("input[name=insurance]:checked").val() == "7000"){
				var total_price = "<%= total_price %>";
				console.log(7000 + parseInt(total_price));
				setPrice(7000 + parseInt(total_price));
				
			}else if($("input[name=insurance]:checked").val() == "5000"){
				var total_price = "<%= total_price %>";
				console.log(5000 + parseInt(total_price));
				setPrice(5000 + parseInt(total_price));
			}
		});
	});
	
</script>
</head>
<body>
	<div class="header">
		<h1>예약 결제</h1>
	</div>
	<div class="book">
		<form method="post" name="book" encType="utf-8" >
		<div class="car_table">
		<label><%= dcar.getC_model() %>   <%=dcar.getC_number() %></label><br>
		<label><%= date_start %>~<%=date_end %> (<%=total_date %>일)</label><br>
		</div>
		<div class="car_table">
		<h3>예약 정보</h3>
		<table>
			<tr>
				<td "text-align:left">
					대여 장소
				</td>
				<td "text-align:right">
					<%= dcar.getAddress() %>
				</td>
			</tr>
			<tr>
				<td>
					요금 정보 
				</td>
				<td id="price">
					<%= formatter.format(total_price+10000) %>원
				</td>
			</tr>
			<tr>
				<td>
					대여 요금 
				</td>
				<td>
					<%= formatter.format(total_price) %>원
				</td>
			</tr>
			<tr>
				<td>
					보험료
				</td>
				<td>
					<input type="radio" name="insurance" value= "10000" checked> 10,000원 (자기 부담금 5만원)
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<input type="radio" name="insurance" value= "7000"> 7,000원 (자기 부담금 30만원)
				</td>
			</tr>
			<tr>
				<td>
				</td>			
				<td>
					<input type="radio" name="insurance" value= "5000"> 5,000원 (자기 부담금 100만원)
				</td>
			</tr>
		</table>
		</div>
		<div class="booker_table">
			<%if(dm != null){ %>
			<h3>예약자 정보</h3>
			<table>
				<tr>
					<td>예약자</td>
					<td><%=dm.getUser_name() %></td>
				</tr>
				<tr>
					<td>연락처</td>
					<td><%=dm.getPhone() %></td>
				</tr>
			</table>
			<%}else{ %>
				<script>alert("잘못된 접근입니다. 다시 시도해주세요.");</script>
				<jsp:forward page="mainScreen2.jsp"></jsp:forward>
			<%} %>
		</div>
		<div class="confirm">
		
		<h3>결제 확인</h3>
			<table>
				<tr>
					<td>최종 결제금액</td>
					<td id="last_price"><%= formatter.format(total_price+10000) %>원</td>
				</tr>
			</table>
		<div class="add_button">
			<button type="button" class="n-btn" id="add" 
			onClick="test('<%=dcar.getC_number()%>','<%=dcar.getC_model()%>','<%=dcar.getAddress()%>'
			,'<%=dm.getUser_id()%>','<%=date_start%>','<%=date_end%>',<%=total_price%>)">결제하기</button>
		</div>
	</div>
	</form>
	</div>
	
</body>
</html>