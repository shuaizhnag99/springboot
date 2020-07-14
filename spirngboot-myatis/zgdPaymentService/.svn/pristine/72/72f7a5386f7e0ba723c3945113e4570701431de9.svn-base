<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ule.uhj.util.Constants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>掌柜贷</title>
    <style>
        html,body,div,a,img{margin: 0;  padding: 0;  }
        .w-content{background: #f13b3d;min-width: 1000px;}
        .w-content a{display: block;  width: 100%;height: 100%;}
        .w-content a img{display: block;border: none;width: 100%;}
        .w-content .section{width: 980px;  height:454px;padding-bottom: 44px;margin: 0 auto;}
        .w-content .last-btn{width: 952px;  height:95px;margin: 0 auto;position: relative;left: 15px;padding-bottom: 60px;}
    </style>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/j/lib/jquery.js"></script>
	<script src="<%=Constants.get("UHJ_CSS_SERVER") %>/j/jend/jend.js"></script>
	<script>var SITE_ID=16, _jend_page_loadtime = new Date().getTime();JEND.track.init();</script>	
</head>
<body>
<div class="w-content">
    <div class="head">
        <a href="javascript:;">
            <img src="<%=Constants.get("UHJ_CSS_SERVER") %>/i/event/2016/0423/top-1.jpg" alt="">
            <img src="<%=Constants.get("UHJ_CSS_SERVER") %>/i/event/2016/0423/top-2.jpg" alt="">
            <img src="<%=Constants.get("UHJ_CSS_SERVER") %>/i/event/2016/0423/top-3.jpg" alt="">
        </a>
    </div>
    <div class="section"><a href="javascript:;"><img src="<%=Constants.get("UHJ_CSS_SERVER") %>/i/event/2016/0423/section-1.jpg" alt=""></a></div>
    <div class="section"><a href="javascript:;"><img src="<%=Constants.get("UHJ_CSS_SERVER") %>/i/event/2016/0423/section-2.jpg" alt=""></a></div>
    <div class="section"><a href="javascript:;"><img src="<%=Constants.get("UHJ_CSS_SERVER") %>/i/event/2016/0423/section-3.jpg" alt=""></a></div>
    <div class="section"><a href="javascript:;"><img src="<%=Constants.get("UHJ_CSS_SERVER") %>/i/event/2016/0423/section-4.jpg" alt=""></a></div>
    
    <div class="last-btn"><a id="btn" href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_upLimit.action"><img src="<%=Constants.get("UHJ_CSS_SERVER") %>/i/event/2016/0423/last-btn.jpg" alt=""></a></div>
</div>
		<script type="text/javascript">
			/*统计*/
			var track = function(action, actionNote, actionValue) {
		        var date = ['_trackEvent', "yzg-zgd", action || '', actionNote || '', actionValue || ''];
		        window['_hmt'] && window['_hmt'].push(date);//百度
		        window['_utrack'] && window['_utrack'].push(date);//ule
		    };
		    /*统计非白名单页面初始化*/
			track("enter", "noQualify-init");
			/*统计查看预估额度*/
		    $('#btn').live('click',function(){
		        track("click", "look-yugue", "");
		    });
					
		</script>
</body>
</html>
