package com.my.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.dao.BoardDao;
import com.my.domain.BoardVo;
import com.my.domain.PagingDto;

public class ContentService implements IService {
	
	private BoardDao boardDao = BoardDao.getInstance();

	@Override
	public String excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/* list.jsp에서 받은 pagingDto 데이터 content.jsp에 보내기 */
		int page = 1;
		String pageStr = request.getParameter("page");
		if (pageStr != null && !pageStr.equals("")) {
			page = Integer.parseInt(pageStr);
		}
		
		int perPage = 10;
		String perPageStr = request.getParameter("perPage");
		if (perPageStr != null && !perPageStr.equals("")) {
			perPage = Integer.parseInt(perPageStr);
		}
		
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		int totalArticle = boardDao.getCount(searchType, keyword);
		
		PagingDto pagingDto = new PagingDto();
		pagingDto.setPaging(page, perPage, totalArticle, searchType, keyword);
		request.setAttribute("pagingDto", pagingDto);
		
		/* 선택된 boardVo 찾기, 조회수 증가 content.jsp에 보내기 */
		int b_no = Integer.parseInt(request.getParameter("b_no"));
		BoardVo boardVo = boardDao.selectArticle(b_no);
		request.setAttribute("boardVo", boardVo);
		int count = boardDao.updateView(b_no);
		String view = "";
		if (count > 0) {
			view = "content";
		}
		return view;
	}

} //ContentService
