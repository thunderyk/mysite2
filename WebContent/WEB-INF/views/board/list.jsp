<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

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
			
			<c:set var="totalData" value="${fn:length(boardList)}"></c:set> <!-- 게시판 전체 글 수 가져오기 list안에 들어 있는거 갯수-->
			<c:set var="dataPerPage" value="10"></c:set> <!-- 페이당 몇개의 글을 담을 것이가 -->
			<c:set var="up" value="${totalData/dataPerPage}"></c:set> 
			<c:set var="pageCount" value="${up+(1-(up%1))%1}"></c:set> <!-- 총 몇개의 페이지가 필요한가 -->

			<c:choose>
				<c:when test="${param.currentPage == null}"> <!-- 파라미터가 없으면 현재 페이지를 1로 -->
					<c:set var="currentPage" value="1"></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="currentPage" value="${param.currentPage}"></c:set> <!-- 파라미터 값에 따라 현재 페이지 바뀜 -->
				</c:otherwise>
			</c:choose>
			
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
						<!-- forEach문을 수행할 때 list에 담겨 있는 순서대로 시작과 끝으로 데이터를 가져옴(계산해서 1페이지면 list의 0번부터 9번까지 10개를 가져옴) -->
							<c:forEach items="${requestScope.boardList}" var="vo" begin="${dataPerPage*(currentPage-1)}" end="${(dataPerPage*currentPage)-1}">
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
					<br>
					<div id="paging">
						<ul>
							<c:choose><!-- 이전페이지 버튼 -->
								<c:when test="${currentPage == 1}"> <!-- 현재 페이지가 1페이지면 이전 페이지로 못감 -->
									<li>◀</li>
								</c:when>
								<c:otherwise>
									<li><a href="./board?action=list&currentPage=${currentPage-1}">◀</a></li>
								</c:otherwise>
							</c:choose>
							
							
							<c:forEach var="i" begin="1" end="${pageCount}"> <!-- 밑에 보여질 페이지들과 링크 -->
								<c:choose>
									<c:when test="${currentPage == i}">
										<li><a href="./board?action=list&currentPage=${i}" class="active">${i}</a></li>	
									</c:when>
									<c:otherwise>
										<li><a href="./board?action=list&currentPage=${i}">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							
							<c:choose><!-- 다음 페이지로 가기 -->
								<c:when test="${currentPage == pageCount}"> <!-- 현재 페이지가 마지막 페이지면 다음 페이지로 못감 -->
									<li>▶</li>
								</c:when>
								<c:otherwise>
									<li><a href="./board?action=list&currentPage=${currentPage+1}">▶</a></li>
								</c:otherwise>
							</c:choose>
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
