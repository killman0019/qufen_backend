/*-------------------------------*\
	掌薪计划投资确认
\*-------------------------------*/
define(function (require) {
	var $ = require("zepto");

	var $form = $("#j_invest_confirm_form");

	var $actualAmount = $("#j_actual_amount");
	var originalInvestAmount = $("#j_original_invest_amount").text();

	// 表单校验
	var validator = {};
	var controls = ["payPassword", "agreeProtocol"];
	$.each(controls, function (i, v) {
		var $input = $form.find("[name='"+ v +"']");
		validator[v] = {
			name: v,
			$input: $input,
			$msg: $input.siblings(".error"),
			doCheck: function (msg) {
				if (!msg) {
					msg = this.check();
				}

				if (this.$note) {
					this.$note[ msg === "pass" ? "show" : "hide" ]();
				}

				if (msg === "pass") {
					this.$input.removeClass("error");
					this.$msg.hide();
					return true;
				}
				else {
					this.$input.addClass("error");
					this.$msg.text(msg).show();
				}

				return false;
			}
		};
	});

	validator.payPassword.check = function () {
		var v = this.$input.val();
		if (v === "") {
			return "请输入交易密码";
		}
		return "pass";
	};

	validator.agreeProtocol.check = function () {
		if (!this.$input.prop("checked")) {
			return "请阅读并同意《铜掌柜投资咨询与管理服务协议》与《铜掌柜投资合同》";
		}
		return "pass";
	};

	$form.on("submit", function (e) {
		e.preventDefault();

		var result = true;
		$.each(controls, function (i, v) {
			result = validator[v].doCheck() && result;
		});

		if (result) {
			$form.find("[type='submit']").addClass("-loading");
			$.ajax({
				url: $form.attr("action"),
				type: $form.attr("method"),
				dataType: "json",
				data: $form.serialize(),
				context: $form,
				// complete: function () {
				// 	this.find("[type='submit']").removeClass("-loading").prop("disabled", false);
				// },
				success: function (data) {
					if (data.redirectURL) {
						if (data.redirectURL.indexOf("http") != 0) {
							data.redirectURL = basePath + data.redirectURL;
						}
						self.location.replace(data.redirectURL);
					}
					else {
						this.find("[type='submit']").removeClass("-loading").prop("disabled", false);
					}

					if (!data.success) {
						alert(data.msg);
					}
				}
			});
		}
	});
	$form.find("[type='submit']").prop("disabled", false);

	validator.payPassword.$input.on("input", function (e) {
		validator.payPassword.doCheck();
	});

	validator.agreeProtocol.$input.on("click", function (e) {
		validator.agreeProtocol.doCheck();
	});


	// 协议
	var $protocolPopup = $("#j_protocol_popup");
	$protocolPopup.find("[ppclosebtn]").on("tap", function () {
		$protocolPopup.removeClass("-opened");
	});
	$("#j_protocol_btn").on("tap", function () {
		$protocolPopup.addClass("-opened");
	});
	//合同
	var $protocolPopup2 = $("#j_protocol_popup2");
	$protocolPopup2.find("[ppclosebtn2]").on("tap", function () {
		$protocolPopup2.removeClass("-opened");
	});
	$("#j_protocol_btn2").on("tap", function () {
		$protocolPopup2.addClass("-opened");
	});
});
