<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	session.invalidate(); // 세션폐기하고 사라짐 = 로그아웃 -> 새로운 세션 부여 받음
	System.out.println("로그아웃");
	response.sendRedirect(request.getContextPath()+"/login.jsp");
%>