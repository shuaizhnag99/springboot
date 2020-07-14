<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page import="com.ule.uhj.util.Constants"%>

<!DOCTYPE html>
<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>掌柜贷</title>
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/css/common.css">
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/c/zgd_query.css">
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0401/c/zgd_popbox.css">
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/css/accountOverView.css">
  </head>
  
  <body>
	<input type="hidden" id="uhj_server" value="<%=Constants.get("UHJ_SERVER") %>" /> 
	<input type="hidden" id="provice" value="${provice}" />  
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
       <!--  <div class="festival_tips">
        	通知：尊敬的掌柜，春节期间掌柜贷已开通的掌柜可正常借款！同时也可正常提交开通申请，2月6日-14日暂停审核。恭祝您新春快乐！
		</div> -->
		<div class="zgd-con-det">
          <div class="zgd-con-header">
              <div class="zgd-header-lf">
                <div class="zgd-padding">
                    <div class="zgd-h-tit">
                      <div class="zgd-h-tit-txt">近期应还金额（元）<i class="isOverDue-i"></i></div>
                      <i class="zgd-icons icons-m2"></i>
                      <i class="zgd-iconarr"></i>
                    </div>
                    <div class="zgd-h-con">
                      <div class="zgd-h-con-lf">
                          <p class="zgd-h-t-red">
                            <span class="zgd-line-red"></span>
                            <span class="zgd-acc-num recentRepayAmt">0.00</span>
                            <!-- <a class="check-account" href="">查看账单</a> -->
                          </p>
                          <p class="zgd-h-total-date">还款日：<span>--</span></p>
                        </div>
                      
                    </div>
                </div>
            </div>
            <div class="zgd-header-rt">
                <div class="zgd-padding">
                    <div class="zgd-h-tit">
                      <div class="zgd-h-tit-txt">我的额度（元）
                      <!-- 提额攻下线<i id="availPro-i-id" class="availPro-i"></i> -->
                      </div>
                      <i class="zgd-icons icons-m"></i>
                      <i class="zgd-iconarr"></i>
                    </div>
                    <div class="zgd-h-con">
                        <div class="zgd-h-con-lf">
                          <p class="zgd-h-t-red">
                            <span class="zgd-line-red"></span>
                            <span class="zgd-acc-tg">可用额度</span>
                            <span class="zgd-acc-num availBalance">0.00</span>
                          </p>
                        </div>
                        <div class="zgd-h-con-rt">
                            <a class="zgd-btn zgd-btn-red lendAccOptBtn">
                              <span class="zgd-icons zgd-btn-icon"></span><span class="zgd-btn-txt">我要借款</span>
                            </a>
                            <!-- <a class="zgd-btn zgd-btn-blue" href="javascript:void(0);">
                              <span class="zgd-icons zgd-btn-icon2"></span><span class="zgd-btn-txt">我要还款</span>
                            </a> -->
                        </div>
                    </div>
                    <p class="zgd-h-total-lim">
                          	<span>总额度</span><span class="tot-sp">0.00</span>
                          	<span>冻结额度</span><span class="tot-sp freezeLimit">0.00</span>
                          </p>
                </div>
            </div>          
          </div>
          <div class="zgd-con-bd">
            <div class="zgd-btn-tab">
              <div class="tab-but red-but">借款记录<i class="zgd-iconarr"></i></div>
              <div class="tab-but grey-but">还款记录<i class="zgd-iconarr"></i></div>
            </div>
            <div class="zgd-con-tab">
            	<div class="zgd-tab-tbl">
	               <div class="borrowOrLend my_borrow_tbl" style="display:block;">
	                <table cellpadding="0" cellspacing="0">
	                   <thead>      
	                    <tr>
	                        <th>合同</th>
	                        <th>借款日期</th>
	                        <th>到期日</th>
	                        <th>借款金额</th>
	                        <th>剩余本金</th>
	                        <th>应还金额</th>
	                        <th>借款类型</th>
	                        <th>状态</th>
	                        <th>操作</th>
                     	</tr>
	                    </thead>
	                    <tbody></tbody>
	                </table>
	              </div>
 				  <div class="borrowOrLend my_repay_tbl">
	                  <table cellpadding="0" cellspacing="0">
	                     <thead>      
	                      <tr>
	                        <th>借据编号</th>
	                        <th>还款时间</th>
	                        <th>还款金额</th>
	                        <th>备注</th>
	                      </tr>
	                      </thead>
	                      <tbody>                                         
	                        
	                      </tbody>
	                  </table>
                  </div>
              </div>

              <div class="page">
                
              </div>

            </div>

          </div>

        </div>
      </div>
    </div>
    
     <!-- popbox -->

    <!-- popbox repayPlan -->
    <div class="popbox popbox-repayPlan">
      <div class="popbox-bg"></div>
      <div class="popbox-center" style="width: 600px;margin-left: -300px;">
          <div class="popbox-main">
            <div class="popbox-title">
                  <h4>还款查询</h4>
                  <span class="close close-pop" title="关闭浮层">关闭</span>
              </div>
              <div class="popbox-contentframe">
                
                <div  class="popbox-repayone repayOne">
                  <div class="repayOne-det">
                    <i class="zgd-icons lendrt-icon"></i>
                    <div class="repayone-tbl pp_repayPlan_tbl">
                      <table cellpadding="0" cellspacing="0">
                          <thead>
                            <tr>
                              <th style="text-align:center;">序号</th>
                              <th style="text-align:center;">日期</th>
                              <th style="text-align:center;">应还</th>
                              <th style="text-align:center;">=</th>
                              <th style="text-align:center;">本金</th>
                              <th style="text-align:center;">+</th>
                              <th style="text-align:center;">利息</th>
