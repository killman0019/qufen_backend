/**
 * 工具
 * 
 * Author: 吴岸林
 */

define({
	/**
	 * 将浏览器地址参数转换成json
	 * 
	 * @param query
	 * @returns
	 */
	queryToJSON: function (queryStr) {
		if (!(queryStr && typeof queryStr === "string")) {
			return null;
		}
		if (queryStr.indexOf("?") === 0) {
			queryStr = queryStr.substr(1);
		}
		var temp = queryStr.split("&");
		var json = {};
		for (var i in temp) {
			var item = temp[i].split("=");

			// 如果前面已经有该参，则表明是多选框
			if (json[item[0]]) {
				if ($.isArray(json[item[0]])) {
					json[item[0]].push(item[1]);
				}
				else {
					json[item[0]] = [json[item[0]], item[1]];
				}
			}
			else {
				json[item[0]] = item.length === 1 ? "" : item[1];
			}
		}
		return json;
	}
});
