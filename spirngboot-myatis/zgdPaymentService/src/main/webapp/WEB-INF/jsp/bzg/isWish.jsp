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
    <title>借款意愿</title>
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
		function checkParams(formName){
			if(formName=='no_wish_form'){
				if($('#isOther').attr('checked')){
					var content=$('input[name="otherContent"]').val();
					if(null==content || 0==content.replace(/ /g,'').length){
					    dialog({ title : '提示',content : '其他原因不能为空'});
						return false;
					}else if(content.length<5){
						dialog({ title : '提示',content : '原因不能少于5个字符'});
						return false;
					}	
				}else{
					var noWishReason=$('input:radio[name="noWishReason"]:checked').val();
					if(null==noWishReason){
						dialog({ title : '提示',content : '请选择无意愿借款原因'});
						return false;
					}
				}
				var nextPatrolTime=$('input[name="nextPatrolTime"]').val();
				if(null==nextPatrolTime || 0==nextPatrolTime.replace(/ /g,'').length){
					dialog({ title : '提示',content : '下次巡店时间不能为空'});
					return false;
				}	
				if(checkDate(nextPatrolTime)){
					dialog({ title : '提示',content : '下次巡店时间不能晚于当前日期'});
					return false;
				}
				$('#wish_form').trigger("reset")
			}else{
				var predictLoanTime=$('input[name="predictLoanTime"]').val();
				if(null==predictLoanTime || 0==predictLoanTime.replace(/ /g,'').length){
					dialog({ title : '提示',content : '预计贷款时间不能为空'});
					return false;
				}	
				if(checkDate(predictLoanTime)){
					dialog({ title : '提示',content : '预计贷款时间不能晚于当前日期'});
					return false;
				}
				$('#no_wish_form').trigger("reset")
				$('input[name="otherContent"]').attr('disabled','disabled');
			}
			return true;
		}
		function getJsonParams(formName){
		    var jsonParams='{';
		    if(formName=='no_wish_form'){
		    	jsonParams+="'isWish'"+":"+"'"+$('#rd_no_wish').val()+"',";
			    var nextPatrolTime=$('input[name="nextPatrolTime"]').val();
		    	jsonParams+="'nextPatrolTime'"+":"+"'"+nextPatrolTime+"',";
		    	if(!$('#isOther').attr('checked')){
		    		jsonParams+="'isOther':'0',";
		    	}
			}else{
				jsonParams+="'isWish'"+":"+"'"+$('#rd_wish').val()+"',";
				var predictLoanTime=$('input[name="predictLoanTime"]').val();
		    	jsonParams+="'predictLoanTime'"+":"+"'"+predictLoanTime+"',";
			}
		    
		    var obj = $('#'+formName).serializeArray();
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
			$('input[name="nextPatrolTime"]').val(addDate(30));
			$('input[name="predictLoanTime"]').val(addDate(30));
			$('#rd_wish').attr('checked','checked');
			$('#rd_wish').click(function(){
				$('#div_no_wish').hide();
				$('#div_wish').show();
			});
			$('#rd_no_wish').click(function(){
				$('#div_wish').hide();
				$('#div_no_wish').show();
			});

			$('input[name="noWishReason"]').each(function(i,n){
				$(n).click(function(){
					$('#isOther').removeAttr("checked");//不选中
					$('input[name="otherContent"]').attr('disabled','disabled').val('');
				});
			});
			
			$('#isOther').click(function(){
				$('input[name="noWishReason"]').removeAttr("checked");//不选中
				$('input[name="otherContent"]').removeAttr('disabled');
			});
			
			$('#comp_no_wish_but').click(function(){
				if(checkParams('no_wish_form')){
					$(this).attr('disabled',"disabled").css({'background':'#ccc'});
					$.ajax({
	                    type: "post", 
	                    contentType: "application/json", 
	                    url: '<%=request.getContextPath()%>/promoter/feedback', 
	                    data: getJsonParams('no_wish_form'),
	                    dataType: 'json',
	                    success: function (result) { 
	                    	$('#comp_no_wish_but').removeAttr("disabled").css({'background':'#069d5e'});
	                    	dialog({ title : '提示',content : result.msg});
	                  	},
	                  	error : function() { 
	                  		$('#comp_no_wish_but').removeAttr("disabled").css({'background':'#069d5e'});
                  			dialog({ title : '提示',content : '请求异常！'});
				    	}  
	                });
				}
			});
			
			$('#comp_wish_but').click(function(){
				if(checkParams('wish_form')){
					$(this).attr('disabled',"disabled").css({'background':'#ccc'});
					$.ajax({
	                    type: "post", 
	                    contentType: "application/json", 
	                    url: '<%=request.getContextPath()%>/promoter/feedback', 
	                    data: getJsonParams('wish_form'),
	                    dataType: 'json',
	                    success: function (result) { 
	                    	$('#comp_wish_but').removeAttr("disabled").css({'background':'#069d5e'});
	                    	dialog({ title : '提示',content : result.msg});
	                  	},
	                  	error : function() {  
	                  		$('#comp_wish_but').removeAttr("disabled").css({'background':'#069d5e'});
	                  		dialog({ title : '提示',content : '请求异常！'});
				    	}  
	                });
				}
			});
			$("[id='back_but']").each(function(){
				$(this).click(function(){
					window.location.href='<%=Constants.get("CP_HELPER_TASK")%>';
				});				
			});
		});

		/** 
		* 以下为html5代码,获取地理位置 
		*/ 
		function getLocation() { 
			//检查浏览器是否支持地理位置获取 
			if (navigator.geolocation) { 
				//若支持地理位置获取,成功调用showPosition(),失败调用showError 
				var config = { enableHighAccuracy: true, timeout: 20000, maximumAge: 30000 }; 
				navigator.geolocation.getCurrentPosition(showPosition, showError, config); 
			} else { 
				//alert("Geolocation is not supported by this browser."); 
				alert("定位失败,用户已禁用位置获取权限"); 
			} 
		}  
		/** 
		* 获取地址位置成功 
		*/ 
		function showPosition(position) { 
			//获得经度纬度 
			var x = position.coords.latitude; 
			var y = position.coords.longitude; 
			//配置Baidu Geocoding API 
			var url = "http://api.map.baidu.com/geocoder/v2/?ak=C93b5178d7a8ebdb830b9b557abce78b" + 
			"&callback=renderReverse" + 
			"&location=" + x + "," + y + 
			"&output=json" + 
			"&pois=0"; 
			$.ajax({ 
			type: "GET", 
			dataType: "jsonp", 
			url: url, 
			success: function (json) { 
			if (json == null || typeof (json) == "undefined") { 
				return; 
			} 
			if (json.status != "0") { 
				return; 
			} 
				setAddress(json.result.addressComponent); 
			}, 
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
				alert("[x:" + x + ",y:" + y + "]地址位置获取失败,请手动选择地址"); 
			} 
			}); 
		} 
		/** 
		* 获取地址位置失败[暂不处理] 
		*/ 
		function showError(error) { 
			switch (error.code) { 
			case error.PERMISSION_DENIED: 
			alert("定位失败,用户拒绝请求地理定位"); 
			//x.innerHTML = "User denied the request for Geolocation.[用户拒绝请求地理定位]" 
			break; 
			case error.POSITION_UNAVAILABLE: 
			alert("定位失败,位置信息是不可用"); 
			//x.innerHTML = "Location information is unavailable.[位置信息是不可用]" 
			break; 
			case error.TIMEOUT: 
			alert("定位失败,请求获取用户位置超时"); 
			//x.innerHTML = "The request to get user location timed out.[请求获取用户位置超时]" 
			break; 
			case error.UNKNOWN_ERROR: 
			alert("定位失败,定位系统失效"); 
			//x.innerHTML = "An unknown error occurred.[未知错误]" 
			break; 
			} 						
		} 
		/** 
		* 设置地址 
		*/ 
		function setAddress(json) { 
			var position = document.getElementById("txtPosition"); 
			//省 
			var province = json.province; 
			//市 
			var city = json.city; 
			//区 
			var district = json.district; 
			province = province.replace('市', ''); 
			position.value = province + "," + city + "," + district; 
			position.style.color = 'black'; 
		}
	</script>
