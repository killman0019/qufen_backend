/*--------------------------*\
	项目信息
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	// 相关材料
	var $loadMore = $("#j_load_more");
	var $fileList = $("#j_file_list");
	if ($loadMore.size()) {
		$loadMore.on(tzg.config.CLICK_EVENT, function (e) {
			e.preventDefault();

			$fileList.find("li:hidden").each(function (i, element) {
				var $element = $(element);
				var $img = $element.find("img");
				$img.attr("src", $img.data("original-src"));
				$element.show();
			});

			$loadMore.hide();
		});
	}

	require("component/fancyBox/jquery.fancybox.pack");
	require("component/fancyBox/jquery.fancybox.css");
	var $imgLinks = $fileList.find("a");
	$imgLinks.fancybox();
	//$fileList.find("img").mlightbox();
});
