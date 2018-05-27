/*--------------------------*\
	充值 添加银行卡
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	var $form = $("#j_charge_form");

	// 银行卡选择
	// ----------------------------------------------------------
	require("module/jquery.selectBank");

	var banks = $.parseJSON($("#j_bank_list").val());

	// 已绑卡用户
	if (banks.length && banks[1].cardNo) {
		banks.shift();
	}
	
	var $isNewCard = $form.find("[name=isNewCard]");

	var selectBank = $form.selectBank({
		"bankList": banks,
		"selectHandler": function(index) {
			var bank = banks[index];
			$isNewCard.val(bank ? (bank.cardNo ? 0 : 1) : 0);
		}
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
	var createForm = require("module/create-form");

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
				amount: {
					required: true,
					number: true,
					min: 1,
					max: 10000000
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
				amount: {
					required: "请输入充值金额",
					number: "充值金额不正确",
					min: "充值金额不得小于{0}元",
					max: "充值金额不得大于一千万元"
				}
			},
			errorPlacement: function(error, element) {
				switch( element.attr("name") ) {
					case "agreeProtocol":
						error.insertAfter(element.parents("div:first"));
						break;

					default:
						error.insertAfter(element.parent());
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
				var chargeJsonStr = data.chargeJson;
				var chargeJson = $.parseJSON(chargeJsonStr);
				var form = createForm({
					formAction: chargeJson.info_order,
					formMethod: "post",
					formTarget: "",
					data: {
						req_data: chargeJsonStr
					}
				});
				form.submit();
				return false;
			}
			else {
				$form.find(":submit").focus();
			}
		}
	});
});
