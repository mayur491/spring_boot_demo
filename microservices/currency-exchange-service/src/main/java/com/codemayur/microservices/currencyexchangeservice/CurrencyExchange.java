package com.codemayur.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CurrencyExchange {

	@Id
	private Long id;
	private String fromCurrencyCode;
	private String toCurrencyCode;
	private BigDecimal conversionMultiple;
	private String environment;

	public CurrencyExchange(Long id,
			String from,
			String to,
			BigDecimal conversionMultiple) {
		super();
		this.id = id;
		this.fromCurrencyCode = from;
		this.toCurrencyCode = to;
		this.conversionMultiple = conversionMultiple;
	}

}
