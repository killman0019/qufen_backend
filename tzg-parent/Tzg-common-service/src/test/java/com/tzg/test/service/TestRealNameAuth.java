//package com.tzg.test.service;
//
//import org.datacontract.schemas._2004._07.finance_epm.IdentifierData;
//
//import cn.com.nciic.www.SimpleCheckByJson;
//import cn.com.nciic.www.SimpleCheckByJsonResponse;
//import cn.com.nciic.www.service.IdentifierServiceStub;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.tzg.common.utils.Assert;
//
//public class TestRealNameAuth {
//
//	public static void main(String[] args) {
//		System.out.println(simpleCheckByJson("370102197706112914","张伟"));
//	}
//
//	public static String simpleCheckByJson(String idCardNum, String realName){
//		try {
//
//			Assert.isBlank(idCardNum, "身份证号码不能为空");
//			Assert.isBlank(realName, "姓名不能为空");
//			String req = String.format("{\"IDNumber\":\"%s\",\"Name\":\"%s\"}", idCardNum, realName);
//			String cred = String.format("{\"UserName\":\"%s\",\"Password\":\"%s\"}", "tzg_admin", "q5p94lW5");
//			IdentifierServiceStub client = new IdentifierServiceStub();
//			SimpleCheckByJson scbj = new SimpleCheckByJson();
//			scbj.setCred(cred);
//			scbj.setRequest(req);
//			SimpleCheckByJsonResponse scbr = client.simpleCheckByJson(scbj);
//			String result = scbr.getSimpleCheckByJsonResult();
//			Assert.isBlank(result, "请求失败");
//			JSONObject jsonObject = JSON.parseObject(result);
//			if(jsonObject.get("ResponseCode").toString().equals("100")){
//				IdentifierData identifier = JSON.parseObject(jsonObject.get("Identifier").toString(),IdentifierData.class);
//				if(identifier.getResult().equals("一致")){
//					return "SUCCESS";
//				}
//				return "FAILURE";
//			}
//			return "ERROR";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "ERROR";
//		}
//	}
//
//}