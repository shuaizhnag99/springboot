<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ule.uhj.util.Constants" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
    <meta name="mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="format-detection" content="telephone=no" />
    <title>掌柜是否重新申请</title>
    <link rel="stylesheet" href="../css/feedback.css" />
    <link rel="stylesheet" href="../css/dialog.css" />
	<script src="<%=Constants.get("UHJ_CSS_SERVER") %>/j/lib/jquery.js"></script>
	<script src="../js/dialog.js"></script>
	<script type="text/javascript">
		function getJsonParams(){
		    var jsonParams='{';
		    var obj = $('#anewApplyForm').serializeArray();
		    $.each(obj, function(i,n) {
		    	jsonParams+="'"+n.name+"':"+"'"+n.value+"',";
		    });
		    if(jsonParams.length>1){
		    	jsonParams=jsonParams.substring(0,(jsonParams.length)-1);
		    }
		    jsonParams+='}';
		    return encodeURI(jsonParams);
		};
		$(function(){
			$('#comp_but').click(function(){
				$(this).attr('disabled',"disabled").css({'background':'#ccc'});
				$.ajax({
                    type: "post", 
                    contentType: "application/json", 
                    url: '<%=request.getContextPath()%>/promoter/feedback', 
                    data: getJsonParams('wish_form'),
                    dataType: 'json',
                    success: function (result) {
                    	$('#comp_but').removeAttr("disabled").css({'background':'#069d5e'}); 
                    	dialog({ title : '提示',content : result.msg});
                  	},
                  	error : function() {  
                  		$('#comp_but').removeAttr("disabled").css({'background':'#069d5e'});
                  		dialog({ title : '提示',content : '请求异常！'});  
			    	}  
                });
			});
			
			$('#back_but').click(function(){
				window.location.href='<%=Constants.get("CP_HELPER_TASK")%>';
			});
		});
	</script>
</head>
<body>
	<div class="content">
        <header>
            <p class="p1">${storeName}</p>
            <p class="p2">${address}</p>
        </header>
        <p class="title">掌柜贷推广反馈</p>
        <section class="from-xd">
        	<form id="anewApplyForm">
        		<input type="hidden" name="taskNo" value="${taskNo}"/>
        		<input type="hidden" name="userOnlyId" value="${userOnlyId}"/>
        		<input type="hidden" name="taskType" value="3"/>
        		<input type="hidden" name="loanApplyStatus" value="${status}"/>
	            <span class="small-triangle"></span>
	            <div>
	           		<p style="padding-top:12px;padding-left:13px;">掌柜是否重新申请？</p>
	           		<p class="from-radio" style="padding-top:18px;padding-left:13px;">
	                    <label><input type="radio" checked="checked" name="isAnewApply" value="1"><span>已申请</span></label>
	                    <label><input type="radio" name="isAnewApply" value="0"><span>未申请</span></label>
	                </p>
	           	</div>
	           	<div class="sub-warp">
	           		<a href="javascript:;" class="back-btn" id="back_but">返回</a>
	           		<input type="button" class="sub-btn" id="comp_but" value="完成" />
	            </div>
            </form>
        </section>
    </div>
</body>
</html>