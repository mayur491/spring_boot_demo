package com.macmak.cart.entity;

import java.util.List;

public class Items {

	private List<Item> itemList;

	public Items() {
	}
	
	public Items(List<Item> itemList) {
		this.itemList = itemList;
	}
	
	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		return "Items [itemList=" + itemList + "]";
	}

}
