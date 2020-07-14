<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.ule.uhj.util.Constants" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
    <meta name="mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="format-detection" content="telephone=no" />
    <link rel="stylesheet" type="text/css" href="<%=Constants.get("UHJ_CSS_SERVER_HTTP") %>/c/my/loan/2016/0506/process.css" />
    <title>掌柜贷进度查询</title>

</head>
<body>
<!--banner-->
<div class="banner">

</div>
<!--header-->
<div class="header">
    <div class="info">
    	<h2>${result.storeName != null ? result.storeName : ''}</h2>
        <p>${result.userName != null ? result.userName : ''}</p>
    </div>
</div>
<!--process-->
<div class="process">
    <ul class="left">
    	 <c:if test="${preLine != null}">
    	 	<li class="on">
	            <div class="ball"></div>
	            <div class="line "></div>
	        </li>
    	 </c:if>
    	 <c:forEach items="${result.reList}" var="list" varStatus="stat"> 
	        <c:if test="${stat.last }">
	        	<li class="on">
		            <div class="ball"></div>
		        </li>
	        </c:if>
	        <c:if test="${!stat.last }">
	        	<li class="on">
		            <div class="ball"></div>
		            <div class="line "></div>
		        </li>
	        </c:if>
         </c:forEach> 
    </ul>

<ul class="right">
	<c:if test="${preLine != null}">
		<li class="tip current">
	    	<h2>${preLine.shouldOperateContent} </h2>
	        <span>${preLine.operationTime}</span>
	        <span>${preLine.opetime}</span>
	        <b></b>
	    </li>
	</c:if>
	
	<c:forEach items="${result.reList}" var="list" varStatus="cstats"> 
    	<c:if test="${list.opeStatus != '10'}">
	    	<li class="tip <c:if test="${preLine == null && cstats.first}">current</c:if>">
		    	<h2>
		        	<c:if test="${list.opeName != ''}">${list.opeName}</c:if>
		        	<c:if test="${list.opeStatus == '0'}">审核拒绝</c:if>
		        	<c:if test="${list.opeStatus == '12'}">已实名认证</c:if>
		        	<c:if test="${list.opeStatus == '14'}">已提交个人信息</c:if>
		        	<c:if test="${list.opeStatus == '20'}">已上传图片</c:if>
		        	<c:if test="${list.opeStatus == '88'}">审批成功</c:if>
		        </h2>
		        <span>${list.operationTime}</span>
		        <span>${list.opeTime}</span>
		        <b></b>
		    </li>
    	</c:if>
    </c:forEach>
</ul>
</div>
</body>
</html>