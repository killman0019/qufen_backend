/*
 * 将当前url中的query部分添加到页面的链接中
 */
define(function(require, exports, module) {
	var $ = require("jquery");
	
	module.exports = function(queryName, $links) {
		var s = location.search,
			regexp = new RegExp(queryName + "\=[^\&\=]*", "g"),
			targetQuery;
		
		if (s && queryName) {
			targetQuery = s.match(regexp);
			if (targetQuery) {
				targetQuery = targetQuery[0];
			}
		}
		
		if (targetQuery && $links instanceof $ && $links.length) {
			$links.each(function() {
				var $this = $(this);
				var href = $this.attr("href");
				
				if (href.indexOf(queryName + "=") > -1) {
					$this.attr("href", href.replace(regexp, targetQuery));
				}
				else {
					$this.attr("href", href + ( href.indexOf("?") > -1 ? "&" : "?" ) + targetQuery);
				}
			});
		}
	};
});
