package com.tzg.service.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.message.Message;
import com.tzg.entitys.message.MessageMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class MessageService extends BaseService {

	@Autowired
	private MessageMapper messageMapper;	
	   
	@Transactional(readOnly=true)
    public Message findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return messageMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        messageMapper.deleteById(id);
    }
	
	public void save(Message message) throws Exception {	    
		messageMapper.save(message);
	}
	
	public void update(Message message) throws Exception {	
		if(message.getId() == null){
			throw new Exception("id不能为空");
		}
		messageMapper.update(message);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Message> findPage(PaginationQuery query) throws Exception {
		PageResult<Message> result = null;
		try {
			Integer count = messageMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Message> list = messageMapper.findPage(query.getQueryData());
				result = new PageResult<Message>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
