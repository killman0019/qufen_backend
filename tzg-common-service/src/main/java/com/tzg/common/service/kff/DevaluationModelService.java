package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.devaluationModel.DevaluationModel;
import com.tzg.entitys.kff.devaluationModel.DevaluationModelMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFDevaluationModelService")
@Transactional
public class DevaluationModelService {

	@Autowired
	private DevaluationModelMapper devaluationModelMapper;

	@Transactional(readOnly = true)
	public DevaluationModel findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return devaluationModelMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		devaluationModelMapper.deleteById(id);
	}

	public void save(DevaluationModel devaluationModel) throws RestServiceException {
		devaluationModelMapper.save(devaluationModel);
	}

	public void update(DevaluationModel devaluationModel) throws RestServiceException {
		if (devaluationModel.getModelId() == null) {
			throw new RestServiceException("id不能为空");
		}
		devaluationModelMapper.update(devaluationModel);
	}

	@Transactional(readOnly = true)
	public PageResult<DevaluationModel> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<DevaluationModel> result = null;
		try {
			Integer count = devaluationModelMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<DevaluationModel> list = devaluationModelMapper.findPage(query.getQueryData());
				result = new PageResult<DevaluationModel>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional(readOnly = true)
	public List<DevaluationModel> selectEvaluationModelByModelType(Integer modelType) {
		// TODO Auto-generated method stub
		return devaluationModelMapper.selectEvaluationModelByModelType(modelType);
	}

	@Transactional(readOnly = true)
	public List<DevaluationModel> findAll() {
		// TODO Auto-generated method stub
		return devaluationModelMapper.findAll();
	}

}
