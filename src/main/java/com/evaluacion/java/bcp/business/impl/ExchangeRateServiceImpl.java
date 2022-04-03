package com.evaluacion.java.bcp.business.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evaluacion.java.bcp.business.ExchangeRateService;
import com.evaluacion.java.bcp.model.db.ExchangeRate;
import com.evaluacion.java.bcp.model.request.ExchangeRateRequest;
import com.evaluacion.java.bcp.repository.ExchangeRateRepository;
import com.evaluacion.java.bcp.util.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
	
	private float calculation = 0;
	private static float typeExchangeVenta = (float) 3.80;
	private static float typeExchangeCompra = (float) 3.90;
	
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	@Override
	public Mono<ExchangeRate> createExchangeRate(ExchangeRateRequest request) {
		
		
		return Mono.just(request)
				.flatMap(req -> {
					
					ExchangeRate exchangeRate = new ExchangeRate();
					
					if(request.getExchangeOperation().equals(Constants.VENTA) &&
							request.getCurrencySource().equals(Constants.PEN)) {
						
						calculation = request.getAmount() / typeExchangeVenta;
						exchangeRate.setExchangeOperation(Constants.VENTA);
						exchangeRate.setCurrencyTarget(Constants.USD);
						exchangeRate.setExchangePrice(typeExchangeVenta);
						exchangeRate.setResultPrice(calculation);
						
					}else if(request.getExchangeOperation().equals(Constants.VENTA) &&
							request.getCurrencySource().equals(Constants.USD)){
						
						calculation = request.getAmount() * typeExchangeVenta;
						exchangeRate.setExchangeOperation(Constants.VENTA);
						exchangeRate.setCurrencyTarget(Constants.PEN);
						exchangeRate.setExchangePrice(typeExchangeVenta);
						exchangeRate.setResultPrice(calculation);
						
					}else if(request.getExchangeOperation().equals(Constants.COMPRA) &&
							request.getCurrencySource().equals(Constants.PEN)) {
						
						calculation = request.getAmount() / typeExchangeCompra;
						exchangeRate.setExchangeOperation(Constants.COMPRA);
						exchangeRate.setCurrencyTarget(Constants.USD);
						exchangeRate.setExchangePrice(typeExchangeCompra);
						exchangeRate.setResultPrice(calculation);
						
					}else if(request.getExchangeOperation().equals(Constants.COMPRA) &&
							request.getCurrencySource().equals(Constants.USD)) {
						
						calculation = request.getAmount() * typeExchangeCompra;
						exchangeRate.setExchangeOperation(Constants.COMPRA);
						exchangeRate.setCurrencyTarget(Constants.PEN);
						exchangeRate.setExchangePrice(typeExchangeCompra);
						exchangeRate.setResultPrice(calculation);
						
					}
					
					exchangeRate.setAmount(request.getAmount());
					exchangeRate.setCurrencySource(request.getCurrencySource());
					exchangeRate.setCreationUser("EVALUACION");
					exchangeRate.setCreationDate(LocalDateTime.now());
					exchangeRate.setUserAuditDate("EVALUACION");
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

	@Override
	public int updateExchangeRate(Long id, ExchangeRateRequest request) {

		return exchangeRateRepository.updateExchangeRate(id, request.getAmount(), request.getCurrencySource(), request.getCurrencyTarget(), 
				request.getExchangeOperation(), request.getExchangePrice(), request.getResultPrice());
	}

	@Override
	public Flux<List<ExchangeRate>> getExchangeRateOperation(String exchangeRateOperation) {
		return Flux.just(exchangeRateRepository.findByExchangeOperation(exchangeRateOperation));
	}


}
