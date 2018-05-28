package com.tzg.entitys.kff.userwallet;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface KFFUserWalletMapper extends BaseMapper<KFFUserWallet, java.lang.Integer> {

	void findByUserId(Integer id);

	void updateWallet(KFFUserWallet kffUserWallet);

	KFFUserWallet findbyWallet(KFFUserWallet kffUserWallet);



}
