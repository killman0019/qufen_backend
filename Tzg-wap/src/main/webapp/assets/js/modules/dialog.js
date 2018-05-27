define(function (require) {
	var $ = require("jquery");

	var dialog = {};

	var html = [];
	html.push('<div class="dialog2" id="j_dialog">');
	html.push('  <div class="dialog2_box" j-dialog-box>');
	html.push('    <div class="dialog2_hd" j-dialog-header></div>');
	html.push('    <div class="dialog2_bd" j-dialog-body></div>');
	html.push('    <div class="dialog2_ft" j-dialog-footer></div>');
	html.push('  </div>');
	html.push('</div>');
	dialog.tpl = html.join("");

	$dialog = $("j_dialog");
	// 插入
	if ($dialog.length === 0) {
		$dialog = $(dialog.tpl);
		$dialog.appendTo(document.body);
	}

	dialog.elements = {
		$dialog: $dialog,
		$dialogBox: $dialog.find("[j-dialog-box]"),
		$dialogHeader: $dialog.find("[j-dialog-header]"),
		$dialogBody: $dialog.find("[j-dialog-body]"),
		$dialogFooter: $dialog.find("[j-dialog-footer]")
	};

	dialog.config = {
		closeCallback: null
	};

	dialog.close = function() {
		this.elements.$dialog.css("left", "-100%");
		if ($.isFunction(this.config.closeCallback)) {
			this.config.closeCallback();
		}
	};

	dialog.open = function(opt) {
		var realOpt = $.extend({
			title: "消息",
			content: "",
			ft: null,
			closeCallback: null
		}, opt);

		this.config.closeCallback = realOpt.closeCallback;

		if (realOpt.ft == null) {
			realOpt.ft = '<a href="#" j-action-cancel>取消</a><a href="#" j-action-ok>确定</a>';
		}

		var es = this.elements;

		es.$dialogHeader.html(realOpt.title);
		es.$dialogBody.html(realOpt.content || "");
		es.$dialogFooter.html(realOpt.ft || "");

		es.$dialog.css("left", 0);
		es.$dialogBox.css("marginTop", (es.$dialog.height() - es.$dialogBox.height()) / 2.5);
	};

	$dialog.on("click", "[j-action-cancel], [j-action-ok]", function (e) {
		e.preventDefault();
		dialog.close();
	});

	return dialog;
});

