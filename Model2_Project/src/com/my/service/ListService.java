package com.my.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.dao.BoardDao;
import com.my.domain.BoardVo;
import com.my.domain.PagingDto;

public class ListService implements InterService {
	
	private BoardDao boardDao = BoardDao.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// PagingDto
		PagingDto pagingDto = new PagingDto();
		
		// perPage
		int perPage = 10;
		String strPerPage = request.getParameter("perPage");
		if (strPerPage != null && !strPerPage.equals("")) {
			perPage = Integer.parseInt(strPerPage);
		} 
		
		// totalCount
		int totalCount = boardDao.getTotalCount();
		
		// page
		int page = 1;
		String strPage = request.getParameter("page");
		if (strPage != null) {
			page = Integer.parseInt(strPage);
		} 
		
		pagingDto.setPagingData(perPage, totalCount, page);
		request.setAttribute("pagingDto", pagingDto);
		
		List<BoardVo> list = boardDao.getList(pagingDto);
		request.setAttribute("list", list);
		
		return "list";
	}

} //ListService
