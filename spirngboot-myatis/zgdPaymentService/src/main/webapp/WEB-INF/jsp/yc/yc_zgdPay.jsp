<%@ page contentType="text/html;charset=utf-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.ule.uhj.util.Constants"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>进货批发</title>
    <link href="https://i1.ule.com/sld/20170803/css/base.css" rel="stylesheet"/>
	<style>
        .mask{
            position: fixed;
            left: 0;
            top:0;
            width: 100%;
            height:100%;
            background: rgba(0,0,0,.3);
            z-index: 1000;
        }
        .flower-dialog{
            position: absolute;
            width: 20%;
            position: absolute;
            left: 40%;
            top:50%;
            -webkit-transform: translateY(-50%);
            -moz-transform: translateY(-50%);
            -ms-transform: translateY(-50%);
            -o-transform: translateY(-50%);
            transform: translateY(-50%);
            background: #ffffff;
            box-shadow: 0 0 2px 2px rgba(0,0,0,.4);
        }
        .flower-dialog .title{
            line-height: 30px;
            font-family: 'Microsoft YaHei';
            font-weight: normal;
            font-size: 16px;
            color: #333333;
            padding: 0;
            margin: 0;
            padding-left: 20px;
            border-bottom: 2px solid #c24648;
        }
        .flower-dialog .d-content{
            padding: 10px;
            line-height: 26px;
            font-size: 14px;
            color: #666666;
        }
        .flower-dialog .dialog-close{
            width: 30px;
            height:30px;
            font-size: 20px;
            line-height: 30px;
            text-align: center;
            position: absolute;
            right: 0;
            top:0;
            background: rgba(255,0,0,.4);
        }

    </style>     
	<script>
		String.prototype.substitute = function (a) {
		    return a&&"object"==typeof a?this.replace(/\{([^{}]+)\}/g,function(b,c){var d=a[c];return void 0!==d?""+d:""}):this.toString()
		}
		function _Dialog(options) {
		    var _this = this;
		    var Tmp = [
		        '<div class="flower-dialog">',
		        '<span class="dialog-close">X</span>',
		        '<h4 class="title">{title}</h4>',
		        '<div class="d-content">{content}</div>',
		        '</div>'
		    ].join('');
		    if(typeof options !== 'object'){
		        this.title = '消息提示';
		        this.content = options;
		    }else{
		        this.title = options.title;
		        this.content = options.content;
		    }
		    var HTML = Tmp.substitute({
		        title : this.title,
		        content : this.content
		    })
		    var body = document.body;
		    this.dom = document.createElement("div");
		    this.dom.className = 'mask';
		    this.dom.id = 'onlyMask';
		    this.dom.innerHTML = HTML;
		    body.appendChild(this.dom);
		    var closeBtn = this.dom.getElementsByClassName('dialog-close')[0];
		    closeBtn.addEventListener('click',function () {
		        _this.remove();
		    },false)
		}
		_Dialog.prototype.remove = function () {
		    document.body.removeChild(this.dom)
		}
		function dialog(agu){
		    return new _Dialog(agu);
		}
				
	</script>
    <style>
        .fc_red{
            color:#c30d23;
        }
        #zgdPayBox {
            font-size:16px;
            background-color:#f2f2f2;
            padding-bottom:10px;
        }
        #zgdPayBox #logo{
            background-color:#fff;
        }
        #zgdPayBox .logo{
            padding:16px 0;
            width: 1056px;
            margin: auto;
        }
        .zgdPayBox .zgdPay{
            width: 1056px;
            margin: auto;
            background-color:#fff;
        }

        .zgdPay .zgdPay_head{
            padding:16px 0;
            line-height:20px;
            background-color:#f2f2f2;
        }
        .zgdPay_head .ellipsis{
            width:260px;
            display:inline-block;
            overflow: hidden;
            text-overflow:ellipsis;
            white-space: nowrap;
            vertical-align: -4px;
        }
        .zgdPay .zgdPay_main{
            padding:20px 40px 40px 40px;
        }
        .zgdPay_main .zgdzfp1{
            margin-bottom:20px;
        }
        .zgdPay_main .zgdzfs2{
            color:#f03b3b;
        }
        .zgdPay_main .ul1 .icon{
            display:inline;
            vertical-align: middle;
            margin-right:10px;
        }
        .zgdPay_main .ul1 li:first-child{
            margin-right:10px;
        }
        .zgdPay_main .ul1 li.li21{
            padding:20px;
            border:1px solid #ccc;
            margin:0 0 20px 0;
        }
        .li21 dl{
            width:516px;
            float:left;
        }
        .li21 dd p, .li21 dd div{
            text-align: center;
            float:left;
        }
        .li21 .dl1{
            width:400px;
            border-right:1px solid #eee;
        }
        .li21 .dl2 dd div, .li21 .dl2 dd p{
            width:33.33%;
        }
        .li21 .dl1 dd p{
            width:50%;
        }
        .li21 dl dd:first-child{
            margin-bottom:10px;
            color:#999;
        }
        .ul1 .li2 .fixedRepayDateOuterBox select.select-lend{
            padding:4px 50px;
        }
        .ul1 .li2 .fixedRepayDateOuterBox .icon_calendar{
            margin-left:10px;
            margin-top:4px;
        }
        .ul1 .li2 dl dd:first-child{
            width:80px;
            text-align: right;
            margin-left:0;
        }
        .ul1 .li2 dl dd{
            line-height:30px;
            float:left;
            margin-left:30px;
        }
        .ul1 .li2 .smscode{
            margin:20px 0 20px 0;
        }
        .li2 .smscode .yzm{
            width:244px;
        }
        .li2 .smscode .isShowSMS{
            display:none;
        }
        .ul1 .li2 .smscode input{
            width:240px;
            height:26px;
            line-height:26px;
            text-indent:1rem;
            border:1px solid #bbb;
        }
        .smscode .smscodedd{
            display:none;
            margin-left:0;
        }
        .li2 .smscode .msgValidateCodebox{
            margin-left:4px;
        }
        .xieyi .submit{
            margin-top:10px;
        }
        .xieyi .submit input{
            display:block;
            padding:6px 20px;
            background-color:#ccc;
            color:#000;
            font-size:16px;
            cursor:pointer;
        }
        .xieyi .submit .checked{
            background-color:#f03b3b;
            color:#fff;
        }
        .xieyi .zgd-lend-check .zgd-checkbox{
            border:1px solid #ccc;
            border-radius: 50%;
            vertical-align: -3px;
            width:16px;
            height:16px;
            display:inline-block;
        }
        .xieyi .zgd-lend-check .zgd-check{
            background-color:#fff;
            width:12px;
            height:12px;
            display:block;
            border-radius: 100%;
            margin:2px;
        }
        .xieyi .zgd-checkbox .checked{
            background-color:#f03b3b;
        }
        .xieyi .dd2 .amount-contract{
            color: #426bf2;
        }
        .zgdpayfooter{
            text-align:center;
            background-color:#f2f2f2;
            padding:20px 10px;
        }
        .zgdpayfooter img{
            display:inline;
        }
        #getValidateCode{
            cursor:pointer;
        }
    </style>
    <!--<link rel="stylesheet" href="https://i1.beta.ule.com//c/my/loan/2016/0111/c/zgd_popbox.css">-->
