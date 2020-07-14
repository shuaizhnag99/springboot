<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ule.uhj.util.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <script src="<%=Constants.get("GLOBAL_SERVER") %>/j/lib/jquery-1.7.2.min.js"></script>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/j/layer.js"></script>
</head>
<body></body>
<script>
	 // 征信查询协议
	$.ajax({
	    url: '<%=Constants.get("UHJ_SERVER")%>/framework/pdf/queryLoanContact.do',
	    data: {businessType: 'limit'},
	    dataType: 'jsonp',
	    jsonp: 'jsoncallback',
	    success: function(res) {
	        console.log('这是征信查询协议返回', res);
	        if (res.code === '0000') {
	        	var BODY_REG = /<body[^>]*>([\s\S]+?)<\/body>/;
                var _dom = BODY_REG.exec(res.htmlStr)[0].replace('<body>','').replace('</body>','');
	            document.getElementsByTagName('body')[0].innerHTML = _dom;
	        } else {
	            layer.msg(res.msg);
	        }
	    },
	    error: function(err) {
	        layer.msg(err);
	    }
	});
</script>
</html>
