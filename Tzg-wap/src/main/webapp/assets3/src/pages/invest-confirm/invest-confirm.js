/**
 * 投资确认
 * 
 * Author: 吴岸林
 */

define(['jquery', 'com/jquery.form'], function($) {
	// 底部弹框
	// =====================================
	// 
	var pop, closePop;
	(function() {
		var $popup = $('#popup');
		$popup.on('click', '[j-close]', function(e) {
			e.preventDefault();
			$popup.removeClass('-open');
		});

		var $popupTitle = $popup.find('[j-title]');
		var $popupContent = $popup.find('[j-content]');

		pop = function(options) {
			var o = $.extend({
				title: '',
				content: '',
			}, options);

			$popupTitle.html(o.title);
			$popupContent.html(o.content);

			$popup.addClass('-open');
		};

		closePop = function() {
			$popup.removeClass('-open');
		};
	})();

	// 表单提交
	// =====================================
	//
	(function() {
		var $form = $('#invest_confirm_form');
		var $popup4form = $('#popup4form');
		$popup4form.on('click', '[j-close]', function(e) {
			e.preventDefault();
			$popup4form.removeClass('-open');
		});
		var $confirmBtn = $('#confirm_btn');
		$confirmBtn.on('click', function() {
			$popup4form.addClass('-open');
		});

		// 协议
		// 
		var $protocolPopup = $('#protocol_popup');
		$protocolPopup.on('click', '[j-close]', function(e) {
			e.preventDefault();
			$protocolPopup.removeClass('-open');
		});
		$('#protocol_btn').on('click', function() {
			$protocolPopup.addClass('-open');
		});
		//合同
		var $protocolPopup2 = $('#protocol_popup2');
		$protocolPopup2.on('click', '[j-close2]', function(e) {
			e.preventDefault();
			$protocolPopup2.removeClass('-open');
		});
		$('#protocol_btn2').on('click', function() {
			$protocolPopup2.addClass('-open');
		});
		// 表单提交
		// 
		var validator = $form.ajaxForm({
			validate: {
				rules: {
					payPassword: {
						required: true
					},
					agreeProtocol: {
						required: true
					}
				},
				messages: {
					payPassword: {
						required: "请输入交易密码"
					},
					agreeProtocol: {
						required: "必须同意相关协议"
					}
				},
				errorPlacement: function($error, $control) {
					switch( $control.attr("name") ) {
						case "agreeProtocol":
							$error.insertAfter($control.parents('.password-part_cols'));
							break;

						default:
							$error.insertAfter($control);
					}
				}
			}
		});
	})();

	// 计算预期收益及表单提交
	// =====================================
	//
	(function() {
		var $form = $('#invest_confirm_form');

		var $rewardRadios = $form.find('[name=rewardType]');
		if ($rewardRadios.length === 0) {
			return;
		}
		$rewardRadios.eq(0).prop('checked', true);

		var $actualAmount = $('#actual_amount');

		var $ticketRadio = $("#ticket_radio");
		if ($ticketRadio.length) {
			var $selectedTicket = $("#selected_ticket");
			var $selectedTicketId = $("#selected_ticket_id");
			var $ticketContent = $('#ticket_content');

			var calcRequest;
			var calcParams = {};
			$.each(["subjectId", "investAmt"], function(i, key) {
				calcParams[key] = $form.find("[name="+ key +"]").val();
			});
			function calculateExpectedEarning() {
				if (!$ticketRadio.prop("checked")) {
					return;
				}
			};

			$ticketRadio.on('click', $ticketContent.length ? function() {
					pop({
						title: '选择加息券',
						content: $ticketContent.html()
					});
					$actualAmount.html($actualAmount.data('original'));
				} : function() {
					$actualAmount.html($actualAmount.data('original'));
			});

			$(document).on('click', '.ticket-dataset tr', function(e) {
				e.preventDefault();
				var $this = $(this);
				var $radio = $this.find(':radio');
				$radio.prop('checked', true);
				closePop();

				$selectedTicket.html($radio.data('interestRate') + '%');
				$selectedTicketId.val($radio.val());
				calculateExpectedEarning();
			});
		}

		var $redpacketRadio = $('#redpacket_radio');
		if ($redpacketRadio.length) {
			$redpacketRadio.on('click', function() {
				$actualAmount.html($actualAmount.data("real"));
			});
		}
	})();
});
