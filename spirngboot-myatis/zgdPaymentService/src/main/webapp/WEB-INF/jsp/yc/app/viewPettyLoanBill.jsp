<%@ page contentType="text/html;charset=utf-8" language="java"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset=utf-8 />
<title>中国邮政储蓄银行小额贷款额度借款支用单</title>
</head>

<body style = "font-family: SimSun;" >
<div class="hetong_wrap">
	<p>时间戳：${nowTime}</p>
    <h2>中国邮政储蓄银行小额贷款额度借款支用单</h2>
    <p><strong>一、借款人填写</strong></p>
    <p>借款人姓名（法人填写法人名称）：<span>${billInfo.userName}</span>证件名称： <span>身份证</span>  证件号码： <span>${billInfo.certNo}</span></p>
    <p>申请支用借款金额：人民币（大写）<span>${billInfo.capitalAmount}</span>     （小写）<span>${billInfo.applyAmount}</span> 元</p>
    <p>借款期限：<span>${billInfo.startYear}</span>年<span>${billInfo.startMonth}</span>月至<span>${billInfo.endYear}</span> 年<span>${billInfo.endMonth}</span>月                           借款用途： <span>掌柜贷</span></p>
    <p>申请拟按以下第<span> 2 </span>种方式归还贷款本息：</p>
    <p>1.一次性还本付息还款法：<span>    </span>年<span>   </span>月<span>   </span>日到期一次归还贷款本息之和。2.等额本息还款法：每<span>期</span>等额归还贷款本息。3.阶段性等额本息还款法：借款前<span></span>个<span></span>按期偿还当期利息，不还本金。此后期间，按照等额本息还款法偿还。4.按<span></span>付息，到期一次性还本。5.按<span></span>付息，按还本计划表还本（具体还本日期及金额见还本计划表，还本计划表为本支用单的附件，与本支用单具有同等法律效力）。6.其他方式<span></span>。</p>
    <p>还款日为放款日以后期的      （对日/指定日）；采用对日还款法的，放款日在以后期没有对日的，月末日为还款日（具体还款日与还款金额以还款计划表为准）</p>
    <p>借款如获批准，根据相关要求拟申请采用如下支付方式<span>自主支付方式</span>（最终以贷款行批准为准）：</p>
    <p>受托支付方式：还款账户户名：<span></span>，账号：<span></span> ，委托银行将资金先发放到上述账户（资金发放到上述账户日为贷款起息日），并授权银行直接将资金划转至贷款受托支付清单所列账户，且承诺如在划转过程中需要本人配合，本人自愿履行配合义务。</p>
    <p>自主支付方式：还款账户户名：<span>${billInfo.userName}</span>，账号：<span>${billInfo.cardNo}</span>。</p>
    <div class="clearfix">
        <div class="flr">
            <p> 自然人填写<span>${billInfo.userName}</span>：</p>
            <p>借款人（签字）：<span>${billInfo.userName}</span>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span>${billInfo.startYear}</span>年<span>${billInfo.startMonth}</span>月<span>${billInfo.startDay}</span>日</p>
            <p>法人填写：</p>
            <p>借款人（公章）：<span>    </span></p>
            <p>法定代表人或授权代理人（签字）：<span>${billInfo.userName}</span> &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span>${billInfo.startYear}</span>年<span>${billInfo.startMonth}</span>月<span>${billInfo.startDay}</span>日</p>
        </div>
    </div>
    <p><strong>二、贷款银行填写</strong></p>
    <p>经审核，借款人的小额贷款额度借款合同（合同编号：<span>${billInfo.contractNo}</span> ）总额度为<span>${billInfo.creditLimit}</span> 元，</p>
    <p>当前可用额度为<span>${billInfo.availBalance}</span>元，额度支用期截止到<span>${billInfo.expireYear}</span>年<span>${billInfo.expireMonth}</span>月<span>${billInfo.expireDay}</span>日，符合借款支用条件。同意借款人申请借款<span>${billInfo.applyAmount}</span>元，期限<span>3</span>个月，还款方式为<span>等额本息</span> 。实际放款日与到期日以借款借据为准。</p>
    <p>一、本笔贷款利率为年利率，采用下列第<span>  1  </span>种：</p>
    <p>（一）固定利率：</p>
    <p>1.年利率为<span>${billInfo.yearRate}</span>，在借款期限内，该利率保持不变；</p>
    <p>2.在人民银行同期同档次贷款基准利率（贷款发放日的基准利率）水平<span>   </span>(上浮/下调)<span>   </span>%，支用期内利率不调整；</p>
    <p>（二）浮动利率，在人民银行同期同档次贷款基准利率（贷款发放日的基准利率）水平<span>    </span>(上浮/下调)<span>    </span>%，该贷款利率自起息日起，每年年初1月1日调整一次。</p>
    <p>二、借款人提前还款的，最低提前还款本金为<span>    </span>元，并应按照下列第<span>     </span>项约定向贷款银行支付提前还款违约金：<span>    </span></p>
    <p>（一）无须支付提前还款违约金。</p>
    <p>（二）应按提前偿还贷款本金的<span>    </span>%支付违约金。</p>
    <div class="clearfix">
        <div class="flr">
            <p>贷款银行经办人签字：<span>    </span>&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 审查人签字：<span>   </span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span>${billInfo.startYear}</span>年<span>${billInfo.startMonth}</span>月<span>${billInfo.startDay}</span>日</p>
            <p>贷款银行业务主管意见： <span>    </span>   </p>
            <p>贷款银行业务主管（签字）： <span>    </span></p>
            <p>贷款银行（盖章）：  &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span>${billInfo.startYear}</span>年<span>${billInfo.startMonth}</span>月<span>${billInfo.startDay}</span>日</p>
        </div>
    </div>
    <p><strong>三、借款人确认签字</strong></p>
    <p>借款人同意贷款银行以上关于贷款条件的填写内容，并承诺按照以上约定及已签订的合同履行义务。</p>
    <div class="clearfix">
        <div class="flr">
            <p>自然人填写：<span>${billInfo.userName}</span></p>
            <p> 借款人（签字）：<span>${billInfo.userName}</span> &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span>${billInfo.startYear}</span>年<span>${billInfo.startMonth}</span>月<span>${billInfo.startDay}</span>日</p>
            <p>法人填写：</p>
            <p>借款人（公章）：<span>     </span></p>
            <p>法定代表人或授权代理人（签字）：<span>${billInfo.userName}</span>  &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span>${billInfo.startYear}</span>年<span>${billInfo.startMonth}</span>月<span>${billInfo.startDay}</span>日</p>
        </div>
    </div>
</div>
</body>
</html>
