/*--------------------------*\
	按钮倒计时
	主要用于填写验证码
\*--------------------------*/

define(function(require) {
	var $ = require("jquery");

	$.fn.extend({
		countdown: function(config) {
			var defConfig = {
				time: 60,	// 开始倒计时间(秒)
				inCountdownMsg: "{0}秒后重试",	// 正在倒计时显示的文字
				countDownOverMsg: false,	// 倒计时完成显示的文字，没有则显示倒计时之前的值
				beforeCountdown: false, // 开始倒计时要执行的函数
				countdownOver: false,	// 倒计时结束要执行的函数
				eventType: "click",
				disabledClass: "z-gray",	// 不可用时的class
				request: {
					url: "",
					data: null,
					checkFunction: function (data) {
						if (data.msg) {
							tzg.showMessage(data.msg);
						}
						return data.success;
					},
					errorHandler: function () {
						tzg.showMessage("请求出错，请重试！");
					},
					timestamp: "mm"	// 给请求添加时间戳。false 表示不添加
				}
			};
			config = $.extend(true, {}, defConfig, config);

			var element = this.get(0);
			var tagName = element.tagName.toLowerCase();
			
			config.defMsg = (tagName == "input" || tagName == "textarea" ? this.val() : this.text() );

			this.data("cdConfig", config);

			if( config.eventType ) {
				this.on(config.eventType, function() {
					$(this).startCountdown();
				});
			}
			return this;
		},
		startCountdown: function() {
			var config = this.data("cdConfig");

			if( !config || this.data("cdDoing") ) {
				return this;
			}
			
			var $this = this;

			if( $.isFunction( config.beforeCountdown ) && config.beforeCountdown( this, config ) === false ) {
				this.data("cdDoing", false)
				return this;
			}

			var time = config.time;
			var tagName = this.get(0).tagName.toLowerCase();
			var changeFunc = ( tagName == "input" || tagName == "textarea" ? "val" : "text" );

			var countdown = function(){
				if(time > 0) {
					$this[changeFunc]( config.inCountdownMsg.replace("{0}", time--) );
					timer = setTimeout(countdown, 1000);
				}
				else {
					clearTimeout(timer);

					// 按钮恢复
					$this.removeClass(config.disabledClass);
					$this.data("cdDoing", false);
					if( $.isFunction( config.countdownOver ) && config.countdownOver( this, config ) === false ) {
						return this;
					}
					$this[changeFunc]( config.countDownOverMsg || config.defMsg );
				}
			};

			// 先发送请求
			if (config.request && config.request.url) {
				var r = config.request;
				var requestData = $.extend({}, r.data);
				for (var pp in requestData) {
					if ($.isFunction(requestData[pp])) {
						requestData[pp] = requestData[pp]();
					}
				}
				if (r.timestamp) {
					requestData[r.timestamp] = Math.random();
				}

				$this.data("cdDoing", true);

				$.ajax(r.url, {
					context: $this.get(0),
					type: "GET",
					dataType: "json",
					data: requestData,
					success: function (data) {
						if ($.isFunction(r.successHandler) && r.successHandler(data) === false) {
							return;
						}
						if ($.isFunction(r.checkFunction)) {
							if (r.checkFunction(data)) {
								countdown();
							}
							else {
								$this.data("cdDoing", false);
								$this.removeClass(config.disabledClass);
							}
						}
						else {
							countdown();
						}
					},
					error: r.errorHandler
				});
			}
			else {
				$this.data("cdDoing", true);
				countdown();
			}

			// 按钮变灰
			$this.addClass(config.disabledClass);

			return this;
		}
	});
});
