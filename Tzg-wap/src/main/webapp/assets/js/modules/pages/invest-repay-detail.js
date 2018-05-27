/*--------------------------*\
	回款明细
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");
	var ListLoader = require("module/list-loader");

	var loader = new ListLoader({
		params: {"istate": 1},
		requestURL: basePath + "/user/investRepayDetail/list"
	});
	loader.load();
});
