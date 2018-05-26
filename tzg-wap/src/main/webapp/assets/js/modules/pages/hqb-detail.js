/*-------------------------------*\
	掌薪计划(定期宝)详情
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");
/*
	// 模拟弹框
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    $.fn.alert = function (msg, buttonHandler, buttonText) {
		if (this.data("alertInited") != "yes") {
			this.on("click", function() {
				this.style.display = "none";
			});
			this.on("click", ".xm-dialog_btn", function () {
				var $wrapper = $(this).parents(".xm-dialog");
				var buttonHandler = $wrapper.data("buttonHandler");
				if (buttonHandler && buttonHandler()) {
					buttonHandler();
					return;
				}
				$wrapper.hide();
			});
			this.on("click", ".xm-dialog_confirm_btn", function () {
				var $wrapper = $(this).parents(".xm-dialog");
				$wrapper.hide();
			});

			this.on("click", ".xm-dialog_win", function (e) {
				e.stopPropagation();
			})
			this.data("alertInited", "yes");
		}

		if (!msg) {
			msg = "";
		}

		this.find(".xm-dialog_content").html(msg);
		this.data("buttonHandler", buttonHandler && $.isFunction(buttonHandler) ? buttonHandler : false);
		this.find(".xm-dialog_btn").text(buttonText || "确 定");
		this.show();
	};

    var $alert = $("#j_xm_dialog");

	function alert() {
		$alert.alert.apply($alert, arguments);
	};


	$("#j_enter-btn").on("click", function(){
		$.post( basePath + "/currentBao/tochargein",function(data){
			if(data.success){
				self.location.href = basePath + data.redirectURL;
			}else{
				alert("<div class='xm-dialog_title'>提示</div><div class='con-text'>"+data.msg+"</div>", function(){self.location.href = basePath + data.redirectURL}, data.flag == 3 ? "设 置" : "充 值" );			
			}
		});
	});
*/

	// 转入转出校验
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    var dialog = require("module/dialog");
	var $dialog = dialog.elements.$dialog;

	$("#j_footbar [data-verify]").each(function (i, btn) {
		var $btn = $(btn);
		var verifyURL = $btn.data("verify");
		if (verifyURL.indexOf("http") < 0) {
			verifyURL = basePath + verifyURL;
		}

		$btn.on("click", function (e) {
			e.preventDefault();
			if ($btn.prop("disabled")) {
				return;
			}
			$btn.prop("disabled", true);
			// 铜钱宝状态判断
			$.ajax(verifyURL, {
				type: "post",
				dataType: "json",
				data: {
					m: parseInt(Math.random() * 1000000)
				},
				complete: function() {
					$btn.prop("disabled", false);
				},
				success: function(data) {
					var redirectURL = data.redirectURL || data.redirect_url;
					if (redirectURL && redirectURL.indexOf("http") < 0) {
						redirectURL = basePath + redirectURL;
					}
					if (data && data.success) {
						if (redirectURL) {
							self.location.href = redirectURL;
						}
					}
					else {
						dialog.open({
							title: "消息",
							content: data.msg || '出错',
							ft: '<a href="#" j-action-cancel>取消</a><a href="'+ redirectURL +'">确定</a>'
						});
					}
				}
			});
		});
	});

	// 图表
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	(function() {
		var Chart = require("module/chart");

		var $tabs = $("#j_chart_tab li");
		var $contents = $("#j_main_block [j-tab-content]");
		
		var currentClass = "z-atv";
		var currentIndex = -1;
		
		$tabs.each(function(index) {
			$(this).data("theTabIndex", index);
		});
		$tabs.on("click", function(e) {
			e.preventDefault();
			switchTab($(this).data("theTabIndex"));
		});
		
		var $canvas = $("#j_main_block canvas");
		var originalDatasets = window.chartDatasets;
		var dataLabelNames = ["daysFor7IncomeRate", "daysFor7Income"];
		var chartLabelNames = ["铜钱宝七日年利率走势(%)", "我的七日收益走势(元)"];
		
		function initChart(index) {
			var canvas = $canvas.get(index);
			var ctx = canvas.getContext("2d");
			var originalDataset = originalDatasets[index];
			var maxY = originalDataset.maxNumY;

			originalDataset = originalDataset[dataLabelNames[index]];

			var dataLabels = [];
			var datas = [];
			var temp;
			for (var i=0,l=originalDataset.length; i<l; i++) {
				temp = originalDataset[i];
				dataLabels.push(temp.dateX);
				datas.push(temp.numY);
			}

			var chartData = {
				"labels": dataLabels,
				"datasets": [
				    {
				    	label: chartLabelNames[index],
						fillColor: "transparent",
						strokeColor: "#f75263",
						pointColor: "#f75263",
						pointStrokeColor: "#f75263",
						pointHighlightFill: "#f75263",
						pointHighlightStroke: "#000",
						data: datas
				    }
				]
			};

			var opt = {
				animation: false,
				bezierCurve: false,
				responsive: true
			};
			
			if (maxY <= 0) {
				$.extend(opt, {
					scaleOverride: true,
					scaleSteps: 1,
					scaleStepWidth: 1
				});
			}
			else if (maxY < 0.1) {
				$.extend(opt, {
					scaleOverride: true,
					scaleSteps: 5,
					scaleStepWidth: 0.02
				});
			}
			else if (maxY < 1) {
				$.extend(opt, {
					scaleOverride: true,
					scaleSteps: 5,
					scaleStepWidth: 0.2
				});
			}
			else {
				$.extend(opt, {
					scaleOverride: true,
					scaleSteps: 5,
					scaleStepWidth: Math.ceil(maxY / 5)
				});
			}
			
			(new Chart(ctx)).Line(chartData, opt);
		};
		
		function switchTab(index) {
			var $this = $tabs.eq(index);
			var thisIndex = $this.data("theTabIndex");
			if (thisIndex == currentIndex) {
				return;
			}
			
			$this.addClass(currentClass);
			$tabs.eq(currentIndex).removeClass(currentClass);
			
			$contents.eq(thisIndex).show();
			$contents.eq(currentIndex).hide();
			
			currentIndex = thisIndex;
			
			if ($this.data("chartInited") != "yes") {
				initChart(thisIndex);
				$this.data("chartInited", "yes");
			}
		};
		
		switchTab(0);
	})();
});
