package com.tzg.entitys.smssend;

import com.tzg.entitys.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmssendMapper extends BaseMapper<Smssend, java.lang.Integer> {	

     public List<Smssend> getAllByType(Integer type);

}
