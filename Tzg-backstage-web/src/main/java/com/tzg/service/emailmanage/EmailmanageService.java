package com.tzg.service.emailmanage;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.core.utils.DateUtil;
import com.tzg.entitys.emailmanage.Emailmanage;
import com.tzg.entitys.emailmanage.EmailmanageMapper;
import com.tzg.service.base.BaseService;
import com.tzg.service.emailsender.EMailSenderService;

@Service
@Transactional
public class EmailmanageService extends BaseService {

	private static Logger log = Logger.getLogger(EmailmanageService.class);

	@Autowired
	private EmailmanageMapper emailmanageMapper;



	@Autowired
	private EMailSenderService eMailSenderService;

	@Transactional(readOnly = true)
	public Emailmanage findById(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		return emailmanageMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		emailmanageMapper.deleteById(id);
	}

	public void save(Emailmanage emailmanage) throws Exception {
		check(emailmanage);
		emailmanage.setIstate(1);
		emailmanage.setDtCreate(new Date());
		if (1 == emailmanage.getsType()) {
			emailmanage.setDtSend(null);
		}
		emailmanageMapper.save(emailmanage);
	}

	public void update(Emailmanage emailmanage) throws Exception {
		if (emailmanage.getId() == null) {
			throw new Exception("id不能为空");
		}
		check(emailmanage);
		if (1 == emailmanage.getsType()) {
			emailmanage.setDtSend(null);
		}
		emailmanage.setIstate(1);
		emailmanageMapper.updateAll(emailmanage);
	}

