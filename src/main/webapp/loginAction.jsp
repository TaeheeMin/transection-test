<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="service.*" %>
<%
	request.setCharacterEncoding("utf-8");
	//1. 유효성 검사
	if(session.getAttribute("loginMember") != null){
		response.sendRedirect(request.getContextPath()+"/main.jsp");
		System.out.println("로그인폼-로그인중");
		return;
	}
	
	// 작성 확인
	if(request.getParameter("memberId") == null || request.getParameter("memberPw") == null 
		|| request.getParameter("memberId").equals("") || request.getParameter("memberPw").equals("")){
		System.out.println("로그인 정보 입력 필요");
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		return;
	}
	
	Member paramMember = new Member();
	paramMember.setMemberId(request.getParameter("memberId"));
	paramMember.setMemberPw(request.getParameter("memberPw"));
	
	// model 호출
	MemberService memberService = new MemberService();
	Member resultMember = memberService.login(paramMember);
	
	String redirectUrl = "/login.jsp";
	if(resultMember != null) { // 로그인 결과있음
		System.out.println("로그인 성공");
		session.setAttribute("loginMember", resultMember);
		redirectUrl = "/main.jsp";
		response.sendRedirect(request.getContextPath()+redirectUrl);
		return;
	}
	System.out.println("로그인 실패");
	response.sendRedirect(request.getContextPath()+redirectUrl);
%>