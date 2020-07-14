<%@ page contentType="text/html;charset=utf-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.ule.uhj.util.Constants"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我要借款</title>
<link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2016/0111/c/zgd_lend.css">
<link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2016/0111/c/zgd_popbox.css">
</head>

  <body>
<input type="hidden" id="uhj_server" value="<%=Constants.get("UHJ_SERVER") %>" />   
    <div id="wrapper" class="clear">
      <ul class="zgd-breadcrumb">
        <li>
          <a href="<%=Constants.get("UHJ_SERVER")%>/uhj/zgd_toZgdPage">掌柜贷</a>
          <i class="icon-bread"></i>
        </li>
        <li>我要借款</li>
      </ul>
      <div class="main zgd-lend-container">
        <div class="zgd-lend-lf">
          <div class="zgd-lend-wrap">
            <form class="lend-form">
              <div class="zgd-lend-header">
                <span class="zgd-icons zgd-lend-iconh"></span><span class="zgd-btn-txt">我要借款</span>
              </div>
              <div class="zgd-lend-mid">
                <div class="lend-mid-hd">
                  <label class="lend-forminput inline">
                    <span class="formtxt">掌柜贷可用额度:</span>
					<input name="balance" class="balance-input" type="text" value=""/>
                  </label>
                  <label class="lend-forminput inline">
                    <span class="formtxt">活动可支用金额:</span>
					<input name="balance" class="balance-input" type="text" value=""/>
                  </label>
                </div>
                <div class="lend-mid-con">
                  <table>
                  	<tbody>
                  	<tr>
                      <td>借款金额：</td>
                      <td>
                        <div class="balance">
                        </div>
                      </td>
                      <td>国定还款日：</td>
                      <td>
                        <span class="formtxt">每月固定还款日</span>
	                    <div class="fixedRepayDateOuter">
		                    <select name="fixedRepayDate" class="fixedRepayDate select-lend">
		                      <option value="1">1</option>
		                      <option value="2">2</option>
		                      <option value="3">3</option>
		                      <option value="4">4</option>
		                      <option value="5">5</option>
		                      <option value="6">6</option>
		                      <option value="7">7</option>
		                      <option value="8">8</option>
		                      <option value="9">9</option>
		                      <option value="10">10</option>
		                      <option value="11">11</option>
		                      <option value="12">12</option>
		                      <option value="13">13</option>
		                      <option value="14">14</option>
		                      <option value="15">15</option>
		                      <option value="16">16</option>
		                      <option value="17">17</option>
		                      <option value="18">18</option>
		                      <option value="19">19</option>
		                      <option value="20">20</option>
		                      <option value="21">21</option>
		                      <option value="22">22</option>
		                      <option value="23">23</option>
		                      <option value="24">24</option>
		                      <option value="25">25</option>
		                      <option value="26">26</option>
		                      <option value="27">27</option>
		                      <option value="28">28</option>
		                    </select>
	                    </div>
                      </td>
                    </tr>
                    <tr>
                      <td>收款账户：</td>
                      <td>
                      	<i class="bankLogo"></i>
                        <div class="holdingBank">
                          <input name="holdingBank" class="balance-input" type="text" value=""/>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>贷款利率：</td>
                      <td>年利率4.5%（中秋活动利率五折特惠）</td>
                    </tr>
                   </tbody>
                  </table>
              </div>
             </div>
            </form>
          </div>
        </div>
        <div class="zgd-lend-rt">
          <div class="zgd-lend-wrap">
            <div class="zgd-lend-header">
              <span class="zgd-icons zgd-back-iconh"></span><span class="zgd-btn-txt">还款计划</span>
            </div>
            <div class="lend-mid-hd zgd-lend-bd">
              <i class="zgd-icons lendrt-icon"></i>
              <div class="zgd-lendbd-tbl">
                <table>
                    <thead>
                      <tr>
                        <th>还款日期</th>
                        <th>还款本金</th>
                        <th>还款利息</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td colspan="3">暂无内容</td>
                      </tr>
                    </tbody>
                </table>
              </div>
            </div>
            <div class="zgd-lend-bot">
	            <label class="zgd-lend-check y-check-con ">
	                <span class="zgd-check checked"></span>
	                <span class="zgd-check-txt">我已阅读并同意以下协议
	                </span>
	            </label>
	            <a href="javascript:void(0);" class="zgd-btn zgd-btn-red lendbtn-conf-gray">
	                <span class="zgd-icons zgd-btn-icon"></span><span class="zgd-btn-txt">确认借钱</span>
	            </a>
	          </div>
	          <a class="amount-contract lend-contract1" href="" target="_blank">《掌柜贷咨询服务协议》</a>
	          <a class="amount-contract lend-contract2" href="" target="_blank">《小额贷款额度借款合同》</a>
	          <a class="amount-contract lend-contract3" href="" target="_blank">《中国邮政储蓄银行小额贷款额度借款支用单》</a>
          </div>
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
                
                <div class="popbox-bindcard-txt" style="text-align: center;">
                
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
	 <div class="popbox popbox-confirmsg">
	      <div class="popbox-bg"></div>
	      <div class="popbox-center" style="width: 400px; margin-left: -200px">
	          <div class="popbox-main">
	            <div class="popbox-title">
	                  <h4>借款确认</h4>
	                  <span class="close close-pop" title="关闭浮层">关闭</span>
	              </div>
	              <div class="popbox-contentframe">
	                
	                <div  class="popbox-confirmsg-txt" style="text-align: center;">
	                 
	                </div>
	
	              </div>
	
	              <div class="popbox-btn-con">
	                  <div class="button-wrap">
	                      <a class="zgd-btn zgd-btn-graysm close-pop">
	                        <span class="zgd-icons cancel-btn-icon"></span><span class="zgd-btn-txt">取消</span>
	                      </a>
	                      <a class="zgd-btn zgd-btn-redsm confirmBorrowBtn" style="margin-left: 10px;">
	                        <span class="zgd-icons ok-btn-icon"></span><span class="zgd-btn-txt">确认</span>
	                      </a>
	                  </div>
	              </div>
	            </div>
	        </div>
	    </div>
<!-- 短信验证 -->
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

    <!-- popbox -->
    
	<script src="<%=Constants.get("GLOBAL_SERVER") %>/j/lib/jquery-1.7.2.min.js"></script>
    <script src="<%=Constants.get("GLOBAL_SERVER") %>/j/jend/jend.js"></script>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/j/string.valid.js"></script>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0401/j/zgd_lend_v3.js"></script>
<%-- 	<script src="<%=Constants.get("UHJ_SERVER") %>/js/zgd_lend.js"></script> --%>
</body>
</html>