package dao;

import java.sql.*;

public class OutidDao {
	// 탈퇴아이디 입력
	public int insertMemberId(String memberId) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		 try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db58", "root", "java1234");
			String sql = "INSERT INTO outid(member_id, createdate) VALUES(?, NOW())";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			row = stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}
}