<!-- 							  <th style="text-align:center;">+</th> -->
<!--                               <th style="text-align:center;">罚息</th> -->
                              <th style="text-align:center;">状态</th>
                            </tr>
                          </thead>
                          <tbody>
                            
                          </tbody>
                      </table>
                    </div>
                  </div>
                  
                </div>

              </div>

            </div>
        </div>
    </div>

     <!-- popbox payall 提前还款-->
    <div class="popbox popbox-payAll tcboxmainNew">
      <div id="tcBox">
            <div class="tcMain">
                <p class="tcHeader clearfix">
                    <a href="javascript:;" class="fr" @click="isShowWindow = false">Ｘ</a>
                    <span class="sp1">提前还款</span>
                    <span>
                        （<span class="fc_red">*</span>提前还款时间为每天7:00--16:00）
                    </span>
                </p>
                <div class="autoAmount">
                    <p class="title">请选择或输入还款金额</p>
                    <ul class="showBox">
                        <li class="radio-group">
                            <div class="radio-warp">
                                <div class="check-box check-on" style="margin-right: 4px; width: 100%;">
                                    <span class="check-icon"><i></i></span>
                                    <span class="check-label">最低还款金额</span>
                                </div>
                            </div>
                            <input type="text" class="publicInput input1" id="needAmount" disabled="disabled"/>
                        </li>
                        <li class="radio-group">
                            <div class="radio-warp">
                                <div class="check-box check-off" style="margin-right: 4px; width: 100%;">
                                    <span class="check-icon"><i></i></span>
                                    <span class="check-label">全部还清</span>
                                </div>
                            </div>
                            <input type="text" class="publicInput input1" id="preRepayAmount" disabled="disabled" />
                        </li>
                        <li class="radio-group">
                            <div class="radio-warp">
                                <div class="check-box check-off">
                                    <span class="check-icon"><i></i></span>
                                    <span class="check-label">自定义金额</span>
                                </div>
                            </div>
                            <div class="li3div">
                                <input type="number" class="publicInput input1" maxlength="9" id="needAmount2" disabled="disabled"/>
                                <p class="writeErrTxt"><span class="fc_red errtxt">您输入的还款金额不符合最低还款要求</span></p>
                            </div>
                        </li>
                        <li><p class="des_hk">请确保您尾号为<span id="cardNo"></span>的邮储银行卡上有足够的钱用于还款</p></li>
                    </ul>
                </div>
                <div class="btn">
                    <button class="btncancel">取消</button>
                    <button class="zbtn btnsure">确定</button>
                </div>
            </div>
        </div>
    </div>
	
	<div class="popbox popbox-payAll tcboxmainOld">
      <div class="popbox-bg"></div>
      <div class="popbox-center" style="width: 600px; margin-left: -300px;">
          <div class="popbox-main">
            <div class="popbox-title">
                  <h4>提前还款</h4>
                  <span class="close close-pop" title="关闭浮层">关闭</span>
              </div>
              <div class="popbox-contentframe">
                <!-- <div class="popbox-repayall repayAll">
                  <table cellpadding="0" cellspacing="0">
                    <tbody> 
                     
                    </tbody>
                  </table>
                </div> -->
                
                <div class="popbox-repayone repayOne">
                  <div class="repayOne-det">
                    <i class="zgd-icons lendrt-icon"></i>
                    <div class="repayone-tbl pp_repayAll_tbl">
                      <table cellpadding="0" cellspacing="0">
                          <thead>
                            <tr>
