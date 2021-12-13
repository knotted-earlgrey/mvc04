<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EmployeeUpdateForm.jsp</title>
<link rel="stylesheet" type="text/css" href="<%=cp %>/css/main.css">
<link rel="stylesheet" type="text/css" href="<%=cp %>/css/jquery-ui.css">
<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript" src="<%=cp%>/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=cp %>/js/ajax.js"></script>
<script type="text/javascript">
/*
	function check()
	{
		var positionId = document.getElementById("positionId").value;
		
		// url 준비
		var url = "employeeinsertform.action?positionId=" + positionId;
		
		// ajax 객체 생성
		ajax = createAjax();
		
		ajax.open("GET", url, true);
		
		ajax.onreadystatechange = function()
		{
			if (ajax.readyState==4 && ajax.status==200)
			{
				// 업무 처리 함수 호출
				callBack();
			}
		}
		
		ajax.send("");
	}
	
	function callBack()
	{
		var minBasicPay = ajax.responseText;
		
		minBasicPay = parseInt(minBasicPay);
		
		document.getElementById("minBasicPay").innerText = minBasicPay;
	}
*/
</script>

<script type="text/javascript">
	//$();로 표현해도 됨
	$(document).ready(function()
	{
		// Ajax 요청 및 응답 처리 (최초)
		ajaxRequest();
		
		// jQuery-UI 캘린더를 불러오는 함수 처리(datepicker())
		$("#birthday").datepicker(
		{
			dateFormat: "yy-mm-dd"
			, changeMonth: true
			, changeYear: true
		});
		
		// 직위(select)의 선택된 내용이 변경되었을 경우 수행해야 할 코드 처리
		$("#positionId").change(function()
		{
			// 테스트
			//alert("변경");
			
			// Ajax 요청 및 응답 처리
			ajaxRequest();
		});
		
		
		// 직원 추가 버튼이 클릭되었을 때 수행해야 할 코드 처리
		$("#submitBtn").click(function()
		{
			// 데이터 검사(누락된 입력값이 있는지 없는지에 대한 여부 확인)
			if ( $("#name").val()=="" || $("#ssn1").val()=="" || $("#ssn2").val()=="" || $("#birthday").val()==""
					|| $("#telephone").val()=="" || $("#basicPay").val()=="" )
			{
				$("#err").html("필수 입력 항목이 누락되었습니다.");
				$("#err").css("display", "inline");
				return;		// submit 액션 처리 중단
			}
			
			// 테스트
			//alert($("#minBasicPay").val());
			//alert($("#minBasicPay").text());	값 출력됨
			
			// 최소 기본급에 대한 유효성 검사
			// 직급별최소기본급 > 사용자가입력한기본급 → submit 중단
			if ( parseInt($("#minBasicPay").text()) > parseInt($("#basicPay").val()) )
			{
				
				$("#err").html("입력하신 기본급이 최소 기본급보다 작습니다.");
				$("#err").css("display", "inline");
				return;		// submit 액션 처리 중단
			}
			
			
			// 폼 submit 액션 처리 수행
			$("#employeeForm").submit();
			
			
		});
		
		
	});	
	
	function ajaxRequest()
	{
		//alert("ㅎㅇ");
		
		// 『$.post()』 / 『$.get()』 : 제이쿼리$가 가지고 있는 post, get 함수
		// jQuery에서 Ajax를 써야할 경우 지원해주는 함수
		// (서버 측에서 요청한 데이터를 받아오는 기능의 함수)
		
		// ※ 이 함수(『$.post()』)의 사용 방법(방식)
		// $.post(요청 주소, 전송 데이터, 응답액션처리)
		// - 요청주소(url) : 데이터를 요청할 파일에 대한 정보
		// - 전송 데이터(data) : 서버 측에 요청하는 과정에서 내가 전달할 파라미터
		// - 응답액션처리(function) : 응답을 받을 수 있는 함수. 기능 처리
		
		// ※ 참고로 data 는 파라미터의 데이터타입을 그대로 취하게 되므로 html이든, 문자열이든 상관이 없다.
		$.post("ajax.action", {positionId : $("#positionId").val()}, function(data)
		{
			$("#minBasicPay").html(data);
		});
		
	}
	
