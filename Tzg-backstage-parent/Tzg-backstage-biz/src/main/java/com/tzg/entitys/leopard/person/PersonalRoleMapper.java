package com.tzg.entitys.leopard.person;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cfour on 12/1/14.
 */
@Component
public interface PersonalRoleMapper {
    List<String> findRoleByAccountId(int accountId);

    int insertByList(List<PersonalRole> personalRoleList);

    void deleteByAccount(int id);
}
