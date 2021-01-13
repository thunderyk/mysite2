package com.javaex.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;
@WebServlet("/user")
public class UserController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		
		
		String action =	request.getParameter("action");
		UserDao userDao = new UserDao();
		
		if("joinForm".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		}else if("join".equals(action)) {
			String id = request.getParameter("uid");
			String password = request.getParameter("pw");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");
			
			UserVo userVo = new UserVo(id,password,name,gender);
			
			userDao.insert(userVo);
			WebUtil.forward(request, response,"/WEB-INF/views/user/joinOk.jsp");
			
		}else if("loginForm".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		}else if("login".equals(action)) {
			String id = request.getParameter("uid");
			String pw = request.getParameter("pw");
			
			UserVo authorMember = userDao.loginMember(id,pw);
			
			
			if(authorMember != null) {
				HttpSession session = request.getSession();
				session.setAttribute("authorMember",authorMember);
				
				WebUtil.forward(request, response, "./main");
			}else {
				/*
				PrintWriter out = response.getWriter();
				out.println("<script>"
						+   "alert('아이디 혹은 비밀번호가 잘못 되었습니다.');"
						+   "location.href='./user?action=loginForm';"
						+   "</script>");
				*/ //로그인 실패시 alert창 뜸
				
				WebUtil.redirect(request, response, "./user?action=loginForm&result=fail");
			}
		}else if("logout".equals(action)) {
			HttpSession session = request.getSession();
			session.removeAttribute("authorMember");
			
			WebUtil.forward(request,response,"/WEB-INF/views/main/index.jsp");
			
		}if("modifyForm".equals(action)) {
			HttpSession session =request.getSession();
			UserVo userVo = (UserVo)session.getAttribute("authorMember");
			userVo = userDao.getMember(userVo.getNo());
			
			request.setAttribute("userVo", userVo);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
		}else if("modify".equals(action)) {
			
			int no = Integer.parseInt(request.getParameter("uno"));
			String id= request.getParameter("uid");
			String password= request.getParameter("password");
			String name= request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo userVo = new UserVo(no,id,password,name,gender);
			userDao.update(userVo);
			
			HttpSession session =request.getSession();
			UserVo authorMember = userDao.loginMember(userVo.getId(),userVo.getPassword());
			session.setAttribute("authorMember",authorMember);
			
			request.setAttribute("userVo", userVo);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
