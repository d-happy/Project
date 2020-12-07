package com.my.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.my.domain.BoardVo;
import com.my.domain.PagingDto;

public class BoardDao {

	private static BoardDao instance;
	private BoardDao() {}
	public static BoardDao getInstance() {
		if (instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}
	
	public static Connection getConnection() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/orcl");
			Connection conn = ds.getConnection();
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		if (conn != null)  try { conn.close(); }  catch (Exception e) { }
		if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
		if (rs != null)    try { rs.close(); }    catch (Exception e) { }
	}
	
	// 글목록
	public List<BoardVo> getList(PagingDto pagingDto) throws Exception {
		int startRow = pagingDto.getStartRow();
		int endRow = pagingDto.getEndRow();
		
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from" + 
					"    	(select rownum rnum, a.* from" + 
					"       	(select * from tbl_board" +
					"        	 order by b_no desc) a)" + 
					"	  where rnum between ? and ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			List<BoardVo> list = new ArrayList<>();
			while (rs.next()) {
				int b_no = rs.getInt("b_no");
				String b_title = rs.getString("b_title");
				String b_content = rs.getString("b_content");
				String m_id = rs.getString("m_id");
				Timestamp b_date = rs.getTimestamp("b_date");
				int b_readcount = rs.getInt("b_readcount");
				String b_file_path = rs.getString("b_file_path");
				
				BoardVo boardVo = new BoardVo();
				boardVo.setB_no(b_no);
				boardVo.setB_title(b_title);
				boardVo.setB_content(b_content);
				boardVo.setM_id(m_id);
				boardVo.setB_date(b_date);
				boardVo.setB_readcount(b_readcount);
				boardVo.setB_file_path(b_file_path);
				
				list.add(boardVo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return null;
	}
	
	// 글갯수
	public int getTotalCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select count(*) cnt from tbl_board";
			pstmt = conn.prepareStatement(sql);
	 		rs = pstmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt("cnt");
				return count;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return 0;
	}
	
} //BoardDao
