<%@ page contentType="text/html;charset=utf-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=utf-8 />
<title>中国邮政储蓄银行小额贷款额度借款合同</title>
</head>

<body>
<div class="hetong_wrap">
	<p>时间戳：${nowTime}</p>
    <h1>小额贷款额度借款合同</h1>
    <p>编号：<span>${contractInfo.lineContractNo}</span></p>
    <p>贷款人（甲方）：中国邮政储蓄银行股份有限公司</p>
    <p>通讯地址：<span>${Address}</span></p>
    <p>邮政编码：<span>${ZipCode}</span></p>
    <p>电话：<span>${mobileNo}</span> 传真：<span>${customerFax}</span> </p>
    <p>借款人（乙方）：<span>${customerName}</span></p>
    <p>通讯地址：<span>${loanerAddress}</span></p>
    <p>邮政编码：<span>${loanZipCode}</span></p>
    <p>电话：<span>${contractInfo.phone}</span> 传真：<span>${loanCustomerFax}</span></p>
    <p><strong>特别提示：本合同系甲方、乙方在平等、自愿的基础上依法协商订立，所有合同条款均是双方意思的真实表示。</strong></p>
    <h3>第一章　授信额度及类别</h3>
    <p><strong>第一条</strong><strong>授信额度</strong></p>
    <p>本合同所称授信额度（以下简称"额度"），系指甲方根据对乙方的信用评价、财务状况及乙方提供的担保等因素综合确定的，乙方在一定条件下，可以申请支用的借款本金的限额。对于可循环使用的额度，在额度支用期内，只要乙方未偿还的本合同项下的借款本金余额不超过授信额度金额，乙方可以连续申请借款，不论借款的次数和每次的金额，但在任何时点乙方所申请的借款本金金额与乙方未偿还的本合同项下的借款本金余额之和不得超过授信额度金额。对于不可循环使用的额度，在额度支用期间内，乙方可以多次申请借款，但乙方可支用的借款本金金额与乙方累计已经支用的借款本金金额之和不得超过授信额度金额。</p>
	<p><strong>本合同所称的额度借款，系指借款人按本合同约定使用额度所发生的借款。</strong></p>
    <p>本合同项下授信额度金额为：人民币（大写）<span>${contractInfo.capitalAmount}</span>，（小写）<span>${contractInfo.creditLimit}</span> 元（大小写不一致时，以大写为准，下同）。在额度支用期内，乙方（可以/不可以）循环使用上述额度。额度支用期内未使用的授信额度在额度支用期届满后自动失效。</p>
    <p><strong>第二条</strong>甲方有权对本合同项下额度使用情况进行不定期审查，如出现本合同述明的情形，甲方有权单方调整授信额度。</p>
    <h3>第二章　额度期限</h3>
    <p><strong>第三条</strong>额度存续期最长为<span>2</span>年，自<span>${contractInfo.openYear}</span>年<span>${contractInfo.openMonth}</span>月<span>${contractInfo.openDay}</span>日至<span>${contractInfo.expireYear}</span>年<span>${contractInfo.expireMonth}</span>月<span>${contractInfo.expireDay}</span>日。额度存续期内的前<span>2</span>年为额度支用期，借款人可以申请支用借款，额度内单笔支用借款最长期限为<span>3个月</span>。额度项下借款的到期日不得超过额度存续期到期日。</p>
    <p><strong>第四条</strong>乙方每次提款的借款期限自甲方将贷款发放至乙方放款账户起至约定还款日止，以借据记载为准。</p>
    <h3>第三章　额度的使用</h3>
    <p><strong>第五条</strong>额度的使用与可用额度</p>
    <p>（一）在额度支用期和授信额度金额以内，乙方可以根据需要逐笔申请借款，双方应办理相应的手续。本合同约定的担保生效并产生对抗第三人效力之前，甲方有权拒绝乙方的借款申请。本合同项下所有贷款仅限于乙方使用。</p>
    <p>（二）可用额度是指乙方在每次支用时，根据本合同项下已经发生的支用情况计算得出的，本次可以支用的最高金额。可用额度可以通过乙方本人账户支用。</p>
    <p>在额度从未使用之时，可用额度等于授信额度金额。</p>
    <p>如额度已经被使用，可用额度的确定须区分额度是否可以循环：对于可循环使用的额度，可用额度等于授信额度金额减去当前合同项下借款人在甲方所有借款本金余额的差；对于不可循环使用的额度，可用额度等于授信额度金额减去合同项下借款人在甲方所有借款本金累计发放额的差。</p>
    <p><strong>第六条</strong>申请支用额度时，乙方应提交《中国邮政储蓄银行小额贷款额度借款支用单》，经甲方审核同意并经乙方确认后，甲方按约定发放贷款。借款金额、期限、用途、利率、还款方式等按本合同约定及《中国邮政储蓄银行小额贷款额度借款支用单》中约定的内容确定。</p>
    <p><strong>第七条</strong>乙方提款时，应满足下列前提条件，否则甲方有权拒绝向乙方发放任何款项：</p>
    <p>（一）	至每次提款日前，乙方在本合同所做陈述与保证真实、准确、完整，且未出现本合同约定的任何违约情形。</p>
    <p>（二）	本合同项下担保未发生不利于甲方的变化，或虽然发生此类变化，但乙方已另行提供了经甲方认可的合法有效的担保。</p>
    <p>（三）	法律法规、规章或有权部门不禁止且不限制甲方发放本合同项下的贷款。</p>
    <p>（四）	乙方已向甲方提交下列文件：</p>
    <p>1、乙方填写无误的《中国邮政储蓄银行小额贷款额度借款支用单》、按甲方要求填写的借款凭证；</p>
    <p>2、乙方还须按甲方的合理要求提交与本合同有关的其他文件。</p>
    <p>（五）	若甲方要求，还应办妥本合同的公证手续。</p>
    <p>（六）	未发生本合同所列之任一违约事件。</p>
    <p>（七）	乙方已按本合同规定开立了存款账户。</p>
    <p>（八）	未出现甲方认为影响乙方资信能力及贷款资金安全的不利情况，包括但不限于乙方在任何金融机构债务逾期或涉及重大诉讼案件等情况。</p>
    <p>（九）	法律、法规规定和本合同约定的其他提款条件已经满足。</p>
    <p>（十）	其他条件：<span>${otherConditions}</span></p>
    <p><strong>第八条</strong>贷款用途</p>
    <p>本额度借款合同项下所有贷款仅限于借款人用于<span> 掌柜贷 </span>。合同项下单笔借款具体用途由《中国邮政储蓄银行小额贷款额度借款支用单》约定。乙方不得擅自挪用贷款，甲方有权监督款项的使用。乙方提款时应承诺按约定用途用款，否则甲方有权依照本合同约定处理，并要求乙方承担相应的违约责任。</p>
    <p><strong>第九条</strong>支付方式</p>
    <p>本额度借款合同项下的借款支付采用以下两种支付方式：</p>
    <p>（一）贷款人受托支付，即甲方根据乙方的提款申请和支付委托，将贷款通过乙方账户支付给符合合同约定用途的乙方交易对象。乙方应按照甲方要求的方式和期限提交相关交易资料，并按要求配合甲方做好有关细节的认定记录,以上材料经甲方审核同意后，甲方通过乙方账户将贷款资金支付给乙方交易对象。</p>
    <p>受托支付过程中，如甲方划转贷款资金需要乙方配合，乙方应按照甲方要求履行配合义务。受托支付按甲方相关规定产生费用的，费用按照甲方相关规定由乙方或乙方交易对象承担。</p>
    <p>（二）借款人自主支付，即甲方根据乙方的提款申请将贷款资金发放至乙方账户后，由乙方自主支付给符合合同约定用途的乙方交易对象。乙方应定期汇总报告贷款资金支付情况，甲方有权通过账户分析、凭证查验或现场调查等方式核查贷款支付是否符合约定用途。</p>
    <p>乙方应在单笔支用时就具体贷款用途、金额、对象等予以说明，甲方有权决定该笔贷款的支付方式。</p>
    <p>对于金额超过50万元，且乙方交易对象具备有效使用非现金结算方式条件的，乙方须委托甲方采取贷款受托支付方式支付贷款资金；对于金额不超过50万元，或虽然金额超过50万元、但乙方交易对象不具备有效使用非现金结算方式条件的，经甲方同意后乙方可采取自主支付方式。</p>
    <p>出现以下触发事件时，甲方有权将乙方自主支付变更为贷款人受托支付，或者要求补充贷款支付条件，或者停止贷款发放和支付：</p>
    <p>1、乙方及其经营实体发生重大不利变化；</p>
    <p>2、乙方及其经营实体信用状况下降；</p>
    <p>3、乙方贷款资金使用出现异常；</p>
    <p>4、乙方及其经营实体现金流异常，可能影响贷款安全；</p>
    <p>5、甲方认定的其他严重影响甲方利益的情形。</p>
    <p><strong>第十条</strong>乙方授权甲方将贷款划入其在甲方开立的放款账户。放款账户应为乙方以本合同项下的乙方姓名、证件名称及证件号码在甲方开立并凭密码支用的个人结算账户。如乙方变更本账户，需经过甲方同意，变更后的账户应当在甲方开立。放款账户户名、账号、开户行以《中国邮政储蓄银行小额贷款额度借款支用单》的约定为准。</p>
    <p>对于单笔贷款，以实际放款日为起息日，借款期限自起息日起算，贷款到期日根据借款期限长度相应推算。</p>
    <h3>第四章  利息计算与还款约定</h3>
    <p><strong>第十一</strong>条贷款利率、罚息利率和计息</p>
    <p>（一）贷款利率</p>
    <p>甲方有权确定本合同的贷款利率，一般高于中国人民银行公布的基准贷款利率，具体利率水平以甲方公布的为准。本合同项下单笔贷款利率为年利率，可采用固定利率或者浮动利率，以《中国邮政储蓄银行小额贷款额度借款支用单》中的约定为准。</p>
    <p>如采用固定利率，则在借款期限内该利率保持不变，该贷款利率在《中国邮政储蓄银行小额贷款额度借款支用单》中约定。</p>
    <p>如采用浮动利率，则该利率在借款期限内将按照约定进行浮动。本合同项下所有贷款利率以中国人民银行公布的同期同档次贷款利率为基准利率，最低（上/下）浮<span></span>%。本合同项下单笔贷款利率在《中国邮政储蓄银行小额贷款额度借款支用单》中约定，若对本合同利率进行调整，须经甲方同意。</p>
    <p>如遇基准利率调整，贷款利率自起息日起，根据下列方式进行调整：</p>
    <p>1、贷款发放前，基准利率调整并适用于本合同项下贷款的，适用新的基准利率并按《中国邮政储蓄银行小额贷款额度借款支用单》约定利率浮动比例，重新确定贷款利率。</p>
    <p>2、贷款发放后，遇基准利率调整的，若贷款期限在一年（含）以下，执行《中国邮政储蓄银行小额贷款额度借款支用单》中约定的利率，不分段计息；若贷款期限在一年以上，自每次基准利率调整之日的次年1月1日开始，按该次调整后的基准利率及上述约定的利率浮动比例确定并执行新的利率。</p>
    <p>（二）罚息利率</p>
    <p>对于乙方未按合同约定日期（包括被宣布提前到期）偿还的贷款本金，甲方有权自逾期之日起至拖欠本息全部清偿之日止，按罚息利率和本合同约定的结息方式计收罚息；对不能按时支付的利息，按罚息利率和本合同约定的结息方式计收复利。罚息利率按在本条第（一）款约定的利率基础上加收30％确定。</p>
    <p>对于乙方未按合同约定用途使用的贷款，甲方有权自未按合同约定用途使用贷款之日起至贷款本息全部清偿之日止，按罚息利率和本合同约定的结息方式计收罚息；对不能按时支付的利息，按罚息利率和本合同约定的结息方式计收复利。罚息利率按在本条第（一）款约定的利率基础上加收100％确定。</p>
    <p>对于乙方未按合同约定使用且未按合同约定日期（包括被宣布提前到期）偿还的贷款，按上述约定的较高罚息利率执行。</p>
    <p>贷款利率按本条第（一）款约定进行调整的，利率调整后罚息利率亦相应变动，其变动周期与利率变动周期一致。</p>
    <p>本合同所称基准利率，是指起息日当日中国人民银行公布施行的同档次人民币贷款基准利率，如申请时涉及到基准利率变动，以贷款实际起息日当日的人民银行人民币贷款基准利率为准；单笔贷款利率或罚息利率依前述约定调整时，基准利率是利率调整日当日中国人民银行公布施行的同档次贷款利率；如果中国人民银行不再公布同档次贷款利率,基准利率是利率调整日当日中国邮政储蓄银行公布的同档次贷款利率。</p>
    <p>在确定"同档次贷款利率"时，应按照单笔贷款的期限长短确定所对应的贷款利率期限档次。</p>
    <p>（三）计息</p>
    <p>贷款利息自单笔贷款发放到乙方账户之日起计算。本合同项下贷款的计息方式依还款方式不同而不同，分为按期（年、季、月）计息和按日计息。按期计息是指以期次为计算利息的最小单位，其中季利率=年利率/4；月利息=年利率/12；日利率=年利率/365，乙方在一个期次的任何一非还款日提前偿还当期贷款时，均按贷款占用的实际天数计算还款日的利息，本期剩余天数的利息将与下一期利息合并计算。</p>
    <p>1.按日计息。</p>
    <p>到期还款额=贷款本金+贷款本金x贷款实际占用天数x日利率</p>
    <p>2.按期计息。</p>
    <p>（四）还款方式。</p>
    <p>甲乙双方商定，乙方可采用以下还款方式归还贷款本息。还款日可为放款日以后期的对日或指定日；采用对日还款法的，放款日在以后期没有对日的，月末日为还款日（具体还款日与还款金额以还款计划表为准，具体还款计划在《中国邮政储蓄银行小额贷款额度借款支用单》中约定）：</p>
    <p>1.一次性还本付息还款法：到期一次归还贷款本息之和。</p>
    <p>2.等额本息还款法：每<span> 期 </span>等额归还贷款本息。</p>
    <p>3.阶段性等额本息还款法：借款前<span>${stageBorrowingBeforeBorrowing }</span>个<span>${stageBorrowingOnSchedule}</span>按期偿还当期利息，不还本金。此后期间，按照等额本息还款法偿还。</p>
    <p>4.按<span>${lumpSuminterest}</span>付息，到期一次性还本。</p>
    <p>5.按<span>${planningTableInterest}</span>付息，按还本计划表还本。</p>
    <p>6.其他方式。</p>
    <p><strong>第十二条</strong>还款顺序</p>
    <p>乙方应按照下列原则偿还本合同项下借款：</p>
    <p>甲方有权将乙方的还款首先用于偿还本合同约定的应由乙方承担而由甲方垫付的各项费用以及甲方实现债权的费用，剩余款项按照先还利息后还本金的原则偿还。但是对于已经发生减值的不良贷款，偿还上述费用后应按照先还本金后还利息的原则偿还。</p>
    <p><strong>第十三条</strong>还款约定</p>
    <p>乙方应在本合同约定的还款日当天16：00之前将当期应还款项足额存入指定还款账户内，由甲方在结息日进行扣款。</p>
    <p>乙方授权甲方直接从乙方在甲方系统开立的还款账户(个人结算账户)中扣收应还款项，此授权行为不再另行签发授权书。还款账户户名、账号、开户行以甲乙双方在《中国邮政储蓄银行小额贷款额度借款支用单》中约定为准。乙方在结息日未能按时偿还应还款项的，甲方有权在结息日后直接扣划乙方应还款项。</p>
    <p>乙方提供的个人账户出现被冻结、扣划、变更、余额不足等情况而造成甲方无法全额扣收本息的，乙方应及时向甲方提供新的还款账户或及时补足账户余额，使甲方能够按时全额扣收贷款本息。如乙方在借款期内要变更还款账户，须提前10个工作日向甲方提出申请。由于上述情况造成甲方不能按时全额收回应收本息的，乙方应承担相应违约责任。</p>
    <p><strong>第十四条</strong>提前还款</p>
    <p>乙方提前偿还全部或部分贷款的，应向甲方提交书面申请。</p>
    <p>乙方提前还款的，最低部分提前还款本金为<span>${earlyRepaymentOfPrincipal } </span>元，部分提前还款金额以100元为单位，并应按照下列第<span>${penaltyTerm }</span>项约定向甲方支付提前还款违约金：</p>
    <p>A、无须支付提前还款违约金。</p>
    <p>B、应按提前偿还贷款本金的<span>${penaltyPercentage }</span>%支付违约金。</p>
    <p>在提前还款时，乙方应首先支付到期应付未付的任何款项，包括但不限于贷款本金、利息、违约金、补偿金和其他费用。对于分期还款的贷款，部分提前还款应先归还提前还款日所在期间贷款实际占用天数内的利息，及当期的全部本金，剩余资金用于提前归还贷款剩余本金直至提前结清贷款。</p>
    <p>提前还款后，甲方按照乙方剩余贷款本金、期限和当前贷款执行利率重新试算还款计划，但对借款期限不作调整。</p>
    <p><strong>第十五条</strong>期限调整</p>
    <p>贷款期限调整分为展期和缩期。对于本合同项下单笔借款，乙方不能按照本合同及相关协议、文件约定的还款计划偿还贷款的，可以向甲方申请延长贷款期限，即展期。乙方的展期申请应该在贷款还款日前10个工作日提出申请。经甲方批准后，双方办理相关手续。</p>
    <p>本合同项下每笔借款只能展期一次，贷款期限在1年（含）以内的，贷款展期期限不得超过原贷款期限，贷款期限在1-5年（含）的，贷款展期期限累计不得超过原贷款期限的一半，且展期期限最长不得超过1年。若单笔支用借款采用的是浮动利率，且原借款期限加上展期期限达到新的利率期限档次时，从展期之日起，贷款利息根据新的利率期限档次计收。已计收的利息不再调整。</p>
    <p>乙方申请缩短贷款期限的，应在贷款还款日前10个工作日向甲方提出贷款缩期的书面申请，经甲方审查同意后，双方办理相关手续。若单笔支用借款采用的是浮动利率，且缩期后借款期限达到新的利率期限档次时，贷款利息仍应根据原利率期限档次计收，已计收的利息不进行调整。还款计划应按照原利率期限档次、期限调整日人民银行公布的同档次基准利率和剩余贷款本金重新计算。</p>
    <p>乙方申请调整贷款期限的，应首先归还拖欠的贷款本息（如有）和期限调整日所属期间一整期的贷款本金和利息。</p>
    <h3>第五章　担保</h3>
    <p>第十六条 	本合同项下的全部债务采取以下第<span>${typeOfSecurity }</span>种担保方式： </p>
    <p>1.由<span>${js.jointLiability }</span>提供连带责任保证，并另行签订《中国邮政储蓄银行小额贷款保证合同》（编号：<span>${js.jointLiabilityNum }</span>）；</p>
    <p>2.由<span>${js.mortgageGuarantee }</span>提供抵押担保，并另行签订《中国邮政储蓄银行小额贷款抵押合同》（编号:<span>${js.mortgageGuaranteeNum }</span>）；</p>
    <p>3.由<span>${js.pledgeGuarantee }</span>提供质押担保，并另行签订《中国邮政储蓄银行小额贷款质押合同》（编号: <span>${js.pledgeGuaranteeNum }</span>）；</p>
    <p>4.由<span>${js.marginGuarantee }</span>提供保证金担保，具体担保条款见《<span>${js.marginGuaranteeClause }</span>》（编号：<span>${js.marginGuaranteeNum }</span>）；</p>
    <p>5.信用担保；</p>
    <p>6.其他担保方式：由<span>${js.otherSecurityMethodsFrom }</span>提供<span>${js.otherSecurityMethodsProvide }</span>担保，并另行签订《<span>${js.otherSecurityMethodsSign }</span>》（编号：<span>${js.otherSecurityMethodsNum }</span>）。</p>
    <p><strong>第十七条</strong>本合同项下的担保如发生了不利于甲方债权的事项，经甲方通知，乙方应按甲方要求补充或另行提供令甲方满意的担保。</p>
    <h3>第六章　授信额度的管理</h3>
    <p><strong>第十八条</strong>	授信额度金额的调整</p>
    <p>在授信额度有效期内，若甲方发现乙方的还款能力变化、抵押物价值变动等情况，甲方有权调整对乙方的授信额度金额。</p>
    <p><strong>第十九条</strong>授信额度的冻结与解冻结。</p>
    <p>在额度支用期内，乙方发生下列情形之一者，甲方有权暂停循环授信额度的使用，并有权随时冻结相应的额度：</p>
    <p>1、乙方不按合同约定按时偿还甲方借款本息；</p>
    <p>2、乙方在各商业银行办理的信用卡出现了透支并恶意拖欠；</p>
    <p>3、通过人民银行征信系统查出借款人在其他金融机构办理的贷款出现三期或30天逾期且未归还，或信用卡逾期三期以上（含）且未归还；</p>
    <p>4、违反国家有关法律法规使用贷款或不按合同约定使用贷款；</p>
    <p>5、拒绝或不配合甲方对其经营情况或信用情况进行检查；</p>
    <p>6、担保物出现损毁、权属争议等情况，影响甲方信贷资产安全；</p>
    <p>7、乙方或经营的经营实体卷入或即将卷入重大的诉讼或其他法律纠纷，足以影响其偿债能力；</p>
    <p>8、甲方认定的其他情况。</p>
    <p>当乙方同时具备下列条件时，甲方可以对已冻结的循环授信额度解除冻结：</p>
    <p>1、担保物得到修复、恢复其原值或权属争议已经解决，甲方的担保物权益可以得到有效保障；</p>
    <p>2、乙方及其乙方经营实体的法律纠纷已经解除，还款能力及意愿得以恢复；</p>
    <p>3、甲方认定的其他情况。</p>
    <p><strong>第二十条</strong>额度的终止。</p>
    <p>额度有效期满，额度自行终止。若在额度有效期内，乙方发生下列情况之一，甲方有权终止对乙方的授信额度；对已经发放的额度项下的贷款，甲方有权提前收回贷款：</p>
    <p>1、额度内任意一笔借款的最长逾期超过30天（含）或累计逾期次数超过3次；</p>
    <p>2、人行征信报告显示乙方存在重大不良信用记录，被甲方认定为禁入类客户，且未能提供符合甲方要求的相关证明材料；</p>
    <p>3、乙方不配合甲方对其经营或信用状况进行检查，并经多次劝说无效的情形；</p>
    <p>4、乙方有隐匿、转移财产逃避债务的行为；</p>
    <p>5、乙方申请贷款时存在重大隐瞒或欺骗，通过提供虚假信息来获取贷款的情形；</p>
    <p>6、担保物损毁且借款人不能提供价值相当的抵押物，或担保物权属发生对甲方的重大不利变化，且未能在甲方要求的期限内得到改善；</p>
    <p>7、额度冻结后，乙方的违规行为得不到纠正；</p>
    <p>8、乙方额度生效后两年未支用贷款额度；</p>
    <p>9、甲方认为应当终止额度的其他情形。</p>
    <h3>第七章　乙方的权利与义务</h3>
    <p><strong>第二十一条</strong>乙方对于借款资金的使用，应符合法律法规的规定及合同或协议的约定。</p>
    <p><strong>第二十二条</strong>乙方有权要求甲方对乙方提供的有关个人还款能力和信誉的资料予以保密，但双方另有约定或法律、行政法规另有规定的除外。</p>
    <p><strong>第二十三条</strong>乙方应按甲方要求提供有关个人身份、还款能力、个人信用、家庭财务状况和企业经营情况的资料，并保证资料的真实性、完整性和有效性。</p>
    <p><strong>第二十四条</strong>在贷款全部清偿前，乙方个人及家庭经济状况发生变化，可能影响其债务清偿能力的，应当在10个工作日内通知甲方。</p>
    <p><strong>第二十五条</strong>乙方不得将借款用于非生产经营的投资与消费用途，不得用于有价证券、期货买卖、股本权益性投资及房地产开发项目。</p>
    <p><strong>第二十六条</strong>乙方采用自主支付方式的，应于贷款发放后一个月内向甲方提交资金使用相关交易资料和凭证（如购货合同、发货单据等），如乙方确实无法提供全部相关交易资料和凭证的，应如实填写《贷款支用报告书》，并最迟于贷款发放后一个月内提交给甲方。</p>
    <p>贷款支付过程中，乙方出现信用状况下降、贷款资金使用异常等情形的，乙方应及时与甲方进行协商，甲方有权采取变更贷款支付方式、停止贷款发放及支付等措施。</p>
    <p><strong>第二十七条</strong>在额度借款存续期内，未经甲方书面同意，乙方不得为他人债务提供担保，不得对外拆借资金。</p>
    <p><strong>第二十八条</strong>乙方应按照本合同、额度借款支用单、借据以及相关协议、文件的约定，按期归还相关合同及支用单项下的贷款本金及利息。乙方未如期归还本息的，应承担因此而产生的罚息、违约金、赔偿金、实现债权的费用（包括但不限于诉讼费用、律师费用、公证费用、执行费用等）、因乙方违约而给甲方造成的损失和其他所有应付费用。</p>
    <p><strong>第二十九条</strong>乙方确认，甲方依照本合同以及相关协议、文件约定扣款时，视为已征得乙方事先同意。即使乙方已在此做出不可撤销的授权，但如甲方有要求，乙方仍需协助办理扣款的一切相关手续。</p>
    <p><strong>第三十条</strong>乙方转让其经营性资产的行为，应事先书面报甲方同意。</p>
    <p><strong>第三十一条</strong>乙方不得签署任何可能损害甲方利益的协议或文件，或从事任何足以损害甲方利益的活动。</p>
    <p><strong>第三十二条</strong>一旦发生或将要发生任何足以对担保人的财务状况或其履行担保义务的能力产生重大不利影响的事件，乙方应及时补充或另行提供经甲方认可的新的担保。</p>
    <p><strong>第三十三条</strong>本合同项下抵质押物的价值减少的，足以影响贷款安全的，乙方应在甲方要求的限期内补足担保，并由担保人与甲方依法签订有效担保合同。</p>
    <h3>第八章　甲方的权利与义务</h3>
    <p><strong>第三十四条</strong>如若乙方申请使用额度符合本合同的各项约定，甲方应按本合同约定及时履行合同义务。</p>
    <p><strong>第三十五条</strong>对乙方在本合同及相关协议、文件项下的到期应付未付款,乙方在此不可撤销地授权：甲方可按本合同的约定从乙方在中国邮政储蓄银行及其分支机构开立的任何账户中直接扣收应由其偿付的贷款本金、利息、复利、罚息、保险费、违约金、赔偿金等款项而无需事先获得乙方的同意；需要办理结售汇或外汇买卖手续的，乙方有义务协助甲方办理，汇率风险由乙方承担。此外，甲方有权对上述账户进行查询，有权对乙方的资金使用情况随时进行检查。</p>
    <p>即使乙方已在此做出不可撤销的授权，但如甲方有要求，乙方仍需协助办理划款的一切相关手续。</p>
    <p><strong>第三十六条</strong>对于乙方逃避甲方监督、拖欠借款本金及利息的行为或其他违约行为，甲方有权实施救济措施，有权向有关部门或单位予以通报，有权通过新闻媒体实现公告催收、进行披露和通报。</p>
    <p><strong>第三十七条</strong><strong>乙方不可撤销地授权甲方将本人信息和本合同相关信息提供给金融信用信息基础数据库和其他依法设立的信用数据库，供具有适当资格的机构或个人查询和使用。乙方事先已明确被告知并同意贷款人将本合同项下违约信息提供给金融信用信息基础数据库和其他依法设立的信用数据库。甲方有权为本合同订立和履行之目的，通过金融信用信息基础数据库和其他依法设立的信用数据库或有关单位、部门及个人查询乙方的相关信息。</strong></p>
    <p><strong>第三十八条</strong>对乙方提供的有关债务、财务、生产经营等方面的资料及情况，甲方有义务采取适当的保密措施，且保密义务不随合同的终止而终止，但本合同另有约定和法律法规另有规定的除外。</p>
    <p><strong>第三十九条</strong>甲方有权了解、核实有关乙方身份、还款能力、个人信用、家庭财务状况和经营实体经营情况，有权要求乙方提供相关文件资料。</p>
    <p><strong>第四十条</strong>甲方可以随时以各种方式检查、监督贷款的使用情况、贷款合同的履行情况。若发现乙方未按合同约定用途使用贷款，甲方有权要求乙方提前结清贷款，并有权提前终止授信额度。</p>
    <p><strong>第四十一条</strong>甲方在本合同贷款发放期内有权对乙方信用和借款质量进行复审，以决定是否向借款人继续发放贷款；对符合条件的予以贷款，对不符合条件的撤销贷款。</p>
    <p><strong>第四十二条</strong>甲方在贷款发放和支付阶段如发现乙方信用下降或贷款资金使用出现异常等风险因素，可停止贷款发放和支付。</p>
    <h3>第九章　违约及违约救济措施</h3>
    <p><strong>第四十三条</strong>发生以下情形之一的，乙方即构成违约：</p>
    <p>（一）	乙方未按期支付与甲方有关的到期未清偿债务，包括但不限于本合同以及相关协议、文件、支用单约定的本金、利息及其他费用；</p>
    <p>（二）	乙方未履行对甲方负有的其它到期债务，或甲方发现乙方有其他拖欠债务的行为的；</p>
    <p>（三）	乙方未按照本合同或与甲方签订的其他有关协议或文件规定的用途使用贷款资金；</p>
    <p>（四）	本合同以及相关协议、文件要求为相关债务提供担保，乙方或第三方未按照担保合同约定提供担保或未提供适当担保；</p>
    <p>（五）	本合同项下的担保发生了不利于甲方债权的变化，且乙方未能按甲方要求另行提供适当担保；</p>
    <p>（六）	乙方未充分履行本合同或与甲方签订的其他有关合同、协议或支用单项下的任何义务或未完全遵守其中的任一规定，且在接到甲方书面通知后未采取令甲方满意的补救措施；</p>
    <p>（七）	乙方向甲方提供虚假的或不完整的信息或资料的；</p>
    <p>（八）	乙方拒绝或阻碍甲方对其收入或信用情况进行检查的；</p>
    <p>（九）	乙方死亡而其财产合法继承人不继续履行本合同的；</p>
    <p>（十）	乙方被宣告失踪，而其财产代管人不继续履行本合同的；</p>
    <p>（十一）	乙方丧失民事行为能力，而其监护人不继续履行本合同的；</p>
    <p>（十二）	乙方或其财产合法继承人卷入或即将卷入重大的诉讼或仲裁程序及其他法律纠纷，足以影响其偿债能力；</p>
    <p>（十三）	乙方转移资产，以逃避债务的；</p>
    <p>（十四）	乙方的资信情况或还贷能力出现其他重大变化（包括但不限于工作调整、收入降低、失业、重大疾病、拖欠其他债务等），足以影响偿债能力，已经不再符合甲方贷款条件且未追加甲方认可的担保的；</p>
    <p>（十五）	发生甲方认为足以影响债权实现的其他情形的；</p>
    <p>（十六）	违反本合同约定的其他情形的。</p>
    <p><strong>第四十四条</strong>保证期间内，保证人发生下列情况之一，乙方未提供符合甲方要求的新的担保的，构成乙方违约：</p>
    <p>（一）	保证人失去担保能力的；</p>
    <p>（二）	作为保证人的法人发生承包、发包、租赁、合并和兼并、合资、分立、联营、股份制改造、破产等行为，足以影响本合同项下保证人承担连带保证责任的；</p>
    <p>（三）	保证人拒绝甲方对其资金和财产状况进行监督的；</p>
    <p>（四）	保证人向第三方提供超出其自身负担能力的担保的。</p>
    <p><strong>第四十五条</strong>抵押期间内，抵押人发生下列情况之一，乙方未提供符合甲方要求的新的担保的，构成乙方违约：</p>
    <p>（一）	抵押人未按甲方要求办理抵押物财产保险的，或发生保险事故后，未按抵押合同约定处理保险赔偿金的；</p>
    <p>（二）	因第三人的行为导致抵押物毁损、灭失、价值减少，抵押人未按抵押合同约定处理损害赔偿金的；</p>
    <p>（三）	未经甲方书面同意，抵押人赠予、转让、出租、重复抵押、迁移或以其他方式处分抵押物的；</p>
    <p>（四）	抵押人经甲方同意处分抵押物，但处分抵押物所得价款未按抵押合同约定进行处理的；</p>
    <p>（五）	抵押物毁损、灭失、价值减少，足以影响本合同项下的债务的清偿，抵押人未及时恢复抵押物价值，或未提供甲方认可的其他担保的；</p>
    <p>（六）	抵押合同约定的抵押人其他违约情形。</p>
    <p><strong>第四十六条</strong>质押期间内，出质人发生下列情况之一，乙方未提供符合甲方要求的新的担保的，构成乙方违约：</p>
    <p>（一）	出质人未按甲方要求办理质押财产保险的，或发生保险事故后，未按质押合同约定处理保险赔偿金的；</p>
    <p>（二）	因第三人的行为导致质押财产毁损、灭失、价值减少，出质人未按质押合同约定处理损害赔偿金的；</p>
    <p>（三）	出质人经甲方同意处分质押财产，但处分质押财产所得价款未按质押合同约定进行处理的；</p>
    <p>（四）	质物毁损、灭失、价值减少，足以影响债务本息清偿的，出质人未及时恢复质物价值，或未提供甲方认可的其他担保的；</p>
    <p>（五）	质押合同约定的出质人其他违约情形。</p>
    <p><strong>第四十七条</strong>担保合同或其他担保方式未生效、无效、被撤销，或担保人出现部分或全部丧失担保能力的其他情形或者拒绝履行担保义务，乙方未按甲方要求落实新的担保的，视为乙方违约。</p>
    <p><strong>第四十八条</strong>乙方发生本合同约定的违约情形的，甲方有权根据情节轻重调整、减少、暂停或终止本合同项下授信额度金额及额度支用期，并有权采取以下部分或全部措施：</p>
    <p>（一）	宣布直接或间接源于本合同的一切债务提前到期，并要求乙方立即清偿；</p>
    <p>（二）	停止发放贷款； </p>
    <p>（三）	单方面解除合同；</p>
    <p>（四）	要求乙方限期纠正违约情形； </p>
    <p>（五）	如乙方未按期还款且又未就展期事宜与甲方达成协议即构成贷款逾期，甲方有权按照本合同约定的罚息利率计收罚息；</p>
    <p>（六）	乙方未按本合同规定的用途使用贷款资金，甲方有权按照本合同约定的罚息利率计收罚息；</p>
    <p>（七）	要求乙方支付本合同金额<span>${js.contractAmountpenaltyPercentage }</span>%的违约金，违约金不足以赔偿贷款人损失的，乙方应当继续承担赔偿责任；</p>
    <p>（八）	以法律手段追偿贷款，并要求乙方承担甲方因实现债权而发生的各项合理费用(包括但不限于诉讼费、财产保全费、执行费、仲裁费、律师代理费、差旅费、评估费、拍卖费等)；</p>
    <p>（九）	处分抵押/质押财产，实现抵押权/质押权；</p>
    <p>（十）	要求乙方提供或追加担保，担保的形式包括但不限于保证、抵押和质押；</p>
    <p>（十一）	从乙方和／或保证人在中国邮政储蓄银行及其分支机构开立的银行账户扣收全部贷款本息和其他应付款项。如果账户中款项的币种与贷款币种不同，甲方有权按当日外汇挂牌价折算成贷款币种清偿贷款；甲方扣收乙方未到期的定期存款时，需全部提前支取的，按支取日挂牌公告的活期存款利率计付利息；需部分提前支取的，提前支取部分按支取日挂牌公告的活期存款利率计付利息，其余部分到期时按该笔定期存款开户日的定期存款利率计付利息。因扣收而产生的利息损失，由乙方承担。</p>
    <p>（十二）	采取维护其在本合同项下权益的符合有关法律规定的其他措施。</p>
    <p><strong>第四十九条</strong>发生以下情形之一的，甲方即构成违约：</p>
    <p>（一）无本合同约定的正当理由，未按本合同约定向乙方提供贷款；</p>
    <p>（二）无本合同约定的正当理由，停止发放或提前收回贷款；</p>
    <p>（三）未按中国人民银行有关利率的规定计收利息。</p>
    <p>发生本合同约定的甲方违约事件时，乙方有权要求甲方限期纠正；给乙方造成损失的，乙方有权要求甲方赔偿。</p>
    <h3>第十章　其他条款</h3>
    <p><strong>第五十条</strong>费用的承担</p>
    <p>（一）	本合同及与本合同项下担保有关的律师服务、保险、评估、登记、保管、鉴定、公证等费用，由乙方承担，双方另有约定的除外；</p>
    <p>（二）	甲方为实现债权而实际发生的一切费用（包括但不限于诉讼费、仲裁费、财产保全费、差旅费、执行费、评估费、拍卖费、公证费、送达费、公告费、律师费等）均由乙方承担。</p>
    <p><strong>第五十一条</strong>公告催收</p>
    <p>对乙方拖欠贷款本息或发生其他违约情形的，甲方有权向有关部门或单位予以通报，有权通过新闻媒体或其他方式进行公告催收，或为催收之目的将有关信息提供给催收机构。乙方同意接受甲方电话与短信提醒、催收函等催收方式，并对预留的电话号码和通讯地址的真实性负责，预留电话的接听人视为乙方本人，催收函送予预留通讯地址后视为已送达乙方；乙方电话号码和通讯地址变更后，应当在当期结息日之前通知甲方，否则由乙方承担由此产生的不利后果。电话、短信催收等其他催收方式与催收函具有相同的法律效力。</p>
    <p><strong>第五十二条</strong>甲方记录的证据效力</p>
    <p>除非有可靠、确定的相反证据，甲方有关本金、利息、费用和还款记录等内容的内部账务记载，甲方制作或保留的乙方办理提款、还款、付利息等业务过程中发生的单据、凭证及甲方催收贷款的记录、凭证，均构成有效证明甲乙双方之间债权债务关系的确定证据。乙方不能仅因为上述记录、记载、单据、凭证由甲方单方制作或保留而提出异议。</p>
    <p><strong>第五十三条</strong>权利保留</p>
    <p>甲方在本合同项下的权利并不影响和排除其根据法律、法规和其他合同所享有的任何权利。甲方对任何违约或延误行为所施以的任何宽容、宽限、优惠或延缓行使本合同项下的任何权利，均不能视为对本合同项下权利、权益的放弃或对任何违反本合同行为的许可或认可，也不限制、阻止和妨碍对该权利的继续行使或对其任何其它权利的行使，也不因此导致甲方对乙方承担义务和责任。</p>
    <p><strong>第五十四条</strong>除本合同项下的债务外，乙方对甲方还负有其他到期债务的，甲方有权查询、划收乙方在中国邮政储蓄银行及其分支机构开立账户中的人民币或其他币种的款项首先用于清偿任何一笔到期债务，乙方应予以积极配合，甲方应在扣收后及时通知乙方。</p>
    <p><strong>第五十五条</strong>本合同履行过程中，甲方传递给乙方的书面通知，按合同所列通讯地址或号码进行。乙方的通讯地址或联系方式如发生变动，应提前10个工作日书面通知甲方，因未及时通知而造成的损失由乙方自行承担。甲方发生需通知乙方的事项时，也可采用在甲方营业机构张贴公告、网上银行、电话银行等方式通知。</p>
    <p><strong>第五十六条</strong>公证</p>
    <p>如果甲方要求，乙方应配合甲方对本合同进行强制执行公证。强制执行公证后乙方不履行本合同项下义务的，甲方可以依法向有管辖权的人民法院申请强制执行。</p>
    <p><strong>第五十七条</strong>双方约定的其他事项：</p>
    <p><strong>第五十八条</strong>本合同一式<span> 2 </span>份，乙方<span> 1 </span>份，甲方<span> 1 </span>份，<span>                   </span>，每份具有同等法律效力。</p>
    <h3>第十一章　合同生效、变更和解除</h3>
    <p><strong>第五十九条</strong>本合同自乙方签字并由甲方负责人（或授权代理人）签字（或签章）并加盖公章或合同专用章后成立并生效。</p>
    <p><strong>第六十条</strong>本合同生效后，甲、乙双方任何一方不得擅自变更或提前解除本合同。除本合同另有约定外、需要变更或解除合同时，应经双方协商一致，以书面形式予以变更或解除。乙方要求变更或提前解除合同时，应提前一个月以书面形式通知甲方。</p>
    <h3>第十二章　争议和解决</h3>
    <p><strong>第六十一条</strong>本合同项下业务办理过程中使用的《中国邮政储蓄银行小额贷款额度借款支用单》、借据、放款单、提前还款申请书、还款计划表、还本计划表等单据、文件或材料，均为本合同的有效组成部分，构成一个合同整体。</p>
    <p><strong>第六十二条</strong>甲、乙双方在履行本合同中如发生争议，可由双方协商或者通过调解解决。协商或调解不成的，按照以下第<span> 一 </span>种方式解决：</p>
    <p>（一）向甲方住所地有管辖权的人民法院提起诉讼。</p>
    <p>（二）向仲裁委员会申请按该会届时有效的仲裁规则进行仲裁，仲裁裁决是终局的，对双方具有约束力。</p>
    <p>在诉讼或仲裁期间，本合同不涉及争议部分的条款仍需履行。</p>
    <h3>第十三章  声明条款</h3>
    <p><strong>第六十三条</strong>乙方清楚地知悉甲方的经营范围、授权权限。</p>
    <p><strong>第六十四条</strong><strong>保证与承诺</strong></p>
    <p><strong>1.乙方有权签署本合同。</strong></p>
    <p><strong>2.乙方承诺将合法、合规使用合同项下贷款。</strong></p>
    <p><strong>3.乙方若违反上述保证与承诺，需承担相应的违约与法律责任。</strong></p>
    <p><strong>第六十五条</strong><strong>提示</strong></p>
    <p><strong>乙方已阅读本合同及主合同所有条款。应乙方要求，甲方已经就本合同及主合同做了相应的条款说明。乙方对本合同及主合同条款的含义及相应的法律后果已全部通晓并充分理解。</strong></p>
    <p><strong>（以下无正文）</strong></p>
    <p><strong>本页以下为签字页，无正文</strong></p>
    <p>甲方：中国邮政储蓄银行股份有限公司（合同专用章或公章）</p>
    <p>负责人（或授权代理人）：<span>${js.lenderLeading}</span>（签字或名章）</p>
    <p class="tar">合同签署日期：<span>${year}</span>年<span>${month}</span>月<span>${day}</span>日</p>
    <p>乙方为自然人填写</p>
    <p>乙方（借款人签字及手印）：<span>${customerName}</span>（身份证号：<span>${certNo}</span>） </p>
    <p>乙方配偶（签字及手印）：<span>${contactName}</span>（身份证号：<span>${contactCertNo}</span>）</p>
    <p><strong>乙方为法人填写：</strong></p>
    <p>乙方：<span>${customerName}</span> （公章）</p>
    <p>法定代表人/负责人（或授权代理人）：<span>${customerName}</span></p>
    <p class="tar">合同签署日期：<span>${year}</span>年 <span>${month}</span> 月<span>${day}</span>日</p>
   
</div>
</body>
</html>
