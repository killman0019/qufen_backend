package com.tzg.entitys.leopard.system;

import org.springframework.stereotype.Component;

import com.tzg.entitys.BaseMapper;

@Component
public interface SystemParamMapper extends BaseMapper<SystemParam, Integer> {
    
    SystemParam findByCode(String vcParamCode);
}
