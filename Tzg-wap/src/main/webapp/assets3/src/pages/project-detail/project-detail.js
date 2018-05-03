/**
 * 标的详情
 * 
 * Author: 吴岸林
 */

define(['jquery', 'com/jquery.form'], function ($) {

	var $investForm = $('#invest_form');

	// 投资
	// ------------------------------------
	// 
	(function() {
		if ($investForm.length === 0) {
			return;
		}

		// 添加金额校验
		$.validator.addMethod("amount", function (amount) {
			return (/^\d+(\.\d{1,2}){0,}$/g).test(amount+"");
		});

		// 最大可投资金额
		var $investAmt = $investForm.find("[name=investAmt]");
		var maxInvestAmount = $investAmt.data("value");
		maxInvestAmount = Number(maxInvestAmount);

		$investForm.ajaxForm({
			validate: {
				rules: {
					investAmt: {
						required: true,
						number: true,
						amount: true,
						min: 0.01
						//max: maxInvestAmount
					}
				},
				messages: {
					investAmt: {
						required: "请输入投资金额",
						number: "投资金额不正确",
						amount: "投资金额不正确",
						min: "投资金额不得小于{0}元"
						//max: "投资金额不得大于最高可投金额"
					}
				},
				errorPlacement: function($error, $element) {
					switch( $element.attr("name") ) {
						case "investAmt":
							$error.appendTo($investForm);
							break;

						default:
							$error.insertAfter($element);
					}
				},
				submitHandler:function(form){
					form.submit();
				}
			}
		});

		if (document.getElementById("investAmt-error")) {
			$investAmt.one('focus', function() {
				$investAmt.valid();
			});
		}
	})();

	// 计算预期收益
	// ------------------------------------
	// 
	(function() {
		var $calcResult = $("#projected_proceeds");
		if ($calcResult.length === 0) {
			return;
		}

		var earningPerTenThousand = $calcResult.data("earningPerTenThousandYuan");

		var $investAmt = $investForm.find("[name=investAmt]");
		var toCurrency = function(amt) {
			amt = Number(amt);
			if (isNaN(amt)) {
				return "0.00";
			}

			var absoluteAmt = Math.abs(amt);
			if (absoluteAmt < 1000) {
				return String(amt.toFixed(2));
			}

			var amt100times = Math.round(absoluteAmt * 100);
			var amtStr = String(amt100times);

			var result = [];
			var currentNum;
			for (var i=0, l=amtStr.length; i<l; i++) {
				currentNum = amtStr.charAt(i);
				result.push(currentNum);
				if (i < l - 1 && (l - i + 3) % 3 == 0) {
					result.push(i >= l - 3 ? "." : ",");
				}
			}
			result = result.join("");
			if (amt < 0) {
				result = '-' + result;
			}
			return result;
		};

		var calcEarning = function() {
			var amt = $investAmt.val();
			amt = Number(amt);
			var earning = "";
			if (!isNaN(amt)) {
				earning = amt / 10000 * earningPerTenThousand;
				//earning = earning.toFixed(2);
			}
			$calcResult.html(toCurrency(earning));
		};
		$investAmt.on("oninput" in $investAmt.get(0) ? "input" : "propertychange", function (e) {
			if (e.type === "input" || e.type === "propertychange" && e.originalEvent.propertyName === "value") {
				calcEarning();
			}
		});

		calcEarning($investAmt.val());
	})();
});
