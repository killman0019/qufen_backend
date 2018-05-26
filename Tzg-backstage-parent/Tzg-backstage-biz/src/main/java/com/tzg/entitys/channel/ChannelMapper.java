package com.tzg.entitys.channel;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface ChannelMapper extends BaseMapper<Channel, java.lang.Integer> {	
	
	public List<Channel> queryTbChannelAll() throws Exception;
	
	public Channel findByName(String vcName);

}
