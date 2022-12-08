<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 1. 유효성 검사
	// -> 이 페이지를 계속 실행해야하는지 확인
	// 로그인O -> 실행X
	if(session.getAttribute("loginMember") != null){
		response.sendRedirect(request.getContextPath()+"/main.jsp");
		System.out.println("로그인폼-로그인중");
		return;
	}
%>
<!-- view -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>로그인</title>
	</head>
	
	<body>
		<h1>로그인</h1>
		<form action="<%=request.getContextPath()%>/loginAction.jsp" method="post">
			<table>
				<tr>
					<td>아이디</td>
					<td>
						<input type="text" name="memberId">
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td>
						<input type="password" name="memberPw">
					</td>
				</tr>
			</table>
			<button type="submit">로그인</button>
		</form>
		<br>
		<a href="<%=request.getContextPath()%>/insertMember.jsp">회원가입</a>
	</body>
</html>