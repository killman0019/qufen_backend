package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.bouncycastle.jce.provider.symmetric.AES.OFB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.sql.parser.Token;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.tokenaward.Tokenaward;
import com.tzg.entitys.kff.tokenaward.TokenawardMapper;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.tokenrecords.TokenrecordsMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFTokenawardService")
@Transactional
public class TokenawardService {

	@Autowired
	private TokenawardMapper tokenawardMapper;	
	   
	@Transactional(readOnly=true)
    public List<Tokenaward> findByUserId(Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return tokenawardMapper.findByUserId(id);
    }
	@Transactional(readOnly=true)
	public List<Tokenaward> priaiseAwardSum(java.lang.Integer userId) throws RestServiceException {
		if(userId == null){
			throw new RestServiceException("id不能为空");
		}
		return tokenawardMapper.priaiseAwardSum(userId);
	}
	
	public Tokenaward findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return tokenawardMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
    	tokenawardMapper.deleteById(id);
    }
	
	public void save(Tokenaward tokenaward) throws RestServiceException {	    
		tokenawardMapper.save(tokenaward);
	}
	
	public void update(Tokenaward tokenaward) throws RestServiceException {	
		if(tokenaward.getUserId() == null){
			throw new RestServiceException("userid不能为空");
		}
		tokenawardMapper.update(tokenaward);
	}	
	public Double reawardSum(Integer userId,Integer functionType) throws RestServiceException {	
		if(userId == null || functionType == null){
			throw new RestServiceException("传入参数不能为空");
		}
		return tokenawardMapper.reawardSum(userId,functionType);
	}	
	public Double findUserSumInviteRewards(Integer userId,Integer functionType) throws RestServiceException {	
		if(userId == null || functionType == null){
			throw new RestServiceException("传入参数不能为空");
		}
		return tokenawardMapper.findUserSumInviteRewards(userId,functionType);
	}	
	public Double findUserSumRewardToken(Integer userId,Integer functionType) throws RestServiceException {	
		if(userId == null || functionType == null){
			throw new RestServiceException("传入参数不能为空");
		}
		return tokenawardMapper.findUserSumRewardToken(userId,functionType);
	}
	public  List<Tokenaward> findAllTokenawardByWhere(Map<String, Object> map) {
	
		return tokenawardMapper.findAllTokenawardByWhere (map);
	}
	public void updateByGrantType(Integer a) {

		tokenawardMapper.updateByGrantType(a);
	}
	/**
	 * 根据token_award_function_type=18   userID   获取invite_rewards 的值 
	 * @param userId
	 * @return
	 */
	public List<Tokenaward> selectInvationAward(Integer userId) {
		
		return tokenawardMapper.selectInvationAward(userId);
	}
	
	public Tokenaward findPraiseAwardByType(Integer createUserId) {
		return tokenawardMapper.findPraiseAwardByType(createUserId);
		
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Tokenaward> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Tokenaward> result = null;
		try {
			Integer count = tokenawardMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Tokenaward> list = tokenawardMapper.findPage(query.getQueryData());
				result = new PageResult<Tokenaward>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
/*
	@Transactional(readOnly=true)
	public int findUserSumRewardToken(java.lang.Integer userId) throws RestServiceException{
		
		return tokenawardMapper.findUserSumRewardToken();
	}*/
	public List<Tokenaward> findAllTokenawardUserId(Integer userId) {
		// TODO Auto-generated method stub
		return tokenawardMapper.findAllTokenawardUserId(userId);
	}
	
}
