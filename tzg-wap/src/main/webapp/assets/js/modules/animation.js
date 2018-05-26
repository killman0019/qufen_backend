/*--------------------------*\
	Animation
\*--------------------------*/

define(function (require, exports, module) {
	var requestAFrame = window.requestAnimationFrame ||
		window.webkitRequestAnimationFrame ||
		window.mozRequestAnimationFrame ||
		window.oRequestAnimationFrame ||
		(function (callback) {
			return window.setTimeout(callback, 1000 / 60);
		});

	var cancelAFrame = window.cancelAnimationFrame ||
		window.webkitCancelAnimationFrame ||
		window.mozCancelAnimationFrame ||
		window.oCancelAnimationFrame ||
		(function (id) {
			window.clearTimeout(id);
		});

	module.exports = {
		nextFrame: requestAFrame,
		cancelNextFrame: cancelAFrame,
		effects: {
			elastic: {
				/**
				 * @param t: current time 当前步
				 * @param b: beginning time 开始步
				 * @param c: change value 总变化
				 * @param d: duration 总步数
				 */
				easeIn: function (t, b, c, d, a, p) {
					if (t == 0) {
						return b;
					}
					if ((t /= d) == 1) {
						return b + c;
					}
					if (!p) {
						p = d * .3;
					}
					if (!a || a < Math.abs(c)) {
						a = c;
						var s = p / 4;
					}
					else {
						var s = p / (2 * Math.PI) * Math.asin (c / a);
					}
					return -(a * Math.pow(2, 10 * ( t-= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p )) + b;
				},
				easeOut: function (t, b, c, d, a, p) {
					if (t == 0) {
						return b;
					}
					if ((t /= d) == 1) {
						return b + c;
					}
					if (!p) {
						p = d * .3;
					}
					if (!a || a < Math.abs(c)) {
						a = c;
						var s = p / 4;
					}
					else {
						var s = p / (2 * Math.PI) * Math.asin(c / a);
					}
					return (a * Math.pow(2, -10 * t) * Math.sin((t * d - s) * (2 * Math.PI) / p) + c + b);
				},
				easeInOut: function(t,b,c,d,a,p){
					if (t == 0) {
						return b;
					}
					if ((t /= d / 2) == 2) {
						return b + c;
					}
					if (!p) {
						p = d * (.3 * 1.5);
					}
					if (!a || a < Math.abs(c)) {
						a = c;
						var s = p / 4;
					}
					else {
						var s = p / (2 * Math.PI) * Math.asin(c / a);
					}
					if (t < 1) {
						return - .5 * (a * Math.pow(2, 10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
					}
					return a * Math.pow(2, -10 * (t -= 1)) * Math.sin((t * d - s) * (2 * Math.PI) / p) * .5 + c + b;
				}
			},

			back: {
				easeIn: function(t,b,c,d,s){
					if (s == undefined) s = 1.70158;
					return c*(t/=d)*t*((s+1)*t - s) + b;
				},
				easeOut: function(t,b,c,d,s){
					if (s == undefined) s = 1.70158;
					return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
				},
				easeInOut: function(t,b,c,d,s){
					if (s == undefined) s = 1.70158; 
					if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b;
					return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b;
				}
			}
		}
	};
});
