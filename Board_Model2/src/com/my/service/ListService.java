package com.my.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.dao.BoardDao;
import com.my.domain.BoardVo;
import com.my.domain.PagingDto;
import com.my.util.QueryStringMaker;

public class ListService implements IService {

	private BoardDao boardDao = BoardDao.getInstance();
	
	@Override
	public String excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/* pagingDto 데이터 list.jsp에 보내기 */
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
		
		/* pagingDto 사용해서 얻은 boardList 데이터 list.jsp에 보내기 */
		List<BoardVo> boardList = boardDao.getList(pagingDto);
		request.setAttribute("boardList", boardList);
		
		return "list";
	}
	
} //ListService
