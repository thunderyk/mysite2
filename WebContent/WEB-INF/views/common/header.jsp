<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo" %>
<%
	UserVo authorVo = (UserVo)session.getAttribute("authorMember");
%>

		<div id="header">
			<h1><a href="./main">MySite</a></h1>
			
			<%if(authorVo == null){ %>		 
				<ul>
					<li><a href="./user?action=loginForm">로그인</a></li>
					<li><a href="./user?action=joinForm">회원가입</a></li>
				</ul>
			<%}else{ %>
				<ul>
					<li><%=authorVo.getName() %> 님 안녕하세요^^</li>
					<li><a href="./user?action=logout">로그아웃</a></li>
					<li><a href="./user?action=modifyForm">회원정보수정</a></li>
				</ul>
			<%} %>
			
		</div>
		<!-- //header -->

		<div id="nav">
			<ul>
				<li><a href="./guest?action=addList">방명록</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="">게시판</a></li>
				<li><a href="">입사지원서</a></li>
			</ul>
			<div class="clear"></div>
		</div>