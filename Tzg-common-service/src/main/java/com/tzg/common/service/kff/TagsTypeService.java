package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.tagstype.TagsType;
import com.tzg.entitys.kff.tagstype.TagsTypeMapper;

@Service(value = "tagsTypeService")
@Transactional
public class TagsTypeService {

	@Autowired
	private TagsTypeMapper tagsTypeMapper;

	/**
	 * 
	* @Title: findByMap 
	* @Description: TODO map <map查询数据库>
	* @author zhangdd <方法创建作者>
	* @create 下午6:10:03
	* @param @param map
	* @param @return <参数说明>
	* @return List<TagsType> 
	* @throws 
	* @update 下午6:10:03
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public List<TagsType> findByMap(Map<String, Object> map) {

		return tagsTypeMapper.findByMap(map);
	}

	@Transactional(readOnly = true)
	public TagsType findById(Integer id) {

		return tagsTypeMapper.findById(id);
	}

	public void save(TagsType tagsType) {

		tagsTypeMapper.save(tagsType);
	}

	public void update(TagsType tagsType) {
		// TODO Auto-generated method stub

	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	public void active(Integer id) {
		// TODO Auto-generated method stub

	}

	@Transactional(readOnly = true)
	public PageResult<TagsType> findPage(PaginationQuery query) {
		PageResult<TagsType> result = null;
		try {
			Integer count = tagsTypeMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<TagsType> list = tagsTypeMapper.findPage(query.getQueryData());
				result = new PageResult<TagsType>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
