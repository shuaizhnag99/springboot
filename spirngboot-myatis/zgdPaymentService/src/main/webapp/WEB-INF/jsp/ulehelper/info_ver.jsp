<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.ule.uhj.util.Constants" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
	    <meta name="mobile-web-app-capable" content="yes" />
	    <meta name="apple-mobile-web-app-capable" content="yes" />
	    <meta name="format-detection" content="telephone=no" />
		<title>信息确认页</title>
		<link rel="stylesheet" type="text/css" href="http://i0.beta.ule.com/c/event/2016/1020/reset.css"/>
		<link rel="stylesheet" type="text/css" href="http://i0.beta.ule.com/c/event/2016/1020/info_ver.css"/>
	</head>
	<body>
		<div class="info_ver_bg">
<!-- 			<div class="ver_head"> -->
<!-- 				<span><</span> -->
<!-- 				掌柜贷 -->
<!-- 			</div> -->
			<div class="ver_content">
				<ul>
					<li>姓名<input disabled="disabled" type="text" name="name" value="" /></li>
					<li>身份证号<input disabled="disabled" type="text" name="idc" value="" /></li>
					<li>营业执照注册号<input type="text" name="bunum" value="" /></li>
					<li>店铺名称<input disabled="disabled" type="text" name="store" value="" /></li>
					<li>店铺所有人姓名<input type="text" name="stname" value="" /></li>
				</ul>
			</div>
			<input type='hidden' value='${userOnlyId}' id="userOnlyId" />
			<input type='hidden' value='${orgCode}' id="orgCode" />
			<div class="ver_foot">
				<p class="info_ch">修改</p>
				<p class="info_tr">确认</p>
			</div>
			<div class="ps">
				<img src="http://i0.beta.ule.com/c/event/2016/1020/i/close.png" alt="" class="close" />
				<p id="error_message">对不起，该掌柜的信息有误，请点击修改跳转至掌柜基础信息中修改</p>
			</div>
		</div>
	</body>
	<script src="http://i1.beta.ule.com/j/app/event/2016/1020/zepto.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="http://i1.beta.ule.com/j/app/event/2016/1020/rem-w-debug.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function () {
			var uid = $("#userOnlyId").val();
            $.ajax({ 
                type:"get",
                url:'/lendvps/financerhelper/accountInfoQuery.do',
                data:{"userOnlyId":$("#userOnlyId").val()},
                dataType:"jsonp", 
                jsonp:"jsoncallback", 
                success:function(data){ 
                	var _data = data.dataMap;
                    $("input[name='name']").val(_data.personName);
                    $("input[name='idc']").val(_data.personCode);
                    $("input[name='bunum']").val(_data.regCode);
					$("input[name='store']").val(_data.shopName);
					$("input[name='stname']").val(_data.shopOwnName);
                },
                error:function () {
//              	$(".ps").show();
                }
            }); 
			$(".info_tr").click(function () {
				var name = $("input[name='name']").val();
				var idc = $("input[name='idc']").val();
				var bunum = $("input[name='bunum']").val();
				var store = $("input[name='store']").val();
				var stname = $("input[name='stname']").val();
//				if((name==""||null)||(idc==""||null)||(bunum==""||null)||(store==""||null)||(stname==""||null)){
//					$(".ps").show();
//				}
				var paramer = new Object();
				paramer.userOnlyId = uid;
				paramer.regCode = bunum;
				paramer.shopOwnName = stname;
				$.ajax({ 
	                type:"post",
	                url:'/lendvps/financerhelper/accountInfoCheck.do',
	                contentType: "application/json",
	                data:paramer,
	                success:function(data){ 
	                	if(data.code == "000000"){
//	            			window.location="http://127.0.0.1:8020/TESTwc/liuxiaochen/%E9%82%AE%E5%8A%A9%E6%89%8B-%E7%A7%BB%E5%8A%A8%E7%AB%AF/info_upload.html";
	                	}else{
	                		$("#error_message").html(data.message);
	                		$(".ps").show();
	                	}
	                },
	                error:function () {
	                	$(".ps").show();
	                }
	           });        
			});
//			=====================================
			$(".info_ch").click(function () {
				var orgCode = $("#orgCode").val();
				window.location.href = "ULEMOBILE://bossDetail_"+orgCode;
			});
			$(".close").click(function () {
				$(".ps").hide();
			});
					
		});
	</script>
</html>