</script>

</head>
<body>

<!-- ??. EmployeeUpdateForm.jsp -->
<!-- 직원 데이터 수정 페이지 -->

<div>
	<!-- 메뉴 영역 -->
	<div>
		<c:import url="EmployeeMenu.jsp"></c:import>
	</div>
	
	<!-- 콘텐츠 영역 -->
	<div id="content">
		<h1> [ 직원 변경 ] </h1>
		<hr />
		<form action="employeeupdate.action" method="post" id="employeeForm">
			<table>
				<tr>
					<th>사원번호</th>
					<td><input type="text" id="employeeId" name="employeeId" readonly="readonly" value="${employee.employeeId }"></td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input type="text" id="name" name="name" value="${employee.name }"></td>
				</tr>
				<tr>
					<th>주민번호</th>
					<td><input type="text" id="ssn1" name="ssn1" style="width: 100px;" value="${employee.ssn1 }">
					- <input type="text" id="ssn2" name="ssn2" style="width: 110px;" placeholder="뒷 7자리"></td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td><input type="text" id="birthday" name="birthday" value="${employee.birthday }"></td>
				</tr>
				<tr>
					<th>양/음력</th>
					<td>
						<input type="radio" value="0" name="lunar" id="lunar0" ${employee.lunar==0 ? "checked=\"checked\"" : "" }>
						<label for="lunar0">양력</label>
						<input type="radio" value="1" name="lunar" id="lunar1" ${employee.lunar==1 ? "checked=\"checked\"" : "" }>
						<label for="lunar1">음력</label>
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td><input type="tel" id="telephone" name="telephone" value="${employee.telephone }"></td>
				</tr>
				<tr>
					<th>지역</th>
					<td>
						<select name="regionId" id="regionId">
							<!-- <option value="1">마포</option>
							<option value="2">은평</option>
							<option value="3">성북</option>		 -->
							<c:forEach var="region" items="${regionList }">
								<option value="${region.regionId }" ${employee.regionId == region.regionId ? "selected=\"selected\"" : "" }>${region.regionName }</option>
							</c:forEach>					
						</select>
					</td>
				</tr>
				<tr>
					<th>부서</th>
					<td>
						<select name="departmentId" id="departmentId">
							<!-- <option value="1">독서부</option>
							<option value="2">축구부</option>
							<option value="3">원예부</option>	 -->		
							<c:forEach var="department" items="${departmentList }">
								<option value="${department.departmentId }" ${employee.departmentId == department.departmentId ? "selected=\"selected\"" : "" }>${department.departmentName }</option>
							</c:forEach>				
						</select>
					</td>
				</tr>
				<tr>
					<th>직위</th>
					<td>
						<select name="positionId" id="positionId">
							<!-- <option value="1">팀장</option>
							<option value="2">기술고문</option>
							<option value="3">팀원</option>		 -->
							<c:forEach var="position" items="${positionList }">
								<option value="${position.positionId }" ${employee.positionId == position.positionId ? "selected=\"selected\"" : "" }>${position.positionName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>기본급</th>
					<td>
						<input type="text" id="basicPay" name="basicPay" value="${employee.basicPay }">
						(최소 기본급 <span id="minBasicPay" style="color: red; font-weight: bold;">0</span>원)
					</td>
				</tr>
				<tr>
					<th>수당</th>
					<td><input type="text" id="extraPay" name="extraPay" value="${employee.extraPay }"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<br><br>
						<button type="button" class="btn" id="submitBtn" style="width: 40%;">직원 변경</button>
						<button type="button" class="btn" id="listBtn" style="width: 40%;" onclick="location.href='employeelist.action'">직원 리스트</button>
						<br><br>
						<span id="err" style="color: red; font-weight: bold; display: none;"></span>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<!-- 회사 소개 및 어플리케이션 소개 영역 -->
	<div id="footer">
	</div>
</div>

</body>
</html>