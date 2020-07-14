<%@ page contentType="text/html;charset=utf-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.ule.uhj.util.Constants"%>

<!DOCTYPE html>
<html>
<head>
<meta charset=utf-8 />
<title>中国邮政储蓄银行小额贷款额度借款支用单</title>
<link href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/c/css.css" rel="stylesheet">
<script src="<%=Constants.get("GLOBAL_SERVER") %>/j/lib/jquery-1.7.2.min.js"></script>
<script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/j/layer.js"></script>
</head>

<body></body>
<script>
	 // 支用单
	$.ajax({
	    url: '<%=Constants.get("UHJ_SERVER")%>/framework/pdf/queryLoanContact.do',
	    data: {
		    businessType: 'loan',
		    loanAmount: '${js.applyAmount}',
		    loan2Limit: '3',
		    loanRate: '${js.yearRate}'.replace(/%/g,''),
		    loanRepayType: '04'
	    },
	    dataType: 'jsonp',
	    jsonp: 'jsoncallback',
	    success: function(res) {
	        console.log('这是支用单返回', res);
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
