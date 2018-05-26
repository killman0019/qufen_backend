package com.tzg.entitys.subjectplan;

import com.tzg.entitys.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectplanMapper extends BaseMapper<Subjectplan, java.lang.Integer> {
    public void savePlanList(List<Subjectplan> subjectplanList);

}