	@Transactional(readOnly = true)
	public PageResult<Emailmanage> findPage(PaginationQuery query)
			throws Exception {
		PageResult<Emailmanage> result = null;
		try {
			Integer count = emailmanageMapper.findPageCount(query
					.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",
						Integer.toString(query.getRowsPerPage()));
				List<Emailmanage> list = emailmanageMapper.findPage(query
						.getQueryData());
				result = new PageResult<Emailmanage>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 邮件发布
	 * 
	 * @param id
	 * @throws Exception
	 */
	public synchronized void publish(Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		Emailmanage emailmanage = emailmanageMapper.findById(id);
		if (emailmanage == null) {
			throw new Exception("该记录已经不存在！");
		}
		if (emailmanage.getIstate() != 1) {
			throw new Exception("该状态下的记录不允许执行发布！");
		}
		emailmanage.setDtPublish(new Date());
		emailmanage.setIstate(2);
		emailmanage.setIstate(2);
		if (1 == emailmanage.getsType()) {
			emailmanage.setDtSend(new Date());
		}
		// emailmanage.setIstate(3);
		//
		// // 根据接收对象类型，获取符合条件的接收对象的邮箱地址列表
		// Integer itype = emailmanage.getItype();
		// PaginationQuery query = new PaginationQuery();
		// if (itype == 1) {// 所有人
		// query.addQueryData("itype", null);
		// } else if (itype == 2) {// 投资者
		// query.addQueryData("itype", "2");
		// } else if (itype == 3) {// 借款人
		// query.addQueryData("itype", "1");
		// }
		// PageResult<Loginaccount> pageList =
		// loginaccountService.findPage(query);
		// for (int i = 0; i < pageList.getPageSize(); i++) {
		// // 发送邮件
		// for (Loginaccount loginAccount : pageList.getRows()) {
		// if (checkEmail(loginAccount.getVcEmail())) {
		// try {
		// eMailSenderService.sendTextMail(
		// loginAccount.getVcEmail(),
		// emailmanage.getVcTitle(),
		// emailmanage.getVcContent());
		// } catch (Exception e) {
		// log.error("用户[" + loginAccount.getVcPhone()
		// + "]的邮件通知异常", e);
		// }
		// }
		// }
		// query.setPageIndex(query.getPageIndex() + 1);
		// pageList = loginaccountService.findPage(query);
		// }
		emailmanageMapper.update(emailmanage);
	}

	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 * @return
	 */
	private static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	private void check(Emailmanage emailmanage) throws Exception {
		if (StringUtils.isBlank(emailmanage.getVcTitle())) {
			throw new Exception("标题不能为空！");
		}
		if (emailmanage.getItype() == null) {
			throw new Exception("接收对象不能为空！");
		}

		if (StringUtils.isNotBlank(emailmanage.getDtRegisterStartStr())
				&& StringUtils.isBlank(emailmanage.getDtRegisterEndStr())) {
			throw new Exception("注册结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(emailmanage.getDtRegisterStartStr())
				&& StringUtils.isNotBlank(emailmanage.getDtRegisterEndStr())
				&& DateUtil.compareDateTime(emailmanage.getDtRegisterStartStr(),
						emailmanage.getDtRegisterEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("注册开始时间[" + emailmanage.getDtRegisterStartStr()
					+ "]应小于或等于注册结束时间[" + emailmanage.getDtRegisterEndStr() + "]！");
		}
		if (StringUtils.isNotBlank(emailmanage.getDtLastLoginStartStr())
				&& StringUtils.isBlank(emailmanage.getDtLastLoginEndStr())) {
			throw new Exception("最后登录结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(emailmanage.getDtLastLoginStartStr())
				&& StringUtils.isNotBlank(emailmanage.getDtLastLoginEndStr())
				&& DateUtil.compareDateTime(emailmanage.getDtLastLoginStartStr(),
						emailmanage.getDtLastLoginEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("最后登录开始时间["
					+ emailmanage.getDtLastLoginStartStr() + "]应小于或等于最后登录结束时间["
					+ emailmanage.getDtLastLoginEndStr() + "]！");
		}
		if (StringUtils.isNotBlank(emailmanage.getDtLastRechargeStartStr())
				&& StringUtils.isBlank(emailmanage.getDtLastRechargeEndStr())
			) {
			throw new Exception("最后充值结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(emailmanage.getDtLastRechargeStartStr())
				&& StringUtils.isNotBlank(emailmanage.getDtLastRechargeEndStr())
				&& DateUtil.compareDateTime(
						emailmanage.getDtLastRechargeStartStr(),
						emailmanage.getDtLastRechargeEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("最后充值开始时间["
					+ emailmanage.getDtLastRechargeStartStr()
					+ "]应小于或等于最后充值结束时间[" + emailmanage.getDtLastRechargeEndStr()
					+ "]！");
		}
		if (StringUtils.isNotBlank(emailmanage.getDtLastInvestStartStr())
				&& StringUtils.isBlank(emailmanage.getDtLastInvestEndStr())
				) {
			throw new Exception("最后投资结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(emailmanage.getDtLastInvestStartStr())
				&& StringUtils.isNotBlank(emailmanage.getDtLastInvestEndStr())
				&& DateUtil.compareDateTime(
						emailmanage.getDtLastInvestStartStr(),
						emailmanage.getDtLastInvestEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("最后投资开始时间["
					+ emailmanage.getDtLastInvestStartStr() + "]应小于或等于最后投资结束时间["
					+ emailmanage.getDtLastInvestEndStr() + "]！");
		}


		if (null != emailmanage.getMinTotalRecharge()
				&& null != emailmanage.getMaxTotalRecharge()
				&& emailmanage.getMinTotalRecharge().compareTo(
						emailmanage.getMaxTotalRecharge()) > 0) {
			throw new Exception("累计充值最小金额[" + emailmanage.getMinTotalRecharge()
					+ "]应小于或等于累计充值最大金额[" + emailmanage.getMaxTotalRecharge()
					+ "]！");
		}
		if (null != emailmanage.getMinTotalInvest()
				&& null != emailmanage.getMaxTotalInvest()
				&& emailmanage.getMinTotalInvest().compareTo(
						emailmanage.getMaxTotalInvest()) > 0) {
			throw new Exception("累计投资最小金额[" + emailmanage.getMinTotalInvest()
					+ "]应小于或等于累计投资最大金额[" + emailmanage.getMaxTotalInvest()
					+ "]！");
		}
		if (null != emailmanage.getMinTotalBalance()
				&& null != emailmanage.getMaxTotalBalance()
				&& emailmanage.getMinTotalBalance().compareTo(
						emailmanage.getMaxTotalBalance()) > 0) {
			throw new Exception("账户最小余额[" + emailmanage.getMinTotalBalance()
					+ "]应小于或等于账户最大余额[" + emailmanage.getMaxTotalBalance()
					+ "]！");
		}
		if (null == emailmanage.getsType()) {
			throw new Exception("发送时间不能为空！");
		} else {
			if (2 == emailmanage.getsType()
					&& StringUtils.isBlank(emailmanage.getDtSendStr())) {
				throw new Exception("发送时间不能为空！");
			}
		}
		if (StringUtils.isBlank(emailmanage.getVcContent())) {
			throw new Exception("内容不能为空！");
		}

	}
}
