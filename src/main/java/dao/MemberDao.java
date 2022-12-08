package dao;

import java.sql.*;
import vo.*;

public class MemberDao {
	// 아이디 중복 검사
	// param String = 사용할 아이디
	// t = 사용가능, f = 사용 불가능
	public boolean checkMemberId(String memberId) {
		boolean result = true;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		 try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58", "root", "java1234");
			String sql = "SELECT t.id"
					+ " FROM ("
					+ " SELECT member_id id FROM member"
					+ " UNION"
					+ " SELECT member_id id FROM outid) t"
					+ " WHERE t.id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				result = false;
				// 결과값 있으면 중복있음 사용불가
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs != null) { rs.close(); }
				if(stmt != null) { stmt.close(); }
				if(conn != null) { conn.close(); }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 회원가입
	public int insertMember(Member member) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		 try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58", "root", "java1234");
			String sql = "INSERT INTO member("
					+ " member_id, member_pw, member_name"
					+ " ) VALUES (?, PASSWORD(?), ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			stmt.setString(3, member.getMemberName());
			row = stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(stmt  != null) { stmt.close(); }
				if(conn != null) { conn.close(); }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	
	// 로그인
	public Member login(Member paramMember) {
		Member resultMember = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		 try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58", "root", "java1234");
			String sql = "SELECT"
					+ " member_id memberId"
					+ ", member_name memberName"
					+ " FROM member"
					+ " WHERE member_id=? AND member_pw = PASSWORD(?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramMember.getMemberId());
			stmt.setString(2, paramMember.getMemberPw());
			rs = stmt.executeQuery();
			while(rs.next()) {
				resultMember = new Member();
				resultMember.setMemberId(rs.getString("memberId"));
				resultMember.setMemberName(rs.getString("memberName"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs != null) { rs.close(); }
				if(stmt != null) { stmt.close(); }
				if(conn != null) { conn.close(); }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultMember;
		// 실패 = null, 성공 = Member객체 
	}
	
	// 회원탈퇴
	public int deleteMember(String memberId) {
		int outIdRow = 0;
		int memberRow = 0;
		Connection conn = null;
		PreparedStatement outIdStmt = null;
		PreparedStatement memberStmt = null;
		
		 try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58", "root", "java1234");
			// auto Commit 해제
			conn.setAutoCommit(false);
			
			// 1-1 outid insert
			String outIdSql = "INSERT INTO outid(member_id, createdate) VALUES(?, NOW())";
			outIdStmt = conn.prepareStatement(outIdSql);
			outIdStmt.setString(1, memberId);
			outIdRow = outIdStmt.executeUpdate(); // autoCommit -> 자동커밋 해제 필요
			
			// 1-2 member delete
			String memberSql = "DELETE FROM member WHERE member_id = ?";
			memberStmt = conn.prepareStatement(memberSql);
			memberStmt.setString(1, memberId);
			memberRow = memberStmt.executeUpdate(); // autoCommit -> 자동커밋 해제 필요
			
			// 1-3 commit
			// 1~2 동시에 처리하는 트랜잭션 처리
			conn.commit();
			
		} catch (Exception e) {
			try {
				conn.rollback();
				// 1~2에서 예외 발생시 롤백
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		} finally {
			try {
				if(outIdStmt != null) { outIdStmt.close(); }
				if(memberStmt != null) { memberStmt.close(); }
				if(conn != null) { conn.close(); }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memberRow+outIdRow;
		// 
	}
}
