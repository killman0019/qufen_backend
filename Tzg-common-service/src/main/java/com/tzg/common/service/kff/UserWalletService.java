package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.bouncycastle.jce.provider.symmetric.AES.OFB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.tokenaward.Tokenaward;
import com.tzg.entitys.kff.tokenaward.TokenawardMapper;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.tokenrecords.TokenrecordsMapper;
import com.tzg.entitys.kff.userwallet.KFFUserWallet;
import com.tzg.entitys.kff.userwallet.KFFUserWalletMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="UserWalletService")
@Transactional
public class UserWalletService {

	@Autowired
	private KFFUserWalletMapper kffUserWalletMapper;	
	   
	@Transactional(readOnly=true)
    public void findByUserId(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return;
    }
	
	public KFFUserWallet findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return kffUserWalletMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
    	kffUserWalletMapper.deleteById(id);
    }
	
	public void save(KFFUserWallet kffUserWallet) throws RestServiceException {	    
		kffUserWalletMapper.save(kffUserWallet);
	}
	
	public void update(KFFUserWallet kffUserWallet) throws RestServiceException {	
		if(kffUserWallet.getUserWalletId() == null){
			throw new RestServiceException("id不能为空");
		}
		kffUserWalletMapper.update(kffUserWallet);
	}	
	public void updateWallet(KFFUserWallet kffUserWallet) throws RestServiceException {	
		if(kffUserWallet.getUserWalletId() == null){
			throw new RestServiceException("id不能为空");
		}
		kffUserWalletMapper.updateWallet(kffUserWallet);
	}	
	public KFFUserWallet findbyWallet(KFFUserWallet kffUserWallet) throws RestServiceException {	
		if(kffUserWallet.getUserId() == null){
			throw new RestServiceException("userId不能为空");
		}
		if(kffUserWallet.getWallet() == null){
			throw new RestServiceException("钱包地址不能为空");
		}
		return kffUserWalletMapper.findbyWallet(kffUserWallet);
	}	
	/*@Transactional(readOnly=true)
	public PageResult<Tokenaward> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Tokenaward> result = null;
		try {
			Integer count = tokenawardMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Tokenrecords> list = tokenawardMapper.findPage(query.getQueryData());
				result = new PageResult<Tokenaward>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional(readOnly=true)
	public int findUserSumRewardToken(java.lang.Integer userId) throws RestServiceException{
		
		return tokenawardMapper.findUserSumRewardToken();
	}
	*/
}
