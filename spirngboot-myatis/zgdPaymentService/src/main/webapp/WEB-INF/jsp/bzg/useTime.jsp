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
    <title>借款何时支用提醒</title>
    <link rel="stylesheet" href="../css/feedback.css" />
    <link rel="stylesheet" href="../css/dialog.css" />
	<script src="<%=Constants.get("UHJ_CSS_SERVER") %>/j/lib/jquery.js"></script>
	<script src="../js/dialog.js"></script>
	<script type="text/javascript">
		function checkDate(targetDate){
			var elem=targetDate.split("-");
			var myDate=new Date();
			myDate.setFullYear(elem[0]*1,((elem[1]*1)-1),elem[2]*1);
			var today = new Date();
			if (myDate<today){
				return true;
			}
		}

		function addDate(AddDayCount){ 
			var dd = new Date(); 
			dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期 
			var y = dd.getFullYear(); 
			var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
			var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate(); //获取当前几号，不足10补0
			return y+"-"+m+"-"+d; 
		}
		
		function checkParams(){
			var useTime=$('input[name="useTime"]').val();
			if(null==useTime || 0==useTime.replace(/ /g,'').length){
				dialog({ title : '提示',content : '支用时间不能为空'});  
				return false;
			}	
			if(checkDate(useTime)){
				dialog({ title : '提示',content : '支用时间不能晚于当前日期'});
				return false;
			}
			var use=$('select[name="use"]').val();
			if('0'==use){
				dialog({ title : '提示',content : '请选择支用用途'});  
				return false;
			}
			return true;
		}
	
		function getJsonParams(){
		    var jsonParams='{';
		    var useTime=$('input[name="useTime"]').val();
	    	jsonParams+="'useTime'"+":"+"'"+useTime+"',";
		    var obj = $('#useForm').serializeArray();
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
			$('input[name="useTime"]').val(addDate(7));
			$('#comp_but').click(function(){
				if(checkParams()){
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
				}
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
        	<form id="useForm">
        		<input type="hidden" name="taskNo" value="${taskNo}"/>
        		<input type="hidden" name="userOnlyId" value="${userOnlyId}"/>
        		<input type="hidden" name="taskType" value="3"/>
        		<input type="hidden" name="loanApplyStatus" value="${status}"/>
	            <span class="small-triangle"></span>
	           	<div>
	           		<p style="padding-top:12px;padding-left:13px;">掌柜何时支用？</p>
	           		<p style="padding-top:12px;padding-left:13px;"><input type="date" name="useTime" /></p>
	           		<p style="padding-top:12px;padding-left:13px;">支用用途：
	           			<select name="use">
	           				<option value="0" selected="selected">请选择</option>
	           				<option value="1">经营周转</option>
	           				<option value="2">扩大经营</option>
	           				<option value="3">购买农资农具</option>
	           				<option value="4">借给他人</option>
	           				<option value="5">日常消费</option>
	           				<option value="6">红白喜事</option>
	           				<option value="7">其它</option>
	           			</select>
	           		</p>
	           	</div>
	           	<div class="sub-warp" style="margin-top: 16px;">
	           		<a href="javascript:;" class="back-btn" id="back_but">返回</a>
	           		<input type="button" class="sub-btn" id="comp_but" value="完成" />
	            </div>
            </form>
        </section>
    </div>
</body>
</html>