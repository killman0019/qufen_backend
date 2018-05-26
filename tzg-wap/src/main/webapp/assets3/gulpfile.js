/*!
 * Gulp 构建文件
 */

var gulp = require("gulp");
var less = require("gulp-less");
var minifyCss = require("gulp-minify-css");
var sourcemaps = require("gulp-sourcemaps");
var rename = require("gulp-rename");
var uglify = require("gulp-uglify");
var imagemin = require("gulp-imagemin");
var iconfont = require("gulp-iconfont");

var taskName;

// 构建 CSS
//==================================================

var cssTasks = {
	"css-static": "src/statics/css/global.less",
	// "css-com": "src/components/**/*.less",
	// "css-page": "src/pages/**/*.less"
};

for (taskName in cssTasks) {
	(function(taskName, taskPath) {
		gulp.task(taskName, function() {
			gulp.src(taskPath, { base: "src" })
				.pipe(sourcemaps.init())	// sourcemap 初始化
				.pipe(less())
				//.pipe(sourcemaps.write("./"))	// 写入 sourcemap
				.pipe(minifyCss({
					compatibility: "ie7",		// 兼容到 IE7
					keepBreaks: true			// 每组样式换行
				}))
				.pipe(gulp.dest("build"));
		});
	})(taskName, cssTasks[taskName]);
}
gulp.watch(["src/statics/css/**/*.less", "src/components/**/*.less", "src/pages/**/*.less"], ["css-static"]);

// 所有 css
gulp.task("css", ["css-static", "css-com", "css-page"]);


// 构建 JS
//==================================================

var jsTasks = {
	"js-static": "src/statics/js/**/*.js",
	"js-com": "src/components/**/*.js",
	"js-page": "src/pages/**/*.js"
};

for (taskName in jsTasks) {
	(function(taskName, taskPath) {
		gulp.task(taskName, function() {
			gulp.src(taskPath, { base: "src" })
				.pipe(uglify({
						mangle: {
							except : ["require", "exports", "module"]	// 排除混淆
						}
					})
				)
				.pipe(gulp.dest("build"));
		});
	})(taskName, jsTasks[taskName]);
}

// 所有 JS
gulp.task("js", ["js-static", "js-com", "js-page"]);


// 构建图片
//==================================================

var imgTasks = {
	"img-static": "src/statics/images/**/*.{png,jpg,gif}",
	"img-com": "src/components/**/*.{png,jpg,gif}",
	"img-page": "src/pages/**/*.{png,jpg,gif}"
};

for (taskName in imgTasks) {
	(function(taskName, taskPath) {
		gulp.task(taskName, function() {
			gulp.src(taskPath, { base: "src" })
				.pipe(imagemin())
				.pipe(gulp.dest("build"));
		});
	})(taskName, imgTasks[taskName]);
}

gulp.task("img", ["img-static", "img-com", "img-page"]);


// 构建字体图标
// 字体图标的unicode编码是根据svg命名顺序来的
// 所以新加的图标顺序一定要往后排
// 否则unicode编码会全乱
// AI中画板的大小不得小于500. 推荐 1000。存储svg时
// “SVG配置文件”要选择“SVG Tiny 1.1”
//==================================================
//
gulp.task('iconfont', function() {
	return gulp.src('src/fonts/icon-base/*.svg', {base: 'src'})
		.pipe(iconfont({
			fontName: 'icon-base', // required
			appendUnicode: true,
			formats: ['ttf', 'eot', 'woff', 'svg'],
			timestamp: Math.round(Date.now()/1000), // recommended to get consistent builds when watching files
			normalize: true, fontHeight: 1001   // 避免生成的字体变形。无理由，必须硬性加上
		}))
		.pipe(gulp.dest('build/statics/fonts/icon-base'));
});

// 默认只编译 Less
//==================================================

gulp.task("default", ["css-static"]);
