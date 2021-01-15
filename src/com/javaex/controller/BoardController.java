package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.util.WebUtil;
import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

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
			
		}else if("readBoard".equals(action)) {
			int readNum = Integer.parseInt(request.getParameter("readNum"));
			BoardVo boardVo = boardDao.readBoard(readNum);
			boardDao.updateHit(boardVo.getHit(), boardVo.getNo());
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		}else if("wForm".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		}else if("writeBoard".equals(action)) {
			String content = request.getParameter("content");
			String title = request.getParameter("title");
			HttpSession session =request.getSession();
			UserVo userVo = (UserVo)session.getAttribute("authorMember");
			
			BoardVo boardVo = new BoardVo(title,content,userVo.getNo());
			boardDao.insertBoard(boardVo);
			WebUtil.redirect(request, response, "./board?action=list");
		}else if("mForm".equals(action)) {
			int modifyNum = Integer.parseInt(request.getParameter("modifyNum"));
			BoardVo boardVo = boardDao.readBoard(modifyNum);
			request.setAttribute("boardVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}else if("modifyBoard".equals(action)) {
			String content = request.getParameter("content");
			String title = request.getParameter("title");
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardVo boardVo = new BoardVo(no, title, content);
			boardDao.modifyBoard(boardVo);
			WebUtil.redirect(request, response, "./board?action=list");
		}else if("search".equals(action)) {
			
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
