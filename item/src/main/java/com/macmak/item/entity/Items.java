package com.macmak.item.entity;

import java.util.List;

public class Items {

	private List<Item> itemList;

	public List<Item> getItems() {
		return itemList;
	}

	public void setItems(List<Item> items) {
		this.itemList = items;
	}

	@Override
	public String toString() {
		return "Items [items=" + itemList + "]";
	}

}
