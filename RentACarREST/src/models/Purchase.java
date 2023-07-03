package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Purchase {
	private String id;
	@JsonIgnoreProperties("rentACar")
	private ArrayList<Vehicle> vehicles;
	private ArrayList<Integer> vehicleIds;
	@JsonIgnoreProperties({"rentings", "vehicles"})
	private HashSet<RentACar> rentACars;
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
	private ArrayList<SubPurchase> subPurchases;
	public Purchase() {
		this.vehicles = new ArrayList<>();
		this.vehicleIds = new ArrayList<>();
		this.subPurchases = new ArrayList<>();
		this.rentACars = new HashSet<RentACar>();
		price = 0;
	}
	
	public Purchase(String id, ArrayList<Vehicle> vehicles, ArrayList<Integer> vehicleIds, HashSet<RentACar> rentACars,
			int price, LocalDateTime start, LocalDateTime end, int duration, String startDateTime, String endDateTime,
			Customer customer, int customerId, PurchaseStatus status, ArrayList<SubPurchase> subPurchases) {
		super();
		this.id = id;
		this.vehicles = vehicles;
		this.vehicleIds = vehicleIds;
		this.rentACars = rentACars;
		this.price = price;
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.customer = customer;
		this.customerId = customerId;
		this.status = status;
		this.subPurchases = subPurchases;
	}

	public Purchase(String id, ArrayList<Vehicle> vehicles, HashSet<RentACar> rentACars, int customerId, int price,
			LocalDateTime start, LocalDateTime end, int duration, String startDateTime, String endDateTime,
			Customer customer, PurchaseStatus status) {
		super();
		this.id = id;
		this.vehicles = vehicles;
		this.rentACars = rentACars;
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
		this.subPurchases = new ArrayList<>();
	}
	public Purchase(String id, int price, LocalDateTime start, 
			 LocalDateTime end, int duration, String startDateTime, String endDateTime, PurchaseStatus status, int customerId) {
		this.id = id;
		this.price = price;
		this.start = start;
		this.end = end;
		this.duration = duration;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.status = status;
		this.customerId = customerId;
		this.vehicles = new ArrayList<>();
		this.vehicleIds = new ArrayList<>();
		this.subPurchases = new ArrayList<>();
		this.rentACars = new HashSet<RentACar>();
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
	public HashSet<RentACar> getRentACars() {
		return rentACars;
	}
	public void setRentACars(HashSet<RentACar> rentACars) {
		this.rentACars = rentACars;
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
	public void removeVehicleId(Integer id) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(Integer i : this.vehicleIds) {
			if(i != id) {
				result.add(i);
			}
		}
		this.vehicleIds = result;
	}
	public void addPrice(int p) {
		this.price += p;
	}
	public void removePrice(int p) {
		this.price -= p;
	}
	public ArrayList<SubPurchase> getSubPurchases() {
		return subPurchases;
	}
	public void setSubPurchases(ArrayList<SubPurchase> subPurchases) {
		this.subPurchases = subPurchases;
	}
}
