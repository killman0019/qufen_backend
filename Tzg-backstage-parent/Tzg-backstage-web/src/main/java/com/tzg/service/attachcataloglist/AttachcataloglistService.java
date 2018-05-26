package com.tzg.service.attachcataloglist;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.attachcataloglist.Attachcataloglist;
import com.tzg.entitys.attachcataloglist.AttachcataloglistMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class AttachcataloglistService extends BaseService {

	@Autowired
	private AttachcataloglistMapper attachcataloglistMapper;

	@Transactional(readOnly = true)
	public Attachcataloglist findById(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		return attachcataloglistMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		attachcataloglistMapper.deleteById(id);
	}

	public void save(Attachcataloglist attachcataloglist) throws Exception {
		if (StringUtils.isBlank(attachcataloglist.getVcName())) {
			throw new Exception("名称不能为空！");
		}
		attachcataloglist.setiState(1);
		attachcataloglistMapper.save(attachcataloglist);
	}

	public void update(Attachcataloglist attachcataloglist) throws Exception {
		if (attachcataloglist.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (StringUtils.isBlank(attachcataloglist.getVcName())) {
			throw new Exception("名称不能为空！");
		}
		attachcataloglistMapper.update(attachcataloglist);
	}

	@Transactional(readOnly = true)
	public PageResult<Attachcataloglist> findPage(PaginationQuery query)
			throws Exception {
		PageResult<Attachcataloglist> result = null;
		try {
			Integer count = attachcataloglistMapper.findPageCount(query
					.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",
						Integer.toString(query.getRowsPerPage()));
				List<Attachcataloglist> list = attachcataloglistMapper
						.findPage(query.getQueryData());
				result = new PageResult<Attachcataloglist>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param iState
	 * @return
	 * @throws Exception
	 */
	public List<Attachcataloglist> queryAttachCatalogByIStateList(Integer iState)
			throws Exception {
		List<Attachcataloglist> list = null;
		list = attachcataloglistMapper.queryAttachCatalogByIStateList(iState);
		return list;
	}

}
