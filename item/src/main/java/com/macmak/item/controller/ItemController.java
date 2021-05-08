package com.macmak.item.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macmak.item.entity.Item;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

	@GetMapping
	@RequestMapping("/{itemId}")
	public Optional<Item> getItemDetails(@PathVariable("itemId") int itemId) {

		// Database
		List<Item> itemList = Arrays.asList(
			new Item(
				Integer.valueOf(1),
				"OnePlus 6T 128GB Black",
				Double.valueOf(749l)
			),
			new Item(
				Integer.valueOf(2),
				"Dell XPS 15 512GB Grey",
				Double.valueOf(2000l)
			)
		);
		
		return itemList
			.stream()
			.filter(item -> item.getId() == itemId)
			.findFirst();
	}
}
