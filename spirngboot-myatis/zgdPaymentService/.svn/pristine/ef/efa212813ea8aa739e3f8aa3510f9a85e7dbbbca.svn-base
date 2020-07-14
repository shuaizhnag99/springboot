<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page import="com.ule.uhj.util.Constants"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>借款成功</title>
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/c/zgd_lend.css">
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/1110/c/who-box.css">
  </head>

  <body>
	<input type="hidden" id="provice" value="${provice}" />  
    <div id="wrapper" class="clear">
      <ul class="zgd-breadcrumb">
        <li>
          <a href="<%=Constants.get("UHJ_SERVER")%>/uhj/zgd_toZgdPage">掌柜贷</a>
          <i class="icon-bread"></i>
        </li>
        <li>借款成功</li>
      </ul>
      <div class="main zgd-con-det">
        <div class="zgd-lend-con">
          <p class="zgd-suc-txt">合同签署成功!</p>
          <p class="zgd-suc-my">￥${loanAmount}</p>
          <p class="zgd-suc-dt">预计1个工作日内到账</p>
        </div>
        <div class="lend-account-msg">
          <span class="lend-account-tt">收款账户</span>
          <span class="lend-account-det">邮储银行储蓄卡 ${receiveAcc }</span>
        </div>
        <div class="lend-btn-con">
          <a href="<%=Constants.get("UHJ_SERVER")%>/uhj/zgd_toZgdPage" class="zgd-btn zgd-btn-red">
            <span class="zgd-icons zgd-btn-icon"></span>
            <span class="zgd-btn-txt">返回首页</span>
          </a>
        </div>
      </div>
    </div>

    <script src="<%=Constants.get("GLOBAL_SERVER") %>/j/lib/jquery-1.7.2.min.js"></script>
    <script src="<%=Constants.get("GLOBAL_SERVER") %>/j/jend/jend.js"></script>
    <script src="<%=Constants.get("GLOBAL_SERVER") %>/c/my/loan/2016/1110/j/who-box.js"></script>
<%--     <script src="./lendSuccess_files/zgd_lend_suc.js"></script> --%>
</body></html>