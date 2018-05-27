/*--------------------------*\
	绑卡
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	var $form = $("#j_add_bank_card_form");

	// 银行卡选择
	// ----------------------------------------------------------
	require("module/jquery.selectBank");

	var banks = $.parseJSON($("#j_bank_list").val());
	// 已绑卡用户
	if (banks.length && banks[1].cardNo) {
		banks.shift();
	}

	$form.selectBank({
		"bankList": banks,
		"selectedBankTemplate": '<a href="#" class="bank-item -has-bank" data-bank-index="{{index}}"><i class="u-icoBnk--{{bankCode}}"></i><div class="bank-name -high">{{bankName}}</div></a>'
	});

	// 表单提交
	// ----------------------------------------------------------
	require("module/jquery.form");

	$.validator.addMethod("bankCard", function (v) {
		if (v && !(/^[\s\d]+$/g).test(v)) {
			return false;
		}
		return true;
	});

	$.validator.addMethod("mobile", function(v) {
		return v && (/^1[0-9]{10}$/g).test(v);
	});

	$form.ajaxForm({
		successMessage: false,
		validate: {
			rules: {
				vcName: {
					required: true
				},
				vcCardCode: {
					required: true
				},
				cardNo: {
					required: true,
					bankCard: true
				},
				phone: {
					required: true,
					mobile: true
				},
				dynamicVerifyCode: {
					required: true,
					digits: true,
					length: 6
				},
				agreeProtocol: {
					required: true
				}
			},
			messages: {
				vcName: {
					required: "请输入姓名"
				},
				vcCardCode: {
					required: "请输入身份证号"
				},
				cardNo: {
					required: "请输入银行卡号",
					bankCard: "银行卡号不正确"
				},
				phone: {
					required: "请输入手机号码",
					mobile: "手机号码不正确"
				},
				dynamicVerifyCode: {
					required: "请输入验证码",
					length: "验证码不正确",
					digits: "验证码不正确"
				},
				agreeProtocol: {
					required: "必须同意相关协议"
				}
			},
			submitHandler: function (form) {
				var $form = $(form);
				var config = $form.data("ajaxFormConfig");
				var queryStr = $form.serialize();

				// 去掉银行卡号中的空格
				queryStr = queryStr.replace(/cardNo=[^&]+/gi, function (rs) {
					return rs.replace(/\+/g, "");
				});

				$.ajax($form.attr("action"), {
					dataType: "json",
					type: $form.attr("method"),
					data: queryStr,
					context: form,
					success: config.successHandler,
					failed: config.failedHandler
				});

				$form.find(":submit").attr("disabled", true).html('<i class="u-fntIco--ldg"></i>');
			},
			errorPlacement: function(error, element) {
				switch( element.attr("name") ) {
					case "dynamicVerifyCode":
						error.insertAfter(element.parents(".captcha"));
						break;

					case "agreeProtocol":
						error.insertAfter(element.parents("div:first"));
						break;

					default:
						error.insertAfter(element);
				}
			}
		}
	});

	// 验证码
	// ----------------------------------------------------------
	require("module/countdown");

	var $phone = $form.find("[name=phone]");
	var sendCaptchaParam = {};
	var captcahParams = ["vcName", "vcCardCode", "ibankId", "cardNo", "phone"];
	var captcahParamInputs = $.map(captcahParams, function(v) {
		return $form.find("[name="+ v +"]");
	});

	$.each(captcahParams, function(i, v) {
		sendCaptchaParam[v] = v == "cardNo" ? function(){return captcahParamInputs[i].val().replace(/\s/g, "");} : function(){return captcahParamInputs[i].val();}
	});

	$requestId = $form.find("[name=requestid]");

	var $sendCaptchaBtn = $("#j_send_captcha");
	$sendCaptchaBtn.countdown({
		eventType: "click",
		countDownOverMsg: "重新获取",
		beforeCountdown: function($element, config) {
			$requestId.val("");
			for (var i=0,l=captcahParamInputs.length; i<l; i++) {
				if (!captcahParamInputs[i].valid()) {
					tzg.showMessage( $("#"+ captcahParams[i] +"-error").text() );
					return false;
				}
			}
		},
		request: {
			url: basePath + "/user/bindCard/getBindingVerification",
			data: sendCaptchaParam,
			successHandler: function (data) {
				if (data.success) {
					if (data.requestid) {
						$requestId.val(data.requestid);
					}
				}
			}
		}
	});
});

