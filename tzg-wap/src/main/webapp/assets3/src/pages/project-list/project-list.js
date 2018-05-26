/**
 * 标的列表
 * 
 * Author: 吴岸林
 */

define(['jquery', 'com/list-loader'], function ($, ListLoader) {
	var loader = new ListLoader({
		template: "#list_template",
		container: "#project_list_container",
		loadMoreBtn: "#load_more",
		hasLoadAllPages: function(data) {	// 判断是否加载完所有分页
			return data.result == null || data.result.pageSize <= this.currentPage || data.result.rows.length === 0;
		}
	});
	loader.load();
});
