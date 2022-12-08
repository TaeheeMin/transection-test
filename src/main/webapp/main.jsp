<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
	// 1. 유효성 검사
	// -> 이 페이지를 계속 실행해야하는지 확인
	// 로그인X -> 로그인으로
	if(session.getAttribute("loginMember") == null){
		System.out.println("로그인 필요");
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		return;
	}
	
	Member loginMember = (Member)session.getAttribute("loginMember");
	/*
		정석방법
		Object obj = session.getAttribute("loginMember");
		Member loginMember = (Member)obj;
	*/
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>홈</title>
	</head>
	
	<body>
		<h1><%=loginMember.getMemberName() %>님</h1>
		<a href="<%=request.getContextPath()%>/deleteMember.jsp">회원탈퇴</a>
		<a href="<%=request.getContextPath()%>/logout.jsp">로그아웃</a>
	</body>
</html>