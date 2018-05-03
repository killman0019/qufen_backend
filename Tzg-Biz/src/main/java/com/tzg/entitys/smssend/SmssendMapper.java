package com.tzg.entitys.smssend;

import com.tzg.common.base.BaseMapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SmssendMapper extends BaseMapper<Smssend, java.lang.Integer> {	

     public List<Smssend> getAllByType(Integer type);
     /**
      * Description: 根据短信内容查询短信记录
      * @author:     linjie 
      * Create at:   2015年12月30日 上午10:29:46  
      * @param param
      * @return
      */
     public Integer getSendSmsByContent(Map<String,String> param);

}
