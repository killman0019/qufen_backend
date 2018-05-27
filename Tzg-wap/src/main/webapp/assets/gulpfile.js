/*--------------------------*\
	Gulp 构建脚本
\*--------------------------*/

var gulp = require("gulp");
var plugins = require("gulp-load-plugins")();

// tasks ----------------------------------------------------------

// style.css
gulp.task("css", function () {
	gulp.src("css/less/css-style.less")
		.pipe(plugins.less())
		.pipe(plugins.rename("style-o.css"))
		.pipe(gulp.dest("css/"))
		.pipe(plugins.minifyCss())
		.pipe(plugins.rename("style.css"))
		.pipe(gulp.dest("css/"));
});


gulp.task("default", ["css"]);

// watch ----------------------------------------------------------

// css
gulp.watch(["css/less/*.less", "css/less/**/*.less"], ["css"]);

