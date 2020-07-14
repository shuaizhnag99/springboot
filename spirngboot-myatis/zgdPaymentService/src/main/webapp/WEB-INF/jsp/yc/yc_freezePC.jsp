<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.ule.uhj.util.Constants" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>掌柜贷提示页面</title>
		<link rel="stylesheet" type="text/css" href="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/c/wait.css">
	</head>
	<body>
		<div class="header">
			简单<em class="red">3&nbsp;</em>步<em>！</em>轻松申请掌柜贷<em>！</em>
			<span></span>
		</div>
		<div class="wrap">
			<div class="auto">
				<div class="tt3">提示信息</div>
				<div class="content">
					<div class="box_l">
							<div class="info">亲，手机版掌柜贷已正式上线啦，申请更简单，审批更快捷，赶紧扫描右侧二维码下载邮掌柜APP进行使用吧</div>
							<span>由于掌柜贷产品升级，所有未成功申请开通掌柜贷的用户均需要去邮掌柜APP中申请开通。</span>
					</div>
					<div class="box_r"></div>
				</div>
			</div>
		</div>
	</body>
</html>