/*--------------------------*\
	会员中心 投资记录
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");
	var ListLoader = require("module/list-loader");

	var loader = new ListLoader();
	loader.load();

	require("module/jquery.topbar2");
	$("#j_topbar").topbar({ "listLoader": loader });
});
