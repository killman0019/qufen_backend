/*--------------------------*\
	List Loader
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");
	var juicer = require("module/juicer");
	var tools = require("module/tools");

	var ListLoader = function (config) {
		var defConfig = {
			requestURL: "list",					// 请求的地址
			dataType: "json",
			template: "#j_list_template",		// 模板容器
			container: "#j_list_container",		// 列表容器
			loadMoreBtn: "#j_load_more",		// "加载更多"按钮
			params: null,						// 默认请求的参数
			sendCurrentPageParams: true,		// 是否加入当前页面的参数
			currentPage: 1,						// 当前页码
			pageQueryName: "pageIndex",			// 页码请求的参数名
			loadingHTML: '<div class="f-mtb2 f-tlc"><i class="u-fntIco--ldg"></i></div>',
			successHandler: function (data) {
				var html = this.parseHTML(data);
				var c = this.config;
				if (html) {
					c.currentPage == 1 ? c.$container.html(html) : c.$container.append(html);
				}
				else if (c.currentPage === 1) {
					this.emptyHandler();
				}
				
				// 所有记录加载完毕
				if (data.pageSize <= c.currentPage || data.rows.length === 0) {
					if (c.$loadMore) {
						//$('<div class="u-lstHnt">没有更多了</div>').insertBefore(c.$loadMore);
						c.$loadMore.hide();
					}
				}
				else {
					if (c.$loadMore) {
						c.$loadMore.attr("disabled", false).removeClass("z-ldg").show();
					}
				}
			},
			errorHandler: function () {},
			completeHandler: function (xhr, status) {
				var c = this.config;
				var response = $.trim(xhr.responseText);
				// 返回为空
				if (!response) {
					this.emptyHandler();
				}
			}
		};
		var _t = this;
		var c = _t.config = $.extend({}, defConfig, config);

		c.templateStr = $(c.template).html();
		c.$container = $(c.container);

		c.$loadMore = $(c.loadMoreBtn);
		if (c.$loadMore.size()) {
			c.$loadMore.on(tzg.config.CLICK_EVENT, function (e) {
				e.preventDefault();
				$(this).addClass("z-ldg");
				c.currentPage++;
				_t.load();
			}).hide();
		}
		else {
			c.$loadMore = null;
		}
	};
	ListLoader.prototype = {
		load: function () {
			var _t = this;
			var c = _t.config;
			if (!(c.templateStr && c.$container.size())) {
				return;
			}

			if (c.currentPage === 1) {
				c.$container.html(c.loadingHTML);
			}

			var params = c.sendCurrentPageParams ? (tools.queryToJSON(self.location.search) || {}) : {};

			params[c.pageQueryName] = c.currentPage;
			params = $.extend({}, c.params, params);
			$.ajax(c.requestURL, {
				"context": _t,
				"type": "GET",
				"data": params,
				"dataType": c.dataType,
				"success": c.successHandler,
				"error": c.errorHandler,
				"complete": function (xhr, status) {
					var _t = this;
					if (c.completeHandler.call(_t, xhr, status) === false) {
						return;
					}
					//_t.config.$loadMore.show().removeClass("z-ldg");
				}
			});
		},
		parseHTML: function (data) {
			var c = this.config;
			if (!(data && c.templateStr && c.$container.size())) {
				return "";
			}
			data.basePath = basePath;
			return $.trim(juicer(c.templateStr, data));
		},
		emptyHandler: function (data) {
			var c = this.config;
			if (c.currentPage === 1) {
				c.$container.html('<div class="u-lstHnt z-pg">暂无数据</div>');
			}
			if (c.$loadMore) {
				c.$loadMore.hide();
			}
		}
	};

	module.exports = ListLoader;
});
