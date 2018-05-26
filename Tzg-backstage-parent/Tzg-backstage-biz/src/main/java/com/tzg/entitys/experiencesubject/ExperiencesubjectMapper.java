package com.tzg.entitys.experiencesubject;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;
import com.tzg.entitys.subject.SubjectCountVO;

@Repository
public interface ExperiencesubjectMapper extends BaseMapper<Experiencesubject, java.lang.Integer> {

    SubjectCountVO findSubjectListInStateCount(Map<String, String> map);

    List<Experiencesubject> findSubjectListInState(Map<String, String> map);

    List<Experiencesubject> FindPageInState(Map<String, String> map);

    List<Experiencesubject> findSubjectByState(Integer state);

    /**
     * 需要待开标的标的
     * 
     * @return
     */
    List<Experiencesubject> findState5();

    /**
     * 需要开标的
     * 
     * @return
     */
    List<Experiencesubject> findState10();

    /**
     * 标的募集完毕
     * 
     * @return
     */
    void finishState11();
}
