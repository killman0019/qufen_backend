package com.tzg.entitys.kff.notice;
import com.tzg.common.base.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface KFFNoticeMapper extends BaseMapper<KFFNotice, java.lang.Integer> {

	KFFNotice selectLatestNotice();	

}
