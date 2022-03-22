<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    import="java.util.*, controller.*, model.*, model.management.*"
    import="java.text.DecimalFormat"
    import="com.spring.management.*"
    %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
	//금액 형식 지정
	DecimalFormat formatter = new DecimalFormat("###,###");
	String sql = "select * from d_car";
	CarDAO cardao = new CarDAO();
	ArrayList carsList = (ArrayList) cardao.listCar(sql);
	
	request.setAttribute("carsList", carsList);
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../css/dcarCSS.css">
<meta charset="UTF-8">
<title>DCar 메인 페이지</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	sessionStorage.setItem("contextPath", "${pageContext.request.contextPath}");
	function getContextPath(){
		return sessionStorage.getItem("contextPath");			
	}
	function showTable(list, date_start, date_end){
		var div = document.querySelector('#table_body');
		var html = "";
		var date_start = date_start;
		var date_end = date_end;
		
		var loginSession = "<%=(String)session.getAttribute("login")%>";
		console.log("loginSession :: " + loginSession);
		for (var i = 0; i < Object.keys(list.result).length; i++) {
			html += "<tr align='center'><td>" + list.result[i].c_number + "</td><td>" + list.result[i].c_model + "</td><td>" +
			list.result[i].address + "</td><td>" +
			priceToString(list.result[i].c_price) + "</td><td>";
			if(loginSession == "login"){
				
				html += "<a href='" + getContextPath() + "/dcar.do/book?c_number=" + list.result[i].c_number + "&date_start=" + date_start + "&date_end=" + date_end +"'>예약하기</a>";
			}else{
				html += "<a href='" + getContextPath() + "/dcar.do/login'>예약하기</a>";
			}
			 " </td><tr>";
		}
		
		console.log(html);
		div.innerHTML = html;
	} 
	function fn_search(){
		var do_ = document.searchCar.do_.value;
		var si = document.searchCar.si.value;
		var gu = document.searchCar.gu.value;
		var dong = document.searchCar.dong.value;
		
		var date_start = document.searchCar.start.value;
		var date_end = document.searchCar.end.value;
		
		var _address = do_ + " " + si + " " + gu + " " + dong;
		
		//alert(date_start);
		$.ajax({
			type : "get",
			async : true,
			url : "http://localhost:8080/DCar/carCon",
			dataType : "json",
			data : {address : _address, date_start : date_start, date_end : date_end},
			success : function (result){
				var list = result;				
				showTable(list, date_start, date_end);
			},
			error:function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
	function priceToString(price) {
	    return price.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}
</script>

</head>
<body>
	<div class ="header-member">
		<div class="header-member__block">
		<%
			D_Member member = (D_Member) session.getAttribute("member");
			String name = "";
		 	if(session.getAttribute("member") == null){

		 %>		 	
			<button type="button" class="header-member__login" onClick="location.href='${contextPath}/dcar.do/login'">로그인</button>
		<%
		 	}else{
		 		if(member.getUser_id() != null && member.getUser_id().equals("admin")){	//관리자 이면
		 			name = "관리자";
		 %>
					<a href="" class="header-member__link--user"><%=name %></a>

		<%	
		 		}else{		// 관리자가 아니면
		 			
		 		
				name = member.getUser_name();
				System.out.println("idTest :: " + name);
		%>
			<a href="${contextPath}/dcar.do/login" class="header-member__link--user"><%=name %></a>

		<%
		 		}
		 	}
		%>
		</div>
		<div class="header-member__block">
		<%
			if(session.getAttribute("member") == null){
		%>
			<a href="${contextPath}/dcar.do/myPage.jsp">예약확인</a>
		<%
		 	}else{
		 		if(member.getUser_id() != null && member.getUser_id().equals("admin")){	//관리자 이면

		%>
			<a href="${contextPath}/management/memberInfo.jsp">회원정보</a>
		<%
		 		}else{
		%>
			<a href="${contextPath}/dcar.do/myPage.jsp">예약확인</a>
		<%		
		 		//관리자 아니면
		 		}
		 	}
		%>	
		</div>
		<div class="header-member__block">
		<%
			if(session.getAttribute("member") == null){
		%>
			<a href="${contextPath}/dcar.do/login">마이페이지</a>
		<%
		 	}else{
				if(member.getUser_id() != null && member.getUser_id().equals("admin")){
		%>
			<a href="${contextPath}/management/addCarForm.jsp">차량등록</a>
		<%			
				}else{		// 관리자 아니면
		%>
			<a href="${contextPath}/member/myPage.jsp">마이페이지</a>
		<%
				}
		 	}
		%>
		</div>
		<div class="header-member__block">
		<%
			if(session.getAttribute("member") == null){

		 	}else{
		 		if(member.getUser_id() != null && member.getUser_id().equals("admin")){
		%>
				<a href="${contextPath}/management/carInfo.jsp">차량정보</a>
		<%
		 		}else{			
		%>
				<a href="${contextPath}/dcar.do/logout">로그아웃</a>
		<% 		
		 		}
		 	}
		%>	
		</div>
		<%
			if(session.getAttribute("member") == null){
			
			}else{
		 		if(member.getUser_id() != null && member.getUser_id().equals("admin")){
		 %>
		 <div class="header-member__block">
		 			<a href="${contextPath}/management/manageBook.jsp">예약확인</a>
		 </div>
		 <%
		 		}
			}
		%>
		<%
			if(session.getAttribute("member") == null){
			
			}else{
		 		if(member.getUser_id() != null && member.getUser_id().equals("admin")){
		 %>
		 <div class="header-member__block">
		 			<a href="${contextPath}/dcar.do/logout">로그아웃</a>
		 </div>
		 <%
		 		}
			}
		%>
		
	</div>
		<%
			if(session.getAttribute("member") == null || !member.getUser_id().equals("admin")){
		%>
		<div>
		<form method="post" name="searchCar" encType="utf-8" >
		<table align="center">
			<tr align="center">
				<td>
					<select name="do_">
						<option value="" selected disabled hidden>==도==</option>
						<option value="서울시">서울시</option>
						<option value="경기도">경기도</option>
						<option value="충청북도">충청북도</option>
						<option value="충청남도">충청남도</option>
						<option value="강원도">강원도</option>
						<option value="제주도">제주도</option>
						<option value="경상북도">경상북도</option>
						<option value="경상남도">경상남도</option>
						<option value="전라북도">전라북도</option>
						<option value="전라남도">전라남도</option>
					</select>
				</td>
				<td>
					<select name="si">
					<option value="" selected disabled hidden>==시==</option>
						<option value="청주시">청주시</option>
					</select>
				</td>
				<td>
					<select name="gu">
						<option value="" selected disabled hidden>==구==</option>
						<option value="상당구">상당구</option>
						<option value="서원구">서원구</option>
						<option value="흥덕구">흥덕구</option>
						<option value="청원구">청원구</option>
					</select>
				</td>
				<td>
					<select name="dong">
						<option value="" selected disabled hidden>==동==</option>
						<option value="수곡동">수곡동</option>
						<option value="모충동">모충동</option>
						<option value="우암동">우암동</option>
						<option value="율량동">율량동</option>
					</select>
				</td>
				<td>
					<input type='date' name="start" />
					 ~ <input type='date' name="end" />
				</td>
				<td>
					<input type="button" class="header-member__login"value="검색" onClick="fn_search()">
				</td>
			</tr>
		</table>
		</form>
		</div>
		<div class="table">
			<table>
				<tr align="center">
					<td width="15%"><b>차 번호</b></td>
					<td width="20%"><b>차종</b></td>
					<td width="*"><b>주소</b></td>
					<td width="15%"><b>1일 대여 금액</b></td>
					<td width="7%"><b>선택</b></td>
				</tr>
			<tbody id="table_body">	
			<c:choose>
			<c:when test="${ empty carList }">
				<tr>
					<td colspan=6 align="center">
					<b> 등록된 차량이 없습니다.</b>
					</td>
				</tr>
			</c:when>
			<c:when test="${!empty carList }">
				<c:forEach var="car" items="${carList}">
					<tr align="center">
						<td>${car.c_number }</td>
						<td>${car.c_model }</td>
						<td>${car.address }</td>
						<td>${car.c_price }</td>
						<td><a href="">선택</a></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
		</tbody>
			</table>
		</div>
	<%}else{ 
		request.setCharacterEncoding("UTF-8");
		SummaryDAO sDAO = new SummaryDAO();
		
		int total_account = sDAO.selectTotal_account();
		//int new_account = sDAO.selectNew_account();
		int total_book = sDAO.selectTotal_book();
		int month_book = sDAO.selectMonth_Book();
		int year_book = sDAO.selectYear_Book();
		int total_sales = sDAO.selectTotal_sales();
		int month_sales = sDAO.selectMonth_sales();
		int year_sales = sDAO.selectYear_sales();
		
	%>	
		<div class="n-member-area">	
		<h2 class="common-layout__sc-wllrag-0 fuSBOR">사이트 요약</h2>
		<table class="n-table table-col n-order-view">
			<colgroup>
				<col style="width:20%">
				<col style="width:20%">
				<col style="width:20%">
				<col style="width:20%">
				<col style="width:20%">
			</colgroup>
			<thead>
			<tr>
				<th scope="col">누적 계정 수</th>
				<th scope="col">신규 계정 수</th>
				<th scope="col">누적 예약 수</th>
				<th scope="col">올해 예약 건 수</th>
				<th scope="col">이번 달 예약 건 수</th>
			</tr>
			</thead>
			<tbody id="table_body">
				<tr align="center">
					<td><%= total_account %></td>
					<td><%-- <%= new_account %> --%></td>
					<td><%= total_book %>건</td>
					<td><%= year_book %>건</td>
					<td><%= month_book %>건</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="n-member-area">	
		<h2 class="common-layout__sc-wllrag-0 fuSBOR">매출 정보</h2>
		<table class="n-table table-col n-order-view">
			<colgroup>
				<col style="width:10%">
				<col style="width:25%">
				<col style="width:25%">
				<col style="width:25%">
				<col style="width:10%">
			</colgroup>
			<thead>
			<tr>
				<th scope="col"></th>
				<th scope="col">누적 매출</th>
				<th scope="col">올해 매출</th>
				<th scope="col">이번 달 매출</th>
				<th scope="col"></th>
			</tr>
			</thead>
			<tbody id="table_body">
				<tr align="center">
					<td></td>
					<td><%= formatter.format(total_sales) %>원</td>
					<td><%= formatter.format(year_sales) %>원</td>
					<td><%= formatter.format(month_sales) %>원</td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
	<%
	}
	%>
</body>
</html>