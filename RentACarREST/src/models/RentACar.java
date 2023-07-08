package models;

import java.util.ArrayList;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
public class RentACar {
	private Integer id;
	private int locationId;
	private String name;
	private int startHour;
	private int startMinute;
	private int endHour;
	private int endMinute;
	private Status status;
	private String logoImg;
	private int logoId;
	private double Grade;
	private Location location;
	private ArrayList<Purchase> rentings;
	@JsonIgnoreProperties("rentACar")
	private ArrayList<Vehicle> vehicles;
	private double sumGrades;
	private double gradesCount;
	public RentACar() {
		rentings = new ArrayList<>();
		vehicles = new ArrayList<Vehicle>();
	}
	public RentACar(Integer id, int locationId, String name, int startHour, int startMinute, int endHour, int endMinute, Status status,
			int logoId, double grade) {
		this.id = id;
		this.locationId = locationId;
		this.name = name;
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
		this.status = status;
		this.logoId = logoId;
		Grade = grade;
		rentings = new ArrayList<>();
		vehicles = new ArrayList<Vehicle>();
		this.sumGrades = 0;
		this.gradesCount = 0;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getLogoId() {
		return logoId;
	}
	public void setLogoId(int logoId) {
		this.logoId = logoId;
	}
	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStartHour() {
		return startHour;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public int getStartMinute() {
		return startMinute;
	}
	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}
	public int getEndHour() {
		return endHour;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	public int getEndMinute() {
		return endMinute;
	}
	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getLogoImg() {
		return logoImg;
	}
	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}
	public double getGrade() {
		return Grade;
	}
	public void setGrade(double grade) {
		Grade = grade;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public ArrayList<Purchase> getRentings() {
		return rentings;
	}
	public void setRentings(ArrayList<Purchase> rentings) {
		this.rentings = rentings;
	}
	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	public void addGrade(double g) {
		this.sumGrades += g;
		this.gradesCount++;
		this.Grade = this.sumGrades/this.gradesCount;
	}
	public void setSumGradesCountZero() {
		this.sumGrades = 0;
		this.gradesCount = 0;
	}
}
