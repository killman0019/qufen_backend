/*--------------------------*\
	Topbar 2
\*--------------------------*/

define(function (require) {
	var $ = require("$");

	$.fn.topbar = function (config) {
		var c = $.extend({listLoader: null}, config);
		var $topbar = this;
		$topbar.find(".f-hd-v2-title").on(tzg.config.CLICK_EVENT, function (e) {
			$topbar.toggleClass("-open");
			e.stopPropagation();
		});
		$(document).on(tzg.config.CLICK_EVENT, function () {
			$topbar.removeClass("-open");
		});

		var $items = $topbar.find(".m-topbar-sub a");
		$items.on(tzg.config.CLICK_EVENT, function (e) {
			e.preventDefault();
			var $this = $(this);
			if ($this.hasClass("z-atv")) {
				return;
			}

			$items.filter(".z-atv").removeClass("z-atv");
			$this.addClass("z-atv");

			if (c.listLoader) {
				var param = null;
				eval("param=" + $this.data("param"));
				$.extend(true, c.listLoader.config, {
					params: param,
					currentPage: 1
				});
				c.listLoader.load();
			}
		});
	};
});
