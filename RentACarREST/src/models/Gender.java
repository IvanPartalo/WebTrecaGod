package models;

public enum Gender {
	male, female;
	public String getGender() {
		if(this == male) {
			return "M";
		}
		else {
			return "F";
		}
	}
}
