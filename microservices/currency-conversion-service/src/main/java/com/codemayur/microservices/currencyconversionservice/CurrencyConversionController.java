package com.codemayur.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;

	@GetMapping("/currency-conversion/from/{fromCurrencyCode}/to/{toCurrencyCode}/quantity/{quantity}")
	public CurrencyConversion calculateConversion(@PathVariable String fromCurrencyCode,
			@PathVariable String toCurrencyCode,
			@PathVariable BigDecimal quantity) {

		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("fromCurrencyCode",
				fromCurrencyCode);
		uriVariables.put("toCurrencyCode",
				toCurrencyCode);

		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{fromCurrencyCode}/to/{toCurrencyCode}",
				CurrencyConversion.class,
				uriVariables);

		CurrencyConversion currencyConversion = responseEntity.getBody();

		if (currencyConversion == null) {
			throw new RuntimeException("Reponse was empty!");
		}

		return new CurrencyConversion(currencyConversion.getId(),
				fromCurrencyCode,
				toCurrencyCode,
				quantity,
				currencyConversion.getConversionMultiple(),
				currencyConversion.getConversionMultiple()
						.multiply(quantity),
				currencyConversion.getEnvironment() + " rest template");
	}

	@GetMapping("/currency-conversion-feign/from/{fromCurrencyCode}/to/{toCurrencyCode}/quantity/{quantity}")
	public CurrencyConversion calculateConversionFeign(@PathVariable String fromCurrencyCode,
			@PathVariable String toCurrencyCode,
			@PathVariable BigDecimal quantity) {

		CurrencyConversion currencyConversion = currencyExchangeProxy.getExchangeValue(fromCurrencyCode,
				toCurrencyCode);

		if (currencyConversion == null) {
			throw new RuntimeException("Reponse was empty!");
		}

		return new CurrencyConversion(currencyConversion.getId(),
				fromCurrencyCode,
				toCurrencyCode,
				quantity,
				currencyConversion.getConversionMultiple(),
				currencyConversion.getConversionMultiple()
						.multiply(quantity),
				currencyConversion.getEnvironment() + " feign");
	}

}
