package com.tzg.service.smsmanage;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.core.utils.DateUtil;
import com.tzg.entitys.smsmanage.Smsmanage;
import com.tzg.entitys.smsmanage.SmsmanageMapper;
import com.tzg.service.base.BaseService;
import com.tzg.service.smssend.SmssendService;

@Service
@Transactional
public class SmsmanageService extends BaseService {

	@Autowired
	private SmsmanageMapper smsmanageMapper;


	@Autowired
	private SmssendService smssendService;

	@Transactional(readOnly = true)
	public Smsmanage findById(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		return smsmanageMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		smsmanageMapper.deleteById(id);
	}

	public void save(Smsmanage smsmanage) throws Exception {
		check(smsmanage);
		smsmanage.setIstate(1);
		smsmanage.setDtCreate(new Date());
		if (1 == smsmanage.getsType()) {
			smsmanage.setDtSend(null);
		}
		smsmanageMapper.save(smsmanage);
	}

	public void update(Smsmanage smsmanage) throws Exception {
		if (smsmanage.getId() == null) {
			throw new Exception("id不能为空");
		}
		check(smsmanage);
		if (1 == smsmanage.getsType()) {
			smsmanage.setDtSend(null);
		}
		smsmanage.setIstate(1);
		smsmanageMapper.updateAll(smsmanage);
	}

