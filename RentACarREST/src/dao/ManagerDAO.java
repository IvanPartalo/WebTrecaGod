package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import models.Customer;
import models.Gender;
import models.Manager;
import models.Role;

public class ManagerDAO {
	private ArrayList<Manager> managers = new ArrayList<>();
	private String path;
	public ManagerDAO(String contextPath) {
		path = contextPath;
		loadManagers(contextPath);
	}
	public void loadManagers(String contextPath) {
		managers.clear();
		BufferedReader in = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			File file = new File(contextPath + "/managers.txt");
			in = new BufferedReader(new FileReader(file));
			String line, id = "", username = "", password = "", firstName = "", lastName = "", gender = "", role = "", dateOfBirth="", rentACarId = "";
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
					rentACarId = st.nextToken().trim();
				}
				managers.add(new Manager(Integer.parseInt(id), username, password, firstName, lastName, Gender.male, 
						Role.valueOf(role), formatter.parse(dateOfBirth), Integer.parseInt(rentACarId)));
			}
		} catch (Exception e) {
			System.out.println("greska man");
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
	private void SaveToFile() {
		BufferedWriter bw = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			File fout = new File(path + "/managers.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(Manager m : managers) {
				String date = formatter.format(m.getDateOfBirth());
				String lineToWrite = 
				m.getId()+";"+m.getUsername()+";"+m.getPassword()+";"+m.getFirstName()+";"+m.getLastName()+";"+m.getGender()+";"+
				m.getRole()+";"+date+";"+m.getRentACarId();
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
	public ArrayList<Manager> getAll(){
		return managers;
	}
	public void addNewManager(Manager manager) {
		managers.add(manager);
		SaveToFile();
	}
	public void updateManager(int managerId, int rentACarId) {
		for(Manager m : managers) {
			if(m.getId() == managerId) {
				m.setRentACarId(rentACarId);
				SaveToFile();
				break;
			}
		}
	}
	public int getNextId() {
		int maxId = -1;
		for(Manager m : managers) {
			if(m.getId()>maxId) {
				maxId = m.getId();
			}
		}
		return maxId++;
	}
	public ArrayList<Manager> getFreeManagers(){
		ArrayList<Manager> freeManagers = new ArrayList<Manager>();
		for(Manager m : managers) {
			if(m.getRentACarId() == -1) {
				freeManagers.add(m);
			}
		}
		return freeManagers;
	}
		
}
