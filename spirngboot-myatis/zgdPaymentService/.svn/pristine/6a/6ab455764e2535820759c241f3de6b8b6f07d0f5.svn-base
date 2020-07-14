<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page import="com.ule.uhj.util.Constants"%>

<!DOCTYPE html>
<html>

 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>产品介绍</title>
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/c/zgd_apply.css">
  </head>

  <body>
    <div class="zgd-apply-content">
      <div class="zgd-apply-con-fir"></div>
      <div class="zgd-apply-con-sec"></div>
      <div class="zgd-apply-con-thir">
        <a href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_toLendPage.action" class="zgd-apply-btn"></a>
      </div>
    </div>
    <script src="<%=Constants.get("GLOBAL_SERVER")%>/j/lib/jquery-1.7.2.min.js"></script>
    <script src="<%=Constants.get("GLOBAL_SERVER")%>/j/jend/jend.js"></script>
	<div style="display:none"><script>var SITE_ID = 16; JEND.track.init();</script></div>
	<script type="text/javascript">
		$(function(){
			var track = function(action, actionNote, actionValue) {
				var date = ['_trackEvent', "yzg-zgd", action || '', actionNote || '', actionValue || ''];
				window['_hmt'] && window['_hmt'].push(date);//百度
				window['_utrack'] && window['_utrack'].push(date);//ule
			}
			//去申请按钮
			$('.zgd-apply-btn').live('click',function(){
				track("click", "apply-btn", "");
			})
		});
	</script>
  </body>

</html>