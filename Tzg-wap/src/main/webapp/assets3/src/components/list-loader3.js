/*--------------------------*\
	List Loader for v3 版
\*--------------------------*/

define(["jquery", "com/juicer"], function ($, juicer) {

	// 构造函数
	var ListLoader = function (options) {
		var _s = this,
			s = arguments.callee,
			o = {};

		_s.options = $.extend(o, s.defaultOptions, options);

		if (o.requestURL == null) {
			return false;
		}

		_s.requestData = {};
		_s.requestData[o.pageParamName] = 1;

		_s.requestParams = {
			"type": 	"GET",
			"dataType": "json",
			"data": 	_s.requestData,
			"complete": s.completeHandler,
			"error": 	s.errorHandler,
			"success": 	s.successHandler,
			"context": 	_s
		};

		// DOM 提取
		$.each(o, function (name, value) {
			if (name.indexOf("$") === 0) {
				if (value && !(value instanceof $)) {
					o[name] = $(o[name]);
				}
			}
		});

		// 分页初始化
		if (o.$loadmore.length) {
			o.$loadmore.on('click', function(e) {
				e.preventDefault();
				var targetPage = o.getCurrentPage(_s.currentData) || 1;
				_s.setData(o.pageParamName, targetPage);
			});
		}

		// 模板
		if (o.$template.length) {
			o.templateStr = o.$template.html();
		}

		// 插入消息显示区
		if ($.isFunction(o.messagePlacement)) {
			o.$msg = o.messagePlacement(o.$listContainer);
		}

		// 重试按钮
		o.$msg.on("click", "[j-list-loader-reload-btn]", function (e) {
			// 加载中提示
			if (o.loadingTpl) {
				_s.msg(o.loadingTpl);
			}

			_s.request();
		});
	};

	// 静态属性/方法
	$.extend(ListLoader, {
		// 默认配置
		"defaultOptions": {
			"requestURL": 		null,
			"$listContainer": 	null,
			"$template": 		null,
			"$loadmore": 		'[j-loadmore]',
			"errorTpl": 		'<div class="no-result bg-white">对不起，请求出错。您可以 <a href="javascript:;" class="lc-primary" j-list-loader-reload-btn>重试</a>！</div>',
			"loadingTpl": 	'<div class="loading"></div>',
			"noResultTpl": 		'<div class="noresult">暂无记录！</div>',
			"pageParamName": 	"pageIndex",	// 分页参数名

			// 插入显示显示区
			"messagePlacement": function ($listContainer, listLoader) {
				if ($listContainer && $listContainer instanceof $) {
					var $msg = $('<div>');
					$msg.hide();
					$msg.insertAfter($listContainer.parents("table"));
					return $msg;
				}
				return null;
			},

			"successHandler": 	null,
			"completeHandler": 	null,
			"errorHandler": 	null,

			// 从返回的数据中返回页码
			"getPageCount": function (data) {
				return data && data.result && data.result.paginator && data.result.paginator.totalPages;
			},
			"getCurrentPage": function (data) {
				return data && data.result && data.result.curPageNum;
			},
			"getResultCount": function (data) {
				return data && data.result && data.result.rowCount;
			},
			// 本页记录数
			"getPageResultCount": function (data) {
				return data && data.result && data.result.rows && data.result.rows.length;
			}
		},

		"successHandler": function(data, textStatus, XMLHttpRequest) {
			var _s = this,
				o = this.options;

			var currentPage = _s.currentPage = o.getCurrentPage(data) || 1;

			if ($.isFunction(o.successHandler) && o.successHandler(_s, data, textStatus, XMLHttpRequest) === false) {
				return;
			}

			// 刷新分页
			var pageCount = o.getPageCount(data) || 0;
			var pageResultCount = o.getPageResultCount(data);
			_s.page(pageCount, currentPage);

			// 本页为空
			if (!pageResultCount) {
				o.$listContainer.html("");
				_s.msg(o.noResultTpl);
			}
			else {
				// 生成代码
				var html = juicer(o.templateStr, data);
				o.$listContainer.html(html);
				o.$msg.hide();
			}
		},

		"errorHandler": function(XMLHttpRequest, textStatus, errorThrown) {
			var _s = this,
				o = this.options;

			if ($.isFunction(o.errorHandler) && o.errorHandler(_s, XMLHttpRequest, textStatus, errorThrown) === false) {
				return;
			}

			_s.msg(o.errorTpl);
		},

		"completeHandler": null
	});


	// 原型
	$.extend(ListLoader.prototype, {
		request: function() {
			var _s = this,
				o = _s.options;

			if (_s.currentRequest && _s.currentRequest.readyState != 1 && _s.currentRequest.readyState != 4) {
				_s.currentRequest.blur();
			}
			_s.currentRequest = $.ajax(_s.options.requestURL, _s.requestParams);

			if (o.$msg && o.loadingTpl) {
				_s.msg(o.loadingTpl);
			} else if (o.$msg) {
				o.$msg.hide();
			}
		},

		setData: function(name, value, reload) {
			if (name) {
				this.requestData[name] = value;
				if (reload !== false) {
					this.request();
				}
			}
		},

		getData: function(name) {
			if (name && name in this.requestData) {
				return this.requestData[name];
			}
			return null;
		},

		setPageData: function(page, reload) {
			this.setData(this.options.pageParamName, page, reload);
		},

		resetData: function(reload) {
			if (this.requestData) {
				var datas = this.requestData;

				for (var p in datas) {
					datas[p] = p == this.options.pageParamName ? 1 : "";
				}
			}

			if (reload !== false) {
				this.request();
			}
		},

		// 翻页动作
		page: function(pageCount, currentPage) {
			var _s = this,
				p = _s.pagination;
			if (p) {
				if (pageCount != null) {
					p.options.pageCount = pageCount;
				}
				if (currentPage != null) {
					p.options.currentPage = currentPage;
				}
				p.refresh({
					"pageCount": pageCount,
					"currentPage": currentPage
				});
			}
		},

		// 显示消息的内容
		msg: function(html) {
			var _s = this,
				o = _s.options;

			if (o.$msg) {
				o.$msg.show().html(html);
			}
		}
	});

	return ListLoader;
});
