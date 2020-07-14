<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ule.uhj.util.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset=utf-8 />
<title>附件2：个人信息查询及留存授权书</title>
<link href="<%=Constants.get("UHJ_CSS_SERVER")%>/c/my/loan/2016/0111/c/css.css" rel="stylesheet">
</head>

<body style = "font-family: SimSun;" >
<div class="hetong_wrap">
	<h2>个人信息查询及留存授权书</h2>
    <p><strong>重要提示：</strong></p>
    <p><strong>尊敬的客户：为了您的权益，请在签署本授权书前，仔细阅读本授权书各条款（特别是黑体字条款），关注您在授权书中的权利、义务。如有任何疑问，请向经办行咨询。</strong></p>
    <p>一、本人同意并不可撤销地授权：<strong>中国邮政储蓄银行（包括各分支机构）按照国家相关规定采集并向金融信用信息基础数据库和其他依法设立的征信机构提供符合相关规定的本人个人信息和包括信贷信息在内的信用信息（包括本人在中国邮政储蓄银行办理业务时产生的不良信息）。</strong></p>
    <p>二、本人同意并不可撤销地授权：<strong>中国邮政储蓄银行（包括各分支机构）在办理<span>掌柜贷</span>业务申请及业务存续期间可以根据国家有关规定，通过金融信用信息基础数据库和其他依法设立的征信机构查询、打印、保存符合相关规定的本人个人信息和包括信贷信息在内的信用信息。用途如下：□审核本人贷款申请；□审核本人贷记卡、准贷记卡申请；□审核本人作为担保人；□对已发放的个人信贷进行贷后管理；□受理法人、其他组织的贷款申请或其作为担保人，需要查询其法定代表人、出资人及关联人信用状况；□对公业务贷后管理需查询担保人、法定代表人、出资人及关联人信用状况；□审核特约商户开户申请;□处理本人异议；□经人民银行同意的其他查询事项。具体查询事项为：</strong></p>
    <p>若信贷业务未获批准，同意贵行继续保留此查询授权书和身份证件复印件。</p>
    <p><strong>授权人声明：贵行已依法向本人提示了相关条款（特别是黑体字条款），应本人要求对相关条款的概念、内容及法律效果做了说明，本人已经知悉并理解上述条款。</strong></p>
    <div class="clearfix">
        <div class="flr">
            <p>授权人（签名）：${dataMap["userName"] }</p>
            <p>身份证件类型以及号码：身份证${dataMap["certNo"] } </p>
            <p>授权日期：   ${dataMap["year"] } 年 ${dataMap["mouth"] }  月  ${dataMap["date"] } 日</p>
        </div>
    </div>
	<h3>（为保护您的合法权益，请您完整填写空白项内容，并正确选择查询授权原因。）</h3>
</div>
</body>
</html>
