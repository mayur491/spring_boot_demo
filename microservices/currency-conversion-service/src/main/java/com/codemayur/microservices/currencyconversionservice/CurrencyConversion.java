package com.codemayur.microservices.currencyconversionservice;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CurrencyConversion {

	private Long id;
	private String fromCountryCode;
	private String toCountryCode;
	private BigDecimal quantity;
	private BigDecimal conversionMultiple;
	private BigDecimal totalCalulatedAmount;
	private String environment;

}
