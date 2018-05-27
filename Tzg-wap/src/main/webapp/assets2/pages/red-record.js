//
// 红包列表
//
define(function (require, exports, module) {
	var $ = require("zepto");

	(function () {
		var ListLoader = require("modules/list-loader");
		var loader = new ListLoader({
			params: {
				itypes: ""
			}
		});

		function getParam(hash) {
			if (!hash) {
				return false;
			}
			var r = hash.match(/^#itypes\=([^\=&]+)$/i);
			if (r != null) {
				return r[1];
			}
		}

		function load (type) {
			if (!type) {
				delete loader.config.params.itypes;
			}
			else {
				loader.config.params.itypes = type;
			}
			loader.load();
		}
		
		var $tabs = $("#j_red_tabs a");
		var $tablis = $tabs.parents();
		$tabs.each(function () {
			var $this = $(this);
			var param = getParam($this.attr("href"));
			$this.data("param", param);
		});

		$tabs.on("click", function () {
			var $this = $(this);
			var $parent = $this.parent();
			if ($parent.hasClass("-on")) {
				return;
			}

			$tablis.removeClass("-on");
			$parent.addClass("-on");

			load($this.data("param"));
		});

		// 默认选中
		var type = getParam(location.hash);
		if (type == "0") {
			type = false;
		}

		if (!type) {
			$tablis.eq(0).addClass("-on");
		}
		else {
			$tablis.removeClass("-on");
			$tabs.each(function () {
				var $this = $(this);
				if ($this.data("param") == type) {
					$this.parent().addClass("-on");
				}
			});
		}

		load(type);
	})();

	//
	// 红包兑换
	//
	(function () {
		var $openBtn = $("#j_open_exchange_btn");
		var $winWrapper = $("#j_win_wrapper");
		var $closeBtn = $winWrapper.find(".win_close");

		var $input = $("#j_redcode_ipt");
		var $msg = $("#j_form_msg");
		var $actionBtn = $("#j_exchange_btn");

		$openBtn.on("click", function (e) {
			e.preventDefault();
			$winWrapper.removeClass("-hidden");
			$winWrapper.removeClass("-msg");
		});

		$closeBtn.on("click", function (e) {
			e.preventDefault();
			$winWrapper.addClass("-hidden");
			$openBtn.focus();

			if ($winWrapper.hasClass("-msg")) {
				self.location.reload();
			}
		});

		$actionBtn.on("click", function () {
			var redCode = $input.val();
			if ($.trim(redCode) == "") {
				$msg.text("请输入兑换码");
				return;
			}
			$msg.text("");
			$.getJSON(basePath + "/user/redRecord/useCDkey?vcRedCode="+ redCode, function (data) {
				if (data.success) {
					$winWrapper.addClass("-msg");
				}
				else {
					$msg.text(data.msg);
					//$input.val("");
				}
			});
		});
		$input.on("input", function () {
			if ($msg.text()) {
				$msg.text("");
			}
		});
	})();
});
