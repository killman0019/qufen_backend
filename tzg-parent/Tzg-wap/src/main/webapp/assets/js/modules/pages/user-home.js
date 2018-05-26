/*-------------------------------*\
	掌薪计划(定期宝)详情
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	var dialog = require("module/dialog");
	var $dialog = dialog.elements.$dialog;

	var $autoTransferInSwitch = $("#j_movableauto-btn");
	var enabled = $autoTransferInSwitch.data("state") != 2;
	$autoTransferInSwitch.prop({
		"checked": enabled,
		"disabled": false
	});

	var closeDialogCallback = function() {
		$autoTransferInSwitch.prop("disabled", false);
	};

	$autoTransferInSwitch.on("click", function (e) {
		e.preventDefault();
		$autoTransferInSwitch.prop("disabled", true);

		// 关闭自动转入不用后台判断
		if ($autoTransferInSwitch.data("state") != 2) {
			dialog.open({
				title: "关闭自动转入",
				content: "关闭后账户余额将停止每日(00:00:00)自动转入铜钱宝。",
				ft: '<a href="#" j-action-cancel>取消</a><a href="#" j-action-confirm>确定</a>',
				closeCallback: closeDialogCallback
			});
		}
		else {
			// 先检查状态
			$.ajax(basePath + "/currentBao/checkState", {
				"type": "post",
				"dataType": "json",
				"data": {
					"m": parseInt(Math.random() * 10000000)
				},
				"complete": function() {
					$autoTransferInSwitch.prop("disabled", false);
				},
				"success": function (data) {
					if (data && data.success) {
						dialog.open({
							title: "开启自动转入",
							content: '<div>开启后账户余额将在每日(00:00:00)自动转入铜钱宝。</div>' + 
									'<div class="f-mt"><input name="agreeProtocol" type="checkbox" checked><a href="#" class="u-clPrm" j-open-transferin-protocol>《自动转入协议》</a></div>',
							ft: '<a href="#" j-action-cancel>取消</a><a href="#" j-action-confirm>确定</a>',
							closeCallback: closeDialogCallback
						});
					}
					else {
						dialog.open({
							content: data.msg,
							ft: '<a href="#" j-action-cancel>取消</a><a href="'+ (basePath + data.redirectURL) +'">确定</a>',
							closeCallback: closeDialogCallback
						});
					}
				}
			});
		}
	});

	$dialog.on("click", "[j-action-confirm]", function (e) {
		e.preventDefault();

		if ($(this).hasClass("-disabled")) {
			return;
		}

		$.ajax(basePath + "/currentBao/setautoinvest", {
				"type": "post",
				"dataType": "json",
				"data": {
					"iState": $autoTransferInSwitch.data("state") == 2 ? 1 : 2,
					"CSRFToken": $("#j_input_csrftoken").val(),
					"m": parseInt(Math.random() * 10000000)
				},
				"complete": function () {
					dialog.close();
				},
				"success": function (data) {
					if (data && data.success) {
						$autoTransferInSwitch.prop("checked", data.autoState != 2).data("state", data.autoState);
						$("#j_input_csrftoken").val(data.CSRFToken);
					}
					else {
						if (data.redirectURL) {
							self.location.href = basePath + data.redirectURL;
						}
						else if (data.msg) {
							alert(data.msg);
						}
					}
				}
		});
	});

	// 是否同意协议
	$dialog.on("click", "[name=agreeProtocol]", function (e) {
		var $this = $(this);
		$dialog.find("[j-action-confirm]")[$this.prop("checked") ? "removeClass" : "addClass"]("-disabled");
	});

	// 协议弹出
	var $popupProtocol = $("#j_protocol_popup");
	$popupProtocol.css("zIndex", 200);
	$popupProtocol.find("[pppclosebtn]").on("click", function () {
		$popupProtocol.removeClass("z-opened");
	});
	$dialog.on("click", "[j-open-transferin-protocol]", function (e) {
		e.preventDefault();
		$popupProtocol.addClass("z-opened");
	});
});
