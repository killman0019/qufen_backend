/*----------------------------------
 * fis 构建脚本
 *----------------------------------*/

 fis.config.set("staticPath", "/assets2");

//后缀名的less的文件使用fis-parser-less编译
//modules.parser.less表示设置后缀名为less的文件的parser，第二个less表示使用fis-parser-less进行编译
fis.config.set('modules.parser.less', 'less');
//将less文件编译为css
fis.config.set('roadmap.ext.less', 'css');

// md5戳连接符
fis.config.set("project.md5Connector", "-");

// 将零散资源进行自动打包
fis.config.set('settings.postpackager.simple.autoCombine', true);

// 资源映射
fis.config.set("roadmap.path", [
	//
	// 模板编译
	//
/*	{
		reg: "/templates/**.vm",
		release: "/WEB-INF/$&"
	},*/

	//
	// CSS
	//
	{
		reg: /^\/modules\/css\/css\-(.+)\.less/i,
		release: "${staticPath}/css/$1.css"
	},

	// 其他less不编译
	{
		reg: "**.less",
		release: false
	},

	//
	// Components
	//
	{
		reg: "/components/**",
		release: "${staticPath}/$&",
		//useOptimizer: false
	},

	//
	// js
	//
	{
		reg: /^\/(modules|pages)\/(.*)\.js$/i,
		release: "${staticPath}/$1/$2",
		useOptimizer: false
		// release: false,
		// isMod: true,
		// jswrapper: {
		// 	type: 'amd'
		// }
	},

	{
		reg: /\/statics\/js\/(html5shiv|sea)\.js/i,
		release: "${staticPath}/js/$1.js",
		useOptimizer: false
	},

	//
	// 非模块化文件
	//
	{
		reg: /\/statics\/(.+)/i,
		release: "${staticPath}/$1"
	}
]);


fis.config.set('pack', {
	"/assets2/js/lib.js": [
		"/statics/js/mod.js",
		"/components/zepto/event.js",
		"/components/zepto/ajax.js",
		"/components/zepto/form.js",
		"/components/zepto/ie.js",
		"/components/zepto/main.js"
	]
});


fis.config.set("deploy", {
	local: {
		to: "../"
	}
});
