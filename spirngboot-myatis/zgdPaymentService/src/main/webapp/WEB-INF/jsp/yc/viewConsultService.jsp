<%@ page contentType="text/html;charset=utf-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.ule.uhj.util.Constants"%>

<!DOCTYPE html>
<html>
<head>
<meta charset=utf-8 />
<title>掌柜贷咨询服务协议</title>
<link href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2016/0111/c/css.css" rel="stylesheet">
</head>

<body>
<div class="hetong_wrap">
    <h2>掌柜贷咨询服务协议</h2>
    <p><strong>甲方（出借人）： 中国邮政储蓄银行股份有限公司</strong></p>
    <p>地址： </p>
   	<p><strong>乙方（借款人）：${js.userName}</strong></p>
    <p><strong>身份证号码：${js.certNo}</strong></p>
    <p>电    话：${js.phone}</p>
    <p>邮掌柜ID：${js.orgCode}</p>
    <p>开 户 名：${js.userName} </p>
    <p>银行账号：${js.cardNo}</p>
    <p>开 户 行： 中国邮政储蓄银行</p>
    <p>丙方（居间人）:上海邮乐网络技术有限公司</p>
    <p>地址：上海市浦东新区德平路289号5-14楼</p>
    <p>鉴于：</p>
    <p>1、	甲方是一家拥有银行牌照的金融机构，提供存款贷款等金融服务; </p>
    <p>2、	乙方是邮掌柜注册用户，已经成为丙方的线下加盟商，利用邮掌柜平台进行销售经营，有资金借款需求；</p>
    <p>3、	丙方是邮掌柜平台合法的所有者及经营者；</p>
    <p>4、	甲乙丙三方同意，乙方通过邮掌柜平台向甲方借款，甲方通过邮掌柜平台向乙方放贷，丙方通过邮掌柜平台向甲方和乙方提供居间咨询服务。</p>
    <p>依据《中华人民共和国合同法》及其他相关法律法规，甲、乙，丙三方经协商一致，达成如下协议：</p>
    <p>第一条 服务方式</p>
    <p>1、	丙方推荐甲方作为邮掌柜平台的贷款机构，为甲方和乙方进行撮合、对接，促成甲方和乙方达成借款交易，并为甲方和乙方提供与借款事项相关的资信审核、还款保证及贷后辅助管理等服务。</p>
    <p>2、	甲方和乙方达成借款意向后，丙方督促和协助双方另行签署正式的借款协议，并监督和管理借款协议的执行。</p>
    <p>第二条  甲方的权利与义务</p>
    <p>1、	对于丙方推荐的客户，经甲方审核不符合规定的，可以不予发放贷款；</p>
    <p>2、	甲方与乙方根据甲乙双方另行签署的借款协议约定权利和义务；</p>
    <p>3、	甲方与丙方根据甲丙双方另行签署的业务合作协议约定权利和义务；</p>
    <p>第三条  乙方的权利和义务</p>
    <p>1、	乙方应保证借款用途仅限于用于与丙方合作的业务的经营（包括邮掌柜店铺日常经营，邮掌柜系统进货等）以及在丙方系统各平台进行的消费。</p>
    <p>2、	乙方应自行负担因借款所产生的风险。</p>
    <p>3、	乙方同意本协议抬头中登记的银行账户为乙方向甲方借款的唯一收还款账户（以下简称"借款账户"）。</p>
    <p>4、	乙方应按借款协议约定的日期和金额将需要偿还的借款本息汇入借款账户。如乙方未按借款协议的约定及时偿还借款本息，并因此给丙方造成损失，应赔偿丙方所受一切损失（包括但不限于丙方为向乙方催收所发生的一切费用）。</p>
    <p>5、	乙方应按本协议的约定向丙方支付服务费。</p>
    <p>6、	乙方应对在本协议签署及履行过程中所获知的甲方的保密信息予以保密。</p>
    <p>7、	乙方保证其披露给丙方的任何信息真实、准确且没有重大遗漏，并授权丙方就本协议项下借款之目的向甲方披露该信息。</p>
    <p>第四条  丙方的权利和义务</p>
    <p>1、	就本协议约定的借款居间服务，丙方有权向乙方收取服务费。</p>
    <p>2、	丙方应勤勉尽职地为甲方和乙方提供借款咨询居间服务，包括但不限于收集整理乙方的相关资质、材料，对乙方进行资信审核。乙方向丙方披露的与借款相关的信息及材料均需真实、合法、有效，不得虚构或有重大遗漏。</p>
    <p>3、	丙方仅就甲方与乙方之间的借款提供居间咨询服务，并不就甲方与乙方借款关系的达成提供任何保证，如因乙方未能通过甲方审核而无法获得甲方贷款的，丙方不承担任何责任。</p>
    <p>4、	乙方逾期偿还借款本息时，丙方有权对乙方进行还款提醒和催收工作。</p>
    <p>5、	除各方另有约定或法律另有规定外，丙方应对本协议签署及履行过程中所获知的乙方的保密信息承担保密义务。</p>
    <div class="clred">
        <p>第五条 信息收集、使用、共享与保护</p>
        <p>1、	信息的收集</p>
        <p>（1）	乙方了解并同意当其访问邮掌柜平台向甲方借款时，须向丙方主动提供一些信息，乙方同意并授权丙方为本协议项下借款之目的通过以下途径获取乙方的信息：</p>
        <p>a.	收集乙方留存在丙方及其关联公司经营的所有业务平台处与借款相关的信息；</p>
        <p>b.	收集乙方留存在丙方合作伙伴处与借款相关的信息；</p>
        <p>c.	丙方自己或委托第三方向中国人民银行金融信用信息基础数据库以及其他依法设立的征信机构、资信评估机构或有关法律、监管机构许可的类似机构（以下统称"征信机构"）查询、核实、打印、留存乙方的信息和信用报告；</p>
        <p>d.	丙方自己或委托第三方向行政机关、司法机关查询、打印、留存与借款相关的乙方的信息；</p>
        <p>e.	向合法留存乙方信息的自然人、法人以及其他组织收集与借款相关的乙方的信息。</p>
        <p>（2）	乙方同意并授权丙方收集的信息包括但不限于：</p>
        <p>a.	身份信息，包括但不限于姓名/名称、证件号码、证件类型、住所地、电话号码以及其他身份信息；</p>
        <p>b.	乙方在申请、使用丙方提供的服务时所提供以及形成的任何数据和信息；</p>
        <p>c.	乙方在丙方及其关联公司和/或合作伙伴处中留存以及形成的用以评估其借款额度和还款能力等的任何数据和信息；</p>
        <p>d.	信用信息，包括但不限于乙方的征信记录和信用报告；</p>
        <p>e.	财产信息，包括但不限于乙方的个人经营状况、财税信息、房产信息、车辆信息、基金、保险、股票、信托、债券、互联网金融等投资理财信息和负债信息等；乙方同意并确认，乙方在将前述信息提供给丙方之前已清楚知晓可能带来的不利后果，包括但不限于基于前述信息对乙方做出的负面评价。</p>
        <p>f.	乙方在行政机关、司法机关留存的任何信息，包括但不限于户籍信息/工商信息、诉讼信息、执行信息和违法犯罪信息等；</p>
        <p>g.	与乙方申请或使用的借款服务相关的、乙方留存在其他自然人、法人和组织的其他相关信息。</p>
        <p>2、	为了更好地为乙方提供服务，也为了丙方自身的信贷风险防控，乙方同意并授权丙方将其信息用于如下用途：</p>
        <p>（1）	创建数据分析模型，为乙方提供适合的金融技术等服务，并维护、改进这些服务；</p>
        <p>（2）	比较信息的准确性并与第三方进行验证；</p>
        <p>（3）	合理评估乙方的财务状况，防控风险；</p>
        <p>（4）	为使乙方知晓丙方的服务情况或了解丙方的服务，通过电子邮件、邮掌柜平台、手机短信和传真等方式向乙方发送服务状态的通知、营销活动及其他商业性电子信息；</p>
        <p>（5）	因乙方与丙方的纠纷未能够协商解决而需要通过借助催收及法律途径解决的，丙方会将乙方信息提供给催收公司、律师事务所、法院、仲裁委员会和其他有权机关；</p>
        <p>（6）	预防或阻止非法的活动；</p>
        <p>（7）	经乙方许可的其他用途。</p>
        <p>3、	丙方对乙方的信息承担保密义务，不会为满足第三方的营销目的而向其出售或出租乙方的任何信息，丙方会在下列任一情况下才将乙方的信息与第三方共享：</p>
        <p>（1）	获得乙方的同意或授权；</p>
        <p>（2）	为建立信用体系，乙方同意并授权丙方及其关联公司向征信机构发送乙方的信息，并一经通知乙方即将乙方的不良信息发送征信机构，包括但不限于中国人民银行征信中心；</p>
        <p>（3）	根据法律法规的规定及有权机关的要求；</p>
        <p>4、	丙方将根据法律法规规定对乙方的信息予以保密，但下列情形除外：</p>
        <p>（1）法律、法规、规章及其他规范性文件另有规定；</p>
        <p>（2）本协议另有约定。</p>
        
    </div>
    <p>第六条 费用支付</p>
    <p>1、	收费标准：</p>
    <p>在甲方向乙方成功放款后，乙方向丙方支付每笔借款金额的 %作为服务费，此费用随每笔借款一次性收取；</p>
    <p>2、	收费方式：</p>
    <p>（1）	乙方授权丙方从乙方借款账户中直接扣减上述应向丙方支付的服务费。</p>
    <p>（2）	如乙方申请提前还款或发生其他导致借款协议提前解除的情形，前述服务费不予退还。</p>
    <p>第七条  债权转让</p>
    <p>1、	乙方贷款逾期第10天甲方会将享有的该笔贷款债权转让给丙方，丙方拥有对该笔贷款的完全债权，在丙方拥有债权期间继续按照甲方乙方所签署之借款协议中约定的本息及罚息计算方式计收利息和罚息，直至丙方完全收回丙方替乙方向甲方垫付的本息及罚息以及乙方所欠丙方利息和罚息；</p>
    <p>2、	该笔转让一经甲方或丙方发送给乙方的债权转让短信通知或者在邮掌柜系统中显示的债权转让标记或通知即视为甲方和丙方已将债权转让事宜通知乙方，乙方不以任何理由对此提出异议或抗辩。</p>
    <p>第八条  违约责任</p>
    <p>1、	任何一方违反本协议的约定，违约方应赔偿守约方因此产生的全部损失。</p>
    <p>2、	乙方违反与甲方的借款协议的约定，给丙方造成损失的，应当按照本协议第3.4条承担违约责任。</p>
    <p>第九条 争议解决方式</p>
    <p>1、	本协议的签署、履行、终止和解释均适用中华人民共和国法律；</p>
    <p>2、	甲、乙、丙三方在本协议履行过程中如有争议，应协商解决。协商不成，向丙方所在地人民法院提起诉讼。</p>
    <p>第十条  有效期及其他</p>
    <p>1、	本协议自三方签字盖章之日起生效，</p>
    <p>（1）	甲方和乙方成功签署借款协议的， 本协议自乙方向甲方或丙方归还所欠全部本金，利息、服务费等全部费用之日终止；</p>
    <p>（2）	甲方和乙方未成功签署借款协议的，本协议自任何一方向另外两方发出解除通知之日起即终止。</p>
    <p>2、	本协议一式三份，具备同等效力。</p>
    <p>[以下无正文]</p>
    <p>甲方（盖章）：</p>
    <p>法定代表人/授权代表（签字）：${js.bankName}</p>
    <p>日期：${js.date}</p>
    <p>乙方（盖章）：</p>
    <p>法定代表人/授权代表（签字）：${js.userName}</p>
    <p>日期：${js.date}</p>
    <p>丙方（签字）：${js.uleName}</p>
    <p>日期：${js.date}</p>
</div>
</body>
</html>
