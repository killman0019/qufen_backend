define(function(require) {
	var $ = require("zepto");

	//
	// 弹窗
	//
	(function () {
		var $openBtn = $("#j_calculator_btn");
		var $winWrapper = $("#j_win_wrapper");
		var $closeBtn = $winWrapper.find(".win_close");
		var $input = $("#j_amount_ipt");
		var $calcBtn = $("#j_calc_btn");
		var $resultItem = $("#j_item_result");
		var $result = $("#j_result");
		var $resultWrap = $("#j_result_wrap");
		var $msgItem = $("#j_form_msg");
		var subjectId = $input.data("subject-id");

		$resultWrap.css("visibility", "hidden");

		function checkAmount(a) {
			a += "";
			var r = /^\d+(\.\d{1,2})?$/g.test(a);
			if (r) {
				a = Number(a);
				r = !isNaN(a) && a >= 0.01;
			}
			return r;
		}

		function handleInput(msg) {
			var v = $input.val();

			if (!msg) {
				if (v.length === 0) {
					msg = "请输入投资金额";
				}
				else if (!checkAmount(v)) {
					msg = "请输入正确的金额";
				}
				else {
					var a = Number(v);
					if (a > 10000000) {
						msg = "请输入小于一千万投资金额";
					}
				}
			}

			if (msg) {
				$msgItem.text(msg).show();
				$resultItem.hide();
			}
			else {
				$msgItem.hide();
				$resultItem.show();
				//$result.text("");
			}

			return msg;
		}

		$openBtn.on("tap", function () {
			$winWrapper.removeClass("-hidden");
			$input.val("");
			$result.text("");
			$resultWrap.css("visibility", "hidden");
		});
		$closeBtn.on("tap", function () {
			$winWrapper.addClass("-hidden");
			$("#j_nav_switch").focus();
		});

		$input.on("input", function (e) {
			handleInput();
		});

		$calcBtn.on("click", function () {
			var msg = handleInput();

			if (!msg) {
				$.getJSON(basePath + "/subject/getInvestmentIncome/list?subjectId="+ subjectId +"&investAmt="+ $input.val(), function (data) {
					if (data.success) {
						$resultWrap.css("visibility", "visible");
						$result.text(data.amount);
					}
					else if (data.msg) {
						handleInput(data.msg);
					}
				});
			}
		});
	})();

	//
	// 倒计时
	//
	require("modules/jquery.countdown");
	(function () {
		var $countdown = $("#j_subject_countdown");

		$countdown.countdown({
			overhandler: function (element) {
				var $element = $(element);
				var start = $element.data("current-timestamp");
				var end = $element.data("target-timestamp");
				if (start < end + 5000) {
					self.location.reload();
				}
			}
		});
	})();
});