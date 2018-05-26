package com.tzg.entitys.pcextendimage;

import com.tzg.entitys.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface PcextendimageMapper extends BaseMapper<Pcextendimage, java.lang.Integer> {	
	public Integer getMaxSort(Integer iextendPlace);
	
	public Integer getVaildMaxSort(Integer iextendPlace);
}
