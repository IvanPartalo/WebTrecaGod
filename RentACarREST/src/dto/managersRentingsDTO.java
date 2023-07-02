package dto;

import java.util.ArrayList;

import models.Purchase;

public class managersRentingsDTO {
	private ArrayList<Purchase> rentings;
	private Integer rentACarId;
	
	public managersRentingsDTO(ArrayList<Purchase> rentings, Integer rentACarId) {
		super();
		this.rentings = rentings;
		this.rentACarId = rentACarId;
	}
	public ArrayList<Purchase> getRentings() {
		return rentings;
	}
	public void setRentings(ArrayList<Purchase> rentings) {
		this.rentings = rentings;
	}
	public Integer getRentACarId() {
		return rentACarId;
	}
	public void setRentACarId(Integer rentACarId) {
		this.rentACarId = rentACarId;
	}
}
