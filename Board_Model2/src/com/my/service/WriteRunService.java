package com.my.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.dao.BoardDao;
import com.my.domain.BoardVo;

public class WriteRunService implements IService {

	private BoardDao boardDao = BoardDao.getInstance();
	
	@Override
	public String excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/* 받아온 데이터로 BoardVo 생성 */
		String b_title = request.getParameter("b_title");
		String b_content = request.getParameter("b_content");
		String m_id = request.getParameter("m_id");
		BoardVo boardVo = new BoardVo();
		boardVo.setB_title(b_title);
		boardVo.setB_content(b_content);
		boardVo.setM_id(m_id);
		
		/* DB에 저장 -> 이동할 jsp 정하고 메세지 전달 */
		HttpSession session = request.getSession();
		String view = "";
		int count = boardDao.insertArticle(boardVo);
		if (count > 0) {
			session.setAttribute("message", "write_success");
			view = "redirect:list.my";
		}
		return view;
	}

} //WriteRunService
