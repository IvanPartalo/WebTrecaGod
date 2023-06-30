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

import models.FuelType;
import models.Gearshift;
import models.Vehicle;


public class VehicleDAO {
	private ArrayList<Vehicle> vehicles = new ArrayList<>();
	private String path;
	public VehicleDAO(String contextPath) {
		path = contextPath;
		loadVehicles(contextPath);
	}
	public void loadVehicles(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/vehicles.txt");
			in = new BufferedReader(new FileReader(file));
			String line, id = "", fuelType="", brand = "", model = "", type = "", gearType="", description = "", photo = "", boolString = "";
			int price=0, rentACarId=0, doors=0, consumption=0, maxPeople=0;
			boolean available = true;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					brand = st.nextToken().trim();
					model = st.nextToken().trim();
					type = st.nextToken().trim();
					gearType = st.nextToken().trim();
					description = st.nextToken().trim();
					photo = st.nextToken().trim();
					fuelType = st.nextToken().trim();
					price = Integer.parseInt(st.nextToken().trim());
					doors = Integer.parseInt(st.nextToken().trim());
					consumption = Integer.parseInt(st.nextToken().trim());
					maxPeople = Integer.parseInt(st.nextToken().trim());
					rentACarId = Integer.parseInt(st.nextToken().trim());
					boolString = st.nextToken().trim();
				}
				if(boolString.equals("true")) {
					available = true;
				}
				else {
					available = false;
				}
				 vehicles.add(new Vehicle(Integer.parseInt(id), brand, model, price, type, Gearshift.valueOf(gearType), null, 
						rentACarId, consumption, doors, maxPeople, description, photo, available, FuelType.valueOf(fuelType)));
				}
		} catch (Exception e) {
			System.out.println("greska veh");
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
		try {
			File fout = new File(path + "/vehicles.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(Vehicle v : vehicles) {
				String lineToWrite = 
				v.getId()+";"+v.getBrand()+";"+v.getModel()+";"+v.getType()+";"+v.getGearshiftType()+";"+v.getDescription()+";"+v.getPhoto()+";"+
				v.getFuelType()+";"+v.getPrice()+";"+v.getDoors()+";"+v.getConsumption()+";"+v.getMaxPeople()
				+";"+v.getRentACarId()+";"+v.getAvailable();
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
	public void save(Vehicle v) {
		v.setPhoto("asd");
		v.setAvailable(true);
		v.setId(getNextId());
		vehicles.add(v);
		SaveToFile();
	}
	public int getNextId() {
		int maxId = -1;
		for(Vehicle v : vehicles) {
			if(v.getId()>maxId) {
				maxId = v.getId();
			}
		}
		return ++maxId;
	}
	public ArrayList<Vehicle> getAvailable() {
		ArrayList<Vehicle> availables = new ArrayList<>();
		for(Vehicle v : vehicles) {
			if(v.getAvailable() == true) {
				availables.add(v);
			}
		}
		return availables;
	}
	public ArrayList<Vehicle> getAll() {
		return vehicles;
	}
	public Vehicle getById(int id) {
		for(Vehicle v : vehicles) {
			if(v.getId() == id) {
				return v;
			}
		}
		return null;
	}
}