	private void check(Smsmanage smsmanage) throws Exception {
		if (smsmanage.getItype() == null) {
			throw new Exception("接收对象不能为空！");
		}
		if (StringUtils.isNotBlank(smsmanage.getDtRegisterStartStr())
				&& StringUtils.isBlank(smsmanage.getDtRegisterEndStr())) {
			throw new Exception("注册结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(smsmanage.getDtRegisterStartStr())
				&& StringUtils.isNotBlank(smsmanage.getDtRegisterEndStr())
				&& DateUtil.compareDateTime(smsmanage.getDtRegisterStartStr(),
						smsmanage.getDtRegisterEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("注册开始时间[" + smsmanage.getDtRegisterStartStr()
					+ "]应小于或等于注册结束时间[" + smsmanage.getDtRegisterEndStr() + "]！");
		}
		if (StringUtils.isNotBlank(smsmanage.getDtLastLoginStartStr())
				&& StringUtils.isBlank(smsmanage.getDtLastLoginEndStr())) {
			throw new Exception("最后登录结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(smsmanage.getDtLastLoginStartStr())
				&& StringUtils.isNotBlank(smsmanage.getDtLastLoginEndStr())
				&& DateUtil.compareDateTime(smsmanage.getDtLastLoginStartStr(),
						smsmanage.getDtLastLoginEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("最后登录开始时间["
					+ smsmanage.getDtLastLoginStartStr() + "]应小于或等于最后登录结束时间["
					+ smsmanage.getDtLastLoginEndStr() + "]！");
		}
		if (StringUtils.isNotBlank(smsmanage.getDtLastRechargeStartStr())
				&& StringUtils.isBlank(smsmanage.getDtLastRechargeEndStr())
			) {
			throw new Exception("最后充值结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(smsmanage.getDtLastRechargeStartStr())
				&& StringUtils.isNotBlank(smsmanage.getDtLastRechargeEndStr())
				&& DateUtil.compareDateTime(
						smsmanage.getDtLastRechargeStartStr(),
						smsmanage.getDtLastRechargeEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("最后充值开始时间["
					+ smsmanage.getDtLastRechargeStartStr()
					+ "]应小于或等于最后充值结束时间[" + smsmanage.getDtLastRechargeEndStr()
					+ "]！");
		}
		if (StringUtils.isNotBlank(smsmanage.getDtLastInvestStartStr())
				&& StringUtils.isBlank(smsmanage.getDtLastInvestEndStr())
				) {
			throw new Exception("最后投资结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(smsmanage.getDtLastInvestStartStr())
				&& StringUtils.isNotBlank(smsmanage.getDtLastInvestEndStr())
				&& DateUtil.compareDateTime(
						smsmanage.getDtLastInvestStartStr(),
						smsmanage.getDtLastInvestEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("最后投资开始时间["
					+ smsmanage.getDtLastInvestStartStr() + "]应小于或等于最后投资结束时间["
					+ smsmanage.getDtLastInvestEndStr() + "]！");
		}

		if (null != smsmanage.getMinTotalRecharge()
				&& null != smsmanage.getMaxTotalRecharge()
				&& smsmanage.getMinTotalRecharge().compareTo(
						smsmanage.getMaxTotalRecharge()) > 0) {
			throw new Exception("累计充值最小金额[" + smsmanage.getMinTotalRecharge()
					+ "]应小于或等于累计充值最大金额[" + smsmanage.getMaxTotalRecharge()
					+ "]！");
		}
		if (null != smsmanage.getMinTotalInvest()
				&& null != smsmanage.getMaxTotalInvest()
				&& smsmanage.getMinTotalInvest().compareTo(
						smsmanage.getMaxTotalInvest()) > 0) {
			throw new Exception("累计投资最小金额[" + smsmanage.getMinTotalInvest()
					+ "]应小于或等于累计投资最大金额[" + smsmanage.getMaxTotalInvest() + "]！");
		}
		if (null != smsmanage.getMinTotalBalance()
				&& null != smsmanage.getMaxTotalBalance()
				&& smsmanage.getMinTotalBalance().compareTo(
						smsmanage.getMaxTotalBalance()) > 0) {
			throw new Exception("账户最小余额[" + smsmanage.getMinTotalBalance()
					+ "]应小于或等于账户最大余额[" + smsmanage.getMaxTotalBalance() + "]！");
		}
		if (null == smsmanage.getsType()) {
			throw new Exception("发送时间不能为空！");
		} else {
			if (2 == smsmanage.getsType()
					&& StringUtils.isBlank(smsmanage.getDtSendStr())) {
				throw new Exception("发送时间不能为空！");
			}
		}

		if (StringUtils.isBlank(smsmanage.getVcTitle())) {
			throw new Exception("标题不能为空！");
		}
		if (StringUtils.isBlank(smsmanage.getVcContent())) {
			throw new Exception("内容不能为空！");
		}
	}

	@Transactional(readOnly = true)
	public PageResult<Smsmanage> findPage(PaginationQuery query)
			throws Exception {
		PageResult<Smsmanage> result = null;
		try {
			Integer count = smsmanageMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",
						Integer.toString(query.getRowsPerPage()));
				List<Smsmanage> list = smsmanageMapper.findPage(query
						.getQueryData());
				result = new PageResult<Smsmanage>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 执行发布操作
	 * 
	 * @param id
	 * @throws Exception
	 */
	public synchronized void publish(Integer id) throws Exception {
		//
		Smsmanage smsmanage = smsmanageMapper.findById(id);
		if (smsmanage == null) {
			throw new Exception("该记录已经不存在！");
		}
		if (smsmanage.getIstate() != 1) {
			throw new Exception("该状态下的记录不允许执行发布！");
		}
		smsmanage.setDtPublish(new Date());
		smsmanage.setIstate(2);
		if (1 == smsmanage.getsType()) {
			smsmanage.setDtSend(new Date());
		}
		// smsmanage.setIstate(3);
		// //根据类型获取最大的前台登陆账号主简值
		// PaginationQuery query = new PaginationQuery();
		// if(smsmanage.getItype() == 2)
		// query.addQueryData("itype", "2");
		// else if(smsmanage.getItype() == 3)
		// query.addQueryData("itype", "1");
		// PageResult<Loginaccount> pageList =
		// loginaccountService.findPage(query);
		// for(int i =0; i < pageList.getPageSize(); i ++){
		// //处理
		// for(Loginaccount loginAccount : pageList.getRows()){
		// smssendService.save(loginAccount.getVcPhone(),smsmanage.getVcContent(),3);
		// }
		// query.setPageIndex(query.getPageIndex() + 1);
		// pageList = loginaccountService.findPage(query);
		// }
		smsmanageMapper.update(smsmanage);

	}

}
