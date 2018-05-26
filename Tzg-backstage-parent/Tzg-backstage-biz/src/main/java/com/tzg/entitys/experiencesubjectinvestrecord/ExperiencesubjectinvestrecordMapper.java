package com.tzg.entitys.experiencesubjectinvestrecord;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface ExperiencesubjectinvestrecordMapper extends BaseMapper<Experiencesubjectinvestrecord, java.lang.Integer> {

    public int countInvestRecord(Map<String, Object> map);

    /**
     * 根据subjectid更新状态
     * 
     * @param map (istate,isubjectid)
     */
    public void updateStateBySubjectId(Map<String, Object> map);

    /**
     * 根据标的主键ID计算收益
     * 
     * @param map
     * @return
     */
    public BigDecimal statProfit(Map<String, Object> map);
}
