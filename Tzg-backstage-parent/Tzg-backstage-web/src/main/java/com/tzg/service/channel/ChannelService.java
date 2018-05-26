package com.tzg.service.channel;

import java.util.Date;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.channel.Channel;
import com.tzg.entitys.channel.ChannelMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class ChannelService extends BaseService {

	@Autowired
	private ChannelMapper channelMapper;

	public Channel findById(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		return channelMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		channelMapper.deleteById(id);
	}

	public void save(Channel channel) throws Exception {
		if(StringUtils.isBlank(channel.getVcName())){
			throw new Exception("名称不能为空");
		}
		Channel nel = channelMapper.findByName(channel.getVcName());
		if(nel != null && nel.getIvalid() == 1){
			throw new Exception("名称已经存在");
		}
		channel.setIvalid(1);
		channel.setDtCreate(new Date());
		channelMapper.save(channel);
	}

	public void update(Channel channel) throws Exception {
		if(StringUtils.isBlank(channel.getVcName())){
			throw new Exception("名称不能为空");
		}
		Channel nel = channelMapper.findByName(channel.getVcName());
		if(nel != null && nel.getIvalid() == 1 && nel.getId() != channel.getId() ){
			throw new Exception("名称已经存在");
		}
		channel.setDtModify(new Date());
		channelMapper.update(channel);
	}

	public PageResult<Channel> findPage(PaginationQuery query)
			throws Exception {
		PageResult<Channel> result = null;
		try {
			Integer count = channelMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",
						Integer.toString(query.getRowsPerPage()));
				List<Channel> list = channelMapper.findPage(query
						.getQueryData());
				result = new PageResult<Channel>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Channel> queryTbChannelAll() throws Exception {
		List<Channel> list = null;
		list = channelMapper.queryTbChannelAll();
		return list;
	}

}
