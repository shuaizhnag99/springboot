<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ule.uhj.util.Constants" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
    <meta name="mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="format-detection" content="telephone=no" />
    <title>巡店任务异常</title>
     <style>
        body,html,div,p{margin: 0;padding: 0;font-family: 'Microsoft YaHei'}
        .errBox{width: 80%;padding-top: 40px ;margin: 0 auto;font-size: 24px;color: #333333; line-height: 60px;}
        .back-btn{float: right;width: 20%;min-width: 100px;height: 40px;line-height: 40px;background: #428bca;
            border-radius: 6px;text-align: center;color:#ffffff;margin-right: 10%;}

    </style>
	<script src="<%=Constants.get("UHJ_CSS_SERVER") %>/j/lib/jquery.js"></script>
	<script type="text/javascript">
		$(function(){
			$('#back_but').click(function(){
				window.location.href='<%=Constants.get("CP_HELPER_TASK")%>';
			});
		});
	</script>
</head>
<body>
	<div class="errBox">
	${msg}
	</div>
	<div class="back-btn" id="back_but">返回</div>
</body>
</html>