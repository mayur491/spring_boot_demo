package com.codemayur.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

	CurrencyExchange findByFromCurrencyCodeAndToCurrencyCode(String fromCurrencyCode,
			String toCurrencyCode);

}
