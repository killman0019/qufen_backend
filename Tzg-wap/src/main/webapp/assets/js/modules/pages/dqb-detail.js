/*-------------------------------*\
	掌薪计划(定期宝)详情
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");
	// 添加金额校验
	$.validator.addMethod("amount", function (amount) {
		return (/^\d+(\.\d{1,2}){0,}$/g).test(amount+"");
	});

	var $form = $("#j_invest_form");

    // 最大可投资金额
	var maxInvestAmount = $form.find("[name=investAmt]").data("value");
	maxInvestAmount = Number(maxInvestAmount);

	$form.ajaxForm({
		validate: {
			rules: {
				investAmt: {
					required: true,
					number: true,
					amount: true,
					min: 0.01,
					max: maxInvestAmount
				}
			},
			messages: {
				investAmt: {
					required: "请输入投资金额",
					number: "投资金额不正确",
					amount: "投资金额不正确",
					min: "投资金额不得小于{0}元",
					max: "投资金额不得大于剩余可投金额"
				}
			},
			errorPlacement: function(error, element) {
				switch( element.attr("name") ) {
					case "investAmt":
						error.appendTo($form);
						break;

					default:
						error.insertAfter(element);
				}
			},
			submitHandler:function(form){
				form.submit();
			}
		}
	});

    // 计算预期收益
	var $calcResult = $("#j_earning");
	if ($calcResult.length) {
		var earningPerTenThousand = $calcResult.data("earning-per-10000yuan");

		var $investAmt = $form.find("[name=investAmt]");
		var calcEarning = function() {
			var amt = $investAmt.val();
			amt = Number(amt);
			var earning = "";
			if (!isNaN(amt)) {
				earning = amt / 10000 * earningPerTenThousand;
				earning = earning.toFixed(2);
			}
			$calcResult.html(earning + "元");
		};
		$investAmt.on("oninput" in $investAmt.get(0) ? "input" : "propertychange", function (e) {
			if (e.type === "input" || e.type === "propertychange" && e.originalEvent.propertyName === "value") {
				calcEarning();
			}
		});

		calcEarning($investAmt.val());
	}
});
