package models;

public class CustomerType {
	private Integer id;
	private String name;
	private double discount;
	private Integer requiredPoints;
	public CustomerType() {
	}
	
	public CustomerType(Integer id, String name, double discount, Integer requiredPoints) {
		super();
		this.id = id;
		this.name = name;
		this.discount = discount;
		this.requiredPoints = requiredPoints;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Integer getRequiredPoints() {
		return requiredPoints;
	}

	public void setRequiredPoints(Integer requiredPoints) {
		this.requiredPoints = requiredPoints;
	}
	
}
