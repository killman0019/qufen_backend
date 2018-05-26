package com.tzg.entitys.leopard.console;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by cfour on 12/1/14.
 */
@Component
public interface ConsoleLoginAccountMapper {
    ConsoleLoginAccount selectByName(String loginName);

    ConsoleLoginAccount findById(int id);

    int save(ConsoleLoginAccount account);

    int update(ConsoleLoginAccount account);

    int queryAccountsCount(Map<String,String> map);

    void delete(int id);

    int updatePassword(ConsoleLoginAccount account);

    List<ConsoleLoginAccount> queryAccounts(Map<String,String> map);
    
    /**
     * 查询当前用户的审核权限
     * @param vcLoginName
     * @return
     */
    public List<Integer> queryCurrentUserAudit(Integer id);
    
    /**
     * 查询所有有效的管理端平台账户信息
     * @return
     */
    public List<ConsoleLoginAccount> queryConsoleLoginAccountAll();
    
    
}
