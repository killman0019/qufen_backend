/*-------------------------------*\
	掌薪计划(定期宝)详情
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");
	var dialog = require("module/dialog");
	var $dialog = dialog.elements.$dialog;

	$.validator.addMethod("amount", function (amount) {
		return (/^\d+(\.\d{1,2}){0,}$/g).test(amount+"");
	});

	var $enter_form = $("#j_enter-form"),
		$out_form = $("#j_out-form");

	//最大可转入金额
	
	var maxEnterAmount = $enter_form.find("[name=entertAmt]").data("value");
	maxEnterAmount = Number(maxEnterAmount);

	$enter_form.ajaxForm({
		validate: {
			rules: {
				investAmt: {
					required: true,
					number: true,
					amount: true,
					min: 0.01,
					max: maxEnterAmount
				},
				password: {
					required: true
				},
				enterProtocol: {
					required: true
				}
			},
			messages: {
				investAmt: {
					required: "请输入转入金额",
					number: "转入金额不正确",
					amount: "转入金额不正确",
					min: "转入金额不得小于{0}元",
					max: "转入金额不得大于剩余可投金额"
				},
				password: {
					required: "请输入密码"
				},
				enterProtocol: {
					required: "必须接受并同意转入协议"
				}
			},
			errorPlacement: function(error, element) {
				switch( element.attr("name") ) {
					case "password":
						error.appendTo($enter_form);
						break;

					default:
						error.insertAfter(element);
				}
			}
		},
		successHandler: function (data) {
			if(data.success){
				self.location.replace(basePath + data.redirectURL)
			}else{
				$enter_form.find(":submit").toDefaultStatu();
				alert(data.msg);
			}
		}
	});

	// 最大可转出金额
	var maxOutAmount = $out_form.find("[name=maxOutAmt]").data("value");
	maxOutAmount = Number(maxOutAmount);

	$out_form.ajaxForm({
		validate: {
			rules: {
				investAmt: {
					required: true,
					number: true,
					amount: true,
					min: 0.01,
					max: maxOutAmount
				},
				password: {
					required: true
				}
			},
			messages: {
				investAmt: {
					required: "请输入转出金额",
					number: "转出金额不正确",
					amount: "转出金额不正确",
					min: "转出金额不得小于{0}元",
					max: "转出金额不得大于铜钱宝总金额"
				},
				password: {
					required: "请输入密码"
				}
			},
			errorPlacement: function(error, element) {
				switch( element.attr("name") ) {
					case "password":
						error.appendTo($enter_form);
						break;

					default:
						error.insertAfter(element);
				}
			}
		},
		successHandler: function (data) {
			if(data.success){
				self.location.replace(basePath + data.redirectURL)
			}else{
				$out_form.find(":submit").toDefaultStatu();
				alert(data.msg);
			}
		}
	});

	// 协议弹出
	var $popupProtocol = $("#j_protocol_popup");
	$popupProtocol.css("zIndex", 200);
	$popupProtocol.find("[pppclosebtn]").on("click", function () {
		$popupProtocol.removeClass("z-opened");
	});
	$("#j-protocol-btn").on("click", function (e) {
		e.preventDefault();
		$popupProtocol.addClass("z-opened");
	});
});
