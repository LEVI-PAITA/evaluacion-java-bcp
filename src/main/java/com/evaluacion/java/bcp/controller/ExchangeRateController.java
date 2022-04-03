package com.evaluacion.java.bcp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evaluacion.java.bcp.business.ExchangeRateService;
import com.evaluacion.java.bcp.model.db.ExchangeRate;
import com.evaluacion.java.bcp.model.request.ExchangeRateRequest;
import com.evaluacion.java.bcp.model.response.GeneralResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/exchange-rate")
public class ExchangeRateController {

	@Autowired
	private ExchangeRateService exchangeRateService;
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/v1")
	public Mono<ResponseEntity<ExchangeRate>> crearExchangeRate(@RequestBody ExchangeRateRequest request){
		
		return exchangeRateService.createExchangeRate(request).map(exchange -> {
			return new ResponseEntity<>(exchange, HttpStatus.CREATED);
		});
		
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping(
			path = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<GeneralResponse<ExchangeRate>> updateExchangeRate(@PathVariable("id") Long id,
			@RequestBody ExchangeRateRequest requestUpdate){
		
		int cuenta = exchangeRateService.updateExchangeRate(id, requestUpdate);
		
		if (cuenta > 0) {
			return new ResponseEntity<>(
					GeneralResponse.<ExchangeRate>builder()
									.success(true)
									.message("updated successfully")
									.build(),
									HttpStatus.ACCEPTED
					);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					(new GeneralResponse<>()));
		
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<List<ExchangeRate>> getExchangeRateOperation(@RequestParam(value = "exchangeOperation") String exchangeOperation){
		
		return exchangeRateService.getExchangeRateOperation(exchangeOperation);
	}

}
