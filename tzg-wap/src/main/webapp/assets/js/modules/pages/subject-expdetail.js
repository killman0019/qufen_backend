/*-------------------------------*\
	项目详情
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");
	
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
	
	// 出错提示
	var $errorMsg = $("#j_error_msg");
	if ($errorMsg.val()) {
		alert($errorMsg.val());
	}
});
