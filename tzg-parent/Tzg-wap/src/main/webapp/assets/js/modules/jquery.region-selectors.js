/*--------------------------*\
	地区联动菜单
\*--------------------------*/

define(function (require, exports, module) {
	var $ = require("$");

	$.fn.extend({
		// 主方法
		regionSelectors: function (config) {
			var len = this.size();
			if (len === 0 || this.data("regionSelectorsConfig")) {
				return this;
			}

			var $selectors = this;
			var defConfig = {
				rootLevelURL: "/area/queryFirstLevelAll",
				subLevelURL: "/area/queryInfoByiFatherID",
				requestCodeParamName: "vcCode",
				defaultValueAttrName: "selected-region-code",
				parentValueAttrName: "parent-region-code"
			};
			var c = $.extend({ "data": {}, "$selectors": $selectors }, defConfig, config);
			this.data("regionSelectorsConfig", c);

			this.each(function (i) {
				var $this = $(this);
				$this.data("regionLevel", i);
				// 原来就有的下拉选项保存起来当做默认选项
				$this.data("defaultOptions", $this.html());
			});

			// 先加载根菜单
			$selectors.eq(0).initRegion();

			$selectors.on("change", function () {
				$(this).selectChange();
			});
		},

		// 根据父菜单的code生成数据. 没有code则表示是根目录
		initRegion: function (code, selectedOption) {
			var $this = this;

			var selector = $this.get(0);
			var c = $this.data("regionSelectorsConfig");
			if (!c) {
				return this;
			}
			var dataSet = c.data;

			if (typeof code === "undefined") {
				code = "root";
			}
			// 选择了空选项
			else if (!code) {
				$this.generateRegion(null);
				return $this;
			}

			// 缓存数据
			var optionsData = dataSet[code];

			// 有缓存数据则直接显示
			if (optionsData) {
				$this.generateRegion(optionsData);
				return $this;
			}

			// 没有缓存则抓取数据 ---------------------------------------

			// 已经在抓取
			if ($this.val() === "-") {
				return $this;
			}

			var requestURL = basePath + (code === "root" ? c.rootLevelURL : c.subLevelURL);
			var requestParams = null;

			if (code !== "root") {
				requestParams = {};
				requestParams[c.requestCodeParamName] = code;
			}

			$this.html('<option value="-" selected>数据加载中...</option>');
			$.getJSON(requestURL, requestParams, function (data) {
				// 将数据缓存起来
				c.data[code || "root"] = data;
				// 刷新
				$this.generateRegion(data);
			});
		},

		// 根据数据更新select
		generateRegion: function (data, selectedOption) {
			var $this = this;
			var selector = this.get(0);
			var defaultOptions = $this.data("defaultOptions");
			var level = $this.data("regionLevel");
			var c = $this.data("regionSelectorsConfig");

			$this.html(defaultOptions || "");
			if (data) {
				$.each(data, function (i, v) {
					selector.add(new Option(v.vcName, v.vcCode));
				});
			}
			
			// 选择指定的下拉框
			$this.selectOne(selectedOption || $this.data(c.defaultValueAttrName));

			// 将上一个默认值清空
			$this.data(c.defaultValueAttrName, "");

			// 子菜单更新
			if (level < c.$selectors.length-1) {
				c.$selectors.eq(level+1).initRegion($this.val());
			}
		},

		// 根据值选中某个选项
		selectOne: function (value) {
			$.each(this.get(0).options, function (i, optionElement) {
				if (optionElement.value == value) {
					optionElement.selected = true;
				}
			});
		},

		// 响应下拉选择事件
		selectChange: function () {
			var $this = this;
			var c = $this.data("regionSelectorsConfig");
			var level = $this.data("regionLevel");

			c.$selectors.eq(level+1).initRegion($this.val());
		}
	});
});
