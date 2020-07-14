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
	<div style="display: none" id="pxResult">${result}</div>   
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
                    <span class="formtxt">借款金额</span>
<!--                     <input name="balance" class="balance-input" type="text"  readonly /> -->
					<input name="balance" class="balance-input" type="text" value="${result.applyAmount}" readonly/>
                  </label>
                  <label class="lend-forminput inline backdate">
                    <c:if test="${result.paidAll eq 1 }">
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
                    </c:if>
                    <c:if test="${result.paidAll eq 0 }">
                    	 <span class="formtxt">固定还款日：每月${result.fixedRepayDate}日</span>
                    </c:if>
                  </label>
                </div>
                <div class="lend-mid-con">
                  <table>
                  	<tbody>
                  	<tr>
                      <td>收款账户：</td>
                      <td  class="holdingBank">
                       <i class="bankLogo"></i> ${result.cardNo}
                      </td>
                    </tr>
                    <tr>
                      <td>借款期限：</td>
                      <td  class="periods">
                        3个月
                      </td>
                    </tr>
                    <tr>
                      <td>贷款利率：</td>
                      <td class="loanRate">年利率9.00%（按月支付）</td>
                    </tr>
                   <tr>
                       <td>还款方式：</td>
                       <td class="repayType">
                       <input type="radio" name="interest" value="equal" checked="checked">等额本息<input type="radio" name="interest" value="early" style="margin-left: 100px">先息后本
                       </td>
                   </tr>
                   </tbody>
                  </table>
                  <div class="equal" style="position: relative;left: 99px;top:6px;font-size: 14px;">每期还部分本金和利息<br>利息总额少</div>
                  <div class="early" style="position: relative;left: 269px;top:-26px;font-size: 14px;">每期只还利息，本金最后一次还清<br>资金利用率高 </div>
                  <div class="known" style="padding:15px 12px;border:1px solid #666;font-size: 14px;line-height: 42px;text-align: center;position:relative;top:0px;left:8px;display: none">
                      <p>第一次借钱只能使用等额本息方式还钱，请重新选择还款方式！</p>
                      <p>如果您正常还款，下次借钱就可以使用先息后本的方式还钱了！</p>
                      <input  type="button" value="我知道了" class="kbn" style="padding:5px 25px;border:1px solid #666666;border-radius: 3px;background-color:#F3F3F3;outline: none;">
                  </div>
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

<%--               <a class="amount-contract" href="<%=Constants.get("UHJ_SERVER")%>/jsp/youChuXiaoE.pdf" target="_blank">《中国邮政储蓄银行小额贷款额度借款合同》</a> --%>
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
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/1122/jiekuan.js"></script>
</body>
</html>