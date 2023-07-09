package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import models.CustomerType;

public class CustomerTypeDAO {
	private ArrayList<CustomerType> customerTypes = new ArrayList<>();
	
	public CustomerTypeDAO(String contextPath) {
		loadCustomerTypes(contextPath);
	}
	public void loadCustomerTypes(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/customerTypes.txt");
			in = new BufferedReader(new FileReader(file));
			String line, id = "", name = "", discount = "", requiredPoints = "";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					name = st.nextToken().trim();
					discount = (st.nextToken().trim());
					requiredPoints = (st.nextToken().trim());
				}
				customerTypes.add(new CustomerType(Integer.parseInt(id), name, Double.parseDouble(discount), Integer.parseInt(requiredPoints)));
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
	public CustomerType getByName(String name) {
		for(CustomerType c : customerTypes) {
			if(c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	public CustomerType getByPoints(Double collectedPoints) {
		int maxPoints = 0;
		CustomerType customerType = null;
		for(CustomerType c : customerTypes) {
			if(c.getRequiredPoints() <= collectedPoints && maxPoints <= c.getRequiredPoints()) {
				maxPoints = c.getRequiredPoints();
				customerType = c;
			}
		}
		return customerType;
	}
}
