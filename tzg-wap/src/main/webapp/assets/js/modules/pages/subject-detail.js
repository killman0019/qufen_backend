/*-------------------------------*\
	项目详情
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
			errorPlacement: function(error, element) {
				switch( element.attr("name") ) {
					case "investAmt":
						error.appendTo($form);
						break;

					default:
						error.insertAfter(element);
				}
			},
			submitHandler:function(form) {
				form.submit();
			}
		}
	});

	// 计算预期收益
	var $calcResult = $("#j_earning");
	var earningPerTenThousand = $calcResult.data("earning-per-10000yuan");

	var $investAmt = $form.find("[name=investAmt]");
	var subjectId = $form.find('[name=subjectId]').val();
	var calcEarning = function() {
		if ($investAmt.valid()) {
			$.get(basePath + "/subject/getInvestmentIncome/list", {"subjectId": subjectId, "investAmt": $investAmt.val(), m: Math.random()}, function (data) {
				if (data.success) {
					$calcResult.html(data.amount + "元");
				} else {
					$calcResult.html(data.msg);
				}
			});
		} else {
			$calcResult.html("0.00元");
		}
	};
	$investAmt.on("oninput" in $investAmt.get(0) ? "input" : "propertychange", function (e) {
		if (e.type === "input" || e.type === "propertychange" && e.originalEvent.propertyName === "value") {
			calcEarning();
		}
	});

	if ($investAmt.hasClass('z-err')) {
		$calcResult.html("0.00元");
		$investAmt.one('focus', function() {
			calcEarning($investAmt.val());
		});
	} else {
		calcEarning($investAmt.val());
	}

	var $footbar = $("#j_footbar");

	// 倒计时
	var $countdown = $("#j_subject_countdown");
	if ($countdown.length) {
		require("module/jquery.countdown2");

		var currentTimestamp = $countdown.data("current-timestamp");
		var targetTimestamp = $countdown.data("target-timestamp");

		var timeItemsArr = {
			"date": {
				"dividend": 24 * 60 * 60 * 1000,
				"unit": "天"
			},
			"hour": {
				"dividend": 60 * 60 * 1000,
				"unit": "小时"
			},
			"minute": {
				"dividend": 60 * 1000,
				"unit": "分"
			},
			"second": {
				"dividend": 1000,
				"unit": "秒"
			}
		};

		var timer;
		function countdown() {
			var interval = targetTimestamp - currentTimestamp;
			if (interval <= 1000) {
				countdownOver();
				return;
			}
			timer = setInterval(function () {
				var countdownTextArr = [];
				interval -= 1000;

				// 最近的倒计时结束，重新开始倒计时
				if (interval < 0) {
					clearInterval(timer);
					countdownOver();
					return;
				}
				var t = interval;
				$.each(timeItemsArr, function(i, v) {
					var dividend = v.dividend;
					var pv = Math.floor(t / dividend)
					t = t % dividend;
					if (!(pv == 0 && v.unit == "天")) {
						countdownTextArr.push(" " + pv + " " + v.unit);
					}
				});

				$countdown.text(countdownTextArr.join("") + "后开标");
			}, 1000);
		};

		function countdownOver() {
			$footbar.addClass("-investable");
		};
		countdown();
	}
});
