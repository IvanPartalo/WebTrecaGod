package dto;

import java.util.Date;

import models.CustomerType;
import models.Gender;
import models.Role;

public class UserDTO {
	private String username;
	private Integer id;
	private String firstName;
	private String lastName;
	private Gender gender;
	private Date dateOfBirth;
	private Role role;
	private Double collectedPoints;
	private String customerType;
	private Boolean blocked;
	public UserDTO() {
		
	}
	public UserDTO(Integer id, String username,
			String firstName, String lastName, Gender gender,
			Role role, Date dateOfBirth) {
		super();
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.role = role;
		this.dateOfBirth = dateOfBirth;
		this.blocked = false;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Double getCollectedPoints() {
		return collectedPoints;
	}
	public void setCollectedPoints(Double collectedPoints) {
		this.collectedPoints = collectedPoints;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getBlocked() {
		return blocked;
	}
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
	
}
