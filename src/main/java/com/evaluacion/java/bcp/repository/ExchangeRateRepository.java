package com.evaluacion.java.bcp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.evaluacion.java.bcp.model.db.ExchangeRate;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>{
	
	@Transactional
	@Modifying
	@Query("update ExchangeRate a set a.amount = :amount, a.currencySource = :currencySource, a.currencyTarget = :currencyTarget,"
			+ " a.exchangeOperation = :exchangeOperation, a.exchangePrice = :exchangePrice, a.resultPrice = :resultPrice where a.id = :id")
	int updateExchangeRate(@Param("id") Long id, @Param("amount") float amount, @Param("currencySource") String currencySource,
			@Param("currencyTarget") String currencyTarget, @Param("exchangeOperation") String exchangeOperation,
			@Param("exchangePrice") Float exchangePrice, @Param("resultPrice") Float resultPrice);
	
	List<ExchangeRate> findByExchangeOperation(String exchangeOperation);
}
