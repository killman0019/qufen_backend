/**
 * 人人赚
 * 
 * Author: 吴岸林
 */

define(['jquery'], function($) {
	var $qrcodePopup = $('#qrcode_popup');
	$qrcodePopup.click(function(e) {
		e.preventDefault();
		$qrcodePopup.removeClass('-open');
	});
	$('#open_qrcode_popup').click(function(e) {
		e.preventDefault();
		$qrcodePopup.addClass('-open');
	});
});
