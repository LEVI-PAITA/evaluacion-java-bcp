package com.evaluacion.java.bcp.business;

import java.util.List;

import com.evaluacion.java.bcp.model.db.ExchangeRate;
import com.evaluacion.java.bcp.model.request.ExchangeRateRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeRateService {

	Mono<ExchangeRate> createExchangeRate(ExchangeRateRequest request);
	
	int updateExchangeRate(Long id, ExchangeRateRequest request);
	
	Flux<List<ExchangeRate>> getExchangeRateOperation(String exchangeRateOperation);
	
}
