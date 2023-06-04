package models;

import java.util.ArrayList;
import java.util.Date;

public class Customer extends User {
	private Integer collectedPoints;
	private ShoppingCart shoppingCart;
	private CustomerType customerType;
	private ArrayList<Renting> rentings;
	
	public Customer() {
		
	}
	public Customer(Integer id, String username, String password, String firstName, String lastName, Gender gender, Role role, Date dateOfBirth, Integer collectedPoints, ShoppingCart shoppingCart, CustomerType customerType,
			ArrayList<Renting> rentings) {
		super(id, username, password, firstName, lastName, gender, role, dateOfBirth);
		this.collectedPoints = collectedPoints;
		this.shoppingCart = shoppingCart;
		this.customerType = customerType;
		this.rentings = rentings;
	}
	public Customer(Integer id, String username, String password, String firstName, String lastName, Gender gender, Role role, Date dateOfBirth, Integer collectedPoints) {
		super(id, username, password, firstName, lastName, gender, role, dateOfBirth);
		this.collectedPoints = collectedPoints;
	}
	public Integer getCollectedPoints() {
		return collectedPoints;
	}
	public void setCollectedPoints(Integer collectedPoints) {
		this.collectedPoints = collectedPoints;
	}
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	public ArrayList<Renting> getRentings() {
		return rentings;
	}
	public void setRentings(ArrayList<Renting> rentings) {
		this.rentings = rentings;
	}
	public String toCSVString() {
		StringBuilder csvBuidler = new StringBuilder();
		csvBuidler.append(super.getId().toString());
		csvBuidler.append(";");
		csvBuidler.append(super.getUsername());
		csvBuidler.append(";");
		csvBuidler.append(super.getPassword());
		csvBuidler.append(";");
		csvBuidler.append(super.getFirstName());
		csvBuidler.append(";");
		csvBuidler.append(super.getLastName());
		csvBuidler.append(";");
		csvBuidler.append(super.getGender().toString());
		csvBuidler.append(";");
		csvBuidler.append(collectedPoints.toString());
		csvBuidler.append(";");
		csvBuidler.append(getDateOfBirth().toString());
		return csvBuidler.toString();
	}
	
}
