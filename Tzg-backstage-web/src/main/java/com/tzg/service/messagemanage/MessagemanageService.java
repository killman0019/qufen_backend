package com.tzg.service.messagemanage;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.core.utils.DateUtil;
import com.tzg.entitys.messagemanage.Messagemanage;
import com.tzg.entitys.messagemanage.MessagemanageMapper;
import com.tzg.service.base.BaseService;
import com.tzg.service.message.MessageService;

@Service
@Transactional
public class MessagemanageService extends BaseService {

	@Autowired
	private MessagemanageMapper messagemanageMapper;



	@Autowired
	private MessageService messageService;

	@Transactional(readOnly = true)
	public Messagemanage findById(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		return messagemanageMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		messagemanageMapper.deleteById(id);
	}

	public void save(Messagemanage messagemanage) throws Exception {
		check(messagemanage);
		messagemanage.setIstate(1);
		messagemanage.setDtCreate(new Date());
		if (1 == messagemanage.getsType()) {
			messagemanage.setDtSend(null);
		}
		messagemanageMapper.save(messagemanage);
	}

	public void update(Messagemanage messagemanage) throws Exception {
		if (messagemanage.getId() == null) {
			throw new Exception("id不能为空");
		}
		check(messagemanage);
		if (1 == messagemanage.getsType()) {
			messagemanage.setDtSend(null);
		}
		messagemanage.setIstate(1);
		messagemanageMapper.updateAll(messagemanage);
	}

