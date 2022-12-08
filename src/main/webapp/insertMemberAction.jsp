<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>
<%
	request.setCharacterEncoding("utf-8");
	// 1. 유효성 검사
	if(session.getAttribute("loginMember") != null){
		System.out.println("로그인폼-로그인중");
		response.sendRedirect(request.getContextPath()+"/main.jsp");
		return;
	}
	
	// 작성 확인
	if(request.getParameter("memberId") == null || request.getParameter("memberPw") == null || request.getParameter("memberName") == null
		|| request.getParameter("memberId").equals("") || request.getParameter("memberPw").equals("") || request.getParameter("memberName").equals("")){
		System.out.println("회원가입 입력 필요");
		response.sendRedirect(request.getContextPath()+"/lnsertMember.jsp");
		return;
	}
	
	String memberName = request.getParameter("memberName");
	String memberId = request.getParameter("memberId");
	String memberPw = request.getParameter("memberPw");
	
	// model 호출
	// 1-1 아이디 확인
	MemberDao memberDao = new MemberDao();
	boolean resultCheck = memberDao.checkMemberId(memberId);
	// t -> 가입가능
	if(resultCheck == false){
		response.sendRedirect(request.getContextPath()+"/insertMember.jsp");
		return;
	}
	// 1-2 insert 
	Member member = new Member();
	member.setMemberId(memberId);
	member.setMemberPw(memberPw);	
	member.setMemberName(memberName);
	int insertRow = memberDao.insertMember(member);
	
	String redirectUrl = "/insertMember.jsp";
	if(insertRow == 1){
		redirectUrl = "/login.jsp";
		System.out.println("가입성공");
		response.sendRedirect(request.getContextPath()+redirectUrl);
		return;
	}
	System.out.println("가입실패");
	response.sendRedirect(request.getContextPath()+redirectUrl);
%>