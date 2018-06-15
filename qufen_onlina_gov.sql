/*
Navicat MySQL Data Transfer

Source Server         : 测试
Source Server Version : 50719
Source Host           : 192.168.10.202:3306
Source Database       : qufen_onlina_gov

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-06-15 11:27:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for d_areas
-- ----------------------------
DROP TABLE IF EXISTS `d_areas`;
CREATE TABLE `d_areas` (
  `code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '区划编号',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '区划名称',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='行政区划(字典)';

-- ----------------------------
-- Records of d_areas
-- ----------------------------
INSERT INTO `d_areas` VALUES ('11', '北京');
INSERT INTO `d_areas` VALUES ('12', '天津');
INSERT INTO `d_areas` VALUES ('13', '河北');
INSERT INTO `d_areas` VALUES ('1301', '石家庄市');
INSERT INTO `d_areas` VALUES ('1302', '唐山市');
INSERT INTO `d_areas` VALUES ('1303', '秦皇岛市');
INSERT INTO `d_areas` VALUES ('1304', '邯郸市');
INSERT INTO `d_areas` VALUES ('1305', '邢台市');
INSERT INTO `d_areas` VALUES ('1306', '保定市');
INSERT INTO `d_areas` VALUES ('1307', '张家口市');
INSERT INTO `d_areas` VALUES ('1308', '承德市');
INSERT INTO `d_areas` VALUES ('1309', '沧州市');
INSERT INTO `d_areas` VALUES ('1310', '廊坊市');
INSERT INTO `d_areas` VALUES ('1311', '衡水市');
INSERT INTO `d_areas` VALUES ('14', '山西');
INSERT INTO `d_areas` VALUES ('1401', '太原市');
INSERT INTO `d_areas` VALUES ('1402', '大同市');
INSERT INTO `d_areas` VALUES ('1403', '阳泉市');
INSERT INTO `d_areas` VALUES ('1404', '长治市');
INSERT INTO `d_areas` VALUES ('1405', '晋城市');
INSERT INTO `d_areas` VALUES ('1406', '朔州市');
INSERT INTO `d_areas` VALUES ('1407', '晋中市');
INSERT INTO `d_areas` VALUES ('1408', '运城市');
INSERT INTO `d_areas` VALUES ('1409', '忻州市');
INSERT INTO `d_areas` VALUES ('1410', '临汾市');
INSERT INTO `d_areas` VALUES ('1411', '吕梁市');
INSERT INTO `d_areas` VALUES ('15', '内蒙');
INSERT INTO `d_areas` VALUES ('1501', '呼和浩特市');
INSERT INTO `d_areas` VALUES ('1502', '包头市');
INSERT INTO `d_areas` VALUES ('1503', '乌海市');
INSERT INTO `d_areas` VALUES ('1504', '赤峰市');
INSERT INTO `d_areas` VALUES ('1505', '通辽市');
INSERT INTO `d_areas` VALUES ('1506', '鄂尔多斯市');
INSERT INTO `d_areas` VALUES ('1507', '呼伦贝尔市');
INSERT INTO `d_areas` VALUES ('1508', '巴彦淖尔市');
INSERT INTO `d_areas` VALUES ('1509', '乌兰察布市');
INSERT INTO `d_areas` VALUES ('1522', '兴安盟');
INSERT INTO `d_areas` VALUES ('1525', '锡林郭勒盟');
INSERT INTO `d_areas` VALUES ('1529', '阿拉善盟');
INSERT INTO `d_areas` VALUES ('21', '辽宁');
INSERT INTO `d_areas` VALUES ('2101', '沈阳市');
INSERT INTO `d_areas` VALUES ('2102', '大连市');
INSERT INTO `d_areas` VALUES ('2103', '鞍山市');
INSERT INTO `d_areas` VALUES ('2104', '抚顺市');
INSERT INTO `d_areas` VALUES ('2105', '本溪市');
INSERT INTO `d_areas` VALUES ('2106', '丹东市');
INSERT INTO `d_areas` VALUES ('2107', '锦州市');
INSERT INTO `d_areas` VALUES ('2108', '营口市');
INSERT INTO `d_areas` VALUES ('2109', '阜新市');
INSERT INTO `d_areas` VALUES ('2110', '辽阳市');
INSERT INTO `d_areas` VALUES ('2111', '盘锦市');
INSERT INTO `d_areas` VALUES ('2112', '铁岭市');
INSERT INTO `d_areas` VALUES ('2113', '朝阳市');
INSERT INTO `d_areas` VALUES ('2114', '葫芦岛市');
INSERT INTO `d_areas` VALUES ('22', '吉林');
INSERT INTO `d_areas` VALUES ('2201', '长春市');
INSERT INTO `d_areas` VALUES ('2202', '吉林市');
INSERT INTO `d_areas` VALUES ('2203', '四平市');
INSERT INTO `d_areas` VALUES ('2204', '辽源市');
INSERT INTO `d_areas` VALUES ('2205', '通化市');
INSERT INTO `d_areas` VALUES ('2206', '白山市');
INSERT INTO `d_areas` VALUES ('2207', '松原市');
INSERT INTO `d_areas` VALUES ('2208', '白城市');
INSERT INTO `d_areas` VALUES ('2224', '延边朝鲜族自治州');
INSERT INTO `d_areas` VALUES ('23', '黑龙江');
INSERT INTO `d_areas` VALUES ('2301', '哈尔滨市');
INSERT INTO `d_areas` VALUES ('2302', '齐齐哈尔市');
INSERT INTO `d_areas` VALUES ('2303', '鸡西市');
INSERT INTO `d_areas` VALUES ('2304', '鹤岗市');
INSERT INTO `d_areas` VALUES ('2305', '双鸭山市');
INSERT INTO `d_areas` VALUES ('2306', '大庆市');
INSERT INTO `d_areas` VALUES ('2307', '伊春市');
INSERT INTO `d_areas` VALUES ('2308', '佳木斯市');
INSERT INTO `d_areas` VALUES ('2309', '七台河市');
INSERT INTO `d_areas` VALUES ('2310', '牡丹江市');
INSERT INTO `d_areas` VALUES ('2311', '黑河市');
INSERT INTO `d_areas` VALUES ('2312', '绥化市');
INSERT INTO `d_areas` VALUES ('2327', '大兴安岭地区');
INSERT INTO `d_areas` VALUES ('31', '上海');
INSERT INTO `d_areas` VALUES ('32', '江苏');
INSERT INTO `d_areas` VALUES ('3201', '南京市');
INSERT INTO `d_areas` VALUES ('3202', '无锡市');
INSERT INTO `d_areas` VALUES ('3203', '徐州市');
INSERT INTO `d_areas` VALUES ('3204', '常州市');
INSERT INTO `d_areas` VALUES ('3205', '苏州市');
INSERT INTO `d_areas` VALUES ('3206', '南通市');
INSERT INTO `d_areas` VALUES ('3207', '连云港市');
INSERT INTO `d_areas` VALUES ('3208', '淮安市');
INSERT INTO `d_areas` VALUES ('3209', '盐城市');
INSERT INTO `d_areas` VALUES ('3210', '扬州市');
INSERT INTO `d_areas` VALUES ('3211', '镇江市');
INSERT INTO `d_areas` VALUES ('3212', '泰州市');
INSERT INTO `d_areas` VALUES ('3213', '宿迁市');
INSERT INTO `d_areas` VALUES ('33', '浙江');
INSERT INTO `d_areas` VALUES ('3301', '杭州市');
INSERT INTO `d_areas` VALUES ('3302', '宁波市');
INSERT INTO `d_areas` VALUES ('3303', '温州市');
INSERT INTO `d_areas` VALUES ('3304', '嘉兴市');
INSERT INTO `d_areas` VALUES ('3305', '湖州市');
INSERT INTO `d_areas` VALUES ('3306', '绍兴市');
INSERT INTO `d_areas` VALUES ('3307', '金华市');
INSERT INTO `d_areas` VALUES ('3308', '衢州市');
INSERT INTO `d_areas` VALUES ('3309', '舟山市');
INSERT INTO `d_areas` VALUES ('3310', '台州市');
INSERT INTO `d_areas` VALUES ('3311', '丽水市');
INSERT INTO `d_areas` VALUES ('34', '安徽');
INSERT INTO `d_areas` VALUES ('3401', '合肥市');
INSERT INTO `d_areas` VALUES ('3402', '芜湖市');
INSERT INTO `d_areas` VALUES ('3403', '蚌埠市');
INSERT INTO `d_areas` VALUES ('3404', '淮南市');
INSERT INTO `d_areas` VALUES ('3405', '马鞍山市');
INSERT INTO `d_areas` VALUES ('3406', '淮北市');
INSERT INTO `d_areas` VALUES ('3407', '铜陵市');
INSERT INTO `d_areas` VALUES ('3408', '安庆市');
INSERT INTO `d_areas` VALUES ('3410', '黄山市');
INSERT INTO `d_areas` VALUES ('3411', '滁州市');
INSERT INTO `d_areas` VALUES ('3412', '阜阳市');
INSERT INTO `d_areas` VALUES ('3413', '宿州市');
INSERT INTO `d_areas` VALUES ('3414', '巢湖市');
INSERT INTO `d_areas` VALUES ('3415', '六安市');
INSERT INTO `d_areas` VALUES ('3416', '亳州市');
INSERT INTO `d_areas` VALUES ('3417', '池州市');
INSERT INTO `d_areas` VALUES ('3418', '宣城市');
INSERT INTO `d_areas` VALUES ('35', '福建');
INSERT INTO `d_areas` VALUES ('3501', '福州市');
INSERT INTO `d_areas` VALUES ('3502', '厦门市');
INSERT INTO `d_areas` VALUES ('3503', '莆田市');
INSERT INTO `d_areas` VALUES ('3504', '三明市');
INSERT INTO `d_areas` VALUES ('3505', '泉州市');
INSERT INTO `d_areas` VALUES ('3506', '漳州市');
INSERT INTO `d_areas` VALUES ('3507', '南平市');
INSERT INTO `d_areas` VALUES ('3508', '龙岩市');
INSERT INTO `d_areas` VALUES ('3509', '宁德市');
INSERT INTO `d_areas` VALUES ('36', '江西');
INSERT INTO `d_areas` VALUES ('3601', '南昌市');
INSERT INTO `d_areas` VALUES ('3602', '景德镇市');
INSERT INTO `d_areas` VALUES ('3603', '萍乡市');
INSERT INTO `d_areas` VALUES ('3604', '九江市');
INSERT INTO `d_areas` VALUES ('3605', '新余市');
INSERT INTO `d_areas` VALUES ('3606', '鹰潭市');
INSERT INTO `d_areas` VALUES ('3607', '赣州市');
INSERT INTO `d_areas` VALUES ('3608', '吉安市');
INSERT INTO `d_areas` VALUES ('3609', '宜春市');
INSERT INTO `d_areas` VALUES ('3610', '抚州市');
INSERT INTO `d_areas` VALUES ('3611', '上饶市');
INSERT INTO `d_areas` VALUES ('37', '山东');
INSERT INTO `d_areas` VALUES ('3701', '济南市');
INSERT INTO `d_areas` VALUES ('3702', '青岛市');
INSERT INTO `d_areas` VALUES ('3703', '淄博市');
INSERT INTO `d_areas` VALUES ('3704', '枣庄市');
INSERT INTO `d_areas` VALUES ('3705', '东营市');
INSERT INTO `d_areas` VALUES ('3706', '烟台市');
INSERT INTO `d_areas` VALUES ('3707', '潍坊市');
INSERT INTO `d_areas` VALUES ('3708', '济宁市');
INSERT INTO `d_areas` VALUES ('3709', '泰安市');
INSERT INTO `d_areas` VALUES ('3710', '威海市');
INSERT INTO `d_areas` VALUES ('3711', '日照市');
INSERT INTO `d_areas` VALUES ('3712', '莱芜市');
INSERT INTO `d_areas` VALUES ('3713', '临沂市');
INSERT INTO `d_areas` VALUES ('3714', '德州市');
INSERT INTO `d_areas` VALUES ('3715', '聊城市');
INSERT INTO `d_areas` VALUES ('3716', '滨州市');
INSERT INTO `d_areas` VALUES ('3717', '菏泽市');
INSERT INTO `d_areas` VALUES ('41', '河南');
INSERT INTO `d_areas` VALUES ('4101', '郑州市');
INSERT INTO `d_areas` VALUES ('4102', '开封市');
INSERT INTO `d_areas` VALUES ('4103', '洛阳市');
INSERT INTO `d_areas` VALUES ('4104', '平顶山市');
INSERT INTO `d_areas` VALUES ('4105', '安阳市');
INSERT INTO `d_areas` VALUES ('4106', '鹤壁市');
INSERT INTO `d_areas` VALUES ('4107', '新乡市');
INSERT INTO `d_areas` VALUES ('4108', '焦作市');
INSERT INTO `d_areas` VALUES ('4109', '濮阳市');
INSERT INTO `d_areas` VALUES ('4110', '许昌市');
INSERT INTO `d_areas` VALUES ('4111', '漯河市');
INSERT INTO `d_areas` VALUES ('4112', '三门峡市');
INSERT INTO `d_areas` VALUES ('4113', '南阳市');
INSERT INTO `d_areas` VALUES ('4114', '商丘市');
INSERT INTO `d_areas` VALUES ('4115', '信阳市');
INSERT INTO `d_areas` VALUES ('4116', '周口市');
INSERT INTO `d_areas` VALUES ('4117', '驻马店市');
INSERT INTO `d_areas` VALUES ('4190', '省直辖县级行政区划');
INSERT INTO `d_areas` VALUES ('42', '湖北');
INSERT INTO `d_areas` VALUES ('4201', '武汉市');
INSERT INTO `d_areas` VALUES ('4202', '黄石市');
INSERT INTO `d_areas` VALUES ('4203', '十堰市');
INSERT INTO `d_areas` VALUES ('4205', '宜昌市');
INSERT INTO `d_areas` VALUES ('4206', '襄阳市');
INSERT INTO `d_areas` VALUES ('4207', '鄂州市');
INSERT INTO `d_areas` VALUES ('4208', '荆门市');
INSERT INTO `d_areas` VALUES ('4209', '孝感市');
INSERT INTO `d_areas` VALUES ('4210', '荆州市');
INSERT INTO `d_areas` VALUES ('4211', '黄冈市');
INSERT INTO `d_areas` VALUES ('4212', '咸宁市');
INSERT INTO `d_areas` VALUES ('4213', '随州市');
INSERT INTO `d_areas` VALUES ('4228', '恩施土家族苗族自治州');
INSERT INTO `d_areas` VALUES ('4290', '省直辖县级行政区划');
INSERT INTO `d_areas` VALUES ('43', '湖南');
INSERT INTO `d_areas` VALUES ('4301', '长沙市');
INSERT INTO `d_areas` VALUES ('4302', '株洲市');
INSERT INTO `d_areas` VALUES ('4303', '湘潭市');
INSERT INTO `d_areas` VALUES ('4304', '衡阳市');
INSERT INTO `d_areas` VALUES ('4305', '邵阳市');
INSERT INTO `d_areas` VALUES ('4306', '岳阳市');
INSERT INTO `d_areas` VALUES ('4307', '常德市');
INSERT INTO `d_areas` VALUES ('4308', '张家界市');
INSERT INTO `d_areas` VALUES ('4309', '益阳市');
INSERT INTO `d_areas` VALUES ('4310', '郴州市');
INSERT INTO `d_areas` VALUES ('4311', '永州市');
INSERT INTO `d_areas` VALUES ('4312', '怀化市');
INSERT INTO `d_areas` VALUES ('4313', '娄底市');
INSERT INTO `d_areas` VALUES ('4331', '湘西土家族苗族自治州');
INSERT INTO `d_areas` VALUES ('44', '广东');
INSERT INTO `d_areas` VALUES ('4401', '广州市');
INSERT INTO `d_areas` VALUES ('4402', '韶关市');
INSERT INTO `d_areas` VALUES ('4403', '深圳市');
INSERT INTO `d_areas` VALUES ('4404', '珠海市');
INSERT INTO `d_areas` VALUES ('4405', '汕头市');
INSERT INTO `d_areas` VALUES ('4406', '佛山市');
INSERT INTO `d_areas` VALUES ('4407', '江门市');
INSERT INTO `d_areas` VALUES ('4408', '湛江市');
INSERT INTO `d_areas` VALUES ('4409', '茂名市');
INSERT INTO `d_areas` VALUES ('4412', '肇庆市');
INSERT INTO `d_areas` VALUES ('4413', '惠州市');
INSERT INTO `d_areas` VALUES ('4414', '梅州市');
INSERT INTO `d_areas` VALUES ('4415', '汕尾市');
INSERT INTO `d_areas` VALUES ('4416', '河源市');
INSERT INTO `d_areas` VALUES ('4417', '阳江市');
INSERT INTO `d_areas` VALUES ('4418', '清远市');
INSERT INTO `d_areas` VALUES ('4419', '东莞市');
INSERT INTO `d_areas` VALUES ('4420', '中山市');
INSERT INTO `d_areas` VALUES ('4451', '潮州市');
INSERT INTO `d_areas` VALUES ('4452', '揭阳市');
INSERT INTO `d_areas` VALUES ('4453', '云浮市');
INSERT INTO `d_areas` VALUES ('45', '广西');
INSERT INTO `d_areas` VALUES ('4501', '南宁市');
INSERT INTO `d_areas` VALUES ('4502', '柳州市');
INSERT INTO `d_areas` VALUES ('4503', '桂林市');
INSERT INTO `d_areas` VALUES ('4504', '梧州市');
INSERT INTO `d_areas` VALUES ('4505', '北海市');
INSERT INTO `d_areas` VALUES ('4506', '防城港市');
INSERT INTO `d_areas` VALUES ('4507', '钦州市');
INSERT INTO `d_areas` VALUES ('4508', '贵港市');
INSERT INTO `d_areas` VALUES ('4509', '玉林市');
INSERT INTO `d_areas` VALUES ('4510', '百色市');
INSERT INTO `d_areas` VALUES ('4511', '贺州市');
INSERT INTO `d_areas` VALUES ('4512', '河池市');
INSERT INTO `d_areas` VALUES ('4513', '来宾市');
INSERT INTO `d_areas` VALUES ('4514', '崇左市');
INSERT INTO `d_areas` VALUES ('46', '海南');
INSERT INTO `d_areas` VALUES ('4601', '海口市');
INSERT INTO `d_areas` VALUES ('4602', '三亚市');
INSERT INTO `d_areas` VALUES ('4690', '省直辖县级行政区划');
INSERT INTO `d_areas` VALUES ('50', '重庆');
INSERT INTO `d_areas` VALUES ('51', '四川');
INSERT INTO `d_areas` VALUES ('5101', '成都市');
INSERT INTO `d_areas` VALUES ('5103', '自贡市');
INSERT INTO `d_areas` VALUES ('5104', '攀枝花市');
INSERT INTO `d_areas` VALUES ('5105', '泸州市');
INSERT INTO `d_areas` VALUES ('5106', '德阳市');
INSERT INTO `d_areas` VALUES ('5107', '绵阳市');
INSERT INTO `d_areas` VALUES ('5108', '广元市');
INSERT INTO `d_areas` VALUES ('5109', '遂宁市');
INSERT INTO `d_areas` VALUES ('5110', '内江市');
INSERT INTO `d_areas` VALUES ('5111', '乐山市');
INSERT INTO `d_areas` VALUES ('5113', '南充市');
INSERT INTO `d_areas` VALUES ('5114', '眉山市');
INSERT INTO `d_areas` VALUES ('5115', '宜宾市');
INSERT INTO `d_areas` VALUES ('5116', '广安市');
INSERT INTO `d_areas` VALUES ('5117', '达州市');
INSERT INTO `d_areas` VALUES ('5118', '雅安市');
INSERT INTO `d_areas` VALUES ('5119', '巴中市');
INSERT INTO `d_areas` VALUES ('5120', '资阳市');
INSERT INTO `d_areas` VALUES ('5132', '阿坝藏族羌族自治州');
INSERT INTO `d_areas` VALUES ('5133', '甘孜藏族自治州');
INSERT INTO `d_areas` VALUES ('5134', '凉山彝族自治州');
INSERT INTO `d_areas` VALUES ('52', '贵州');
INSERT INTO `d_areas` VALUES ('5201', '贵阳市');
INSERT INTO `d_areas` VALUES ('5202', '六盘水市');
INSERT INTO `d_areas` VALUES ('5203', '遵义市');
INSERT INTO `d_areas` VALUES ('5204', '安顺市');
INSERT INTO `d_areas` VALUES ('5222', '铜仁地区');
INSERT INTO `d_areas` VALUES ('5223', '黔西南布依族苗族自治州');
INSERT INTO `d_areas` VALUES ('5224', '毕节地区');
INSERT INTO `d_areas` VALUES ('5226', '黔东南苗族侗族自治州');
INSERT INTO `d_areas` VALUES ('5227', '黔南布依族苗族自治州');
INSERT INTO `d_areas` VALUES ('53', '云南');
INSERT INTO `d_areas` VALUES ('5301', '昆明市');
INSERT INTO `d_areas` VALUES ('5303', '曲靖市');
INSERT INTO `d_areas` VALUES ('5304', '玉溪市');
INSERT INTO `d_areas` VALUES ('5305', '保山市');
INSERT INTO `d_areas` VALUES ('5306', '昭通市');
INSERT INTO `d_areas` VALUES ('5307', '丽江市');
INSERT INTO `d_areas` VALUES ('5308', '普洱市');
INSERT INTO `d_areas` VALUES ('5309', '临沧市');
INSERT INTO `d_areas` VALUES ('5323', '楚雄彝族自治州');
INSERT INTO `d_areas` VALUES ('5325', '红河哈尼族彝族自治州');
INSERT INTO `d_areas` VALUES ('5326', '文山壮族苗族自治州');
INSERT INTO `d_areas` VALUES ('5328', '西双版纳傣族自治州');
INSERT INTO `d_areas` VALUES ('5329', '大理白族自治州');
INSERT INTO `d_areas` VALUES ('5331', '德宏傣族景颇族自治州');
INSERT INTO `d_areas` VALUES ('5333', '怒江傈僳族自治州');
INSERT INTO `d_areas` VALUES ('5334', '迪庆藏族自治州');
INSERT INTO `d_areas` VALUES ('54', '西藏');
INSERT INTO `d_areas` VALUES ('5401', '拉萨市');
INSERT INTO `d_areas` VALUES ('5421', '昌都地区');
INSERT INTO `d_areas` VALUES ('5422', '山南地区');
INSERT INTO `d_areas` VALUES ('5423', '日喀则地区');
INSERT INTO `d_areas` VALUES ('5424', '那曲地区');
INSERT INTO `d_areas` VALUES ('5425', '阿里地区');
INSERT INTO `d_areas` VALUES ('5426', '林芝地区');
INSERT INTO `d_areas` VALUES ('61', '陕西');
INSERT INTO `d_areas` VALUES ('6101', '西安市');
INSERT INTO `d_areas` VALUES ('6102', '铜川市');
INSERT INTO `d_areas` VALUES ('6103', '宝鸡市');
INSERT INTO `d_areas` VALUES ('6104', '咸阳市');
INSERT INTO `d_areas` VALUES ('6105', '渭南市');
INSERT INTO `d_areas` VALUES ('6106', '延安市');
INSERT INTO `d_areas` VALUES ('6107', '汉中市');
INSERT INTO `d_areas` VALUES ('6108', '榆林市');
INSERT INTO `d_areas` VALUES ('6109', '安康市');
INSERT INTO `d_areas` VALUES ('6110', '商洛市');
INSERT INTO `d_areas` VALUES ('62', '甘肃');
INSERT INTO `d_areas` VALUES ('6201', '兰州市');
INSERT INTO `d_areas` VALUES ('6202', '嘉峪关市');
INSERT INTO `d_areas` VALUES ('6203', '金昌市');
INSERT INTO `d_areas` VALUES ('6204', '白银市');
INSERT INTO `d_areas` VALUES ('6205', '天水市');
INSERT INTO `d_areas` VALUES ('6206', '武威市');
INSERT INTO `d_areas` VALUES ('6207', '张掖市');
INSERT INTO `d_areas` VALUES ('6208', '平凉市');
INSERT INTO `d_areas` VALUES ('6209', '酒泉市');
INSERT INTO `d_areas` VALUES ('6210', '庆阳市');
INSERT INTO `d_areas` VALUES ('6211', '定西市');
INSERT INTO `d_areas` VALUES ('6212', '陇南市');
INSERT INTO `d_areas` VALUES ('6229', '临夏回族自治州');
INSERT INTO `d_areas` VALUES ('6230', '甘南藏族自治州');
INSERT INTO `d_areas` VALUES ('63', '青海');
INSERT INTO `d_areas` VALUES ('6301', '西宁市');
INSERT INTO `d_areas` VALUES ('6321', '海东地区');
INSERT INTO `d_areas` VALUES ('6322', '海北藏族自治州');
INSERT INTO `d_areas` VALUES ('6323', '黄南藏族自治州');
INSERT INTO `d_areas` VALUES ('6325', '海南藏族自治州');
INSERT INTO `d_areas` VALUES ('6326', '果洛藏族自治州');
INSERT INTO `d_areas` VALUES ('6327', '玉树藏族自治州');
INSERT INTO `d_areas` VALUES ('6328', '海西蒙古族藏族自治州');
INSERT INTO `d_areas` VALUES ('64', '宁夏');
INSERT INTO `d_areas` VALUES ('6401', '银川市');
INSERT INTO `d_areas` VALUES ('6402', '石嘴山市');
INSERT INTO `d_areas` VALUES ('6403', '吴忠市');
INSERT INTO `d_areas` VALUES ('6404', '固原市');
INSERT INTO `d_areas` VALUES ('6405', '中卫市');
INSERT INTO `d_areas` VALUES ('65', '新疆');
INSERT INTO `d_areas` VALUES ('6501', '乌鲁木齐市');
INSERT INTO `d_areas` VALUES ('6502', '克拉玛依市');
INSERT INTO `d_areas` VALUES ('6521', '吐鲁番地区');
INSERT INTO `d_areas` VALUES ('6522', '哈密地区');
INSERT INTO `d_areas` VALUES ('6523', '昌吉回族自治州');
INSERT INTO `d_areas` VALUES ('6527', '博尔塔拉蒙古自治州');
INSERT INTO `d_areas` VALUES ('6528', '巴音郭楞蒙古自治州');
INSERT INTO `d_areas` VALUES ('6529', '阿克苏地区');
INSERT INTO `d_areas` VALUES ('6530', '克孜勒苏柯尔克孜自治州');
INSERT INTO `d_areas` VALUES ('6531', '喀什地区');
INSERT INTO `d_areas` VALUES ('6532', '和田地区');
INSERT INTO `d_areas` VALUES ('6540', '伊犁哈萨克自治州');
INSERT INTO `d_areas` VALUES ('6542', '塔城地区');
INSERT INTO `d_areas` VALUES ('6543', '阿勒泰地区');
INSERT INTO `d_areas` VALUES ('6590', '自治区直辖县级行政区划');
INSERT INTO `d_areas` VALUES ('71', '台湾');
INSERT INTO `d_areas` VALUES ('7101', '台北市');
INSERT INTO `d_areas` VALUES ('7102', '桃园市');

-- ----------------------------
-- Table structure for d_evaluation_model
-- ----------------------------
DROP TABLE IF EXISTS `d_evaluation_model`;
CREATE TABLE `d_evaluation_model` (
  `model_id` int(11) NOT NULL AUTO_INCREMENT,
  `model_name` varchar(30) DEFAULT NULL COMMENT '描述',
  `model_desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `model_weight` int(2) DEFAULT NULL COMMENT '占比，百分比2位数',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '0-禁用；1-启用',
  `model_type` int(1) DEFAULT '2' COMMENT '评测类型：1-简单测评；2-系统定义专业测评;3-用户自定义专业测评',
  `detail_desc` varchar(255) DEFAULT NULL COMMENT '专业评测对应的维度名称集合',
  `batch_id` int(11) DEFAULT NULL COMMENT '批次号',
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of d_evaluation_model
-- ----------------------------

-- ----------------------------
-- Table structure for d_evaluation_model_detail
-- ----------------------------
DROP TABLE IF EXISTS `d_evaluation_model_detail`;
CREATE TABLE `d_evaluation_model_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model_id` int(11) NOT NULL,
  `detail_name` varchar(30) DEFAULT NULL COMMENT '维度名称',
  `detail_desc` varchar(255) DEFAULT NULL COMMENT '维度说明',
  `detail_weight` int(2) DEFAULT NULL COMMENT '占比，百分比2位数',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '0-删除；1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of d_evaluation_model_detail
-- ----------------------------

-- ----------------------------
-- Table structure for d_project_type
-- ----------------------------
DROP TABLE IF EXISTS `d_project_type`;
CREATE TABLE `d_project_type` (
  `project_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_type_name` varchar(30) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '状态:0-删除；1-有效',
  PRIMARY KEY (`project_type_id`),
  UNIQUE KEY `uq_idx_type_name` (`project_type_name`,`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of d_project_type
-- ----------------------------

-- ----------------------------
-- Table structure for d_tags
-- ----------------------------
DROP TABLE IF EXISTS `d_tags`;
CREATE TABLE `d_tags` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(8) NOT NULL COMMENT '标签名称，不超过8个字',
  `create_user_id` int(11) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '1' COMMENT '状态:0-删除;1-有效',
  `memo` varchar(20) DEFAULT NULL COMMENT '标签备注，最多20字',
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `unq_tag_name` (`tag_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of d_tags
-- ----------------------------

-- ----------------------------
-- Table structure for tbapprovalrating
-- ----------------------------
DROP TABLE IF EXISTS `tbapprovalrating`;
CREATE TABLE `tbapprovalrating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vcApprovalName` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统中的最大可执行的审批等级\n暂定为三级：\n预审\n经办\n审批';

-- ----------------------------
-- Records of tbapprovalrating
-- ----------------------------

-- ----------------------------
-- Table structure for tbarticle
-- ----------------------------
DROP TABLE IF EXISTS `tbarticle`;
CREATE TABLE `tbarticle` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) NOT NULL,
  `post_uuid` varchar(32) DEFAULT NULL,
  `article_contents` text COMMENT '文章内容，目前限定30000字，图片信息用富文本保存在里边',
  PRIMARY KEY (`article_id`),
  KEY `FK_post_id` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbarticle
-- ----------------------------

-- ----------------------------
-- Table structure for tbauthentication
-- ----------------------------
DROP TABLE IF EXISTS `tbauthentication`;
CREATE TABLE `tbauthentication` (
  `accountAuthenticationId` int(11) NOT NULL AUTO_INCREMENT COMMENT '认证ID',
  `userId` int(11) NOT NULL COMMENT '用户ID',
  `type` int(1) DEFAULT NULL COMMENT '认证类型   1  项目号  2    评测媒体    3     机构号  ',
  `qufenNickName` varchar(100) DEFAULT NULL COMMENT '区分昵称',
  `authInformation` varchar(300) DEFAULT NULL COMMENT '认证信息',
  `company` varchar(100) DEFAULT NULL COMMENT '企业名称',
  `registrationNum` varchar(100) DEFAULT NULL COMMENT '营业执照注册号',
  `licencePic` varchar(255) DEFAULT NULL COMMENT '营业照',
  `missivePic` varchar(255) DEFAULT NULL COMMENT '认证公函照',
  `operatorName` varchar(20) DEFAULT NULL COMMENT '运营者姓名',
  `assistPic` varchar(255) DEFAULT NULL COMMENT '辅助材料照片',
  `link` varchar(100) DEFAULT NULL COMMENT '评测内容链接',
  `wechat` varchar(36) DEFAULT NULL COMMENT '微信号',
  `number` varchar(11) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(50) DEFAULT NULL COMMENT '联系邮箱',
  `mediaName` varchar(50) DEFAULT NULL COMMENT '媒体名称',
  `mediaChannel` varchar(100) DEFAULT NULL COMMENT '媒体渠道',
  `mediaIntroduce` varchar(300) DEFAULT NULL COMMENT '媒体介绍',
  `status` int(2) DEFAULT NULL COMMENT '1    通过审核   2  审核中3 审核不通过    4未提交审核 ',
  `valid` int(2) DEFAULT NULL COMMENT '有效性     1  有效      0    无效',
  `createData` date DEFAULT NULL COMMENT '提交时间',
  `notPassReason` varchar(300) DEFAULT NULL COMMENT '没通过审核原因',
  PRIMARY KEY (`accountAuthenticationId`)
) ENGINE=InnoDB AUTO_INCREMENT=300 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbauthentication
-- ----------------------------

-- ----------------------------
-- Table structure for tbcoinproperty
-- ----------------------------
DROP TABLE IF EXISTS `tbcoinproperty`;
CREATE TABLE `tbcoinproperty` (
  `coin_property_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资产id',
  `user_id` int(11) DEFAULT NULL,
  `coin_lock` double(11,0) DEFAULT NULL COMMENT '锁定状态的币值',
  `coin_unlock` double(11,0) DEFAULT NULL COMMENT '解锁中的币值',
  `coin_unlock_time` datetime(6) DEFAULT NULL COMMENT '解锁时间',
  `coin_unlock_type` int(3) DEFAULT NULL COMMENT '1-解锁中  2-解锁完成 3-取消解锁',
  `coin_distributed` double(11,0) DEFAULT NULL COMMENT '发放中的B',
  `coin_usable` double(11,0) DEFAULT NULL COMMENT '可用(可用提现的,解锁完成的)',
  `coin_unlock_uptime` datetime(6) DEFAULT NULL COMMENT '解锁完成时间',
  PRIMARY KEY (`coin_property_id`),
  UNIQUE KEY `uk_user` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbcoinproperty
-- ----------------------------

-- ----------------------------
-- Table structure for tbcollect
-- ----------------------------
DROP TABLE IF EXISTS `tbcollect`;
CREATE TABLE `tbcollect` (
  `collect_id` int(11) NOT NULL AUTO_INCREMENT,
  `collect_user_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `post_type` int(1) NOT NULL COMMENT '帖子类型：1-评测；2-讨论；3-文章',
  `status` int(1) DEFAULT NULL COMMENT '状态:0-取消收藏；1-收藏',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`collect_id`),
  UNIQUE KEY `uk_user_post_status` (`collect_user_id`,`post_id`) USING BTREE,
  KEY `idx_post_id` (`post_id`) USING BTREE,
  KEY `idx_collect_user_id` (`collect_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbcollect
-- ----------------------------

-- ----------------------------
-- Table structure for tbcommendation
-- ----------------------------
DROP TABLE IF EXISTS `tbcommendation`;
CREATE TABLE `tbcommendation` (
  `commendation_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) NOT NULL DEFAULT '1',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `send_user_id` int(11) DEFAULT NULL COMMENT '捐赠人用户ID',
  `send_user_icon` varchar(255) DEFAULT NULL COMMENT '发送人头像url',
  `receive_user_id` int(11) DEFAULT NULL COMMENT '接受人id',
  `post_id` int(11) DEFAULT NULL COMMENT '帖子ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目ID',
  `post_type` int(1) DEFAULT NULL COMMENT '帖子类型：1-评测；2-讨论；3-文章',
  `amount` int(11) DEFAULT NULL COMMENT '捐赠金额',
  PRIMARY KEY (`commendation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbcommendation
-- ----------------------------

-- ----------------------------
-- Table structure for tbcomments
-- ----------------------------
DROP TABLE IF EXISTS `tbcomments`;
CREATE TABLE `tbcomments` (
  `comments_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_user_id` int(11) DEFAULT NULL,
  `comment_user_icon` varchar(255) DEFAULT NULL,
  `comment_user_name` varchar(100) DEFAULT NULL,
  `comment_content` varchar(300) DEFAULT NULL COMMENT '评论内容',
  `project_id` int(11) DEFAULT NULL,
  `post_id` int(11) DEFAULT NULL COMMENT '帖子ID',
  `post_type` int(1) DEFAULT NULL COMMENT '帖子类型：1-评测；2-讨论；3-文章',
  `praise_num` int(11) DEFAULT '0' COMMENT '点赞数量',
  `parent_comments_id` int(11) DEFAULT NULL COMMENT '评论ID',
  `becommented_user_id` int(11) DEFAULT NULL,
  `becommented_user_name` varchar(100) DEFAULT NULL,
  `becommented_user_icon` varchar(100) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '状态:0-删除；1-有效',
  PRIMARY KEY (`comments_id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbcomments
-- ----------------------------

-- ----------------------------
-- Table structure for tbconsoleloginaccount
-- ----------------------------
DROP TABLE IF EXISTS `tbconsoleloginaccount`;
CREATE TABLE `tbconsoleloginaccount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vcLoginName` char(13) COLLATE utf8_bin DEFAULT NULL COMMENT '系统的后台登陆账号，默认采用手机号码。',
  `vcLoginPassword` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `vcRealName` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `vcPhone` char(13) COLLATE utf8_bin DEFAULT NULL,
  `iValid` int(2) DEFAULT '1' COMMENT '是否有效。\n            1 -- 有效 （默认）\n            2 -- 无效',
  `dtCreate` datetime DEFAULT NULL COMMENT '该条记录第一次创建的时间。',
  `dtModify` datetime DEFAULT NULL COMMENT '最后一次修改时间，每次修改需更改此字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统管理平台登陆账号表';

-- ----------------------------
-- Records of tbconsoleloginaccount
-- ----------------------------
INSERT INTO `tbconsoleloginaccount` VALUES ('1', 'root', 'ee79976c9380d5e337fc1c095ece8c8f22f91f306ceeb161fa51fecede2c4ba1', 'root', '15967158669', '1', '2014-12-10 15:41:07', '2015-09-29 10:26:20');
INSERT INTO `tbconsoleloginaccount` VALUES ('2', 'swj', 'ee79976c9380d5e337fc1c095ece8c8f22f91f306ceeb161fa51fecede2c4ba1', '宋文健', '13388603801', '1', '2015-03-13 15:22:46', '2015-03-13 17:03:58');
INSERT INTO `tbconsoleloginaccount` VALUES ('3', 'lzy', '0d9b0da6eae9dc5d3e6bd0968acd1a2e1180b6088ed5946cc7f1a9dc205aa290', '刘志远', '13388603801', '1', '2015-03-13 15:23:19', '2018-06-07 15:43:24');
INSERT INTO `tbconsoleloginaccount` VALUES ('62', 'wh', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '王晗', '18632152811', '1', '2018-05-19 08:55:13', '2018-05-19 09:06:50');
INSERT INTO `tbconsoleloginaccount` VALUES ('63', 'fghfhgf', '51d97a389cb239913df2bd72a4aafb4b7f961229bb580f80f3f142bf6a2bbddd', 'fghfghg', '打算大撒旦撒', '2', '2018-06-08 21:35:47', '2018-06-09 10:26:30');
INSERT INTO `tbconsoleloginaccount` VALUES ('64', '2222', 'bce367ef69da4897e8f96ad72586ff917229cb20ac0c5cc83d9533a82688233b', '2222', '13777829385', '1', '2018-06-09 10:01:59', null);
INSERT INTO `tbconsoleloginaccount` VALUES ('65', '1111', '7a1e9f5cbd3476c1539a7de3a1823cdf9b6eaec857bfe5f40374f5c686fd5439', '111', '13777829385', '1', '2018-06-09 10:13:38', null);
INSERT INTO `tbconsoleloginaccount` VALUES ('66', '22', '013893f5787b6ff8d24e37c51119f6d6b8a94e965af1b04847fc4fc100720e4f', '2222', '15856983323', '1', '2018-06-09 10:57:35', null);
INSERT INTO `tbconsoleloginaccount` VALUES ('67', '2222444', '045014bc9056f096fcb13de1a6d6e96c5845eab9b5ceb125dc6d0c7a8fb467ac', '2222244', '15856983323', '1', '2018-06-09 10:59:39', null);
INSERT INTO `tbconsoleloginaccount` VALUES ('68', '666', '39b3f9d37181ee9afb8d62fd688bb7ed0e0891b7c4928882a9104d95612aaef1', '666', '15856983323', '1', '2018-06-09 11:02:16', null);
INSERT INTO `tbconsoleloginaccount` VALUES ('69', '1234', 'af6d53463e0edb8f408978a37cb0ae5edd9dede95e0788ef54787f74fb5d55fe', '1234', '13777829385', '1', '2018-06-09 11:03:26', null);
INSERT INTO `tbconsoleloginaccount` VALUES ('70', 'aaaaa', 'dc8c2a5481b844f65e96a5e1a32b3d010c5d79023446cb7f7e82128a32699bcf', '顶顶顶顶', '15856983323', '1', '2018-06-09 18:48:23', null);
INSERT INTO `tbconsoleloginaccount` VALUES ('71', '诶我去二群若', '6e4ce0e4758aa359f98d8183b3a9cd08b81d1fcd9a63eb58153deb4637e2b8d9', '去玩儿群无若无群若群若', '12345678910', '1', '2018-06-12 13:49:40', null);

-- ----------------------------
-- Table structure for tbconsolepersonalrole
-- ----------------------------
DROP TABLE IF EXISTS `tbconsolepersonalrole`;
CREATE TABLE `tbconsolepersonalrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `iConsoleLoginAccountID` int(11) DEFAULT NULL COMMENT '关联到tbConsoleLoginAccount',
  `iRoleinfoID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='人员和角色的关联信息';

-- ----------------------------
-- Records of tbconsolepersonalrole
-- ----------------------------
INSERT INTO `tbconsolepersonalrole` VALUES ('1', '2', '1');
INSERT INTO `tbconsolepersonalrole` VALUES ('5', '6', '5');
INSERT INTO `tbconsolepersonalrole` VALUES ('6', '7', '6');
INSERT INTO `tbconsolepersonalrole` VALUES ('9', '10', '8');
INSERT INTO `tbconsolepersonalrole` VALUES ('10', '9', '7');
INSERT INTO `tbconsolepersonalrole` VALUES ('11', '11', '9');
INSERT INTO `tbconsolepersonalrole` VALUES ('16', '13', '11');
INSERT INTO `tbconsolepersonalrole` VALUES ('19', '17', '13');
INSERT INTO `tbconsolepersonalrole` VALUES ('20', '4', '3');
INSERT INTO `tbconsolepersonalrole` VALUES ('21', '18', '3');
INSERT INTO `tbconsolepersonalrole` VALUES ('23', '20', '15');
INSERT INTO `tbconsolepersonalrole` VALUES ('30', '22', '17');
INSERT INTO `tbconsolepersonalrole` VALUES ('31', '23', '9');
INSERT INTO `tbconsolepersonalrole` VALUES ('33', '21', '16');
INSERT INTO `tbconsolepersonalrole` VALUES ('34', '21', '17');
INSERT INTO `tbconsolepersonalrole` VALUES ('35', '19', '14');
INSERT INTO `tbconsolepersonalrole` VALUES ('37', '25', '5');
INSERT INTO `tbconsolepersonalrole` VALUES ('38', '26', '18');
INSERT INTO `tbconsolepersonalrole` VALUES ('40', '28', '4');
INSERT INTO `tbconsolepersonalrole` VALUES ('41', '29', '7');
INSERT INTO `tbconsolepersonalrole` VALUES ('42', '30', '19');
INSERT INTO `tbconsolepersonalrole` VALUES ('45', '32', '19');
INSERT INTO `tbconsolepersonalrole` VALUES ('46', '12', '10');
INSERT INTO `tbconsolepersonalrole` VALUES ('47', '33', '7');
INSERT INTO `tbconsolepersonalrole` VALUES ('48', '34', '7');
INSERT INTO `tbconsolepersonalrole` VALUES ('49', '31', '10');
INSERT INTO `tbconsolepersonalrole` VALUES ('50', '31', '20');
INSERT INTO `tbconsolepersonalrole` VALUES ('58', '41', '21');
INSERT INTO `tbconsolepersonalrole` VALUES ('59', '42', '21');
INSERT INTO `tbconsolepersonalrole` VALUES ('60', '43', '21');
INSERT INTO `tbconsolepersonalrole` VALUES ('61', '44', '21');
INSERT INTO `tbconsolepersonalrole` VALUES ('63', '27', '22');
INSERT INTO `tbconsolepersonalrole` VALUES ('64', '46', '23');
INSERT INTO `tbconsolepersonalrole` VALUES ('66', '48', '25');
INSERT INTO `tbconsolepersonalrole` VALUES ('67', '16', '1');
INSERT INTO `tbconsolepersonalrole` VALUES ('68', '16', '2');
INSERT INTO `tbconsolepersonalrole` VALUES ('69', '16', '3');
INSERT INTO `tbconsolepersonalrole` VALUES ('70', '16', '4');
INSERT INTO `tbconsolepersonalrole` VALUES ('71', '16', '5');
INSERT INTO `tbconsolepersonalrole` VALUES ('72', '16', '18');
INSERT INTO `tbconsolepersonalrole` VALUES ('73', '1', '1');
INSERT INTO `tbconsolepersonalrole` VALUES ('74', '1', '2');
INSERT INTO `tbconsolepersonalrole` VALUES ('75', '1', '3');
INSERT INTO `tbconsolepersonalrole` VALUES ('76', '1', '4');
INSERT INTO `tbconsolepersonalrole` VALUES ('77', '1', '5');
INSERT INTO `tbconsolepersonalrole` VALUES ('78', '1', '6');
INSERT INTO `tbconsolepersonalrole` VALUES ('79', '1', '7');
INSERT INTO `tbconsolepersonalrole` VALUES ('80', '1', '8');
INSERT INTO `tbconsolepersonalrole` VALUES ('81', '1', '9');
INSERT INTO `tbconsolepersonalrole` VALUES ('82', '1', '18');
INSERT INTO `tbconsolepersonalrole` VALUES ('83', '49', '9');
INSERT INTO `tbconsolepersonalrole` VALUES ('84', '50', '9');
INSERT INTO `tbconsolepersonalrole` VALUES ('86', '52', '7');
INSERT INTO `tbconsolepersonalrole` VALUES ('87', '14', '12');
INSERT INTO `tbconsolepersonalrole` VALUES ('88', '15', '12');
INSERT INTO `tbconsolepersonalrole` VALUES ('93', '53', '26');
INSERT INTO `tbconsolepersonalrole` VALUES ('96', '51', '10');
INSERT INTO `tbconsolepersonalrole` VALUES ('97', '51', '20');
INSERT INTO `tbconsolepersonalrole` VALUES ('98', '35', '10');
INSERT INTO `tbconsolepersonalrole` VALUES ('99', '35', '20');
INSERT INTO `tbconsolepersonalrole` VALUES ('103', '56', '29');
INSERT INTO `tbconsolepersonalrole` VALUES ('105', '24', '16');
INSERT INTO `tbconsolepersonalrole` VALUES ('107', '59', '30');
INSERT INTO `tbconsolepersonalrole` VALUES ('110', '60', '19');
INSERT INTO `tbconsolepersonalrole` VALUES ('115', '61', '9');
INSERT INTO `tbconsolepersonalrole` VALUES ('116', '57', '29');
INSERT INTO `tbconsolepersonalrole` VALUES ('117', '5', '4');
INSERT INTO `tbconsolepersonalrole` VALUES ('118', '45', '21');
INSERT INTO `tbconsolepersonalrole` VALUES ('120', '62', '1');
INSERT INTO `tbconsolepersonalrole` VALUES ('121', '62', '2');
INSERT INTO `tbconsolepersonalrole` VALUES ('122', '62', '3');
INSERT INTO `tbconsolepersonalrole` VALUES ('123', '62', '5');
INSERT INTO `tbconsolepersonalrole` VALUES ('124', '62', '26');
INSERT INTO `tbconsolepersonalrole` VALUES ('125', '3', '1');
INSERT INTO `tbconsolepersonalrole` VALUES ('127', '64', '1');
INSERT INTO `tbconsolepersonalrole` VALUES ('128', '65', '1');
INSERT INTO `tbconsolepersonalrole` VALUES ('130', '63', '1');
INSERT INTO `tbconsolepersonalrole` VALUES ('131', '66', '1');
INSERT INTO `tbconsolepersonalrole` VALUES ('132', '67', '1');
INSERT INTO `tbconsolepersonalrole` VALUES ('133', '68', '1');
INSERT INTO `tbconsolepersonalrole` VALUES ('134', '69', '2');
INSERT INTO `tbconsolepersonalrole` VALUES ('135', '70', '1');

-- ----------------------------
-- Table structure for tbconsoleresource
-- ----------------------------
DROP TABLE IF EXISTS `tbconsoleresource`;
CREATE TABLE `tbconsoleresource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `iParentId` int(11) DEFAULT NULL,
  `vcName` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `vcUrl` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `iType` int(2) DEFAULT NULL COMMENT '资源类型，1：菜单资源，2：按钮资源',
  `vcButtonName` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `vcIcon` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `isort` int(11) DEFAULT NULL COMMENT '显示菜单按此顺序排列',
  `iValid` int(2) DEFAULT '1' COMMENT '是否有效。\n            1 -- 有效 （默认）\n            2 -- 无效',
  `dtCreate` datetime DEFAULT NULL COMMENT '该条记录第一次创建的时间。',
  `dtModify` datetime DEFAULT NULL COMMENT '最后一次修改时间，每次修改需更改此字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=660 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统权限控制的资源列表';

-- ----------------------------
-- Records of tbconsoleresource
-- ----------------------------
INSERT INTO `tbconsoleresource` VALUES ('9', '0', '系统管理', '/', '1', '', 'icon-cog', '12', '1', '2014-12-08 10:38:00', '2014-12-08 10:40:44');
INSERT INTO `tbconsoleresource` VALUES ('10', '9', '系统参数', '/systemparams', '1', '', '', '2', '1', '2014-12-08 11:11:05', '2014-12-30 07:31:25');
INSERT INTO `tbconsoleresource` VALUES ('11', '87', '账号管理', '/consoleaccounts', '1', null, null, '3', '1', '2014-12-08 10:38:00', '2014-12-08 10:38:00');
INSERT INTO `tbconsoleresource` VALUES ('12', '87', '角色管理', '/roleinfos', '1', null, null, '2', '1', '2014-12-08 10:38:00', '2014-12-08 10:38:00');
INSERT INTO `tbconsoleresource` VALUES ('13', '9', '系统资源', '/resources', '1', '', '', '1', '1', '2014-12-08 10:38:00', '2014-12-30 07:30:35');
INSERT INTO `tbconsoleresource` VALUES ('15', '10', '系统参数/编辑', '/systemparams/update;/systemparams/find', '2', 'resources_update', '', null, '1', '2014-12-08 15:27:47', '2014-12-17 18:43:09');
INSERT INTO `tbconsoleresource` VALUES ('17', '10', '系统参数/添加', '/systemparams/save;/systemparams/new', '2', 'resources_save', '', null, '1', '2014-12-08 15:31:28', '2014-12-17 18:43:54');
INSERT INTO `tbconsoleresource` VALUES ('18', '10', '系统参数/删除', '/systemparams/delete', '2', 'systemparams_delete', '', null, '1', '2014-12-08 15:32:02', '2014-12-20 12:34:26');
INSERT INTO `tbconsoleresource` VALUES ('26', '8', '担保机构', '/guarantee', '1', '', '', '2', '1', '2014-12-15 09:24:47', '2015-01-05 12:59:14');
INSERT INTO `tbconsoleresource` VALUES ('28', '27', '标的列表', '/subject', '1', '', '', '1', '1', '2014-12-15 18:46:58', '2014-12-18 09:59:30');
INSERT INTO `tbconsoleresource` VALUES ('30', '29', '项目列表', '/projects', '1', '', null, null, '1', '2014-12-16 14:05:53', null);
INSERT INTO `tbconsoleresource` VALUES ('33', '26', '担保机构/添加', '/guarantee/save;/guarantee/new', '2', 'guarantee_save', '', null, '1', '2014-12-16 21:06:46', '2015-01-05 12:59:25');
INSERT INTO `tbconsoleresource` VALUES ('34', '26', '担保机构/编辑', '/guarantee/update;/guarantee/find', '2', 'guarantee_update', '', null, '1', '2014-12-16 21:07:27', '2015-01-05 12:59:37');
INSERT INTO `tbconsoleresource` VALUES ('35', '26', '担保机构/删除', '/guarantee/delete', '2', 'guarantee_delete', '', null, '1', '2014-12-16 21:08:28', '2015-01-05 12:59:45');
INSERT INTO `tbconsoleresource` VALUES ('38', '66', '新增标的/添加', '/subject/save;/subject/new', '2', 'subject_save', '', null, '1', '2014-12-17 09:09:24', '2014-12-17 18:47:14');
INSERT INTO `tbconsoleresource` VALUES ('39', '28', '标的列表/编辑', '/subject/update;/subject/find', '2', 'subject_update', '', null, '1', '2014-12-17 09:09:24', '2014-12-17 18:46:03');
INSERT INTO `tbconsoleresource` VALUES ('40', '28', '标的列表/详情', '/subject/getById', '2', 'subject_getById', null, null, '1', '2014-12-17 09:09:24', null);
INSERT INTO `tbconsoleresource` VALUES ('41', '28', '标的列表/提交审核', '/subject/submitApproval', '2', 'subject_submitApproval', null, null, '1', '2014-12-17 09:09:24', null);
INSERT INTO `tbconsoleresource` VALUES ('43', '28', '标的列表/审核', '/subject/approval;/subject/toApproval', '2', 'subject_approval', '', null, '1', '2014-12-17 09:09:24', '2014-12-17 18:46:23');
INSERT INTO `tbconsoleresource` VALUES ('44', '27', '待审核标的', '/subject?istate=2', '1', '', '', '2', '1', '2014-12-17 09:18:45', '2014-12-18 09:59:41');
INSERT INTO `tbconsoleresource` VALUES ('45', '8', '客户列表', '/loginaccount', '1', '', '', '3', '1', '2014-12-17 13:16:36', '2014-12-30 10:07:18');
INSERT INTO `tbconsoleresource` VALUES ('47', '87', '地区管理', '/areainfo', '1', '', '', '3', '1', '2014-12-17 14:23:58', '2014-12-24 14:10:42');
INSERT INTO `tbconsoleresource` VALUES ('48', '30', '项目管理/删除', '/projects/delete', '2', 'projects_audit', '', null, '1', '2014-12-17 18:51:14', null);
INSERT INTO `tbconsoleresource` VALUES ('49', '11', '账号管理/添加', '/consoleaccounts/save;/consoleaccounts/new', '2', null, null, null, '1', '2014-12-08 10:38:00', null);
INSERT INTO `tbconsoleresource` VALUES ('50', '11', '账号管理/编辑', '/consoleaccounts/update;/consoleaccounts/find', '2', null, null, null, '1', '2014-12-08 10:38:00', null);
INSERT INTO `tbconsoleresource` VALUES ('51', '12', '角色管理/删除', '/roleinfos/delete', '2', null, null, null, '1', '2014-12-08 10:38:00', null);
INSERT INTO `tbconsoleresource` VALUES ('52', '12', '角色管理/编辑', '/roleinfos/update;/roleinfos/find', '2', null, null, null, '1', '2014-12-08 10:38:00', null);
INSERT INTO `tbconsoleresource` VALUES ('53', '12', '角色管理/添加', '/roleinfos/save;/roleinfos/new', '2', null, null, null, '1', '2014-12-08 10:38:00', null);
INSERT INTO `tbconsoleresource` VALUES ('54', '13', '资源管理/删除', '/resources/delete', '2', null, null, null, '1', '2014-12-08 10:38:00', null);
INSERT INTO `tbconsoleresource` VALUES ('55', '13', '资源管理/编辑', '/resources/update;/resources/find', '2', null, null, null, '1', '2014-12-08 10:38:00', null);
INSERT INTO `tbconsoleresource` VALUES ('56', '13', '资源管理/添加', '/resources/save;/resources/new', '2', null, null, null, '1', '2014-12-08 10:38:00', null);
INSERT INTO `tbconsoleresource` VALUES ('58', '47', '地区列表/编辑', '/areainfo/update;/areainfo/edit', '2', null, null, null, '1', '2014-12-17 14:23:16', null);
INSERT INTO `tbconsoleresource` VALUES ('59', '47', '地区列表/添加', '/areainfo/save;/areainfo/new', '2', null, null, null, '1', '2014-12-17 14:23:16', null);
INSERT INTO `tbconsoleresource` VALUES ('60', '30', '项目列表/添加', '/projects/save;/projects/new;/projects/borrower;/projects/projectCategyList', '2', '', '', null, '1', '2014-12-16 14:05:53', '2015-01-20 09:06:20');
INSERT INTO `tbconsoleresource` VALUES ('61', '30', '项目列表/编辑', '/projects/update;/projects/edit', '2', null, null, null, '1', '2014-12-16 14:05:53', null);
INSERT INTO `tbconsoleresource` VALUES ('62', '30', '项目列表/提交审核', '/projects/projectSubmit;/projects/submit', '2', '', '', null, '1', '2014-12-16 14:05:53', '2015-01-16 15:08:08');
INSERT INTO `tbconsoleresource` VALUES ('63', '30', '项目列表/审核', '/projects/projectAudit;/projects/audit', '2', '', '', null, '1', '2014-12-16 14:05:53', '2015-01-16 15:57:07');
INSERT INTO `tbconsoleresource` VALUES ('64', '26', '担保机构/详情', '/guarantee/details', '2', 'guarantee_details', '', null, '1', '2014-12-18 10:47:20', '2015-01-05 12:59:53');
INSERT INTO `tbconsoleresource` VALUES ('65', '8', '渠道管理', '/channel', '1', '', '', '1', '1', '2014-12-18 11:34:25', null);
INSERT INTO `tbconsoleresource` VALUES ('66', '27', '新增标的', '/subject/projectList', '1', null, null, '0', '1', '2014-12-17 09:09:24', null);
INSERT INTO `tbconsoleresource` VALUES ('67', '9', '审批等级', '/approvalratings', '1', '', '', '3', '2', '2014-12-18 19:52:56', '2018-04-28 14:53:40');
INSERT INTO `tbconsoleresource` VALUES ('68', '9', '审批流程', '/approvalflows', '1', '', '', '4', '2', '2014-12-18 19:53:33', '2018-04-28 14:53:31');
INSERT INTO `tbconsoleresource` VALUES ('71', '70', '合同模板/添加', '/contracttemplate/save;/contracttemplate/new;/uploadfile/filePathList', '2', '', '', null, '1', '2014-12-18 20:03:02', '2015-02-02 11:25:46');
INSERT INTO `tbconsoleresource` VALUES ('72', '70', '合同模板/编辑', '/contracttemplate/update;/contracttemplate/edit', '2', '', '', null, '1', '2014-12-18 20:04:46', null);
INSERT INTO `tbconsoleresource` VALUES ('73', '67', '审批等级/添加', '/contracttemplate/save;/contracttemplate/new', '2', '', '', null, '1', '2014-12-18 20:06:48', null);
INSERT INTO `tbconsoleresource` VALUES ('74', '67', '审批等级/编辑', '/approvalratings/update;/approvalratings/edit', '2', '', '', null, '1', '2014-12-18 20:08:17', null);
INSERT INTO `tbconsoleresource` VALUES ('75', '68', '审批流程/添加', '/approvalflows/save;/approvalflows/new', '2', '', '', null, '1', '2014-12-18 20:10:03', null);
INSERT INTO `tbconsoleresource` VALUES ('76', '68', '审批流程/编辑', '/approvalflows/update;/approvalflows/edit', '2', '', '', null, '1', '2014-12-18 20:11:13', null);
INSERT INTO `tbconsoleresource` VALUES ('77', '11', '修改密码', '/consoleaccounts/changepassword;/consoleaccounts/updatepassword', '2', '', '', null, '1', '2014-12-18 20:21:01', '2014-12-18 20:22:29');
INSERT INTO `tbconsoleresource` VALUES ('78', '30', '项目列表/详情', '/projects/detail', '2', '', '', null, '1', '2014-12-19 10:38:37', null);
INSERT INTO `tbconsoleresource` VALUES ('79', '8', '新增借款人', '/loginaccount/borrower/new', '1', '', '', '4', '1', '2014-12-19 13:11:11', '2014-12-30 10:08:17');
INSERT INTO `tbconsoleresource` VALUES ('81', '45', '详情', '/loginaccount/details', '2', 'loginaccount_details', '', null, '1', '2014-12-19 15:51:12', null);
INSERT INTO `tbconsoleresource` VALUES ('82', '79', '新增借款人/添加', '/loginaccount/borrower/save', '2', 'account_save', '', null, '1', '2014-12-19 16:01:31', null);
INSERT INTO `tbconsoleresource` VALUES ('83', '26', '担保机构/添加登陆账户', '/guarantee/guarantor/save;/guarantee/guarantor/new', '2', '', '', null, '1', '2014-12-19 16:02:38', '2015-01-05 13:00:13');
INSERT INTO `tbconsoleresource` VALUES ('87', '0', '基础信息', '/', '1', '', 'icon-book', '0', '1', '2014-12-23 11:18:42', '2018-04-27 00:53:04');
INSERT INTO `tbconsoleresource` VALUES ('91', '87', '保障措施', '/safeguardmeasures', '1', '', '', '8', '1', '2014-12-25 14:52:39', null);
INSERT INTO `tbconsoleresource` VALUES ('100', '88', '风控类型/添加', '/cerifycatalogtype/save;/cerifycatalogtype/new', '2', '', '', null, '1', '2014-12-26 14:31:03', null);
INSERT INTO `tbconsoleresource` VALUES ('101', '88', '风控类型/编辑', '/cerifycatalogtype/update;/cerifycatalogtype/edit', '2', '', '', null, '1', '2014-12-26 14:31:48', null);
INSERT INTO `tbconsoleresource` VALUES ('102', '88', '风控类型/无效', '/cerifycatalogtype/delete', '2', '', '', null, '1', '2014-12-26 14:33:41', null);
INSERT INTO `tbconsoleresource` VALUES ('103', '89', '风控清单/添加', '/cerifycataloglist/save;/cerifycataloglist/new', '2', '', '', null, '1', '2014-12-26 14:37:40', null);
INSERT INTO `tbconsoleresource` VALUES ('104', '89', '风控清单/编辑', '/cerifycataloglist/update;/cerifycataloglist/edit', '2', '', '', null, '1', '2014-12-26 14:38:26', null);
INSERT INTO `tbconsoleresource` VALUES ('105', '89', '风控清单/无效', '/cerifycataloglist/delete', '2', '', '', null, '1', '2014-12-26 14:39:05', null);
INSERT INTO `tbconsoleresource` VALUES ('106', '90', '风控附件/添加', '/attachcataloglist/save;/attachcataloglist/new', '2', '', '', null, '1', '2014-12-26 14:40:22', null);
INSERT INTO `tbconsoleresource` VALUES ('107', '90', '风控附件/编辑', '/attachcataloglist/update;/attachcataloglist/edit', '2', '', '', null, '1', '2014-12-26 14:40:50', null);
INSERT INTO `tbconsoleresource` VALUES ('108', '90', '风控附件/无效', '/attachcataloglist/delete', '1', '', '', null, '1', '2014-12-26 14:41:17', null);
INSERT INTO `tbconsoleresource` VALUES ('109', '91', '保障措施/添加', '/safeguardmeasures/save;/safeguardmeasures/new;/uploadfile/filePathList', '2', '', '', null, '1', '2014-12-26 14:44:06', null);
INSERT INTO `tbconsoleresource` VALUES ('110', '91', '保障措施/编辑', '/safeguardmeasures/update;/safeguardmeasures/edit', '2', '', '', null, '1', '2014-12-26 14:44:32', null);
INSERT INTO `tbconsoleresource` VALUES ('111', '91', '保障措施/无效', '/safeguardmeasures/delete', '2', '', '', null, '1', '2014-12-26 14:45:13', null);
INSERT INTO `tbconsoleresource` VALUES ('115', '29', '项目风控', '/projectrisk', '1', '', '', '2', '1', '2015-01-06 11:13:16', null);
INSERT INTO `tbconsoleresource` VALUES ('117', '0', '前台管理', '/', '1', '', 'icon-home', '9', '1', '2015-01-06 18:38:06', null);
INSERT INTO `tbconsoleresource` VALUES ('118', '0', '消息管理', '/', '1', '', 'icon-envelope-alt', '10', '2', '2015-01-06 18:38:30', '2018-04-28 14:33:07');
INSERT INTO `tbconsoleresource` VALUES ('120', '116', '我要融资', '/financing', '1', '', '', null, '1', '2015-01-07 08:45:39', '2015-01-07 09:58:03');
INSERT INTO `tbconsoleresource` VALUES ('121', '120', '我要融资/详情', '/financing/details', '2', 'financing_details', '', null, '1', '2015-01-07 09:59:14', null);
INSERT INTO `tbconsoleresource` VALUES ('122', '120', '我要融资/处理', '/financing/update;/financing/edit', '2', '', '', null, '1', '2015-01-07 10:00:20', null);
INSERT INTO `tbconsoleresource` VALUES ('123', '117', '友情链接', '/friendlylink', '1', '', '', '4', '1', '2015-01-07 10:33:05', null);
INSERT INTO `tbconsoleresource` VALUES ('124', '123', '友情链接/添加', '/friendlylink/save;/friendlylink/new', '2', '', '', null, '1', '2015-01-07 10:49:04', null);
INSERT INTO `tbconsoleresource` VALUES ('125', '123', '友情链接/编辑', '/friendlylink/update;/friendlylink/find', '2', '', '', null, '1', '2015-01-07 10:50:07', null);
INSERT INTO `tbconsoleresource` VALUES ('126', '117', '系统公告', '/notice', '1', '', '', '2', '1', '2015-01-07 11:39:43', null);
INSERT INTO `tbconsoleresource` VALUES ('127', '87', '上传资料', '/uploadfile', '1', '', '', '9', '1', '2015-01-07 11:42:56', '2015-01-07 14:53:02');
INSERT INTO `tbconsoleresource` VALUES ('128', '117', '帮助类别', '/helpcategy', '1', '', '', '7', '1', '2015-01-07 14:54:24', '2015-01-08 10:19:41');
INSERT INTO `tbconsoleresource` VALUES ('129', '117', '帮助明细', '/helpdetail', '1', '', '', '8', '1', '2015-01-07 15:53:53', '2015-01-08 10:41:21');
INSERT INTO `tbconsoleresource` VALUES ('131', '126', '系统公告/添加', '/notice/save;/notice/new', '2', '', '', null, '1', '2015-01-08 10:17:58', null);
INSERT INTO `tbconsoleresource` VALUES ('132', '126', '系统公告/编辑', '/notice/update;/notice/find', '2', '', '', null, '1', '2015-01-08 10:18:59', null);
INSERT INTO `tbconsoleresource` VALUES ('133', '117', '焦点图', '/focus', '1', '', '', '1', '2', '2015-01-08 14:31:47', '2018-04-28 14:54:28');
INSERT INTO `tbconsoleresource` VALUES ('136', '118', '站内信', '/messagemanage', '1', '', '', null, '1', '2015-01-12 15:11:51', null);
INSERT INTO `tbconsoleresource` VALUES ('137', '118', '短信', '/smsmanage', '1', '', '', null, '1', '2015-01-12 15:12:39', null);
INSERT INTO `tbconsoleresource` VALUES ('138', '118', '邮件', '/emailmanage', '1', '', '', null, '1', '2015-01-12 15:12:59', null);
INSERT INTO `tbconsoleresource` VALUES ('139', '117', '新闻动态', '/news', '1', '', '', '3', '1', '2015-01-14 10:51:42', null);
INSERT INTO `tbconsoleresource` VALUES ('141', '117', '媒体报道', '/mediareports', '1', '', '', '6', '1', '2015-01-16 13:25:53', null);
INSERT INTO `tbconsoleresource` VALUES ('142', '128', '帮助类别/添加', '/helpcategy/save;/helpcategy/new', '2', '', '', null, '1', '2015-01-16 17:46:40', null);
INSERT INTO `tbconsoleresource` VALUES ('144', '129', '帮助明细/添加', '/helpdetail/save;/helpdetail/new', '2', '', '', null, '1', '2015-01-16 17:48:51', null);
INSERT INTO `tbconsoleresource` VALUES ('145', '128', '帮助类别/编辑', '/helpcategy/update;/helpcategy/find', '2', '', '', null, '1', '2015-01-16 17:50:18', null);
INSERT INTO `tbconsoleresource` VALUES ('146', '129', '帮助明细/编辑', '/helpdetail/update;/helpdetail/find', '2', '', '', null, '1', '2015-01-16 17:51:13', null);
INSERT INTO `tbconsoleresource` VALUES ('147', '129', '帮助明细/详情', '/helpdetail/detail', '2', '', '', null, '1', '2015-01-16 17:51:58', null);
INSERT INTO `tbconsoleresource` VALUES ('148', '133', '焦点图/添加', '/focus/save;/focus/new;/uploadfile/filePathList;/focus/getChannel', '2', '', '', null, '1', '2015-01-16 17:53:45', '2015-01-20 18:34:37');
INSERT INTO `tbconsoleresource` VALUES ('149', '133', '焦点图/编辑', '/focus/update;/focus/find;/uploadfile/filePathList;/focus/getChannel', '2', '', '', null, '1', '2015-01-16 17:54:34', '2015-01-20 18:34:56');
INSERT INTO `tbconsoleresource` VALUES ('150', '139', '新闻动态/添加', '/news/save;/news/new;/uploadfile/filePathList', '2', '', '', null, '1', '2015-01-16 17:56:34', null);
INSERT INTO `tbconsoleresource` VALUES ('151', '139', '新闻动态/编辑', '/news/update;/news/find;/uploadfile/filePathList', '2', '', '', null, '1', '2015-01-16 17:57:01', null);
INSERT INTO `tbconsoleresource` VALUES ('152', '139', '新闻动态/发布/撤销发布', '/news/publish;/news/doPublish;/news/undopublish', '2', '', '', null, '1', '2015-01-16 17:58:29', null);
INSERT INTO `tbconsoleresource` VALUES ('153', '139', '新闻动态/详情', '/news/detail', '2', '', '', null, '1', '2015-01-16 17:58:57', null);
INSERT INTO `tbconsoleresource` VALUES ('154', '140', '合作机构/添加', '/institution/save;/institution/new', '2', '', '', null, '1', '2015-01-16 18:00:16', null);
INSERT INTO `tbconsoleresource` VALUES ('155', '140', '合作机构/编辑', '/institution/update;/institution/find', '2', '', '', null, '1', '2015-01-16 18:00:40', null);
INSERT INTO `tbconsoleresource` VALUES ('156', '140', '合作机构/详情', '/institution/detail', '2', '', '', null, '1', '2015-01-16 18:01:22', null);
INSERT INTO `tbconsoleresource` VALUES ('157', '141', '媒体报道/添加', '/mediareports/save;/mediareports/new', '2', '', '', null, '1', '2015-01-16 18:02:32', null);
INSERT INTO `tbconsoleresource` VALUES ('158', '141', '媒体报道/编辑', '/mediareports/update;/mediareports/find', '2', '', '', null, '1', '2015-01-16 18:02:59', null);
INSERT INTO `tbconsoleresource` VALUES ('159', '141', '媒体报道/详情', '/mediareports/detail', '1', '', '', null, '1', '2015-01-16 18:03:24', null);
INSERT INTO `tbconsoleresource` VALUES ('160', '138', '邮件/添加', '/emailmanage/save;/emailmanage/new', '2', '', '', null, '1', '2015-01-16 18:04:20', null);
INSERT INTO `tbconsoleresource` VALUES ('161', '138', '邮件/编辑', '/emailmanage/update;/emailmanage/find', '2', '', '', null, '1', '2015-01-16 18:04:45', null);
INSERT INTO `tbconsoleresource` VALUES ('162', '138', '邮件/详情', '/emailmanage/detail', '2', '', '', null, '1', '2015-01-16 18:05:18', null);
INSERT INTO `tbconsoleresource` VALUES ('163', '138', '邮件/发布', '/emailmanage/publish;/emailmanage/doPublish', '1', '', '', null, '1', '2015-01-16 18:05:50', null);
INSERT INTO `tbconsoleresource` VALUES ('164', '127', '上传资料/添加', '/uploadfile/save;/uploadfile/new;/uploadfile/sync', '2', '', '', null, '1', '2015-01-16 18:08:35', '2015-01-28 17:00:00');
INSERT INTO `tbconsoleresource` VALUES ('165', '127', '上传资料/编辑', '/uploadfile/update;/uploadfile/find', '2', '', '', null, '1', '2015-01-16 18:09:13', null);
INSERT INTO `tbconsoleresource` VALUES ('166', '127', '上传资料/详情', '/uploadfile/detail', '2', '', '', null, '1', '2015-01-16 18:09:47', '2015-01-22 15:20:50');
INSERT INTO `tbconsoleresource` VALUES ('167', '137', '短信/添加', '/smsmanage/save;/smsmanage/new', '2', '', '', null, '1', '2015-01-16 18:12:10', null);
INSERT INTO `tbconsoleresource` VALUES ('168', '137', '短信/编辑', '/smsmanage/update;/smsmanage/find', '2', '', '', null, '1', '2015-01-16 18:12:37', null);
INSERT INTO `tbconsoleresource` VALUES ('169', '137', '短信/详情', '/smsmanage/detail', '2', '', '', null, '1', '2015-01-16 18:13:05', null);
INSERT INTO `tbconsoleresource` VALUES ('170', '137', '短信/删除', '/smsmanage/delete', '2', '', '', null, '1', '2015-01-16 18:13:33', null);
INSERT INTO `tbconsoleresource` VALUES ('171', '137', '短信/发布', '/smsmanage/publish;/smsmanage/doPublish', '2', '', '', null, '1', '2015-01-16 18:13:58', null);
INSERT INTO `tbconsoleresource` VALUES ('172', '136', '站内信/添加', '/messagemanage/save;/messagemanage/new', '2', '', '', null, '1', '2015-01-16 18:15:28', null);
INSERT INTO `tbconsoleresource` VALUES ('173', '136', '站内信/编辑', '/messagemanage/update;/messagemanage/find', '2', '', '', null, '1', '2015-01-16 18:15:52', null);
INSERT INTO `tbconsoleresource` VALUES ('174', '136', '站内信/删除', '/messagemanage/delete', '2', '', '', null, '1', '2015-01-16 18:16:26', null);
INSERT INTO `tbconsoleresource` VALUES ('175', '136', '站内信/详情', '/messagemanage/detail', '2', '', '', null, '1', '2015-01-16 18:16:52', null);
INSERT INTO `tbconsoleresource` VALUES ('176', '136', '站内信/发布', '/messagemanage/publish;/messagemanage/doPublish', '2', '', '', null, '1', '2015-01-16 18:17:16', null);
INSERT INTO `tbconsoleresource` VALUES ('178', '115', '项目风控/项目风控', '/projectrisk/risk;/projectrisk/detail;/projectrisk/select', '2', '', '', null, '1', '2015-01-19 19:06:40', '2015-01-20 11:48:12');
INSERT INTO `tbconsoleresource` VALUES ('179', '177', '项目产品/添加', '/projectcategy/save;/projectcategy/new', '2', '', '', null, '1', '2015-01-20 09:19:29', null);
INSERT INTO `tbconsoleresource` VALUES ('180', '177', '项目产品/编辑', '/projectcategy/update;/projectcategy/find', '2', '', '', null, '1', '2015-01-20 09:20:05', null);
INSERT INTO `tbconsoleresource` VALUES ('181', '115', '项目风控/风控信息提交', '/projectrisk/riskpersonal;/projectrisk/riskenterprise;/projectrisk/suggestions;/projectrisk/projectattach;/projectrisk/projectcatalog;/projectrisk/safeguardmeasures;/projectrisk/upload', '2', '', '', null, '1', '2015-01-20 11:33:45', null);
INSERT INTO `tbconsoleresource` VALUES ('182', '133', '焦点图列表/详情', '/uploadfile/filePathDetail;/upload', '2', '', '', null, '1', '2015-01-20 18:37:05', '2015-01-20 18:41:13');
INSERT INTO `tbconsoleresource` VALUES ('184', '65', '渠道列表/添加', '/channel/save;/channel/new', '2', '', '', null, '1', '2015-01-20 21:06:15', null);
INSERT INTO `tbconsoleresource` VALUES ('185', '65', '渠道列表/编辑', '/channel/update;/channel/find', '2', '', '', null, '1', '2015-01-20 21:06:40', null);
INSERT INTO `tbconsoleresource` VALUES ('186', '65', '渠道列表/删除', '/channel/delete', '2', '', '', null, '1', '2015-01-20 21:08:52', null);
INSERT INTO `tbconsoleresource` VALUES ('187', '47', '地区管理/详情', '/areainfo/detail', '2', '', '', null, '1', '2015-01-21 17:22:20', null);
INSERT INTO `tbconsoleresource` VALUES ('188', '9', '短信模板', '/smstemplate', '1', '', '', '5', '1', '2015-01-23 13:35:16', null);
INSERT INTO `tbconsoleresource` VALUES ('189', '188', '短信模板/编辑', '/smstemplate/update;/smstemplate/find', '2', '', '', null, '1', '2015-01-23 14:12:27', null);
INSERT INTO `tbconsoleresource` VALUES ('190', '188', '短信模板/详情', '/smstemplate/detail', '2', '', '', null, '1', '2015-01-23 14:13:00', null);
INSERT INTO `tbconsoleresource` VALUES ('192', '114', '借款人还款', '/financialrepay', '1', '借款人还款', '', '0', '1', '2015-02-03 12:48:56', '2015-02-04 22:03:17');
INSERT INTO `tbconsoleresource` VALUES ('193', '114', '借款人补还', '/financialmakeup', '1', '借款人补还', '', '1', '1', '2015-02-03 12:50:19', '2015-02-05 11:03:43');
INSERT INTO `tbconsoleresource` VALUES ('196', '192', '借款人还款添加', '/financialrepay/new', '2', '借款人还款', '', '0', '1', '2015-02-04 09:53:55', '2015-02-04 09:54:26');
INSERT INTO `tbconsoleresource` VALUES ('197', '193', '备用金补还款', '/financialmakeup/new', '2', '备用金补还款', '', '0', '1', '2015-02-04 09:55:11', null);
INSERT INTO `tbconsoleresource` VALUES ('198', '192', '借款人还款单审核页面', '/financialrepay/financialrepayAudit', '2', '借款人还款单审核页面', '', '2', '1', '2015-02-04 15:48:31', '2015-02-04 17:37:21');
INSERT INTO `tbconsoleresource` VALUES ('199', '192', '借款人还款单查看', '/financialrepay/detail', '2', '借款人还款单查看', '', '1', '1', '2015-02-04 15:54:48', '2015-02-06 11:02:18');
INSERT INTO `tbconsoleresource` VALUES ('200', '193', '补还单审核页面', '/financialmakeup/financialmakeupAudit', '2', '补还单审核页面', '', '2', '1', '2015-02-04 15:56:30', '2015-02-04 19:45:56');
INSERT INTO `tbconsoleresource` VALUES ('201', '193', '补还单查看', '/financialmakeup/detail', '2', '补还单查看', '', '1', '1', '2015-02-04 15:57:16', '2015-02-06 11:02:45');
INSERT INTO `tbconsoleresource` VALUES ('202', '192', '借款人还款单审核', '/financialrepay/audit', '2', '借款人还款单审核页面', '', '3', '1', '2015-02-04 17:35:37', '2015-02-04 17:37:41');
INSERT INTO `tbconsoleresource` VALUES ('203', '193', '补还单审核', '/financialmakeup/audit', '2', '补还单审核', '', '3', '1', '2015-02-04 17:36:47', '2015-02-04 17:38:11');
INSERT INTO `tbconsoleresource` VALUES ('204', '192', '借款人还款选择还款记录', '/financialrepay/repay', '2', '借款人还款选择还款记录', '', null, '1', '2015-02-06 10:59:56', null);
INSERT INTO `tbconsoleresource` VALUES ('205', '192', '借款人还款添加', '/financialrepay/create', '2', '借款人还款添加', '', null, '1', '2015-02-06 11:03:52', null);
INSERT INTO `tbconsoleresource` VALUES ('206', '192', '借款人还款保存', '/financialrepay/save', '2', '借款人还款保存', '', null, '1', '2015-02-06 11:04:43', '2015-02-06 11:06:36');
INSERT INTO `tbconsoleresource` VALUES ('207', '192', '借款人还款提交', '/financialrepay/submit', '2', '借款人还款提交', '', null, '1', '2015-02-06 11:09:11', null);
INSERT INTO `tbconsoleresource` VALUES ('208', '193', '借款人补还选择还款记录', '/financialmakeup/repay', '2', '借款人补还选择还款记录', '', null, '1', '2015-02-06 10:59:56', null);
INSERT INTO `tbconsoleresource` VALUES ('209', '193', '借款人补还添加', '/financialmakeup/create', '2', '借款人补还添加', '', null, '1', '2015-02-06 11:03:52', null);
INSERT INTO `tbconsoleresource` VALUES ('210', '193', '借款人补还保存', '/financialmakeup/save', '2', '借款人补还保存', '', null, '1', '2015-02-06 11:04:43', '2015-02-06 11:06:36');
INSERT INTO `tbconsoleresource` VALUES ('211', '193', '借款人补还提交', '/financialmakeup/submit', '2', '借款人补还提交', '', null, '1', '2015-02-06 11:09:11', null);
INSERT INTO `tbconsoleresource` VALUES ('212', '114', '借款人提现', '/financialcash', '1', '借款人提现', '', '2', '1', '2015-02-06 17:42:36', '2015-02-06 20:43:31');
INSERT INTO `tbconsoleresource` VALUES ('213', '212', '添加借款人提现申请', '/financialcash/new', '2', '添加借款人提现申请', '', null, '1', '2015-02-06 20:44:11', null);
INSERT INTO `tbconsoleresource` VALUES ('214', '212', '借款人提现确认', '/financialcash/create', '2', '借款人提现确认', '', null, '1', '2015-02-06 20:45:05', null);
INSERT INTO `tbconsoleresource` VALUES ('215', '212', '借款人提现保存', '/financialcash/save;/financialcash/getCashFee', '2', '借款人提现保存', '', null, '1', '2015-02-06 20:45:33', null);
INSERT INTO `tbconsoleresource` VALUES ('216', '212', '借款人提现单提交', '/financialcash/submit', '2', '借款人提现单提交', '', null, '1', '2015-02-06 20:46:11', null);
INSERT INTO `tbconsoleresource` VALUES ('217', '212', '借款人提现审核页面', '/financialcash/financialcashAudit', '2', '借款人提现审核页面', '', null, '1', '2015-02-06 20:46:47', '2015-02-06 20:47:53');
INSERT INTO `tbconsoleresource` VALUES ('218', '212', '借款人提现审核', '/financialcash/audit', '2', '借款人提现审核', '', null, '1', '2015-02-06 20:47:22', null);
INSERT INTO `tbconsoleresource` VALUES ('219', '192', '删除借款单', '/financialrepay/delete', '2', '删除借款单', '', null, '1', '2015-02-06 20:53:52', null);
INSERT INTO `tbconsoleresource` VALUES ('220', '193', '补还单删除', '/financialmakeup/delete', '2', '补还单删除', '', null, '1', '2015-02-06 20:54:27', null);
INSERT INTO `tbconsoleresource` VALUES ('221', '212', '借款人提款申请删除', '/financialcash/delete', '2', '借款人提款申请删除', '', null, '1', '2015-02-06 20:55:30', null);
INSERT INTO `tbconsoleresource` VALUES ('222', '212', '借款人提现查看', '/financialcash/detail', '2', '借款人提现查看', '', null, '1', '2015-02-07 16:57:18', null);
INSERT INTO `tbconsoleresource` VALUES ('223', '114', '平台账户余额', '/financialbalance', '1', '平台账户余额', null, '3', '1', '2015-02-09 10:02:44', null);
INSERT INTO `tbconsoleresource` VALUES ('224', '114', '平台财务流水', '/financialrecord', '1', '平台财务流水', null, '4', '1', '2015-02-09 10:02:44', null);
INSERT INTO `tbconsoleresource` VALUES ('225', '114', '投资人退款申请', '/financialcashfailure', '1', '提现失败申请', '', '6', '1', '2015-02-06 17:42:36', '2015-02-06 20:43:31');
INSERT INTO `tbconsoleresource` VALUES ('226', '225', '添加投资人退款申请', '/financialcashfailure/new;/financialcashfailure/create', '2', '添加提现失败申请', '', null, '1', '2015-02-06 20:44:11', null);
INSERT INTO `tbconsoleresource` VALUES ('227', '225', '投资人退款申请保存', '/financialcashfailure/save', '2', '提现失败申请保存', '', null, '1', '2015-02-06 20:45:33', null);
INSERT INTO `tbconsoleresource` VALUES ('228', '225', '投资人退款申请单提交', '/financialcashfailure/submit', '2', '提现失败申请单提交', '', null, '1', '2015-02-06 20:46:11', null);
INSERT INTO `tbconsoleresource` VALUES ('229', '225', '投资人退款申请审核页面', '/financialcashfailure/financialcashfailureAudit', '2', '提现失败申请审核页面', '', null, '1', '2015-02-06 20:46:47', '2015-02-06 20:47:53');
INSERT INTO `tbconsoleresource` VALUES ('230', '225', '投资人退款申请审核', '/financialcashfailure/audit', '2', '提现失败申请审核', '', null, '1', '2015-02-06 20:47:22', null);
INSERT INTO `tbconsoleresource` VALUES ('231', '114', '投资人提现申请', '/cash', '1', '', '', '5', '1', '2015-03-03 17:04:45', '2015-03-03 17:06:32');
INSERT INTO `tbconsoleresource` VALUES ('232', '231', '投资人提现申请/下载', '/cash/downcash', '2', 'cash.downloadfile', '', null, '1', '2015-03-03 17:49:32', null);
INSERT INTO `tbconsoleresource` VALUES ('233', '225', '投资人退款申请查看', '/financialcashfailure/detail', '2', null, null, null, '1', '2015-03-05 11:37:24', null);
INSERT INTO `tbconsoleresource` VALUES ('234', '225', '投资人退款申请删除', '/financialcashfailure/delete', '2', null, null, null, '1', '2015-03-05 11:43:15', null);
INSERT INTO `tbconsoleresource` VALUES ('238', '119', '推广统计', '/report001', '1', '推广统计', '', '0', '1', '2015-03-30 17:12:03', '2015-04-01 14:57:30');
INSERT INTO `tbconsoleresource` VALUES ('239', '119', '用户统计', '/report002', '1', '用户统计', '', '1', '1', '2015-03-30 17:13:49', '2015-04-01 14:58:18');
INSERT INTO `tbconsoleresource` VALUES ('242', '238', '列表查看下载', '/report001/view;/report001/download/report001', '2', '列表查看下载', '', '0', '1', '2015-04-01 14:58:04', '2015-04-03 16:01:00');
INSERT INTO `tbconsoleresource` VALUES ('243', '239', '列表查看下载', '/report002/view;/report002/download/report002', '2', '列表查看下载', '', '0', '1', '2015-04-01 14:58:37', '2015-04-03 16:00:56');
INSERT INTO `tbconsoleresource` VALUES ('244', '119', '安卓统计', '/report003', '1', 'APP统计', '', '2', '1', '2015-04-14 10:42:07', '2015-04-15 18:48:15');
INSERT INTO `tbconsoleresource` VALUES ('245', '244', '安卓统计列表查看下载', '/report003/view;/report003/download/report003', '2', 'APP统计列表查看下载', '', null, '1', '2015-04-14 10:44:12', '2015-04-15 18:48:12');
INSERT INTO `tbconsoleresource` VALUES ('246', '8', '冻结解冻用户列表', '/loginaccountaudit', '1', '冻结解冻用户', '', '5', '1', '2015-04-15 10:57:33', '2015-04-20 18:09:14');
INSERT INTO `tbconsoleresource` VALUES ('247', '246', '添加冻结解冻用户', '/loginaccountaudit/toFrozen;/loginaccountaudit/save;/loginaccountaudit/delete;/loginaccountaudit/view', '2', '添加冻结解冻用户', '', null, '1', '2015-04-15 11:03:55', '2015-04-20 18:09:12');
INSERT INTO `tbconsoleresource` VALUES ('248', '246', '提交冻结解冻审核', '/loginaccountaudit/submitApproval;/loginaccountaudit/toApproval;/loginaccountaudit/approval', '2', '提交冻结解冻审核', '', null, '1', '2015-04-15 11:11:28', '2015-04-20 18:09:10');
INSERT INTO `tbconsoleresource` VALUES ('250', '119', '投资统计', '/report004', '1', '投资统计', '', '3', '1', '2015-04-21 16:05:59', '2015-04-21 16:06:14');
INSERT INTO `tbconsoleresource` VALUES ('251', '250', '下载', '/report004/download/report004', '2', '下载', '', null, '1', '2015-04-21 17:22:57', '2015-04-22 01:46:59');
INSERT INTO `tbconsoleresource` VALUES ('252', '117', '注册提示语', '/registemessage', '1', 'registemessage', null, null, '1', '2015-05-13 09:13:49', null);
INSERT INTO `tbconsoleresource` VALUES ('253', '252', '注册提示语/编辑', '/registemessage/update;/registemessage/find', '2', 'registemessage_find', null, null, '1', '2015-05-13 09:09:24', null);
INSERT INTO `tbconsoleresource` VALUES ('254', '252', '注册提示语/添加', '/registemessage/save;/registemessage/new', '2', 'registemessage_new', null, null, '1', '2015-05-13 09:09:24', null);
INSERT INTO `tbconsoleresource` VALUES ('257', '231', '投资人提现申请/小额提现', '/cash/downcash;/cash', '2', '小额提现', '', null, '1', '2015-05-14 16:36:30', '2015-05-14 16:41:49');
INSERT INTO `tbconsoleresource` VALUES ('258', '119', 'IOS统计', '/report005', '1', 'IOS统计', '', '4', '1', '2015-05-20 14:40:54', '2015-05-20 15:20:37');
INSERT INTO `tbconsoleresource` VALUES ('259', '258', 'IOS统计列表查看', '/report005/view', '2', 'IOS统计', '', null, '1', '2015-05-20 15:31:45', null);
INSERT INTO `tbconsoleresource` VALUES ('260', '258', 'IOS统计列表下载', '/report005/download/report005', '2', 'IOS统计列表查看下载', '', null, '1', '2015-05-20 15:33:13', null);
INSERT INTO `tbconsoleresource` VALUES ('262', '261', '推广渠道管理/添加', '/promotechannel/save;/promotechannel/new', '2', '', '', null, '1', '2015-05-22 14:20:16', '2015-05-22 14:20:56');
INSERT INTO `tbconsoleresource` VALUES ('263', '261', '推广渠道管理/修改', '/promotechannel/update;/promotechannel/find', '2', '', '', null, '1', '2015-05-26 17:58:08', '2015-05-27 17:48:40');
INSERT INTO `tbconsoleresource` VALUES ('264', '261', '推广渠道管理/删除', '/promotechannel/delete', '2', '', '', null, '1', '2015-05-26 17:59:38', '2015-05-26 18:10:49');
INSERT INTO `tbconsoleresource` VALUES ('265', '84', '活动申请', '/awardsapply', '1', '', '', '11', '1', '2015-05-29 10:44:58', null);
INSERT INTO `tbconsoleresource` VALUES ('266', '84', '活动发放', '/awardsgrant', '1', '', '', '13', '1', '2015-05-29 16:12:54', '2015-06-10 20:25:58');
INSERT INTO `tbconsoleresource` VALUES ('267', '84', '活动发布', '/awardspublish', '1', '', '', '12', '1', '2015-06-02 11:21:51', null);
INSERT INTO `tbconsoleresource` VALUES ('268', '84', '红包使用统计', '/awardsused', '1', '', '', '14', '1', '2015-06-02 11:29:35', '2015-06-02 20:35:56');
INSERT INTO `tbconsoleresource` VALUES ('269', '266', '活动发放/定向发放', '/awardsgrant/directedred;/awardsgrant/directedredDown;/awardsgrant/directedredUpload;', '2', '', '', null, '1', '2015-06-02 20:29:45', '2015-06-10 20:26:33');
INSERT INTO `tbconsoleresource` VALUES ('270', '266', '活动发放/发放详情', '/awardsgrant/grantdetails', '2', '', '', null, '1', '2015-06-02 20:31:21', '2015-06-10 20:26:47');
INSERT INTO `tbconsoleresource` VALUES ('271', '268', '红包使用详情', '/awardsused/uselist', '2', '', '', null, '1', '2015-06-02 20:36:36', null);
INSERT INTO `tbconsoleresource` VALUES ('272', '265', '活动申请/修改', '/awardsapply/update;/awardsapply/find;/awardsapply/longtermupdate;/awardsapply/autoupdate;/awardsapply/exchangeupdate;/awardsapply/artificialupdate', '2', '', '', null, '1', '2015-06-08 10:48:13', null);
INSERT INTO `tbconsoleresource` VALUES ('273', '265', '活动申请/添加', '/awardsapply/save;/awardsapply/new;/awardsapply/redirect;/awardsapply/longterm;/awardsapply/auto;/awardsapply/exchange;/awardsapply/artificial', '2', '', '', null, '1', '2015-06-08 10:52:09', null);
INSERT INTO `tbconsoleresource` VALUES ('274', '267', '活动发布/发布', '/awardspublish/publish', '2', '', '', null, '1', '2015-06-08 10:54:40', null);
INSERT INTO `tbconsoleresource` VALUES ('275', '265', '活动发布/导出兑换码', '/awardspublish/exportCDKey', '2', '', '', null, '1', '2015-06-08 10:55:57', null);
INSERT INTO `tbconsoleresource` VALUES ('277', '265', '活动申请/查看', '/awardsapply/detail', '2', '', '', null, '1', '2015-06-08 11:13:55', null);
INSERT INTO `tbconsoleresource` VALUES ('278', '265', '红包追加', '/awardsapply/awardsapplyAppend;/awardsapply/append', '2', null, null, null, '1', null, null);
INSERT INTO `tbconsoleresource` VALUES ('279', '267', '活动发布/查看', '/awardspublish/find', '2', '', '', null, '1', '2015-06-08 11:18:09', null);
INSERT INTO `tbconsoleresource` VALUES ('281', '265', '活动申请/审核', '/awardsapply/awardsapplyAudit;/awardsapply/audit', '2', '', '', null, '1', '2015-06-08 15:55:05', null);
INSERT INTO `tbconsoleresource` VALUES ('282', '265', '活动追加修改', '/awardsapply/awardsapplyAppendEdit;/awardsapply/appendUpdate', '2', null, null, null, '1', null, null);
INSERT INTO `tbconsoleresource` VALUES ('283', '266', '活动发放/失败补发', '/awardsgrant/failSend', '2', '', '', null, '1', '2015-06-10 20:24:46', '2015-06-10 20:26:59');
INSERT INTO `tbconsoleresource` VALUES ('285', '284', '充值流水', '/report009', '1', null, null, '1', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('286', '284', '投资流水', '/report010', '1', null, null, '2', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('287', '284', '提现申请', '/report011', '1', null, null, '3', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('288', '284', '退款申请', '/report012', '1', null, null, '4', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('289', '284', '借款标的', '/report013', '1', null, null, '5', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('290', '284', '标的还款', '/report014', '1', null, null, '6', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('291', '284', '红包统计', '/report006', '1', null, null, '7', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('292', '284', '投资人', '/report015', '1', null, null, '8', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('293', '284', '借款人', '/report016', '1', null, null, '9', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('294', '285', '充值流水/下载', '/report009/download/report009', '2', '下载', null, '1', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('295', '286', '投资流水/下载', '/report010/download/report010', '2', '下载', null, '1', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('296', '287', '提现申请/下载', '/report011/download/report011', '2', '下载', null, '1', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('297', '288', '退款申请/下载', '/report012/download/report012', '2', '下载', null, '1', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('298', '289', '借款标的/下载', '/report013/download/report013', '2', '下载', null, '1', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('299', '290', '标的还款/下载', '/report014/download/report014', '2', '下载', null, '1', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('300', '291', '红包统计/下载', '/report006/download/report006', '2', '下载', null, '1', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('301', '292', '投资人/下载', '/report015/download/report015', '2', '下载', null, '1', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('302', '293', '借款人/下载', '/report016/download/report016', '2', '下载', null, '1', '1', '2015-07-13 18:02:25', null);
INSERT INTO `tbconsoleresource` VALUES ('303', '87', '短信白名单', '/smswhitelist', '1', '', '', '1', '1', '2015-07-23 14:10:14', '2018-04-27 00:51:05');
INSERT INTO `tbconsoleresource` VALUES ('304', '304', '短信白名单/添加', '/smswhitelist/new;/smswhitelist/save', '2', '', '', null, '1', '2015-07-23 14:10:14', '2015-07-13 17:00:17');
INSERT INTO `tbconsoleresource` VALUES ('305', '87', '短信列表', '/smsrecord', '1', '', '', '3', '1', '2015-07-23 14:10:14', '2018-04-27 00:51:33');
INSERT INTO `tbconsoleresource` VALUES ('314', '8', '投资人', '/investor', '1', '', '', null, '1', '2015-07-30 18:44:58', null);
INSERT INTO `tbconsoleresource` VALUES ('317', '315', '认证未充值', '/report018', '1', '', '', '1', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('318', '315', '充值未投资', '/report019', '1', '', '', '2', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('319', '315', '提现客户', '/report020', '1', '', '', '3', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('321', '8', '投资人手机号码更换列表', '/changephoneaudit', '1', '投资人手机号码更换', '', '6', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('322', '314', '账户信息', '/investor/accountInfo', '2', '', '', '1', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('323', '314', '投资记录', '/investor/investrecord', '2', '', '', '2', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('325', '314', '还款记录', '/investor/investRepayRecord', '2', '', '', '3', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('326', '314', '资金记录', '/investor/cashRecord', '2', '', '', '4', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('327', '314', '红包记录', '/investor/redRecord', '2', '', '', '5', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('329', '8', '投资人身份认证列表', '/identityaudit', '1', '身份认证', '', '7', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('334', '8', '投资人提现金额转移列表', '/cashtransferaudit', '1', '', '投资人提现金额转移', '8', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('335', '8', '投资人绑卡删除列表', '/carddeleteaudit', '1', '投资人绑卡删除', '', '9', '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('336', '321', '添加手机号码更换用户', '/changephoneaudit/toChange;/changephoneaudit/save;/changephoneaudit/delete;/changephoneaudit/view', '2', '添加手机号码更换用户', '', null, '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('337', '321', '提交手机号码更换审核', '/changephoneaudit/submitApproval;/changephoneaudit/toApproval;/changephoneaudit/approval', '2', '提交手机号码更换审核', '', null, '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('338', '329', '添加身份认证用户', '/identityaudit/toIdentity;/identityaudit/save;/identityaudit/delete;/identityaudit/view', '2', '添加身份认证用户', '', null, '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('339', '329', '提交身份认证审核', '/identityaudit/submitApproval;/identityaudit/toApproval;/identityaudit/approval', '2', '提交身份认证审核', '', null, '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('340', '334', '添加提现金额转移用户', '/cashtransferaudit/toCashTransfer;/cashtransferaudit/save;/cashtransferaudit/delete;/cashtransferaudit/view', '2', '添加提现金额转移用户', '', null, '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('341', '334', '提交提现金额转移审核', '/cashtransferaudit/submitApproval;/cashtransferaudit/toApproval;/cashtransferaudit/approval', '2', '提交提现金额转移审核', '', null, '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('342', '335', '添加绑卡删除用户', '/carddeleteaudit/toCardDelete;/carddeleteaudit/save;/carddeleteaudit/delete;/carddeleteaudit/view', '2', '添加绑卡删除用户', '', null, '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('343', '335', '提交绑卡删除审核', '/carddeleteaudit/submitApproval;/carddeleteaudit/toApproval;/carddeleteaudit/approval', '2', '提交绑卡删除审核', '', null, '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('347', '345', '新增体验标', '/experiencesubject', '1', '', '', null, '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('348', '317', '认证未充值/添加', '/customservicetrack/update;/customservicetrack/find018', '2', '', '', null, '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('351', '318', '充值未投资/添加', '/customservicetrack/update;/customservicetrack/find019', '2', '', '', null, '1', '2015-07-30 18:45:08', null);
INSERT INTO `tbconsoleresource` VALUES ('354', '319', '提现客户/添加', '/customservicetrack/update;/customservicetrack/find020', '2', '', '', null, '1', '2015-07-30 18:45:09', null);
INSERT INTO `tbconsoleresource` VALUES ('357', '314', '添加冻结解冻用户', '/loginaccountaudit/toFrozen', '2', '添加冻结解冻用户', '', null, '1', '2015-07-30 18:45:09', null);
INSERT INTO `tbconsoleresource` VALUES ('358', '314', '添加手机号码更换用户', '/changephoneaudit/toChange', '2', '添加手机号码更换用户', '', null, '1', '2015-07-30 18:45:09', null);
INSERT INTO `tbconsoleresource` VALUES ('359', '314', '添加身份认证用户', '/identityaudit/toIdentity', '2', '添加身份认证用户', '', null, '1', '2015-07-30 18:45:09', null);
INSERT INTO `tbconsoleresource` VALUES ('360', '314', '添加提现金额转移用户', '/cashtransferaudit/toCashTransfer', '2', '添加提现金额转移用户', '', null, '1', '2015-07-30 18:45:09', null);
INSERT INTO `tbconsoleresource` VALUES ('361', '314', '添加绑卡删除用户', '/carddeleteaudit/toCardDelete', '2', '添加绑卡删除用户', '', null, '1', '2015-07-30 18:45:09', null);
INSERT INTO `tbconsoleresource` VALUES ('371', '117', 'App欢迎页', '/appwelcomeimage', '1', '', '', '1', '2', '2015-08-14 11:12:58', '2018-04-28 14:54:15');
INSERT INTO `tbconsoleresource` VALUES ('372', '347', '新增体验标', '/experiencesubject/new', '2', '', '', null, '1', '2015-08-07 13:46:47', null);
INSERT INTO `tbconsoleresource` VALUES ('373', '347', '体验标详情', '/experiencesubject/getById', '2', '', '', null, '1', '2015-08-07 13:47:31', null);
INSERT INTO `tbconsoleresource` VALUES ('374', '347', '体验标提交审核', '/experiencesubject/submitApproval', '2', '', '', null, '1', '2015-08-07 13:48:49', null);
INSERT INTO `tbconsoleresource` VALUES ('375', '347', '体验标审核跳转', '/experiencesubject/toApproval', '2', '', '', null, '1', '2015-08-07 13:49:24', null);
INSERT INTO `tbconsoleresource` VALUES ('376', '347', '体验标审核', '/experiencesubject/approval;/experiencesubject/toApproval', '2', '', '', null, '1', '2015-08-07 13:49:52', null);
INSERT INTO `tbconsoleresource` VALUES ('377', '347', '新增表的保存', '/experiencesubject/save', '2', '', '', null, '1', '2015-08-07 14:08:27', '2015-08-07 14:41:14');
INSERT INTO `tbconsoleresource` VALUES ('378', '347', '体验标的编辑', '/experiencesubject/update;/experiencesubject/find', '2', '', '', null, '1', '2015-08-07 14:51:04', null);
INSERT INTO `tbconsoleresource` VALUES ('379', '347', '体验标的删除', '/experiencesubject/delete', '2', '', '', null, '1', '2015-08-07 14:51:42', null);
INSERT INTO `tbconsoleresource` VALUES ('380', '371', 'APP欢迎页新增', '/appwelcomeimage/new;/appwelcomeimage/save', '2', 'APP欢迎页新增', '', null, '1', '2015-08-17 10:29:02', '2015-08-17 10:32:32');
INSERT INTO `tbconsoleresource` VALUES ('381', '371', 'APP欢迎页修改', '/appwelcomeimage/find;/appwelcomeimage/update', '2', 'APP欢迎页修改', '', null, '1', '2015-08-17 10:29:56', null);
INSERT INTO `tbconsoleresource` VALUES ('383', '382', '支付渠道配置', '/bankpaymanage', '1', '', '', '1', '1', '2015-07-14 10:56:08', '2015-07-14 11:57:09');
INSERT INTO `tbconsoleresource` VALUES ('384', '382', '提现渠道配置', '/bankcashmanage', '1', '', '', '2', '1', '2015-07-14 10:58:37', '2015-07-15 15:07:44');
INSERT INTO `tbconsoleresource` VALUES ('385', '382', '提现审核', '/cashapprove/list', '1', '', '', '4', '1', '2015-07-22 18:04:49', '2015-07-24 13:52:33');
INSERT INTO `tbconsoleresource` VALUES ('386', '382', '提现放款', '/cashloan/list', '1', '', '', '5', '1', '2015-07-23 17:38:38', '2015-07-24 13:52:48');
INSERT INTO `tbconsoleresource` VALUES ('387', '382', '提现结果查询', '/cashsearch/list', '1', '', '', '6', '1', '2015-07-24 10:26:04', '2015-07-24 13:52:57');
INSERT INTO `tbconsoleresource` VALUES ('388', '382', '充值结果查询', '/rechargesearch/list', '1', '', '', '3', '1', '2015-07-24 13:53:49', null);
INSERT INTO `tbconsoleresource` VALUES ('389', '383', '编辑', '/bankpaymanage/find;/bankpaymanage/update', '2', '', '', null, '1', '2015-07-15 14:39:00', '2015-07-15 14:41:10');
INSERT INTO `tbconsoleresource` VALUES ('390', '383', '开通/关闭', '/bankpaymanage/changeIstate', '2', '', '', null, '1', '2015-07-15 14:41:52', null);
INSERT INTO `tbconsoleresource` VALUES ('391', '384', '编辑', '/bankcashmanage/find;/bankcashmanage/update', '2', '', '', null, '1', '2015-07-15 16:24:54', null);
INSERT INTO `tbconsoleresource` VALUES ('392', '386', '选择项放款', '/cashloan/loan', '2', '', '', null, '1', null, '2015-08-31 19:18:26');
INSERT INTO `tbconsoleresource` VALUES ('393', '385', '一键审核', '/cashapprove/approve', '2', null, null, null, '1', null, null);
INSERT INTO `tbconsoleresource` VALUES ('395', '394', '平台账户', '/reportplatformaccount', '1', null, null, '1', '1', '2015-08-28 00:07:03', null);
INSERT INTO `tbconsoleresource` VALUES ('396', '395', '平台账户/充值', '/reportplatformaccount/recharge;/companyfinancialaudit/rechargeSubmit', '2', '充值', null, '1', '1', '2015-08-28 00:07:03', null);
INSERT INTO `tbconsoleresource` VALUES ('397', '395', '平台账户/提现', '/reportplatformaccount/withdrawcash;/companyfinancialaudit/withdrawcashSubmit', '2', '提现', null, '2', '1', '2015-08-28 00:07:03', null);
INSERT INTO `tbconsoleresource` VALUES ('398', '394', '备用金账户', '/reportreserveaccount', '1', null, null, '2', '1', '2015-08-28 00:07:03', null);
INSERT INTO `tbconsoleresource` VALUES ('399', '398', '备用金账户/充值', '/reportreserveaccount/recharge;/companyfinancialaudit/rechargeSubmit ', '2', '充值', null, '1', '1', '2015-08-28 00:07:03', null);
INSERT INTO `tbconsoleresource` VALUES ('400', '398', '备用金账户/提现', '/reportreserveaccount/withdrawcash;/companyfinancialaudit/withdrawcashSubmit', '2', '提现', null, '2', '1', '2015-08-28 00:07:03', null);
INSERT INTO `tbconsoleresource` VALUES ('401', '394', '投资人账户', '/reportinvestaccount', '1', null, null, '3', '1', '2015-08-28 00:07:03', null);
INSERT INTO `tbconsoleresource` VALUES ('402', '394', '借款人账户', '/reportborroweraccount', '1', null, null, '4', '1', '2015-08-28 00:07:03', null);
INSERT INTO `tbconsoleresource` VALUES ('403', '394', '充值提现申请记录', '/companyfinancialaudit', '1', null, null, '5', '1', '2015-08-28 00:07:03', null);
INSERT INTO `tbconsoleresource` VALUES ('404', '403', '充值提现申请/审批', '/companyfinancialaudit/audit;/companyfinancialauditflow/addFlow', '2', '审批', null, '1', '1', '2015-08-28 00:07:04', null);
INSERT INTO `tbconsoleresource` VALUES ('405', '403', '充值提现申请/查看', '/companyfinancialaudit/find', '2', '查看', null, '2', '1', '2015-08-28 00:07:04', null);
INSERT INTO `tbconsoleresource` VALUES ('406', '403', '充值提现申请/提交', '/companyfinancialaudit/submitAudit', '2', '提交', null, '3', '1', '2015-08-28 00:07:04', null);
INSERT INTO `tbconsoleresource` VALUES ('415', '386', '一键放款', '/cashloan/loanall', '2', '', '', '1', '1', '2015-08-31 19:19:08', null);
INSERT INTO `tbconsoleresource` VALUES ('416', '386', '下载', '/cashloan/download/cashloan', '2', '', '', '2', '1', '2015-08-31 19:19:38', null);
INSERT INTO `tbconsoleresource` VALUES ('417', '385', '下载', '/cashapprove/download/cashapprove', '2', '', '', '1', '1', '2015-08-31 19:20:25', null);
INSERT INTO `tbconsoleresource` VALUES ('419', '418', '每日概况', '/fixedbaodialyoverview', '1', '', '', '1', '1', '2015-08-24 09:55:39', '2015-09-20 16:57:27');
INSERT INTO `tbconsoleresource` VALUES ('420', '418', '标的管理', '/fixedbaosubject', '1', '', '', '2', '1', '2015-08-24 09:57:14', '2015-09-20 16:57:38');
INSERT INTO `tbconsoleresource` VALUES ('422', '418', '平台结算', '/fixedbaodialyoverview/fixedbaodialyoverviewReport', '1', '', '', '3', '1', '2015-08-24 10:00:07', '2015-09-20 16:57:55');
INSERT INTO `tbconsoleresource` VALUES ('423', '418', '到期还款', '/fixedbaorepayrecord', '1', '', '', '4', '1', '2015-08-24 10:00:24', '2015-09-20 16:58:16');
INSERT INTO `tbconsoleresource` VALUES ('424', '418', '申购记录', '/fixedbaoinvestrecord', '1', '', '', '5', '1', '2015-08-24 10:00:39', '2015-09-20 16:58:25');
INSERT INTO `tbconsoleresource` VALUES ('425', '418', '定期宝设置', '/fixedbaoproject', '1', '', '', '6', '1', '2015-08-24 10:00:56', '2015-09-20 16:58:34');
INSERT INTO `tbconsoleresource` VALUES ('427', '425', '定期宝设置修改保存', '/fixedbaoproject/find;/fixedbaoproject/update;', '2', '', '', null, '1', '2015-08-25 17:31:15', null);
INSERT INTO `tbconsoleresource` VALUES ('428', '425', '定期宝清算开始时间', '/fixedbaoproject/changeTime', '2', '', '', null, '1', '2015-08-25 17:32:00', null);
INSERT INTO `tbconsoleresource` VALUES ('432', '419', '每日概况详情', '/fixedbaoprojectdetail', '2', '', '', null, '1', '2015-08-31 16:27:49', null);
INSERT INTO `tbconsoleresource` VALUES ('433', '419', '每日概况标的详情', '/fixedbaosubject/getById', '2', '', '', null, '1', '2015-08-31 16:28:22', null);
INSERT INTO `tbconsoleresource` VALUES ('434', '117', '社区动态', '/community', '1', '', '', null, '2', '2015-09-01 10:45:19', '2018-04-28 14:53:59');
INSERT INTO `tbconsoleresource` VALUES ('435', '434', '社区动态/修改', '/community/find', '2', '', '', null, '1', '2015-09-01 10:50:24', '2015-09-01 10:51:00');
INSERT INTO `tbconsoleresource` VALUES ('436', '434', '社区动态/删除', '/community/delete', '2', '', '', null, '1', '2015-09-01 10:52:14', null);
INSERT INTO `tbconsoleresource` VALUES ('437', '434', '社区动态/置顶', '/community/updateState;/community/update', '2', '', '', null, '1', '2015-09-01 10:52:43', null);
INSERT INTO `tbconsoleresource` VALUES ('438', '434', '社区动态/添加', '/community/new;/community/save', '2', '', '', null, '1', '2015-09-01 10:53:10', null);
INSERT INTO `tbconsoleresource` VALUES ('439', '387', '下载', '/cashsearch/download/cashsearch', '2', '', '', '0', '1', '2015-09-02 11:56:58', null);
INSERT INTO `tbconsoleresource` VALUES ('446', '420', '定期宝新增', '/fixedbaosubject/new;/fixedbaosubject/bankCollectionSave;/fixedbaosubject/fixedClaimSave;/fixedbaosubject/thirdPartyDiscountSave;/fixedbaosubject/typeChange', '2', '', '', null, '1', '2015-09-06 11:17:55', '2015-09-06 11:28:36');
INSERT INTO `tbconsoleresource` VALUES ('447', '420', '定期宝修改', '/fixedbaosubject/find;/fixedbaosubject/thirdPartyDiscountUpdate;/fixedbaosubject/thirdPartyDiscountUpdate;/fixedbaosubject/fixedClaimUpdate', '2', '', '', null, '1', '2015-09-06 11:19:26', '2015-09-06 11:27:42');
INSERT INTO `tbconsoleresource` VALUES ('448', '420', '定期宝银行规模调整', '/fixedbaosubject/reset;/fixedbaosubject/resetUpdate', '2', '', '', null, '1', '2015-09-06 11:21:03', null);
INSERT INTO `tbconsoleresource` VALUES ('449', '420', '定期宝详情；删除;提交;审核；发验证码', '/fixedbaosubject/getById;/fixedbaosubject/delete;/fixedbaosubject/submitApproval;/fixedbaosubject/toApproval;/fixedbaosubject/approval;dynamicValidateCode/fixedbaosubjectAudit/send', '2', '', '', null, '1', '2015-09-06 11:21:49', '2015-09-08 15:35:59');
INSERT INTO `tbconsoleresource` VALUES ('450', '119', 'PC统计', '/reportPromotePC', '1', '', '', '5', '1', '2015-09-07 15:40:09', '2015-09-07 15:43:13');
INSERT INTO `tbconsoleresource` VALUES ('451', '450', '下载', '/reportPromotePC/download/reportPromotePC', '2', '', '', '0', '1', '2015-09-07 15:41:05', null);
INSERT INTO `tbconsoleresource` VALUES ('452', '119', 'WAP统计', '/reportPromoteWap', '1', '', '', '6', '1', '2015-09-07 15:44:08', null);
INSERT INTO `tbconsoleresource` VALUES ('453', '452', '下载', '/reportPromoteWap/download/reportPromoteWap', '2', '', '', '0', '1', '2015-09-07 15:44:55', null);
INSERT INTO `tbconsoleresource` VALUES ('463', '462', '活动列表', '/active', '1', '', '', null, '1', '2015-09-18 10:11:18', null);
INSERT INTO `tbconsoleresource` VALUES ('465', '463', '中奖人名单，中奖人导出', '/activerewardrecord;/active/download/downActive;', '1', '', '', null, '1', '2015-09-18 17:11:50', null);
INSERT INTO `tbconsoleresource` VALUES ('470', '284', '投资公司', '/investcompanyfinancialrecord', '1', '', '', '10', '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('471', '470', '投资公司/下载', '/investcompanyfinancialrecord/download/investcompanyfinancialrecord', '2', '下载', '', '1', '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('472', '114', '投资公司充值', '/investcompanyrechargeaudit', '1', '', '', '7', '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('473', '114', '投资公司提现', '/investcompanywithdrawalsaudit', '1', '', '', '8', '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('474', '472', '投资公司充值', '/investcompanyrechargeaudit/recharge;/investcompanyrechargeaudit/rechargeSubmit', '2', '充值', '', '0', '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('475', '472', '投资公司充值审核/提交', '/investcompanyrechargeaudit/submitAudit', '2', '提交', '', '0', '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('476', '472', '投资公司充值审核/删除', '/investcompanyrechargeaudit/delete', '2', '删除', '', '0', '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('477', '472', '投资公司充值审核/审核', '/investcompanyrechargeaudit/toApproval;/investcompanyrechargeaudit/approval/approval', '2', '审核', '', '0', '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('478', '472', '投资公司充值审核/查看', '/investcompanyrechargeaudit/find', '2', '查看', '', '0', '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('479', '473', '投资公司提现', '/investcompanywithdrawalsaudit/withdrawcash;/investcompanywithdrawalsaudit/withdrawcashSubmit', '2', '提现', '', '0', '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('480', '473', '投资公司提现审核/提交', '/investcompanywithdrawalsaudit/submitAudit', '2', '提交', '', null, '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('481', '473', '投资公司提现审核/删除', '/investcompanywithdrawalsaudit/delete', '2', '删除', '', null, '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('482', '473', '投资公司提现审核/审核', '/investcompanywithdrawalsaudit/toApproval;/investcompanywithdrawalsaudit/approval', '2', '审核', '', null, '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('483', '473', '投资公司提现审核/查看', '/investcompanywithdrawalsaudit/find', '2', '查看', '', null, '1', '2015-11-13 17:30:51', null);
INSERT INTO `tbconsoleresource` VALUES ('484', '423', '到期还款下载', '/fixedbaorepayrecord/repayrecordDownload', '2', '', '', '0', '1', '2015-11-13 14:13:17', null);
INSERT INTO `tbconsoleresource` VALUES ('485', '424', '申购记录下载', '/fixedbaoinvestrecord/investrecordDownload', '2', '', '', '0', '1', '2015-11-13 14:09:56', null);
INSERT INTO `tbconsoleresource` VALUES ('486', '422', '平台结算下载', '/fixedbaodialyoverview/fixedbaodialyReportDownload', '2', '', '', '0', '1', '2015-11-13 14:08:12', null);
INSERT INTO `tbconsoleresource` VALUES ('487', '418', '渠道结算', '/fixedbaosubjectcollect', '1', '', '', '4', '1', '2015-10-22 10:29:42', '2015-10-22 11:22:12');
INSERT INTO `tbconsoleresource` VALUES ('488', '487', '渠道结算下载', '/fixedbaosubjectcollect/downloadCollect', '2', '', '', '0', '1', '2015-11-13 14:08:57', null);
INSERT INTO `tbconsoleresource` VALUES ('490', '489', '每日概况', '/currentbaodialyoverview', '1', '', '', '0', '1', '2015-10-28 10:10:51', null);
INSERT INTO `tbconsoleresource` VALUES ('491', '489', '标的管理', '/currentbaosubject', '1', '', '', '1', '1', '2015-10-28 10:51:20', null);
INSERT INTO `tbconsoleresource` VALUES ('492', '489', '转入记录', '/currentbaoinvestrecord', '1', '', '', '4', '1', '2015-10-28 11:19:59', '2015-10-28 11:21:39');
INSERT INTO `tbconsoleresource` VALUES ('493', '489', '转出记录', '/currentbaorepayrecord', '1', '', '', '5', '1', '2015-10-28 11:38:12', null);
INSERT INTO `tbconsoleresource` VALUES ('494', '489', '平台结算', '/currentbaodialyoverview/currentbaodialyoverviewReport', '1', '', '', '2', '1', '2015-10-30 11:45:29', null);
INSERT INTO `tbconsoleresource` VALUES ('495', '489', '固定资产结算', '/currentbaosubjectcollect', '1', '', '', '3', '1', '2015-10-30 13:36:20', null);
INSERT INTO `tbconsoleresource` VALUES ('496', '489', '活期宝设置', '/currentbaoproject', '1', '', '', '6', '1', '2015-10-30 13:53:56', null);
INSERT INTO `tbconsoleresource` VALUES ('497', '490', '每日概况详情', '/currentbaodialyoverview/dailydetail', '2', '', '', '0', '1', '2015-11-19 16:14:53', null);
INSERT INTO `tbconsoleresource` VALUES ('498', '490', '每日概况标的详情', '/currentbaosubject/getById', '2', '', '', '1', '1', '2015-11-19 16:15:22', null);
INSERT INTO `tbconsoleresource` VALUES ('499', '491', '添加活期宝', '/currentbaosubject/typeChioce;/currentbaosubject/typeChange;/currentbaosubject/bankCollectionSave;/currentbaosubject/fixedClaimSave;/currentbaosubject/thirdPartyDiscountSave', '2', '', '', '0', '1', '2015-11-19 16:20:41', null);
INSERT INTO `tbconsoleresource` VALUES ('500', '491', '活期宝调整银行规模', '/currentbaosubject/reset;/currentbaosubject/resetUpdate', '2', '', '', '1', '1', '2015-11-19 16:22:32', null);
INSERT INTO `tbconsoleresource` VALUES ('501', '491', '活期宝标的提交', '/currentbaosubject/submitApproval', '2', '', '', '2', '1', '2015-11-19 16:24:25', null);
INSERT INTO `tbconsoleresource` VALUES ('502', '491', '活期宝标的修改', '/currentbaosubject/find;/currentbaosubject/fixedClaimUpdate;/currentbaosubject/thirdPartyDiscountUpdate;/currentbaosubject/bankCollectionUpdate', '2', '', '', '3', '1', '2015-11-19 16:26:05', null);
INSERT INTO `tbconsoleresource` VALUES ('503', '491', '活期宝标的删除', '/currentbaosubject/delete', '2', '', '', '4', '1', '2015-11-19 16:26:56', null);
INSERT INTO `tbconsoleresource` VALUES ('504', '491', '活期宝审核', '/currentbaosubject/toApproval;/currentbaosubject/approval', '2', '', '', '5', '1', '2015-11-19 16:28:44', null);
INSERT INTO `tbconsoleresource` VALUES ('505', '496', '活期宝项目编辑', '/currentbaoproject/find;/currentbaoproject/update', '2', '', '', '0', '1', '2015-11-19 16:30:40', null);
INSERT INTO `tbconsoleresource` VALUES ('506', '489', '利息结算', '/currentbaoincome', '1', '', '', '5', '1', '2015-11-26 12:57:42', '2015-11-26 12:57:55');
INSERT INTO `tbconsoleresource` VALUES ('507', '495', '下载', '/currentbaosubjectcollect/downloadCollect', '2', '', '', '0', '1', '2015-11-26 15:16:39', null);
INSERT INTO `tbconsoleresource` VALUES ('508', '492', '下载', '/currentbaoinvestrecord/investrecordDownload', '2', '', '', '0', '1', '2015-11-26 15:17:10', null);
INSERT INTO `tbconsoleresource` VALUES ('509', '493', '下载', '/currentbaorepayrecord/repayRecordDownload', '2', '', '', '0', '1', '2015-11-26 15:17:39', null);
INSERT INTO `tbconsoleresource` VALUES ('510', '494', '下载', '/currentbaodialyoverview/currentbaodialyoverviewReport/download', '2', '', '', '0', '1', '2015-11-26 15:15:42', null);
INSERT INTO `tbconsoleresource` VALUES ('511', '506', '下载', '/currentbaoincome/incomeRecordDownload', '2', '', '', '0', '1', '2015-11-26 15:18:15', null);
INSERT INTO `tbconsoleresource` VALUES ('512', '119', '用户统计（含定期宝、活期宝）', '/reportforuser', '1', '', '', '2', '1', '2015-12-08 15:13:47', '2015-12-08 15:14:00');
INSERT INTO `tbconsoleresource` VALUES ('513', '512', '下载', '/reportforuser/download/reportforuser', '2', '', '', '0', '1', '2015-12-08 15:14:33', null);
INSERT INTO `tbconsoleresource` VALUES ('515', '514', '预算申请', '/pyramidfundapplication/list', '1', '', '', '3', '1', '2015-12-08 16:35:08', '2015-12-08 16:40:26');
INSERT INTO `tbconsoleresource` VALUES ('516', '514', '预算账户', '/pyramidfund/list', '1', '', '', '4', '1', '2015-12-08 16:36:29', '2015-12-08 16:40:39');
INSERT INTO `tbconsoleresource` VALUES ('517', '514', '转出审核', '/pyramidcash/list', '1', '', '', '5', '1', '2015-12-08 16:37:40', '2015-12-08 16:41:12');
INSERT INTO `tbconsoleresource` VALUES ('518', '514', '统计列表', '/pyramidreport/list', '1', '', '', '2', '1', '2015-12-08 16:38:44', '2015-12-08 16:39:47');
INSERT INTO `tbconsoleresource` VALUES ('519', '514', '用户列表', '/pyramidaccount/list', '1', '', '', '1', '1', '2015-12-08 16:39:31', null);
INSERT INTO `tbconsoleresource` VALUES ('520', '519', '用户列表/查看', '/pyramidaccount/stair;/pyramidaccount/second', '2', '', '', '1', '1', '2015-12-10 10:33:57', null);
INSERT INTO `tbconsoleresource` VALUES ('521', '518', '统计列表/下载', '/pyramidreport/download/report', '2', '', '', '1', '1', '2015-12-10 10:36:03', null);
INSERT INTO `tbconsoleresource` VALUES ('522', '515', '预算申请/新增', '/pyramidfundapplication/new;/pyramidfundapplication/save;/pyramidfundapplication/update', '2', '', '', '1', '1', '2015-12-10 10:37:17', '2015-12-16 02:26:09');
INSERT INTO `tbconsoleresource` VALUES ('523', '515', '预算申请/查看', '/pyramidfundapplication/detail', '2', '', '', '2', '1', '2015-12-10 10:38:03', null);
INSERT INTO `tbconsoleresource` VALUES ('524', '515', '预算申请/编辑', '/pyramidfundapplication/find', '2', '', '', '3', '1', '2015-12-10 10:39:13', null);
INSERT INTO `tbconsoleresource` VALUES ('525', '515', '预算申请/审核', '/pyramidfundapplication/pyramidfundapplicationAudit;/pyramidfundapplication/audit', '2', '', '', '4', '1', '2015-12-10 10:40:02', null);
INSERT INTO `tbconsoleresource` VALUES ('526', '516', '预算账户/编辑', '/pyramidfund/updateState', '2', '', '', '1', '1', '2015-12-10 10:42:41', null);
INSERT INTO `tbconsoleresource` VALUES ('527', '517', '转出申请/一键转出', '/pyramidcash/approve', '2', '', '', '1', '1', '2015-12-10 10:45:21', null);
INSERT INTO `tbconsoleresource` VALUES ('528', '517', '转出申请/下载', '/pyramidcash/download/pyramincash', '2', '', '', '2', '1', '2015-12-10 10:45:52', null);
INSERT INTO `tbconsoleresource` VALUES ('529', '27', '自动开标列表', '/subject/autoOpenList', '1', '', '', '4', '1', '2015-12-25 17:31:27', '2015-12-25 17:31:27');
INSERT INTO `tbconsoleresource` VALUES ('552', '539', '添加', '/pcextendimage/new;/pcextendimage/save', '2', '', '', '0', '1', '2015-12-25 14:20:00', null);
INSERT INTO `tbconsoleresource` VALUES ('553', '539', '删除', '/pcextendimage/delete', '2', '', '', '1', '1', '2015-12-25 14:20:43', null);
INSERT INTO `tbconsoleresource` VALUES ('554', '539', '编辑', '/pcextendimage/update;/pcextendimage/find', '2', '', '', '2', '1', '2015-12-25 14:21:42', null);
INSERT INTO `tbconsoleresource` VALUES ('555', '539', '置顶', '/pcextendimage/toTop', '2', '', '', '3', '1', '2015-12-25 14:22:18', null);
INSERT INTO `tbconsoleresource` VALUES ('556', '489', '活期资产结算', '/currentbaosubjectcollectbank', '1', '', '', '4', '1', '2016-01-22 23:24:30', '2016-01-23 02:39:53');
INSERT INTO `tbconsoleresource` VALUES ('557', '27', '标的类型', '/subjectclass/list', '1', '', '', '6', '1', '2015-12-30 13:58:33', '2015-12-30 13:59:03');
INSERT INTO `tbconsoleresource` VALUES ('558', '557', '标的类型添加', '/subjectclass/new;/subjectclass/save', '2', '添加', '', '1', '1', '2016-01-11 09:46:23', null);
INSERT INTO `tbconsoleresource` VALUES ('559', '557', '标的类型修改', '/subjectclass/find;/subjectclass/update', '2', '修改', '', '2', '1', '2016-01-11 09:48:28', null);
INSERT INTO `tbconsoleresource` VALUES ('560', '557', '标的类型删除', '/subjectclass/delete', '2', '删除', '', '3', '1', '2016-01-11 09:49:19', '2016-01-19 22:34:59');
INSERT INTO `tbconsoleresource` VALUES ('561', '557', '标的类型复核', '/subjectclass/recheck', '2', '复核', '', '4', '1', '2016-01-11 09:51:11', null);
INSERT INTO `tbconsoleresource` VALUES ('563', '562', '利率配置', '/interestconfig/list', '1', '', '', '1', '1', '2015-12-31 14:58:20', null);
INSERT INTO `tbconsoleresource` VALUES ('564', '563', '利率配置添加', '/interestconfig/new;/interestconfig/save', '2', '添加', '', '1', '1', '2016-01-11 09:52:53', null);
INSERT INTO `tbconsoleresource` VALUES ('565', '563', '利率配置修改', '/interestconfig/find;/interestconfig/update', '2', '修改', '', '2', '1', '2016-01-11 09:53:52', null);
INSERT INTO `tbconsoleresource` VALUES ('566', '563', '利率配置查看', '/interestconfig/detaillist', '2', '查看', '', '3', '1', '2016-01-11 10:03:45', null);
INSERT INTO `tbconsoleresource` VALUES ('567', '563', '利率配置配置明细添加', '/interestconfig/detailnew;/interestconfig/detailsave', '2', '添加', '', '4', '1', '2016-01-11 10:05:39', null);
INSERT INTO `tbconsoleresource` VALUES ('568', '563', '利率配置明细修改', '/interestconfig/detailfind;/interestconfig/detailupdate', '2', '修改', '', '5', '1', '2016-01-11 10:06:53', null);
INSERT INTO `tbconsoleresource` VALUES ('569', '563', '利率配置明细删除', '/interestconfig/detaildelete', '2', '删除', '', '6', '1', '2016-01-11 10:07:37', null);
INSERT INTO `tbconsoleresource` VALUES ('572', '571', '加息券管理', '/interestticket/list', '1', '', '', '1', '1', '2015-12-21 13:42:09', null);
INSERT INTO `tbconsoleresource` VALUES ('573', '572', '加息券管理/增加', '/interestticket/new;/interestticket/save', '2', '', '', '1', '1', '2015-12-21 13:44:20', null);
INSERT INTO `tbconsoleresource` VALUES ('574', '572', '加息券管理/编辑', '/interestticket/find;/interestticket/update', '2', '', '', '2', '1', '2015-12-21 13:45:34', null);
INSERT INTO `tbconsoleresource` VALUES ('575', '572', '加息券管理/删除', '/interestticket/delete', '2', '', '', '3', '1', '2015-12-21 13:47:14', '2015-12-21 13:47:24');
INSERT INTO `tbconsoleresource` VALUES ('576', '571', '加息券申请', '/interestticketapply/list', '1', '', '', '2', '1', '2015-12-22 17:32:46', null);
INSERT INTO `tbconsoleresource` VALUES ('577', '576', '加息券活动申请添加', '/interestticketapply/new;/interestticketapply/redirect;/interestticketapply/save', '2', '加息券活动添加', '', '1', '1', '2016-01-11 10:24:06', null);
INSERT INTO `tbconsoleresource` VALUES ('578', '576', '加息券活动申请修改', '/interestticketapply/find;/interestticketapply/update', '2', '修改', '', '2', '1', '2016-01-11 10:28:35', null);
INSERT INTO `tbconsoleresource` VALUES ('579', '576', '加息券活动申请查看', '/interestticketapply/detail', '2', '查看', '', '3', '1', '2016-01-11 10:30:11', null);
INSERT INTO `tbconsoleresource` VALUES ('580', '576', '加息券活动申请审核', '/interestticketapply/interestticketapplyAudit;/interestticketapply/audit', '2', '审核', '', '4', '1', '2016-01-11 10:34:29', null);
INSERT INTO `tbconsoleresource` VALUES ('581', '576', '加息券活动申请追加', '/interestticketapply/interestticketapplyAppend;/interestticketapply/append', '2', '追加', '', '5', '1', '2016-01-11 10:35:44', '2016-01-11 10:52:26');
INSERT INTO `tbconsoleresource` VALUES ('582', '576', '加息券活动申请追加修改', '/interestticketapply/interestticketapplyEdit;/interestticketapply/appendUpdate', '2', '追加修改', '', '6', '1', '2016-01-11 10:37:30', null);
INSERT INTO `tbconsoleresource` VALUES ('583', '571', '活动发放', '/interestticketapply/grantlist', '1', '发布', '', '4', '1', '2016-01-11 10:40:43', '2016-01-19 22:48:39');
INSERT INTO `tbconsoleresource` VALUES ('584', '583', '加息券活动发放', '/interestticketapply/findgrant;/interestticketapply/grant;', '2', '发放', '', '1', '1', '2016-01-11 10:46:51', null);
INSERT INTO `tbconsoleresource` VALUES ('585', '583', '加息券活动发放格式下载', '/interestticketapply/directedredDown', '2', '下载', '', '2', '1', '2016-01-11 10:48:27', null);
INSERT INTO `tbconsoleresource` VALUES ('586', '583', '加息券活动发放详情', '/interestticketapply/issuelist', '2', '发放详情', '', '3', '1', '2016-01-11 10:49:19', null);
INSERT INTO `tbconsoleresource` VALUES ('587', '583', '加息券活动发放使用详情', '/interestticketapply/employlist', '2', '使用详情', '', '4', '1', '2016-01-11 10:50:06', '2016-01-11 10:52:51');
INSERT INTO `tbconsoleresource` VALUES ('589', '562', '数据查询', '/subjectresult/list', '1', '', '', '2', '1', '2016-01-19 22:33:41', null);
INSERT INTO `tbconsoleresource` VALUES ('590', '589', '多财数据查看', '/subjectresult/investlist', '2', '', '', '1', '1', '2016-01-19 22:34:36', null);
INSERT INTO `tbconsoleresource` VALUES ('591', '571', '活动发布', '/interestticketapply/publishlist', '1', '', '', '3', '1', '2016-01-19 22:37:52', null);
INSERT INTO `tbconsoleresource` VALUES ('592', '591', '加息券活动发布	', '/interestticketapply/findpublish;/interestticketapply/publish', '2', '', '', '1', '1', '2016-01-19 22:39:38', '2016-01-23 03:10:43');
INSERT INTO `tbconsoleresource` VALUES ('593', '571', '加息券活动统计', '/interestticketapply/reportlist', '1', '', '', '5', '1', '2016-01-19 22:40:33', '2016-01-19 22:48:56');
INSERT INTO `tbconsoleresource` VALUES ('594', '593', '加息券活动统计查看', '/interestticketapply/settlementlist', '2', '', '', '1', '1', '2016-01-19 22:41:33', null);
INSERT INTO `tbconsoleresource` VALUES ('595', '583', '加息券活动发放待发详情', '/interestticketapply/dueoutlist', '2', '使用详情', '', '5', '1', '2016-01-11 10:50:06', '2016-01-11 10:52:52');
INSERT INTO `tbconsoleresource` VALUES ('596', '576', '加息券查询权限', '/interestticketapply/findRate;/interestticketapply/rates;/interestticketapply/interestTime;/interestticketapply/maxRate;/interestticketapply/iexpired', '2', '', '', '7', '1', '2016-01-23 02:31:08', '2016-01-23 02:46:35');
INSERT INTO `tbconsoleresource` VALUES ('597', '556', '年化利率管理', '/currentbaosubject/editBankIntrestRate;/currentbaosubject/editBankIntrestRate/update', '2', '', '', null, '1', '2016-01-23 02:43:14', '2016-01-23 02:44:29');
INSERT INTO `tbconsoleresource` VALUES ('598', '556', '下载', '/currentbaosubjectcollectbank/downloadCollect', '2', '', '', null, '1', '2016-01-23 02:46:02', null);
INSERT INTO `tbconsoleresource` VALUES ('599', '494', '查看', '/currentbaodialyoverview/currentbaodialyoverviewReportDetail', '2', '查看', '', '2', '1', '2016-01-23 02:58:47', null);
INSERT INTO `tbconsoleresource` VALUES ('600', '529', '自动开标列表/设置排序', '/subject/updAutoOpenPriority', '2', '', '', null, '1', '2016-01-26 12:47:10', null);
INSERT INTO `tbconsoleresource` VALUES ('601', '529', '自动开标列表/下架', '/subject/downSubject', '2', '', '', '1', '1', '2016-01-26 12:47:39', '2016-01-26 12:47:52');
INSERT INTO `tbconsoleresource` VALUES ('602', '290', '借款标的/查看', '/report013/phase', '2', '', '', '2', '1', '2016-01-31 18:43:02', '2016-01-31 18:43:54');
INSERT INTO `tbconsoleresource` VALUES ('603', '118', 'APP内公告', '/appnotice', '1', '', '', '4', '1', '2016-02-16 15:37:42', '2016-02-16 15:37:42');
INSERT INTO `tbconsoleresource` VALUES ('604', '87', '评测模型', '/devaluationModel', '1', '', 'icon-list', '0', '1', '2018-04-27 00:50:46', '2018-05-01 11:23:31');
INSERT INTO `tbconsoleresource` VALUES ('605', '87', '讨论标签', '/dtags', '1', '', '', '0', '1', '2018-04-27 00:52:08', null);
INSERT INTO `tbconsoleresource` VALUES ('606', '0', '用户管理', '/', '1', '', 'icon-list', '1', '1', '2018-04-27 01:01:23', null);
INSERT INTO `tbconsoleresource` VALUES ('607', '606', '用户信息', '/user', '1', '', '', '0', '1', '2018-04-27 01:02:24', '2018-05-01 16:14:42');
INSERT INTO `tbconsoleresource` VALUES ('608', '606', '实名认证审核', '/userCard', '1', '', '', '1', '1', '2018-04-27 01:02:52', '2018-05-21 16:13:51');
INSERT INTO `tbconsoleresource` VALUES ('609', '0', '项目管理', '/', '1', '', '', '2', '1', '2018-04-27 01:03:36', null);
INSERT INTO `tbconsoleresource` VALUES ('610', '609', '项目信息', '/project', '1', '', '', '0', '1', '2018-04-27 01:04:10', '2018-05-02 11:45:58');
INSERT INTO `tbconsoleresource` VALUES ('611', '609', '项目类型', '/dprojectType', '1', '', '', '1', '1', '2018-04-27 01:04:37', '2018-05-02 11:46:10');
INSERT INTO `tbconsoleresource` VALUES ('612', '0', '内容管理', '/', '1', '', '', '3', '1', '2018-04-27 01:05:18', null);
INSERT INTO `tbconsoleresource` VALUES ('613', '612', '评测管理', '/post/evaluation', '1', '', '', '0', '1', '2018-04-27 01:05:55', '2018-05-02 11:46:23');
INSERT INTO `tbconsoleresource` VALUES ('614', '612', '讨论管理', '/post/discuss', '1', '', '', '1', '1', '2018-04-27 01:06:24', '2018-05-02 11:46:40');
INSERT INTO `tbconsoleresource` VALUES ('615', '612', '文章管理', '/post/article', '1', '', '', '2', '1', '2018-04-27 01:06:53', '2018-05-02 11:46:53');
INSERT INTO `tbconsoleresource` VALUES ('616', '612', '评论管理', '/post/comments', '1', '', '', '3', '1', '2018-04-27 01:07:31', '2018-05-02 11:47:03');
INSERT INTO `tbconsoleresource` VALUES ('617', '604', '新增', '/devaluationModel/new', '2', '新增', '', '0', '1', '2018-05-01 11:23:59', null);
INSERT INTO `tbconsoleresource` VALUES ('618', '604', '修改', '/devaluationModel/update', '2', '修改', '', null, '1', '2018-05-01 11:24:37', null);
INSERT INTO `tbconsoleresource` VALUES ('619', '604', '保存', '/devaluationModel/save', '2', '保存', '', '1', '1', '2018-05-01 11:25:10', null);
INSERT INTO `tbconsoleresource` VALUES ('620', '604', '删除', '/devaluationModel/delete', '2', '删除', '', '2', '1', '2018-05-01 11:25:31', null);
INSERT INTO `tbconsoleresource` VALUES ('621', '605', '新增', '/dtags/new', '2', '新增', '', '0', '1', '2018-05-01 11:26:43', null);
INSERT INTO `tbconsoleresource` VALUES ('622', '605', '修改', '/dtags/update', '2', '修改', '', '1', '1', '2018-05-01 11:27:09', null);
INSERT INTO `tbconsoleresource` VALUES ('623', '605', '保存', '/dtags/save', '2', '保存', '', '2', '1', '2018-05-01 11:27:32', null);
INSERT INTO `tbconsoleresource` VALUES ('624', '605', '删除', '/dtags/delete', '2', '删除', '', '3', '1', '2018-05-01 11:27:54', null);
INSERT INTO `tbconsoleresource` VALUES ('625', '607', '修改', '/user/update', '2', '修改', '', '0', '1', '2018-05-01 16:15:14', null);
INSERT INTO `tbconsoleresource` VALUES ('626', '607', '禁用', '/user/delete', '2', '禁用', '', '1', '1', '2018-05-01 16:16:08', null);
INSERT INTO `tbconsoleresource` VALUES ('627', '607', '激活', '/user/active', '2', '激活', '', '2', '1', '2018-05-01 16:17:09', null);
INSERT INTO `tbconsoleresource` VALUES ('628', '605', '启用', '/dtags/active', '2', '启用', '', '2', '1', '2018-05-01 16:17:45', null);
INSERT INTO `tbconsoleresource` VALUES ('629', '613', '修改', '/post/evaluation/update', '2', '', '', '0', '1', '2018-05-02 10:32:20', null);
INSERT INTO `tbconsoleresource` VALUES ('630', '613', '显示', '/post/evaluation/active', '2', '', '', '1', '1', '2018-05-02 10:32:51', null);
INSERT INTO `tbconsoleresource` VALUES ('631', '613', '隐藏', '/post/evaluation/delete', '2', '', '', '2', '1', '2018-05-02 10:33:16', null);
INSERT INTO `tbconsoleresource` VALUES ('632', '614', '修改', '/post/discuss/update', '2', '', '', '0', '1', '2018-05-02 10:33:44', null);
INSERT INTO `tbconsoleresource` VALUES ('633', '614', '显示', '/post/discuss/active', '2', '', '', '1', '1', '2018-05-02 10:34:10', null);
INSERT INTO `tbconsoleresource` VALUES ('634', '614', '隐藏', '/post/discuss/delete', '2', '', '', '2', '1', '2018-05-02 10:34:36', null);
INSERT INTO `tbconsoleresource` VALUES ('635', '615', '修改', '/post/article/update', '2', '', '', '0', '1', '2018-05-02 10:35:06', null);
INSERT INTO `tbconsoleresource` VALUES ('636', '615', '显示', '/post/article/active', '2', '', '', '1', '1', '2018-05-02 10:37:28', null);
INSERT INTO `tbconsoleresource` VALUES ('637', '615', '隐藏', '/post/article/delete', '2', '', '', '2', '1', '2018-05-02 10:37:43', null);
INSERT INTO `tbconsoleresource` VALUES ('638', '616', '修改', '/post/comments/update', '2', '', '', '0', '1', '2018-05-02 10:41:02', null);
INSERT INTO `tbconsoleresource` VALUES ('639', '616', '显示', '/post/comments/active', '2', '', '', '1', '1', '2018-05-02 10:41:21', null);
INSERT INTO `tbconsoleresource` VALUES ('640', '616', '隐藏', '/post/comments/delete', '2', '', '', '2', '1', '2018-05-02 10:41:43', null);
INSERT INTO `tbconsoleresource` VALUES ('641', '611', '新增', '/dprojectType/new', '2', '', '', '0', '1', '2018-05-02 10:46:13', null);
INSERT INTO `tbconsoleresource` VALUES ('642', '611', '修改', '/dprojectType/update', '2', '', '', '1', '1', '2018-05-02 10:46:41', null);
INSERT INTO `tbconsoleresource` VALUES ('643', '611', '禁用', '/dprojectType/delete', '2', '', '', '1', '1', '2018-05-02 10:47:15', null);
INSERT INTO `tbconsoleresource` VALUES ('644', '611', '启用', '/dprojectType/active', '2', '', '', '2', '1', '2018-05-02 10:47:55', null);
INSERT INTO `tbconsoleresource` VALUES ('645', '610', '新建', '/project/new', '2', '', '', '0', '1', '2018-05-02 10:48:18', null);
INSERT INTO `tbconsoleresource` VALUES ('646', '610', '修改', '/project/update', '2', '', '', '1', '1', '2018-05-02 10:48:46', null);
INSERT INTO `tbconsoleresource` VALUES ('647', '610', '删除', '/project/delete', '2', '', '', '2', '1', '2018-05-02 10:49:18', null);
INSERT INTO `tbconsoleresource` VALUES ('648', '606', '账号认证审核', '/authentication', '1', '', '', '2', '1', '2018-05-18 20:30:58', '2018-05-23 13:28:15');
INSERT INTO `tbconsoleresource` VALUES ('649', '608', '身份审核', '/userCard/update', '2', '', '', '0', '1', '2018-05-18 20:33:15', '2018-05-21 16:12:56');
INSERT INTO `tbconsoleresource` VALUES ('650', '648', '账号审核', '/authentication/updata', '2', '', '', null, '1', '2018-05-18 20:44:40', '2018-05-23 13:28:27');
INSERT INTO `tbconsoleresource` VALUES ('651', '607', '奖励明细', '/user/financial', '1', '', '', '3', '2', '2018-05-19 09:13:48', '2018-05-19 11:28:59');
INSERT INTO `tbconsoleresource` VALUES ('652', '607', '发放代币', '/user/issue', '1', '', '', '4', '2', '2018-05-19 09:14:44', '2018-05-19 11:29:06');
INSERT INTO `tbconsoleresource` VALUES ('653', '606', '用户奖励明细', '/user', '1', '', '', '4', '2', '2018-05-19 11:23:21', '2018-05-19 11:59:50');
INSERT INTO `tbconsoleresource` VALUES ('654', '606', '用户发放代币', '/user', '1', '', '', '5', '2', '2018-05-19 11:24:36', '2018-05-19 11:59:57');
INSERT INTO `tbconsoleresource` VALUES ('655', '654', '发放代币', '/user/issue', '2', '', '', '1', '1', '2018-05-19 11:30:25', '2018-05-19 11:32:19');
INSERT INTO `tbconsoleresource` VALUES ('656', '653', '奖励明细', '/user/financial', '2', '', '', '1', '1', '2018-05-19 11:30:45', '2018-05-19 11:33:21');
INSERT INTO `tbconsoleresource` VALUES ('657', '0', '代币管理', '/', '1', '', '', '4', '1', '2018-05-19 11:51:46', '2018-05-19 11:52:19');
INSERT INTO `tbconsoleresource` VALUES ('658', '657', '奖励明细', '/tokenaward', '1', '', '', '1', '1', '2018-05-19 11:52:50', '2018-05-21 16:47:53');
INSERT INTO `tbconsoleresource` VALUES ('659', '657', '发放代币', '/tokenaward/grantFind', '1', '', '', '2', '1', '2018-05-19 11:53:39', '2018-05-23 15:44:23');

-- ----------------------------
-- Table structure for tbconsoleroleinfo
-- ----------------------------
DROP TABLE IF EXISTS `tbconsoleroleinfo`;
CREATE TABLE `tbconsoleroleinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vcRoleName` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `iApprovalRatingID` int(11) DEFAULT NULL,
  `iValid` int(2) DEFAULT '1' COMMENT '是否有效。\n            1 -- 有效 （默认）\n            2 -- 无效',
  `dtCreate` datetime DEFAULT NULL COMMENT '该条记录第一次创建的时间。',
  `dtModify` datetime DEFAULT NULL COMMENT '最后一次修改时间，每次修改需更改此字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tbconsoleroleinfo
-- ----------------------------
INSERT INTO `tbconsoleroleinfo` VALUES ('1', '总经理', '1', '1', '2015-03-13 13:54:38', '2018-06-07 15:42:38');
INSERT INTO `tbconsoleroleinfo` VALUES ('2', '数据总监', '2', '1', '2015-03-13 14:23:30', '2018-06-09 10:19:12');
INSERT INTO `tbconsoleroleinfo` VALUES ('3', '运营总监', '2', '1', '2015-03-13 14:28:07', '2018-06-09 10:20:20');
INSERT INTO `tbconsoleroleinfo` VALUES ('5', '财务经理', '2', '1', '2015-03-13 14:30:24', '2018-04-27 00:35:58');
INSERT INTO `tbconsoleroleinfo` VALUES ('26', 'COO', '5', '1', '2015-10-12 09:20:15', '2018-04-27 00:21:47');

-- ----------------------------
-- Table structure for tbconsoleroleresource
-- ----------------------------
DROP TABLE IF EXISTS `tbconsoleroleresource`;
CREATE TABLE `tbconsoleroleresource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `iRoleinfoID` int(11) DEFAULT NULL,
  `iResourceID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19868 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tbconsoleroleresource
-- ----------------------------
INSERT INTO `tbconsoleroleresource` VALUES ('19718', '1', '87');
INSERT INTO `tbconsoleroleresource` VALUES ('19719', '1', '604');
INSERT INTO `tbconsoleroleresource` VALUES ('19720', '1', '618');
INSERT INTO `tbconsoleroleresource` VALUES ('19721', '1', '617');
INSERT INTO `tbconsoleroleresource` VALUES ('19722', '1', '619');
INSERT INTO `tbconsoleroleresource` VALUES ('19723', '1', '620');
INSERT INTO `tbconsoleroleresource` VALUES ('19724', '1', '605');
INSERT INTO `tbconsoleroleresource` VALUES ('19725', '1', '621');
INSERT INTO `tbconsoleroleresource` VALUES ('19726', '1', '622');
INSERT INTO `tbconsoleroleresource` VALUES ('19727', '1', '623');
INSERT INTO `tbconsoleroleresource` VALUES ('19728', '1', '628');
INSERT INTO `tbconsoleroleresource` VALUES ('19729', '1', '624');
INSERT INTO `tbconsoleroleresource` VALUES ('19730', '1', '303');
INSERT INTO `tbconsoleroleresource` VALUES ('19731', '1', '12');
INSERT INTO `tbconsoleroleresource` VALUES ('19732', '1', '51');
INSERT INTO `tbconsoleroleresource` VALUES ('19733', '1', '52');
INSERT INTO `tbconsoleroleresource` VALUES ('19734', '1', '53');
INSERT INTO `tbconsoleroleresource` VALUES ('19735', '1', '11');
INSERT INTO `tbconsoleroleresource` VALUES ('19736', '1', '49');
INSERT INTO `tbconsoleroleresource` VALUES ('19737', '1', '50');
INSERT INTO `tbconsoleroleresource` VALUES ('19738', '1', '77');
INSERT INTO `tbconsoleroleresource` VALUES ('19739', '1', '47');
INSERT INTO `tbconsoleroleresource` VALUES ('19740', '1', '58');
INSERT INTO `tbconsoleroleresource` VALUES ('19741', '1', '59');
INSERT INTO `tbconsoleroleresource` VALUES ('19742', '1', '187');
INSERT INTO `tbconsoleroleresource` VALUES ('19743', '1', '305');
INSERT INTO `tbconsoleroleresource` VALUES ('19744', '1', '91');
INSERT INTO `tbconsoleroleresource` VALUES ('19745', '1', '109');
INSERT INTO `tbconsoleroleresource` VALUES ('19746', '1', '110');
INSERT INTO `tbconsoleroleresource` VALUES ('19747', '1', '111');
INSERT INTO `tbconsoleroleresource` VALUES ('19748', '1', '127');
INSERT INTO `tbconsoleroleresource` VALUES ('19749', '1', '164');
INSERT INTO `tbconsoleroleresource` VALUES ('19750', '1', '165');
INSERT INTO `tbconsoleroleresource` VALUES ('19751', '1', '166');
INSERT INTO `tbconsoleroleresource` VALUES ('19752', '1', '606');
INSERT INTO `tbconsoleroleresource` VALUES ('19753', '1', '607');
INSERT INTO `tbconsoleroleresource` VALUES ('19754', '1', '625');
INSERT INTO `tbconsoleroleresource` VALUES ('19755', '1', '626');
INSERT INTO `tbconsoleroleresource` VALUES ('19756', '1', '627');
INSERT INTO `tbconsoleroleresource` VALUES ('19757', '1', '608');
INSERT INTO `tbconsoleroleresource` VALUES ('19758', '1', '649');
INSERT INTO `tbconsoleroleresource` VALUES ('19759', '1', '648');
INSERT INTO `tbconsoleroleresource` VALUES ('19760', '1', '650');
INSERT INTO `tbconsoleroleresource` VALUES ('19761', '1', '609');
INSERT INTO `tbconsoleroleresource` VALUES ('19762', '1', '610');
INSERT INTO `tbconsoleroleresource` VALUES ('19763', '1', '645');
INSERT INTO `tbconsoleroleresource` VALUES ('19764', '1', '646');
INSERT INTO `tbconsoleroleresource` VALUES ('19765', '1', '647');
INSERT INTO `tbconsoleroleresource` VALUES ('19766', '1', '611');
INSERT INTO `tbconsoleroleresource` VALUES ('19767', '1', '641');
INSERT INTO `tbconsoleroleresource` VALUES ('19768', '1', '642');
INSERT INTO `tbconsoleroleresource` VALUES ('19769', '1', '643');
INSERT INTO `tbconsoleroleresource` VALUES ('19770', '1', '644');
INSERT INTO `tbconsoleroleresource` VALUES ('19771', '1', '612');
INSERT INTO `tbconsoleroleresource` VALUES ('19772', '1', '613');
INSERT INTO `tbconsoleroleresource` VALUES ('19773', '1', '629');
INSERT INTO `tbconsoleroleresource` VALUES ('19774', '1', '630');
INSERT INTO `tbconsoleroleresource` VALUES ('19775', '1', '631');
INSERT INTO `tbconsoleroleresource` VALUES ('19776', '1', '614');
INSERT INTO `tbconsoleroleresource` VALUES ('19777', '1', '632');
INSERT INTO `tbconsoleroleresource` VALUES ('19778', '1', '633');
INSERT INTO `tbconsoleroleresource` VALUES ('19779', '1', '634');
INSERT INTO `tbconsoleroleresource` VALUES ('19780', '1', '615');
INSERT INTO `tbconsoleroleresource` VALUES ('19781', '1', '635');
INSERT INTO `tbconsoleroleresource` VALUES ('19782', '1', '636');
INSERT INTO `tbconsoleroleresource` VALUES ('19783', '1', '637');
INSERT INTO `tbconsoleroleresource` VALUES ('19784', '1', '616');
INSERT INTO `tbconsoleroleresource` VALUES ('19785', '1', '638');
INSERT INTO `tbconsoleroleresource` VALUES ('19786', '1', '639');
INSERT INTO `tbconsoleroleresource` VALUES ('19787', '1', '640');
INSERT INTO `tbconsoleroleresource` VALUES ('19788', '1', '657');
INSERT INTO `tbconsoleroleresource` VALUES ('19789', '1', '658');
INSERT INTO `tbconsoleroleresource` VALUES ('19790', '1', '659');
INSERT INTO `tbconsoleroleresource` VALUES ('19791', '1', '117');
INSERT INTO `tbconsoleroleresource` VALUES ('19792', '1', '252');
INSERT INTO `tbconsoleroleresource` VALUES ('19793', '1', '253');
INSERT INTO `tbconsoleroleresource` VALUES ('19794', '1', '254');
INSERT INTO `tbconsoleroleresource` VALUES ('19795', '1', '126');
INSERT INTO `tbconsoleroleresource` VALUES ('19796', '1', '131');
INSERT INTO `tbconsoleroleresource` VALUES ('19797', '1', '132');
INSERT INTO `tbconsoleroleresource` VALUES ('19798', '1', '139');
INSERT INTO `tbconsoleroleresource` VALUES ('19799', '1', '150');
INSERT INTO `tbconsoleroleresource` VALUES ('19800', '1', '151');
INSERT INTO `tbconsoleroleresource` VALUES ('19801', '1', '152');
INSERT INTO `tbconsoleroleresource` VALUES ('19802', '1', '153');
INSERT INTO `tbconsoleroleresource` VALUES ('19803', '1', '123');
INSERT INTO `tbconsoleroleresource` VALUES ('19804', '1', '124');
INSERT INTO `tbconsoleroleresource` VALUES ('19805', '1', '125');
INSERT INTO `tbconsoleroleresource` VALUES ('19806', '1', '141');
INSERT INTO `tbconsoleroleresource` VALUES ('19807', '1', '157');
INSERT INTO `tbconsoleroleresource` VALUES ('19808', '1', '158');
INSERT INTO `tbconsoleroleresource` VALUES ('19809', '1', '159');
INSERT INTO `tbconsoleroleresource` VALUES ('19810', '1', '128');
INSERT INTO `tbconsoleroleresource` VALUES ('19811', '1', '142');
INSERT INTO `tbconsoleroleresource` VALUES ('19812', '1', '145');
INSERT INTO `tbconsoleroleresource` VALUES ('19813', '1', '129');
INSERT INTO `tbconsoleroleresource` VALUES ('19814', '1', '144');
INSERT INTO `tbconsoleroleresource` VALUES ('19815', '1', '146');
INSERT INTO `tbconsoleroleresource` VALUES ('19816', '1', '147');
INSERT INTO `tbconsoleroleresource` VALUES ('19817', '1', '9');
INSERT INTO `tbconsoleroleresource` VALUES ('19818', '1', '13');
INSERT INTO `tbconsoleroleresource` VALUES ('19819', '1', '54');
INSERT INTO `tbconsoleroleresource` VALUES ('19820', '1', '55');
INSERT INTO `tbconsoleroleresource` VALUES ('19821', '1', '56');
INSERT INTO `tbconsoleroleresource` VALUES ('19822', '1', '10');
INSERT INTO `tbconsoleroleresource` VALUES ('19823', '1', '15');
INSERT INTO `tbconsoleroleresource` VALUES ('19824', '1', '17');
INSERT INTO `tbconsoleroleresource` VALUES ('19825', '1', '18');
INSERT INTO `tbconsoleroleresource` VALUES ('19826', '1', '188');
INSERT INTO `tbconsoleroleresource` VALUES ('19827', '1', '189');
INSERT INTO `tbconsoleroleresource` VALUES ('19828', '1', '190');
INSERT INTO `tbconsoleroleresource` VALUES ('19829', '2', '87');
INSERT INTO `tbconsoleroleresource` VALUES ('19830', '2', '606');
INSERT INTO `tbconsoleroleresource` VALUES ('19831', '2', '609');
INSERT INTO `tbconsoleroleresource` VALUES ('19832', '2', '612');
INSERT INTO `tbconsoleroleresource` VALUES ('19833', '2', '657');
INSERT INTO `tbconsoleroleresource` VALUES ('19834', '2', '117');
INSERT INTO `tbconsoleroleresource` VALUES ('19835', '3', '87');
INSERT INTO `tbconsoleroleresource` VALUES ('19836', '3', '604');
INSERT INTO `tbconsoleroleresource` VALUES ('19837', '3', '618');
INSERT INTO `tbconsoleroleresource` VALUES ('19838', '3', '617');
INSERT INTO `tbconsoleroleresource` VALUES ('19839', '3', '619');
INSERT INTO `tbconsoleroleresource` VALUES ('19840', '3', '620');
INSERT INTO `tbconsoleroleresource` VALUES ('19841', '3', '605');
INSERT INTO `tbconsoleroleresource` VALUES ('19842', '3', '621');
INSERT INTO `tbconsoleroleresource` VALUES ('19843', '3', '622');
INSERT INTO `tbconsoleroleresource` VALUES ('19844', '3', '623');
INSERT INTO `tbconsoleroleresource` VALUES ('19845', '3', '628');
INSERT INTO `tbconsoleroleresource` VALUES ('19846', '3', '624');
INSERT INTO `tbconsoleroleresource` VALUES ('19847', '3', '303');
INSERT INTO `tbconsoleroleresource` VALUES ('19848', '3', '12');
INSERT INTO `tbconsoleroleresource` VALUES ('19849', '3', '51');
INSERT INTO `tbconsoleroleresource` VALUES ('19850', '3', '52');
INSERT INTO `tbconsoleroleresource` VALUES ('19851', '3', '53');
INSERT INTO `tbconsoleroleresource` VALUES ('19852', '3', '11');
INSERT INTO `tbconsoleroleresource` VALUES ('19853', '3', '49');
INSERT INTO `tbconsoleroleresource` VALUES ('19854', '3', '50');
INSERT INTO `tbconsoleroleresource` VALUES ('19855', '3', '77');
INSERT INTO `tbconsoleroleresource` VALUES ('19856', '3', '47');
INSERT INTO `tbconsoleroleresource` VALUES ('19857', '3', '58');
INSERT INTO `tbconsoleroleresource` VALUES ('19858', '3', '59');
INSERT INTO `tbconsoleroleresource` VALUES ('19859', '3', '187');
INSERT INTO `tbconsoleroleresource` VALUES ('19860', '3', '305');
INSERT INTO `tbconsoleroleresource` VALUES ('19861', '3', '91');
INSERT INTO `tbconsoleroleresource` VALUES ('19862', '3', '109');
INSERT INTO `tbconsoleroleresource` VALUES ('19863', '3', '110');
INSERT INTO `tbconsoleroleresource` VALUES ('19864', '3', '127');
INSERT INTO `tbconsoleroleresource` VALUES ('19865', '3', '164');
INSERT INTO `tbconsoleroleresource` VALUES ('19866', '3', '165');
INSERT INTO `tbconsoleroleresource` VALUES ('19867', '3', '166');

-- ----------------------------
-- Table structure for tbdiscuss
-- ----------------------------
DROP TABLE IF EXISTS `tbdiscuss`;
CREATE TABLE `tbdiscuss` (
  `discuss_id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) DEFAULT NULL,
  `disscuss_contents` varchar(3000) DEFAULT NULL COMMENT '讨论内容',
  `post_uuid` varchar(32) NOT NULL COMMENT '图片url等json信息；目前保存9张',
  `tag_infos` varchar(300) DEFAULT NULL COMMENT '标签ID，名称的json串',
  PRIMARY KEY (`discuss_id`)
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbdiscuss
-- ----------------------------

-- ----------------------------
-- Table structure for tbevaluation
-- ----------------------------
DROP TABLE IF EXISTS `tbevaluation`;
CREATE TABLE `tbevaluation` (
  `evaluation_id` int(11) NOT NULL AUTO_INCREMENT,
  `post_uuid` varchar(32) NOT NULL,
  `post_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `model_type` int(1) NOT NULL COMMENT '1-简单测评；2-全面系统专业测评;3-部分系统专业评测；4-用户自定义专业测评',
  `total_score` decimal(3,1) DEFAULT NULL COMMENT '1-10，小数点1位',
  `professional_eva_detail` text COMMENT '专业评测各子项评测内容详情json字符串',
  `evauation_content` varchar(10000) DEFAULT NULL COMMENT '评测描述信息',
  `evaluation_tags` varchar(500) DEFAULT NULL COMMENT '评测表标签',
  `status` int(1) DEFAULT NULL COMMENT '0删除；1有效',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `create_user_id` int(11) NOT NULL,
  PRIMARY KEY (`evaluation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbevaluation
-- ----------------------------

-- ----------------------------
-- Table structure for tbfollow
-- ----------------------------
DROP TABLE IF EXISTS `tbfollow`;
CREATE TABLE `tbfollow` (
  `follow_id` int(11) NOT NULL AUTO_INCREMENT,
  `follow_user_id` int(11) NOT NULL,
  `follow_type` int(1) NOT NULL DEFAULT '1' COMMENT '关注类型：1-关注项目;2-关注帖子；3-关注用户',
  `follower_user_name` varchar(100) DEFAULT NULL,
  `followed_user_id` int(11) NOT NULL,
  `followed_user_signature` varchar(255) DEFAULT NULL,
  `followed_user_icon` varchar(255) DEFAULT NULL,
  `followed_user_name` varchar(100) DEFAULT NULL,
  `followed_id` int(11) DEFAULT NULL COMMENT '关注项目为项目id，关注用户为uerid，关注帖子为postid',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：1-有效；0-删除',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`follow_id`),
  UNIQUE KEY `uk_user_follow_status` (`follow_user_id`,`followed_user_id`,`status`) USING BTREE,
  KEY `idx_user_id` (`follow_user_id`) USING BTREE,
  KEY `idx_type_user` (`follow_type`,`followed_user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbfollow
-- ----------------------------

-- ----------------------------
-- Table structure for tbmessage
-- ----------------------------
DROP TABLE IF EXISTS `tbmessage`;
CREATE TABLE `tbmessage` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(1) DEFAULT '1' COMMENT '消息类型：1-关注；2-点赞；3-评论；4-赞赏；5-评论被回复；6-上榜单；7-奖励token',
  `status` int(1) DEFAULT NULL COMMENT '状态：0-删除；1-有效',
  `state` int(1) DEFAULT NULL COMMENT '状态：1-未读；2-已读',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `sender_user_id` int(11) DEFAULT NULL COMMENT '消息发送人id',
  `jump_info` varchar(255) DEFAULT NULL COMMENT '跳转用到的信息',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=360 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbmessage
-- ----------------------------

-- ----------------------------
-- Table structure for tbmobileversionupdate
-- ----------------------------
DROP TABLE IF EXISTS `tbmobileversionupdate`;
CREATE TABLE `tbmobileversionupdate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `m_type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT ' IOS APPSTORE  ANDROID',
  `base_version` int(11) NOT NULL COMMENT '新版本主版本号',
  `alpha_version` int(11) NOT NULL COMMENT '新版本次版本号',
  `beta_version` int(11) NOT NULL COMMENT '新版本修订版本号',
  `f_base_version` int(11) NOT NULL COMMENT '强制升级主版本号',
  `f_alpha_version` int(11) NOT NULL COMMENT '强制升级次版本号',
  `f_beta_version` int(11) NOT NULL COMMENT '强制升级修订版本号',
  `upgrade_url` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '升级apk地址',
  `guide_url` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '强制升级apk地址',
  `upexplain` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '更新内容描述',
  `m_enable` int(2) NOT NULL COMMENT '是否生效 1 生效 0 失效',
  `memo` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tbmobileversionupdate
-- ----------------------------
INSERT INTO `tbmobileversionupdate` VALUES ('1', 'IOS_99', '4', '3', '8', '3', '9', '0', 'https://itunes.apple.com/cn/app/id1044513283', 'https://itunes.apple.com/cn/app/id1044513283', '新增毕业班功能\r\n-毕业生所在班级空间升级为毕业空间；\r\n-毕业生将显示毕业标识；\r\n-毕业班原班主任、校管理员可在“班级管理”中的“毕业班级”查看、管理毕业班级\r\n', '1', 'KFF IOS 个人版');
INSERT INTO `tbmobileversionupdate` VALUES ('2', 'ANDROID', '1', '1', '6', '1', '1', '5', 'http://app.qufen.top/upload/apk/upgradeapk/find.apk', 'http://app.qufen.top/upload/apk/gradeapk/find.apk', ' 1.修复了一些bug\r\n 2.优化了用户注册信息。。。', '1', 'KFF 安卓版');
INSERT INTO `tbmobileversionupdate` VALUES ('3', 'IOS_299', '4', '3', '8', '3', '9', '0', 'https://itunes.apple.com/cn/app/id1044513283', 'https://itunes.apple.com/cn/app/id1044513283', '新增毕业班功能\r\n-毕业生所在班级空间升级为毕业空间；\r\n-毕业生将显示毕业标识；\r\n-毕业班原班主任、校管理员可在“班级管理”中的“毕业班级”查看、管理毕业班级\r\n', '1', 'KFF IOS企业版');

-- ----------------------------
-- Table structure for tbnotice
-- ----------------------------
DROP TABLE IF EXISTS `tbnotice`;
CREATE TABLE `tbnotice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) DEFAULT NULL COMMENT '标题',
  `content` varchar(300) DEFAULT NULL COMMENT '公告内容',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL COMMENT '状态1有效0删除',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbnotice
-- ----------------------------

-- ----------------------------
-- Table structure for tbpost
-- ----------------------------
DROP TABLE IF EXISTS `tbpost`;
CREATE TABLE `tbpost` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目ID',
  `project_icon` varchar(255) DEFAULT NULL,
  `project_code` varchar(10) DEFAULT NULL,
  `project_english_name` varchar(30) DEFAULT NULL,
  `project_chinese_name` varchar(30) DEFAULT NULL COMMENT '项目中文名称',
  `post_title` varchar(30) NOT NULL COMMENT '帖子标题',
  `post_type` int(1) NOT NULL COMMENT '帖子类型：1-评测；2-讨论；3-文章',
  `post_short_desc` varchar(300) DEFAULT NULL COMMENT '剪短描述',
  `post_small_images` varchar(2000) DEFAULT NULL COMMENT '缩略图json，目前最多三张图的url等信息',
  `comments_num` int(11) NOT NULL DEFAULT '0' COMMENT '评论数量',
  `praise_num` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `pageview_num` int(11) DEFAULT '0' COMMENT '浏览量',
  `donate_num` int(11) DEFAULT NULL COMMENT '捐赠人数',
  `collect_num` int(11) DEFAULT NULL COMMENT '收藏人数',
  `create_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `create_user_icon` varchar(100) DEFAULT NULL COMMENT '用户头像',
  `create_user_signature` varchar(30) DEFAULT NULL COMMENT '用户签名',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '用户昵称',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `uuid` varchar(32) NOT NULL,
  `status` int(1) DEFAULT NULL COMMENT '状态：0-删除；1-有效',
  `stick_top` int(1) DEFAULT '0' COMMENT '是否推荐：0-否，1-是',
  `stick_updateTime` datetime DEFAULT NULL COMMENT '操作推荐时间',
  PRIMARY KEY (`post_id`),
  UNIQUE KEY `idx_uuid` (`uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=404 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbpost
-- ----------------------------

-- ----------------------------
-- Table structure for tbpraise
-- ----------------------------
DROP TABLE IF EXISTS `tbpraise`;
CREATE TABLE `tbpraise` (
  `praise_id` int(11) NOT NULL AUTO_INCREMENT,
  `praise_user_id` int(11) NOT NULL,
  `project_id` int(11) DEFAULT NULL,
  `praise_type` int(1) DEFAULT NULL COMMENT '点赞类型1-帖子点赞；2-评论点赞',
  `post_id` int(11) DEFAULT NULL,
  `post_type` int(1) DEFAULT NULL COMMENT '帖子类型：1-评测；2-讨论；3-文章',
  `bepraise_user_id` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT '1' COMMENT '状态:0-取消点赞;1-点赞有效',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`praise_id`),
  UNIQUE KEY `uk_user_post_status` (`praise_user_id`,`post_id`) USING BTREE,
  KEY `idx_post_id` (`post_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbpraise
-- ----------------------------

-- ----------------------------
-- Table structure for tbproject
-- ----------------------------
DROP TABLE IF EXISTS `tbproject`;
CREATE TABLE `tbproject` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_icon` varchar(255) DEFAULT NULL COMMENT '项目图标',
  `state` int(1) NOT NULL DEFAULT '1' COMMENT '审核状态：1；待审核；2-审核通过；3-拒绝',
  `project_code` varchar(10) NOT NULL COMMENT '代币名称',
  `project_english_name` varchar(30) DEFAULT NULL COMMENT '代币英文名称',
  `project_chinese_name` varchar(30) DEFAULT NULL,
  `project_signature` varchar(30) DEFAULT NULL COMMENT '项目标题',
  `website_url` varchar(255) DEFAULT NULL COMMENT '官方网址',
  `listed` int(1) NOT NULL DEFAULT '0' COMMENT '是否上市：0-未上市；1-已上市',
  `issue_date` date DEFAULT NULL COMMENT '上市时间',
  `issue_num` int(11) DEFAULT NULL COMMENT '发行数量',
  `whitepaper_url` varchar(255) DEFAULT NULL COMMENT '白皮书地址',
  `project_type_name` varchar(30) DEFAULT NULL COMMENT '项目分类名称',
  `project_type_id` int(11) DEFAULT NULL COMMENT '项目分类',
  `project_desc` text COMMENT '项目描述',
  `submit_user_id` int(11) DEFAULT NULL COMMENT '创建用户id',
  `submit_user_contact_info` varchar(30) DEFAULT NULL,
  `submit_user_type` int(1) DEFAULT NULL COMMENT '提交用户类型:1-普通用户;2-利益相关;3-项目方;4-投资者',
  `submit_reason` text COMMENT '推荐理由',
  `status` int(1) DEFAULT NULL COMMENT '状态：0-删除；1-有效',
  `createTime` datetime DEFAULT NULL,
  `publishTime` datetime DEFAULT NULL COMMENT '发布时间，对应审核通过时间',
  `updateTime` datetime DEFAULT NULL,
  `total_score` decimal(2,1) DEFAULT NULL COMMENT '项目总评分：1-10精确到1位',
  `rater_num` int(11) DEFAULT '0' COMMENT '评分用户人数',
  `follower_num` int(11) DEFAULT '0' COMMENT '关注用户人数',
  `comments_num` int(11) DEFAULT '0' COMMENT '评论数量',
  `collect_num` int(11) DEFAULT NULL COMMENT '收藏人数',
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbproject
-- ----------------------------

-- ----------------------------
-- Table structure for tbqfindex
-- ----------------------------
DROP TABLE IF EXISTS `tbqfindex`;
CREATE TABLE `tbqfindex` (
  `qf_index_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `contribute_content` double(11,0) DEFAULT NULL COMMENT '内容贡献指数',
  `locked_position` double(11,0) DEFAULT NULL COMMENT '锁仓评测指数',
  `liveness` double(11,0) DEFAULT NULL COMMENT '活跃度指数',
  `influence` double(11,0) DEFAULT NULL COMMENT '影响力指数',
  `community_assessment` double(11,0) DEFAULT NULL COMMENT '社区考核指数',
  `health_degree` double(11,0) DEFAULT NULL COMMENT '健康度',
  `status_hierarchy_desc` varchar(20) DEFAULT NULL COMMENT '0-99刁民  100-199平民  200-299元芳  300-499狄仁杰  500-699柯南 700-899福尔摩斯900-1000包青天',
  `status_hierarchy_type` int(11) DEFAULT NULL COMMENT '分数',
  `yxpraise` int(9) DEFAULT NULL COMMENT '有效赞',
  `createTime` datetime(6) DEFAULT NULL,
  `updateTime` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`qf_index_id`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbqfindex
-- ----------------------------

-- ----------------------------
-- Table structure for tbsmsmanage
-- ----------------------------
DROP TABLE IF EXISTS `tbsmsmanage`;
CREATE TABLE `tbsmsmanage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vcTitle` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `vcContent` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `iType` int(2) DEFAULT NULL COMMENT '1 -- 所有人；\n            2 -- 投资者；\n            3 -- 借款人；',
  `iState` int(2) DEFAULT NULL COMMENT '1 -- 刚录入；\n            2 -- 发布中；\n            3 -- 已发布。\n            ',
  `dtCreate` datetime DEFAULT NULL,
  `dtPublish` datetime DEFAULT NULL,
  `iMaxID` int(11) DEFAULT NULL COMMENT '界面点击发布时，查询前台登陆账号中的最大ID。',
  `iCurrentID` int(11) DEFAULT NULL COMMENT '发布时，登陆账号按ID从小到大排序。记录当前发布的登陆账号ID。任务中断时，允许从此字段继续。',
  `dtRegisterStart` datetime DEFAULT NULL COMMENT '注册开始时间',
  `dtRegisterEnd` datetime DEFAULT NULL COMMENT '注册结束时间',
  `dtLastLoginStart` datetime DEFAULT NULL COMMENT '最后开始登录时间',
  `dtLastLoginEnd` datetime DEFAULT NULL COMMENT '最后登录结束时间',
  `dtLastRechargeStart` datetime DEFAULT NULL COMMENT '最后开始充值时间',
  `dtLastRechargeEnd` datetime DEFAULT NULL COMMENT '最后结束充值时间',
  `dtLastInvestStart` datetime DEFAULT NULL COMMENT '最后开始投资时间',
  `dtLastInvestEnd` datetime DEFAULT NULL COMMENT '最后结束投资时间',
  `minTotalRecharge` decimal(18,2) DEFAULT NULL COMMENT '最小累计充值金额',
  `maxTotalRecharge` decimal(18,2) DEFAULT NULL COMMENT '最大累计充值金额',
  `minTotalInvest` decimal(18,2) DEFAULT NULL COMMENT '最小累计投资金额',
  `maxTotalInvest` decimal(18,2) DEFAULT NULL COMMENT '最大累计投资金额',
  `minTotalBalance` decimal(18,2) DEFAULT NULL COMMENT '最小账户余额',
  `maxTotalBalance` decimal(18,2) DEFAULT NULL COMMENT '最大账户余额',
  `sType` int(2) DEFAULT NULL COMMENT '发送类型：1：立即；2：定时',
  `dtSend` datetime DEFAULT NULL COMMENT '定时时间',
  `minTotalAsset` decimal(18,2) DEFAULT NULL COMMENT '最小资产总计',
  `maxTotalAsset` decimal(18,2) DEFAULT NULL COMMENT '最大资产总计',
  `dtLastRepayStart` date DEFAULT NULL COMMENT '最后一笔回款开始时间',
  `dtLastRepayEnd` date DEFAULT NULL COMMENT '最后一笔回款结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tbsmsmanage
-- ----------------------------

-- ----------------------------
-- Table structure for tbsmsrecord
-- ----------------------------
DROP TABLE IF EXISTS `tbsmsrecord`;
CREATE TABLE `tbsmsrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vcPhone` char(13) COLLATE utf8_bin DEFAULT NULL,
  `vcContent` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `iType` int(2) DEFAULT NULL COMMENT '1 -- 通知；\n            2 -- 验证码。',
  `dtCreate` datetime DEFAULT NULL,
  `iPriority` int(2) DEFAULT NULL COMMENT '1--优先；\n            2--正常。\n            目的：系统正常跑短信的时候，优先发送优先等级为1的短信。允许优先等级为1的短信插队发送。',
  `dtSend` datetime DEFAULT NULL,
  `iState` int(2) DEFAULT NULL,
  `vcMemo` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `sInterface` int(2) DEFAULT '1' COMMENT '短信接口:1:漫道;2:建周',
  `sType` int(2) DEFAULT '1' COMMENT '短信类型:1:文本;2:语音',
  `sResult` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '运营商返回实际结果',
  `sState` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '运营商返回实际状态',
  PRIMARY KEY (`id`),
  KEY `index_vcphone` (`vcPhone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='存放已经发送的短信记录。';

-- ----------------------------
-- Records of tbsmsrecord
-- ----------------------------

-- ----------------------------
-- Table structure for tbsmssend
-- ----------------------------
DROP TABLE IF EXISTS `tbsmssend`;
CREATE TABLE `tbsmssend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vcPhone` char(13) COLLATE utf8_bin DEFAULT NULL,
  `vcContent` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `iType` int(2) DEFAULT NULL COMMENT '1 -- 通知；\n            2 -- 验证码。',
  `dtCreate` datetime DEFAULT NULL,
  `iPriority` int(2) DEFAULT NULL COMMENT '1--优先；\n            2--正常。\n            目的：系统正常跑短信的时候，优先发送优先等级为1的短信。允许优先等级为1的短信插队发送。',
  `vcMemo` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `sType` int(2) DEFAULT '1' COMMENT '短信类型:1:文本;2:语音',
  `dtsend` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=961 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='存放待发送的短信信息，发送之后存入已发短信表';

-- ----------------------------
-- Records of tbsmssend
-- ----------------------------

-- ----------------------------
-- Table structure for tbsmstemplate
-- ----------------------------
DROP TABLE IF EXISTS `tbsmstemplate`;
CREATE TABLE `tbsmstemplate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `iType` int(2) DEFAULT NULL,
  `vcPhones` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '多个手机用 英文";"分隔\n            如：13000000008;1788888888;188777666',
  `vcTemplate` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `iState` int(2) DEFAULT NULL COMMENT '1 -- 有效；\n            2 -- 无效。',
  `dtModify` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tbsmstemplate
-- ----------------------------

-- ----------------------------
-- Table structure for tbsmswhitelist
-- ----------------------------
DROP TABLE IF EXISTS `tbsmswhitelist`;
CREATE TABLE `tbsmswhitelist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vcPhone` char(13) COLLATE utf8_bin DEFAULT NULL,
  `dtCreate` datetime DEFAULT NULL COMMENT '该条记录第一次创建的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tbsmswhitelist
-- ----------------------------

-- ----------------------------
-- Table structure for tbsuggest
-- ----------------------------
DROP TABLE IF EXISTS `tbsuggest`;
CREATE TABLE `tbsuggest` (
  `suggest_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(300) DEFAULT NULL COMMENT '公告内容',
  `contact_info` varchar(60) DEFAULT NULL COMMENT '用户联系方式',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '状态1有效0删除',
  PRIMARY KEY (`suggest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tbsuggest
-- ----------------------------

-- ----------------------------
-- Table structure for tbsystemparam
-- ----------------------------
DROP TABLE IF EXISTS `tbsystemparam`;
CREATE TABLE `tbsystemparam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vcParamName` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '系统参数的中文名称',
  `vcParamCode` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '系统参数代码。系统通过此字段来获取。一经采用，不能修改',
  `vcParamValue` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '具体的系统参数的值，允许平台管理用户修改。',
  `iValid` int(2) DEFAULT '1' COMMENT '是否有效。\n            1 -- 有效 （默认）\n            2 -- 无效',
  `dtModify` datetime DEFAULT NULL COMMENT '最后一次修改时间，每次修改需更改此字段',
  `dtCreate` datetime DEFAULT NULL COMMENT '该条记录第一次创建的时间。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统参数表';

-- ----------------------------
-- Records of tbsystemparam
-- ----------------------------
INSERT INTO `tbsystemparam` VALUES ('1', '系统所属环境(test=测试环境 ； online=在线)', 'sys_environment', 'online', '1', null, '2015-01-23 10:35:39');
INSERT INTO `tbsystemparam` VALUES ('2', '每日计息截至时间节点(如：下午4点为16)', 'sys_trading_day_hour_node', '16', '1', '2014-12-17 18:54:19', '2014-12-16 13:54:19');
INSERT INTO `tbsystemparam` VALUES ('3', '平台最低提现手续费金额', 'sys_min_cash_fee_amt', '3.00', '1', '2015-02-10 10:18:23', '2015-01-08 10:28:35');
INSERT INTO `tbsystemparam` VALUES ('4', '平台收取提现手续费费率', 'sys_cash_fee_rate', '0.003', '1', '2015-02-10 10:18:15', '2015-01-08 09:27:23');
INSERT INTO `tbsystemparam` VALUES ('5', '平台收取借款人提现手续费费率', 'sys_borrower_cash_fee_rate', '0.000', '1', '2015-04-22 09:58:57', '2015-03-16 16:57:34');
INSERT INTO `tbsystemparam` VALUES ('8', '上传资料_方式(ftp=通过ftp上传，local=存放本机)', 'upload_file_type', 'local', '1', '2015-02-25 14:00:20', null);
INSERT INTO `tbsystemparam` VALUES ('9', '上传资料_本地存储目录', 'upload_local_path', '/opt/file/upload/', '1', '2015-02-25 11:26:47', null);
INSERT INTO `tbsystemparam` VALUES ('10', '上传资料__网站访问路径', 'upload_file_path', '/upload', '1', '2015-02-12 15:42:49', '2015-01-08 09:16:13');
INSERT INTO `tbsystemparam` VALUES ('11', '上传资料__服务器地址', 'upload_server_ip', '127.0.0.1', '1', '2015-12-10 13:46:57', '2015-01-27 14:24:43');
INSERT INTO `tbsystemparam` VALUES ('12', '上传资料__服务器端口', 'upload_server_port', '21', '1', null, '2015-01-27 14:25:07');
INSERT INTO `tbsystemparam` VALUES ('13', '上传资料__服务器用户名', 'upload_server_username', 'ftp', '1', null, '2015-01-27 14:25:28');
INSERT INTO `tbsystemparam` VALUES ('14', '上传资料__服务器密码', 'upload_server_password', '123456', '1', '2015-01-28 08:57:12', '2015-01-27 14:25:45');
INSERT INTO `tbsystemparam` VALUES ('15', '邮件发件__服务器地址', 'mail_send_server_host', 'smtp.exmail.qq.com', '1', null, '2015-01-13 16:49:01');
INSERT INTO `tbsystemparam` VALUES ('16', '邮件发件__服务器端口', 'mail_send_server_port', '25', '1', null, '2015-01-13 16:52:33');
INSERT INTO `tbsystemparam` VALUES ('17', '邮件发件__邮箱登录账号', 'mail_send_user_login_account', 'admin@tzg.cn', '1', '2015-01-20 13:02:43', '2015-01-13 16:54:20');
INSERT INTO `tbsystemparam` VALUES ('18', '邮件发件__邮箱登录密码', 'mail_send_user_login_pwd', 'tzg2014', '1', '2015-01-20 13:02:54', '2015-01-13 16:58:12');
INSERT INTO `tbsystemparam` VALUES ('19', '短信__SN', 'sms_sn', 'SDK-WSS-010-06839', '1', null, '2015-01-27 08:23:14');
INSERT INTO `tbsystemparam` VALUES ('20', '短信__PWD', 'sms_pwd', '53cbA67e', '1', null, '2015-01-27 08:23:34');
INSERT INTO `tbsystemparam` VALUES ('21', '短信__MTURL', 'sms_mturl', 'http://sdk.entinfo.cn:8061/webservice.asmx/mdsmssend', '1', null, '2015-01-27 08:24:03');
INSERT INTO `tbsystemparam` VALUES ('22', '实名认证_用户名', 'IDCARD_USERNAME', 'tzg_admin', '1', null, '2015-02-25 09:55:47');
INSERT INTO `tbsystemparam` VALUES ('23', '实名认证_密码', 'IDCARD_PASSWORD', 'q5p94lW5', '1', null, '2015-02-25 09:56:12');
INSERT INTO `tbsystemparam` VALUES ('24', 'zooKeeper服务器地址', 'sys_zooKeeper_server_ip', '192.168.1.7', '1', null, '2015-03-02 21:04:44');
INSERT INTO `tbsystemparam` VALUES ('25', '接收连连异步通知WAP地址', 'sys_llpay_notify_wap_url', 'http://122.224.156.194:83/callBack/charge', '1', null, '2015-03-12 14:28:27');
INSERT INTO `tbsystemparam` VALUES ('26', '接收连连异步通知WEB地址', 'sys_llpay_notify_web_url', 'http://122.224.156.194:1080/callBack/charge', '1', null, '2015-03-12 14:28:24');
INSERT INTO `tbsystemparam` VALUES ('28', '交易密码最大连续错误次数', 'sys_pay_password_error_num', '10', '1', '2015-11-03 09:38:06', '2015-03-19 21:02:48');
INSERT INTO `tbsystemparam` VALUES ('29', '静态资源服务器地址', 'sys_static_file_server_url', 'http://59.110.163.83:80', '1', null, null);
INSERT INTO `tbsystemparam` VALUES ('30', 'web访问地址', 'web_address', 'http://59.110.163.83:8085', '1', null, null);
INSERT INTO `tbsystemparam` VALUES ('31', 'wap访问地址', 'wap_address', 'http://59.110.163.83/tzg-wap', '1', '2015-04-30 15:26:28', '2015-04-30 15:26:31');
INSERT INTO `tbsystemparam` VALUES ('32', '富爸爸通知地址', 'fbaba_notify_url', 'http://www.fbaba.net/track/cps.php', '1', '2015-05-08 10:31:47', '2015-05-08 10:31:50');
INSERT INTO `tbsystemparam` VALUES ('33', '投资人最低提现金额(元)', 'sys_investor_min_cash_num', '1', '1', '2015-08-18 14:09:35', '2015-05-12 10:55:13');
INSERT INTO `tbsystemparam` VALUES ('34', '下载服务器地址', 'sys_download_server_url', 'http:/192.168.1.7:92', '1', '2015-05-21 09:55:29', '2015-05-21 09:55:31');
INSERT INTO `tbsystemparam` VALUES ('35', 'ios审核敏感内容开关', 'ios_audit_sensitive_switch', '0', '1', '2015-05-21 14:47:55', '2015-05-21 14:47:59');
INSERT INTO `tbsystemparam` VALUES ('36', '注册协议地址', 'registration_protocol_url', 'http://www.iiic.io/RegisteregProtocol.html', '1', '2015-05-21 16:22:12', '2015-05-21 14:48:04');
INSERT INTO `tbsystemparam` VALUES ('38', 'ios红包功能审核开关', 'ios_audit_red_switch', '1', '1', '2015-07-02 15:49:32', '2015-07-02 16:13:50');
INSERT INTO `tbsystemparam` VALUES ('39', '推动-appkey', 'appKey', '33df8f9ab3343333625b3f35', '1', '2015-07-02 15:49:35', '2015-07-02 11:45:39');
INSERT INTO `tbsystemparam` VALUES ('40', '推送-masterSecret', 'masterSecret', 'f1994a9710e26deef8a3b1c7', '1', '2015-07-02 15:49:38', '2015-07-02 11:46:04');
INSERT INTO `tbsystemparam` VALUES ('41', '建周短信__ACCOUNT', 'jzsms_account', 'sdk_hztongmi', '1', null, '2015-07-06 20:32:36');
INSERT INTO `tbsystemparam` VALUES ('42', '建周短信__PASSWORD', 'jzsms_password', '20150701', '1', null, '2015-07-06 20:32:36');
INSERT INTO `tbsystemparam` VALUES ('43', '建周短信__MTURL', 'jzsms_mturl', 'http://www.jianzhou.sh.cn/JianzhouSMSWSServer/http/sendBatchMessage', '1', null, '2015-07-06 20:32:36');
INSERT INTO `tbsystemparam` VALUES ('44', '短信渠道配置(1:老,2:建周)', 'sms_channel_type', '2', '1', null, '2015-07-07 18:28:08');
INSERT INTO `tbsystemparam` VALUES ('45', '建周营销短信__ACCOUNT', 'jzmsms_account', 'sdk_tzg888', '1', null, '2015-07-23 14:05:32');
INSERT INTO `tbsystemparam` VALUES ('46', '建周营销短信__PASSWORD', 'jzmsms_password', '20150701', '1', null, '2015-07-23 14:05:32');
INSERT INTO `tbsystemparam` VALUES ('47', '建周营销短信__MTURL', 'jzmsms_mturl', 'http://www.jianzhou.sh.cn/JianzhouSMSWSServer/http/sendBatchMessage', '1', null, '2015-07-23 14:05:32');
INSERT INTO `tbsystemparam` VALUES ('48', '短信渠道执行顺序', 'sms_channel_order', '6', '1', '2015-08-25 14:36:03', '2015-07-23 14:05:32');
INSERT INTO `tbsystemparam` VALUES ('49', '登陆同步私钥', 'sign_private_key', '12345', '1', null, '2015-07-23 18:27:40');
INSERT INTO `tbsystemparam` VALUES ('54', '验证码生命周期', 'validatecode_life', '1', '1', '2015-10-29 14:31:58', null);
INSERT INTO `tbsystemparam` VALUES ('55', '亿美验证码短信__ACCOUNT', 'ymsms_account', '9SDK-EMY-0999-JFRMN', '1', null, '2015-08-25 13:55:39');
INSERT INTO `tbsystemparam` VALUES ('56', '亿美验证码短信__PASSWORD', 'ymsms_password', '291790', '1', null, '2015-08-25 13:55:39');
INSERT INTO `tbsystemparam` VALUES ('57', '亿美验证码短信__MTURL', 'ymsms_mturl', 'http://sdk999ws.eucp.b2m.cn:8080/sdkproxy/sendsms.action', '1', null, '2015-08-25 13:55:39');
INSERT INTO `tbsystemparam` VALUES ('58', '亿美验证码短信状态报告__MTURL', 'ymsmsreport_mturl', 'http://sdk999ws.eucp.b2m.cn:8080/sdkproxy/getreport.action', '1', null, '2015-08-25 13:55:39');
INSERT INTO `tbsystemparam` VALUES ('59', '亿美营销短信__ACCOUNT', 'ymmsms_account', '6SDK-EMY-6688-JCWRS', '1', null, '2015-08-25 13:55:39');
INSERT INTO `tbsystemparam` VALUES ('60', '亿美营销短信__PASSWORD', 'ymmsms_password', '897870', '1', null, '2015-08-25 13:55:39');
INSERT INTO `tbsystemparam` VALUES ('61', '亿美营销短信__MTURL', 'ymmsms_mturl', 'http://sdk4report.eucp.b2m.cn:8080/sdkproxy/sendsms.action', '1', null, '2015-08-25 13:55:39');
INSERT INTO `tbsystemparam` VALUES ('62', '亿美营销短信状态报告__MTURL', 'ymmsmsreport_mturl', 'http://sdk4report.eucp.b2m.cn:8080/sdkproxy/getreport.action', '1', null, '2015-08-25 13:55:39');
INSERT INTO `tbsystemparam` VALUES ('63', '营销短信运营商__OPERATOR', 'msms_operator', '2', '1', null, '2015-08-25 13:55:39');
INSERT INTO `tbsystemparam` VALUES ('64', '易宝支付回调地址-web', 'sys_yeepay_callback_web_url', 'http://122.224.156.194:1080/callBack/yeepayCharge', '1', null, '2015-07-16 14:38:04');
INSERT INTO `tbsystemparam` VALUES ('65', '易宝支付回调地址-wap', 'sys_yeepay_callback_wap_url', 'http://122.224.156.194:83/callBack/yeepayCharge', '1', '2015-07-30 19:49:36', '2015-07-30 19:49:38');
INSERT INTO `tbsystemparam` VALUES ('66', '银行卡支付服务协议地址', 'bank_card_payment_protocol', 'http://192.168.1.7/BankCardPaymentProtocol.html', '1', null, '2015-08-14 16:29:34');
INSERT INTO `tbsystemparam` VALUES ('67', '微信分销返佣接口请求地址', 'disbackcommission_url', 'http://wx.tzg.cn/api/touzi.phptest', '1', null, '2015-09-01 15:49:30');
INSERT INTO `tbsystemparam` VALUES ('68', '点赞后台添加数量', 'add_praise_num', '29994', '1', '2015-09-17 19:30:08', '2015-09-17 19:30:08');
INSERT INTO `tbsystemparam` VALUES ('87', '红包使用时标的期限的适用天数', 'sys_red_use_apply_days', '15', '1', '2016-01-21 20:29:47', '2015-09-24 14:27:02');
INSERT INTO `tbsystemparam` VALUES ('88', '单标红包最大使用金额', 'sys_red_use_max_red_amount', '100', '1', '2015-12-31 12:33:57', '2015-10-10 09:38:55');
INSERT INTO `tbsystemparam` VALUES ('89', '微信注册用户绑卡分销返佣接口请求地址', 'disbackcommission_bkurl', 'http://wx.tzg.cn/api/binding.phptest', '1', null, '2015-10-09 18:55:38');
INSERT INTO `tbsystemparam` VALUES ('90', '微信注册用户投资分销返佣接口请求地址', 'disbackcommission_tzurl', 'http://wx.tzg.cn/api/touzi.phptest', '1', null, '2015-10-09 18:55:38');
INSERT INTO `tbsystemparam` VALUES ('92', '首页利率', 'index_rate', '11.88', '1', '2015-10-21 14:49:14', '2015-10-21 11:04:02');
INSERT INTO `tbsystemparam` VALUES ('93', '首页利率提示语', 'index_rate_msg', '新手专享', '1', '2015-10-21 14:49:07', '2015-10-21 11:21:01');
INSERT INTO `tbsystemparam` VALUES ('94', 'app资产收益轴显示开关', 'app_profit_axis_switch', '1', '1', null, null);
INSERT INTO `tbsystemparam` VALUES ('95', '新客红包数值', 'NEW_CUSTOMER_RED_AMOUNT', '20', '1', '2015-12-15 13:50:53', '2015-09-08 10:46:49');
INSERT INTO `tbsystemparam` VALUES ('96', '自动投标协议地址', 'autoinvest_protocol_url', 'http://192.168.1.7:90/upload/201505/RegisteregProtocol.html', '1', '2015-11-26 10:11:56', '2015-11-26 10:11:56');
INSERT INTO `tbsystemparam` VALUES ('97', 'active访问地址', 'act_address', 'http://192.168.1.7:85', '1', null, '2015-11-26 14:58:48');
INSERT INTO `tbsystemparam` VALUES ('98', '投资成功地址-h5开关', 'investsuccess_h5', 'off', '1', null, '2015-11-26 15:00:05');
INSERT INTO `tbsystemparam` VALUES ('99', '自动投标协议地址', 'autoinvest_protocol_url', 'http://192.168.1.7:90/upload/201505/RegisteregProtocol.html', '1', '2015-11-26 10:13:04', '2015-11-26 10:13:04');
INSERT INTO `tbsystemparam` VALUES ('100', '自动转入协议', 'autoTransferProtocol-web', 'http://192.168.1.7/autoTransferProtocol', '1', null, '2015-11-16 10:26:00');
INSERT INTO `tbsystemparam` VALUES ('101', '自动转入协议', 'autoTransferProtocol-wap', 'http://192.168.1.7:83/autoTransferProtocol', '1', null, null);
INSERT INTO `tbsystemparam` VALUES ('102', 'bbs新帖数量查询地址', 'bbs_new_psot_num', 'https://bbs.tzg.cn/api/todayposts.php', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('105', '人人赚活动红包文案', 'pyramid_promotion_slogan', '邀友越多 赚的越多', '1', '2015-12-13 18:54:47', '2015-12-13 18:54:47');
INSERT INTO `tbsystemparam` VALUES ('106', '铜钱宝app推荐页文案', 'currentbao_recommend_slogan', '1元起投，随存随取', '1', '2015-12-13 18:54:48', '2015-12-13 18:54:48');
INSERT INTO `tbsystemparam` VALUES ('107', '人人赚app推荐页文案', 'pyramid_recommend_slogan', '邀友越多赚越多', '1', '2015-12-13 18:54:48', '2015-12-13 18:54:48');
INSERT INTO `tbsystemparam` VALUES ('110', '掌柜吧app链接', 'bbs_app_url', 'http://192.168.1.17:81/forum.php?mod=guide&view=hot&mobile=2&source=app', '1', '2015-12-14 15:34:04', '2015-12-14 15:34:04');
INSERT INTO `tbsystemparam` VALUES ('111', '一级推荐人推荐奖励金5', 'one_recommended_award_5', '15', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('112', '二级推荐人推荐奖励金1', 'two_recommended_award_1', '5', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('113', '二级推荐人推荐奖励金2', 'two_recommended_award_2', '5', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('114', '二级推荐人推荐奖励金3', 'two_recommended_award_3', '5', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('115', '二级推荐人推荐奖励金4', 'two_recommended_award_4', '5', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('116', '二级推荐人推荐奖励金5', 'two_recommended_award_5', '5', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('117', '一级佣金（百分比）', 'one_commission', '5', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('118', '二级佣金（百分比）', 'two_commission', '2', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('119', '达人奖完成人数1', 'master_num1', '5', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('120', '达人奖完成人数2', 'master_num2', '10', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('121', '达人奖完成人数3', 'master_num3', '20', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('122', '达人奖完成人数4', 'master_num4', '50', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('123', '达人奖完成人数5', 'master_num5', '100', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('124', '达人奖奖金1', 'master_money1', '10', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('125', '达人奖奖金2', 'master_money2', '25', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('126', '达人奖奖金3', 'master_money3', '60', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('127', '达人奖奖金4', 'master_money4', '180', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('128', '达人奖奖金5', 'master_money5', '500', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('129', '达人奖奖金（超出最多，单个人金额）', 'master_money', '2', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('130', '达人奖达标金额', 'standard_amount', '1000', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('131', '人人赚参加人数（暂定）', 'pyramid_all_user_num', '53427', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('132', '人人赚总收益（暂定）', 'all_num_total_profit', '224685', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('133', '人人赚投资金额1', 'pyramid_invest_amount_1', '1000', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('134', '人人赚投资金额2', 'pyramid_invest_amount_2', '1000', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('135', '人人赚投资金额3', 'pyramid_invest_amount_3', '1000', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('136', '人人赚投资金额4', 'pyramid_invest_amount_4', '1000', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('137', '人人赚投资金额5', 'pyramid_invest_amount_5', '1000', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('138', '一级推荐人推荐奖励金1', 'one_recommended_award_1', '15', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('139', '一级推荐人推荐奖励金2', 'one_recommended_award_2', '15', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('140', '一级推荐人推荐奖励金3', 'one_recommended_award_3', '15', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('141', '一级推荐人推荐奖励金4', 'one_recommended_award_4', '15', '1', '2015-11-30 10:17:45', '2015-11-30 10:17:45');
INSERT INTO `tbsystemparam` VALUES ('142', '示远验证码短信__ACCOUNT', 'sysms_account', '002008', '1', null, '2015-12-15 20:39:28');
INSERT INTO `tbsystemparam` VALUES ('143', '示远验证码短信__PASSWORD', 'sysms_password', 'Sy123008', '1', null, '2015-12-15 20:39:28');
INSERT INTO `tbsystemparam` VALUES ('144', '示远验证码短信__MTURL', 'sysms_mturl', 'http://send.18sms.com/msg/HttpBatchSendSM', '1', null, '2015-12-15 20:39:28');
INSERT INTO `tbsystemparam` VALUES ('145', '示远营销短信__USERID', 'symsms_userid', '127', '1', null, '2015-12-15 20:39:28');
INSERT INTO `tbsystemparam` VALUES ('146', '示远营销短信__ACCOUNT', 'symsms_account', 'tongzhanggui', '1', null, '2015-12-15 20:39:28');
INSERT INTO `tbsystemparam` VALUES ('147', '示远营销短信__PASSWORD', 'symsms_password', '123456789', '1', null, '2015-12-15 20:39:28');
INSERT INTO `tbsystemparam` VALUES ('148', '示远营销短信__MTURL', 'symsms_mturl', 'http://121.43.107.8:8888/sms.aspx', '1', null, '2015-12-15 20:39:28');
INSERT INTO `tbsystemparam` VALUES ('149', '红包使用比例(正整数)', 'sys_red_use_ratio', '200', '1', '2015-12-15 23:52:20', '2015-12-15 23:52:20');
INSERT INTO `tbsystemparam` VALUES ('150', '开启自动投标时，线上剩余新客标可投金额总额', 'xkb_totolMoney', '200000', '1', '2015-12-25 14:08:05', '2015-12-25 14:08:05');
INSERT INTO `tbsystemparam` VALUES ('151', '开启自动投标时，线上剩余普通标可投金额总额', 'ptb_totolMoney', '1000000', '1', '2016-01-17 13:14:01', '2015-12-25 14:08:05');
INSERT INTO `tbsystemparam` VALUES ('152', '开启自动投标时，线上剩余新客标个数下限，会触发上标条件', 'xkb_sum', '3', '1', '2015-12-28 14:29:18', '2015-12-28 14:29:18');
INSERT INTO `tbsystemparam` VALUES ('153', '自动开标无库存时，发送短信的时间间隔，单位是分钟', 'kg_sendSmsTime', '2', '1', '2016-01-17 13:13:18', '2015-12-30 15:32:53');
INSERT INTO `tbsystemparam` VALUES ('154', '消息推送-人人赚开关', 'msg_pyramid', 'off', '1', null, '2016-01-27 18:09:57');
INSERT INTO `tbsystemparam` VALUES ('155', '消息推送-站内信开关', 'msg_message', 'off', '1', null, '2016-01-27 18:09:57');
INSERT INTO `tbsystemparam` VALUES ('156', '消息推送-活动开关', 'msg_activity', 'off', '1', null, '2016-01-27 18:09:57');
INSERT INTO `tbsystemparam` VALUES ('157', 'ios/android转入合同', 'autoTransferHeTong-wap', 'http://192.168.1.7:83/currentBao/currentBaoContract', '1', '2016-01-28 15:51:42', '2016-01-28 15:51:42');
INSERT INTO `tbsystemparam` VALUES ('159', '裂变二期红包申请标识', 'new_fission_vcAwardsAccountLogo', '1602003', '1', '2016-01-31 16:37:13', '2016-01-31 16:37:13');
INSERT INTO `tbsystemparam` VALUES ('160', '极光推送-开关', 'sys_push_environment', 'online', '1', null, '2016-02-03 14:30:56');
INSERT INTO `tbsystemparam` VALUES ('161', '阿里短信发送', 'alisms_accessKeyId', 'LTAIiFUsFH585KQA', '1', null, null);
INSERT INTO `tbsystemparam` VALUES ('162', '阿里短信发送', 'alisms_accessKeySecret', 'Xxasg3Vujek7pSoYBGMRnaotfnFlRL', '1', null, null);
INSERT INTO `tbsystemparam` VALUES ('163', '阿里短信发送', 'alisms_domain', 'dysmsapi.aliyuncs.com', '1', null, null);
INSERT INTO `tbsystemparam` VALUES ('164', '253云通信短信发送account', '253chuanglan_key', 'N160122_N7376197', '1', '2017-09-12 21:59:37', '2017-09-12 21:59:40');
INSERT INTO `tbsystemparam` VALUES ('165', '253云通信短信发送pwd', '253chuanglan_pwd', 'h0NDKrUFYH9ba8', '1', '2017-09-12 22:00:40', '2017-09-12 22:00:48');
INSERT INTO `tbsystemparam` VALUES ('166', '253云通信短信发送url', '253chuanglan_url', 'http://smssh1.253.com/msg/send/json', '1', '2017-09-12 22:01:42', '2017-09-12 22:01:44');
INSERT INTO `tbsystemparam` VALUES ('167', '开封府关于我们的url', 'kff_about_us_url', 'http://www.iiic.io/tzg-wap/aboutus', '1', '2018-04-19 22:17:27', '2018-04-19 22:17:30');
INSERT INTO `tbsystemparam` VALUES ('168', '开封府邀请好友的url', 'kff_invite_user_url', 'http://www.iiic.io/tzg-wap/invite', '1', '2018-04-19 22:19:20', '2018-04-19 22:19:23');

-- ----------------------------
-- Table structure for tbtokenaward
-- ----------------------------
DROP TABLE IF EXISTS `tbtokenaward`;
CREATE TABLE `tbtokenaward` (
  `token_award_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '奖励表主键',
  `user_id` int(11) NOT NULL,
  `token_records_id` int(11) DEFAULT NULL COMMENT '交易流水表',
  `praise_id` int(11) DEFAULT NULL COMMENT '点赞的表',
  `token_award_function_desc` varchar(30) DEFAULT NULL COMMENT '交易描述:0-充值1-评测奖励2-讨论奖励3-文章奖励4-榜单奖励5-用户赞赏6-注册奖励7-点赞奖励8-邀请好友奖励21-提现22-赞赏他人',
  `token_award_function_type` int(2) DEFAULT NULL COMMENT '10-充值11-评测奖励12-讨论奖励13-文章奖励14-榜单奖励15-用户赞赏16-注册奖励17-点赞奖励18-邀请好友奖励21-提现22-赞赏他人',
  `invite_rewards` double(11,0) NOT NULL DEFAULT '0' COMMENT '邀请奖励,线性发放,',
  `give_time` datetime DEFAULT NULL COMMENT '返还时间',
  `priaise_award` double(11,0) DEFAULT '0' COMMENT '点赞奖励',
  `container_award` double(11,0) DEFAULT '0' COMMENT '平台奖励',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `give_next` double(11,0) DEFAULT '0' COMMENT '返回结果专用一般为空',
  `reward_token` double(11,0) DEFAULT '0' COMMENT '奖励总数,一次性发放的奖励总数,包括充值的',
  `award_balance` double(11,0) NOT NULL DEFAULT '0' COMMENT '奖励余额,显示的是没有发放的邀请奖励',
  `invite_number` int(11) DEFAULT '0' COMMENT '邀请人数',
  `distribution_type` int(11) NOT NULL DEFAULT '0' COMMENT '发放类型 1代表线性发放 2代表一次性发放',
  `counter` int(10) DEFAULT '0' COMMENT '线性奖励发放的次数',
  `grant_type` int(3) DEFAULT '2' COMMENT '发放状态 1-今天已发放 2-未发放',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '用户手机号',
  `issuer` varchar(100) DEFAULT NULL COMMENT '后台发放人',
  `remark` varchar(100) DEFAULT NULL COMMENT '后台发放备注',
  PRIMARY KEY (`token_award_id`)
) ENGINE=InnoDB AUTO_INCREMENT=459 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbtokenaward
-- ----------------------------

-- ----------------------------
-- Table structure for tbtokenrecords
-- ----------------------------
DROP TABLE IF EXISTS `tbtokenrecords`;
CREATE TABLE `tbtokenrecords` (
  `token_records_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `trade_code` varchar(50) DEFAULT NULL COMMENT '交易流水号=交易类型（2位） 交易时间年月日（8位） 业务记录ID（10位）',
  `trade_type` int(1) DEFAULT NULL COMMENT '交易类型:1-收入；2-支出',
  `function_desc` varchar(30) DEFAULT NULL COMMENT '交易描述:0-充值1-评测奖励2-讨论奖励3-文章奖励4-榜单奖励5-用户赞赏6-注册奖励7-点赞奖励8-邀请好友奖励21-提现22-赞赏他人',
  `function_type` int(2) DEFAULT NULL COMMENT '10-充值11-评测奖励12-讨论奖励13-文章奖励14-榜单奖励15-用户赞赏16-注册奖励17-点赞奖励18-邀请好友奖励21-提现22-赞赏他人',
  `amount` double(11,0) DEFAULT NULL COMMENT 'token数量',
  `trade_date` datetime DEFAULT NULL,
  `balance` int(11) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  `memo` varchar(50) DEFAULT NULL COMMENT '备注',
  `reward_grant_type` int(20) DEFAULT NULL COMMENT '发放类型 1-一次性发放 2-线性发放',
  PRIMARY KEY (`token_records_id`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=367 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbtokenrecords
-- ----------------------------

-- ----------------------------
-- Table structure for tbuploadfile
-- ----------------------------
DROP TABLE IF EXISTS `tbuploadfile`;
CREATE TABLE `tbuploadfile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `iType` int(2) DEFAULT NULL COMMENT '0-未分类图片 1 -- 合同模板；\n            2 -- 风控附件（PC适用）；\n            3 -- 焦点图；\n            4 -- 保障措施图片；\n            5 -- 风控附件（非PC适用）；\n            6-- 风控清单；\n            7--合作机构Logo；\n            8--媒体报道Logo；\n9--新闻动态副图。\n            ',
  `vcName` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `vcOrnName` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `vcUrl` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '存放地址+新的文件名\n            文件名规则：20150117162350******.后缀\n            4位年 2位月 2位日期 2位小时 2位分  2位秒 6位随机数',
  `blobSource` longblob,
  `iState` int(2) DEFAULT NULL COMMENT '1 -- 未同步；\n            2 -- 已同步。',
  `dtCreate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44974 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tbuploadfile
-- ----------------------------

-- ----------------------------
-- Table structure for tbuser
-- ----------------------------
DROP TABLE IF EXISTS `tbuser`;
CREATE TABLE `tbuser` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL COMMENT '昵称',
  `sex` int(1) NOT NULL DEFAULT '1' COMMENT '性别:1男；2女',
  `icon` varchar(100) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(11) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `wechat` varchar(36) DEFAULT NULL COMMENT '微信号',
  `password` varchar(255) DEFAULT NULL,
  `pay_password` varchar(255) DEFAULT NULL,
  `user_type` int(1) NOT NULL DEFAULT '1' COMMENT '用户类型:1-普通用户；2-项目方；3-评测机构；4-机构用户',
  `user_degree` int(1) NOT NULL DEFAULT '1' COMMENT '预留字段 用户等级：1-普通用户；2-高级用户;3-VIP',
  `user_signature` varchar(30) DEFAULT NULL COMMENT '个人简介',
  `fans_num` int(11) NOT NULL DEFAULT '0' COMMENT '粉丝数量',
  `praise_num` int(11) NOT NULL DEFAULT '0' COMMENT '收获点赞数量',
  `evaluation_num` int(11) NOT NULL DEFAULT '0' COMMENT '发表评测数量',
  `discuss_num` int(11) NOT NULL DEFAULT '0' COMMENT '发表讨论数量',
  `article_num` int(11) DEFAULT '0' COMMENT '发表文章数量',
  `kff_coin_num` double(11,0) DEFAULT '0' COMMENT '账号币值',
  `area_name` varchar(30) DEFAULT NULL COMMENT '所在地区域名称:浙江省杭州市',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `status` int(1) DEFAULT '1' COMMENT '状态：0-删除；1-有效',
  `memo` varchar(50) DEFAULT NULL COMMENT '备注信息',
  `province_code` varchar(2) DEFAULT NULL COMMENT '省编码',
  `city_code` varchar(4) DEFAULT NULL COMMENT '城市编码',
  `area_code` varchar(10) DEFAULT NULL,
  `refer_user_id` int(11) DEFAULT NULL,
  `refer_level` int(1) DEFAULT '0' COMMENT '有无推荐人:0-没有推荐人;1-有推荐人',
  `last_login_dateTime` datetime DEFAULT NULL COMMENT '最后登录时间字段',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_idx_mobile` (`mobile`) USING BTREE,
  UNIQUE KEY `uq_idx_username` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=538 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbuser
-- ----------------------------

-- ----------------------------
-- Table structure for tbusercard
-- ----------------------------
DROP TABLE IF EXISTS `tbusercard`;
CREATE TABLE `tbusercard` (
  `cardID` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `userRealName` varchar(10) DEFAULT NULL,
  `userCardNum` varchar(100) DEFAULT NULL,
  `positiveOfCard` varchar(500) DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '1  待审核  2   审核通过  3   未通过审核  4   未提交   身份验证  和账号验证的审核状态  ',
  `createTime` datetime DEFAULT NULL,
  `updataTime` datetime DEFAULT NULL,
  `valid` int(1) DEFAULT NULL COMMENT '1 有效    0  无效',
  `phone` varchar(15) DEFAULT NULL,
  `notPassReason` varchar(100) DEFAULT NULL COMMENT '没有通过原因',
  PRIMARY KEY (`cardID`),
  KEY `b_key` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=255 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbusercard
-- ----------------------------

-- ----------------------------
-- Table structure for tbuserinvation
-- ----------------------------
DROP TABLE IF EXISTS `tbuserinvation`;
CREATE TABLE `tbuserinvation` (
  `userInvationId` int(11) NOT NULL AUTO_INCREMENT,
  `user2Code` varchar(10) DEFAULT NULL COMMENT '二维码字符',
  `user2CodePic` varchar(200) DEFAULT NULL COMMENT '二维码存放地址',
  `userPosterPic` varchar(200) DEFAULT NULL COMMENT 'user海报存放地址',
  `status` int(1) DEFAULT NULL COMMENT '状态     1 有效        0   无效',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `userId` int(11) DEFAULT NULL COMMENT 'userId',
  PRIMARY KEY (`userInvationId`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbuserinvation
-- ----------------------------

-- ----------------------------
-- Table structure for tbuserqfindex
-- ----------------------------
DROP TABLE IF EXISTS `tbuserqfindex`;
CREATE TABLE `tbuserqfindex` (
  `qfindex_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `total_index` int(4) NOT NULL DEFAULT '1' COMMENT '总区分指数',
  `content_weight` int(2) DEFAULT '0' COMMENT '内容贡献权重，百分比数字20，代表20%',
  `locktoken_weight` int(2) NOT NULL DEFAULT '0' COMMENT '锁仓权重',
  `active_weight` int(2) DEFAULT '0' COMMENT '活跃权重',
  `influence_weight` int(2) DEFAULT '0' COMMENT '影响力权重',
  `community_weight` int(2) DEFAULT '0' COMMENT '社区考核权重',
  `healthy_weight` int(2) DEFAULT '0' COMMENT '健康度权重',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `status` int(1) DEFAULT '1' COMMENT '状态：0-删除；1-有效',
  `memo` varchar(50) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`qfindex_id`),
  UNIQUE KEY `uk_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbuserqfindex
-- ----------------------------

-- ----------------------------
-- Table structure for tbuserwallet
-- ----------------------------
DROP TABLE IF EXISTS `tbuserwallet`;
CREATE TABLE `tbuserwallet` (
  `user_wallet_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '钱包表id',
  `user_id` int(11) NOT NULL COMMENT '用户的id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户昵称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '用户手机号',
  `createtime` datetime DEFAULT NULL COMMENT '绑定时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更换时间',
  `wallet` varchar(64) DEFAULT NULL COMMENT '钱包地址',
  `wallet_type` int(2) DEFAULT '0' COMMENT '钱包状态0-未绑定  1-已绑定',
  PRIMARY KEY (`user_wallet_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbuserwallet
-- ----------------------------