	private void check(Messagemanage messagemanage) throws Exception {
		if (messagemanage.getImode() == null) {
			throw new Exception("类型不能为空！");
		}
		if (messagemanage.getItype() == null) {
			throw new Exception("接收对象不能为空！");
		}
		if (StringUtils.isNotBlank(messagemanage.getDtRegisterStartStr())
				&& StringUtils.isBlank(messagemanage.getDtRegisterEndStr())) {
			throw new Exception("注册结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(messagemanage.getDtRegisterStartStr())
				&& StringUtils.isNotBlank(messagemanage.getDtRegisterEndStr())
				&& DateUtil.compareDateTime(messagemanage.getDtRegisterStartStr(),
						messagemanage.getDtRegisterEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("注册开始时间[" + messagemanage.getDtRegisterStartStr()
					+ "]应小于或等于注册结束时间[" + messagemanage.getDtRegisterEndStr() + "]！");
		}
		if (StringUtils.isNotBlank(messagemanage.getDtLastLoginStartStr())
				&& StringUtils.isBlank(messagemanage.getDtLastLoginEndStr())) {
			throw new Exception("最后登录结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(messagemanage.getDtLastLoginStartStr())
				&& StringUtils.isNotBlank(messagemanage.getDtLastLoginEndStr())
				&& DateUtil.compareDateTime(messagemanage.getDtLastLoginStartStr(),
						messagemanage.getDtLastLoginEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("最后登录开始时间["
					+ messagemanage.getDtLastLoginStartStr() + "]应小于或等于最后登录结束时间["
					+ messagemanage.getDtLastLoginEndStr() + "]！");
		}
		if (StringUtils.isNotBlank(messagemanage.getDtLastRechargeStartStr())
				&& StringUtils.isBlank(messagemanage.getDtLastRechargeEndStr())
			) {
			throw new Exception("最后充值结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(messagemanage.getDtLastRechargeStartStr())
				&& StringUtils.isNotBlank(messagemanage.getDtLastRechargeEndStr())
				&& DateUtil.compareDateTime(
						messagemanage.getDtLastRechargeStartStr(),
						messagemanage.getDtLastRechargeEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("最后充值开始时间["
					+ messagemanage.getDtLastRechargeStartStr()
					+ "]应小于或等于最后充值结束时间[" + messagemanage.getDtLastRechargeEndStr()
					+ "]！");
		}
		if (StringUtils.isNotBlank(messagemanage.getDtLastInvestStartStr())
				&& StringUtils.isBlank(messagemanage.getDtLastInvestEndStr())
				) {
			throw new Exception("最后投资结束时间不能为空！");
		}
		if (StringUtils.isNotBlank(messagemanage.getDtLastInvestStartStr())
				&& StringUtils.isNotBlank(messagemanage.getDtLastInvestEndStr())
				&& DateUtil.compareDateTime(
						messagemanage.getDtLastInvestStartStr(),
						messagemanage.getDtLastInvestEndStr(), "yyyy-MM-dd") > 0) {
			throw new Exception("最后投资开始时间["
					+ messagemanage.getDtLastInvestStartStr() + "]应小于或等于最后投资结束时间["
					+ messagemanage.getDtLastInvestEndStr() + "]！");
		}

		if (null != messagemanage.getMinTotalRecharge()
				&& null != messagemanage.getMaxTotalRecharge()
				&& messagemanage.getMinTotalRecharge().compareTo(
						messagemanage.getMaxTotalRecharge()) > 0) {
			throw new Exception("累计充值最小金额["
					+ messagemanage.getMinTotalRecharge() + "]应小于或等于累计充值最大金额["
					+ messagemanage.getMaxTotalRecharge() + "]！");
		}
		if (null != messagemanage.getMinTotalInvest()
				&& null != messagemanage.getMaxTotalInvest()
				&& messagemanage.getMinTotalInvest().compareTo(
						messagemanage.getMaxTotalInvest()) > 0) {
			throw new Exception("累计投资最小金额[" + messagemanage.getMinTotalInvest()
					+ "]应小于或等于累计投资最大金额[" + messagemanage.getMaxTotalInvest()
					+ "]！");
		}
		if (null != messagemanage.getMinTotalBalance()
				&& null != messagemanage.getMaxTotalBalance()
				&& messagemanage.getMinTotalBalance().compareTo(
						messagemanage.getMaxTotalBalance()) > 0) {
			throw new Exception("账户最小余额[" + messagemanage.getMinTotalBalance()
					+ "]应小于或等于账户最大余额[" + messagemanage.getMaxTotalBalance()
					+ "]！");
		}
		if (null == messagemanage.getsType()) {
			throw new Exception("发送时间不能为空！");
		} else {
			if (2 == messagemanage.getsType()
					&& StringUtils.isBlank(messagemanage.getDtSendStr())) {
				throw new Exception("发送时间不能为空！");
			}
		}
		if (StringUtils.isBlank(messagemanage.getVcTitle())) {
			throw new Exception("标题不能为空！");
		}
		if (StringUtils.isBlank(messagemanage.getVcContent())) {
			throw new Exception("内容不能为空！");
		}
	}

	@Transactional(readOnly = true)
	public PageResult<Messagemanage> findPage(PaginationQuery query)
			throws Exception {
		PageResult<Messagemanage> result = null;
		try {
			Integer count = messagemanageMapper.findPageCount(query
					.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",
						Integer.toString(query.getRowsPerPage()));
				List<Messagemanage> list = messagemanageMapper.findPage(query
						.getQueryData());
				result = new PageResult<Messagemanage>(list, count, query);
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
		Messagemanage messagemanage = messagemanageMapper.findById(id);
		if (messagemanage == null) {
			throw new Exception("该记录已经不存在！");
		}
		if (messagemanage.getIstate() != 1) {
			throw new Exception("该状态下的记录不允许执行发布！");
		}
		messagemanage.setDtPublish(new Date());
		if(null != messagemanage.getImode() && messagemanage.getImode() ==3 ){
		    messagemanage.setIstate(3); //对客户端公告类型的 默认设置为 3 已发布，这样跑批就不会处理
		}else{
			messagemanage.setIstate(2);	
		}
		if (1 == messagemanage.getsType()) {
			messagemanage.setDtSend(new Date());
		}
		// messagemanage.setIstate(3);
		// //根据类型获取最大的前台登陆账号主简值
		// PaginationQuery query = new PaginationQuery();
		// if(messagemanage.getItype() == 2)
		// query.addQueryData("itype", "2");
		// else if(messagemanage.getItype() == 3)
		// query.addQueryData("itype", "1");
		// PageResult<Loginaccount> pageList =
		// loginaccountService.findPage(query);
		// int numSend = pageList.getRowCount();
		// messagemanage.setNumSend(numSend);
		// for(int i =0; i < pageList.getPageSize(); i ++){
		// //处理
		// for(Loginaccount loginAccount : pageList.getRows()){
		// Message message= new Message();
		// message.setIloginAccountId(loginAccount.getId());
		// message.setImanageId(id);
		// message.setVcContent(messagemanage.getVcContent());
		// message.setVcTitle(messagemanage.getVcTitle());
		// message.setVcSubTitle(messagemanage.getVcSubTitle());
		// message.setDtPublish(messagemanage.getDtPublish());
		// message.setImode(messagemanage.getImode());
		// message.setIstate(1);
		// messageService.save(message);
		// }
		// query.setPageIndex(query.getPageIndex() + 1);
		// pageList = loginaccountService.findPage(query);
		// }
		messagemanageMapper.update(messagemanage);

	}

}
