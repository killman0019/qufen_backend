package com.tzg.service.SystemParam;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.leopard.system.SystemParamMapper;
import com.tzg.service.base.BaseService;

/**
 * Created by cfour on 12/3/14.
 */
@Service
@Transactional
public class SystemParamService extends BaseService {
	@Autowired
	private RedisService redisService;
    @Autowired
    private SystemParamMapper systemParamMapper;

    public void save(SystemParam systemParam) throws Exception{
    	if(StringUtils.isBlank(systemParam.getVcParamName())){
			throw new Exception("名称不能为空！");
		}
    	if(StringUtils.isBlank(systemParam.getVcParamCode())){
			throw new Exception("代码不能为空！");
		}
		if(StringUtils.isBlank(systemParam.getVcParamValue())){
			throw new Exception("参数值不能为空！");
    	}
    	if(null != systemParamMapper.findByCode(systemParam.getVcParamCode())){
    		throw new Exception("代码已存在！");
    	}
        systemParam.setDtCreate(new Date());
        systemParamMapper.save(systemParam);
        this.redisService.putObj("paramCode"+systemParam.getVcParamCode(), systemParam, 60 * 60 * 24);
    }
    public void update(SystemParam systemParam) throws Exception {
        systemParam.setDtModify(new Date());
        this.redisService.del("paramId"+String.valueOf(systemParam.getId()));
        systemParamMapper.update(systemParam);
        this.redisService.putObj("paramCode"+systemParam.getVcParamCode(), systemParam, 60 * 60 * 24);
    }

    public void delete(int id) throws Exception {
    	this.redisService.del("paramId"+String.valueOf(id));
        systemParamMapper.deleteById(id);
    }

    public SystemParam findById(int id){
        return systemParamMapper.findById(id);
    }

    public SystemParam findByCode(String code){
        return systemParamMapper.findByCode(code);
    }
    
    public PageResult<SystemParam> getParamsList(PaginationQuery query){
        PageResult<SystemParam> result = null;
        try{
        	Integer count = systemParamMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<SystemParam> list = systemParamMapper.findPage(query.getQueryData());
				result = new PageResult<SystemParam>(list,count,query);
			}
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 图片资源地址
     * @return
     */
    private static String staticUploadUrl = null;
    public String getStaticUploadUrl(){
    	if(StringUtils.isBlank(staticUploadUrl)){
    		SystemParam systemParam = systemParamMapper.findByCode("sys_static_file_server_url");
    		if(systemParam!=null){
    			staticUploadUrl = systemParam.getVcParamValue();
    		}
    	}
		return staticUploadUrl;
    }
}
