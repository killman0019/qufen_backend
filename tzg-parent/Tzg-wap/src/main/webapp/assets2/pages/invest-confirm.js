//
// 投资确认
//
define(function (require) {
	var $ = require("zepto");

	var $form = $("#j_invest_confirm_form");

	var $actualAmount = $("#j_actual_amount"); 
	var originalInvestAmount = $("#j_original_invest_amount").text();

	var $redAmount = $form.find("[name='redAmount']");

	// 表单校验
	var validator = {};
	var controls = $redAmount.size() ? ["redAmount", "payPassword", "agreeProtocol"] : ["payPassword", "agreeProtocol"];
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

	if ($redAmount.size()) {
		$.each(["invest-amount", "max-amount"], function (i, v) {
			var amount = $redAmount.data(v)+"";
			amount = amount.split(",").join("");
			amount = parseFloat(amount);
			validator.redAmount[v] = amount;
		});
		validator.redAmount.$note = $("#j_red_note");
		validator.redAmount.check = function () {
			var v = this.$input.val();

			if (v == "") {
				return "pass";
			}

			if (!/^\d+$/g.test(v)) {
				return "红包金额不正确";
			}

			v = parseInt(v);

			if (v > this["max-amount"]) {
				return "本次最多只能使用"+ this["max-amount"] +"元红包";
			}

			return "pass";
		};	
	}
	

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

	if ($redAmount.size()) {
		function toCurrency (num) {   
			num = num.toString().replace(/\$|\,/g,'');  
			if (isNaN(num)) {
				num = "0.00"; 
			} 
			sign = (num == (num = Math.abs(num)));  
			num = Math.floor(num*100+0.50000000001);  
			cents = num%100;  
			num = Math.floor(num/100).toString();  
			if (cents < 10) {
				cents = "0" + cents;
			}
			for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++) {
				num = num.substring(0,num.length-(4*i+3))+','+num.substring(num.length-(4*i+3));
			}
			return (((sign)?'':'-') + num + '.' + cents);  
		}

		function cale() {
			var redAmount = parseInt($redAmount.val());
			$actualAmount.text(validator.redAmount.doCheck() && redAmount > 0 ? toCurrency(validator.redAmount["invest-amount"] - redAmount) : originalInvestAmount);
		}
		
		$redAmount.on("input", cale);
		if ($redAmount.val()) {
			cale();
		}
		$redAmount.on("keydown", function (e) {
			if (!(e.keyCode >= 48 && e.keyCode <= 57 || e.keyCode >= 96 && e.keyCode <= 105 || e.keyCode == 8)) {
				e.preventDefault();
			}
		});
	}


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
	
	
	var $protocolPopup2 = $("#j_protocol_popup2");
	$('#j_protocol_btn2').on('click', function(e) {
		e.preventDefault();
		$protocolPopup2.addClass("-opened");
	});
	$protocolPopup2.find("[ppclosebtn2]").on("tap", function () {
		$protocolPopup2.removeClass("-opened");
	});
});
