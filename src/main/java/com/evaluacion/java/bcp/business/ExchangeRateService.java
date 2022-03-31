package com.evaluacion.java.bcp.business;

import com.evaluacion.java.bcp.model.db.ExchangeRate;

import reactor.core.publisher.Mono;

public interface ExchangeRateService {

	Mono<ExchangeRate> createExchangeRate(ExchangeRate request);
}
