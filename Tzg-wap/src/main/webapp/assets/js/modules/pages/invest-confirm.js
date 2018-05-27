/*--------------------------*\
	提现确认
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var clickEventType = tzg.config.CLICK_EVENT;

	var $form = $("#j_invest_confirm_form");
	
	$form.ajaxForm({
		validate: {
			rules: {
				payPassword: {
					required: true
				}
			},
			messages: {
				payPassword: {
					required: "请输入交易密码"
				}
			}
		},
		successHandler: function (data) {
			var form = this;
			var $form = $(form);
			var config = $form.data("ajaxFormConfig");

			if (config.success && $.isFunction(config.success) && config.success.call(form, data) === false) {
				return;
			}

			if (data.success) {
				if (config.successMessage !== false) {
					tzg.showMessage(config.successMessage);
				}

				if (config.redirect && data[config.returnRedirectName]) {
					var redirectURL = data[config.returnRedirectName];
					if (redirectURL.indexOf("http") != 0) {
						redirectURL = basePath + redirectURL;
					}
					self.location.replace(redirectURL);
				}
				else {
					$form.find(":submit").toDefaultStatu();
				}
			}
			else {
				$form.find(":submit").toDefaultStatu();
				if (data.redirectURL) {
					self.location.replace(basePath + data.redirectURL);
				}
				else {
					alert(data.msg);
				}
			}
		}
	});


	// 协议弹出
	var $popupProtocol = $("#j_protocol_popup");
	$popupProtocol.find("[pppclosebtn]").on(clickEventType, function () {
		$popupProtocol.removeClass("z-opened");
	});
	var $protocolButton = $("#j_protocol_btn");
	$protocolButton.on(clickEventType, function () {
		$popupProtocol.addClass("z-opened");
	});


	var toCurrency = function(num) {   
		num = num.toString().replace(/\$|\,/g,'');  
		if(isNaN(num))  
			num = "0.00";  
		sign = (num == (num = Math.abs(num)));  
		num = Math.floor(num*100+0.50000000001);  
		cents = num%100;  
		num = Math.floor(num/100).toString();  
		if(cents<10)  
			cents = "0" + cents;  
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)  
			num = num.substring(0,num.length-(4*i+3))+','+  
		num.substring(num.length-(4*i+3));  
		return (((sign)?'':'-') + num + '.' + cents);  
	};

	// 选择红包
	$(".redSlt-hd").on(tzg.config.CLICK_EVENT, function (e) {
		e.preventDefault();
		$(this.parentNode).toggleClass("z-open");
	});
	$.fn.selectRed = function () {
		var $this = this;
		var $items = $("li", this);
		var $total = $(".redSlt [mod-total-amount]");
		var $input = $("[name=redId]", $form);

		var $redAmount = $("#j_red_amount");
		var $realAmount = $("#j_real_amount");

		var realAmount = $realAmount.text();
		realAmount = realAmount.split(",").join("");
		realAmount = parseFloat(realAmount);

		function getCurrentRedId () {
			var $current = $items.filter(".z-atv");
			return $current.size() ? parseFloat($current.data("red-id")) : 0;
		}

		function getCurrentRedAmount () {
			var $current = $items.filter(".z-atv");
			return $current.size() ? parseFloat($current.data("red-amount")) : 0;
		}

		function setInput() {
			var currentRedAmount = getCurrentRedAmount();
			var formatCurrentRedAmount = toCurrency(currentRedAmount);
			$input.val(getCurrentRedId() || "");
			$redAmount.text(formatCurrentRedAmount);
			$realAmount.text(toCurrency(realAmount - currentRedAmount));
			$total.text(formatCurrentRedAmount);
		}

		this.on(tzg.config.CLICK_EVENT, "li", function () {
			var $this = $(this);

			$this.toggleClass("z-atv");
			$items.filter(".z-atv").not(this).removeClass("z-atv");

			setInput();
		});
	};
	$(".redSlt-lst").selectRed();
});

