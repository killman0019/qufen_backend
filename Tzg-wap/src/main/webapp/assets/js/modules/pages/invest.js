/*--------------------------*\
	投资 输入金额
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var $form = $("#j_invest_form");

	// 添加金额校验
	$.validator.addMethod("amount", function (amount) {
		return (/^\d+(\.\d{1,2}){0,}$/g).test(amount+"");
	});

	// 剩余可投金额
	var maxInvestAmountStr = $("#j_max_invest_amount").val();
	// 去掉逗号
	maxInvestAmountArr = maxInvestAmountStr.split(",");
	maxInvestAmountStr = maxInvestAmountArr.join("");
	var maxInvestAmount = parseFloat(maxInvestAmountStr);

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
						error.insertAfter("#j_form_item_content_amount");
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

	// 金额
	var $amount = $form.find("[name=investAmt]");
	var $income = $("#j_expect_income");
	var subjectId = $form.find("[name=subjectId]").val();
	function computeExpectIncome () {
		if ($amount.valid()) {
			$.get(basePath + "/user/invest/getInvestmentIncome", {"subjectId": subjectId, "investAmt": $amount.val()}, function (back) {
				$income.text(back);
			});
		}
		else {
			$income.text("");
		}
	}
	
	if($amount.val()){
		computeExpectIncome();
	}

	var timer1 = 0;
	$amount.on("oninput" in $amount.get(0) ? "input" : "propertychange", function () {
		if( timer1 ) {
			clearTimeout( timer1 );
		}
		timer1 = setTimeout(computeExpectIncome, 500);
	});
});


