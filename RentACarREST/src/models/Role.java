package models;

public enum Role {
	customer, manager, administrator;
	public String getRole() {
		if(this == customer) {
			return "C";
		}
		else if(this == manager){
			return "M";
		}
		else {
			return "A";
		}
	}
}
