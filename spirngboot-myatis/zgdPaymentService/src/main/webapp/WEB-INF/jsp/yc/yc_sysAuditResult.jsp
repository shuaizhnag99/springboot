<%@ page contentType="text/html;charset=utf-8" language="java"
	pageEncoding="UTF-8"%>
    <%@ page import="com.ule.uhj.util.Constants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>额度激活结果</title>
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/css/common.css">
    <style>
        #sysAuditResult{
            font-size:16px;
            color:#333;
        }
        #sysAuditResult header img{
            margin:20px 0 20px 300px;
        }
        #sysAuditResult .main{
            padding-top:40px;
            height:calc(100vh - 144px);
            border-top:1px solid #ccc;
            text-align: center;
        }
        .main img{
            margin:70px 0 20px 0;
        }
        .main .p1, .main .p2{
            font-size:20px;
            font-weight:bold;
        }
        .main .p3{
            margin:10px 0 40px 0;
        }
        .main a{
            width:140px;
            display:inline-block;
            color:#fff;
            background-color:#f94b4b;
            height:30px;
            line-height:30px;
        }
        footer{
            height:60px;
            background:#fff url(https://i2.ulecdn.com/yzg/app/zgd/i/bottom.png) no-repeat center;
        }
    </style>
</head>
<body>
<div id="sysAuditResult">
    <header><img src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/img/icon/logo.png" height="40" width="357"/></header>
    <div class="main">
        <p class="p1">您暂不符合掌柜贷建额要求</p>
        <p class="p2">额度建立失败</p>
        <p class="p3">如有疑问请联系客服：11185转4</p>
        <a href="<%=Constants.get("UHJ_SERVER")%>/pxZgd/toFisetPage.do">返回首页</a>
    </div>
    <footer></footer>
</div>
</body>
</html>