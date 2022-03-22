<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    import="java.util.*, model.*, model.book.*"
    import="java.text.DecimalFormat"
    import="com.spring.book.*"
    %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
	D_Member dm = (D_Member) session.getAttribute("member");
	String user_id = (String) dm.getUser_id();
	System.out.println("user_idasdfasdf :: " + user_id);
	BookDAO bookDAO = new BookDAO();
	ArrayList bookList = (ArrayList) bookDAO.listAllBook(user_id);
	request.setAttribute("bookList", bookList);
	
%>
<!DOCTYPE html>
<html>
<head>
<style>
	.n-section-title {
    border-bottom: 3px solid #000000;
    padding-bottom: 14px;
    margin-top: 48px;
    font-family: "Apple SD Gothic Neo", "Noto Sans KR", sans-serif;
    line-height: 1.5;
    font-size: 14px;
    position: relative;
    }
    .n-table-filter {
    margin: 50px 0 16px 0;
	}
	.n-table.table-col {
    border-top: 1px solid #000000;
	}
	table {
    width: 100%;
    border-top: 1px solid #444444;
    border-collapse: collapse;
  	}
  	th, td {
    border-bottom: 1px solid #444444;
    padding: 10px;
  	}
	.n-radio-tab{
	display: block;
	overflow: hidden;
	clip: rect(0 0 0 0);
	margin: -1px;
	padding: 8px 19px;
	}
/* 	.n-radio-tab{
    display: block;
   	overflow: hidden;
    width: 1px;
    height: 1px;
    margin: -1px;
    clip: rect(0 0 0 0);
	} */
	.n-radio-tab input + label {
    display: inline-block;
    min-width: 76px;
    border: 1px solid #f1f1f1;
    /* color: #777777; */
    font-size: 14px;
    line-height: 30px;
    float: left;
    margin-right: -1px;
    text-align: center;
    vertical-align: top;
    position: relative;
    cursor: pointer;
	}
	.n-radio-tab input:checked + label {
    border-color: #000000;
    color: #000000;
    z-index: 1;
}
	input[type=radio]{
		display:none; margin:10px;
	}

</style>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	function setPeriod(e, period, user_id){
		// 현재 시각
		var date = new Date(+new Date() + 3240 * 10000).toISOString().split("T")[0];
		
		if(period == "after"){
			getBooks("after", date, user_id);
			console.log("after");
			console.log(date);
			
		}else if(period == "before"){
			getBooks("before", date, user_id);
			console.log("before");
		}
	}
	function getBooks(period, date, user_id){
		console.log(user_id);
		$.ajax({
			type : "get",
			async : true,
			url : "http://localhost:8080/DCar/myPage",
			dataType : "json",
			data : {period : period, date : date, user_id : user_id},
			success : function (result){
				var list = result;
				setTable(list);
			},
			error:function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	function setTable(list){
		var div = document.querySelector('#table_body');
		var html = "";
		
		for (var i = 0; i < Object.keys(list.result).length; i++) {
			html += "<tr align='center'><td>" + list.result[i].c_number + "</td><td>" + list.result[i].c_model + "</td><td>" +
			list.result[i].c_address + "</td><td>" + list.result[i].date_start + "</td><td>" +
			list.result[i].date_end + "</td><td>" + priceToString(list.result[i].total_price) + "</td></tr>";
		}
		div.innerHTML = html;
	}
	function priceToString(price) {
	    return price.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}
</script>
<meta charset="UTF-8">
<title>마이페이지</title>
</head>
<body>
	<div>
		<header class=".n-section-title">
			<h1><%=dm.getUser_name()%>님의 예약 정보</h1>
		</header>
	</div>
	<div class="n-table-filter">
		<div class="n-radio-tab">
			<input type="radio" id="radioTabGuide0" name="radioTabGuide" onclick="setPeriod(this, 'after','<%=dm.getUser_id()%>')">
			<label for="radioTabGuide0">예약대기</label>
			
			<input type="radio" id="radioTabGuide1" name="radioTabGuide" onclick="setPeriod(this, 'before','<%=dm.getUser_id()%>')">
			<label for="radioTabGuide1">지난예약</label>
			<br>
		</div>
	</div>
	<div>
		<table class="n-table table-col n-order-view">
			<colgroup>
				<col style="width:10%">
				<col style="width:10%">
				<col style="width:30%">
				<col style="width:15%">
				<col style="width:15%">
				<col style="width:5%">
			</colgroup>
			<thead>
			<tr>
				<th scope="col">차량번호</th>
				<th scope="col">모델</th>
				<th scope="col">픽업 장소</th>
				<th scope="col">예약 날짜</th>
				<th scope="col">반납 날짜</th>
				<th scope="col">금액</th>
			</tr>
			</thead>
			<tbody id="table_body">
				<c:forEach var="book" items="${bookList}">
				<tr align="center">
					<td>${book.c_number}</td>
					<td>${book.c_model}</td>
					<td>${book.c_address}</td>
					<td>${book.date_start}</td>
					<td>${book.date_end}</td>
					<td>${book.total_price}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</body>
</html>