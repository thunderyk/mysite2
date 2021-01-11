package com.javaex.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.javaex.vo.GuestBookVo;

public class GuestBookDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private void dbConnect() {
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		conn = DriverManager.getConnection(url, "webdb", "webdb"); // db url, 아이디, 비밀번호
		}catch (ClassNotFoundException e) {
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
	
	public void guestInsert(GuestBookVo guestBookVo) {
		dbConnect();
		try {
			String query="";
			query+="insert into guestbook values("; 
			query+=	"      seq_no.nextval, "; 
			query+=	"      ?, "; 
			query+=	"      ?, "; 
			query+=	"      ?, "; 
			query+="       SYSDATE)";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestBookVo.getName());
			pstmt.setString(2, guestBookVo.getPassword());
			pstmt.setString(3, guestBookVo.getContent());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("guestInsert error: "+e);
		}
		dbDisConnect();
	}
	
	public List<GuestBookVo> getGuestBookList(){
		List<GuestBookVo> guestBookList = new ArrayList<GuestBookVo>();
		
		dbConnect();
		try {
			String query="";
			query+="select no, "; 
			query+=	"      name, "; 
			query+=	"      password, "; 
			query+=	"      content, "; 
			query+="       reg_date "; 
			query+="from guestbook";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GuestBookVo guestBookVo = new GuestBookVo();
				guestBookVo.setNo(rs.getInt("no"));
				guestBookVo.setName(rs.getNString(2));
				guestBookVo.setPassword(rs.getNString(3));
				guestBookVo.setContent(rs.getNString(4));
				guestBookVo.setDate(rs.getTimestamp(5));
				guestBookList.add(guestBookVo);
			}
			
		}catch(Exception e) {
			System.out.println("1.error: "+e);
		}
		dbDisConnect();
		return guestBookList;
	}
	public GuestBookVo getGuestBook(int num) {
		dbConnect();
		GuestBookVo guestBookVo = new GuestBookVo();
		try {
			String query="";
			query+="select no, "; 
			query+=	"      name, "; 
			query+=	"      password, "; 
			query+=	"      content, "; 
			query+="       reg_date "; 
			query+="from guestbook ";
			query+="where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				guestBookVo.setNo(rs.getInt("no"));
				guestBookVo.setName(rs.getNString(2));
				guestBookVo.setPassword(rs.getNString(3));
				guestBookVo.setContent(rs.getNString(4));
				guestBookVo.setDate(rs.getTimestamp(5));
			}
			
		}catch(Exception e) {
			System.out.println("getGuestBook error: "+e);
		}
		dbDisConnect();
		return guestBookVo;
	}
	public void deleteGuestBook(int num) {
		dbConnect();
		try {
			String query="";
			query+="delete from guestbook where no= ?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("Delete Error: "+e);
		}
		dbDisConnect();
	}
}
