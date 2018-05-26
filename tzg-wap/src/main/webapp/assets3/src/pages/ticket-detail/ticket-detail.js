/**
 * 加息券列表
 * 
 * Author: 吴岸林
 */

define(['jquery', 'com/list-loader', 'com/jquery.form'], function ($, ListLoader) {

	// 列表加载
	// ========================================================
	// 
	(function() {
		var $tabs = $('#tab li');
		var $tabContents = $('[j-tab-content]');
		var activeIndex = 0;

		function swtichTab($tab) {
			var index = $tab.data('tabIndex');
			if (index == activeIndex) {
				return;
			}

			var $currentTabContent = $tabContents.eq(index);
			if ($currentTabContent.attr('loaded') != 'yes') {
				$currentTabContent.attr('loaded', 'yes');
				$tab.data('listLoader').load();
			}
			$tab.addClass('-active');
			$currentTabContent.show();

			$tabs.eq(activeIndex).removeClass('-active');
			$tabContents.eq(activeIndex).hide();

			activeIndex = index;
		};

		$tabs.each(function(i, tab) {
			var $tab = $(tab);
			var $tabContent = $tabContents.eq(i);

			var url = $tab.find('a').attr('href');

			var loader = new ListLoader({
				requestURL: url,
				template: $tabContent.find('[j-tpl]'),
				container: $tabContent.find('[j-list-container]'),
				loadMoreBtn: $tabContent.find('[j-loadmore]'),
				hasLoadAllPages: function(data) {
					return data.ticketPage && data.ticketPage.pageSize <= this.currentPage || data.ticketPage && data.ticketPage.rows && data.ticketPage.rows.length === 0;
				},
				preParseHTML: function(data) {
					var formatData = null;
					if (data) {
						formatData = {
							'basePath': window.basePath
						};
						if (data.ticketPage && data.ticketPage.rows && data.ticketPage.rows.length) {
							var rows = data.ticketPage.rows,
								formatRows = {};
							var row, year;
							for (var i=0,l=rows.length; i<l; i++) {
								row = rows[i];
								year = row.dtReceiveStr.substr(0, 4);
								if (!(year in formatRows)) {
									formatRows[year] = [];
								}
								formatRows[year].push(row);
							}
						}
						formatData.formatRows = formatRows;
					}
					return formatData;
				}
			});
			$tab.data('listLoader', loader);
			$tab.data('tabIndex', i);

			$tab.on('click', function(e) {
				e.preventDefault();
				if ($tab.hasClass('-active')) {
					return;
				}
				swtichTab($tab);
			});
		});

		var $currentTab = $tabs.filter('.-active');
		var nextIndex = $currentTab.data('tabIndex') + 1;
		if (nextIndex >= $tabs.length) {
			nextIndex = 0;
		}

		activeIndex = nextIndex;
		swtichTab($currentTab);
	})();
	
	// 红包兑换
	// ========================================================
	// 
	(function() {
		var windowHeight = $(window).innerHeight();
		var $dialog = $("#dialog");
		var $dialogWindow = $dialog.find(".simple-dialog_window");
		var $form = $("#exchange_form");
		var $input = $form.find('[name=code]');

		$dialogWindow.on("click", "[j-cancel]", function(e) {
			e.preventDefault();
			$dialog.hide();
		});

		function openDialog() {
			$dialog.show();
			var dialogHeight = $dialogWindow.height();
			$dialogWindow.css('marginTop', (windowHeight - dialogHeight) / 2.5);
		};

		// 表单校验
		$form.ajaxForm({
			validate: {
				rules: {
					code: {
						required: true
					}
				},
				messages: {
					code: {
						required: "请输入兑换码"
					}
				}
			},
			success: function (data) {
				if (data.success) {
					alert("兑换成功");
					$dialog.hide();
					self.location.reload();
				}
			}
		});

		$("#exchange_btn").on("click", function(e) {
			e.preventDefault();
			openDialog();
		});
	})();

	// 使用规则
	// ========================================================
	// 
	(function() {
		var $btn = $('#rule_btn');
		var $rule = $('#rule_dialog');
		var $ruleWindow = $rule.find('[j-window]');

		var windowHeight = $(window).innerHeight();

		$rule.on('click', '[j-close], [j-ok]', function(e) {
			e.preventDefault();
			$rule.removeClass('-open');
		});

		if ($btn.length === 0) {
			return;
		}
		$btn.on('click', function(e) {
			e.preventDefault();
			var dialogHeight = $ruleWindow.height();
			$rule.addClass('-open');
			$ruleWindow.css('marginTop', (windowHeight - dialogHeight) / 2.5);
		});
	})();
});
