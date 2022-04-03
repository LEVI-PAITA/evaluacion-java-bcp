package com.evaluacion.java.bcp.model.request;

import lombok.Data;

@Data
public class ExchangeRateRequest {

	private Float amount;
	
	private String currencySource;
	
	private String currencyTarget;
	
	private String exchangeOperation;
	
	private Float exchangePrice;
	
	private Float resultPrice;
}
