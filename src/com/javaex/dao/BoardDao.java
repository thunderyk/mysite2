package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

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
	public void deleteBoard(int num) {
		dbConnect();
		try {
			String query="";
			query="delete from board where no = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,num);
			
			pstmt.executeQuery();
			
		}catch(Exception e) {
			System.out.println("deleteBoard error: "+e);
		}
		dbDisConnect();
	}
	
	public List<BoardVo> getBoardList(){
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		dbConnect();
		try {
			String query="";
			query+="select b.no, "; 
			query+=	"      title, "; 
			query+=	"      name, "; 
			query+=	"      hit, "; 
			query+="       TO_CHAR(reg_date,'YYYY-MM-DD HH:MM:SS') reg_date, "; 
			query+=	"      user_no "; 
			query+="from board b, users u ";
			query+="where b.user_no = u.no";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo boardVo = new BoardVo();
				boardVo.setNo(rs.getInt(1));
				boardVo.setTitle(rs.getString("title"));
				boardVo.setName(rs.getString("name"));
				boardVo.setHit(rs.getInt("hit"));
				boardVo.setReg_date(rs.getString("reg_date"));
				boardVo.setUser_no(rs.getInt("user_no"));
				boardList.add(boardVo);
			}
		}catch(Exception e) {
			System.out.println("getBoardList error: "+e);
		}
		
		dbDisConnect();
		
		
		return boardList;
	}
}
