package models;

public enum Status {
	work, doesNotWork;
	public String getRole() {
		if(this == work) {
			return "W";
		}
		else {
			return "D";
		}
	}
}
