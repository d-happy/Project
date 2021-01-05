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
	private BoardDao() { /* singleton */ } 
	public static BoardDao getInstance() {
		if (instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}
	
	private static Connection getConnection() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			Connection conn = ds.getConnection();
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		if (conn != null)  try { conn.close(); }  catch (Exception e) {	}
		if (pstmt != null) try { pstmt.close(); } catch (Exception e) {	}
		if (rs != null)    try { rs.close(); }    catch (Exception e) {	}
	}
	private static void close(Connection conn, PreparedStatement pstmt) {
		if (conn != null)  try { conn.close(); }  catch (Exception e) {	}
		if (pstmt != null) try { pstmt.close(); } catch (Exception e) {	}
	}
	
	// 글 갯수
	public int getCount(String searchType, String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) cnt from tbl_board";
			if (searchType != null && keyword != null 
					&& !searchType.equals("") && !keyword.equals("")) {
				sql += "  where " + searchType + " like '%" + keyword + "%'";
			}
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt("cnt");
				return cnt;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return 0;
	}
	
	// 글 목록
	public List<BoardVo> getList(PagingDto pagingDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int startRow = pagingDto.getStartRow();
		int endRow = pagingDto.getEndRow();
		String searchType = pagingDto.getSearchType();
		String keyword = pagingDto.getKeyword();
		
		try {
			String sql = "select * from";
				sql += "	(select rownum rnum, a.* from";
				sql += " 		(select * from tbl_board";
			if (searchType != null && keyword != null 
					&& !searchType.equals("") && !keyword.equals("")) {
				sql += "  		where " + searchType + " like '%" + keyword + "%'";
			}
				sql += "		order by b_no desc) a)";
				sql += "  where rnum between ? and ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			List<BoardVo> boardList = new ArrayList<>();
			while (rs.next()) {
				int b_no = rs.getInt("b_no");
				String b_title = rs.getString("b_title");
				String b_content = rs.getString("b_content");
				String m_id = rs.getString("m_id");
				Timestamp b_date = rs.getTimestamp("b_date");
				int b_view = rs.getInt("b_view");
				
				BoardVo boardVo = new BoardVo(b_no, b_title, b_content, m_id, b_date, b_view);
				boardList.add(boardVo);
			}
			return boardList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return null;
	}
	
	// 글 선택
	public BoardVo selectArticle(int b_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from tbl_board"
					+ "   where b_no = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String b_title = rs.getString("b_title");
				String b_content = rs.getString("b_content");
				String m_id = rs.getString("m_id");
				Timestamp b_date = rs.getTimestamp("b_date");
				int b_view = rs.getInt("b_view");
				
				BoardVo boardVo = new BoardVo(b_no, b_title, b_content, m_id, b_date, b_view);
				return boardVo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		return null;
	}
	
	// 조회수 증가
	public int updateView(int b_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "update tbl_board set"
					+ "			b_view = b_view + 1"
					+ "	  where b_no = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_no);
			int count = pstmt.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		return 0;
	}
	
	// 글 작성
	public int insertArticle(BoardVo boardVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "insert into tbl_board (b_no, b_title, b_content, m_id)"
					+ "   values (seq_board_bno.nextval, ?, ?, ?)";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardVo.getB_title());
			pstmt.setString(2, boardVo.getB_content());
			pstmt.setString(3, boardVo.getM_id());
			int count = pstmt.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		return 0;
	}
	
	// 글 수정
	public int updateArticlt(BoardVo boardVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "update tbl_board set"
					+ "			b_title = ?,"
					+ "			b_content = ?"
					+ "   where b_no = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardVo.getB_title());
			pstmt.setString(2, boardVo.getB_content());
			pstmt.setInt(3, boardVo.getB_no());
			int count = pstmt.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		return 0;
	}
	
	// 글 삭제
	public int deleteArticle(int b_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "delete from tbl_board"
					+ "   where b_no = ?";
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_no);
			int count = pstmt.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		return 0;
	}
	
} //BoardDao
