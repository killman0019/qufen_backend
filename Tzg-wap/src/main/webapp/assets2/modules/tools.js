/*--------------------------*\
	Tools
\*--------------------------*/

define(function (require) {
	return {
		/*
		 * 功能：给1-9之间的整数前面加0
		 * 入参：1-9间的整数
		 * 依赖：被本tools集中formatDataYMD方法调用
		 **/
		add0: function (num) {
			if (0 <= num && num < 10) {
				return '0' + num;
			} else {
				return num;
			}
		},

		/*
		 * 功能：将0-5之间的分数转换为百分比，主要适用于ecp中根据分数显示实心的星星
		 * 入参：0-5之间的数字
		 * 返回：入参与5的百分比
		 **/
		scoreToPercentage: function (item) {
			return Number(item) / 5.0 * 100 + '%';
		},

		/*
		 * 功能：将时间戳格式化成 YY-MM-DD hh:mm:ss形式
		 * 入参：时间戳
		 **/
		formatDateYMDHMS: function (millisec) {
			if (millisec != null) {
				var d = new Date(millisec);
				var dstr = d.getFullYear() + "-" + this.add0(d.getMonth() + 1) + "-" + this.add0(d.getDate()) + " " + this.add0(d.getHours()) + ":" +
				this.add0(d.getMinutes()) + ":" + this.add0(d.getSeconds());
				return dstr;
			} else {
				return null;
			}
		},

		/*
		 * 功能：将时间戳格式化成 YY-MM-DD 形式
		 * 入参：时间戳
		 **/
		formatYMDDate: function (millisec) {
			if (millisec != null) {
				var d = new Date(millisec);
				var dstr = d.getFullYear() + "-" + this.add0(d.getMonth() + 1) + "-" + this.add0(d.getDate());
				return dstr;
			} else {
				return null;
			}
		},
		
		

		/*
		 * 功能：格式化数字
		 * 入参：@pnumber 要处理的数字，@decimals 要保留小数点的位数
		 * 返回：指定小数点位数的数字，非数字和空都返回相应格式的0
		 **/
		format_number: function (pnumber, decimals) {
			var result = '';
			if (isNaN(pnumber)) {
				for(var i = 0; i < decimals; i++) {
					result = result + '0'
				}
				if (result == '') {
					return '0';
				} else {
					return '0.' + result;
				}
			}
			if (pnumber == '') {
				for(var i = 0; i < decimals; i++) {
					result = result + '0'
				}
				if (result == '') {
					return '0';
				} else {
					return '0.' + result;
				}
			}
			var snum = new String(pnumber);
			var sec = snum.split('.');
			var whole = parseFloat(sec[0]);
			if (sec.length > 1) {
				var dec = new String(sec[1]);
				dec = String(parseFloat(sec[1]) / Math.pow(10, (dec.length - decimals)));
				dec = String(whole + Math.round(parseFloat(dec)) / Math.pow(10, decimals));
				var dot = dec.indexOf('.');
				if (dot == -1) {
					dec += '.';
					dot = dec.indexOf('.');
				}
				while (dec.length <= dot + decimals) {
					dec += '0';
				}
				result = dec;

			} else {
				var dot;
				var dec = new String(whole);
				dec += '.';
				dot = dec.indexOf('.');
				while (dec.length <= dot + decimals) {
					dec += '0';
				}
				result = dec;
			}
			if (decimals === 0) {
				return result.split('.')[0];
			}
			//return S.trim(result);
			return result;
		},

		/*
		 * 功能：计算字符串长度，中文算两个字符。
		 * 入参：str：需要计算长度的字符串。
		 * 结果：返回str的长度(Unicode长度为2，非Unicode长度为1)
		 * */
		strLength: function(str) {
			var length = 0;
			if (str == null || str == '') {
				return length;
			}
			var a = str.toString();
			for (var i = 0; i < a.length; i++) {
				if ((a.charCodeAt(i) < 0) || (a.charCodeAt(i) > 255)) {
					length = length + 2;
				} else {
					length = length + 1;
				}
			}
			return length;
		},

		formattedNumber: function(amount) {
			var delimiter = ","; // replace comma if desired
			amount = new String(amount);
			var a = amount.split('.');
			if (a.length == 1) {
				return amount;
			}
			var d = a[1];
			var i = parseInt(a[0]);
			if (isNaN(i)) {
				return '';
			}
			var minus = '';
			if (i < 0) {
				minus = '-';
			}
			i = Math.abs(i);
			var n = new String(i);
			var a = [];
			while (n.length > 3) {
				var nn = n.substr(n.length - 3);
				a.unshift(nn);
				n = n.substr(0, n.length - 3);
			}
			if (n.length > 0) {
				a.unshift(n);
			}
			n = a.join(delimiter);
			if (d.length < 1) {
				amount = n;
			} else {
				amount = n + '.' + d;
			}
			amount = minus + amount;
			return amount;
		},

		parseJudgePre: function(amount) {
			amount = new String(amount);
			var a = amount.split('.');
			if (a.length == 1) {
				return amount;
			} else {
				return a[0];
			}
		},
		/*
		 * 功能：将时间戳格式化成 hh:mm 形式
		 * 入参：时间戳
		 **/
		formatHMDate: function (millisec) {
			if (millisec != null) {
				var d = new Date(millisec);
				var dstr = this.add0(d.getHours()) + ":" + this.add0(d.getMinutes());
				return dstr;
			} else {
				return null;
			}
		},
		
		/**
		 * 日期格式化，返回年月日
		 * 
		 * @param millisec
		 * @param format
		 * @returns
		 */
		formatDate: function(millisec, format) {
			if (millisec != null) {
				var d = new Date(millisec);
				var o = {
					"M+": d.getMonth() + 1,
					//month
					"d+": d.getDate(),
					//day
					"h+": d.getHours(),
					//hour
					"m+": d.getMinutes(),
					//minute
					"s+": d.getSeconds(),
					//second
					"q+": Math.floor((d.getMonth() + 3) / 3),
					//quarter
					"S": d.getMilliseconds() //millisecond
				};
				if (/(y+)/.test(format)) {
					format = format.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));
				}

				for (var k in o) {
					if (new RegExp("(" + k + ")").test(format)) {
						format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
					}
				}
				return format;
			} else {
				return '';
			}
		},
		/**
		 * 将浏览器地址参数转换成json
		 * 
		 * @param query
		 * @returns
		 */
		queryToJSON: function (queryStr) {
			if (!(queryStr && typeof queryStr === "string")) {
				return null;
			}
			if (queryStr.indexOf("?") === 0) {
				queryStr = queryStr.substr(1);
			}
			var temp = queryStr.split("&");
			var json = {};
			for (var i in temp) {
				var item = temp[i].split("=");

				// 如果前面已经有该参，则表明是多选框
				if (json[item[0]]) {
					if ($.isArray(json[item[0]])) {
						json[item[0]].push(item[1]);
					}
					else {
						json[item[0]] = [json[item[0]], item[1]];
					}
				}
				else {
					json[item[0]] = item.length === 1 ? "" : item[1];
				}
			}
			return json;
		}
	};
});