</head>

<body>
<div id="zgdPayBox">
    <div id="logo">
        <p class="logo">
            <img class="logo_1" src="http://i2.ule.com/yzg/app/zgd/i/logo.png" width="160px" height="55px">
        </p>
    </div>
    <div class="zgdPayBox clear">
        <div class="zgdPay">
            <div class="zgdPay_head clear">
                商品名称：<span class="ellipsis">${result.productBody}</span>
                <span class="fr bold">应付金额<span class="fc_red f18"> ${result.loanAmount} </span>元</span>
            </div>
            <div class="zgdPay_main">
                <p class="zgdzfp1">
                    <span class="zgdzfs1">掌柜贷支付</span>
                    <span class="zgdzfs2">（先拿货、后给钱、订单签收后开始计息）</span>
                </p>
                <ul class="clear ul1">
                    <li class="li21 clear">
                        <dl class="dl1 fl">
                            <dd class="clear">
                                <p>可用额度（元）</p>
                                <p>总额度（元）</p>
                            </dd>
                            <dd class="clear">
                                <p class="bold">${result.availBalance}</p>
                                <p>${result.limit}</p>
                            </dd>
                        </dl>
                        <dl class="dl2">
                            <dd class="clear">
                                <p>年利率</p>
                                <p class="fq"></p>
                                <p>还款方式</p>
                            </dd>
                            <dd class="clear">
                                <div>${result.interRate}%</div>
                                <div class="period"></div>
                                <div class="repayType"></div>
                            </dd>
                        </dl>
                    </li>
                    <li class="fl li2">
                        <dl class="clear fixedRepayDateOuterBox">
                            <dd>固定还款日</dd>
                            <dd class="clear">
                                <label class="lend-forminput fl">
                                    <div class="fixedRepayDateOuter">
                                        <select name="fixedRepayDate" class="fixedRepayDate select-lend"
                                        <c:if test='${result.isHaveFixDate == 1}'>disabled="disabled"</c:if>
                                        >
                                        </select>
                                    </div>
                                </label>
                                <img class="fl icon_calendar" src="http://i2.ule.com/yzg/app/zgd/i/calendar.png">
                            </dd>
                        </dl>
                        <dl class="clear smscode">
                            <dd class="yzm">验证码</dd>
                            <dd>
                                <p><input class="smscodeinput" type="text" placeholder="请输入付款验证码"></p>
                                <p class="isShowSMS">验证码已成功发送至${result.mobile}</p>
                            </dd>
                            <dd class="msgValidateCodebox">
                                <div class="msgValidateCode">
                                    <div class="getcode" id="getValidateCode"> 获取验证码</div>
                                </div>
                            </dd>
                            <dd class="smscodedd"><span></span> s</dd>
                        </dl>
                        <dl class="xieyi clear">
                            <dd>&nbsp;</dd>
                            <dd class="dd2">
                                <div class="zgd-lend-bot">
                                    <label class="zgd-lend-check y-check-con ">
                                        <span class="zgd-checkbox"><span class="zgd-check"></span></span>
                                        <span class="zgd-check-txt">我已阅读并同意以下协议</span>
                                    </label>
                                </div>
                                <a class="amount-contract lend-contract1" href="javascript:;" target="_blank">《掌柜借款协议》</a>
                                <a class="amount-contract lend-contract2" href="javascript:;" target="_blank">《支用单》</a>
                                <c:if test='${result.isFirstLoan == 1}'><a class="amount-contract lend-contract3" href="javascript:;" target="_blank">《小额贷款额度借款合同》</a></c:if>
                                <p class="submit">
                                    <input type="submit" value="立即支付" onclick="sendDataFn()" disabled>
                                </p>
                            </dd>
                        </dl>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
    
