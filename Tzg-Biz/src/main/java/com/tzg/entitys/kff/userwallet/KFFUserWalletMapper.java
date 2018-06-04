package com.tzg.entitys.kff.userwallet;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface KFFUserWalletMapper extends BaseMapper<KFFUserWallet, java.lang.Integer> {

	void findByUserId(Integer id);

	void updateWallet(KFFUserWallet kffUserWallet);

	KFFUserWallet findbyWallet(KFFUserWallet kffUserWallet);

	List<KFFUserWallet> findBywalletAndType(Integer userId);

	//KFFUserWallet updateUserWallet(Integer userId, String wallet);

	//KFFUserWallet updateUserWallet(KFFUserWallet kffUserWallet);

	KFFUserWallet updateUserWallet(String wallet, Integer kffUserWalletId);

	KFFUserWallet findUserId(Integer userId);

}
