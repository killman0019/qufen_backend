/*-------------------------------*\
	人人赚 余额转出
\*-------------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	require("module/jquery.form");

	// 模拟弹框
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    $.fn.alert = function (msg, buttonHandler, buttonText) {
		if (this.data("alertInited") != "yes") {
			this.on("click", function(e) {
				e.stopPropagation();
				//$(this).find(".xm-dialog_win").removeClass("z-opened");
				//$(this).fadeOut();
			});
			this.on("click", ".xm-dialog_btn", function () {
				var $wrapper = $(this).parents(".xm-dialog");
				var buttonHandler = $wrapper.data("buttonHandler");
				if (buttonHandler && buttonHandler()) {
					buttonHandler();
					return;
				}
				$wrapper.fadeOut().find(".xm-dialog_win").removeClass("z-opened");
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
		this.fadeIn().find(".xm-dialog_win").addClass("z-opened");
	};

    var $alert = $("#j_xm_dialog");

	function alert() {
		$alert.alert.apply($alert, arguments);
	};

	$.validator.addMethod("amount", function (amount) {
		return (/^\d+(\.\d{1,2}){0,}$/g).test(amount+"");
	});

	var $out_form = $("#j_out-form");

	var $back_url = "/user/pyramid";

	location.href.indexOf("/wap") >= 0 ? $back_url += "/wap/home" : $back_url += "/home";

	// 最大可转出金额
	var maxOutAmount = $out_form.find("[name=maxOutAmt]").data("value");
	maxOutAmount = Number(maxOutAmount);

	function toCurrency(number){
		return number.indexOf(".")>=0?number:number+".00";
	}

	$out_form.ajaxForm({
		validate: {
			rules: {
				numCash: {
					required: true,
					number: true,
					amount: true,
					min: 0.01,
					max: maxOutAmount
				}
			},
			messages: {
				numCash: {
					required: "请输入转出金额",
					number: "转出金额不正确",
					amount: "转出金额不正确",
					min: "转出金额不得小于{0}元",
					max: "转出金额不得大于人人赚账户余额"
				}
			}
		},
		successHandler: function (data) {
			if(data.confirmCash == "success"){
				alert("<div class='container-r-spacing'><div class='u-fsH1 u-txt-c mb20'>余额转出成功</div><i class='u-fntIco -msg-tip'></i><p class='u-fsH1 u-txt-c'>恭喜您！<br>转出余额<span class='u-clScd'>"+ toCurrency($out_form.find("[name=numCash]").val()) +"元</span></p><p class='u-clGrayLgt u-txt-c'>预计1-2个工作日内到达平台账户余额中</p></div>", function(){
					self.location.replace(basePath + $back_url)
				});
			}else{
				$out_form.find(":submit").toDefaultStatu();
				alert(data.msg);
			}
		}
	});
});
