<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page import="com.ule.uhj.util.Constants"%>
<!DOCTYPE html>
<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>邮乐</title>
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/c/zgd_query.css">
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/c/zgd_query2.css">
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/c/zgd_popbox.css">
  	<script src="<%=Constants.get("GLOBAL_SERVER")%>/j/lib/jquery-1.7.2.min.js"></script>
    <script src="<%=Constants.get("GLOBAL_SERVER")%>/j/jend/jend.js"></script>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/j/string.valid.js"></script>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/j/jquery.spice.js"></script>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/j/jquery.kvScroll.js"></script>
  </head>

  <body>
<input type="hidden" id="uhj_server" value="<%=Constants.get("UHJ_SERVER") %>" /> 
<%-- <input type="hidden" id="uhj_server" value="<%=Constants.get("UHJ_SERVER") %>" />  --%>
    <div id="wrapper" class="clear">
      <div class="main zgd-query-con">
        <div class="banner-detail scroll" id="expo-scroll">
          <div>
            <ul class="slide-items" >
           <!--  <c:if test="${provice  !='NONE'}">
           	  <li>
                <a href="http://www.ule.com/event/2016/1110/marketing.html">
                  <img src="https://i1.ule.com/zgd/i/2016/1110/banner.jpg" width="974" height="140" alt="">
                </a>
              </li>
              </c:if> -->
              <li>
                <a href="#">
                  <img src="https://i1.ule.com/zgd/i/2016/1110/huandai.jpg" width="974" height="140" alt="">
                </a>
              </li>
              <li>
                <a href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_prdIntroduce.action?banner=1">
                  <img src="https://i1.ule.com/zgd/i/2016/0825/banner01.jpg" width="974" height="140" alt="">
                </a>
              </li>
              <li>
                <a href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_prdIntroduce.action?banner=2">
                  <img src="https://i1.ule.com/zgd/i/2016/0825/banner02.jpg" width="974" height="140" alt="">
                </a>
              </li>
              <li>
                <a href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_prdIntroduce.action?banner=3">
                  <img src="https://i1.ule.com/yzg/2016/0517/huanji/i/banner.jpg" width="974" height="140" alt="">
                </a>
              </li>
            </ul>
          </div>
        </div>
		<!-- <div class="festival_tips">
        	通知：尊敬的掌柜，春节期间掌柜贷已开通的掌柜可正常借款！同时也可正常提交开通申请，2月6日-14日暂停审核。恭祝您新春快乐！
		</div> -->
        <div class="zgd-con-det">
          <div class="zgd-con-header zgd-unopen clearFix">
              <div class="zgd-header-lf">
                <div class="zgd-unopen-inner">
                  <p class="zgd-h-t-red">
                    <span class="zgd-line-red"></span>
                    <span class="zgd-h-total-lim-un">
                      <strong class="un-lim-tit">预估${desc }额度（元）</strong>
                      <i class="unopen-icon-wh" title="根据您邮掌柜的业绩来预估额度"></i>
                    </span>
                  </p>
                  <p class="zgd-h-t-red unopen-martop10">
                    <span class="zgd-acc-num availBalance">￥${creditLimit}</span>
                  </p>
                </div>
              </div>
              <div class="zgd-header-rt">
               		<a class="zgd-btn-blue " href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_upLimit.action">
                        <span class="zgd-btn-txt">额度太少，我要提额</span><span class="zgd-icons-jtw"></span>
                    </a>
