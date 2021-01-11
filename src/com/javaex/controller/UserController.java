package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;
@WebServlet("/user")
public class UserController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
			UserVo userVo = userDao.getMember(id);
			
			if(pw.equals(userVo.getPassword())) {
				System.out.println("비밀번호 맞음");
			}else {
				System.out.println("비밀번호 틀림");
			}
		}else if("modifyForm".equals(action)) {
			String id = request.getParameter("uid");	
			UserVo userVo = userDao.getMember(id);
			
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
			
			request.setAttribute("userVo", userVo);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
