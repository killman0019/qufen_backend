package com.tzg.common.service.kff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.entitys.kff.exchange.ExchangeMapper;

@Service(value = "excahngeService")
@Transactional
public class ExchangeService {
	@Autowired
	private ExchangeMapper exchangeMapper;
}
