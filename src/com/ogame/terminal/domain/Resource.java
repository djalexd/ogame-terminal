package com.ogame.terminal.domain;

public class Resource {

	private String name;
	private long quantity;
	private long maxQuantity;
	private int production;

	public Resource(String name, long quantity, long maxQuantity, int production) {
		this.name = name;
		this.quantity = quantity;
		this.maxQuantity = maxQuantity;
		this.production = production;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public int getProduction() {
		return production;
	}
	public void setProduction(int production) {
		this.production = production;
	}
	public long getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(long maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	@Override
	public String toString() {
		return "Resource [name=" + name + ", quantity=" + quantity
				+ ", maxQuantity=" + maxQuantity + ", production=" + production
				+ "]";
	}
}
