package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.entitys.kff.activity.PostShare;
import com.tzg.entitys.kff.activity.PostShareMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
@Transactional
public class PostShareService {

	@Autowired
	private PostShareMapper postShareMapper;

	@Transactional(readOnly = true)
	public PostShare findById(Integer id) throws RestServiceException {
		return postShareMapper.findById(id);
	}


	public void save(PostShare postShare) throws RestServiceException {
		postShareMapper.save(postShare);
	}

	public void update(PostShare postShare) throws RestServiceException {
		postShareMapper.update(postShare);
	}

	public List<PostShare> findListByAttr(Map<String, Object> map) {
		return postShareMapper.findListByAttr(map);
	}

}
