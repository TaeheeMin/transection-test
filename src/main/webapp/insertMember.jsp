<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 1. 유효성 검사
	// -> 이 페이지를 계속 실행해야하는지 확인
	// 로그인O -> 실행X
	if(session.getAttribute("loginMember") != null){
		System.out.println("회원가입-로그인중");
		response.sendRedirect(request.getContextPath()+"/main.jsp");
		return;
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원가입</title>
	</head>
	
	<body>
		<h1>회원가입</h1>
		<a href="<%=request.getContextPath()%>/login.jsp">홈으로</a>
		<form method="post" action="<%=request.getContextPath()%>/insertMemberAction.jsp">
			<table>
				<tr>
					<td>아이디</td>
					<td>
						<input type="text" name="memberId">
					</td>
				</tr>
				<tr>
					<td>이름</td>
					<td>
						<input type="text" name="memberName">
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td>
						<input type="password" name="memberPw">
					</td>
				</tr>
			</table>
			<button type="submit">회원가입</button>
		</form>
	</body>
</html>