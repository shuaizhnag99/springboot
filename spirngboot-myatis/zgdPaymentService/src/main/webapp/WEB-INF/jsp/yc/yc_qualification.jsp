<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.ule.uhj.util.Constants" %>
<!DOCTYPE html>
<html>
<head>
  <meta  charset="utf-8"/>
  <title>资质上传</title>
 <!--  <link rel="stylesheet" type="text/css" href="http://i0.beta.ule.com/c/my/loan/2016/0111/c/upload.css"/> -->
 <link rel="stylesheet" type="text/css" href="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2016/0111/c/upload.css"/>
 <script type="text/javascript">

 var storeTypeFlag="${applyDetailDto.storePropertyType }";

 </script>
</head>

<body>
  <input type="hidden" id="welab_server" value="<%=Constants.get("UHJ_SERVER")%>" /> 
  <input type="hidden" id="userOnlyId" value="${applyDetailDto.userOnlyId}" /> 
    <div class="header">
      <div style="float: left; display: block;">简单
        <em class="red">3&nbsp;</em>步
        <em>！</em>轻松申请掌柜贷
        <em>！</em>
      </div>
      <span></span>
    </div>
  
  
  <div class="wrap">
    <div class="auto">
      <div class="tt2">上传认证资料<span> (上传照片必须清晰；不能上传拍屏照片)</span>
      </div>
      <div class="content">
    <%--     <div class="lice">
          <span class="info">营业执照注册号 </span>    
          <input type="text" name="lice" id="ipt_lice" value="${applyDetailDto.busLiecnceNo}" /><span class="tip" id="verinfo"></span>
          <input type="hidden" name="storePropertyType" id="storePropertyType" value="${applyDetailDto.storePropertyType }" />
          <span class="tip" id="verinfo"></span>
        </div> --%>
        
        <div class="box">
					<div class="info">
						<c:if test="${empty applyDetailDto.busLiecnceImg}">
							<img src="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/no_pic.jpg" class="pic" width="75" height="75" />
						</c:if>
						<c:if test="${not empty applyDetailDto.busLiecnceImg}">
							<img src="${applyDetailDto.busLiecnceImg}" class="pic" width="75" height="75" uploaded_url="${applyDetailDto.busLiecnceImg}"/>
						</c:if>
						<div class="picinfo">
							<p>营业执照照片(<font color="red" >必须上传</font>)</p>
							  <ul class="photo-tip">
                                 <li><font color="red" >营业执照注册满一年；</font></li>
                                <li>完整的整个页面；</li>
                                <li>文字清晰可辨认；</li>
                               </ul>
							<a class="btn_prv" orimg="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/busLiecnceImg.jpg">查看示例>></a>
							<span class="tip"></span>
							<span class="err">未上传照片</span>
						</div>
					</div>
					<a  class="btn_upload"><input type="file" name="logoImg" id="logoImg" ></a>
				</div>
				<div class="box">
					<div class="info">
						<c:if test="${empty applyDetailDto.certHoldImg}">
							<img src="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/no_pic.jpg" class="pic" width="75" height="75" />
						</c:if>
						<c:if test="${not empty applyDetailDto.certHoldImg}">
							<img src="${applyDetailDto.certHoldImg}" class="pic" width="75" height="75" uploaded_url="${applyDetailDto.certHoldImg}"/>
						</c:if>
						<div class="picinfo">
							<p>手持身份证照片(<font color="red" >必须上传</font>)</p>
						    <ul class="photo-tip">
                                <li>五官清晰无遮挡；</li>
                                <li>身份证资料清晰；</li>
                                <li>光线充足；</li>
                            </ul>
							<a class="btn_prv" orimg="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/certHoldImg.jpg">查看示例>></a>
							<span class="tip"></span>
							<span class="err">未上传照片</span>
							
						</div>

					</div>
					<a class="btn_upload"><input type="file" name="logoImg" id="logoImg1"></a>
				</div>
				<div class="box">
					<div class="info">
						<c:if test="${empty applyDetailDto.storeImg}">
							<img src="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/no_pic.jpg" class="pic" width="75" height="75" />
						</c:if>
						<c:if test="${not empty applyDetailDto.storeImg}">
							<img src="${applyDetailDto.storeImg}" class="pic" width="75" height="75" uploaded_url="${applyDetailDto.storeImg}"/>
						</c:if>
						<div class="picinfo">
							<p>店面照片(<font color="red" >必须上传</font>)</p>
						    <ul class="photo-tip">
                                <li>有完整的店铺名称；</li>
                                <li>门牌号（如有），门牌号清晰可见；</li>
                            </ul>
							<a class="btn_prv" orimg="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/storeImg.jpg">查看示例>></a>
							<span class="tip"></span>
							<span class="err">未上传照片</span>
						</div>

					</div>
					<a  class="btn_upload"><input type="file" name="logoImg" id="logoImg2"></a>
				</div>
				<div class="box">
					<div class="info">
						<c:if test="${empty applyDetailDto.certPositiveImg}">
							<img src="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/no_pic.jpg" class="pic" width="75" height="75" />
						</c:if>
						<c:if test="${not empty applyDetailDto.certPositiveImg}">
							<img src="${applyDetailDto.certPositiveImg}" class="pic" width="75" height="75" uploaded_url="${applyDetailDto.certPositiveImg}"/>
						</c:if>
						<div class="picinfo">
							<p>身份证正面照片(<font color="red" >必须上传</font>)</p>
						    <ul class="photo-tip">
                                <li>文字和照片清晰；</li>
                                <li>光线充足；</li>
                            </ul>
							<a class="btn_prv" orimg="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/certPositiveImg.jpg">查看示例>></a>
							<span class="tip"></span>
							<span class="err">未上传照片</span>
						</div>

					</div>
					<a class="btn_upload"><input type="file" name="logoImg" id="logoImg3" ></a>
				</div>
				<div class="box">
					<div class="info">
						<c:if test="${empty applyDetailDto.storeInnerImg}">
							<img src="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/no_pic.jpg" class="pic" width="75" height="75" />
						</c:if>
						<c:if test="${not empty applyDetailDto.storeInnerImg}">
							<img src="${applyDetailDto.storeInnerImg}" class="pic" width="75" height="75" uploaded_url="${applyDetailDto.storeInnerImg}"/>
						</c:if>
						<div class="picinfo">
							<p>店铺内照片(<font color="red" >必须上传</font>)</p>
						   <ul class="photo-tip">
                                <li>照片内要包含所有货架、收银台、店铺内其他等；</li>
                            </ul>
							<a class="btn_prv" orimg="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/storeInnerImg.jpg">查看示例>></a>
							<span class="tip"></span>
							<span class="err">未上传照片</span>
						</div>

					</div>
					<a  class="btn_upload"><input type="file" name="logoImg" id="logoImg4" ></a>
				</div>
				<div class="box">
					<div class="info">
						<c:if test="${empty applyDetailDto.certNegativeImg}">
							<img src="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/no_pic.jpg" class="pic" width="75" height="75" />
						</c:if>
						<c:if test="${not empty applyDetailDto.certNegativeImg}">
							<img src="${applyDetailDto.certNegativeImg}" class="pic" width="75" height="75" uploaded_url="${applyDetailDto.certNegativeImg}" />
						</c:if>
						<div class="picinfo">
							<p>身份证反面照片(<font color="red" >必须上传</font>)</p>
						   <ul class="photo-tip">
                                <li>文字和照片清晰；</li>
                                <li>光线充足；</li>
                            </ul>
							<a class="btn_prv" orimg="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/certNegativeImg.jpg">查看示例>></a>
							<span class="tip"></span>
							<span class="err">未上传照片</span>
						</div>

					</div>
					<a class="btn_upload"><input type="file" name="logoImg" id="logoImg5" ></a>
				</div>
        
        
        <c:if test="${ applyDetailDto.storePropertyType=='0'}">
        
        <div class="box" style="position: relative;">
          <div class="info">
            	  <c:if test="${empty applyDetailDto.storePropertyUrl}">
							<img src="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/no_pic.jpg" class="pic" width="75" height="75" />
						</c:if>
						<c:if test="${not empty applyDetailDto.storePropertyUrl}">
							<img src="${applyDetailDto.storePropertyUrl}" class="pic" width="75" height="75" uploaded_url="${applyDetailDto.storePropertyUrl}" />
				    </c:if>
            
            
            <div class="picinfo">
              <p>店铺房产证明</p>
               <ul class="photo-tip">
                    <li>文字和照片清晰；</li>
                     <li>光线充足；</li>
                 </ul>
              <a  style="float: none;" class="btn_prv" orimg="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2015/0710/i/certNegativeImg.jpg">查看示例>></a>
              <span class="tip"></span>
              <span class="err">未上传照片</span>
              
            </div>

          </div>
          <a class="btn_upload"><input type="file" name="logoImg" id="logoImg6" ></a>
         
        </div>
        </c:if>

      </div>
    <div class="perInfoRetaPower">
        <label class="zgd-lend-check y-check-con  perInfoRetaPower_check">
          <span class="zgd-check "></span>
          <span class="zgd-check-txt">同意<a href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_perInfoRetaPower.action" target="_blank">《个人信息查询及留存授权书》</a>
          <a href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_fwktxy.action" target="_blank">《掌柜贷服务开通协议》</a>
          </span>
        </label>
      </div>
    </div>
    <div class="btn_box">
      <a class="sbm" id="btn_sbm">提交申请</a><!-- 跳转到Action后的参数：btnFlag=null -->
      <a class="prev" id="btn_prev">上一步</a>
      <span>(上传认证资料，即可进入下一步)</span>
    </div>
    
    
    
  </div>
  <div class="popbox" id="popupbox">
    <div class="tt">
      <span id="picName"></span><a class="close" id="close">X</a>
    </div>
    <div class="img"><img src="" class="pic" id="pop_pic"></div>
  </div>
  <div class="masklayer" id="masklayer"></div>
  	<!-- 公共JS引用 -->
	<script src="https://i1.ule.com/j/lib/jquery.js"></script>
	<script src="<%=Constants.get("UHJ_CSS_SERVER")%>/j/jend/jend.js"></script>
	<!-- /公共JS引用 -->
	<script src="<%=Constants.get("UHJ_SERVER") %>/js/upload/jquery.uploadify.js"></script>
	<script src="<%=Constants.get("UHJ_SERVER") %>/js/upload/swfobject.js"></script>
	<script src="<%=Constants.get("UHJ_SERVER") %>/js/upload/upload.js"></script>
	<script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/j/upload.js"></script>
<%--   <!-- 公共JS引用 -->
	<script src="<%=Constants.get("UHJ_SERVER") %>/js/temporary/upload.js"></script>
  <script src="https://i1.ule.com/j/lib/jquery.js"></script>
  <script src="https://i1.beta.ule.com/j/jend/jend.js"></script>
  <!-- /公共JS引用 -->
  <script src="http://i1.beta.ule.com/c/my/loan/2016/0111/j/jquery.uploadify.js"></script>
  <script src="http://i1.beta.ule.com/c/my/loan/2016/0111/j/swfobject.js"></script>
 <script src="http://i1.beta.ule.com/c/my/loan/2016/0111/j/upload.js"></script> --%>

 
  
<div class="upload_rzzl">
    <div class="popbox-bg"></div>  
    <div class="popbox-img">
    	<a href="javascript:void(0);" class="close"></a>
       <!--  <a href="javascript:void(0);" class="knowBtn"></a> -->
    </div>
</div> 
</body>

</html>