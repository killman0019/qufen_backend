/*--------------------------*\
	Gulp 构建脚本
\*--------------------------*/

var gulp = require("gulp");
var plugins = require("gulp-load-plugins")();

gulp.task("default", function () {
	gulp.src("css/style.less")
		.pipe(plugins.less())
		.pipe(plugins.rename("style-o.css"))
		.pipe(gulp.dest("css/"))
		.pipe(plugins.minifyCss())
		.pipe(plugins.rename("style.css"))
		.pipe(gulp.dest("css/"));
});
gulp.watch("css/**", ["default"]);
