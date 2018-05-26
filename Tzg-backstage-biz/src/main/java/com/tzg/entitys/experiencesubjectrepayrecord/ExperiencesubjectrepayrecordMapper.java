package com.tzg.entitys.experiencesubjectrepayrecord;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface ExperiencesubjectrepayrecordMapper extends BaseMapper<Experiencesubjectrepayrecord, java.lang.Integer> {

    /**
     * 按投资状态查已投资金额、待收利息
     * 
     * @param map
     * @return
     */
    public List<InvestIncomeVO> statInvestIncome(Map<String, String> map);

    /**
     * 需立即回款的记录
     * 
     * @return
     */
    public List<Experiencesubjectrepayrecord> findRepayNow();

    /**
     * 根据状态统计回款金额
     * 
     * @param map
     * @return
     */
    public List<RepayStatVO> statRepayByState(Map<String, Object> map);
}
