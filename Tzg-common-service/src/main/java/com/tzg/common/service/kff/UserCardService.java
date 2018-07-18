package com.tzg.common.service.kff;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzg.entitys.kff.usercard.UserCard;
import com.tzg.entitys.kff.usercard.UserCardMapper;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "UserCardService")
@Transactional
public class UserCardService {
	private static final Log logger = LogFactory.getLog(UserCardService.class);

	@Autowired
	private UserCardMapper userCardMapper;

	@Transactional(readOnly = true)
	public List<UserCard> selectStatusByUserID(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		}
		return userCardMapper.selectStatusByUserID(id);
	}

	public void saveUserIdCard(UserCard userCard) throws RestServiceException {
		userCardMapper.save(userCard);
	}

	
	public void updataUserIdCard(UserCard userCard) {
		userCardMapper.updateUserCard(userCard);

	}

	@Transactional(readOnly = true)
	public UserCard findByUserid(java.lang.Integer userId) {
		UserCard userCard = null;
		List<UserCard> userCards = userCardMapper.findByUserid(userId);
		if (!CollectionUtils.isEmpty(userCards)) {
			if (userCards.size() != 0) {
				userCard = userCards.get(0);
			}
		}
		return userCard;

	}

	@Transactional(readOnly = true)
	public Integer selectUserCardNum(String userCardNum) {
		return userCardMapper.selectUserCardNum(userCardNum);

	}

	public Integer selectUserCardStatusByUserId(Integer userId) {
		List<UserCard> userCards = selectStatusByUserID(userId);
		if (userCards.size() == 0) {
			return 5;
		}

		UserCard userCard = userCards.get(0);
		return userCard.getStatus();

	}

}
