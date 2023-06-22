package models;

import java.util.ArrayList;
import java.util.Date;

public class Manager extends User{
	private int rentACarId;
	private RentACar rentACar;
	public Manager() {
		
	}
	public Manager(Integer id, String username, String password, String firstName, String lastName, Gender gender, Role role, Date dateOfBirth, int rentACarId) {
		super(id, username, password, firstName, lastName, gender, role, dateOfBirth);
		this.rentACarId = rentACarId;
	}
	public int getRentACarId() {
		return rentACarId;
	}
	public void setRentACarId(int rentACarId) {
		this.rentACarId = rentACarId;
	}
	public RentACar getRentACar() {
		return rentACar;
	}
	public void setRentACar(RentACar rentACar) {
		this.rentACar = rentACar;
	}
	
}
