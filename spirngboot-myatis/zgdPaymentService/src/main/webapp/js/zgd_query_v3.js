//$(function () {
//	var serverUrl = "//money." + JEND.server.uleUrl;
//	var localUrl = "//localhost:8080";
//	var isLocalTest = true; // 本地测试   如果要上传到生产环境就设成false
//	var URL = isLocalTest ? localUrl : serverUrl;
//	var openDate = new Date();
//	var openTime = +openDate;
//	var openTime1 = +(openDate.format('yyyy-mm-dd ') + '06:59').parseDate();
//	var openTime2 = +(openDate.format('yyyy-mm-dd ') + '17:30').parseDate();
////	if (openTime <= openTime1 || openTime >= openTime2)
//		JEND.load('util.dialog', function () { JEND.util.dialog.alert({ type: 'error', message: '非常抱歉！目前时间段无法借款，请在当日的7:00至17:30之间进行借款。' }); });
//	var iuhjServer = $("#uhj_server").val();
//	////图片
//	//if ($(".zgd-header-rt .zgd-h-tit").length) {
//	//    var js_pro_val = $("#provice").val();
//	//    if (js_pro_val && !js_pro_val.match(/^none$/i)) {
//	//        $(".zgd-header-rt .zgd-h-tit").append('<a href="http://www.ule.com/event/2016/1110/marketing.html" target="_blank"><img style="float: right; margin:-13px 20px 0 0" src="https://secure.ule.com/c/my/loan/2016/1110/i/dkmx.gif"/></a>');
//	//    }
//	//}
//	JEND.load.add('notice', {
//		css: 'c/my/loan/2016/0401/c/notice.css',
//		js: "c/my/loan/2016/0401/j/notice.js"
//	});
//	JEND.load('notice', function () {
//		JEND.notice.initTipBox();
//		JEND.notice.initMoreList();
//	});
//
//
//	var $zgdBtnTabs = $(".zgd-btn-tab");
//	$zgdBtnTabs.find('.tab-but').eq(0).html('现金借款<i class="zgd-iconarr"></i>');
//	$zgdBtnTabs.find('.tab-but').eq(1).html('现金还款<i class="zgd-iconarr"></i>');
//	//进货已出账单 tab
//	JEND.define("JEND.purchase", {
//		init: function () {
//			$zgdBtnTabs.append('<div class="tab-but grey-but">进货已出账单<i class="zgd-iconarr"></i></div>');
//			this.addTable();
//			this.getTableData();
//		},
//		addTable: function () {
//			var tableHtml = ['<div class="borrowOrLend my_purchase_tbl">',
//				'<table cellpadding="0" cellspacing="0">',
//				'<thead>',
//				'<tr>',
//				'<th>账单月份</th>',
//				'<th>还款日</th>',
//				'<th>借款金额</th>',
//				'<th>待还金额</th>',
//				'<th>实还金额</th>',
//				'<th>贷款状态</th>',
//				'<th>操作</th>',
//				'</tr>',
//				'</thead>',
//				'<tbody></tbody>',
//				'</table>',
//				'</div>'];
//			$(".zgd-tab-tbl").append(tableHtml.join(" "));
//		},
//		addTr: function (list) {
//			var $tbody = $(".my_purchase_tbl table tbody");
////			var arrs1 = ['0','1','2','3'];
//			var arrs2 = ['未还款','已还款','已逾期','已取消'];
//			if ($tbody.length) {
//				var html = [];
//				$.each(list, function () {
//					this.display = this.loanStatus == 0 ? 'display:initial' : 'display:none' ;
//					var loanStatusText = arrs2[this.loanStatus] || '';
//					html.push("<tr>");
//					html.push("<td>" + (this.billMonth || '') + "</td>");
//					html.push("<td>" + (this.repayDate || '') + "</td>");
//					html.push("<td>¥" + toFixed(this.loanAmount) + "</td>");
//					html.push("<td class='raAmount' raAmount="+this.raAmount+">¥" + toFixed(this.raAmount) + "</td>");
//					html.push("<td class='rdAmount' rdAmount="+this.rdAmount+">¥" + toFixed(this.rdAmount) + "</td>");
//					html.push("<td>" + loanStatusText + "</td>");
////					html.push("<td><a class='tbl-link btn-pur-query' data-val1='"+this.billMonth+"' data-val2='" + this.repayDate + "'>账单明细</a><a class='tbl-link' style='display: none1'>提前还款</a></td>");
//					html.push('<td><a class="zdmx simsunfont tbl-link repayPlanBtn waitting hideStCancelA" isBill="true">账单明细</a><a class="simsunfont tbl-link repayAllBtn waitting hideRpayA" isBill="true" style='+this.display+'>提前还款</a><input type="hidden" class="dueIds" value='+this.dueIds+'><input type="hidden" name="status" value='+this.loanStatus+'><input type="hidden" class="loanType" name="loanType" value='+this.loanType+'></td>');
//					html.push("</tr>");
//				})
//				$tbody.html(html.join(" "));
////				$tbody.find(".btn-pur-query").bind("click", function () {
////					var $this = $(this);
////					var billMonthVal = $this.attr("data-val1");
////					var repayDateVal = $this.attr("data-val2");
////					JEND.purchase.showRepayBox(billMonthVal,repayDateVal);
////				})
//			}
//		},
//		//显示账单明细弹框
//		showRepayBox: function (data, url, type) {
//			var WCZDisShow,
//				raAmounts,
//				rdAmounts,
//				YCZDisShow;
//			switch (type) {
//				case 'WCZD':
//					WCZDisShow = 'display:none';
//					WCZDyszj = 'display:block'
//					break;
//				case 'YCZD':
//					YCZDisShow = 'display:table-cell';
//					WCZDisShow:'display:block'
//					WCZDyszj = 'display:none'
//					break;
//				default :
//					WCZDisShow = 'display:table-cell'
//			}
//			JEND.load('notice', function () {
//				var ynAlert = JEND.notice.ynAlert;
//				$.ajax({
//					url: url,
//					data: data,
//					dataType: 'jsonp',
//					jsonp: "jsoncallback",
//					success: function (result) {
//						console.log(result, ' 账单明细返回值')
//						var res = JSON.parse(result);
//						var arrs1 = ['1','10','5','55','21','30'];
//						var arrs2 = ['已还清','申请','还款中','已逾期','已取消','申请'];
//						
//						if (res.code == "0000") {
//							var html = [];
//							html.push('<div class = "yn-title">');
//							html.push('<i class = "yn-box-close"></i><span>账单明细</span>');
//							html.push('</div>');
//							html.push('<style>.my-box::-webkit-scrollbar { width: 5px; }.my-box::-webkit-scrollbar-track { background-color: #f1f1f1; }.my-box::-webkit-scrollbar-thumb { background-color: #666666; }</style>');
//							html.push('<div style="max-height: 390px;overflow: auto;font-size: 0;" class="my-box"><table cellpadding="0" cellspacing="0" style="width: 100%;font-size: 13px;">');
//							html.push('<thead>');
//							html.push('<tr style="height: 30px;">');
//							//3)订单号、支付时间、订单金额、还款金额、订单状态、已还总计
//							if (type == 'YCZD') { // 已出账单弹窗表格
////								html.push('<th>借据号</th>');
//								html.push('<th>签收日期</th>');
//								html.push('<th>借款日期</th>');
//								html.push('<th>借款本金</th>');
//								html.push('<th>借款利息</th>');
//								html.push('<th>罚息</th>');
//								html.push('<th>利率</th>');
//								html.push('<th>贷款状态</th>');
//							} else { // 未出账单弹窗表格
//								html.push('<th>订单号</th>');
//								html.push('<th>支付时间</th>');
//								html.push('<th>签收时间</th>');
//								html.push('<th>签收金额</th>');
////								html.push('<th style='+WCZDisShow+'>应还金额</th>');
////								html.push('<th style='+WCZDisShow+'>已还金额</th>');
//							}
//							html.push('</tr>');
//							html.push('</thead>');
//							html.push('<tbody> ');
//							var total = 0;
//							// 1 已结清  0 还款中  2 逾期
//							var statusObj = {
//								"1": "取消",
//								//"0": "还款中",
//								//"2": "逾期",
//								"7":"签收"							}
//							if(res.resultList) {
//								$.each(res.resultList, function () {
//									html.push('<tr style="height: 30px; text-align: center">');
//									if (type == 'YCZD') { // 已出账单
//										raAmounts = raamount;
//										rdAmounts = rdamount;
//										var loanStatusText = arrs2[arrs1.indexOf(this.status + '')] || '';
////										html.push("<td>" + (this.orderId || this.dueId) + "</td>");
//										html.push("<td>" + (this.createTime || this.confirmDate) + "</td>");
//										html.push("<td>" + (this.loanTime || this.payDate) + "</td>");
//										html.push("<td>¥" + toFixed(this.loanAmount) + "</td>");
//										html.push("<td>¥" + toFixed(this.inter) + "</td>");
//										html.push("<td>¥" + toFixed(this.faxi) + "</td>");
//										html.push("<td>" + this.interRate + "</td>");
//										html.push("<td>" + loanStatusText + "</td>");
//									}else { // 未出账单
//										raAmounts = res.totalraAmount;
//										rdAmounts = res.totalrdAmount;
//										html.push("<td>" + (this.orderId || this.dueId) + "</td>");
//										html.push("<td>" + (this.createTime || this.confirmDate) + "</td>");
//										html.push("<td>" + (this.loanTime || this.payDate) + "</td>");
//										html.push("<td>¥" + toFixed(this.loanAmount) + "</td>");
////										html.push("<td style="+ WCZDisShow +">¥" + toFixed(this.loanAmount) + "</td>");
////										html.push("<td style="+WCZDisShow+">¥" + toFixed(this.rdAmount) + "</td>");
//									}
//									html.push("</tr>");
//									total += parseFloat(this.rdAmount || 0);
//								})
//							}else{
//								html.push('<tr style="height: 30px; text-align: center">');
//								html.push("<td colspan='5'>没有数据！</td>");
//								html.push("</tr>");
//							}
//							html.push(' </tbody>');
//							html.push('</table>');
//							html.push('</div>');
//							html.push('<p><span style="float: right; margin-right: 20px;font-size: 13px;">待收金额总计：¥' + toFixed(raAmounts) + '</span><span style="float: right; margin-right: 20px;font-size: 13px;">已收金额总计：¥' + toFixed(rdAmounts) + '</span></p>');
////							html.push('<p style='+WCZDisShow+'><span style="float: right; margin-right: 20px;font-size: 13px;">已还总计：¥' + toFixed(rdamount) + '</span></p>');
//							ynAlert.show(650, 500, html.join("　"), function () {
//							})
//						} else {
//							JEND.load('util.dialog', function () {
//								JEND.util.dialog.alert(res.msg);
//							});
//						}
//					}
//				});
//			});
//		},
//		getTableData: function () {
//			$.ajax({
//				url: URL + "/lendvps/pixiao/queryPxOrder.action",
//				data: null,
//				dataType: 'jsonp',
//				jsonp: "jsoncallback",
//				success: function (result) {
//					console.log(result,'     进货已出账单数据')
//					var res = JSON.parse(result);
//					JEND.purchase.addTr(res.orderList || []);
//				}
//			});
//		}
//	})
//	JEND.purchase.init();
//	//进货未出账单数据
//	JEND.define('moChuZd',{
//		init : function () {
//			$zgdBtnTabs.append('<div class="tab-but grey-but">进货未出账单<i class="zgd-iconarr"></i></div>');
//			this.addTable();
//			this.getTableData();
//		},
//		addTr: function (data) {
//			var $tbody = $("#myMochuzdTbl table tbody");
//			var arrs1 = ['1','10','5','55','21','30'];
//			var arrs2 = ['已还清','申请','还款中','已逾期','已取消','申请'];
//			if ($tbody.length) {
//				var sum = toFixed(data.totalAmount),
//					billMonth = '';
//				var html = [];
//				var list = data.billList || [];
//				$.each(list, function () {
//					this.display = this.status == 5 ? 'display:initial' : 'display:none';
//					var loanStatusText = arrs2[arrs1.indexOf(this.status + '')] || '';
////					var loanStatusText = arrs2[this.status] || '';
//					html.push("<tr>");
//					html.push("<td>" + (this.dueId || '') + "</td>");
////					html.push("<td>" + (this.payDate || '') + "</td>");
//					html.push("<td>" + (this.confirmDate || '') + "</td>");
//					html.push("<td>¥" + toFixed(this.orderAmount) + "</td>");
//					html.push("<td>" + this.interRate + "</td>");
//					html.push("<td>" + loanStatusText + "</td>");
////					html.push("<td><a class='tbl-link btn-pur-query' data-val1='"+this.billMonth+"' data-val2='" + this.repayDate + "'>账单明细</a><a class='tbl-link' style='display: none1'>提前还款</a></td>");
//					html.push('<td><a class="zdmx simsunfont tbl-link repayPlanBtn waitting hideStCancelA" isNotBill="true">订单明细</a><a class="simsunfont tbl-link repayAllBtn waitting hideRpayA" isNotBill="true" style='+this.display+'>提前还款</a><input type="hidden" class="dueIdInput"  value='+this.dueId+'><input type="hidden" name="status" value='+this.status+'><input type="hidden" class="loanType" name="loanType" value='+this.loanType+'></td>');
//					html.push("</tr>");
//				})
//				if(html.length){
//					html.push('<tr><td></td><td></td><td colspan="5" align="right">合计金额 : ¥'+sum+'</td></tr>');
//					$tbody.html(html.join(" "));
//				}else{
//					$tbody.html("<td colspan='5'>没有数据！</td>");
//				}
//			}
//		},
//		addTable: function () {
//			var tableHtml = ['<div class="borrowOrLend my_mochuzd_tbl" id="myMochuzdTbl">',
//				'<table cellpadding="0" cellspacing="0">',
//				'<thead>',
//				'<tr>',
//				'<th>借据号</th>',
////				'<th>签收日期</th>',
//				'<th>借款日期</th>',
//				'<th>金额</th>',
//				'<th>利率</th>',
//				'<th>贷款状态</th>',
//				'<th>操作</th>',
//				'</tr>',
//				'</thead>',
//				'<tbody></tbody>',
//				'</table>',
//				'</div>'];
//			$(".zgd-tab-tbl").append(tableHtml.join(" "));
//		},
//		getTableData : function () {
//			$.ajax({
//				url: URL + "/lendvps/pixiao/queryPxUnOutBill.action",
//				data: null,
//				dataType: 'jsonp',
//				jsonp: "jsoncallback",
//				success: function (result) {
////					console.log(result,'  进货未出账单数据')
//					var res = JSON.parse(result);
//					JEND.moChuZd.addTr(res);
//				}
//			});
//		}
//	})
//	JEND.moChuZd.init();
//	//额度到期提前提醒
//	JEND.define("JEND.balanceTip", {
//		showBox: function (obj) {
//			if (obj.expireRemind && obj.expireRemind.remindCount) {
//
//				var payTopBalanceHtml = [
//					'<div class="pop-edu">',
//					'	<div class="popbox-bg"></div>',
//					'   <div class="content-box">',
//					'		<div class="pop-head"><span class="d-close" style="width: 25px;height: 25px;display: block;float: right; cursor: pointer; margin-right: 8px; background: url(//i0.' + JEND.server.uleUrl + '/c/my/loan/2016/0401/images/ww_icon.jpg) -116px -53px no-repeat"></span>额度到期提醒</div>',
//					'		<div class="pop-content" style="min-height: 60px">',
//					'   	 	<div class="t">您的掌柜贷额度将于' + obj.expireRemind.expireDate + '到期</div>',
//					'    		<div class="t">根据您最近的邮掌柜经营业绩，本月的预估额度为' + obj.expireRemind.yuguAmount + '元。</div>',
//					' 		</div>',
//					'		<div style="color:#e46d11;text-indent: 60px;margin-top: 20px;">',
//					'			<p>温馨提示：</p>',
//					'			<p>1、额度不够用？请在额度到期前提升邮掌柜经营业绩；</p>',
//					'			<p>2、到期后重新申请以实际审批额度为准！</p>',
//					'		</div>',
//					' 	</div>',
//					'</div>'
//				];
//				$("body").append(payTopBalanceHtml.join(''));
//				$(".pop-edu .d-close").bind("click", function () {
//					$('.pop-edu').remove()
//				})
//				this.updateDate(obj.expireRemind.remindCount);
//			}
//		},
//		//修改数据
//		updateDate: function (remindCount) {
//			var url = URL + "/lendvps/uhj/zgd_updateExpireRemind.do?jsoncallback=?";
//			var data = {
//				remindCount: remindCount
//			};
//			$.getJSON(url, data, function (res) {
//				//修改数据返回值不处理
//			})
//		}
//
//	});
//	//额度到期“确认申请”弹窗
//	JEND.define("JEND.sureApplyTip",{
//		showBox:function (flag) {
//			var str = "",bgStr="";
//			if (flag) {
//				str = "恭喜您，新的预估额度高于上一年的贷款额度，确认申请后，实际额度以审批结果为准！";
//				bgStr='style="background:#fff url(//i0.' + JEND.server.uleUrl + '/c/my/loan/2016/0401/images/1228_011.jpg) no-repeat"';
//			} else {
//				str = "您新的预估额度低于上一年的贷款额度，额度不够用？建议提升店铺经营业绩后再申请，实际额度以审批结果为准！";
//			}
//			var payTopBalanceHtml = [
//				'<div class="pop-edu">',
//				'	<div class="popbox-bg" ></div>',
//				'   <div class="content-box" '+bgStr+'>',
//				'		<div class="pop-head"><span class="d-close" style="width: 25px;height: 25px;display: block;float: right; cursor: pointer; margin-right: 8px; background: url(//i0.' + JEND.server.uleUrl + '/c/my/loan/2016/0401/images/ww_icon.jpg) -116px -53px no-repeat"></span>额度到期提醒</div>',
//				'		<div class="pop-content" style="min-height: 60px">',
//				'   	 	<div class="t">' + str + '</div>',
//				'    <div class="btn-war" style="margin-top: 30px;">',
//				'        <div class="btn-l">取消申请</div>',
//				'        <div class="btn-r">确认申请</div>',
//				'    </div>',
//				' 		</div>',
//				' 	</div>',
//				'</div>'
//			];			$("body").append(payTopBalanceHtml.join(''));
//			$(".pop-edu .d-close").bind("click",function () {
//				$('.pop-edu').remove()
//			})
//			$('.btn-l').click(function () {
//				$(this).parents('.pop-edu').remove()
//			})
//			$('.btn-r').click(function () {
//				$(this).parents('.pop-edu').remove()
//				JEND.util.dialog.showLoading({});
//				var d = '';
//				$.getJSON(main.api.immediateApply, d, function (data) {
//					JEND.util.dialog.close();
//					var the = JSON.parse(data);
//					the.code=="000000"&&(the.code="000001");
//					main.returnCode(the.code);
//				})
//			})
//		}
//	});
//
//	$('#expo-scroll').kvScroll({ type: 'scroll-loop', autoPlay: true, loop: true, time: 3000 });
//
//	var repayDateB = 0;
//	var uleUrl = JEND.server.uleUrl;
//	var $wantPay = $('.lendAccOptBtn');
//	var main = {
//		api: {
//			qualificationsCheck: 'https://money.' + uleUrl + '/lendvps/renew/qualificationsCheck.do?jsoncallback=?',
//			immediateApply: 'https://money.' + uleUrl + '/lendvps/renew/immediateApply.do?jsoncallback=?'
//		},
//		init: function () {
//			JEND.load('util.dialog', function () {
//				$wantPay.click(function () {
//					main.wantPay();
//				})
//			});
//		},
//		wantPay: function () {
//			JEND.util.dialog.showLoading({});
//			var d = '';
//			$.getJSON(main.api.qualificationsCheck, d, function (data) {//
//				JEND.util.dialog.close();
//				var the = JSON.parse(data);
//				if (the.limit) {
//					$limit = the.limit;
//				}
//				if (the.code == '000000') {
//					window.location.href = 'https://money.' + uleUrl + '/lendvps/uhj/yczgd_toLendPage';
//				} else if (the.code == '100003') {
//					main.popNurmal($limit)
//
//				}
//
//				else if (the.code == '100004') {//大于1万 ，大于上一期
//					//JEND.sureApplyTip.showBox(1);
//					main.popNurmal(the.limit, the.code);
//				}
//				else if (the.code == '100005') {//大于1万 ，小于上一期
//					//JEND.sureApplyTip.showBox(0);
//					main.popNurmal(the.limit, the.code);
//				}
//				else {
//					main.returnCode(the.code);
//				}
//			})
//		},
//		popNurmal: function (price,code) {//静态页面弹框
//			var payTopBalanceHtml = [
//				'<div class="pop-edu">',
//				'<div class="popbox-bg"></div>',
//				'   <div class="content-box">',
//				'<div class="pop-head">额度过期提醒</div>',
//				'<div class="pop-content">',
//				'    <div class="t">您的额度已过期！不能申请新的借贷，需要重新申请额度。根据您最近邮掌柜经营业绩，新的预估额度为<span>' + price + '</span>元</div>',
//				'    <div class="b">温馨提示：如果您觉得额度不够，请努力提升店铺经营业绩（进销存、代购、批发、充值缴费等），再来申请。</div>',
//				'    <div class="btn-war">',
//				'        <div class="btn-l">暂不申请</div>',
//				'        <div class="btn-r">立即申请</div>',
//				'    </div>',
//				' </div>',
//				' </div>',
//				'</div>'
//			];
//			$("body").append(payTopBalanceHtml.join(''));
//			$('.btn-l').click(function () {
//				$(this).parents('.pop-edu').remove()
//			})
//			$('.btn-r').click(function () {
//				$(this).parents('.pop-edu').remove()
//				if (code) {
//					JEND.sureApplyTip.showBox(code == '100004' ? 1 : 0);
//				} else {
//					JEND.util.dialog.showLoading({});
//					var d = '';
//					$.getJSON(main.api.immediateApply, d, function (data) {
//						JEND.util.dialog.close();
//						var the = JSON.parse(data);
//						the.code == "000000" && (the.code = "000001");//000000 和 000001提示相同
//						main.returnCode(the.code);
//					})
//				}
//			})
//		},
//		returnCode: function (d) {
//			d = parseInt(d);
//			returnCod = d;
//			var list = {
//				999999: '系统异常',
//				900001: '用户未登陆',
//				900002: '用户账户状态异常',
//				000001: '尊敬的掌柜您好，你的申请已经提交，大概需要一个工作日，请耐心等待结果，如有疑问请拨打客服热线  11185-4。',
//				000000: '业务处理成功',
//				100001: '账户冻结',
//				100002: '您的额度已过期，不能申请新的借款，请把所有贷款结清后重新申请额度！',
//				100003: '账户超期但是欠款还清，允许重新申请资格',
//				200004: '年龄超过55岁拒绝申请',
//				200005: '身份证已过有效期，请重新上传身份证正反面照片！',
//				200006: '掌柜姓名有误，请重新实名认证!'
//			}
//			if (d == '000000') {
//				main.popAlert(list[d], true, 'p1')
//			} else if (d == '100002') {
//				main.popAlert(list[d], false, 'p2')
//			}
//
//
//			else if (d == '000001') {
//				main.popAlert(list[d], true, 'p3')
//			} else if (d == '200005' || d == '200006') {
//				main.popAlert(list[d], true, 'p2', 'chao')
//			} else {
//				if (list[d]) {
//					main.popAlert(list[d], true, 'p2')
//				} else {
//					main.popAlert(list['000001'], true, 'p2')
//				}
//			}
//		},
//		popAlert: function (text, isDouble, pj, isChao) {//单双按钮弹框p1笑脸，p2哭脸，p3提示
//			var p1 = '取消'
//			var p2 = '去结清'
//			var html = [];
//			html.push('<div class="pop-edu"><div class="popbox-bg"></div>')
//			html.push('<div class="content-pt"><div class="popbox-title"><h4>提示信息</h4><span class="close close-pop" id="cloce-pop" title="关闭浮层">关闭</span></div>')
//			html.push('<div class="pt-content"><div class="' + pj + '"></div><div class="content-r">' + text + '</div></div>')
//			html.push('<div class="btn-war">')
//			if (isDouble) {
//				html.push('<div class="btn-sp"><span></span>我知道了</div>')
//			} else {
//				html.push('<div class="btn-zu">')
//				html.push('<div class="btn-l"><span></span>' + p1 + '</div>')
//				html.push('<div class="btn-r"><span></span>' + p2 + '</div>')
//				html.push('</div>')
//			}
//			html.push('</div>')
//			html.push('</div>')
//			$("body").append(html.join(''));
//			$('.btn-l ,#cloce-pop ,.btn-sp').click(function () {
//				$(this).parents('.pop-edu').remove()
//			})
//			if (isChao == 'chao') {
//				$('.btn-sp').click(function () {
//					window.open('https://money.' + uleUrl + '/lendvps/uhj/zgd_toZgdPage')
//				})
//			}
//			if (returnCod == '100002' || returnCod == '100003') {
//				$('.pt-content .content-r').append('<div>重新申请预估额度：' + $limit + '元</div>')
//			}
//			$('.btn-r').click(function () {
//				var dueIdL = [];
//				JEND.util.dialog.showLoading({});
//				$(this).parents('.pop-edu').remove()
//				$('tbody').find('tr').each(function () {
//					if ($(this).find('input[name=status]').val() == 0) {
//						dueIdL.push($(this).find('.dueIdInput').val())
//					}
//				})
//				JEND.repayAllList.init({
//					dueId: dueIdL[0]
//				});
//			})
//		}
//	}
//	main.init();
//	//金额保留两位小数，三位隔开
//	var toFixed = function (a, sign) {
//		a = Number(a || 0) || 0;
//		if (a < 0) {
//			a = 0;
//		}
//		var price = a.toFixed(2);
//		var arr = (price + "").split(".");
//		var nowtt = arr[0].split("");
//		nowtt = nowtt.reverse();
//		nowtt = nowtt.join("").replace(/(.{3})/gi, "$1,");
//		nowtt = ((nowtt.split("")).reverse().join("")).replace(/^,/g, "");
//
//		return (sign ? sign : "") + nowtt + "." + arr[1];
//	}
//
//	$('.zgd-btn-tab').find('.tab-but').live('click', function () {
//		$(this).addClass('red-but').removeClass('grey-but').siblings().removeClass('red-but').addClass('grey-but');
//		$('.borrowOrLend').eq($(this).index()).show().siblings().hide();
//	})
//
//	JEND.define("JEND.list", {
//		init: function () {
//			var m = this;
//			m.loadData(function () {
//				m.createHtml();
//			});
//		},
//		opts: {
//			"restype": "",
//			"moduleKeys": ""
//		},
//		loadData: function (callback) {
//			var m = this;
//			// 现金借款 数据
//			$.ajax({
//				url: $("#uhj_server").val() + "/uhj/yczgd_queryDues.action",
//				data: m.opts,
//				dataType: 'jsonp',
//				jsonp: "jsoncallback",
//				success: function (data) {
//					//console.log(data)
//					if (data.code == '0000') {
//						m.data = data || {};
//						callback && callback();
//					}
//				},
//				error: function (o) {
//					callback && callback();
//				}
//			});
//			//m.data ={"list":[{"dueId":11,"drawTime":"2015/11/17","loanAmount":"1000","restPrincipal":"1000","interRate":"686.0%","status":"4"},{"dueId":22,"drawTime":"2015/11/17","loanAmount":"900.33","restPrincipal":"900.33","interRate":"10","status":"1"}],"code":"0000","msg":"success"}
//		},
//		createHtml: function () {
//			var m = this, htmlTem = m.htmlTem.html1;
//			m.$Box = $(".zgd-tab-tbl .my_borrow_tbl tbody");
//			//console.log(m.data)
//			var html = [], data = (m.data || {}).list || [];
//			var status = { "0": "还款中", "1": "已还清", "2": "已逾期", "3": "已取消", "4": "待放款" };
//			var iStatusCss = { "0": "zgd-status-b", "1": "zgd-status-g hideRepayOpt", "2": "zgd-status-r", "3": "zgd-status-b hideRepayOpt hideBtnStatusCancel", "4": "zgd-status-b waitting-st" };
//			$.each(data, function () {
//				var t = this;
//				//console.log(data)
//				t.iStatusCss = iStatusCss[t.status] || "";
//				t.iStatus = status[t.status] || "";
//				t.status = t.status || "";
//				t.loanAmount = toFixed(t.loanAmount);
//				t.restPrincipal = toFixed(t.restPrincipal);
//				//提前还款的链接（状态为还款中、逾期的时候显示）
//				t.display = "display:none";
//				if (t.status == "0" || t.status == "2") {
//					t.display = "";
//				}
//				t.amountType = t.orderTypr == "9121" ? '现金' : '进货';
//				html.push(htmlTem.substitute(t))
//			});
//			m.$Box.html(html.join(""));
//
//			$(".waitting-st").parents('tr').find('a.waitting').remove();
//			$(".hideRepayOpt").parents('tr').find('a.hideRpayA').remove();
//			$(".hideBtnStatusCancel").parents('tr').find('a.hideStCancelA').remove();
//		},
//		htmlTem: function () {
//			var m = this;
//			var arr1 = [], arr2 = [];
//			arr1.push('<tr>');
//			arr1.push('  <td><a class="zgd_viewContract_icon" href="yczgd_viewContract.action?dueId={dueId}" target="_balnk"></a></td>');
//			arr1.push('  <td>{drawTime}</td>');
//			arr1.push('  <td>{beginEndTime}</td>');
//			arr1.push('  <td>￥{loanAmount}</td>');
//			arr1.push('  <td>￥{restPrincipal}</td>');
//			arr1.push('  <td>{shouldRepay}</td>');
//			arr1.push('  <td>{amountType}</td>');
//			arr1.push('  <td><div class="simsunfont zgd-icons zgd-status {iStatusCss}">{iStatus}</div></td>');
//			arr1.push('  <td style="text-align:center;">');
//			//arr1.push('    <a class="simsunfont tbl-link" href="uhj/zgd_viewContract.action?dueId={dueId}" target="_balnk">查看合同</a>');
//            /*
//             for(var i=0;i<m.data.list.length;i++){
//             if(m.data.list[i].status=="0"||m.data.list[i].status=="2"){
//             arr1.push('    <a class="simsunfont tbl-link repayPlanBtn waitting hideStCancelA">还款查询</a>');
//             }
//             }
//             */
//            /*arr1.push('    <a class="simsunfont tbl-link repayOneBtn waitting hideRpayA">立即还款</a>');*/
//			arr1.push('    <a class="zdmx simsunfont tbl-link repayPlanBtn waitting hideStCancelA"style="{display}">还款查询</a>');
//			arr1.push('    <a class="simsunfont tbl-link repayAllBtn waitting hideRpayA" style="{display}">提前还款</a>');
//			arr1.push('    <input type="hidden" class="dueIdInput" value="{dueId}"/>');
//			arr1.push('    <input type="hidden" name="status" value="{status}"/>');
//			arr1.push('  </td>');
//			arr1.push(' </tr>');
//
//			return {
//				html1: arr1.join(" "),
//				html2: arr2.join(" ")
//			};
//		}()
//	}, function () {
//		JEND.list.init();
//	});
//
//	//还款记录
//	JEND.define("JEND.listRepay", {
//		init: function () {
//			var m = this;
//			m.loadData(function () {
//				m.createHtml();
//			});
//		},
//		opts: {
//			"restype": "",
//			"moduleKeys": ""
//		},
//		loadData: function (callback) {
//			var m = this;
//			$.ajax({
//				url: $("#uhj_server").val() + "/uhj/yczgd_queryPaymentHistory.action",
//				data: m.opts,
//				dataType: 'jsonp',
//				jsonp: "jsoncallback",
//				success: function (data) {
////					console.log(data,'还款记录')
//					if (data.code == '0000') {
//						m.data = data || {};
//						callback && callback();
//					}
//				},
//				error: function (o) {
//					callback && callback();
//				}
//			});
//			//m.data ={"list":[{"dueId":11,"tranzTime":"2015/11/17","tranzAmount":"1000","remark":"正常还款"},{"dueId":222,"tranzTime":"2015/11/17","tranzAmount":"1000","remark":"提前结清"}],"code":"0000","msg":"success"}
//			callback && callback();
//		},
//		createHtml: function () {
//			var m = this, htmlTem = m.htmlTem.html1;
//			m.$Box = $(".zgd-tab-tbl .my_repay_tbl tbody");
//			var html = [], data = (m.data || {}).list || [];
//			$.each(data, function () {
//				var t = this;
//				t.tranzAmount = toFixed(t.tranzAmount);
//				html.push(htmlTem.substitute(t));
//
//			})
//			m.$Box.html(html.join(""));
//
//		},
//		htmlTem: function () {
//			var arr1 = [], arr2 = [];
//			arr1.push('<tr>');
//			arr1.push('  <td>{dueId}</td>');
//			arr1.push('  <td>{tranzTime}</td>');
//			arr1.push('  <td>￥{tranzAmount}</td>');
//			arr1.push('  <td>{remark}</td>');
//			arr1.push(' </tr>');
//
//			return {
//				html1: arr1.join(" "),
//				html2: arr2.join(" ")
//			};
//		}()
//	}, function () {
//		JEND.listRepay.init();
//	});
//
//
//
//	JEND.define("JEND.balance", {
//		init: function () {
//			var m = this;
//			m.loadData();
//		},
//		opts: {
//			"restype": "",
//			"moduleKeys": ""
//		},
//		loadData: function (callback) {
//			var m = this;
//			$.ajax({
//				url: $("#uhj_server").val() + "/uhj/yczgd_accOverView",
//				data: m.opts,
//				dataType: 'jsonp',
//				jsonp: "jsoncallback",
//				success: function (data) {
//					if (data.code == '0000') {
//						//额度到期提前提醒
//						if (data.alert == 3000) {
//							JEND.balanceTip.showBox(data);
//							return;
//						}
//						if (data.alert == 1000) {
//							var info = '因为您使用掌柜贷的频率较少，额度已降低，如要恢复，请立即支用！';
//							JEND.load('util.dialog', function () {
//								JEND.util.dialog.alert({ title: '尊敬的掌柜:', message: info });
//							});
//						}
//						if (data.alert == 2000) {
//							var info = '赶快使用掌柜贷贷款吧!如长期不使用额度会降低哟！快去支用吧！';
//							JEND.load('util.dialog', function () {
//								JEND.util.dialog.alert({ title: '尊敬的掌柜:', message: info });
//							});
//						}
//						m.data = data || {};
//						$('.availBalance').html('￥' + m.data.availBalance.toFixed(2));
//						$('.zgd-h-total-lim').html('<span>总额度</span><span class="tot-sp">￥' + m.data.creditLimit.toFixed(2) + '</span><span>冻结额度</span><span class="tot-sp freezeLimit">￥' + m.data.freezeLimit.toFixed(2) + '</span>');
//						$('.recentRepayAmt').html('￥' + m.data.recentRepayAmt.toFixed(2));
//						if (m.data.fixedRepayDate) {
//							$('.zgd-h-total-date').html('还款日：<span>每月' + m.data.fixedRepayDate + '日</span>');
//							repayDateB = m.data.fixedRepayDate;
//						} else {
//							$('.zgd-h-total-date').html('还款日：<span>--</span>');
//						}
//						if (m.data.isOverDue == 'Y') {
//							$('.isOverDue-i').show();
//						} else {
//							$('.isOverDue-i').hide();
//						}
//						callback && callback();
//					}
//				},
//				error: function (o) {
//					callback && callback();
//				}
//			});
//
//		}
//	}, function () {
//		JEND.balance.init();
//	});
//
//	var sumAmt = 0,
//		repayPrincipal = 0,
//		planIds = '',
//		isShowBtnA = '',
//		isShowBtnO = '',
//		advInter = 0,
//		advPrincipal = 0;
//
//	//立即还款加载
//	JEND.define("JEND.repayOneList", {
//		init: function (data) {
//			var m = this;
//			m.loadData(data, function () {
//				m.createHtml();
//			});
//		},
//		opts: {
//			"restype": ""
//		},
//		loadData: function (data, callback) {
//			data = data || {};
//			var m = this;
//			$.ajax({
//				url: $("#uhj_server").val() + "/uhj/yczgd_queryShouldPlans.action",
//				data: $.extend(m.opts, data),
//				dataType: 'jsonp',
//				jsonp: "jsoncallback",
//				success: function (data) {
//					if (data.code == '0000') {
//						m.data = data || {};
//						if (m.data.sumRepayAmt == 0) {
//							$('.popbox-bindcard-txt').html("尚未到本期还款日，您的还款日是每月" + repayDateB + "日，如需提前还款，请点击提前还款。");
//							$('.popbox-msgtips').show();
//							setPopboxBg();
//							$('.popbox-bindcard-txt').parents('.popbox-center').css({ 'margin-top': -$('.popbox-bindcard-txt').parents('.popbox-center').height() / 2 });
//						} else {
//							callback && callback();
//						}
//					}
//				},
//				error: function (o) {
//					//callback && callback();
//				}
//			});
//            /*m.data ={"list":[{"index":"1","princ":"0","endDate":"2015-12-04","inter":"3.6","status":"4"},
//             {"index":"2","princ":"6000","endDate":"2016-01-04","inter":"37.2","status":"1"},
//             {"index":"2","princ":"6000","endDate":"2015/12/17","inter":"37.2","status":"0"}
//             ],"planIds":["81120151202100000087","81120151202100000088","81120151202100000089"],"sumRepayAmt":"2010","sumPrincipal":"2000","sumInter":"10","sumForfeit":"10","code":"0000","msg":"success"
//             }
//
//             callback && callback();*/
//		},
//		createHtml: function () {
//			var m = this, htmlTem = m.htmlTem.html1, htmlTem2 = m.htmlTem.html2;
//			m.$Box = $(".pp_repayOne_tbl tbody");
//			m.$Box2 = $(".repayOneDet ul");
//			var html = [], html2 = [], data = (m.data || {}).list || [];
//			var status = { "0": "还款中", "1": "已还清", "2": "已逾期", "3": "已取消", "4": "待放款" };
//			var iStatusCss = { "0": "zgd-status-b", "1": "zgd-status-g", "2": "zgd-status-r", "3": "zgd-status-b", "4": "zgd-status-b" };
//			$.each(data, function () {
//				var t = this;
//				t.iStatusCss = iStatusCss[t.status] || "";
//				t.iStatus = status[t.status] || "";
//				t.oInter = toFixed(t.inter);
//				t.oPrinc = toFixed(t.princ);
//				html.push(htmlTem.substitute(t));
//			})
//			sumAmt = m.data.sumRepayAmt;
//			repayPrincipal = m.data.sumPrincipal;
//			planIds = m.data.planIds;
//			isShowBtnO = m.data.isShowBtn;
//
//			m.data.sumRepayAmt = toFixed(m.data.sumRepayAmt);
//			m.data.sumPrincipal = toFixed(m.data.sumPrincipal);
//			m.data.sumInter = toFixed(m.data.sumInter);
//			m.data.sumForfeit = toFixed(m.data.sumForfeit);
//
//
//			html2.push(htmlTem2.substitute(m.data));
//
//			m.$Box.html(html.join(""));
//			m.$Box2.html(html2.join(""));
//			if (isShowBtnO == 'Y') {
//				$('.popbox-payOne').find('.oprateNotAllow').addClass('confirmPayOneBtn').removeClass('oprateNotAllow');
//			} else {
//				$('.popbox-payOne').find('.confirmPayOneBtn').addClass('oprateNotAllow').removeClass('confirmPayOneBtn');
//			}
//
//			$('.popbox-payOne').show();
//			setPopboxBg();
//			$('.popbox-payOne').find('.popbox-center').css({ 'margin-top': -$('.popbox-payOne').find('.popbox-center').height() / 2 });
//
//		},
//		htmlTem: function () {
//			var arr1 = [], arr2 = [];
//
//			arr1.push('<tr>');
//			arr1.push('  <td>{endDate}</td>');
//			arr1.push('  <td class="font-YaHei3">￥{oInter}</td>');
//			arr1.push('  <td class="font-YaHei3">￥{oPrinc}</td>');
//			arr1.push('  <td><div class="simsunfont zgd-icons zgd-status {iStatusCss}">{iStatus}</div></td>');
//			arr1.push(' </tr>');
//
//			arr2.push('');
//			arr2.push('<li class="total-li">');
//			arr2.push('		<p>应还款总额</p>');
//			arr2.push('		<p class="repay-m">￥{sumRepayAmt}</p>');
//			arr2.push('</li>');
//			arr2.push('<li><p class="sign">=</p></li>');
//			arr2.push('<li>');
//			arr2.push('		<p>本金</p>');
//			arr2.push('		<p class="repay-m">{sumPrincipal}</p>');
//			arr2.push('</li>');
//			arr2.push('<li><p class="sign">+</p></li>');
//			arr2.push('<li>');
//			arr2.push('		<p>利息</p>');
//			arr2.push('		<p class="repay-m">{sumInter}</p>');
//			arr2.push('</li>');
//			arr2.push('<li><p class="sign">+</p></li>');
//			arr2.push('<li>');
//			arr2.push('		<p>罚金</p>');
//			arr2.push('		<p class="repay-m">{sumForfeit}</p>');
//			arr2.push('</li>');
//
//			return {
//				html1: arr1.join(" "),
//				html2: arr2.join(" ")
//			};
//		}()
//	}, function () {
//
//	});
//
//	//立即还款
//	$('.repayOneBtn').live('click', function () {
//		JEND.repayOneList.init({
//			dueId: $(this).parent('td').find('input.dueIdInput').val()
//		});
//	})
//	//确认立即还款
//	$('.confirmPayOneBtn').live('click', function () {
//		var dataIn = {
//			'sumAmt': sumAmt,
//			'planIds': planIds.toString(),
//			'remark': 'type1',
//			'repayPrincipal': repayPrincipal
//		}
//		$.ajax({
//			url: $("#uhj_server").val() + "/uhj/yczgd_initZGDBill.action",
//			data: dataIn,
//			dataType: 'jsonp',
//			jsonp: "jsoncallback",
//			success: function (data) {
//				if (data.code == '0000') {
//
//					data = data || {};
//					if (data.remark == dataIn.remark) {
//
//						var dataPay = {
//							'userId': data.userOnlyId,
//							'bigOrderNo': data.bigOrderNo,
//							'orderAmount': data.orderAmount,
//							'creditRepayId': data.bigOrderNo,
//							'signParam': 'zgd',
//							'orderType': 4102
//						}
//						finalPay(dataPay);
//
//					} else {
//						$('.popbox').hide();
//						$('.popbox-bindcard-txt').html("请选择提前还款的还款方式");
//						$('.popbox-msgtips').show();
//						setPopboxBg();
//						$('.popbox-bindcard-txt').parents('.popbox-center').css({ 'margin-top': -$('.popbox-bindcard-txt').parents('.popbox-center').height() / 2 });
//					}
//
//					closePopbox($(this));
//				}
//			},
//			error: function (o) {
//
//			}
//		})
//
//		//data ={"code":"0000","remark":"立即还款","userId":"3242","bigOrderNo":"3232","orderAmount":"2434"};
//
//
//
//	})
//
//	function finalPay(data) {
//		$.ajax({
//			url: URL + "/payment/post-mall-credit-order-create.do",
//			data: data,
//			dataType: 'jsonp',
//			jsonp: "jsonp",
//			success: function (data) {
//				if (data.code == '8888') {
//					data = data || {};
//					location.href = URL + "/payment/pay/credit-order-pay-select.do?orderNo=" + data.escOrderId;
//				}
//			},
//			error: function (o) {
//
//			}
//		})
//	}
//	var odata;
//	//提前还款加载
//	JEND.define("JEND.repayAllList", {
//		init: function (data, url, confirmLink) {
//			var m = this,
//			LoanType = data.loanType || '',
//			clink = confirmLink || '',
//			raPrincOver = data.raPrincOver || '';
//			$('.popbox-payAll .confirmPayAllBtn').attr({'dataLink':url,'confirmLink':clink,'loanType':LoanType,'raPrincOver':raPrincOver});
//			m.loadData(data, function(){
//				m.createHtml();
//			}, url);
//		},
//		opts: {
//			"restype": ""
//		},
//		loadData: function (dt, callback, url) {
//			var adata = dt || {};
//			var m = this;
//			m.opts = $.extend(m.opts, adata);
////			console.log(m.opts,'    这是点击提前还款按钮后传到后台的数据')
//			$.ajax({
//				url: url,
//				data: m.opts,
//				dataType: 'jsonp',
//				jsonp: "jsoncallback",
//				async:false,
//				success: function (data) {
//					odata = data;
//					JEND.util.dialog.close();
//					if (data.code == '0000') {
//						m.data = data || {};
//						m.opts = $.extend(m.opts, m.data);
//						callback && callback();
//					} else {
//						JEND.load('util.dialog', function () {
//							JEND.util.dialog.alert(data.msg);
//						});
//					}
//				}
//			});
//			//m.data={"normalInterest":3,"defaultInterest":0,"presentCapitalBalance":2966.46,"overdueCapitalBalance":0,"preRepayAmount1":9033.54,"preRepayAmount":12003,"code":"0000","msg":"success","overdueInterest":0}
//		},
//		createHtml: function () {
//			var m = this, htmlTem = m.htmlTem.html1, html = [];
//			m.data.preRepayAmount1 = toFixed(m.data.preRepayAmount1);
//			m.data.presentCapitalBalance = toFixed(m.data.presentCapitalBalance);
//			m.data.normalInterest = toFixed(m.data.normalInterest);
//			m.data.overdueCapitalBalance = toFixed(m.data.overdueCapitalBalance);
//			m.data.overdueInterest = toFixed(m.data.overdueInterest);
//			m.data.defaultInterest = toFixed(m.data.defaultInterest);
//			m.data.preRepayAmount = toFixed(m.data.preRepayAmount);
//
//			html.push(htmlTem.substitute(m.data))
////			console.log(m.data)
//			$(".pp_repayAll_tbl table").html(html.join(""));
//			$('.popbox-payAll').show();
//			$('.popbox-payAll').find('.popbox-center').css({ 'margin-top': -$('.popbox-payAll').find('.popbox-center').height() / 2 });
//
//			//给确定按钮绑定参数
//			$('.popbox-payAll .confirmPayAllBtn').attr("data-a", m.opts.dueId).attr("data-b", m.opts.preRepayAmount).attr("data-c", m.opts.preRepayAmount1)
//				.attr("data-d", m.opts.presentCapitalBalance).attr("data-e", m.opts.overdueCapitalBalance).attr("data-f", m.opts.normalInterest)
//				.attr("data-g", m.opts.overdueInterest).attr("data-h", m.opts.defaultInterest)
//		},
//		htmlTem: function () {
//			var html = '<h3 style="line-height: 30px">{userName}老板您要做提前还款，请确保您尾号为{cardNo}的邮储银行卡上有足够的钱用于还款。您提前还款总金额为:{preRepayAmount}元</h3>'
//				+ '</br>以下是您应还款明细：本金{repayPrinc}+利息{repayInter}={preRepayAmount}元';
//
//			return {
//				html1: html
//			};
//		}()
//	}, function () {
//
//	});
//	
//	// 进货未出账单 提前还款按钮
////	$('.repayAllBtn2').live('click', function () {
////		JEND.repayAllList.init(
////			{
////				dueId: $(this).parent('td').find('input.dueIdInput').val(),
////				loanType: $(this).parent('td').find('input.loanType').val()
////			},
////			$("#uhj_server").val() + "/pixiao/px_preRepay.action"
////		)
////	});
//	var btnStatus = true;
//	//确认提前还款
//	$('.confirmPayAllBtn').live('click', function () {
//		var $this = $(this);
////			var dataIn = {
////				'dueId': $this.attr("data-a"),
////				'loanType':this.getAttribute('loanType'),
////				'preRepayAmount': $this.attr("data-b"),
////				'preRepayAmount1': $this.attr("data-c"),
////				'presentCapitalBalance': $this.attr("data-d"),//当前期本金
////				'overdueCapitalBalance': $this.attr("data-e"),//贷款拖欠本金
////				'normalInterest': $this.attr("data-f"),//正常利息
////				'overdueInterest': $this.attr("data-g"),//拖欠利息
////				'defaultInterest': $this.attr("data-h")//罚息
////
////			}
//			var url = this.getAttribute('confirmLink');
//			odata.dueId=$this.attr("data-a");
//			odata.dueIds=$('input.dueIds').eq(index).val();
//			// 去掉数字中的逗号
//			odata.preRepayAmount=$this.attr("data-b");
//			odata.preRepayAmount1=$this.attr("data-c");
//			odata.presentCapitalBalance=$this.attr("data-d");
//			console.log(odata,'  点击了确认按钮')
////			if (this.getAttribute('dataLink')) {
////				url= this.getAttribute('dataLink');
////				odata.dueId=$this.attr("data-a");
////				// 去掉数字中的逗号
////				odata.preRepayAmount=$this.attr("data-b");
////				odata.preRepayAmount1=$this.attr("data-c");
////				odata.presentCapitalBalance=$this.attr("data-d");
////				dataIn = odata;
////			} else {
////				url = $("#uhj_server").val() + "/uhj/yczgd_confirmEarlyPlans.action";
////			}
//		if (btnStatus) {
////			console.log(odata,url,888888)
//			$.ajax({
//				url: url,
//				data: odata,
//				dataType: 'jsonp',
//				jsonp: "jsoncallback",
//				success: function (data) {
////					console.log(data,333333)
//					closePopbox($this);
//					if (data.code == '0000') {
//						var info = odata.userName + "老板，您已成功提前还款<br>您提前还款的额度将在明天恢复";
//						JEND.load('util.dialog', function () {
//							JEND.util.dialog.alert({ type: 'success', message: info });
//							JEND.util.dialog.alert({
//								message: info,
//								callback: function () {
//									JEND.page.refresh();
//								}
//							});
//						})
//						//
//					}
//					if (data.code == '1000') {
//						var weihao = JEND.repayAllList.data.cardNo;
//						var info = JEND.repayAllList.data.userName + '老板，您的提前还款失败，请确认尾号' + weihao + '为的邮储银行卡上有足够的余额，如有问题请联系客服11185转4';
//						JEND.load('util.dialog', function () {
//							JEND.util.dialog.alert({ title: '提前还款失败', message: info });
//						});
//					}
//					if (data.code == '2000') {
//						var info = '对不起，您的卡上余额不足，提前还款失败。';
//						JEND.load('util.dialog', function () {
//							JEND.util.dialog.alert({ title: '提前还款失败', message: info });
//						});
//					}
//
//				}
//			})
//			btnStatus = false;
//		}
//	});
//
//
//
//	$('.repayAdvance').live('keyup', function (e) {
//
//		var advShouldPaySumAmt = 0;
//		if (!isNaN($(this).val()) && $(this).val() >= 0 && $(this).val() <= advPrincipal) {
//			if ($(this).val() == advPrincipal) {
//				$('.advInterP').text(advInter);
//			} else {
//				$('.advInterP').text('0.00');
//			}
//			advShouldPaySumAmt = Number($(this).val()) + Number($('.advInterP').text()) + Number($('.advShouldPayInter').text()) + Number($('.advShouldPayoverDue').text());
//			sumAmt = advShouldPaySumAmt.toFixed(2);
//			$('.advShouldPaySumAmt').html('￥' + toFixed(advShouldPaySumAmt));
//			if (advShouldPaySumAmt > 0) {
//				$('.popbox-payAll').find('.oprateNotAllow').addClass('confirmPayAllBtn').removeClass('oprateNotAllow');
//			} else {
//				$('.popbox-payAll').find('.confirmPayAllBtn').addClass('oprateNotAllow').removeClass('confirmPayAllBtn');
//			}
//		} else {
//			$(this).val('');
//			$('.advInterP').text('0.00');
//			advShouldPaySumAmt = Number($(this).val()) + Number($('.advInterP').text()) + Number($('.advShouldPayInter').text()) + Number($('.advShouldPayoverDue').text());
//			sumAmt = advShouldPaySumAmt.toFixed(2);
//			$('.advShouldPaySumAmt').html('￥' + toFixed(advShouldPaySumAmt));
//			if (advShouldPaySumAmt > 0) {
//				$('.popbox-payAll').find('.oprateNotAllow').addClass('confirmPayAllBtn').removeClass('oprateNotAllow');
//			} else {
//				$('.popbox-payAll').find('.confirmPayAllBtn').addClass('oprateNotAllow').removeClass('confirmPayAllBtn');
//			}
//			return false;
//		}
//
//
//	});
//
//	//还款计划加载 账单明细加载
//	JEND.define("JEND.repayList", {
//		init: function (data, url) {
//			var m = this;
//			m.loadData(data, function () {
//				m.createHtml();
//			}, url);
//		},
//		opts: {
//			"restype": ""
//		},
//		loadData: function (dt, callback, url) {
//			var data = dt || {};
//			var m = this;
//			$.ajax({
//				url: url,
//				data: data,
//				dataType: 'jsonp',
//				jsonp: "jsoncallback",
//				success: function (data) {
//					console.log(data,'  返回还款查询数据')
//					if (data.code == '0000') {
//						m.data = data || {};
//						callback && callback();;
//					}
//					else {
//						JEND.load('util.dialog', function () {
//							JEND.util.dialog.alert('系统繁忙，请稍后再试！');
//						});
//					}
//				},
//				error: function (o) {
//					callback && callback();
//				}
//			});
//            /*m.data ={"list":[
//             {"index":"1","currPrincipal":"0","planRepayDate":"2015-12-06","currInter":"4.83","shouldAmount":"5.94","currOverDue":"1.11","status":"2","day":"365"},
//             {"index":"2","currPrincipal":"0","planRepayDate":"2016-01-06","currInter":"49.91","shouldAmount":"51.01","currOverDue":"1.11","status":"0","day":"0"},
//             {"index":"3","currPrincipal":"0","planRepayDate":"2016-02-06","currInter":"49.91","shouldAmount":"51.01","currOverDue":"1.11","status":"1","day":"0"},
//             {"index":"4","currPrincipal":"7000","planRepayDate":"2016-03-06","currInter":"46.69","shouldAmount":"7051.01","currOverDue":"1.11","status":"3","day":"0"}
//             ],"sumShouldAmount":"7051.01"
//             ,"sumCurrPrincipal":"7000"
//             ,"sumCurrInter":"200.35"
//             ,"sumCurrOverDue":"4.44"
//             ,"sumStatus":"2"
//             ,"sumDay":"365"
//             ,"code":"0000","msg":"success"}
//
//             callback && callback();*/
//		},
//		createHtml: function () {
//			var m = this, htmlTem = m.htmlTem.html1, htmlTem2 = m.htmlTem.html2;
//			m.$Box = $(".pp_repayPlan_tbl tbody");
//			var html = [], html2 = [], data = (m.data || {}).list || [];
//			var status = { "0": "还款中", "1": "已还清", "2": "逾期", "3": "已取消", "4": "待放款" };
//			var iStatusCss = { "0": "zgd-status-b", "1": "zgd-status-g", "2": "zgd-status-r nobg-overdue", "3": "zgd-status-b", "4": "zgd-status-b" };
//
//			$.each(data, function () {
//				var t = this;
//				t.iStatusCss = iStatusCss[t.status] || "";
//				t.iStatus = t.status == 2 ? status[t.status] + "(" + +t.day + "天)" : status[t.status] || "";
//				t.shouldAmount = toFixed(t.shouldAmount);
//				t.currPrincipal = toFixed(t.currPrincipal);
//				t.currInter = toFixed(t.currInter);
//				t.currOverDue = toFixed(t.currOverDue);
//				html.push(htmlTem.substitute(t));
//			})
//
//			m.data.sumShouldAmount = toFixed(m.data.sumShouldAmount);
//			m.data.sumCurrPrincipal = toFixed(m.data.sumCurrPrincipal);
//			m.data.sumCurrInter = toFixed(m.data.sumCurrInter);
//			m.data.sumCurrOverDue = toFixed(m.data.sumCurrOverDue);
//			m.data.iSumStatus = m.data.sumStatus == 2 ? status[m.data.sumStatus] + m.data.sumDay + "天" : status[m.data.sumStatus] || "";
//			m.data.iSumStatusCss = iStatusCss[m.data.sumStatus] || "";
//
//
//			html2.push(htmlTem2.substitute(m.data));
//
//			m.$Box.html(html.join(""));
//			m.$Box.append(html2.join(""));
//
//			$('.popbox-repayPlan').show();
//			setPopboxBg();
//			$('.popbox-repayPlan').find('.popbox-center').css({ 'margin-top': -$('.popbox-repayPlan').find('.popbox-center').height() / 2 });
//		},
//		htmlTem: function () {
//			var arr1 = [], arr2 = [];
//			arr1.push('<tr>');
//			arr1.push('  <td style="text-align:center;">{index}</td>');
//			arr1.push('  <td style="text-align:center;">{planRepayDate}</td>');
//			arr1.push('  <td style="text-align:center;" class="font-YaHei3">￥{shouldAmount}</td>');
//			arr1.push('  <td style="text-align:center;"></td>');
//			arr1.push('  <td style="text-align:center;" class="font-YaHei3">￥{currPrincipal}</td>');
//			arr1.push('  <td style="text-align:center;"></td>');
//			arr1.push('  <td style="text-align:center;" class="font-YaHei3">￥{currInter}</td>');
//			//arr1.push('  <td style="text-align:center;"></td>');
//			//arr1.push('  <td style="text-align:center;" class="font-YaHei3">￥{currOverDue}</td>');
//			arr1.push('  <td style="text-align:center;"><div class="simsunfont zgd-icons zgd-status {iStatusCss}" style="margin:auto;">{iStatus}</div></td>');
//			arr1.push(' </tr>');
//
//			arr2.push('<tr>');
//			arr2.push('  <td style="text-align:center;"></td>');
//			arr2.push('  <td style="text-align:center;">合计</td>');
//			arr2.push('  <td style="text-align:center;" class="font-YaHei3">￥{sumShouldAmount}</td>');
//			arr2.push('  <td style="text-align:center;"></td>');
//			arr2.push('  <td style="text-align:center;" class="font-YaHei3">￥{sumCurrPrincipal}</td>');
//			arr2.push('  <td style="text-align:center;"></td>');
//			arr2.push('  <td style="text-align:center;" class="font-YaHei3">￥{sumCurrInter}</td>');
//			arr2.push('  <td style="text-align:center;"></td>');
//			//arr2.push('  <td style="text-align:center;" class="font-YaHei3">￥{sumCurrOverDue}</td>');
//			//arr2.push('  <td><div class="simsunfont zgd-icons zgd-status {iSumStatusCss}">{iSumStatus}</div></td>');
//			//arr2.push('  <td style="text-align:center;"></td>');
//			arr2.push('<tr>');
//			arr2.push('<tr>');
//			arr2.push('  <td style="text-align:center;"></td>');
//			arr2.push('  <td style="text-align:center;">总罚息</td>');
//			arr2.push('  <td style="text-align:center;" class="font-YaHei3">￥{sumPrincOver}</td>');
//			arr2.push('  <td style="text-align:center;"></td>');
//			arr2.push('  <td style="text-align:center;"></td>');
//			arr2.push('  <td style="text-align:center;"></td>');
//			arr2.push('  <td style="text-align:center;"></td>');
//			arr2.push('  <td style="text-align:center;"></td>');
//			//arr2.push('  <td style="text-align:center;" class="font-YaHei3">￥{sumCurrOverDue}</td>');
//			//arr2.push('  <td><div class="simsunfont zgd-icons zgd-status {iSumStatusCss}">{iSumStatus}</div></td>');
//			//arr2.push('  <td style="text-align:center;"></td>');
//			arr2.push('<tr>')
//
//
//			return {
//				html1: arr1.join(" "),
//				html2: arr2.join(" ")
//			};
//		}()
//	}, function () {
//		//JEND.repayList.init();
//	});
//
//	
//	var rdamount,raamount,index;
//	
//	/*
//	 * 还款查询 、账单明细、订单明细、提前还款 **弹窗加载**
//	 * JEND.repayAllList  提前还款 
//	 * JEND.purchase.showRepayBox  账单明细、订单明细
//	 * JEND.repayList  现金借款--还款查询
//	 */
//	function btnTabs (data, url, confirmLinks) {
//		index = $(this).parents('table').find('.repayAllBtn').index($(this));
//		var clinks = confirmLinks || '';
//		var isZdmx = this.getAttribute('class').indexOf('zdmx') != -1;
//		if (isZdmx) { // 账单明细、订单明细弹窗
//			if(this.getAttribute('isNotBill')) { // 进货未出账单
//				JEND.purchase.showRepayBox(data[2], url[2],  'WCZD');
//			} else if (this.getAttribute('isBill')) { // 进货已出账单
//				rdamount = $(this).parents().siblings('.rdAmount').attr('rdamount');
//				raamount = $(this).parents().siblings('.raAmount').attr('raamount');
//				JEND.purchase.showRepayBox(data[1], url[1], 'YCZD');
//			} else{ // 现金借款
//				JEND.repayList.init(data[0], url[0]);
//			}
//		} else {  // 提前还款弹窗
//			if(this.getAttribute('isNotBill')) { // 进货未出账单
//				JEND.repayAllList.init(data[2], url[2], clinks[2]);
//			} else if (this.getAttribute('isBill')) { // 进货已出账单
//				JEND.repayAllList.init(data[1], url[1], clinks[1]);
//			} else{ // 现金借款
//				JEND.repayAllList.init(data[0], url[0], clinks[0]);
//			}
//		}
//		
//	}
//	
//	// 点击弹出 账单明细 窗口
//	$('.repayPlanBtn').live('click', function () {
//		var datas = [
//	             {
//	            	 dueId: $(this).parent('td').find('input.dueIdInput').val()
//	             },
//	             {
//	            	 dueIds: $(this).parent('td').find('input.dueIds').val()
//	             },
//	             {
//	            	 dueId: $(this).parent('td').find('input.dueIdInput').val()
//	             },
//             ],
//			urls = [
//		        $("#uhj_server").val() + "/uhj/yczgd_queryPlan.action",  // 现金借款
//		        $("#uhj_server").val() + "/pixiao/px_signBillDetail.action", // 已出账单明细
//		        $("#uhj_server").val() + "/pixiao/px_unOutBillDetail.action" // 未出账单明细
//	        ]
//		btnTabs.call(this, datas, urls);
//	})
//	
//	// 点击弹出 提前还款 窗口
//	$('.repayAllBtn').live('click', function () {
//		var datas = [
//	             { // 现金借款传值
//					dueId: $(this).parent('td').find('input.dueIdInput').val(),
//	             },
//	             { // 进货已出账单传值
//	            	 dueIds: $(this).parent('td').find('input.dueIds').val(),
//	             },
//	             { // 进货未出账单传值
//	            	dueId: $(this).parent('td').find('input.dueIdInput').val(),
//	 				loanType: $(this).parent('td').find('input.loanType').val()	
//	             }
//			],
//			urls = [
//		        $("#uhj_server").val() + "/uhj/yczgd_queryEarlyPlans.action", // 现金借款
//		        $("#uhj_server").val() +"/pixiao/px_querySignPreRepay.action" ,      // 进货已出账单
//		        $("#uhj_server").val() + "/pixiao/px_preRepay.action"         // 进货未出账单
//	        ],
////	        点击确认按钮发送的链接地址
//	        confirmLinks = [
//                $("#uhj_server").val() + "/uhj/yczgd_confirmEarlyPlans.action",
//                $("#uhj_server").val() + "/pixiao/px_confirmSignEarlyRepay.action",
//                $("#uhj_server").val() + "/pixiao/px_sendPreRepay.action",
//            ]
//		btnTabs.call(this, datas, urls, confirmLinks);
//	});
//	
//	
//	
//	
////	 提前还款事件
////	$('.repayAllBtn').live('click', function () {
////		var link = $("#uhj_server").val() + "/pixiao/px_preRepay.action",
////			arr = [];
////		if(this.getAttribute('isNotBill')) { // 进货未出账单
////			arr.push(link);
////			JEND.repayAllList.init(
////					{
////						dueId: $(this).parent('td').find('input.dueIdInput').val(),
////						loanType: $(this).parent('td').find('input.loanType').val(),
////						
////					},
////					$("#uhj_server").val() + "/pixiao/px_preRepay.action" // 进货未出账单传给后台提前还款弹出窗口的请求的URL
////				)
////		} else if (this.getAttribute('isBill')) { // 进货已出账单1111111
////			
////		} else{ // 现金借款
////			JEND.repayAllList.init({
////				dueId: $(this).parent('td').find('input.dueIdInput').val()
////			});
////		}
////	});
//
//
//	$('.close-pop').live('click', function () {
//		closePopbox($(this));
//	})
//	//close popbox
//	function closePopbox(tar) {
//		tar.parents('.popbox').hide();
//	}
//	//ie6 popbox
//	function setPopboxBg() {
//		if ($.browser.msie && $.browser.version == 6) {
//			$('.popbox').find('.popbox-bg').css('height', $(document).height());
//		}
//	}
//
//	//额度不够引导弹窗
//	$('.availPro-i').live('click', function () {
//		$('.popbox-inc').show();
//	})
//	//额度不够引导弹窗关闭
//	$('.close-inc-btn').live('click', function () {
//		$('.popbox-inc').hide();
//	})
//
//})