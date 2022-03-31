package com.evaluacion.java.bcp.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "ev_exchange_rate")
@Data
public class ExchangeRate implements Serializable{

	@Id
	@Column(name = "exchange_rate_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "amount")
	private Float amount;
	
	@Column( name = "currency_source")
	private String currencySource;
	
	@Column( name = "currency_target")
	private String currencyTarget;
	
	@Column(name = "exchange_rate_operation")
	private String exchangeOperation;
	
	@Column(name = "exchange_rate_price")
	private Float exchangePrice;
	
	@Column(name = "creation_user_id")
	private String creationUser;
	
	@Column(name ="creation_date")
	private LocalDateTime creationDate;
	
	@Column(name = "user_audit_date")
	private String userAuditDate;
	
	@Column(name = "audit_date")
	private LocalDateTime auditDate;
	
}
