package service;

import java.sql.*;
import dao.*;
import vo.*;

public class MemberService {
	private OutidDao outIdDao;
	private MemberDao memberDao;
	
	// 아이디 중복 확인
	public boolean checkMemberId(String memberId) {
		boolean result = true;
		Connection conn = null;
		
		 try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58", "root", "java1234");
				this.memberDao = new MemberDao();
				result = this.memberDao.checkMemberId(conn, memberId);
				
			} catch (Exception e) {
				e.printStackTrace();
				
			} finally {
				if(conn != null) { 
					try {
						conn.close(); 
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		return result;
	}
	
	// 회원가입
	public int insertMember(Member member) {
		int result = 0;
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58", "root", "java1234");
			this.memberDao = new MemberDao();
			result = this.memberDao.insertMember(conn, member);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if(conn != null) { 
				try {
					conn.close(); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	// 로그인
	public Member login(Member paramMember) {
		Member member = null;
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58", "root", "java1234");
			
			this.memberDao = new MemberDao();
			member = this.memberDao.login(conn, paramMember);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if(conn != null) { 
				try {
					conn.close(); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return member;
	}
	
	// 로직, DB접근과 해지
	// 회원탈퇴
	public int deleteMember(String memberId) {
		int result = 0;
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58", "root", "java1234");
			conn.setAutoCommit(false); // 오토커밋(execute()) 해지
			
			this.outIdDao = new OutidDao();
			// 메서드 안에 지역변수 만들지 않고 필드로 가져와서 사용
			// 메소드별X 클래스 자체에서 필드로 가져와서 사용하는게..???
			
			if(this.outIdDao.insertMemberId(conn, memberId) == 1) {
				this.memberDao = new MemberDao();
				this.memberDao.deleteMember(conn, memberId);
				// 실패시 예외발생시 catch로 이동해 롤백됨
			}
			conn.commit(); // 커밋
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		} finally {
			if(conn != null) { 
				try {
					conn.close(); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result; //성공 = 1 실패 = 0
	}
}
