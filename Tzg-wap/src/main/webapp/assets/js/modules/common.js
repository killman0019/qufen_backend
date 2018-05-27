/*--------------------------*\
	Common js
\*--------------------------*/

define(function (require) {
	var $ = require("$");

	var clickEventType = tzg.config.CLICK_EVENT;

	// 导航
	var $navSwitch = $("#j_nav_switch");
	var $nav = $("#j_primary_nav");
	var $navMask = $('<div style="position:fixed;left:0;right:0;top:0;bottom:0;z-index:5;display:none"></div>');

	$navSwitch.on(clickEventType, function (e) {
		$nav.toggleClass("z-opened");
		$navMask[$nav.hasClass("z-opened") ? "show" : "hide"]();
		//e.stopPropagation();
	});

	$navMask.appendTo(document.body);
	$navMask.on(clickEventType, function () {
		$nav.removeClass("z-opened");
		$navMask.hide();
	});
	// $("body").on(clickEventType, function () {
	// 	$nav.removeClass("z-opened");
	// });
});
