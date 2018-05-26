package com.tzg.service.user;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;











import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.SHAUtil;
import com.tzg.entitys.user.User;
import com.tzg.entitys.user.UserMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class UserService extends BaseService {

	@Autowired
	private UserMapper userMapper;	
	   
	@Transactional(readOnly=true)
    public User findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return userMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
    	User user = new User();
    	user.setUserId(id);
    	user.setStatus(0);
    	user.setUpdateTime(new Date());
        userMapper.update(user);
    }
	
	public void save(User user) throws Exception {	
		Date now = new Date();
		user.setCreateTime(now);
		user.setUpdateTime(now);
		if(user.getUserDegree() == null){
			user.setUserDegree(1);
		}
		user.setStatus(1);
		if(user.getDiscussNum() == null){
			user.setDiscussNum(0);
		}
		if(user.getEvaluationNum() == null){
			user.setEvaluationNum(0);
		}
		if(user.getFansNum() == null){
			user.setFansNum(0);
		}
		if(user.getKffCoinNum() == null){
			user.setKffCoinNum(0.000);
		}
		if(user.getPraiseNum() == null){
			user.setPraiseNum(0);
		}
		if(user.getUserType()==null){
			user.setUserType(1);
		}
		if(user.getSex() == null){
			user.setSex(1);
		}
		if(user.getSex() != 1 && user.getSex() != 2){
			throw new Exception("性别只能是男或者女");
		}
		
		if(user.getMobile()==null){
			throw new Exception("手机号码为必填项");
		}
		
		if(user.getMobile()==null){
			throw new Exception("手机号码为必填项");
		}
		String phonefmt = RegexUtil.PHONEREGEX;
		if(!user.getMobile().matches(phonefmt)){
			throw new Exception("请填入正确的手机号");
		}
		if(StringUtils.isNotBlank(user.getPassword())){
			user.setPassword(SHAUtil.encode(user.getPassword()));
		}
		userMapper.save(user);
		
	}
	
	public void update(User user) throws Exception {	
		if(user.getUserId() == null){
			throw new Exception("id不能为空");
		}
		if(StringUtils.isNotBlank(user.getMemo()) && user.getMemo().length() > 50){
			throw new Exception("备注信息不能超过50字");
		}
		
		if(user.getUserType() == null){
			throw new Exception("用户类型不能为空");
		}
		
		if(user.getUserType() != 1 &&user.getUserType() != 2 &&user.getUserType() != 3 &&user.getUserType() != 4 ){
			throw new Exception("用户类型不合法");
		}
		if(user.getSex() == null){
			throw new Exception("用户性别不能为空");
		}
		if(user.getSex() != 1 &&user.getSex() != 2){
			throw new Exception("用户性别不合法");
		}
		
		userMapper.update(user);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<User> findPage(PaginationQuery query) throws Exception {
		PageResult<User> result = null;
		try {
			Integer count = userMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<User> list = userMapper.findPage(query.getQueryData());
				result = new PageResult<User>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void active(Integer id)  throws Exception {
		if(id == null){
			throw new Exception("id不能为空");
		}
    	User user = new User();
    	user.setUserId(id);
    	user.setStatus(1);
    	user.setUpdateTime(new Date());
        userMapper.update(user);
		
	}

	public User findByMobile(String mobile) {
		return userMapper.findByMobile(mobile);
	}
	

	
}
