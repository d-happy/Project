package com.my.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.dao.BoardDao;
import com.my.domain.BoardVo;
import com.my.domain.PagingDto;
import com.my.util.QueryStringMaker;

public class UpdateService implements IService {
	
	private BoardDao boardDao = BoardDao.getInstance();

	@Override
	public String excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/* PagingDto 데이터 받기 */
		int page = Integer.parseInt(request.getParameter("page"));
		int perPage = Integer.parseInt(request.getParameter("perPage"));
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		PagingDto pagingDto = new PagingDto(page, perPage, searchType, keyword);
		
		/* 수정된 데이터 받아와서 새로운 BoardVo 생성 */
		int b_no = Integer.parseInt(request.getParameter("b_no"));
		String b_title = request.getParameter("b_title");
		String b_content = request.getParameter("b_content");
		String m_id = request.getParameter("m_id");
		BoardVo boardVo = new BoardVo();
		boardVo.setB_no(b_no);
		boardVo.setB_title(b_title);
		boardVo.setB_content(b_content);
		boardVo.setM_id(m_id);
		
		/* 수정된 BoardVo 넘겨줘서 DB 수정 -> PagingDto, b_no를 query로 보내고 메세지 전달 */
		HttpSession session = request.getSession();
		String view = "";
		int count = boardDao.updateArticlt(boardVo);
		if (count > 0) {
			String query = QueryStringMaker.queryMake(pagingDto, true);
			session.setAttribute("message", "update_success");
			view = "redirect:content.my?b_no=" + b_no + query;
		}
		return view;
	}

} //UpdateService
