package dto;

public class RentACarDTO {
	private String name;
	private String address;
	private String logo;
	private double longitude;
	private double latitude;
	private String beginWorkTime;
	private String endWorkTime;
	public RentACarDTO() {

	}
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getBeginWorkTime() {
		return beginWorkTime;
	}
	public void setBeginWorkTime(String beginWorkTime) {
		this.beginWorkTime = beginWorkTime;
	}
	public String getEndWorkTime() {
		return endWorkTime;
	}
	public void setEndWorkTime(String endWorkTime) {
		this.endWorkTime = endWorkTime;
	}
	
}
