package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class SubPurchase {
	private String purchaseId;
	@JsonIgnoreProperties({"rentings", "vehicles"})
	private RentACar rentACar;
	private Integer rentACarId;
	private String startDateTime;
	private LocalDateTime start;
	private int duration; //in hours
	private PurchaseStatus status;
	private boolean fromCurrentRentACar;
	public SubPurchase(String purchaseId, Integer rentACarId, RentACar rentACar, LocalDateTime start, int duration, String startDateTime,
			PurchaseStatus status) {
		super();
		this.purchaseId = purchaseId;
		this.rentACar = rentACar;
		this.rentACarId = rentACarId;
		this.start = start;
		this.duration = duration;
		this.startDateTime = startDateTime;
		this.status = status;
		this.fromCurrentRentACar = false;
	}
	public SubPurchase(String purchaseId, Integer rentACarId, int duration, String startDateTime, PurchaseStatus status) {
		super();
		this.purchaseId = purchaseId;
		this.rentACarId = rentACarId;
		this.duration = duration;
		this.startDateTime = startDateTime;
		this.status = status;
		this.fromCurrentRentACar = false;
	}
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	public RentACar getRentACar() {
		return rentACar;
	}
	public void setRentACar(RentACar rentACar) {
		this.rentACar = rentACar;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public PurchaseStatus getStatus() {
		return status;
	}
	public void setStatus(PurchaseStatus status) {
		this.status = status;
	}
	public Integer getRentACarId() {
		return rentACarId;
	}
	public void setRentACarId(Integer rentACarId) {
		this.rentACarId = rentACarId;
	}
	public boolean isFromCurrentRentACar() {
		return fromCurrentRentACar;
	}
	public void setFromCurrentRentACar(boolean fromCurrentRentACar) {
		this.fromCurrentRentACar = fromCurrentRentACar;
	}
	
}
