/**
 * 列表异步加载
 * 
 * Author: 吴岸林
 */

define(['jquery', 'com/juicer', 'com/tools'], function ($, juicer, tools) {
	var ListLoader = function (config) {
		var defConfig = {
			requestURL: "list",					// 请求的地址
			dataType: "json",
			template: "[j-list-template]",		// 模板容器
			container: "[j-list-container]",	// 列表容器
			loadMoreBtn: "[j-load-more]",		// "加载更多"按钮
			params: null,						// 默认请求的参数
			sendCurrentPageParams: true,		// 是否加入当前页面的参数
			currentPage: 1,						// 当前页码
			pageQueryName: "pageIndex",			// 页码请求的参数名
			loadingHTML: '<div class="loading"></div>',
			noResultHTML: '<div class="noresult">暂无数据</div>',
			preParseHTML: function(data) {			// 生成HTML代码前处理数据
				if (data) {
					data.basePath = window.basePath;
				}
				return data;
			},
			hasLoadAllPages: function(data) {	// 判断是否加载完所有分页
				return data.pageSize <= this.currentPage || data.rows.length === 0;
			},
			successHandler: function (data) {
				var c = this.config;
				var formatData = c.preParseHTML(data);
				var html = this.parseHTML(formatData);
				if (html) {
					c.currentPage == 1 ? c.$container.html(html) : c.$container.append(html);

					// 所有记录加载完毕
					if (c.hasLoadAllPages(data)) {
						if (c.$loadMore) {
							c.$loadMore.hide();
						}
					} else {
						if (c.$loadMore) {
							c.$loadMore.attr("disabled", false).removeClass("-loading").show();
						}
					}
				} else if (c.currentPage === 1) {
					this.emptyHandler();
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
			c.$loadMore.on("click", function (e) {
				e.preventDefault();
				$(this).addClass("-loading");
				c.currentPage++;
				_t.load();
			});
		} else {
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
					//_t.config.$loadMore.show().removeClass("-loading");
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
				c.$container.html(c.noResultHTML);
			}
			if (c.$loadMore) {
				c.$loadMore.hide();
			}
		}
	};

	return ListLoader;
});
