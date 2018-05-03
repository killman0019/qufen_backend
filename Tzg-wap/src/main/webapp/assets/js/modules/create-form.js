/**
 * 创建一个表单
 */

define(function (require) {
	return function(config) {
		var defConfig = {
			formAction: "",
			formMethod: "post",
			formTarget: "",
			data: {}
		};
		
		if (!(typeof config === "object" && config !== null)) {
			config = {};
		}
		
		var form = document.createElement("form");
		for (var p in defConfig) {
			if(typeof config[p] === "undefined") {
				config[p] = defConfig[p];
			}
		}
		
		for (var p in config) {
			if(p.indexOf("form") == 0 && config[p]) {
				form.setAttribute(p.substr(4).toLowerCase(), config[p]);
			}
		}
		
		if (config.data) {
			var data = config.data;
			for (var p in data) {
				var formItem = document.createElement("input");
				formItem.setAttribute("type", "hidden");
				formItem.setAttribute("name", p);
				formItem.setAttribute("value", data[p]);
				form.appendChild(formItem);
			}
		}

		document.body.appendChild(form);
		
		return form;
	};
});
