/*--------------------------*\
	提现
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var $form = $("#j_cash_form");

	// 银行卡选择
	// ----------------------------------------------------------
	require("module/jquery.selectBank");

	var banks = $.parseJSON($("#j_bank_list").val());
	banks.shift();

	var $maxCashAmount = $("#j_max_cash_amount");
	
	// 账户余额
	var balance = $("#j_balance").val();
	balance = Number(balance);
	if (isNaN(balance)) {
		balance = 0;
	}

	var selectBanK = $form.selectBank({
		"bankList": banks,
		"selectedBankTemplate": '<a href="#" class="bank-item -has-bank" data-bank-index="{{index}}"><i class="u-icoBnk--{{bankCode}}"></i><div class="bank-name -high">{{bankName}}</div></a>',
		"selectHandler": function(index) {
			var bankLimit = banks[index].thisLimitStr;
			$maxCashAmount.text(bankLimit < balance ? bankLimit : balance);
		},
		"selectorConfig": {
			"leftOperatorHtml": '<a href="'+ basePath +'/user/bindCard/addBankCard/page">添加新银行卡</a>'
		}
	});

	// 表单提交
	// ----------------------------------------------------------
	var createForm = require("module/create-form");

	require("module/jquery.form");
	$.validator.addMethod("bankCard", function (v) {
		if (v && !(/^[\s\d]+$/g).test(v)) {
			return false;
		}
		return true;
	});

	$form.ajaxForm({
		successMessage: false,
		validate: {
			rules: {
				numCash: {
					required: true,
					number: true,
					//min: minCashAmount,
					max: 10000000
				}
			},
			messages: {
				numCash: {
					required: "请输入提现金额",
					number: "提现金额不正确",
					min: "提现金额不得小于{0}元",
					max: "提现金额不得大于一千万元"
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
			}
		},
		success: function (data) {
			if(data.success){
				var cashJsonStr = data.cashJson;
				var cashJson = $.parseJSON(cashJsonStr);
				var form = createForm({
					formAction: cashJson.info_order,
					formMethod: "post",
					formTarget: "",
					data: {
						req_data: cashJsonStr
					}
				});
				form.submit();
				return false;
			}
		}
	});
});
