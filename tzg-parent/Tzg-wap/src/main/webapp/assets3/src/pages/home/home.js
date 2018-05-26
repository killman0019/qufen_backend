/**
 * 网站首页
 * 
 * Author: 吴岸林
 */

define([
	"jquery",
	"com/slide"
], function ($, Slide) {
	// 轮播图
	// =======================================
	// 
	(function() {
		var imageLoadTimeout = 2;
		var allImageLoaded = false;

		// 所有图片
		var handleAllImageLoaded = function() {
			if (allImageLoaded) {
				return;
			}
			allImageLoaded = true;
			$("#j_main_slide [j-slide-images]").css("height", "auto");
		};
		setTimeout(handleAllImageLoaded, imageLoadTimeout * 1000);

		var $images = $("#j_main_slide img");
		$images.on("load error readystatechange", function() {
			if (this.complete || this.readyState == "complete" || this.readyState == "loaded") {
				handleAllImageLoaded();
			}
		});

		var slide = new Slide({
			"eWrap": "#j_main_slide",
			"eImagesWrap": "[j-slide-images]",
			"eNavWrap": "[j-slide-nav]",
			"currentNavClass": "-current"
		});
	})();

	// 饼图
	// =======================================
	// 
	(function() {
		var $chartWrap = $("#j_subject_chart");
		var $circle = $("#j_bg_circle");
		var $path = $("#j_completed_path");
		var cp = $path.data("completed") / 100;	// 完成度(百分比值)

		var borderWidthStr = $circle.attr("stroke-width");
		var borderWidthPercent = parseFloat(borderWidthStr) / 100;
		var prevSize = $chartWrap.width();
		if (cp <= 0) {
			return;
		}

		// 完成100%。直接改变底圈的颜色
		if (cp >= 1) {
			$circle.attr("stroke", "#ff5c25");
			return;
		}

		function drawChart() {
			var angle = cp * 2 * Math.PI;
			var size = $chartWrap.width();
			var radius = size / 2 * (1 - borderWidthPercent);
			var m = size / 2 * borderWidthPercent;	// 位移

			var parameters = {
				"startX": radius + m,
				"startY": m,
				"radius": radius,
				"endX": (1 + Math.sin(angle)) * radius + m,
				"endY": (1 - Math.cos(angle)) * radius + m,
				"rotationDirection": cp <= 0.5 ? 0 : 1	// 旋转方向
			};
			var paramStr = "M{{startX}} {{startY}} A{{radius}} {{radius}} 0 {{rotationDirection}} 1 {{endX}} {{endY}}";

			for (var p in parameters) {
				paramStr = paramStr.split("{{"+ p +"}}").join(parameters[p]);
			}

			$path.attr("d", paramStr).attr("stroke-width", borderWidthStr);
		}

		drawChart();

	})();
});
