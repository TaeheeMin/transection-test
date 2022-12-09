<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="service.*" %>
<%
	if(session.getAttribute("loginMember") == null){
		System.out.println("로그인 필요");
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		return;
	}

	Member loginMember = (Member)session.getAttribute("loginMember");
	String memberId = loginMember.getMemberId(); 
	
	MemberService memberService = new MemberService();
	int row = memberService.deleteMember(memberId);
	// Dao와 직접 통신 없음. 서비스로 트랜잭션 처리까지 완료한 결과값만 받아옴 

	String redirectUrl = "/main.jsp";
	if(row == 1){
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