package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.message.KFFMessage;
import com.tzg.entitys.kff.message.KFFMessageMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFMessageService")
@Transactional
public class MessageService {

	@Autowired
	private KFFMessageMapper messageMapper;

	@Transactional(readOnly = true)
	public KFFMessage findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return messageMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		messageMapper.deleteById(id);
	}

	public Integer save(KFFMessage message) throws RestServiceException {
		return messageMapper.save(message);
	}

	public void update(KFFMessage message) throws RestServiceException {
		if (message.getMessageId() == null) {
			throw new RestServiceException("id不能为空");
		}
		messageMapper.update(message);
	}

	@Transactional(readOnly = true)
	public PageResult<KFFMessage> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<KFFMessage> result = null;
		try {
			Integer count = messageMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<KFFMessage> list = messageMapper.findPage(query.getQueryData());
				result = new PageResult<KFFMessage>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void deleteAllMessages(Integer userId) throws RestServiceException {
		if (userId == null) {
			throw new RestServiceException("用户id不能为空");
		}

		messageMapper.deleteAllMessages(userId);
	}

	public void updateAllMessageRead(Integer userId) {
		if (userId == null) {
			throw new RestServiceException("用户id不能为空");
		}
		messageMapper.updateAllMessageRead(userId);

	}

}
