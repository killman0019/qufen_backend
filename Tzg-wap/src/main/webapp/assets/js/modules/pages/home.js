/*--------------------------*\
	Home Page
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");
	var Slider = require("module/slider");
	slider = new Slider({'eWrap': '.m-adv'});

	$("#j_appdown_close").click(function() {
		$("#j_appdown").addClass("z-close");
		$(document.body).removeClass("body-hasTopDown");
	});

	// 生成饼图
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	(function() {
		var SVGChart = function($target, config) {
			if (!($target && $target instanceof jQuery && $target.size() > 0)) {
				return;
			}
			var defConfig = {
				$element: 			$target,
				bgBorderColor: 		"#d6d6d6",
				lightBorderColor: 	"#f87382",
				radius: 			195,
				borderWidth: 		0.11,			// 比例。(相对于半径)
				gapSize: 			110,			// 缺口大小。(单位：角度)
				completedRatioDataPropertyName: "completed"
			};

			var c = this.config = $.extend({}, defConfig, config);
			c.completedRatio = c.$element.data(c.completedRatioDataPropertyName);	// 完成度百分比
			this.generateSVG();
		};
		SVGChart.prototype = {
			generateSVG: function() {
				var c = this.config;
				var html = ['<svg version="1.1" xmlns="http://www.w3.org/2000/svg">'];
				html.push('<path d="" stroke="'+ c.bgBorderColor +'" stroke-linecap="round" fill="rgba(255,255,255,0)" class="path-bg" />');
				html.push('<path d="" stroke="'+ c.lightBorderColor +'" stroke-linecap="round" fill="rgba(255,255,255,0)" class="path-progress" />');
				html.push('</svg>');
				c.$element.html(html.join(""));

				c.$svg = c.$element.find("svg");
				c.$pathProgress = c.$element.find(".path-progress");
				c.$pathBg = c.$element.find(".path-bg");

				this.draw();

				return this;
			},
			draw: function() {
				var c = this.config,
					gap = c.gapSize / 180 * Math.PI,
					borderWidth = c.borderWidth * c.radius,
					adjustForBorder = borderWidth/2,
					radius = c.radius - adjustForBorder,
					rotate = (1 - c.gapSize / 360) * Math.PI, // 旋转的角度。由 (180 - c.gapSize/2) / 180 * Math.PI 得出
					template = "M{{startX}} {{startY}} A{{radius}} {{radius}} 0 {{radianSize}} {{rotationDirection}} {{endX}} {{endY}}";

				c.$svg.attr({
					"width": c.radius*2,
					"height": c.radius*2
				});

				//
				// 先绘制底色
				//
				var param = {
					"startX": 				(1+Math.sin(gap+rotate)) * radius + adjustForBorder,
					"startY": 				(1-Math.cos(gap+rotate)) * radius + adjustForBorder,
					"endX": 				(1+Math.sin(rotate)) * radius + adjustForBorder,
					"endY": 				(1-Math.cos(rotate)) * radius + adjustForBorder,
					"radius": 				radius,
					"rotationDirection": 	1,	// 旋转方向
					"radianSize": 			c.gapSize <= 180 ? 1 : 0	// 小弧/大弧
				},
				paramStr = template;
				for (var p in param) {
					paramStr = paramStr.split("{{"+ p +"}}").join(param[p]);
				}

				c.$pathBg.attr({
					"d": paramStr,
					"stroke-width": borderWidth
				});

				//
				// 绘制进度
				//
				if (c.completedRatio > 0) {
					// 完成度100%
					if (c.completedRatio >= 100) {
						// 将背景弧改色
						c.$pathBg.attr("stroke", c.lightBorderColor);
					}
					else {
						var ratioAngle = (c.completedRatio / 100) * (1 - c.gapSize / 360) * 360;
						// 转换为弧度单位
						var ratioRadian = ratioAngle / 180 * Math.PI;

						$.extend(param, {
							"endX": (1+Math.sin(Math.PI-ratioRadian+rotate)) * radius + adjustForBorder,
							"endY": (1-Math.cos(rotate-ratioRadian)) * radius + adjustForBorder,
							"radianSize": ratioAngle <= 180 ? 0 : 1
						});
						paramStr = template;
						for (var p in param) {
							paramStr = paramStr.split("{{"+ p +"}}").join(param[p]);
						}

						c.$pathProgress.attr({
							"d": paramStr,
							"stroke-width": borderWidth
						});
					}
				}

				// 写入属性

				return this;
			}
		};
		SVGChart.prototype.constructor = SVGChart;

		$.fn.SVGChart = function(config) {
			var svgChart = this.data("SVGChart");
			if (!svgChart) {
				svgChart = new SVGChart(this, config);
				this.data("SVGChart", svgChart);
			}
			return svgChart;
		};

		var $svgWrapper = $("#j_chart_svg");
		var $propertiesWrapper = $(".sc_base-properties_p");
		var height = $propertiesWrapper.height();
		var radius = height / 1.15263;
		var svgChart = $svgWrapper.SVGChart({ radius: radius });

		$svgWrapper.data("previousHeight", height);

/*		$(window).resize(function () {
			var height = $propertiesWrapper.height();
			if (height !== $svgWrapper.data("previousHeight")) {
				svgChart.config.radius = height / 1.15263;
				svgChart.draw();
				$svgWrapper.data("previousHeight", height);
			}
		});*/
	})();
});
