/*--------------------------*\
	年月日倒计时
\*--------------------------*/

define(function(require) {
	var $ = require("zepto");
	if (!$.tzg) {
		$.tzg = {};
	}

	if (!$.tzg.countdown) {
		$.tzg.countdown = {};
	}

	var cd = $.tzg.countdown;

	$.extend(cd, {
		defConfig: {
			attrTargetTime: "data-target-timestamp",	// 目标时间戳
			attrCurrentTime: "data-current-timestamp",	// 当前时间 时间戳
			qDate: "[js-cd-date]",
			qHour: "[js-cd-hour]",
			qMinute: "[js-cd-minute]",
			qSecond: "[js-cd-second]",
			overText: false
		},
		timer: 0,
		interval: 1000,
		dateItems: {
			date: 60 * 60 * 24,
			hour: 60 * 60,
			minute: 60
		},
		$app: null,
		parseTimestamp: function(timestamp) {
			var _return = false;
			var paramType = typeof timestamp;
			if (paramType === "string") {
				timestamp = Number(timestamp);
				if (isNaN(timestamp)) {
					return false;
				}
				_return = timestamp;
			}
			else if (paramType === "number") {
				if (timestamp < 0) {
					return false;
				}
				_return = timestamp;
			}
			return _return;
		},
		overhandler: false	// 倒计时结束回调
	});

	$.extend($.fn, {
		countdownRefresh: function() {
			this.each(function() {
				var _this = this;
				if (!_this.tzgCountDownInitSuccess) {
					return;
				}
				
				var c = _this.tzgCountDownConfig;
				var intervalSecond = c.currentIntervalSecond--;
				if (intervalSecond * 1000 < cd.interval) {
					intervalSecond = 0;
					if ($.isFunction(c.overhandler) && c.overhandler(_this) === false) {
						return;
					}
				}

				$.each(cd.dateItems, function(p, v) {
					var q = Math.floor(intervalSecond / v);
					if (q) {
						intervalSecond %= v;
					}
					if (q < 10) {
						q = "0" + q;
					}
					if (c["$" + p]) {
						c["$" + p].html(q);
					}
				});
				if (intervalSecond < 10) {
					intervalSecond = "0" + intervalSecond;
				}
				if (c.$second) {
					c.$second.html(intervalSecond);
				}
			});
		},

		countdown: function(config) {
			this.each(function() {
				var _this = this;
				var $this = $(this);

				// 已经初始化
				if (_this.tzgCountDownConfig) return;
				var c = $.extend(_this.tzgCountDownConfig = {}, cd.defConfig, config);

				var targetTimestampStr = $this.attr(c.attrTargetTime);
				var targetTimestamp = cd.parseTimestamp(targetTimestampStr);
				if (!targetTimestamp) return;
				
				// 当前时间
				var currentTimestampStr = $this.attr(c.attrCurrentTime);
				var currentTimestamp = cd.parseTimestamp(currentTimestampStr);
				var customTime = +new Date;
				if (!currentTimestamp || currentTimestamp < customTime) {
					currentTimestamp = customTime;
				}
				c.currentTimestamp = currentTimestamp;

				for (var p in c) {
					if (p.indexOf("q") === 0 && p.length >= 2) {
						var proName = "$" +  p.substr(1, 1).toLowerCase();
						if (p.length > 2) {
							proName += p.substr(2);
						}

						var $element = $(c[p], this);
						c[proName] = ($element.size() ? $element : null);
					}
				}

				// 间隔秒数
				c.currentIntervalSecond = parseInt((targetTimestamp - currentTimestamp - cd.interval) / cd.interval);
				$this.countdownRefresh();

				_this.tzgCountDownInitSuccess = true;
			});

			cd.$app = this;
			if (cd.timer) {
				clearInterval(cd.timer);
			}
			cd.timer = setInterval(function() {
				cd.$app.countdownRefresh();
			}, cd.interval);
		}
	});
});
