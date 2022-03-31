package com.evaluacion.java.bcp.business.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evaluacion.java.bcp.business.ExchangeRateService;
import com.evaluacion.java.bcp.model.db.ExchangeRate;
import com.evaluacion.java.bcp.repository.ExchangeRateRepository;

import reactor.core.publisher.Mono;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
	
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	@Override
	public Mono<ExchangeRate> createExchangeRate(ExchangeRate request) {
		
		return Mono.just(request)
				.flatMap(req -> {
					
					ExchangeRate exchangeRate = new ExchangeRate();
					//exchangeRate.setId(request.getId());
					exchangeRate.setAmount(request.getAmount());
					exchangeRate.setCurrencySource(request.getCurrencySource());
					exchangeRate.setCurrencyTarget(request.getCurrencyTarget());
					exchangeRate.setExchangeOperation(request.getExchangeOperation());
					exchangeRate.setExchangePrice(request.getExchangePrice());
					exchangeRate.setCreationUser("PRU");
					exchangeRate.setCreationDate(LocalDateTime.now());
					exchangeRate.setUserAuditDate("TEST");
					exchangeRate.setAuditDate(LocalDateTime.now());
					
					return Mono.just(exchangeRate);
				})
				.cache()
				.flatMap(exchangeRate -> Mono.just(exchangeRateRepository.save(exchangeRate)))
				.retry(1)
				.onErrorResume(error -> {
					LOGGER.error("No se pudo guardar la transaccion en la BD. Error : {}", error.getMessage());
					return Mono.just(new ExchangeRate());
				});
		
	}

}
