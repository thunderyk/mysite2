<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
		
		<div id="aside">
			<h2>게시판</h2>
			<ul>
				<li><a href="">일반게시판</a></li>
				<li><a href="">댓글게시판</a></li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="list">
					<form action="./board?action=search" method="post">
						<div class="form-group text-right">
					    <select name="searchWay">
                            <option selected="selected" value="board_title">제목</option>
                            <option value="board_content">내용</option>
                            <option value="board_titleContent">제목+내용</option>
                            <option value="board_writer">글쓴이</option>
                        </select>
                    
							
							<input type="text" name="searchData" value="">
							<button type="submit" id=btn_search>검색</button>
						</div>
					</form>
					
					
					<table >
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${requestScope.boardList}" var="vo">
								<tr>
									<td>${vo.no}</td>
									<td class="text-left"><a href="./board?action=readBoard&readNum=${vo.no}">${vo.title}</a></td>
									<td>${vo.name}</td>
									<td>${vo.hit}</td>
									<td>${vo.reg_date}</td>
									<td>
										<c:if test="${sessionScope.authorMember.no == vo.user_no }">
											<a href="./board?action=deleteBoard&deleteNum=${vo.no}">[삭제]</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
							<!-- 작성자 본인만 삭제 할 수 있고, 작성자 본인만 삭제 버튼이 나옴 -->
						</tbody>
					</table>
		
					<div id="paging">
						<ul>
							<li><a href="">◀</a></li>
							<li><a href="">1</a></li>
							<li><a href="">2</a></li>
							<li><a href="">3</a></li>
							<li><a href="">4</a></li>
							<li class="active"><a href="">5</a></li>
							<li><a href="">6</a></li>
							<li><a href="">7</a></li>
							<li><a href="">8</a></li>
							<li><a href="">9</a></li>
							<li><a href="">10</a></li>
							<li><a href="">▶</a></li>
						</ul>
						
						
						<div class="clear"></div>
					</div>
					<c:if test="${sessionScope.authorMember != null}">
						<a id="btn_write" href="./board?action=wForm">글쓰기</a>
					</c:if>
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
		
	</div>
	<!-- //wrap -->

</body>

</html>
