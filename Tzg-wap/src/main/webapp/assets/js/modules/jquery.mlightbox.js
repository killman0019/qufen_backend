/*--------------------------*\
	Lightbox for phone
\*--------------------------*/

define(function (require) {
	var $ = require("$");

	// 创建遮罩
	var $mask = $('<div></div>').css({
		position: "fixed",
		top: 0,
		right: 0,
		bottom: 0,
		left: 0,
		zIndex: 10,
		backgroundColor: "rgba(0,0,0,0.75)",
		display: "none"
	});
	$mask.appendTo("body");

	$.fn.mlightbox = function () {
		
	};
});
