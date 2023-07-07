package models;

import java.util.Date;

public class Canceling {
	private int customerId;
	private Date date;
	public Canceling(int customerId, Date date) {
		super();
		this.customerId = customerId;
		this.date = date;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