</div>
<div class="zgdpayfooter"><img src="http://i2.ulecdn.com/yzg/app/zgd/i/bottom.png" width="687px" ></div>

<!-- popbox -->
<script src="<%=Constants.get("GLOBAL_SERVER") %>/j/lib/jquery-1.7.2.min.js"></script>
<script>
console.log('${result}')
var senddata={
		userOnlyId:'${result.userOnlyId}',
		validatecode:$('.smscodeinput').val(), //验证码
		payableAmount:'${result.loanAmount}',
		productDetail:'${result.productDetail}',
		reqNo:'${result.reqNo}',
		orderType:'${result.orderType}',
		princInter:'${result.princInter}'
	};
var _fq, _period, _repayType;
	if(senddata.orderType === '01'){
		_fq = '账单';
		_period =senddata.princInter+ '*1期';
		_repayType = '一次性还本付息';
	}else{
		_fq = '分期';
		_period = '24个月';
		_repayType = '等额本息';
	}
	$('.fq').html(_fq);
	$('.period').html(_period);
	$('.repayType').html(_repayType);
	
function sendDataFn() {
	senddata.validatecode = $('.smscodeinput').val();
	$.ajax({
		url:'<%=Constants.get("UHJ_SERVER") %>/zgd/pixiao/toPay.do',
		type: 'get',
		data:{
			message: JSON.stringify(senddata)
		},
		dataType:'json',
		success: function(data) {
			console.log('这是立即支付后的返回数据',data)
			if(data.code === '0000') {
				var _url = data.returnUrl + '?message=' + encodeURI(data.message);
				location.href = _url;
			} else {
				dialog({ title : '提示',content : data.msg});
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log('XMLHttpRequest:',XMLHttpRequest, 'textStatus:',textStatus, 'errorThrown', errorThrown,'立即支付error')
			dialog({ title : '提示',content : XMLHttpRequest.statusText});  
		}
	});
}
    $(function(){
        var iscontrol1, iscontrol2,
			gdhkval = '${result.fixedRepayDate}', // 固定还款日
			days = 28; //固定还款日总天数

		function setDays(day){
			var _dom = [];
			for (var i = 1, len = days + 1; i<len; i++){
				if(i != day) {
					_dom.push('<option value=' + i + '>' + i + '</option>')
				} else {
					_dom.push('<option value=' + i + ' selected="selected">' + i + '</option>')
				}
			}
			if('${result.isHaveFixDate == 0}') _dom.unshift('<option value="">请选择固定还款日</option>')
			$('.fixedRepayDate').html('');
			$('.fixedRepayDate').html(_dom.join(''));
		};
		setDays(gdhkval);
		senddata.fixDate = gdhkval;
	    //checkbox
	    $('.smscodeinput').keyup(function(){
	        $(this).val() ? iscontrol2 = true : iscontrol2 = false;
	        checkDoSubmit();
	    });
	    $('.zgd-lend-check').live('click',function(){
	        $(this).find('.zgd-check').toggleClass('checked');
	        $(this).find('.zgd-check').hasClass('checked')?iscontrol1 = true:iscontrol1 = false;
	        checkDoSubmit();
	//        signContract=$('.y-check-con').find('.zgd-check').hasClass('checked') ? 1 : 0;
	    });
	    // 检查是否可以点击下一步
	    function checkDoSubmit() {
	        var $input = $('.submit').find('input:submit');
	        if(iscontrol1 && iscontrol2 && gdhkval) {
	            $input.addClass('checked');
	            $input.removeAttr('disabled','');
	        }else{
	            $input.removeClass('checked');
	            $input.attr('disabled','')
	        }
	    }
	
	    // 发送验证码
	    $('#getValidateCode').on('click', function() {
		    var _this = $(this);
	        $.ajax({ // 获取短信验证码
	            url: '<%=Constants.get("UHJ_SERVER")%>/zgd/pixiao/sendMsg.do',
	            type: 'get',
	            data: {userOnlyId:'${result.userOnlyId}'},
	            dataType: 'json',
	            success: function(data) {
		            console.log('这是发送短信验证码的返回',data);
		            _this.hide();
                    $('.smscodedd').show();
                    $('.isShowSMS').show();
                    var time = 60;
                    $('.smscodedd span').text(time);
                    var timer = setInterval(function(){
                        $('.smscodedd span').text(--time);
                        if(time <= 0)  {
                            clearInterval(timer);
                            $('#getValidateCode').show();
                            $('.smscodedd').hide();
                            $('.isShowSMS').hide();
                        }
                    },1000);
	                if (data.code!=='0000') {
	                	dialog({ title : '提示',content : data.msg});  
	                }
	            },
	            error : function(XMLHttpRequest, textStatus, errorThrown) {
		            console.log('XMLHttpRequest:',XMLHttpRequest, 'textStatus:',textStatus, 'errorThrown', errorThrown,'短信验证码error')
	            	dialog({ title : '提示',content : XMLHttpRequest.statusText});  
	            }
	        })
	    });
	    //固定还款日选择
	    $('.fixedRepayDate').on('change', function() {
	    	$(this).find('option:selected').each(function(i){
		    	setDays($(this).val());
	            gdhkval = $(this).val();
	        });
	    	checkDoSubmit();
			senddata.fixDate = gdhkval;
	    });
	    // 掌柜借款协议
	    var applyAmount = '${result.loanAmount}';
	    $('.lend-contract1').live('click',function(){
	        $(this).prop('href', '/lendvps/uhj/yczgd_viewPxContract.do?applyAmount='+applyAmount);
	    })
	    // 支用合同
	    $('.lend-contract2').live('click',function(){
	        $(this).prop('href', '/lendvps/uhj/yczgd_viewPettyLoanBill.do?applyAmount='+ applyAmount +'&lastRepayDate=${result.lastRepayDate}&flag=1');
	    })
	    // 小额贷款额度借款合同
	    $('.lend-contract3').live('click',function(){
	        $(this).prop('href', '/lendvps/uhj/yczgd_viewPettyLoan.do?applyAmount='+ applyAmount );
	    })
    });
</script>
</body>
</html>