<%--               <c:if test="${applyStatus eq '0'}"> --%>
<!-- 	                <a class="zgd-btn zgd-btn-red unopen-openBtn" href="javascript:void(0);"> -->
<%-- 	                  <span class="zgd-btn-txt">立即申请</span><span class="zgd-icons-jtw"></span> --%>
<!-- 	                </a> -->
<%--                 </c:if> --%>
               <c:if test="${applyStatus eq '01'}">
	                <a class="zgd-btn zgd-btn-red unopen-openBtn" href="javascript:void(0);">
	                  <span class="zgd-btn-txt">申请开通</span><span class="zgd-icons-jtw"></span>
	                </a>
                </c:if>
                <c:if test="${applyStatus eq '011'}">
	                <a class="zgd-btn zgd-btn-red" href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_prePersonInfo.action">
	                  <span class="zgd-btn-txt">申请开通</span><span class="zgd-icons-jtw"></span>
	                </a>
                </c:if>
                <c:if test="${applyStatus eq '04'}">
	                <a class="zgd-btn zgd-btn-red" href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_prePersonInfo.action">
	                  <span class="zgd-btn-txt">重新申请</span><span class="zgd-icons-jtw"></span>
	                </a>
                </c:if>
               <%--  <c:if test="${applyStatus eq '05'}">
	                <a class="zgd-btn zgd-btn-red" href="#">
	                  <span class="zgd-btn-txt">申请被拒绝</span>
	                </a>
                </c:if> --%>
                <div class="open-phone clearFix">
                </div>
              </div>
          </div>

          <div class="zgd-con-bd unopen-bd">
            <div class="unopen-bd-con">
              <div class="unopen-line">
                <div class="unopen-img unopen-i1"></div>
                <div class="unopen-txt">还款日自由选择</div>
              </div>
              <div class="unopen-line">
                <div class="unopen-img unopen-i2"></div>
                <div class="unopen-txt">费率优惠</div>
              </div>
              <div class="unopen-line">
                <div class="unopen-img unopen-i3"></div>
                <div class="unopen-txt">提前还款无费用</div>
              </div>
              <div class="unopen-line">
                <div class="unopen-img unopen-i4"></div>
                <div class="unopen-txt">手机开通额度更高</div>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>

    <!-- popbox confirm --> 
    <div class="popbox popbox-openPop">
      <div class="popbox-bg"></div>
      <div class="popbox-center">
          <div class="popbox-main">
            <div class="popbox-title">
                  <h4>请确认以下信息是否与营业执照上的信息一致</h4>
                  <span class="close close-pop" title="关闭浮层">关闭</span>
              </div>
              <div class="popbox-contentframe">
                <div class="popbox-repayall confirm-openPop">
                  <form class="form-bindcard">
                    <table cellpadding="0" cellspacing="0">
                      <tbody> 
                        
                      </tbody>
                    </table>
                  </form>
                </div>
              </div>
    
              <div class="popbox-btn-con">
                  <div class="button-wrap">
                      <a class="zgd-btn zgd-btn-redsm openbtn-msgcorrect" style="margin-left: 0">
                        <span class="zgd-icons ok-btn-icon"></span><span class="zgd-btn-txt">一致</span>
                      </a>
                      <a class="zgd-btn zgd-btn-graysm close-pop" style="margin-left: 20px; background: #e64545; width: 200px;">
                        <span class="zgd-btn-txt">不一致，请联系您的联络员</span>
                      </a>
                  </div>
              </div>
            </div>
        </div>
    </div>

    <div class="popbox popbox-enterPsd">
      <div class="popbox-bg"></div>
      <div class="popbox-center">
          <div class="popbox-main">
            <div class="popbox-title">
                  <h4>短信验证</h4>
                  <span class="close close-pop" title="关闭浮层">关闭</span>
              </div>
              <div class="popbox-contentframe">
                <div class="popbox-repayall ">
                  <div class="enterPsd-con">
                    <form class="form-bindcard">
                      <div class="enterPsd-tel">手机号码：<span class="open-phoneNum"></span></div>
                      <div class="formRow">
                        <div class="inputboxWrapperStd">
                          <input type="text" id="randomCode" name="randomCode" class="inputbox" placeholder="请输入验证码" warn-msg="请输入验证码" error-msg="请输入验证码" maxlength="6">
                        </div>
                        <div class="msgValidateCode">
                          <div class="getcode" id="getValidateCode">获取验证码</div>
                        </div>
                        <div class="msgHolder">
                          <span class=""><i class="zgd-icons icons-in"></i><small></small></span>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
    
              <div class="popbox-btn-con">
                  <div class="button-wrap">
                      <a class="zgd-btn zgd-btn-graysm close-pop">
                        <span class="zgd-icons cancel-btn-icon"></span><span class="zgd-btn-txt">取消</span>
                      </a>
                      <a class="zgd-btn zgd-btn-redsm zgd-btn-graysm validBtn-popConfirm">
                        <span class="zgd-icons ok-btn-icon"></span><span class="zgd-btn-txt">确认</span>
                      </a>
                  </div>
              </div>

            </div>
        </div>
    </div>

    <div class="popbox popbox-selCardNo">
      <div class="popbox-bg"></div>
      <div class="popbox-center">
          <div class="popbox-main">
            <div class="popbox-title">
                  <h4>请选择贷款卡号</h4>
                  <span class="close close-pop" title="关闭浮层">关闭</span>
              </div>
              <div class="popbox-contentframe">
                <div class="popbox-repayall ">
                  <div class="selCardNo-con">
                      <span class="CardNoSel_span">贷款账户：</span>
                      <i class="bankLogo"></i>
                      <select name="cardNos" class="cardNos selCardNo-lend">
                      </select>
                  </div>
                </div>
              </div>
    
              <div class="popbox-btn-con">
                  <div class="button-wrap">
                      <a class="zgd-btn zgd-btn-graysm close-pop">
                        <span class="zgd-icons cancel-btn-icon"></span><span class="zgd-btn-txt">取消</span>
                      </a>
                      <a class="zgd-btn zgd-btn-redsm selCardNo-popConfirm">
                        <span class="zgd-icons ok-btn-icon"></span><span class="zgd-btn-txt">确认</span>
                      </a>
                  </div>
              </div>

            </div>
        </div>
    </div>


    <!-- popbox getvalidcode -->
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

    <div class="popbox popbox-forOpen">
      <div class="popbox-bg"></div>
      <div class="popbox-center">
          <div class="popbox-main">
            <div class="popbox-title">
                  <h4>提示信息</h4>
                  <span class="close forOpen-closePop" title="关闭浮层">关闭</span>
              </div>
              <div class="popbox-contentframe">
                
                <div  class="popbox-forOpen-txt" style="text-align: center;">
                
                </div>

              </div>

              <div class="popbox-btn-con">
                  <div class="button-wrap">
                      <a class="zgd-btn zgd-btn-redsm forOpen-closePop" href="javascript:void(0);" style="margin: 0;">
                        <span class="zgd-icons ok-btn-icon"></span><span class="zgd-btn-txt">确定</span>
                      </a>
                  </div>
              </div>
            </div>
        </div>
    </div>
    <!--如何提额-->
	<div id="tie">
	   	 <img src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/i/pop.jpg" alt="">
	   	 <a class="tie"href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_upLimit.action"></a>
<%-- 	   	 <span class="pop_num">￥${creditLimit}</span> --%>
	</div>
	<div style="display:none"><script type="text/javascript">var SITE_ID = 16; JEND.track.init();</script></div>
   
  </body>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/j/zgd_query_unopen2.js"></script>
<%-- <script src="<%=Constants.get("UHJ_SERVER") %>/js/zgd_query_unopen.js"></script> --%>
</html>