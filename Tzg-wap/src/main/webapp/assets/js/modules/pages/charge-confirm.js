/*--------------------------*\
	充值 确认
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	var $form = $("#j_charge_confirm_form");
	
	var $sendCaptchaBtn = $("#j_send_captcha");
	var $captchaInput = $form.find("[name=code]");

	// 表单提交
	// ----------------------------------------------------------
	require("module/jquery.form");

	$.validator.addMethod("mobile", function(mobileNumber) {
		return mobileNumber && (/^1[0-9]{10}$/g).test(mobileNumber);
	});
	
	var requestIdError = false;
	
	$form.find("[name=phone]").on("input", function() {
		requestIdError = false;
	});

	var v = $form.ajaxForm({
		successMessage: false,
		validate: {
			rules: {
				phone: {
					required: true,
					mobile: true
				},
				captchaSend: {
					required: true
				},
/*				code: {
					required: true,
					digits: true,
					length: 6
				},*/
				agreeProtocol: {
					required: true
				}
			},
			messages: {
				phone: {
					required: "请输入手机号码",
					mobile: "手机号码不正确"
				},
				captchaSend: {
					required: "请先获取验证码"
				},
				code: {
					required: "请输入验证码",
					length: "验证码不正确",
					digits: "验证码不正确"
				},
				agreeProtocol: {
					required: "必须同意相关协议"
				}
			},
			errorPlacement: function(error, element) {
				switch( element.attr("name") ) {
					case "code":
						error.insertAfter(element.parents(".captcha"));
						break;

					case "agreeProtocol":
						error.insertAfter(element.parents("div:first"));
						break;

					default:
						error.insertAfter(element.parent());
				}
			},
			submitHandler: function (form) {
				if (requestIdError) {
					tzg.showMessage(requestIdError);
					return;
				}
				requestIdError = false;
				var $form = $(form);
				var config = $form.data("ajaxFormConfig");
				var queryStr = $form.serialize();

				$.ajax($form.attr("action"), {
					dataType: "json",
					type: $form.attr("method"),
					data: queryStr,
					context: form,
					success: config.successHandler,
					failed: config.failedHandler
				});
				
				$captchaInput.prop("disabled", true);
				$sendCaptchaBtn.prop("disabled", true);

				$form.find(":submit").attr("disabled", true).html('<i class="u-fntIco--ldg"></i>');
			}
		},
		success: function(data) {
			if (data.success) {
				requestResult(data.orderid);
				return false;
			}
			else {
				var config = $form.data("ajaxFormConfig");
				var redirectURL = data[config.returnRedirectName];
				if (redirectURL) {
					self.location.href = (redirectURL.indexOf("http") == 0 ? redirectURL : basePath + redirectURL);
					return false;
				}

				// 恢复按钮
				$form.find(":submit").toDefaultStatu();
				
				$captchaInput.prop("disabled", false);
				$sendCaptchaBtn.prop("disabled", false);
			}
		},
		completeHandler: null
	});
	
	// 请求确认结果
	function requestResult(requestId) {
		var req;
		var timer;
		var requestedTimes = 0;	// 已经请求次数
		var requestURL = basePath + "/yeepay/search";
		var redirectURLs = [basePath + "/yeepay/chargeSucc?requestid="+requestId, basePath + "/yeepay/chargeProcessing?requestid="+requestId, basePath+"/yeepay/chargeFailure"];	// 提示页
		var $submitButton = $form.find(":submit");
		
		var $loading = $("#j_loading");

		// 按钮样式改变
		function setButtonStyle($btn) {
			$btn.removeClass("btn-second").addClass("btn-gray").html('支付确认中 <i class="u-fntIco--ldg"></i>');
		};

		setButtonStyle($submitButton);

		function requestResult() {
			requestedTimes++;
			if (!(req && req.readyState !== 0 && req.readyState !== 4)) {
				req = $.getJSON(requestURL, {"orderid": requestId, "num": requestedTimes, "m": parseInt(Math.random() * 100000)}, ajaxCallback);
			}
		};

		function ajaxCallback(data) {
			if (data.yeepayresult == '1') {
				// 直接跳转成功页
				return dispatch(redirectURLs[0]);
			}
			
			if (data.yeepayresult == '3') {
				// 直接跳转失败页
				return dispatch(redirectURLs[2]+"?msg="+data.errormsg);
			}
			
			if (requestedTimes >= 10) {
				return dispatch(redirectURLs[1]);
			}
			timer = setTimeout(requestResult, 1000);
		};

		function dispatch(url) {
			clearTimeout(timer);
			$submitButton.html('正在跳转  <i class="u-fntIco--ldg"></i>');
			self.location.href = url;
		};

		requestResult();
		$loading.show();
	};

	var validator = $form.data("validator");

	// 验证码
	// ----------------------------------------------------------
	require("module/countdown");

	var sendCaptchaParam = {};
	$.each(["ibankId", "cardNo", "amount"], function(i, v) {
		sendCaptchaParam[v] = $form.find("[name="+ v +"]").val();
	});
	sendCaptchaParam.phone = function(){return $phone.val();}

	var $requestId = $form.find("[name=requestid]");
	var $orderId = $form.find("[name=orderid]");

	var $phone = $form.find("[name=phone]");
	var $captchaSend = $form.find("[name=captchaSend]");	// 用于校验是否获取了验证码
	$captchaSend.val("");

	$sendCaptchaBtn.countdown({
		eventType: "click",
		countDownOverMsg: "重新获取",
		beforeCountdown: function($element, config) {
			$requestId.val("");
			$orderId.val("");
			if ($captchaSend.val() == "") {
				$captchaSend.val("yes");
				$captchaSend.rules("remove");
				$("#captchaSend-error").remove();

				$captchaInput.rules("add", {
					required: true,
					digits: true,
					length: 6
				});
			}
			if( !$phone.valid() ) {
				tzg.showMessage( $("#phone-error").text() );
				return false;		
			}
		},
		request: {
			url: basePath + "/user/charge/getchargeverification",
			data: sendCaptchaParam,
			successHandler: function (data) {
				if (data.success) {
					if (data.requestid) {
						$requestId.val(data.requestid);
					}
					else if (data.orderid) {
						$orderId.val(data.orderid);
					}
					requestIdError = false;
				}
				else {
					requestIdError = data.msg;
				}
			}
		}
	});

	// 协议弹出
	var $popupProtocol = $("#j_protocol_popup");
	$popupProtocol.find("[pppclosebtn]").on("click", function () {
		$popupProtocol.removeClass("z-opened");
	});
	var $protocolButton = $("#j_protocol_btn");
	$protocolButton.on("click", function () {
		$popupProtocol.addClass("z-opened");
	});

});
