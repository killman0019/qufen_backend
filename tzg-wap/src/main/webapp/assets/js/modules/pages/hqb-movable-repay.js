/*-------------------------------*\
	(活期宝) 资金转出详情
\*-------------------------------*/
define(function (require, exports, module) {
	var $ = require("$");
	var ListLoader = require("module/list-loader");

	var loader = new ListLoader({
		requestURL: basePath + '/currentBao/funddetails/getRepayRecord',
		successHandler: function (data) {
			var html = this.parseHTML(data.result);
			var c = this.config;
			if (html) {
				c.currentPage == 1 ? c.$container.html(html) : c.$container.append(html);
			}
			else if (c.currentPage === 1) {
				this.emptyHandler();
			}
			
			// 所有记录加载完毕
			if (data.result == null || data.result.pageSize <= c.currentPage || data.result.rows.length === 0) {
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