package com.tzg.service.consoleLoginAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.entitys.leopard.console.ConsoleLoginAccount;
import com.tzg.entitys.leopard.console.ConsoleLoginAccountMapper;
import com.tzg.entitys.leopard.person.PersonalRole;
import com.tzg.entitys.leopard.person.PersonalRoleMapper;
import com.tzg.entitys.leopard.resource.Resource;
import com.tzg.entitys.leopard.resource.ResourceMapper;
import com.tzg.service.base.BaseService;
import com.tzg.service.smssend.SmssendService;

/**
 * Created by cfour on 12/1/14.
 */
@Service
@Transactional
public class ConsoleLoginAccountService extends BaseService {
	@Autowired
	private ConsoleLoginAccountMapper consoleLoginAccountMapper;

	@Autowired
	private PersonalRoleMapper personalRoleMapper;

	@Autowired
	private ResourceMapper resourceMapper;

	@Autowired
	private SmssendService smssendService;

	/**
	 * 根据登陆账号查询
	 * 
	 * @param loginName
	 * @return
	 */
	public ConsoleLoginAccount getLoginAccount(String loginName) {
		return consoleLoginAccountMapper.selectByName(loginName);
	}

	public int save(ConsoleLoginAccount account) throws Exception {
		if (StringUtils.isBlank(account.getVcLoginName())) {
			throw new Exception("登录账号不能为空");
		}
		if (StringUtils.isBlank(account.getVcPhone())) {
			throw new Exception("手机号不能为空");
		}
		ConsoleLoginAccount loginAccount = consoleLoginAccountMapper
				.selectByName(account.getVcLoginName());
		if (loginAccount != null) {
			throw new Exception("登录账号已经存在");
		}
		String pwd = RandomUtil
				.produceStringAndNumber(TzgConstant.MIN_PASSWORD_LENGTH);
		account.setVcLoginPassword(SHAUtil.encode(pwd));
		account.setiValid(1);
		account.setDtCreate(new Date());
		int id = consoleLoginAccountMapper.save(account);
		if (account.getRolesId() != null) {
			savePersonalRole(account.getId(), account.getRolesId());
		}
		String smsContent = smssendService.getSmsContent(
				34,account.getVcLoginName(), pwd);
		smssendService.save(account.getVcPhone(), smsContent, 2);
		return id;
	}

	public void savePersonalRole(int id, List<String> rolesId) {
		personalRoleMapper.deleteByAccount(id);
		if (rolesId != null && rolesId.size() > 0) {
			List<PersonalRole> personalRoleList = new ArrayList<PersonalRole>();
			for (int i = 0; i < rolesId.size(); i++) {
				String roleId = rolesId.get(i);
				PersonalRole personalRole = new PersonalRole();
				personalRole.setiConsoleLoginAccountID(id);
				personalRole.setiRoleinfoID(Integer.valueOf(roleId));
				personalRoleList.add(personalRole);
			}
			personalRoleMapper.insertByList(personalRoleList);
		}
	}

	public int update(ConsoleLoginAccount account) throws Exception {
		if (StringUtils.isBlank(account.getVcLoginName())) {
			throw new Exception("账号不能为空！");
		}
		if (StringUtils.isBlank(account.getVcPhone())) {
			throw new Exception("手机号不能为空！");
		}

		// 根据账号查询
		ConsoleLoginAccount loginAccount = consoleLoginAccountMapper
				.selectByName(account.getVcLoginName());
		if (loginAccount != null && loginAccount.getId() != account.getId()) {
			throw new Exception("账号已经存在！");
		}
		account.setDtModify(new Date());
		int id = consoleLoginAccountMapper.update(account);
		savePersonalRole(account.getId(), account.getRolesId());
		return id;
	}

	public void updatepassword(ConsoleLoginAccount account) throws Exception {
		consoleLoginAccountMapper.updatePassword(account);
	}

	public void delete(int id) {
		consoleLoginAccountMapper.delete(id);
	}

	public ConsoleLoginAccount findById(int id) throws Exception {
		List<String> rolesId = personalRoleMapper.findRoleByAccountId(id);
		ConsoleLoginAccount consoleLoginAccount = consoleLoginAccountMapper
				.findById(id);
		if (consoleLoginAccount == null)
			throw new Exception(TzgConstant.MESSAGE_RECORD_NOT_EXIST + " id ="
					+ id);
		consoleLoginAccount.setRolesId(rolesId);
		return consoleLoginAccount;
	}

	public List<String> getResourceByAccount(int id) {
		return resourceMapper.findResourcesByAccount(id);
	}

	public List<Resource> getMenuByAccountId(int id) {
		List<Resource> list = null;
		if (id != 0) {
			list = resourceMapper.getFirstResourceByAccountId(id);
		} else {
			list = resourceMapper.queryFirstLevelResourceAll();
		}
		if (0 != list.size()) {
			for (Resource firstMemu : list) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("accountId", id + "");
				map.put("parentID", firstMemu.getId() + "");
				List<Resource> secondList;
				if (id != 0) {
					secondList = resourceMapper
							.getSecondResourceByAccountId(map);
				} else {
					secondList = resourceMapper.getSecondResourceAll(map);
				}
				firstMemu.setChildren(secondList);
			}
		}
		return list;
	}

	public PageResult<ConsoleLoginAccount> getAccountList(PaginationQuery query) {
		PageResult<ConsoleLoginAccount> result = null;
		try {
			Integer count = consoleLoginAccountMapper.queryAccountsCount(query
					.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",
						Integer.toString(query.getRowsPerPage()));
				List<ConsoleLoginAccount> list = consoleLoginAccountMapper
						.queryAccounts(query.getQueryData());
				result = new PageResult<ConsoleLoginAccount>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询当前用户的审核权限
	 * 
	 * @param vcLoginName
	 * @return
	 */
	public List<Integer> queryCurrentUserAudit(Integer id) {
		List<Integer> list = null;
		list = consoleLoginAccountMapper.queryCurrentUserAudit(id);
		return list;
	}

	/**
	 * 查询所有有效的管理端平台账户信息
	 * 
	 * @return
	 */
	public List<ConsoleLoginAccount> queryConsoleLoginAccountAll() {
		List<ConsoleLoginAccount> list = null;
		list = consoleLoginAccountMapper.queryConsoleLoginAccountAll();
		return list;
	}

	public int resetPwd(String id) throws Exception{
		ConsoleLoginAccount account = this
				.findById(Integer.parseInt(id));
		String pwd = RandomUtil
				.produceStringAndNumber(TzgConstant.MIN_PASSWORD_LENGTH);
		account.setVcLoginPassword(SHAUtil.encode(pwd));
		int flag = consoleLoginAccountMapper.updatePassword(account);
		if(flag==1){
			String smsContent = smssendService.getSmsContent(
					53,account.getVcLoginName(), pwd);
			smssendService.save(account.getVcPhone(), smsContent, 2);
		}
		return flag;
	}
}
