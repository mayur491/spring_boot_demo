package com.codemayur.microservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment environment;

	@Autowired
	private CurrencyExchangeRepository repository;

	@GetMapping("currency-exchange/from/{fromCurrencyCode}/to/{toCurrencyCode}")
	public CurrencyExchange getExchangeValue(@PathVariable String fromCurrencyCode,
			@PathVariable String toCurrencyCode) {

		CurrencyExchange currencyExchange = repository.findByFromCurrencyCodeAndToCurrencyCode(fromCurrencyCode,
				toCurrencyCode);
		if (currencyExchange == null) {
			throw new RuntimeException(String.format("Couldn't find data for from: %s to: %s",
					fromCurrencyCode,
					toCurrencyCode));
		}
		currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
		return currencyExchange;

	}

}
