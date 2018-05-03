/*--------------------------*\
	选择银行卡联动
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");
	require("module/selector");

	var SelectBank = function($object, config) {
		var defConfig = {
			"bankSelectedWrapper": 		"[j-bank-selected-wrapper]",	// 已选择的银行卡显示区
			"cardnoInputItem": 			"[j-cardno-input-item]",		// 银行卡输入框所在的表单项
			"cardnoInput": 				"[j-cardno-input]",				// 银行卡号输入框
			"bankidInput": 				"[j-bankid-input]",
			"selectorElement": 			"#j_selector",					// 选择器
			"bankList": 				[],								// 银行卡列表
			"noSelectedBankTemplate": 	'<a href="#" class="bank-item">请选择银行</a>',
			"selectedBankTemplate": 	'<a href="#" class="bank-item -has-bank" data-bank-index="{{index}}"><i class="u-icoBnk--{{bankCode}}"></i><div class="bank-name">{{bankName}}</div><div class="bank-desc">{{bankDesc}}</div></a>',
			"selectorConfig": null,
			"selectHandler": null,
			"defaultIndex": 0,
			"getBankDesc": function (bank) {
				return bank.numSingleLimitStr + " " + bank.numDayLimitStr;
			}
		};

		if (!($object instanceof $ && $object.length)) {
			return false;
		}

		var c = this.config = $.extend({
			"$object": $object
		}, defConfig, config);

		// 初始化
		this.init();
	};

	$.extend(SelectBank.prototype, {
		init: function() {
			var _self = this;
			var c = _self.config;

			// 初始化Element
			var queries = "bankSelectedWrapper cardnoInputItem cardnoInput bankidInput".split(" ");
			for (var i=0, l=queries.length; i<l; i++) {
				c["$"+ queries[i]] = c.$object.find(c[queries[i]]);
			}

			for (var i=0,l=c.bankList.length,v; i<l; i++) {
				v = c.bankList[i];
				if (v) {
					v.bankName = v.name;
					v.bankDesc = c.getBankDesc(v);
				}
			}

			var selectorConfig = $.extend({
					"menuTitle": "请选择银行",
					"menuItems": c.bankList,
					"finishHandler": function(index) {
						_self.select(index);
					}
				}, c.selectorConfig);
			c.selector = $(c.selectorElement).tzgSelector(selectorConfig);

			if (typeof c.defaultIndex === "number") {
				_self.select(c.defaultIndex);
			}

			// 绑定事件
			c.$bankSelectedWrapper.on("click", function (e) {
				e.preventDefault();
				c.selector.open(c.$bankSelectedWrapper.data("bankIndex"));
			});

			c.$cardnoInput.on("input", function() {
				var input = this;
				var cardNo = _self.getCardNo(this.value);
				this.value = cardNo;
				if (this.setSelectionRange) {
					var l = cardNo.length;
					setTimeout(function() { input.setSelectionRange(l, l); }, 10);
				}
			});

			return this;
		},

		getBankHTML: function(bank) {
			var c = this.config,
				result = c.noSelectedBankTemplate,
				variables = ["bankCode", "bankName", "bankDesc"],
				i, l, v;

			if (bank != null && bank.id) {
				result = c.selectedBankTemplate;
				for (i=0,l=variables.length; i<l; i++) {
					v = variables[i];
					result = result.split("{{"+ v +"}}").join(v in bank ? bank[v] : "");
				}
			}
			return result;
		},

		select: function(index) {
			var c = this.config;

			if (c.selectHandler && $.isFunction(c.selectHandler) && c.selectHandler(index, this) === false) {
				return this;
			}

			var banks = c.bankList;
			var showBankCodeInput = true;
			var html = c.noSelectedBankTemplate;

			if (index != null) {
				var bank = banks[index];
				if (bank) {
					html = this.getBankHTML(bank);
					if (bank.cardNo) {
						showBankCodeInput = false;
					}
					c.$bankidInput.val(bank.id);
				}
				else {
					c.$bankidInput.val("");
				}
			}
			else {
				c.$bankidInput.val("");
			}
			c.$cardnoInputItem[showBankCodeInput ? "show" : "hide"]();
			c.$cardnoInput.val(!showBankCodeInput && bank && bank.cardNo ? bank.cardNo : "");
			c.$bankSelectedWrapper.data("bankIndex", index).html(html);

			return this;
		},

		// 在银行卡号中加空格
		// unitSize: 空格间的字符个数
		getCardNo: function(no, unitSize) {
			if (!no) {
				return "";
			}
			no = no.replace(/\s/g, "");
			if (!unitSize) {
				unitSize = 4;
			}
			if (no.length <= unitSize) {
				return no;
			}
			var arr = [];
			for (var i=0,l=no.length; i<Math.ceil(l/unitSize); i++) {
				arr.push( no.substr(i*unitSize, unitSize) );
			}
			return arr.join(" ");
		}
	});

	$.fn.selectBank = function(config) {
		function init($object, config) {
			var selectBank = $object.data("tzgSelectBank");
			if (!selectBank) {
				selectBank = new SelectBank($object, config);
			}
			$object.data("tzgSelectBank", selectBank);
			return selectBank;
		};
		
		if (this.length > 1) {
			this.each(function() {
				init($(this), config);
			});
		}
		else {
			return init(this, config);
		}
	};
});
