/*--------------------------*\
	Utils js
\*--------------------------*/

define(function (require, exports, module) {
	var ua =  navigator.userAgent;
	var device = "pc";
	var mobileMarks = ["iPhone", "iPad", "Android", "Touch", "Mobile", "Windows Phone"];
	for (var i=0,l=mobileMarks.length; i<l; i++) {
		if (ua.indexOf(mobileMarks[i]) > -1) {
			device = "mobile";
			break;
		}
	}

	window.tzg = {
		showMessage: function (msg, timeout){
			var dm = document.getElementById("tzg_message");
			if (!dm) {
				dm = document.createElement("div");
				dm.className = "m-pppMsg";
				dm.id = "tzg_message";
				dm.innerHTML = '<div class="m-pppMsg-inn"></div>';
				document.body.appendChild(dm);
			}
			var dmi = dm.getElementsByTagName("div")[0];
			dmi.innerHTML = msg;
			var ww = (window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth);
			dmi.style.left = (ww - dmi.offsetWidth-16) /2 + "px";
			dm.className = dm.className.replace(/\s*z-show/g, "") + " z-show";
			var defTimeout = 2;
			if(!timeout){
				timeout = defTimeout;
			}
			else {
				timeout = parseInt(timeout);
				if(isNaN(timeout) || timeout < 0){
					timeout = defTimeout;
				}
			}
			if(window.tzg.showMessage.timer) {
				clearTimeout(window.tzg.showMessage.timer);
			}
			window.tzg.showMessage.timer = setTimeout(function(){
				var dm = document.getElementById("tzg_message");
				dm.className = dm.className.replace(/\s*z-show/g, "");
			}, timeout*1000);
		},
		config: {
			device: device,
			//CLICK_EVENT: device === "mobile" ? "tap" : "click"
			CLICK_EVENT: "click"
		}
	};

	module.exports = window.tzg;
});
