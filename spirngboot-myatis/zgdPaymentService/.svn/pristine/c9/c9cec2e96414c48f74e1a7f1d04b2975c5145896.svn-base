<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.ule.uhj.util.Constants" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>等待审核通过</title>
		<link rel="stylesheet" type="text/css" href="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/c/wait.css">
	</head>
	<body>
		<div class="header">
			简单<em class="red">3&nbsp;</em>步<em>！</em>轻松申请掌柜贷<em>！</em>
			<span></span>
		</div>
		<div class="wrap">
			<div class="auto">
				<div class="tt3">审核结果</div>
				<div class="content">
					<div class="box_l">
						<c:if test="${applyStatus eq '02'}">
							<div class="info">额，由于综合评定暂不符合要求，您的申请未通过审核哦！</div>
							<span>额度申请未通过，可能由于经营情况、还款能力、负债比、信用记录等多原因造成的${reactivatedDate}。</span>
						</c:if>
						<c:if test="${applyStatus !='02'}">
						<div class="info">您的申请已经被退回！</div>
						</c:if>
						
						<c:if test="${applyStatus eq '10'}">
							<div style="font-weight: bold">原因如下：</div>
							<span>${msg}</span>
<%-- 							<p>您还可以<a class="btn" href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_prePersonInfo.action">重新申请</a></p> --%>
							<p><a class="btn" href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_returnApply.action">重新申请</a></p>
						</c:if>
						<c:if test="${applyStatus eq '04'}">
							<div style="font-weight: bold">原因如下：</div>
							<span>${msg}</span>
							<p>您还可以<a class="btn" href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_prePersonInfo.action">重新申请</a></p>
						</c:if>
						<c:if test="${applyStatus eq '05'}">
							<div style="font-weight: bold">原因如下：</div>
							<span>${reason}${reactivatedDate}</span>
						</c:if>
<%-- 						<c:if test="${applyStatus eq '02'}"> --%>
<!-- 							<div style="font-weight: bold">原因如下：</div> -->
<%-- 							<span>${msg}</span> --%>
<%-- 						</c:if> --%>
					</div>
					<div class="box_r"></div>
				</div>
			</div>
		</div>
	</body>
</html>