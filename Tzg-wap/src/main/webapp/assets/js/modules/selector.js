/*--------------------------*\
	仿下拉框
	用于充值等页面
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	var Selector = function($selector, config) {
		var defConfig = {
			"menuTitle": "",
			"menu": "[j-menu]",
			"menuItems": [], // 表单项数据,数组形式。如：[{"mark": 1, "text": "中国银行"}]
			"openClass": "-open",
			"currentMenuItemClass": "-current",
			"leftOperator": "[j-operator-left]",
			"rightOperator": "[j-operator-right]",
			"leftOperatorHtml": '',
			"rightOperatorHtml": '<a href="#" j-finish>完成</a>',
			"selectHandler": null,
			"finishHandler": null
		};

		if (!($selector instanceof $ && $selector.length)) {
			return false;
		}

		var c = this.config = $.extend({
			"$wrapper": $selector,
			"menuConfig": {}
		}, defConfig, config);

		// 初始化
		this.init();
	};
	$.extend(Selector.prototype, {
		init: function() {
			var _self = this;
			var c = _self.config;

			c.$wrapper.removeClass(c.openClass);

			var queries = "menu leftOperator rightOperator".split(" ");
			for (var i = 0, l = queries.length; i < l; i++) {
				c["$" + queries[i]] = c.$wrapper.find(c[queries[i]]);
			}

			c.$leftOperator.html(c.leftOperatorHtml);
			c.$rightOperator.html(c.rightOperatorHtml);

			c.$wrapper.find("[j-menu-title]").text(c.menuTitle);

			_self.generateItems(c.menuItems);

			// 绑定事件
			c.$wrapper.on("click", "[j-finish]", function(e) {
				if ($.isFunction(c.finishHandler) && c.finishHandler(c.menuConfig.selectedIndex, _self) === false) {
					return;
				}
				e.preventDefault();
				_self.close();
			});

			c.$menu.on("click", "a", function(e) {
				e.preventDefault();

				var $this = $(this),
					klass = c.currentMenuItemClass;

				if ($.isFunction(c.selectHandler) && c.selectHandler($this.data("menuIndex"), _self) === false) {
					return;
				}

				if (!$this.hasClass(klass)) {
					c.$menu.find("." + klass).removeClass(klass);
					$this.addClass(klass);
					c.menuConfig.selectedIndex = $this.data("menuIndex");
				}
			});
		},
		// 生成表单项
		generateItems: function(menuItems) {
			var c = this.config,
				html = "",
				menuItem,
				i,
				l;

			if (menuItems && $.isArray(menuItems) && menuItems.length) {
				html = [];
				for (i = 0, l = menuItems.length; i < l; i++) {
					menuItem = menuItems[i];
					if (menuItem && typeof menuItem === "object") {
						html.push('<a href="#" class="j-menu-index-'+ i +'" data-menu-index="' + i + '" data-menu-id="' + (menuItem.id || "") + '">' + (menuItem.name || "") + '</a>');
					}
				}
				html = html.join("");
			}

			c.$menu.html(html);
			c.menuConfig.selectedIndex = -1;

			return this;
		},
		setMenuItems: function(menuItems) {
			this.config.menuItems = menuItems;
			this.generateItems(menuItems);
		},
		open: function(index) {
			var c = this.config,
				klass = this.config.currentMenuItemClass;;

			c.$wrapper.addClass(c.openClass);

			if (index == null) {
				c.$menu.scrollTop(0);
				c.$menu.find("." + klass).removeClass(klass);
			} else {
				var $current = c.$menu.find(".j-menu-index-" + index);
				if ($current.length && !$current.hasClass(klass)) {
					c.$menu.find("." + klass).removeClass(klass);
					$current.addClass(klass);
					c.menuConfig.selectedIndex = $current.data("menuIndex");
					// 是否被遮住
					var scrollTop = c.$menu.scrollTop();
					var menuHeight = c.$menu.outerHeight();
					var currentItemTop = $current.position().top;
					// 被上面遮住
					if (scrollTop > currentItemTop) {
						c.$menu.scrollTop(currentItemTop);
					}
					// 被下面遮住
					else if (scrollTop + menuHeight < currentItemTop + $current.outerHeight()) {
						c.$menu.scrollTop(currentItemTop + $current.outerHeight() - menuHeight);
					}
				}
			}

			return this;
		},
		close: function() {
			var c = this.config;
			c.$wrapper.removeClass(c.openClass);
			return this;
		}
	});

	$.fn.tzgSelector = function(config) {
		function init($selector, config) {
			var selector = $selector.data("tzgSelector");
			if (!selector) {
				selector = new Selector($selector, config);
			}
			$selector.data("tzgSelector", selector);
			return selector;
		};

		if (this.length > 1) {
			this.each(function() {
				init($(this), config);
			});
		} else {
			return init(this, config);
		}
	};
});