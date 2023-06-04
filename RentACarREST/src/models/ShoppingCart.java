package models;

import java.util.ArrayList;

public class ShoppingCart {
	private Integer id;
	private ArrayList<Vehicle> vehicles;
	private User user;
	private double price;
	public ShoppingCart() {
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
