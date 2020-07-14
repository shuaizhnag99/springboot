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
    <link rel="stylesheet" href="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/css/guidepage.css">
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/j/vue.min.js"></script>
    <script src="<%=Constants.get("GLOBAL_SERVER") %>/j/lib/jquery-1.7.2.min.js"></script>
    <script src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/j/layer.js"></script>
</head>
<body>
    <div id="guidepage">
        <header></header>
        <div class="main clearfix">
            <div class="Left fl">
                <div class="clearfix list">
                    <div class="icon_logo fl"></div>
                    <div class="ulli fl">
                        <div>
                            <p class="fs_26 f_bold">进货版</p>
                            <p class="fs_14 border1">仅限用于邮掌柜进货</p>
                        </div>
                        <ul>
                            <li class="li1">
                                <p>
                                    <span>年 利 率：</span>
                                    <span>{{jinhuo.lv}}%</span>
                                </p>
                                <p>
                                    <span>最高额度：</span>
                                    <span>¥{{jinhuo.HighestAmount}}</span>
                                </p>
                                <p>
                                    <span>审批时效：</span>
                                    <span>{{jinhuo.limitation}}</span>
                                </p>
                                <p>
                                    <span>贷款期限：</span>
                                    <span>{{jinhuo.limit}}</span>
                                </p>
                            </li>
                            <li class="li2">
                                <p class="f_bold">●&nbsp;&nbsp;无门槛</p>
                                <p class="f_bold">●&nbsp;&nbsp;无费用</p>
                                <p class="f_bold">●&nbsp;&nbsp;无等待</p>
                            </li>
                            <li><button class="btn" @click="checkInfo">立即激活</button></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="Right fr">
                <div class="clearfix list">
                    <div class="icon_logo fl"></div>
                    <div class="ulli fl">
                        <div>
                            <p class="fs_26 f_bold">现金版</p>
                            <p class="fs_14 border1">提现或用于邮掌柜进货</p>
                        </div>
                        <ul class="clearfix">
                            <li class="li1">
                                <p>
                                    <span>年 利 率：</span>
                                    <span>{{xianjing.lv}}%起</span>
                                </p>
                                <p>
                                    <span>最高额度：</span>
                                    <span>¥{{xianjing.HighestAmount}}</span>
                                </p>
                                <p>
                                    <span>审批时效：</span>
                                    <span>{{xianjing.limitation}}</span>
                                </p>
                                <p>
                                    <span>贷款期限：</span>
                                    <span>{{xianjing.limit}}</span>
                                </p>
                            </li>
                            <li class="li2">
                                <img class="img1" src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/img/icon/3.png" height="144" width="144"/>
                                <p>使用手机扫描上方二维码</p>
                                <p>下载邮掌柜APP</p>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <footer></footer>
        <!--弹窗-->
        <div id="tcBox" v-if="isShowWindow">
            <div class="tcMain">
                <p class="tcHeader clearfix">
                    <a href="javascript:;" class="fr" @click="isShowWindow = false">Ｘ</a>
                    <span class="sp1">温馨提示</span>
                </p>
                <div class="content">
                    <ul v-if="isEligible">
                        <li class="li1">
                            <p v-if="ruleRefId == 1">您的<span class="fc_red">姓名和身份证</span>信息不全，请联系邮政工作人员不全</p>
                            <p class="p2" v-else-if="ruleRefId == 2">您还未添加<span class="fc_red">收货地址</span>，请根据下方路径添加</p>
                            <div v-else>
                                <p>1.您的<span class="fc_red">姓名和身份证</span>信息不全，请联系邮政工作人员不全</p>
                                <p class="p2">2.您还未添加<span class="fc_red">收货地址</span>，请根据下方路径添加</p>
                            </div>
                            <p class="p3">添加路径：店铺管理 > 掌柜中心 > 收货地址 > 添加</p>
                        </li>
                        <li class="li2">
                            <p class="p1">备注：</p>
                            <p>1.如果您按照提示操作无法修改相关信息，请联络当地邮政工作人员在OPC后台进行修改。</p>
                            <p>2.信息完善后，请于次日再进入掌柜贷激活进货专享额度.</p>
                        </li>
                    </ul>
                    <!--不符合-->
                    <div v-else class="notEligible">
                        <div class="clearfix">
                            <img class="fl" src="<%=Constants.get("UHJ_CSS_SERVER") %>/c/my/loan/2017/0525/img/icon/7.png" height="48" width="48"/>
                            <div class="fl notEligibletxt">
                                <h2>您暂时不符合开通条件</h2>
                                <p>请下载掌柜贷APP</p>
                                <p>申请开通<span class="fc_red">借现金</span>吧</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="btn"><button @click="isShowWindow = false">知道了</button></div>
            </div>
        </div>
    </div>
    <script>
        var _data = {
            isShowWindow: false, // 是否显示弹窗
            isEligible: true, // 是否符合开通资格
            ruleRefId: '',   // 空都显示，1显示身份证，2显示收货地址
            jinhuo:{
                lv:'7.2',
                HighestAmount:'10000',
                limitation:'5分钟',
                limit:'最长90天'
            },
            xianjing:{
                lv:'9',
                HighestAmount:'150000',
                limitation:'30分钟--1个工作日',
                limit:'3/6/9/12月'
            }
        }, _url1 = '<%=Constants.get("UHJ_SERVER")%>/pxZgd/queryPXLimit.do';
        new Vue({
            el: '#guidepage',
            data: _data,
            methods: {
                checkInfo: function() {
                    $.ajax({
                        url: _url1,
                        dataType: 'jsonp',
                        jsonp: 'jsoncallback',
                        success: function(res) {
                            console.log('这是批销额度的资质数据：', res);
                            if (res.code === '0000') {
                                if (res.status == 3) {
                                    _data.ruleRefId = res.ruleRefId;
                                    _data.isShowWindow = true;
                                } else if (res.status == 1 ) {
                                    location.href = '<%=Constants.get("UHJ_SERVER")%>/pxZgd/toPXBank.do';
                                } else if (res.status == 0) { // 暂无资质
                                    _data.isShowWindow = true;
                                    _data.isEligible = false;
                                }
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
    </script>
</body>
</html>