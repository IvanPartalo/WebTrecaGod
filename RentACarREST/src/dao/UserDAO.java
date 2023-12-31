package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import dto.UserDTO;
import models.Customer;
import models.CustomerType;
import models.Gender;
import models.Manager;
import models.Role;
import models.ShoppingCart;
import models.User;

public class UserDAO {
	private HashMap<Integer, User> users = new HashMap<>();
	private String path = null;
	
	public UserDAO() {
	}
	@SuppressWarnings("deprecation")
	public UserDAO(String contextPath) {
		Calendar cal = Calendar.getInstance();
		path = contextPath;
		loadUsers(path);
		buildCustomers(path);
		buildManagers(contextPath);
	}
	
	public void loadUsers(String contextPath) {
		BufferedReader in = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			File file = new File(contextPath + "/users.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line, id = "", username = "", password = "", firstName = "", lastName = "", gender = "", role = "", dateOfBirth="";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					username = st.nextToken().trim();
					password = st.nextToken().trim();
					firstName = st.nextToken().trim();
					lastName = st.nextToken().trim();
					gender = st.nextToken().trim();
					role = st.nextToken().trim();
					dateOfBirth = st.nextToken().trim();
				}
				User newUser = new User(Integer.parseInt(id), username, password, firstName, lastName, Gender.valueOf(gender) , Role.valueOf(role), formatter.parse(dateOfBirth));
				users.put(Integer.parseInt(id), newUser);
			}
		} catch (Exception e) {
			System.out.println("greska");
			e.printStackTrace();
		} finally {
			if ( in != null ) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
	public void buildCustomers(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/customers.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line,  userId = "", collectedPoints = "";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					userId = st.nextToken().trim();
					collectedPoints = st.nextToken().trim();
				}
				User newUser = users.get(Integer.parseInt(userId));
				if(newUser.getRole() == Role.customer) {
					users.remove(Integer.parseInt(userId));
					users.put(Integer.parseInt(userId), new Customer(newUser, Double.parseDouble(collectedPoints)));
				}
			}
		} catch (Exception e) {
			System.out.println("greska");
			e.printStackTrace();
		} finally {
			if ( in != null ) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
	public void buildManagers(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/managers.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line,  userId = "", objectId = "";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					userId = st.nextToken().trim();
					objectId = st.nextToken().trim();
				}
				User newUser = users.get(Integer.parseInt(userId));
				if(newUser.getRole() == Role.manager) {
					users.remove(Integer.parseInt(userId));
					users.put(Integer.parseInt(userId), new Manager(newUser, Integer.parseInt(objectId)));
				}
			}
		} catch (Exception e) {
			System.out.println("greska");
			e.printStackTrace();
		} finally {
			if ( in != null ) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
	public Collection<User> getAll(){
		return users.values();
	}
	public ArrayList<UserDTO> getAllUsersDTO(){
		ArrayList<UserDTO> usersToShow = new ArrayList<>();
		for(User user : users.values()) {
			UserDTO u = new UserDTO(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getGender(), user.getRole(),
					user.getDateOfBirth());
			if(user.getRole() == Role.customer) {
				Customer c = (Customer)user;
				u.setCollectedPoints(c.getCollectedPoints());
				u.setCustomerType(c.getCustomerType().getName());
			}
			usersToShow.add(u);
		}
		return usersToShow;
	}
	public ArrayList<Customer> getAllCustomers(){
		ArrayList<Customer> customers = new ArrayList<>();
		for(User user : users.values()) {
			if(user.getRole() == Role.customer) {
				customers.add((Customer)user);
			}
		}
		return customers;
	}
	public User getById(Integer id){
		return users.get(id);
	}
	public String saveManager(Manager m, int idR){
		if(m.getUsername() == null || m.getFirstName() == null || m.getPassword() == null || m.getDateOfBirth() == null) {
			return "Can not register this user, not all fields have been filled correctly";
		}
		if(m.getUsername().isBlank() || m.getFirstName().isBlank() || m.getPassword().isBlank()) {
			return "Can not register this user, not all fields have been filled correctly";
		}
		if(!isUsernameUnique(m.getUsername())) {
			return "This username already exists, please choose a different one";
		}
		Integer maxId = 0;
		for (Integer id : users.keySet()) {
			if (id > maxId) {
				maxId = id;
			}
		}
		maxId++;
		m.setId(maxId);
		m.setRentACarId(idR);
		users.put(m.getId(), m);
		SaveUsersToFile();
		SaveManagersToFile();
		return "ok";
	}
	public String saveCustomer(User c){
		if(c.getUsername() == null || c.getFirstName() == null || c.getPassword() == null
				|| c.getFirstName() == null || c.getLastName() == null || c.getGender() == null || c.getDateOfBirth() == null) {
			return "Can not register this user, not all fields have been filled correctly";
		}
		if(c.getUsername().isBlank() || c.getFirstName().isBlank() || c.getPassword().isBlank()
				|| c.getFirstName().isBlank() || c.getLastName().isBlank()) {
			return "Can not register this user, not all fields have been filled correctly";
		}
		if(!isUsernameUnique(c.getUsername())) {
			return "This username already exists, please choose a different one";
		}
		Integer maxId = 0;
		for (Integer id : users.keySet()) {
			if (id > maxId) {
				maxId = id;
			}
		}
		maxId++;
		c.setId(maxId);
		if(c.getRole() == Role.customer) {
			//new user starts with 50 points
			Customer p = new Customer(c, 50.0);
			users.put(c.getId(), p);
		}else {
			users.put(c.getId(), c);
		}
		SaveUsersToFile();
		SaveCustomersToFile();
		return "ok";
	}
	public void SaveAll() {
		SaveUsersToFile();
		SaveCustomersToFile();
		SaveManagersToFile();
	}
	public Boolean isUsernameUnique(String username) {
		for(User u : users.values()) {
			if(u.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	public String editCustomer(User c){
		if(c.getFirstName().isBlank() || c.getLastName().isBlank()) {
			return "First name and last name can't contain only white spaces";
		}
		if(c.getFirstName().isEmpty() || c.getLastName().isEmpty()) {
			return "First name and last name can't be empty";
		}
		User oldCustomer = users.get(c.getId());
		oldCustomer.setUsername(c.getUsername());
		oldCustomer.setPassword(c.getPassword());
		oldCustomer.setFirstName(c.getFirstName());
		oldCustomer.setLastName(c.getLastName());
		oldCustomer.setGender(c.getGender());
		oldCustomer.setDateOfBirth(c.getDateOfBirth());
		SaveUsersToFile();
		SaveCustomersToFile();
		return "ok";
	}
	public boolean changePassword(Integer id, String oldPassword, String newPassword) {
		User c = getById(id);
		if(c.getPassword().equals(oldPassword)) {
			c.setPassword(newPassword);
			SaveUsersToFile();
			return true;
		}
		else {
			return false;
		}
	}
	public User find(String username, String password) {
		for(User c : users.values()){
			if (c.getUsername().equals(username)) {
				if(c.getPassword().equals(password)) {
					return c;
				}
			}
		}
		return null;
	}
	public ArrayList<Manager> getFreeManagers(){
		ArrayList<Manager> freeManagers = new ArrayList<Manager>();
		for(User u : users.values()) {
			if(u.getRole() == Role.manager) {
				Manager m = (Manager)u;
				if(m.getRentACarId() == -1) {
					freeManagers.add(m);
				}
			}
		}
		return freeManagers;
	}
	public void updateManager(int managerId, int rentACarId) {
		for(User u : users.values()) {
			if(u.getRole() == Role.manager) {
				Manager m = (Manager)u;
				if(m.getId() == managerId) {
					m.setRentACarId(rentACarId);
					break;
				}
			}
		}
		SaveAll();
	}
	private void SaveUsersToFile() {
		BufferedWriter bw = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			File fout = new File(path + "/users.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(User c : users.values()) {
				String date = formatter.format(c.getDateOfBirth());
				String lineToWrite = 
				c.getId()+";"+c.getUsername()+";"+c.getPassword()+";"+c.getFirstName()+";"+c.getLastName()+";"+c.getGender()+";"+c.getRole()+";"+date+";";
				bw.write(lineToWrite);
				bw.newLine();
			}
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bw!=null) {
				try {
					bw.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}	
	private void SaveCustomersToFile() {
		BufferedWriter bw = null;
		try {
			File fout = new File(path + "/customers.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(User c : users.values()) {
				if(c.getRole() == Role.customer) {
					Customer p = (Customer)c;
					String lineToWrite = c.getId()+";" + p.getCollectedPoints() + ";";
					bw.write(lineToWrite);
					bw.newLine();
				}
			}
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bw!=null) {
				try {
					bw.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
	private void SaveManagersToFile() {
		BufferedWriter bw = null;
		try {
			File fout = new File(path + "/managers.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(User c : users.values()) {
				if(c.getRole() == Role.manager) {
					Manager m = (Manager)c;
					String lineToWrite = c.getId()+";" + m.getRentACarId() + ";";
					bw.write(lineToWrite);
					bw.newLine();
				}
			}
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bw!=null) {
				try {
					bw.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
	public ArrayList<Manager> getManagers(){
		ArrayList<Manager> managers = new ArrayList<Manager>();
		for(User u : users.values()) {
			if(u.getRole() == Role.manager) {
				managers.add((Manager)u);
			}
		}
		return managers;
	}
	public void removeManagersRent(int id){
		for(User u : users.values()) {
			if(u.getRole() == Role.manager) {
				Manager m = (Manager)u;
				if(m.getRentACarId() == id) {
					m.setRentACarId(-1);
					m.setRentACar(null);
					SaveManagersToFile();
					break;
				}
			}
		}
	}
}
