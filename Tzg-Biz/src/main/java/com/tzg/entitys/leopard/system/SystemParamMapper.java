package com.tzg.entitys.leopard.system;

import org.springframework.stereotype.Component;

import com.tzg.common.base.BaseMapper;

@Component
public interface SystemParamMapper extends BaseMapper<SystemParam, Integer> {
    
    SystemParam findByCode(String vcParamCode);
}
