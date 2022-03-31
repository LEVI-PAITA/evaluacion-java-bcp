package com.evaluacion.java.bcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evaluacion.java.bcp.business.ExchangeRateService;
import com.evaluacion.java.bcp.model.db.ExchangeRate;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/exchange-rate")
public class ExchangeRateController {

	@Autowired
	private ExchangeRateService exchangeRateService;
	
	@PostMapping("/v1")
	public Mono<ResponseEntity<ExchangeRate>> crearExhangeRate(@RequestBody ExchangeRate request){
		
		return exchangeRateService.createExchangeRate(request).map(exchange -> {
			return new ResponseEntity<>(exchange, HttpStatus.CREATED);
		});
		
	}
}
