package com.tzg.entitys.subject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface SubjectMapper extends BaseMapper<Subject, java.lang.Integer> {

	/**
	 * 某项目下标的的募集总额,用于添加标的时
	 */
	BigDecimal totalNumTotalFinancing(Integer iProjectID);

	List<Subject> findSubjectByState(int state);
	
	/**
	 * 需要待开标的标的
	 * @return
	 */
	List<Subject> findState5();
	
	/**
	 * 需要开标的
	 * @return
	 */
	List<Subject> findState10();
	
	/**
	 * 标的募集完毕
	 * @return
	 */
	void finishState11();

	void updateSubjectState(Subject subject);

	SubjectCountVO findSubjectListInStateCount(Map<String, String> map);

	List<Subject> findSubjectListInState(Map<String, String> map);

	/**
	 * 投资完成更新标的 实际募集金额
	 * 
	 * @param map
	 */
	void updateInvestAmt(Map<String, Object> map);

	/**
	 * 根据标的ID，获取项目名称
	 * 
	 * @param subjectId
	 * @return
	 */
	String findProjectNameBySubjectId(Integer subjectId);

	/**
	 * 根据ID，查询标的关联项目
	 * 
	 * @param subjectId
	 * @return
	 */
	Subject findSubjectAsProjectBySubjectId(Integer subjectId);

	/**
	 * 
	 * @param iProjectID
	 * @return
	 * @throws Exception
	 */
	public List<Subject> querySubjectByiProjectID(Map<String, Object> map)
			throws Exception;
	
	/**
	 * 根据标的名称，查询记录
	 * @param map
	 * @return
	 */
	public Subject querySubjectByVcName(Map<String, Object> map);

	public List<Subject> findSubjectByitype(Map<String, String> queryData);

	/**
	 * app推送标的
	 * @param map
	 * @return
	 */
	public List<Subject> findSubjectByNumRate(Map<String, String> queryData);

	public String findVcNameById(int id);
	
	public void updateSubject(Subject subject);
	/**
	 * @Description: 查询期限等级列表
	 * @author 作者 林洁
	 * @version 创建时间：2015年12月10日上午11:52:34
	 * @return
	 */
	public List<Subject> findSubGrade();
	/**
	 * Description: 自动开标标的下架
	 * @author:     linjie 
	 * Create at:   2016年1月15日 上午10:10:15  
	 * @param subject
	 */
	public void downSubject(Subject subject);
}
