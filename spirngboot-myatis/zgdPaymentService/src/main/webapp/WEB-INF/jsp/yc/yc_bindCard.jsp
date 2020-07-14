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
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>绑卡</title>
    <link rel="stylesheet" href="<%= Constants.get("UHJ_CSS_SERVER" ) %>/c/my/loan/2016/0111/c/zgd_lend.css">
    <link rel="stylesheet" href="<%= Constants.get("UHJ_CSS_SERVER" ) %>/c/my/loan/2016/0111/c/zgd_popbox.css">
  </head>
  <body>
	<input type="hidden" id="uhj_server" value="<%=Constants.get("UHJ_SERVER") %>" /> 
	<input type="hidden" id="userOnlyId" value="${js.userOnlyId}" />  
	<input type="hidden" id="applyFlag" value="${applyFlag}" />   
    <div id="wrapper" class="clear">
      <ul class="zgd-breadcrumb">
        <li>
           <a href="<%=Constants.get("UHJ_SERVER")%>/uhj/zgd_toZgdPage">掌柜贷</a>
          <i class="icon-bread"></i>
        </li>
        <li>银行卡绑定</li>
      </ul>
      <div class="main zgd-bind-card">
        <div class="zgd-lend-header">
          <span class="zgd-icons zgd-bind-iconh"></span>
          <span class="zgd-btn-txt">银行卡绑定</span>
        </div>
        <div class="zgd-bindcard-con">
          <div class="zgd-bindcard-t">
            <span class="zgd-icons zgd-bind-iconb"></span>
            <span class="zgd-btn-txt">请填写以下银行卡信息，作为您借款的收还款账户，目前仅支持<strong>邮储银行借记</strong>卡。</span>
          </div>
          <form class="form-bindcard">
            <div class="zgd-bindcard-form">
              <div class="formRow">
                <div class="label">持卡人姓名</div>
                <div class="inputboxWrapperStd">
                  <input type="text" id="bindCardName" name="bindCardName" value="${js.userName}" class="inputbox" placeholder="请确认您的真实姓名" readonly="" warn-msg="请确认您的真实姓名" error-msg="请确认您的真实姓名">
                </div>
                <div class="msgHolder" style="readonly: block">
                  <span class="suc"><i class="zgd-icons icons-in"></i><small></small></span>
                </div>
              </div>
              <div class="formRow">
                <div class="label">身份证号</div>
                <div class="inputboxWrapperStd">
                  <input type="text" id="bindCardIDCard" name="bindCardIDCard" value="${js.certNo}" maxlength="18" class="inputbox" placeholder="请确认您的身份证号码" readonly=""  warn-msg="请确认您的身份证号码" error-msg="请确认您的的身份证号码">
                </div>
                <div class="msgHolder" style="readonly: block">
                  <span class=""><i class="zgd-icons icons-in"></i><small></small></span>
                </div>
              </div>
              <div class="formRow">
                <div class="label">储蓄卡号</div>
                <div class="inputboxWrapperStd">
                  <input type="text" id="bindCardNo" name="bindCardNo" maxlength="21" class="inputbox" placeholder="请输入本人银行卡号" warn-msg="请输入本人银行卡号" error-msg="请填写正确的储蓄卡号">
                </div>
                <div class="msgHolder">
                  <span class=""><i class="zgd-icons icons-in"></i><small></small></span>
                </div>
              </div>
              <div class="formRow">
                <div class="label">手机号</div>
                <div class="inputboxWrapperStd">
                  <input type="text"  id="bindCardMobile" maxlength="11" name="bindCardMobile" class="inputbox" placeholder="请输入银行卡预留手机号" warn-msg="请输入银行卡预留手机号" error-msg="请填写正确的手机号码">
                </div>
                <div class="msgHolder">
                  <span class=""><i class="zgd-icons icons-in"></i><small></small></span>
                </div>
              </div>
              <div class="formRow">
                <div class="label">验证码</div>
                <div class="inputboxWrapperStd">
                  <input type="text" id="randomCode" maxlength="6" name="randomCode" class="inputbox" placeholder="请输入验证码" warn-msg="请输入验证码" error-msg="请输入验证码">
                </div>
                <div class="msgValidateCode">
                  <div class="getcode" id="getValidateCode">获取验证码</div>
                  <!-- <a href="javascript:void(0);" class="getcode getcode-count">59秒后重新获取</a> -->
                </div>
                <div class="msgHolder">
                  <span class=""><i class="zgd-icons icons-in"></i><small></small></span>
                </div>
              </div>
              <div class="formRow">
                <div class="label">&nbsp;</div>
                <div class="inputboxWrapperStd">
                  <div class="zgd-lend-check y-check-con" style="line-height: 32px;">
                    <input type="hidden" id="agreePrivacy" name="agreePrivacy" value="1">
                    <span class="zgd-check checked"></span>
                    <span class="zgd-check-txt">我接受
                      <a target="_blank" href="http://help.ule.com/helpcenter/2012-01-13-03-43-48.html">《邮乐快捷支付服务及相关协议》</a>并<strong>开通快捷支付</strong></span>
                  </div>
                </div>
                <div class="msgHolder">
                  <span class=""><i class="zgd-icons icons-in"></i><small></small></span>
                </div>
              </div>
              <div class="formRow">
                <div class="label">&nbsp;</div>
                <div class="inputboxWrapperStd">
                  <div class="zgd-btn zgd-btn-bdc zgd-btn-red zgd-btn-gray">
                    <span class="zgd-icons zgd-btnbindcard-icon"></span>
                    <span class="zgd-btn-txt">关联银行卡</span>
                  </div>
                </div>
                <div class="inputboxWrapperStd" style="margin-left: 20px;">
                  <a class="zgd-btn zgd-btn-red" href="<%=Constants.get("UHJ_SERVER")%>/uhj/zgd_toZgdPage.action">
                    <span class="zgd-btn-txt">返回首页</span>
                  </a>
                 </div>
              </div>
            </div>
          </form>

        </div>

      </div>
    </div>
<!-- popbox -->

<!-- popbox tobindCard page -->
    <div class="popbox popbox-msgtips">
      <div class="popbox-bg"></div>
      <div class="popbox-center">
          <div class="popbox-main">
            <div class="popbox-title">
                  <h4>提示信息</h4>
                  <span class="close close-pop" title="关闭浮层">关闭</span>
              </div>
              <div class="popbox-contentframe">
                
                <div  class="popbox-bindcard-txt" style="text-align: center;">
                
                </div>

              </div>

              <div class="popbox-btn-con">
                  <div class="button-wrap">
                      <a class="zgd-btn zgd-btn-redsm close-pop" href="javascript:void(0);" style="margin: 0;">
                        <span class="zgd-icons ok-btn-icon"></span><span class="zgd-btn-txt">确定</span>
                      </a>
                  </div>
              </div>
            </div>
        </div>
    </div>

    <!-- popbox -->

    <script src="<%= Constants.get("GLOBAL_SERVER" ) %>/j/lib/jquery.js"></script>
    <script src="<%= Constants.get("GLOBAL_SERVER" ) %>/j/jend/jend.js"></script>
    <script src="<%= Constants.get("UHJ_CSS_SERVER" ) %>/c/my/loan/2016/0111/j/string.valid.js"></script>
    <script src="<%= Constants.get("UHJ_CSS_SERVER" ) %>/c/my/loan/2016/0111/j/zgd_bind_card.js"></script>
<%-- <script src="<%= Constants.get("UHJ_SERVER" ) %>/js/zgd_bind_card.js"></script> --%>
  </body>

</html>