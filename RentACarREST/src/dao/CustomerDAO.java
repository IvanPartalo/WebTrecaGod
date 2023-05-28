package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import models.Customer;
import models.CustomerType;
import models.Gender;
import models.Renting;
import models.Role;
import models.ShoppingCart;

public class CustomerDAO {
	private HashMap<Integer, Customer> customers = new HashMap<>();
	
	public CustomerDAO() {
	}
	@SuppressWarnings("deprecation")
	public CustomerDAO(String contextPath) {
		customers.put(1, new Customer(1, "Kime",  "sifra", "ime", "przime", Gender.male, Role.customer, new Date(89,11,12), 0, new ShoppingCart(), new CustomerType(),
				new ArrayList<Renting>()));
		loadCustomers(contextPath);
	}
	
	public void loadCustomers(String contextPath) {
		BufferedReader in = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			File file = new File(contextPath + "/customers.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line, id = "", username = "", password = "", firstName = "", lastName = "", gender = "", role = "", dateOfBirth="", collectedPoints = "";
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
					collectedPoints = st.nextToken().trim();
				}
				customers.put(Integer.parseInt(id), new Customer(Integer.parseInt(id), username, password, firstName, lastName, Gender.male, Role.customer, formatter.parse(dateOfBirth), Integer.parseInt(collectedPoints)));
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
	
	public Collection<Customer> getAll(){
		return customers.values();
	}
	
	public void saveCustomer(Customer c){
		Integer maxId = 0;
		for (Integer id : customers.keySet()) {
			if (id > maxId) {
				maxId = id;
			}
		}
		maxId++;
		c.setId(maxId);
		customers.put(c.getId(), c);
	}
}
