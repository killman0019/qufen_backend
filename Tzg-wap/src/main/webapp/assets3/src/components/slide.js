/**
 * Slide
 * 幻灯片组件
 * 
 * Author: 吴岸林
 */

define(["jquery"], function ($) {
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


	var Slide = function(settings){
		this.config = {};
		this.inited = false;
		this.init(settings);
	};
	Slide.prototype = {
		'init': function(settings){
			var defSettings = {
				'eWrap': false,
				'eImagesWrap': '.sld-images',
				'eNavWrap': '.sld-nav',
				//'fps': 25,		// frame per second 帧速率
				//'scrollTime': 1	// 邻近项目切换一次的时间(秒)
				'fpc': 6,	// 邻近项目切换一次的帧数
				'autoPlayInterval': 5,
				'currentNavClass': "current"
			};
			var _self = this;

			// override default settings
			_self.settings = {};
			if(settings && typeof settings == 'object'){
				for(var p in defSettings){
					_self.settings[p] = settings[p] || defSettings[p];
				}
			}
			else {
				for(var p in defSettings){
					_self.settings[p] = defSettings[p];
				}
			}

			// 存储程序运行要用的数据
			var c = _self.config;
			// 存储配置
			var s = _self.settings;

			// 检查是否已经初始化
			var eWrap = c.eWrap = $(s.eWrap);
			if(!(eWrap.size() && eWrap.attr('data-wfy-slider-inited') !== 'yes')){
				return false;
			}
			var eImagesWrap = c.eImagesWrap = eWrap.find(s.eImagesWrap);
			if(eImagesWrap.size() == 0){
				return false;
			}

			var eListWrap = c.eListWrap = eImagesWrap.find('ul:first');
			var eList = eImagesWrap.find('li');
			var count = c.count = eList.size();
			if(count <= 1){
				return false;
			}

			eList.css("float", "left");
			eImagesWrap.css({"width": "100%", "overflow": "hidden"});
	
			// 将列表复制一份后, 每列的宽度
			var itemWidth = 100 / (count * 2) + '%';
			// 列表总宽度
			eListWrap.css('width', count * 2 * 100 + '%');
			eList.css('width', itemWidth);

			// make a copy of the list
			var originalHTML = eListWrap.html();

			eListWrap.html(originalHTML + originalHTML);

			var fpc = parseInt(s.fpc);
			if(isNaN(fpc)){
				s.fpc = defSettings.fpc;
			}
			else if(fpc < 3){
				s.fpc = 3;
			}

			// page navigation
			var eNavWrap = eWrap.find(s.eNavWrap);
			if(eNavWrap.size()){
				var navHTML = [];
				for(i=0; i<count; i++){
					navHTML.push('<a href="#" ');
					if(i == 0){
						navHTML.push('class="'+ s.currentNavClass +'" ');
					}
					navHTML.push('slider-index="'+ i +'">'+ (i+1) +'</a>');
				}
				eNavWrap.html(navHTML.join(''));
				var eNavList = c.eNavList = eNavWrap.find('a');
				eNavList.mouseover(function(e){
					e.preventDefault();
					_self.show(this.getAttribute('slider-index'));
				});
			}

			// 自动播放
			var autoPlayInterval = parseFloat(s.autoPlayInterval);
			if(isNaN(autoPlayInterval)){
				s.autoPlayInterval = 0;

			}
			else if(autoPlayInterval < 2) {
				s.autoPlayInterval = 2;
			}
			c.currentIndex = -1;

			eImagesWrap.attr('data-wfy-slider-inited', 'yes');
			_self.inited = true;
			// 动画是否进行中
			c.going = 0;

			_self.show(0);

			// 拖拽
			if(document.hasOwnProperty && document.hasOwnProperty('ontouchstart') || navigator.userAgent.indexOf("iPhone") > -1){
				c.dragStartEventType = 'touchstart';
				c.draggingEventType = 'touchmove';
				c.dragEndEventType = 'touchend';
				_self.getEventPosition = function(e){
					return e.originalEvent.touches[0].pageX;
				};
			}
			else {
				c.dragStartEventType = 'mousedown';
				c.draggingEventType = 'mousemove';
				c.dragEndEventType = 'mouseup';
				_self.getEventPosition = function(e){
					return e.screenX || e.clientX || e.pageX;
				};
			}

			_self.dragRuntime = {};

			c.eDocument = $(document);

			// 拖拽
			_self.dragStart = function(e){
				//e.preventDefault();
				//e.stopPropagation();

				c.eDocument.on(c.draggingEventType, _self.drag);

				// 停止下次滚动
				clearTimeout(s.autoPlayTimer);
				s.autoPlayTimer = 0;
				// 停止当前滚动
				_self.stopScroll();

				// 记录初始位置
				_self.dragRuntime.x = _self.getEventPosition(e);
				_self.dragRuntime.left = c.eImagesWrap.get(0).scrollLeft;
			};
			_self.drag = function(e){
				e.preventDefault();
				e.stopPropagation();
				_self.setPosition(_self.dragRuntime.left + _self.dragRuntime.x - _self.getEventPosition(e));
			};
			_self.dragEnd = function(e){
				//e.preventDefault();
				//e.stopPropagation();
				c.eDocument.off(c.draggingEventType, _self.drag);
				//unbind(eListWrap, 'click', _self.preventDefault);
				var _eImagesWrap = c.eImagesWrap.get(0);
				var colWidth = _eImagesWrap.offsetWidth;
				var maxScrollPosition = colWidth * c.count;
				var currentPosition = _eImagesWrap.scrollLeft % maxScrollPosition;

				// 最近的位置
				var nearestPositionIndex = Math.round(currentPosition / colWidth) % c.count;
				var target = nearestPositionIndex * colWidth;
				// 寻找最近的滚动
				if(Math.abs(target - currentPosition) > colWidth){
					if(target < currentPosition){
						target += maxScrollPosition;
					}
				}
				_self.scrollTo(target, function(_self){
					if(s.autoPlayInterval){
						_self.scrollToNext();
					}
				});
				_self.setCurrentNav(nearestPositionIndex);
			};

			c.eImagesWrap.on(c.dragStartEventType, _self.dragStart);
			c.eDocument.on(c.dragEndEventType, _self.dragEnd);
			// 补充一个结束事件
			if(c.dragEndEventType == 'touchend'){
				c.eDocument.on('touchcancel', _self.dragEnd);
			}
		},
		'setPosition': function(target){
			var _self = this;
			var c = _self.config;
			var _eImagesWrap = c.eImagesWrap.get(0);

			var maxScrollPosition = _eImagesWrap.offsetWidth * c.count;
			target %= maxScrollPosition;
			if(target < 0){
				target += maxScrollPosition;
			}
			_eImagesWrap.scrollLeft = target;
		},
		/**
		 * scroll to a position.
		 * @param target: required.
		 * @param callback: callback of over scroll.
		 * @return: void.
		 */
		'scrollTo': function(target, callback){
			if(!(this.inited && typeof target !== 'undefined' && !isNaN(target))){
				return;
			}
			var _self = this;
			var c = _self.config;
			var _eImagesWrap = c.eImagesWrap.get(0);

			if(target == _eImagesWrap.scrollLeft){
				if(typeof callback === 'function' || callback instanceof Function){
					callback(_self);
				}
				return;
			}

			_self.scrollRuntime = {
				'target': target,
				'callback': callback
			};

			c.going = requestAFrame(function(){ _self.scroll() });
		},
		'scroll': function(){
			var _self = this;
			var c = _self.config;
			var s = _self.settings;
			var r = _self.scrollRuntime;
			var _eImagesWrap = c.eImagesWrap.get(0);

			if(!_self.scrollRuntime) {
				return;
			}
			
			var distance = r.target - _eImagesWrap.scrollLeft;
			// over
			if(distance == 0) {
				_self.stopScroll();
				if(typeof r.callback === 'function' || r.callback instanceof Function){
					r.callback(_self);
				}
			}
			else {
				// range current step 本次切换的幅度
				var rcs = Math[distance < 0 ? 'floor' : 'ceil'](distance / s.fpc);
				_eImagesWrap.scrollLeft += rcs;
				c.going = requestAFrame(function(){ _self.scroll() });
			}
		},
		'stopScroll': function(){
			cancelAFrame(this.config.going);
			this.config.going = 0;
			delete this.scrollRuntime;
		},
		'show': function(index, callback){
			if(!this.inited){
				return;
			}
			var _self = this;
			var c = _self.config;
			index = parseInt(index);
			if(isNaN(index) || index < 0 || index >= c.count || index == c.currentIndex){
				return;
			}
			var _eImagesWrap = c.eImagesWrap.get(0);

			_self.setCurrentNav(index);
			var colWidth = _eImagesWrap.offsetWidth;
			var currentPosition = _eImagesWrap.scrollLeft;
			var target = colWidth * index;
			var maxScrollPosition = colWidth * c.count;

			while(target < currentPosition){
				if(currentPosition < maxScrollPosition){
					target += maxScrollPosition;
				}
				else {
					currentPosition = _eImagesWrap.scrollLeft -= maxScrollPosition;
				}
			}

			_self.scrollTo(target, function(_self){
				var c = _self.config;
				var s = _self.settings;
				c.currentIndex = index;
				if(typeof callback === 'function' || callback instanceof Function){
					callback(_self);
				}
				if(s.autoPlayInterval){
					_self.scrollToNext();
				}
			});
		},
		'scrollToNext': function(){
			var _self = this;
			var c = _self.config;
			var s = _self.settings;

			clearTimeout(s.autoPlayTimer);
			s.autoPlayTimer = setTimeout(function(){
				_self.show(c.currentIndex >= c.count-1 ? 0 : c.currentIndex+1);
				s.autoPlayTimer = 0;
			}, s.autoPlayInterval * 1000);
		},
		'setCurrentNav': function(index){
			var s = this.settings;
			this.config.eNavList.each(function(i){
				$(this)[i == index ? 'addClass' : 'removeClass'](s.currentNavClass);
			});
		}
	};

	return Slide;
});
