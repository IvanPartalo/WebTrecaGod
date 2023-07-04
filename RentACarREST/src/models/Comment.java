package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Comment {
	private int id;
	private int grade;
	private String commentText;
	@JsonIgnoreProperties({"rentings", "shoppingCart"})
	private Customer customer;
	private int customerId;
	@JsonIgnoreProperties({"vehicles", "rentings"})
	private RentACar rentACar;
	private int rentACarId;
	private boolean approved;
	public Comment(int id, int grade, String commentText, Customer customer, int customerId, RentACar rentACar,
			int rentACarId, boolean approved) {
		super();
		this.id = id;
		this.grade = grade;
		this.commentText = commentText;
		this.customer = customer;
		this.customerId = customerId;
		this.rentACar = rentACar;
		this.rentACarId = rentACarId;
		this.approved = approved;
	}
	public Comment() {
		commentText = "";
		grade = 10;
	}
	public Comment(int id,  int customerId, int rentACarId, String commentText, int grade, boolean approved) {
		super();
		this.id = id;
		this.grade = grade;
		this.commentText = commentText;
		this.customerId = customerId;
		this.rentACarId = rentACarId;
		this.approved = approved;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public RentACar getRentACar() {
		return rentACar;
	}
	public void setRentACar(RentACar rentACar) {
		this.rentACar = rentACar;
	}
	public int getRentACarId() {
		return rentACarId;
	}
	public void setRentACarId(int rentACarId) {
		this.rentACarId = rentACarId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
}
