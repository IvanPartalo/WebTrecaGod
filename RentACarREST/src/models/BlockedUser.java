package models;

public class BlockedUser {
	private int usersId;

	public BlockedUser(int usersId) {
		super();
		this.usersId = usersId;
	}

	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}
	
}
