<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page import="com.ule.uhj.util.Constants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/c/zgd_query3.css">
    <style type="text/css">
    </style>
</head>
<body>
<div class="banner">
    <img src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/i/tie_01.jpg" alt="">
</div>
<div class="content">
    <div class="inner">
         <input type="hidden" id="huiYuan" value="${huiYuan}" /> 
	    <input type="hidden" id="wenDingXing" value="${wenDingXing}" /> 
	    <input type="hidden" id="other" value="${other}" /> 
	    <input type="hidden" id="maxjxc" class="c1" value="${maxjxc}" /> 
	    <input type="hidden" id="maxczjf" class="c2" value="${maxczjf}" /> 
	    <input type="hidden" id="maxdg" class="c3" value="${maxdg}" /> 
	    <input type="hidden" id="maxpf" class="c4" value="${maxpf}" /> 
        <ul id="listUl">
            <li>
                <div class="detail">
                    <span>月均进销存:￥</span><i class="i1">${jxc}</i>
                </div>
                <div class="process">
                    <div class="inProcess"></div>
                    <div class="drag"></div>
                    <div class="drag-sub"></div>
                </div>


            </li>
            <li>
                <div class="detail">
                    <span>月均充值缴费:￥</span><i class="i2">${czjf}</i>
                </div>
                <div class="process">
                    <div class="inProcess"></div>
                    <div class="drag"></div>
                    <div class="drag-sub"></div>
                </div>
            </li>
            <li>
                <div class="detail">
                    <span>月均代购:￥</span><i class="i3">${dg}</i>
                </div>
                <div class="process">
                    <div class="inProcess"></div>
                    <div class="drag"></div>
                    <div class="drag-sub"></div>
                </div>
            </li>
            <li>
                <div class="detail">
                    <span>月均批发:￥</span><i class="i4">${pf}</i>
                </div>
                <div class="process">
                    <div class="inProcess"></div>
                    <div class="drag"></div>
                    <div class="drag-sub"></div>
                </div>
            </li>
        </ul>
         <div class="footer">*近三个月每月完成以上业绩，就可能获得您的理想额度，快去提升业绩吧！</div>
    </div>
    <div class="num">￥<b id="totalMoney">${creditLimit}</b></div>
    <div class="floor">
    	<img src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/i/floor1.png" alt="">
	</div>
</div>


<script src="<%=Constants.get("GLOBAL_SERVER")%>/j/lib/jquery-1.7.2.min.js"></script>
<%-- <script src="<%=Constants.get("GLOBAL_SERVER")%>/j/jend/jend.js"></script> --%>
<script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/j/zgd_tie.js"></script>
</body>
</html>