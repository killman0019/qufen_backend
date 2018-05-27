/*--------------------------*\
	添加银行卡
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	var clickEventType = tzg.config.CLICK_EVENT;

	var $form = $("#j_bankcard_add_form");
	var $ibankId = $form.find("[name=ibankId]");
	var $cardNo = $form.find("[name=cardNo]");
	
	$form.ajaxForm({
		validate: {
			rules: {
				ibankId: {
					required: true
				},
				cardNo: {
					required: true,
					remote: {
						url: basePath + "/user/charge/queryBankCard",
						extraFormParams: ["ibankId"],
						checkFunction: function(element, data) {
							return data.success;
						},
						getMessage:function(element, data){
							return data.msg;
						}
					}
				},
				amount: {
					required: true,
					number: true,
					min: 1,
					max: 10000000
				}
			},
			messages: {
				ibankId: {
					required: "请选择银行"
				},
				cardNo: {
					required: "请输入银行卡号",
					remote: "银行卡号不正确"
				},
				amount: {
					required: "请输入充值金额",
					number: "充值金额不正确",
					min: "充值金额不得小于{0}元",
					max: "充值金额不得大于一千万元"
				}
			}
		},
		success: function (data) {
			if(data.success){
				$.getJSON(basePath+data.charge_url, function (data2) {
					if(data2.success){
						var createForm = require("module/create-form");
						var chargeJson = null;
						eval("chargeJson = " + data2.chargeJson);
						var form = createForm({
							formAction: chargeJson.info_order,
							formMethod: "post",
							formTarget: "",
							data: {
								req_data: data2.chargeJson
							}
						});
						form.submit();
					}
				});
			}
		}
	});
	
	$form.find("[name=ibankId]").change(function() {
		$cardNo.removeData("previousValue");
		$cardNo.valid();
	});
});
