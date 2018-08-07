package com.tzg.entitys.kff.app;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;


public interface NewsFlashMapper extends BaseMapper<NewsFlash, Integer> {
//    int deleteByPrimaryKey(Integer id);

    NewsFlash selectById(Integer id);

    List<Integer> findListByAttr(Map<String,Object> map);

    int updateByMap(Map<String,Object> map);
}