</head>
<body>
	<div class="content">
        <header>
            <p class="p1">${storeName}</p>
            <p class="p2">${address}</p>
        </header>
        <p class="title">掌柜贷推广反馈</p>
        <p id="demo">点击这个按钮，获得您的位置：</p>
		<button onclick="getLocation()">试一下</button>
		<div id="mapholder"></div>
        <section class="from-xd">
            <span class="small-triangle"></span>
            <div class="from-warp">
                <p class="from-radio">
                    <label><input type="radio" id="rd_wish" checked="checked" name="wish" value="1"><span>有意愿</span></label>
                    <label><input type="radio" id="rd_no_wish" name="wish" value="0"><span>无意愿</span></label>
                </p>
				<div id="div_no_wish" style="display: none;">
					<form id="no_wish_form">
						<input type="hidden" name="taskType" value="3"/>
						<input type="hidden" name="taskNo" value="${taskNo}"/>
		        		<input type="hidden" name="userOnlyId" value="${userOnlyId}"/>
		        		<input type="hidden" name="loanApplyStatus" value="${status}"/>
		                <div class="check-warp">
		                    <p>
		                        <label class="l1">
		                            <input type="radio" name="noWishReason" value="1">
		                        </label>
		                        <label class="l2">
		                            1 、客户对金融产品不满意，如贷款期限，贷款额度，利息还款等方面不满意
		                        </label>
		                    </p>
		                    <p>
		                        <label class="l1">
		                            <input type="radio" name="noWishReason" value="2">
		                        </label>
		                        <label class="l2">
		                            2 、客户暂时没有资金需求
		                        </label>
		                    </p>
		                    <p>
		                        <label class="l1">
		                            <input type="radio" name="noWishReason" value="3">
		                        </label>
		                        <label class="l2">
		                            3 、客户觉得操作复杂不想申请或由于过多的退回拒绝客户不打算申请
		                        </label>
		                    </p>
		                </div>
						<div class="check-warp">
							 <p style="height: 20px; padding-top: 5px;">
		                        <label class="l1">
		                            <input type="radio" id="isOther" value="1" name="isOther">
		                        </label>
		                        <label class="l2">
		                            <input type="text" name="otherContent" min="5" placeholder="其他原因" disabled="disabled" style="height: 18px;margin-top: 1px;"/>
		                        </label>
		                    </p>
						</div>
		                <div class="time" style="margin-top: 30px;">
		                    <label>下次巡店时间</label>
		                    <input type="date" name="nextPatrolTime">
		                </div>
	                </form>
	                <div class="sub-warp">
	                	<a href="javascript:;" class="back-btn" id="back_but">返回</a>
	                	<input type="button" class="sub-btn" id="comp_no_wish_but" value="完成" />
	                </div>
                </div>
                
                <div id="div_wish">
                	<form id="wish_form">
                		<input type="hidden" name="taskType" value="3"/>
                		<input type="hidden" name="taskNo" value="${taskNo}"/>
		        		<input type="hidden" name="userOnlyId" value="${userOnlyId}"/>
		        		<input type="hidden" name="loanApplyStatus" value="${status}"/>
		                <div class="time" style="margin-top: 30px;">
		                    <label>预计贷款时间</label>
		                    <input type="date" name="predictLoanTime">
		                </div>
	                </form>
	                <div class="sub-warp">
                    	<a href="javascript:;" class="back-btn" id="back_but">返回</a>
                    	<input type="button" class="sub-btn" id="comp_wish_but" value="完成" />
                	</div>
                </div>
            </div>
        </section>
    </div>
</body>
</html>