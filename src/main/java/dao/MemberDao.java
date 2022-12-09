package dao;

import java.sql.*;
import vo.*;

public class MemberDao {
	// 아이디 중복 검사
	// param String = 사용할 아이디
	// t = 사용가능, f = 사용 불가능
	public boolean checkMemberId(Connection conn, String memberId) throws Exception {
		boolean result = true;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
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
		
		rs.close();
		stmt.close();
		return result;
	}
	
	// 회원가입
	public int insertMember(Connection conn, Member member) throws Exception {
		int row = 0;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO member("
				+ " member_id, member_pw, member_name"
				+ " ) VALUES (?, PASSWORD(?), ?)";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, member.getMemberId());
		stmt.setString(2, member.getMemberPw());
		stmt.setString(3, member.getMemberName());
		row = stmt.executeUpdate();
		
		stmt.close();
		return row;
	}
	
	// 로그인
	public Member login(Connection conn, Member paramMember) throws Exception {
		Member resultMember = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
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
		
		rs.close();
		stmt.close();
		
		return resultMember;
		// 실패 = null, 성공 = Member객체 
	}
	
	// 회원탈퇴
	public int deleteMember(Connection conn, String memberId) throws Exception{
		PreparedStatement stmt = null;
		int resultRow = 0;
		
		String sql = "DELETE FROM member WHERE member_id = ?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, memberId);
		resultRow = stmt.executeUpdate(); 
		
		stmt.close();
		
		return resultRow;
	}
}
