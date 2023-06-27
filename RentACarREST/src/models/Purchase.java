package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Purchase {
	private String id;
	@JsonIgnoreProperties("rentACar")
	private ArrayList<Vehicle> vehicles;
	private ArrayList<Integer> vehicleIds;
	@JsonIgnoreProperties("rentings")
	private RentACar rentACar;
	private int rentACarId;
	private int price;
	private LocalDateTime start;
	private LocalDateTime end;
	private int duration; //in hours
	private String startDateTime;
	private String endDateTime;
	@JsonIgnoreProperties({"rentings", "shoppingCart"})
	private Customer customer;
	private int customerId;
	private PurchaseStatus status;
	public Purchase() {
		vehicles = new ArrayList<>();
		vehicleIds = new ArrayList<>();
		price = 0;
	}
	
	public Purchase(String id, ArrayList<Vehicle> vehicles, RentACar rentACar, int rentACarId, int customerId, int price,
			LocalDateTime start, LocalDateTime end, int duration, String startDateTime, String endDateTime,
			Customer customer, PurchaseStatus status) {
		super();
		this.id = id;
		this.vehicles = vehicles;
		this.rentACar = rentACar;
		this.rentACarId = rentACarId;
		this.customerId = customerId;
		this.price = price;
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.customer = customer;
		this.status = status;
		this.vehicleIds = new ArrayList<>();
	}
	public Purchase(String id, int rentACarId, int price, LocalDateTime start, 
			 LocalDateTime end, int duration, String startDateTime, String endDateTime, PurchaseStatus status, int customerId) {
		this.id = id;
		this.rentACarId = rentACarId;
		this.price = price;
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.status = status;
		this.customerId = customerId;
		vehicles = new ArrayList<>();
		vehicleIds = new ArrayList<>();
	}
	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public PurchaseStatus getStatus() {
		return status;
	}
	public void setStatus(PurchaseStatus status) {
		this.status = status;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public ArrayList<Integer> getVehicleIds() {
		return vehicleIds;
	}

	public void setVehicleIds(ArrayList<Integer> vehicleIds) {
		this.vehicleIds = vehicleIds;
	}
	public void addPrice(int p) {
		this.price += p;
	}
	public void removePrice(int p) {
		this.price -= p;
	}
}
