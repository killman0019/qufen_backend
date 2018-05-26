/*-------------------------------*\
	掌薪计划(定期宝)投资记录
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

    var ListLoader = require("module/list-loader");

	var loader = new ListLoader({
        requestURL: $("#j_list_container").data("requestUrl"),
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
		}
	});
	loader.load();
});
