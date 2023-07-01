package models;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Vehicle {
	private int id;
	private String brand;
	private String model;
	private int price;
	private String type;
	private Gearshift gearshiftType;
	@JsonIgnoreProperties({"vehicles", "rentings"})
	private RentACar rentACar;
	private int RentACarId;
	private int consumption; //potrosnja
	private int doors;
	private int maxPeople;
	private String description;
	private String photo;
	private boolean available;
	private FuelType fuelType;
	public Vehicle() {
		
	}

	public Vehicle(int id, String brand, String model, int price, String type, Gearshift gearshiftType, RentACar rentACar, int rentACarId,
			int consumption, int doors, int maxPeople, String description, String photo, boolean available,
			FuelType fuelType) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.type = type;
		this.rentACar = rentACar;
		RentACarId = rentACarId;
		this.consumption = consumption;
		this.doors = doors;
		this.maxPeople = maxPeople;
		this.description = description;
		this.photo = photo;
		this.available = available;
		this.fuelType = fuelType;
		this.gearshiftType = gearshiftType;
	}
	
	public FuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRentACarId() {
		return RentACarId;
	}

	public void setRentACarId(int rentACarId) {
		RentACarId = rentACarId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public RentACar getRentACar() {
		return rentACar;
	}

	public void setRentACar(RentACar rentACar) {
		this.rentACar = rentACar;
	}

	public int getConsumption() {
		return consumption;
	}

	public void setConsumption(int consumption) {
		this.consumption = consumption;
	}

	public int getDoors() {
		return doors;
	}

	public void setDoors(int doors) {
		this.doors = doors;
	}

	public int getMaxPeople() {
		return maxPeople;
	}

	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Gearshift getGearshiftType() {
		return gearshiftType;
	}

	public void setGearshiftType(Gearshift gearshiftType) {
		this.gearshiftType = gearshiftType;
	}
	
}
