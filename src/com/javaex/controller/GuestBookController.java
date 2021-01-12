package com.javaex.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;
@WebServlet("/guest")
public class GuestBookController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		GuestBookDao guestBookDao = new GuestBookDao();
		
		if("addList".equals(action)) {
			
			List<GuestBookVo>guestBookList = guestBookDao.getGuestBookList();
			request.setAttribute("guestBookList", guestBookList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		}else if("add".equals(action)){
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			
			GuestBookVo guestBookVo = new GuestBookVo(name,password,content);
			guestBookDao.guestInsert(guestBookVo);
			
			WebUtil.redirect(request, response, "./guest?action=addList");
		}else if("deleteForm".equals(action)) {
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		}else if("delete".equals(action)) {
			
			String password = request.getParameter("pass");
			int deleteNum = Integer.parseInt(request.getParameter("no"));
			
			GuestBookVo guestBookVo = guestBookDao.getGuestBook(deleteNum);
			
			if(password.equals(guestBookVo.getPassword())){
				guestBookDao.deleteGuestBook(deleteNum);
				WebUtil.redirect(request, response, "./guest?action=addList");
				
			}else{
				PrintWriter out = response.getWriter();
				out.println("<script>"
						+   "alert('아이디 혹은 비밀번호가 잘못 되었습니다.');"
						+   "location.href='./guest?action=addList';"
						+   "</script>");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
