<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="dao.*" %>
<%
	if(session.getAttribute("loginMember") == null){
		System.out.println("로그인 필요");
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		return;
	}

	Member loginMember = (Member)session.getAttribute("loginMember");
	String memberId = loginMember.getMemberId(); 
	
	// 모델호출
	MemberDao memberDao = new MemberDao();
	int deleteRow = memberDao.deleteMember(memberId);
	
	String redirectUrl = "/main.jsp";
	if(deleteRow == 2){
		session.invalidate();
		redirectUrl = "/login.jsp";
		System.out.println("탈퇴 성공");
		response.sendRedirect(request.getContextPath()+redirectUrl);
		return;
	}
	System.out.println("탈퇴 실패");
	response.sendRedirect(request.getContextPath()+redirectUrl);
%>
<!-- view 없음 -->