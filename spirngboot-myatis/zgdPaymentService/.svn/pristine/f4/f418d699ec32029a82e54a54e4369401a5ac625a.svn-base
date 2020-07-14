<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <p id="page" style=" position:absolute; right:20px;"> 
	<input type="hidden" id="beginPageNumber" name="beginPage" value="${page.beginPage}" />
	<c:if test="${page.totalPage gt page.beginPage}">
		<a href="javascript:gotoPage(1);">首页</a>
	</c:if>
	<c:choose>
		<c:when test="${page.firstPage eq 1}">
			<a href="javascript:gotoPage(${page.beginPage-1});">上一页</a>
		</c:when>
    </c:choose>
	<c:choose>
		<c:when test="${page.totalPage gt page.beginPage}">
			<a href="javascript:gotoPage(${page.beginPage+1});">下一页</a>
		</c:when>
    </c:choose>
        第${page.beginPage}页
        共${page.totalPage}页
        <c:if test="${page.total ne 0}">共${page.total}条</c:if>
        转至
	<input id="toPage" class="Bgray tac" type="text" value="" size="2">
	页  
	<a href="javascript:gotoPage($('#toPage').val());">Go</a>
</p>
