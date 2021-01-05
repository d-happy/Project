package com.my.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.dao.BoardDao;
import com.my.domain.PagingDto;
import com.my.util.QueryStringMaker;

public class DeleteService implements IService {
	
	private BoardDao boardDao = BoardDao.getInstance();

	@Override
	public String excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/* PagingDto 데이터 받기 */
		int page = Integer.parseInt(request.getParameter("page"));
		int perPage = Integer.parseInt(request.getParameter("perPage"));
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		PagingDto pagingDto = new PagingDto(page, perPage, searchType, keyword);
		
		/* b_no 받아서 DB에서 해당글 삭제 -> PagingDto를 query로 보내고 메세지 전달  */
		int b_no = Integer.parseInt(request.getParameter("b_no"));
		HttpSession session = request.getSession();
		String view = "";
		int count = boardDao.deleteArticle(b_no);
		if (count > 0) {
			String query = QueryStringMaker.queryMake(pagingDto, false);
			session.setAttribute("message", "delete_success");
			view = "redirect:list.my?" + query;
		}
		return view;
	}

} //DeleteService
