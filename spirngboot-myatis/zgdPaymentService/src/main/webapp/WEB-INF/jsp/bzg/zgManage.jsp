<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.ule.uhj.util.Constants" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/j/lib/jquery.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=Constants.get("UHJ_CSS_SERVER_HTTP") %>/sld/2016/0902/dy10.css" />
    <title>管理掌柜</title>
</head>
<body>
<header>
    <p><span onclick="sortInfo('1')" class="fl">未开通掌柜贷</span><span class="fr">${result.noOpenZgCount}</span></p>
    <p><span onclick="sortInfo('2')" class="fl">已开通掌柜贷</span><span class="fr">${result.openZgCount}</span></p>
    <p><span class="fl">待结算收益</span><span class="fr">${result.notCheckedIncome}</span></p>
    <p><span class="fl">已结算收益</span><span class="fr">${result.checkedIncome}</span></p>
    <p><span class="fl">贷款余额</span><span class="fr">${result.loanTotalBalance}</span></p>
    <p class="tips" style="width:80%;">
    <c:if test="${result.yuqiZgCount ne 0}">
    	<span class="fl"><a href="javascript:;">目前已有${result.yuqiZgCount}个掌柜已经产生逾期</a></span>
    </c:if>	
    <span class="fr"><a style="color:#0044BB;text-decoration:underline;" href="<%=Constants.get("UZS_STATIC_RESOURCES_HTTP") %>/yzs/faq.html">常见问题</a></span>
    </p>
</header>
<input type="hidden" id="sortId" name="sortId" value="0"/>
<input type="hidden" id="server" name="server" value="<%=Constants.get("UHJ_SERVER")%>"/>
<c:forEach items="${zgInfo}" var="list">
	<c:choose>
	<c:when test="${list.userState eq '1'}">
		<section class="clerk-detail">
   		<div class="dy-warp clearfix">
			<p class="user kait clearfix">
				<span class="fl font-bold">${list.userName}的小店</span>
				<!-- <a class="fr" href="javascript:;">详情&gt;</a> -->
			</p>
			<p class="data kait clearfix">
				<c:if test="${list.stateDec eq '已逾期'}">
					<span class="fl font-bold-right" style="color:red">${list.stateDec}</span>
				</c:if>
				<c:if test="${list.stateDec ne '已逾期'}" >
           		<span class="fl font-bold-right" >${list.stateDec}</span>
           		</c:if>
            	<!-- <time class="fr">${list.timeStamp}</time> -->
        	</p>
        	<ul class="list">
            	<li>
              	<div>
                   <span>${list.avaibleAmount}</span>
                   <span>可用额度</span>
                 </div>
                 <div>
                    <span>${list.loanBalance}</span>
                    <span>贷款余额</span>
                 </div>
                 <div>
                    <span>${list.accumulatedIncome}</span>
                    <span>累计收益</span>
                 </div>
           		 </li>
        	</ul>
        </div>
 		</section>
	</c:when>
	<c:when test="${list.userState eq '0'}">
		<section class="clerk-detail">
    	<div class="dy-warp">
			<p class="user kait clearfix ulehelper">
				<span class="fl font-bold">${list.userName}的小店</span>
				<a href="#" useronlyid="${list.userOnlyId}" style="margin-left:80%;color:#0044BB;">协助申请</a>
			</p>
			<p class="data kait clearfix">
				<a class="fr gjdu" style="font-size: 18px;float: right;color: #818287;" href="javascript:;">
            	<span >${list.estimatedAmount}</span><br>
                <span >预估额度</span>
           		 </a>
			</p>
            <p class="data kait clearfix">
	            <span class="fl font-bold">${list.stateDec}</span>
	        </p>
        	<div class="line"></div>
        	<p class="remind-txt clearfix">
        		<c:if test="${list.stateDec eq '审批拒绝'}">
					${list.pushBackReason}
				</c:if>
			</p>
        </div>
		</section>
	</c:when>				
	<c:otherwise><td></td></c:otherwise> 	
	</c:choose>		
</c:forEach>   
<script>
	$(function(){
		$(".ulehelper").each(function(index,element){
			var paramer = new Object();
			var trigger = $(element).children("a");
			trigger.hide();
			paramer.userOnlyId = trigger.attr("useronlyid");
			$.ajax({
				  url:"/lendvps/financerhelper/accountRealNameCheckQuery.do",
				  async:true,
				  dataType:'jsonp',
				  data:paramer,
				  jsonp:'jsoncallback',
				  success:function(result) {
				    if(result.code=='000000'){
				         var status = result.dataMap.status;
				         var url = result.dataMap.url;
				        trigger.attr("href",url+"?userOnlyId="+trigger.attr("useronlyid"));
				        trigger.show();
				  	}
				  },
			});
		});
	});
	
	function sortInfo(sortRule){
		if(sortRule!=$("#sortId").val()){
			$("#sortId").val(sortRule);
		}
		window.location.href="bzg_queryZGManageInfo.do?sortRule="+$("#sortId").val(); 
	}
</script>
</body>
</html>