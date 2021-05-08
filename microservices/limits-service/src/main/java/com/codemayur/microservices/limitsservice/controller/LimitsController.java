package com.codemayur.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemayur.microservices.limitsservice.bean.Limits;
import com.codemayur.microservices.limitsservice.configuration.LimitsConfiguration;

@RestController
public class LimitsController {

	@Autowired
	private LimitsConfiguration configuration;

	@GetMapping("/limits")
	public Limits getLimits() {
		return new Limits(configuration.getMinimum(),
				configuration.getMaximum());
	}

}
