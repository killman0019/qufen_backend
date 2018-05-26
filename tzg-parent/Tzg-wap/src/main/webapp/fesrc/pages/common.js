define(function(require) {
	var $ = require("zepto");

	// 菜单切换
	(function () {
		var $switchBtn = $("#j_nav_switch");
		var $nav = $("#j_primary_nav");
		$nav.removeClass("-opened");

		$switchBtn.on("tap", function (e) {
			e.preventDefault();
			$nav.addClass("-opened");
		});
		$nav.on("tap", function (e) {
			$nav.removeClass("-opened");
		});
		$nav.find(".primary-nav_inner").on("tap", function (e) {
			e.stopPropagation();
		});
	})();
});