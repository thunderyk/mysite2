package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUtil;
import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		BoardDao boardDao = new BoardDao();
		
		if("list".equals(action)) {
			List<BoardVo> boardList = boardDao.getBoardList();
			request.setAttribute("boardList", boardList);
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}else if("deleteBoard".equals(action)) {
			int deleteNum = Integer.parseInt(request.getParameter("deleteNum"));
			boardDao.deleteBoard(deleteNum);
			WebUtil.redirect(request, response, "./board?action=list");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
