/**
 * 数据校验方法集
 */
define(function(require) {
	return {
		// 检测中国手机号码
		'checkMobile': function(mobileNumber) {
			return mobileNumber && (/^1[0-9]{10}$/g).test(mobileNumber);
		},
		
		// 检测用户名(只能为中文、英文、数字且不以数字开头)
		'checkUserName': function(userName) {
			var ret = userName && (/^[a-zA-Z][a-zA-Z0-9]{5,17}$/g).test(userName);
			if( ret ) {
				userName = userName.replace(/[\u4e00-\u9fa5]/g, "00");
				return (userName.length >= 4 && userName.length <= 18);
			}
			return ret;
		}
	};
});
