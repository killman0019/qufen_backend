define(function (require) {
	var $ = require("zepto");

	// 相关材料
	var $loadMore = $("#j_load_more");
	var $fileList = $("#j_file_list");
	if ($loadMore.size()) {
		$loadMore.on("click", function (e) {
			e.preventDefault();

			$fileList.find(".j-hide").each(function (i, element) {
				var $element = $(element);
				var $img = $element.find("img");
				$img.attr("src", $img.data("original-src"));
				$element.show();
			});

			$loadMore.hide();
		});
	}

	require("components/magnific-popup/jquery.magnific-popup");
	$fileList.find('.j-img').magnificPopup({type:'image'});

});
