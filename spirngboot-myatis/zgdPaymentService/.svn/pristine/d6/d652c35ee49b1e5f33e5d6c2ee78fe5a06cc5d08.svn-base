<%@ page contentType="text/html;charset=utf-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.ule.uhj.util.Constants"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>掌柜贷提示页面</title>
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/css/common.css">
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/css/PxBank.css">
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/j/vue.min.js"></script>
    <script src="<%=Constants.get("GLOBAL_SERVER") %>/j/lib/jquery-1.7.2.min.js"></script>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/j/layer.js"></script>
</head>
<body>
    <div id="PxBank">
        <header>
            <p class="p1 f_bold">¥{{creditBaseAmount}}</p>
            <p class="p2">
                <span class="sp1">●&nbsp;<span class="f_bold">年利率：</span> {{loanRate}}%</span>
                <span class="sp2">●&nbsp;<span class="f_bold">还款方式：</span> {{loanRepayType}}</span>
                <span class="sp3">●&nbsp;<span class="f_bold">支持提前还款：</span> {{prepayment}}</span>
            </p>
        </header>
        <div class="main clearfix">
            <h1 class="h2">绑定银行卡</h1>
            <div class="tablelist">
                <ul>
                    <li><label for="user">持 卡 人</label><input type="text" id="user" :value="user" disabled="true"></li>
                    <li class="clearfix">
                        <p class="fl"><label for="idcard">身 份 证</label><input type="text" id="idcard" :value="idCard" disabled="true"></p>
                        <p class="fl p2txt">如信息有误，请联系地推或者拨打客服电话11185转4</p>
                    </li>
                    <li class="clearfix">
                        <p class="fl"><label for="bankcard">银行卡号</label><input type="text" onkeyup="getWriteBankCard(this.value)" :disabled="isAddCard" :class="!isAddCard && 'bcwhite'" id="bankcard" placeholder="仅支持邮储银行借记卡"></p>
                        <p v-if="isAddCard" class="fl addcard" @click="addCard"><span class="icon6"><img src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/img/icon/6.png" height="12" width="12"/></span>添加新卡</p>
                    </li>
                    <li><label for="phone">手机号码</label><input type="text" maxlength="11" onkeyup="getWritePhone(this.value)" onblur="getWritePhone(this.value)" :disabled="isAddCard" :class="!isAddCard && 'bcwhite'" id="phone" placeholder="请输入银行预留手机号"></li>
                    <li class="clearfix">
                        <p class="fl"><label for="smscode">验 证 码</label><input type="text" onkeyup="getWriteSms(this.value)" id="smscode" placeholder="请输入验证码"></p>
                        <input type="text" value="获取验证码" v-if="isShowBtn" class="getsmsCode" :class="getSend" @click="getSMS" :disabled="!getSend.isOkSend" readonly="readonly" >获取验证码</input>
                        <span v-else>{{time}} s</span>
                    </li>
                    <li class="clearfix smsbox">
                        <label class="fl">&nbsp;</label>
                        <p v-show="!isShowBtn" class="fl smstxt" id="yzmTxt"></p>
                    </li>
                    <div class="xieyi clearfix">
                        <label class="fl xieyilabel">&nbsp;</label>
                        <div class="zgd-lend-bot fl">
                            <label class="zgd-lend-check y-check-con " @click="toggleClass()">
                                <span class="zgd-checkbox"><span class="zgd-check" :class="checked && 'checked'"></span></span>
                                <span class="zgd-check-txt">我已阅读并同意下列协议内容</span>
                            </label>
                            <a class="amount-contract" href="<%=Constants.get("UHJ_SERVER")%>/pxZgd/toPersonalInfo.do?flage=1" target="_blank">《邮储银行绑卡协议》</a>
                            <a class="amount-contract" href="<%=Constants.get("UHJ_SERVER")%>/pxZgd/toPersonalInfo.do" target="_blank">《征信查询协议》</a>
                            <a class="amount-contract" href="<%=Constants.get("UHJ_SERVER")%>/uhj/yczgd_fwktxy.action" target="_blank">《掌柜贷服务协议》</a>
                        </div>
                    </div>
                    <div class="clearfix btnbox">
                        <label class="fl">&nbsp;</label>
                        <input type="button" class="fl btn" :class="bankCard && getSend.isOkSend && smscode && checked && 'isOkSend'" :disabled="isSubmit" value="立即激活" @click="submitNext">
                    </div>
                </ul>
            </div>
        </div>
        <footer></footer>
    </div>
    <script>
        var _data,
            _url1= '<%=Constants.get("UHJ_SERVER")%>/pxZgd/queryCardNos.do', // 首次进入页面调用
            _url2= '<%=Constants.get("UHJ_SERVER")%>/pxZgd/sendSmsRandomCode.do', // 获取验证码
            _url3= '<%=Constants.get("UHJ_SERVER")%>/pxZgd/signSubmit.do' // 立即激活
            
        ;
            $.ajax({
                url: _url1,
                dataType: 'jsonp',
                jsonp: 'jsoncallback',
                success: function(res){
                    console.log('页面初始返回的数据：', res);
                    if (res.code === '0000') {
                        var _cardNos = res.cardNos ? res.cardNos[0] : undefined;
                        _data = {
                            user: res.userName,
                            idCard: res.cardNos ? _cardNos.certNo : res.certNo,
                            bankCard: _cardNos ? _cardNos.cardNo : '',
                            userOnlyId: res.userOnlyId,
                            cardNos: _cardNos,
                            creditBaseAmount: res.creditBaseAmount, //专享额度
                            loanRate: 7.2, // 年利率,
                            loanRepayType: '一次性还本付息', //还款方式
                            prepayment: '按日计息，无手续费',
                            phone: _cardNos ? _cardNos.mobileNo : '',
                            smscode: '',
                            checked: false,
                            isShowBtn: true,
                            isSubmit: true, // true 不可以点击 立即激活
                            isAddCard: _cardNos !== undefined,
                            getSend: {isOkSend: false},
                            time: 60       // 验证码倒计时秒
                        };
                        vueRender();
                    } else {
                        layer.msg(res.msg);
                    }
                },
                error: function(err) {
                    layer.msg(err)
                }
            });

            function vueRender() {
                new Vue({
                    el: '#PxBank',
                    data: _data,
                    methods: {
                        toggleClass: function() {
                            this.checked = !this.checked;
                            checkSubmit();
                        },
                        getSMS: function(){
                            var _this = this;
                            var sendData = {
                                userOnlyId: _this.userOnlyId,
                                userName: _this.user,
                                certNo: _this.idCard,
                                cardNo : _this.bankCard,
                                mobileNo: _this.phone
                            };
                            // 获取验证码
                            $.ajax({
                                url: _url2,
                                dataType: 'jsonp',
                                data: sendData,
                                jsonp: 'jsoncallback',
                                success: function(res) {
                                    console.log('发送短信验证码接口返回结果：', res);
                                    if (res.code === '0000') {
                                        var _phone = _this.phone.substr(0,3) + '****' + _this.phone.substr(-4);
                                        _data.isShowBtn = false;
                                        document.querySelector('#yzmTxt').innerHTML = '验证码已成功发送至' + _phone;
                                        function djs() {
                                            setTimeout(function(){
                                                if (--_data.time > 0) {
                                                    djs()
                                                } else {
                                                    _data.isShowBtn = true;
                                                    _data.time = 60;
                                                }
                                            },1000);
                                        }
                                        djs();
                                    } else {
                                        layer.msg(res.msg);
                                    }
                                },
                                error: function(err) {
                                    console.log(err);
                                }
                            });
                        },
                        addCard: function() {
                            this.isAddCard = !this.isAddCard;
                            this.bankCard = '';
                            this.phone = '';
                            document.querySelector('#phone').value = '';
                            document.querySelector('#bankcard').value = '';
                            checkPhonelength(this.phone);
                        },
                        submitNext: function() {
                            var _this = this;
                            var sendData = {
                                cardNo: _this.bankCard,
                                mobileNo: _this.phone,
                                validCode: _this.smscode, // 验证码
                                userName: _this.user,
                                certNo: _this.idCard,     // 身份证号码_this.bankCard
                                cardNos: _this.cardNos
                            };
                            $.ajax({
                                url: _url3,
                                dataType: 'jsonp',
                                data: sendData,
                                jsonp: 'jsoncallback',
                                success: function(res) {
                                    console.log('发送短信验证码接口返回结果：', res);
                                    if (res.code === '0000') {
                                        location.href = 'hodepage.html'
                                    } else {
                                        layer.msg(res.msg);
                                    }
                                },
                                error: function(err) {
                                    layer.msg(err);
                                }
                            });
                        }
                    }
                });
                document.querySelector('#phone').value = _data.phone ? _data.phone.substr(0,3) + ' **** ' + _data.phone.substr(-4) : '';
                document.querySelector('#bankcard').value = _data.bankCard ? _data.bankCard.substr(0,4) + ' **** **** **** ' + _data.bankCard.substr(-3) : '';
                checkPhonelength(_data.phone);
            }
        function checkSubmit() {
            !_data.getSend.isOkSend || !_data.smscode || !_data.bankCard || !_data.checked ? _data.isSubmit = true : _data.isSubmit = false;
        }
        function getWritePhone(v){
            document.querySelector('#phone').value = v;
            _data.phone = v;
            checkPhonelength(v);
            checkSubmit();
        }
        function getWriteBankCard(v){
            _data.bankCard = v;
            checkSubmit();
        }
        function getWriteSms(v) {
            _data.smscode = v;
            checkSubmit();
        }
        function checkPhonelength(v) {
            v = v.toString();
            if (v.length >= 11) {
                _data.getSend.isOkSend = true;
                checkSubmit();
            } else {
                _data.getSend.isOkSend = false;
            }
        }
    </script>
</body>
</html>