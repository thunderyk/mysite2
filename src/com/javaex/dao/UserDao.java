package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private void dbConnect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb"); // db url, 아이디, 비밀번호

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void dbDisConnect() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public int insert(UserVo userVo) {
		int count = 0;
		dbConnect();
		try {

			String query = "";
			query += "insert into users values(seq_users_no.nextval,?,?,?,?)";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			count = pstmt.executeUpdate();
			System.out.println("[" + count + "건이 등록되었습니다.]");

		} catch (Exception e) {
			System.out.println("insert error: " + e);
		}
		dbDisConnect();
		return count;
	}
	public void update(UserVo userVo) {
		dbConnect();
		int count;
		
		try {
			String query="";
			query+="update users ";
			query+=		  "set id = ?,";
			query+=		  "password = ?, ";
			query+=		  "name = ?, ";
			query+=		  "gender = ? ";
			query+="where no = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			pstmt.setInt(5, userVo.getNo());
			
			count = pstmt.executeUpdate();
			System.out.println("[" + count + "건이 수정되었습니다.]");

		}catch(Exception e) {
			System.out.println("update error: "+e);
		}
		dbDisConnect();
	}
	public UserVo getMember(String id) {
		dbConnect();

		UserVo uVo = new UserVo();
		try {
			String query="";
			query+="select no, ";
			query+=		  "id, ";
			query+=		  "password, ";
			query+=		  "name, ";
			query+=		  "gender ";
			query+="from users ";
			query+="where id = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				uVo.setNo(rs.getInt("no"));
				uVo.setId(rs.getString("id"));
				uVo.setPassword(rs.getString("password"));
				uVo.setName(rs.getString("name"));
				uVo.setGender(rs.getString("gender"));
			}
		}catch(Exception e) {
			System.out.println("getMember(String) error: "+e);
		}
		dbDisConnect();
		return uVo;
	}
	
	public UserVo getMember(int no) {
		dbConnect();

		UserVo uVo = new UserVo();
		try {
			String query="";
			query+="select no, ";
			query+=		  "id, ";
			query+=		  "password, ";
			query+=		  "name, ";
			query+=		  "gender ";
			query+="from users ";
			query+="where no = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				uVo.setNo(rs.getInt("no"));
				uVo.setId(rs.getString("id"));
				uVo.setPassword(rs.getString("password"));
				uVo.setName(rs.getString("name"));
				uVo.setGender(rs.getString("gender"));
			}
		}catch(Exception e) {
			System.out.println("getMember(int) error: "+e);
		}
		dbDisConnect();
		return uVo;
	}
	
	public UserVo loginMember(String id, String password) {
		dbConnect();

		UserVo uVo = null;
		try {
			String query="";
			query+="select no, ";
			query+=		  "name ";
			query+="from users ";
			query+="where id = ? ";
			query+="and password = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				uVo = new UserVo(no,name);
			}
		}catch(Exception e) {
			System.out.println("getMember error: "+e);
		}
		dbDisConnect();
		return uVo;
	}

	
}