<!--                               <th style="text-align: center;">提前归还本金</th> -->
<!--                               <th style="text-align: center;">当前期本金</th> -->
<!--                               <th style="text-align: center;">拖欠本金</th> -->
<!--                               <th style="text-align: center;">正常利息</th> -->
<!--                               <th style="text-align: center;">拖欠利息</th> -->
<!--                               <th style="text-align: center;">罚息</th> -->
<!--                               <th style="text-align: center;">总的还款金额</th> -->
                            </tr>
                          </thead>
                          <tbody>
                            
                          </tbody>
                      </table>
                    </div>
                  </div>
                  <div class="repayOne-sum payAllDet">
                    <ul> 
                      
                    </ul>
                  </div>
                </div>

              </div>
    
              <div class="popbox-btn-con">
                  <div class="button-wrap">
                      <a class="zgd-btn zgd-btn-graysm close-pop">
                        <span class="zgd-icons cancel-btn-icon"></span><span class="zgd-btn-txt">取消</span>
                      </a>
                      <a class="zgd-btn zgd-btn-redsm confirmPayAllBtn">
                        <span class="zgd-icons ok-btn-icon"></span><span class="zgd-btn-txt">确认</span>
                      </a>
                  </div>
              </div>
            </div>
        </div>
    </div>

    <!-- popbox payone -->
    <div class="popbox popbox-payOne">
      <div class="popbox-bg"></div>
      <div class="popbox-center">
          <div class="popbox-main">
            <div class="popbox-title">
                  <h4>今日应还</h4>
                  <span class="close close-pop" title="关闭浮层">关闭</span>
              </div>
              <div class="popbox-contentframe">
                
                <div  class="popbox-repayone repayOne">
                  <div class="repayOne-det">
                    <i class="zgd-icons lendrt-icon"></i>
                    <div class="repayone-tbl pp_repayOne_tbl">
                      <table cellpadding="0" cellspacing="0">
                          <thead>
                            <tr>
                              <th>应还日期</th>
                              <th>利息</th>
                              <th>本金</th>
                              <th>状态</th>
                            </tr>
                          </thead>
                          <tbody>
                            
                          </tbody>
                      </table>
                    </div>
                  </div>
                  <div class="repayOne-sum repayOneDet">
                    <ul>
                      
                    </ul>
                  </div>
                </div>

              </div>

              <div class="popbox-btn-con">
                  <div class="button-wrap">
                      <a class="zgd-btn zgd-btn-graysm close-pop">
                        <span class="zgd-icons cancel-btn-icon"></span><span class="zgd-btn-txt">取消</span>
                      </a>
                      <a class="zgd-btn zgd-btn-redsm confirmPayOneBtn">
                        <span class="zgd-icons ok-btn-icon"></span><span class="zgd-btn-txt">确认</span>
                      </a>
                  </div>
              </div>
            </div>
        </div>
    </div>

  <!-- popbox tobindCard page -->
	<div class="popbox popbox-bindCard">
		<div class="popbox-bg"></div>
		<div class="popbox-center">
			<div class="popbox-main">
				<div class="popbox-title">
					<h4>今日应还</h4>
					<span class="close close-pop" title="关闭浮层">关闭</span>
				</div>
				<div class="popbox-contentframe">

					<div class="popbox-bindcard-txt">
						尊敬的掌柜，感谢您使用掌柜贷服务，由于您未在邮乐绑定邮储银行借记卡信息，为了方便您的借款，清根据提示进行银行卡绑定操作。</div>

				</div>

				<div class="popbox-btn-con">
					<div class="button-wrap">
						<a class="zgd-btn zgd-btn-graysm close-pop"> <span
							class="zgd-icons cancel-btn-icon"></span><span
							class="zgd-btn-txt">再看看</span>
						</a> <a class="zgd-btn zgd-btn-redsm" href=""> <span
							class="zgd-icons ok-btn-icon"></span><span class="zgd-btn-txt">去绑卡</span>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
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
						<a class="zgd-btn zgd-btn-redsm close-pop"
							href="javascript:void(0);" style="margin: 0;"> <span
							class="zgd-icons ok-btn-icon"></span><span class="zgd-btn-txt">确定</span>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!--额度不够引导弹窗 -->
    <div class="popbox popbox-inc">
      <div class="popbox-bg"></div>
      <div class="popbox-center">
          <div class="popinc-main">
            <span class="close-popbox-incX close-inc-btn">关闭</span>
            <span class="close-popbox-incB close-inc-btn">知道了</span>
          </div>
      </div>
    </div>
    <!--额度不够引导弹窗 -->
	
	<!-- popbox -->

    <script src="<%=Constants.get("GLOBAL_SERVER")%>/j/lib/jquery-1.7.2.min.js"></script>
    <script src="<%=Constants.get("GLOBAL_SERVER")%>/j/jend/jend.js"></script>
<%--     <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/yzg/2016/0323/j/dialog.js"></script> --%>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/j/jquery.spice.js"></script>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/j/jquery.kvScroll.js"></script>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/j/zgd_query_v3.js"></script>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/j/layer.js"></script>
<%-- <script src="<%=Constants.get("UHJ_SERVER") %>/js/zgd_query.js"></script> --%>
  </body>

</html>