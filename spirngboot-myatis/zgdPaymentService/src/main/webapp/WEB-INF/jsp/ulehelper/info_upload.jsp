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
		<title>拍照上传页</title>
		<link rel="stylesheet" type="text/css" href="http://i0.beta.ule.com/c/event/2016/1020/reset.css"/>
		<link rel="stylesheet" type="text/css" href="http://i0.beta.ule.com/c/event/2016/1020/info_up.css"/>
	</head>
	<body>
		<div class="info_up_bg">
			<div class="up_content">
				<form method="post" enctype="multipart/form-data" id="imgsform">
				<input type='hidden' value='${userOnlyId}' name="userOnlyId" />
				<ul class="up_ul">
					<li class="up_li">
						<div class="div1">
							<p class="p1">身份证正面照片</p>
							<p class="p2">1)文字和照片清晰;2)光线充足</p>
						</div>
						<div class="div2" id="aaa">
							<img src="" alt="" class="up_img up_img1" id="img1" />
							<div class="shili">示例图</div>
						</div>
						<div class="div3">
							<p class="photos photo1">
								拍照<input type="file" name="certPos" class="updateInput inp1" id="file1">
							</p>
						</div>
					</li>
					<li class="up_li">
						<div class="div1">
							<p class="p1">身份证反面照片</p>
							<p class="p2">1)文字和照片清晰;2)光线充足</p>
						</div>
						<div class="div2" id="bbb">
							<img src="" alt="" class="up_img up_img2" id="img2" />
							<div class="shili">示例图</div>
						</div>
						<div class="div3">
							<p class="photos photo2">
								拍照<input type="file" name="certNeg" class="updateInput inp2" id="file2">
							</p>
						</div>
					</li>
					<li class="up_li">
						<div class="div1">
							<p class="p1">手持身份证照片</p>
							<p class="p2">1)五官清晰无遮挡;2)身份证资料清晰;3)光线充足</p>
						</div>
						<div class="div2" id="ccc">
							<img src="" alt="" class="up_img up_img3" id="img3" />
							<div class="shili">示例图</div>
						</div>
						<div class="div3">
							<p class="photos photo3">
								拍照<input type="file" name="certHold" class="updateInput inp3" id="file3">
							</p>
						</div>
					</li>
					<li class="up_li">
						<div class="div1">
							<p class="p1">营业执照照片</p>
							<p class="p2">1)完整的整个页面，若提供副本需包含年检信息;2)文字清晰可辨认</p>
						</div>
						<div class="div2" id="ddd">
							<img src="" alt="" class="up_img up_img4" id="img4" />
							<div class="shili">示例图</div>
						</div>
						<div class="div3">
							<p class="photos photo4">
								拍照<input type="file" name="busLience" class="updateInput inp4" id="file4">
							</p>
						</div>
					</li>
				</ul>
				</form>
			</div>
			<div class="up_foot">
				<p class="info_qx">取消</p>
				<p class="info_tj">提交</p>
			</div>
			<div class="ps">
				<img src="http://i0.beta.ule.com/c/event/2016/1020/i/close.png" alt="" class="close" />
				<p>对不起，照片信息有误或照片未拍摄，请核对后提交</p>
			</div>
		</div>
	</body>
	<script src="http://i1.beta.ule.com/j/app/event/2016/1020/zepto.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="http://i1.beta.ule.com/j/app/event/2016/1020/rem-w-debug.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(function () {
			var imgURL = ["http://i0.beta.ule.com/c/event/2016/1020/i/zheng.jpg","http://i0.beta.ule.com/c/event/2016/1020/i/fan.jpg","http://i0.beta.ule.com/c/event/2016/1020/i/shouchi.jpg","http://i0.beta.ule.com/c/event/2016/1020/i/zhizhao.jpg"];
			$(".up_img1").attr("src",imgURL[0]);
			$(".up_img2").attr("src",imgURL[1]);
			$(".up_img3").attr("src",imgURL[2]);
			$(".up_img4").attr("src",imgURL[3]);
			var img1Url,img2Url,img3Url,img4Url;
			var input1 = document.getElementById("file1");
			var img1 = document.getElementById("img1");
			if(typeof FileReader === 'undefined') {
				$("#aaa").innerHTML = "抱歉，你的浏览器不支持 FileReader";
				input1.setAttribute('disabled', 'disabled');
			} else {
				input1.addEventListener('change', readFile1, false); 
			}
	
			function readFile1() {
				var file = this.files[0]; 
				if(!/image\/\w+/.test(file.type)) {
					alert("文件必须为图片！");
					return false;
				}
	
				var reader = new FileReader();
				reader.readAsDataURL(file);
				reader.onload = function(e) {
					//aaa.innerHTML = '<img src="'+this.result+'" alt=""/>'
					img1Url = this.result;
					img1.src = this.result;
					$("#img1").siblings(".shili").hide();
				}
			}
			var input2 = document.getElementById("file2");
			var img2 = document.getElementById("img2");
			if(typeof FileReader === 'undefined') {
				$("#bbb").innerHTML = "抱歉，你的浏览器不支持 FileReader";
				input2.setAttribute('disabled', 'disabled');
			} else {
				input2.addEventListener('change', readFile2, false); 
			}
	
			function readFile2() {
				var file = this.files[0];
				if(!/image\/\w+/.test(file.type)) {
					alert("文件必须为图片！");
					return false;
				}
	
				var reader = new FileReader(); 
				reader.readAsDataURL(file); 
				reader.onload = function(e) {
					//aaa.innerHTML = '<img src="'+this.result+'" alt=""/>'
					img2Url = this.result;
					img2.src = this.result;
					$("#img2").siblings(".shili").hide();
				}
			}
			var input3 = document.getElementById("file3");
			var img3 = document.getElementById("img3");
			if(typeof FileReader === 'undefined') {
				$("#ccc").innerHTML = "抱歉，你的浏览器不支持 FileReader";
				input3.setAttribute('disabled', 'disabled');
			} else {
				input3.addEventListener('change', readFile3, false); 
			}
	
			function readFile3() {
				var file = this.files[0]; 
				if(!/image\/\w+/.test(file.type)) {
					alert("文件必须为图片！");
					return false;
				}
	
				var reader = new FileReader();
				reader.readAsDataURL(file);
				reader.onload = function(e) {
					//aaa.innerHTML = '<img src="'+this.result+'" alt=""/>'
					img3Url = this.result;
					img3.src = this.result;
					$("#img3").siblings(".shili").hide();
				}
			}
			var input4 = document.getElementById("file4");
			var img4 = document.getElementById("img4");
			if(typeof FileReader === 'undefined') {
				$("#ddd").innerHTML = "抱歉，你的浏览器不支持 FileReader";
				input4.setAttribute('disabled', 'disabled');
			} else {
				input4.addEventListener('change', readFile4, false); 
			}
	
			function readFile4() {
				var file = this.files[0]; 
				if(!/image\/\w+/.test(file.type)) {
					alert("文件必须为图片！");
					return false;
				}
	
				var reader = new FileReader(); 
				reader.readAsDataURL(file); 
				reader.onload = function(e) {
					//aaa.innerHTML = '<img src="'+this.result+'" alt=""/>'
					img4Url = this.result;
					img4.src = this.result;
					$("#img4").siblings(".shili").hide();
				}
			}
			
			$(".info_qx").click(function(){
				window.location.assign("/lendvps/uhj/bzg_queryZGManageInfo.do");
			});
			
			$(".info_tj").click(function () {
				if($(".up_img1").attr("src")!=imgURL[0]&&$(".up_img1").attr("src")!=imgURL[0]&&$(".up_img2").attr("src")!=imgURL[1]&&$(".up_img3").attr("src")!=imgURL[2]&&$(".up_img4").attr("src")!=imgURL[3]){
					var mFormData = new FormData($("#imgsform")[0]);
					$.ajax({  
				          url: '/lendvps/financerhelper/uploadImg.do' ,  
				          type: 'POST',  
				          data: mFormData,  
				          dataType:"json",
				          async: false,  
				          cache: false,  
				          contentType: false,  
				          processData: false,  
				          beforeSend:function(){
				        	alert("即将开始上传图片，视您的网络情况，过程可能会持续10~30秒左右，请耐心等待。"); 
				          },
				          success: function (data) {  
				              if(data.code=='000000'){
				            	  alert("图片上传成功!");
				            	  window.location.assign("/lendvps/uhj/bzg_queryZGManageInfo.do");
				              } else{
				            	  alert(data.message);
				              }
				          },  
				          error: function (returndata) {  
				              alert(returndata);  
				          }  
				     });  
				}
			});
			
			
			$(".close").click(function () {
				$(".ps").hide();
			});
			
		});
	</script>
</html>

