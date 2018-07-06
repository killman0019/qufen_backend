package com.tzg.entitys.kff.newsFlashImg;

import com.tzg.common.base.BaseMapper;

public interface KFFNewsFlashImgMapper extends BaseMapper<KFFNewsFlashImg, Integer>{
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(SystemNewsFlashImg record);
//
//    int insertSelective(SystemNewsFlashImg record);

    KFFNewsFlashImg selectByPrimaryKey(Integer id);

//    int updateByPrimaryKeySelective(SystemNewsFlashImg record);
//
//    int updateByPrimaryKey(SystemNewsFlashImg record);
}