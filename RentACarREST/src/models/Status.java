package models;

public enum Status {
	working, notWorking;
	public String getRole() {
		if(this == working) {
			return "W";
		}
		else {
			return "D";
		}
	}
}
