<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ule.uhj.util.Constants"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>提交个人信息</title>
  <link rel="stylesheet" type="text/css" href="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2016/0111/c/form.css">
 <!--  <link rel="stylesheet" type="text/css" href="http://i0.beta.ule.com/c/my/loan/2016/0111/c/form.css" /> -->
</head>
<body>
  <input type="hidden" id="welab_server" value="<%=Constants.get("UHJ_SERVER")%>" /> 
  
    <div class="header">
      <div style="float: left; display: block;">简单
        <em class="red">3&nbsp;</em>步
        <em>！</em>轻松申请掌柜贷
        <em>！</em>
      </div>
      <span></span>
    </div>
  
  
  <div class="intr">
    <a id="btn_slideup"></a>
  </div>
  <div class="wrap">
    <div class="auto">
      <div class="tt1">提交个人信息</div>
      <div class="content">
      <dl>
          <dt><span>*</span>教育水平</dt>
          <dd>
            <span class="slct slct-arr">
              <a></a>
               <input type="hidden" name="applyDetailDto.id " id="apply_id"  relcode="${applyDetailDto.id }" value="${applyDetailDto.id }" />
               <input type="hidden" name="applyDetailDto.age " id="age"  relcode="${applyDetailDto.age }" value="${applyDetailDto.age }" />
                <input type="hidden" name="applyDetailDto.userName " id="userName"  relcode="${applyDetailDto.userName }" value="${applyDetailDto.userName }" />
               <input type="hidden" name="applyDetailDto.userOnlyId " id="userOnlyId"  relcode="${applyDetailDto.userOnlyId }" value="${applyDetailDto.userOnlyId }" />
                <input type="hidden" name="applyDetailDto.sex " id="sex"  relcode="${applyDetailDto.sex }" value="${applyDetailDto.sex }" />
              <input type="text" name="educationalLevel" id="educationalLevel"  relcode="${applyDetailDto.educationalLevel }" placeholder="-请选择-" disabled="disabled"
              />
              <ul>
                <li>研究生</li>
                <li>中等专业学校或中等技术学校</li>
                <li>大学本科</li>
                <li>大专</li>
                <li>技术学校</li>
                <li>高中</li>
                <li>初中</li>
                <li>小学</li>
                <li>文盲或半文盲</li>
              </ul>
            </span>
            <span class="tip" id="educationalLevelTip">&nbsp;</span>
          </dd>
        </dl>
         <dl id="loanProviderId">
          <dt><span>*</span>健康状况</dt>
          <dd>
            <span class="slct slct-arr">
              <a></a>
              <input type="text" name="healthStatus" id="healthStatus" relcode="${applyDetailDto.healthStatus }" placeholder="-请选择-" disabled="disabled"
              />
              <ul>
                <li>健康或良好</li>
                <li>一般或较弱</li>
                <li>有慢性病</li>
                <li>残疾</li>
              </ul>
            </span>
            <span class="tip" id="healthStatusTip">&nbsp;</span>
          </dd>
        </dl>
            <dl>
          <dt><span>*</span>婚姻状况</dt>
          <dd>
            <span class="slct slct-arr">
              <a></a>
              <input type="text" name="maritalStatus" id="maritalStatus"  relcode="${applyDetailDto.maritalStatus }" disabled="disabled" placeholder="-请选择-"/>
              <ul>
                <li>已婚有子女</li>
                <li>已婚无子女</li>
                <li>丧偶</li>
                <li>未婚</li>
                <li>离异</li>
              </ul>
            </span>
            <span class="tip" id="maritalStatusTip">&nbsp;</span>
          </dd>
        </dl>
              <dl class="otherContact-dl">
          <dt><span>*</span>配偶姓名</dt>
          <dd>
            <span>
              <input type="text" class="tal w200" name="otherContact" id="otherContact" value="${applyDetailDto.otherContact }" placeholder="请填写中文名字"
              />
            </span>
            <span class="tip" id="otherContactTip">&nbsp;</span>
          </dd>
        </dl>
        <dl class="contactMobile-dl">
          <dt><span>*</span>配偶手机</dt>
          <dd>
            <input type="text" name="contactMobile" id="contactMobile" value="${applyDetailDto.contactMobile }" placeholder="请填写手机" />
            <span class="tip" id="contactMobileTip">&nbsp;</span>
          </dd>
        </dl>
        <dl class="contactCertNo-dl">
          <dt>
            <span>*</span>配偶身份证号码</dt>
          <dd>
            <input type="text" name="contactCertNo" id="contactCertNo" value="${applyDetailDto.contactCertNo }" placeholder="请填写身份证号码" />

            <span class="tip" id="contactCertNoTip">&nbsp;</span>
          </dd>
        </dl>
          <dl>
          <dt>
            <span>*</span>掌柜居住地址</dt>
          <dd>
            <span>
              <select name="keeperProvince" id="keeperProvince"  relcode="${applyDetailDto.keeperProvince }"></select>省
              <select id="keeperCity" name="keeperCity"  relcode="${applyDetailDto.keeperCity }"></select>市
              <select relcode="${applyDetailDto.keeperArea }" id="keeperArea" name="keeperArea"></select>区
            </span>
            <span class="tip" id="keeperProvinceTip">&nbsp;</span>
          </dd>
        </dl>
           <dl style="height: 108px;">
          <dt></dt>
          <dd>
            <textarea name="keeperAddress" id="keeperAddress" placeholder="请填写详细地址">${applyDetailDto.keeperAddress }</textarea>
            <span class="tip" id="keeperAddressTip">&nbsp;</span>
          </dd>
        </dl>
        
        <dl>
          <dt>
            <span>*</span>本地居住年限</dt>
          <dd>
            <input type="text" name="nativeLiveTime" id="nativeLiveTime" value="${applyDetailDto.nativeLiveTime }" placeholder="请填写本地居住年限" />年
            <span class="err" id="nativeLiveTimeTip">&nbsp;</span>
          </dd>
        </dl>
          <dl>
          <dt><span>*</span>自住房产类型</dt>
          <dd>
            <span class="slct slct-arr">
              <a></a>
              <input type="text" name="ownerHouseType" id="ownerHouseType"  relcode="${applyDetailDto.ownerHouseType }" placeholder="-请选择-" disabled="disabled"
              />
              <ul>
                <li>自购有贷款</li>
                <li>单位宿舍</li>
                <li>自购无贷款</li>
                <li>亲属产权房</li>
                <li>租房</li>
                <li>其他</li>
              </ul>
            </span>
            <span class="tip" id="ownerHouseTypeTip">&nbsp;</span>
          </dd>
        </dl>
         <dl>
          <dt>
            <span>*</span>户籍所在地</dt>
          <dd>
            <span>
              <select name="householdRegisterProvice" id="householdRegisterProvice" relcode="${applyDetailDto.householdRegisterProvice }"></select>省
              <select id="householdRegisterCity" name="householdRegisterCity"  relcode="${applyDetailDto.householdRegisterCity }"></select>市
              <select id="householdRegisterArea" name="householdRegisterArea"  relcode="${applyDetailDto.householdRegisterArea }"></select>区
            </span>
            <span class="tip" id="householdRegisterProviceTip">&nbsp;</span>
          </dd>
        </dl>
          <dl style="height: 108px;">
          <dt></dt>
          <dd>
            <textarea name="householdRegisterAddress" id="householdRegisterAddress"  placeholder="请填写详细地址!">${applyDetailDto.householdRegisterAddress }</textarea>
            <span class="tip" id="householdRegisterAddressTip">&nbsp;</span>
          </dd>
        </dl>
                 
        <dl>
          <dt><span>*</span>本行业从业年限</dt>
          <dd>
            <input type="text" name="tradeWorkYears" id="tradeWorkYears" value="${applyDetailDto.tradeWorkYears }" placeholder="请填写本行业从业年限" value="" />年
            <span class="tip" id="tradeWorkYearsTip">&nbsp;</span>
          </dd>
        </dl>
         <dl>
         <dt><span>*</span>营业执照注册号</dt>
                    <dd>
                        <input type="text" name="ipt_lice" id="ipt_lice" value="${applyDetailDto.busLiecnceNo}" placeholder="营业执照注册号" />
                        <span class="tip" id="ipt_liceTip">&nbsp;</span>
                    </dd>
                </dl>
           <dl>
          <dt><span>*</span>营业执照经营者</dt>
          <dd>
            <span class="slct slct-arr">
              <a></a>
              <input type="text" name="busLicManager" id="busLicManager"  relcode="${applyDetailDto.busLicManager}"  disabled="disabled" placeholder="-请选择-"/>
              <ul>
                <li>本人</li>
                <li>配偶</li>
              
              </ul>
            </span>
            <span class="tip" id="busLicManagerTip">&nbsp;</span>
          </dd>
        </dl>       
                
        <dl>
          <dt><span>*</span>店铺房产类型</dt>
          
           <dd>
            <span class="slct slct-arr">
              <a></a>
              <input type="text" name="storePropertyType" id="storePropertyType" relcode="${applyDetailDto.storePropertyType }" disabled="disabled" placeholder="-请选择-" />
              <ul>
                <li>自有</li>
                <li>租赁</li>
                <li>以后再说</li>
              </ul>
            </span>
            <div class="storeMouthRent_con">
              <span>&nbsp;房屋月租金&nbsp;</span>
              <span>
                <input type="text" class="tal" name="storeMouthRent" id="storeMouthRent" value="${applyDetailDto.storeMouthRent }" placeholder="请填写租金">
              </span>
            </div>
            <span class="tip" id="storeMouthRentTip">&nbsp;</span>
            <div class="stTypeTip">
                            <span>&nbsp;房产所有人与本人关系&nbsp;</span>
                            <span class="slct slct-arr">
                                <a></a>
                                <input type="text" name="proOwnerRelation_d" id="proOwnerRelation_d" relcode="${applyDetailDto.proOwnerRelation}" placeholder="-请选择-" disabled="disabled" />
                                <ul>
                                    <li>我自己</li>
                                    <li>父母</li>
                                    <li>配偶</li>
                                    <li>子女</li>
                                    <li>其他</li>
                                </ul>
                            </span>
                        </div>
                        <span class="tip" id="storeMouthRentTip">&nbsp;</span>
           <!--  <span class="stTypeTip">请准备店铺房产证明照片，在下步上传。</span> -->
          </dd>
    
               <dl class="proOwnerRelation">
                    <dt>以下资料任选一项，在下一步上传：1.房产证2.土地证3.村委会证明（必须包含店铺地址、产权人、村委会联系人及联系电话）</dt>
                </dl>
         <%--  <dd>
            <span class="slct slct-arr">
              <a></a>
              <input type="text" name="storePropertyType" id="storePropertyType" relcode="${applyDetailDto.storePropertyType }"  disabled="disabled" placeholder="-请选择-" />
              <ul>
                <li>自有</li>
                <li>租赁</li>
              </ul>
            </span>
            <div class="storeMouthRent_con">
              <span>&nbsp;房屋租赁租金&nbsp;</span>
              <span>
                <input type="text" class="tal" name="storeMouthRent" id="storeMouthRent" value="${applyDetailDto.storeMouthRent }" placeholder="请填写租金">
              </span>
            </div>
            <span class="tip" id="storeMouthRentTip">&nbsp;</span>
          </dd> --%>
        </dl>
         <dl>
          <dt>
            <span>*</span>店铺地址</dt>
          <dd>
            <span>
              <select name="storeProvince" id="storeProvince" relcode="${applyDetailDto.storeProvince }"></select>省
              <select id="storeCity" name="storeCity"  relcode="${applyDetailDto.storeCity }"></select>市
              <select id="storeArea" name="storeArea"  relcode="${applyDetailDto.storeArea }"></select>区
            </span>
            <span class="tip" id="storeProvinceTip">&nbsp;</span>
          </dd>
        </dl>
            <dl style="height: 108px;">
          <dt></dt>
          <dd>
            <textarea name="storeAddress" id="storeAddress"  placeholder="请填写详细地址!">${applyDetailDto.storeAddress }</textarea>
            <span class="tip" id="storeAddressTip">&nbsp;</span>
          </dd>
        </dl>
        <dl>
          <dt>固定电话</dt>
          <dd>
         
            <input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="telAera" id="telAera" value="${applyDetailDto.telAera }" placeholder="区号" style="width: 40px;" maxlength="4" /> - 
            <input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="telNo" id="telNo" value="${applyDetailDto.telNo }" placeholder="请填写掌柜的固定电话" />
            <span class="err" id="telNoTip"></span>
   
          </dd>
        </dl>
        <dl>
          <dt>QQ号码</dt>
          <dd><input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="20" name="qq" id="qq" value="${applyDetailDto.qq }" placeholder="请填写QQ号码">
            <span class="err" id="qqTip"></span>
   
          </dd>
        </dl>
     <%--     <dl>
          <dt>推荐人姓名</dt>
          <dd><input type="text" maxlength="20" name="referenceName" id="referenceName" value="${applyDetailDto.referenceName }" placeholder="请填写推荐人姓名">
            <span class="err" id="referenceNameTip"></span>
   
          </dd>
        </dl>
         <dl>
          <dt>推荐人手机号码</dt>
          <dd><input type="text" maxlength="20" name="referenceMobile" id="referenceMobile" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="${applyDetailDto.referenceMobile }" placeholder="请填写推荐人手机号码">
            <span class="err" id="referenceMobileTip"></span>
   
          </dd>
        </dl> --%>
      </div>
    </div>
    	
      <div class="btn_box">
        <a class="next_off" id="btn_next">下一步</a>
        <span>(提交个人信息，即可进入下一步)</span>
      </div>
    
    
  </div>
  	<script src="<%=Constants.get("UHJ_CSS_SERVER")%>/j/lib/jquery.js"></script>
	<script src="<%=Constants.get("UHJ_CSS_SERVER")%>/j/jend/jend.js"></script>
	<!-- /公共JS引用 -->
	<script src="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2016/0111/j/formValidator.js"></script>
	<script src="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2016/0111/j/form.js"></script>

  <!-- <script src="https://secure.beta.ule.com/j/lib/jquery.js"></script>
  <script src="https://secure.beta.ule.com/j/jend/jend.js"></script>
  
  <script src="http://i1.beta.ule.com/c/my/loan/2016/0111/j/formValidator.js"></script>
  <script src="http://i1.beta.ule.com/c/my/loan/2016/0111/j/form.js"></script>  -->
</body>
</html>