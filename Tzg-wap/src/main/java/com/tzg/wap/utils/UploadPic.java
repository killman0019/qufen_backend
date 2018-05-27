package com.tzg.wap.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.entitys.photo.PhotoIview;
import com.tzg.entitys.photo.PhotoParams;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;

public class UploadPic {
	public static JSON uploadIeviw(ArrayList<PhotoIview> arrayLists) {
		if (null == arrayLists) {
			throw new RestServiceException(RestErrorCode.PICTURE_UPLOAD_FAIL);
		}
		
		// 吧前台参数传输给后台
		// 创建图片参数对象,用于存放photo参数
		List<PhotoParams> lParams = new ArrayList<PhotoParams>();

		for (PhotoIview arrayList : arrayLists) {
			PhotoParams photoParams = new PhotoParams();
			String[] str = arrayList.getName().split(".");
			photoParams.setFileName(str[0]);
			photoParams.setExtension("." + str[1]);
			photoParams.setFileUrl(arrayList.getUrl());
			lParams.add(photoParams);
		}

		return (JSON) JSON.toJSON(lParams);
	}

}
