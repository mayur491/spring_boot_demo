package com.macmak.cart.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.macmak.cart.entity.Item;
import com.macmak.cart.entity.Items;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@GetMapping
	public Items getCartDetails() {
		
		List<Integer> itemIdList = Arrays.asList(
			1,2,null,4
		);
		RestTemplate restTemplate = new RestTemplate();
		
		return new Items(itemIdList
			.parallelStream()
			.filter(Objects::nonNull)
			.map(itemId -> 
				restTemplate.getForObject(
					"http://localhost:8082/item/" + itemId,
					Item.class
				)
			)
			.filter(Objects::nonNull)
			.collect(Collectors.toList())
		);
	}
}
