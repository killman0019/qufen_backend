/*--------------------------*\
	导航背景动画效果
\*--------------------------*/

define(function (require) {
	var $ = require("jquery");
	var animation = require("module/animation");
	var nextFrame = animation.nextFrame;
	var cancelNextFrame = animation.cancelNextFrame;

	$.navslider = {
		pool: {},
		setProperties: function($block, properties) {
			$block.css(properties);
		}
	};
	$.fn.extend({
		navslider: function (paramConfig) {
			var wrapper = this.get(0);
			var $wrapper = this;

			var defConfig = {
				qItem: "a",
				qActiveItem: ".m-nav-item--active",
				duration: 30,
				effectFun: animation.effects.back.easeOut,
				// 滑块初始属性
				block: {
					left: 0,
					width: 0,
					background: "#f60"
				}
			};

			var config = $.extend({}, defConfig, paramConfig);
			$.navslider.pool[wrapper] = config;

			// 处理参数
			config.$items = $wrapper.find(config.qItem);
			if (config.$items.size() === 0) {
				return;
			}
			config.$items.each(function () {
				var $this = $(this);
				$this.data("navslider-config", {
					left: $this.offset().left,
					width: $this.innerWidth()
				});
			});

			// 标记容器
			$wrapper.attr("navslider", "yes");

			// 初始化元素
			$wrapper.css({
				position: "relative",
				zIndex: 2
			});

			// 插入滑块
			var $block = config.$block;
			if (!$block) {
				config.$block = $block = $('<div></div>');
				$block.appendTo(document.body);
			}

			$block.css($.extend({}, config.block, {
				position: "absolute",
				top: $wrapper.offset().top,
				zIndex: 1,
				height: config.$items.eq(0).innerHeight()
			}));

			// 当前选中的项
			config.$items.filter(config.qActiveItem).navsliderMove(false);

			$wrapper.on("mouseover", config.qItem, function(e) {
				$(this).navsliderMove();
			});

			// 鼠标移除，滑块位置还原
			$wrapper.hover(function() {}, function () {
				// 有当前选项则返回当前选项
				var $activeItem = config.$items.filter(config.qActiveItem);
				if ($activeItem.size()) {
					$activeItem.navsliderMove();
				}
				// 没有则从左侧消失
				else {
					
				}
			});
		},
		// 针对导航中的项
		navsliderMove: function (animation) {
			var element = this;
			var $this = $(this);
			var $wrapper = $this.parents('[navslider="yes"]:first');
			if ($wrapper.size() === 0) {
				return $this;
			}

			var config = $.navslider.pool[$wrapper.get(0)];
			var block = config.block;

			var currentStep = 0;

			// 动画开始时的属性值
			var startProperties = {
				left: block.left,
				width: block.width
			};

			// 目标属性值
			var targetProperties = $this.data("navslider-config");

			// 不要动画效果
			if (animation === false) {
				$.navslider.setProperties(config.$block, targetProperties);
				$.extend(config.block, targetProperties);
				return $this;
			}

			// 需要的变化属性值
			var changeProperties = {};
			$.each(targetProperties, function (p, v) {
				changeProperties[p] = v - block[p];
			});

			// 没有变化
			if (changeProperties.left === 0 && changeProperties.width === 0) {
				return $this;
			}

			function _run() {
				cancelNextFrame(config.timer);
				if (currentStep < config.duration) {
					currentStep++;
					// 本步目标值
					$.each(changeProperties, function (p, v) {
						block[p] = startProperties[p] + Math.ceil(config.effectFun(currentStep, 0, v, config.duration));
					});
					$.navslider.setProperties(config.$block, {
						left: block.left,
						width: block.width
					});
					config.timer = nextFrame(_run);
				}
				else {
					$.navslider.setProperties(config.$block, targetProperties);
					$.extend(config.block, targetProperties);
				}
			}
			_run();

			return $this;
		}
	});
});
