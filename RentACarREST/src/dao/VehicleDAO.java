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
import models.Logo;
import models.Vehicle;


public class VehicleDAO {
	private ArrayList<Vehicle> vehicles = new ArrayList<>();
	private ArrayList<Logo> photos = new ArrayList<>();
	private String path;
	public VehicleDAO(String contextPath) {
		path = contextPath;
		loadVehicles(contextPath);
		loadPhotos(contextPath);
		LinkPhotos();
	}
	public void loadPhotos(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/vehiclePhotos.txt");
			in = new BufferedReader(new FileReader(file));
			String line, id = "", url = "";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, "|");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					url = st.nextToken().trim();
				}
				Logo logo = new Logo(Integer.parseInt(id), url);
				photos.add(logo);
			}
		} catch (Exception e) {
			System.out.println("greska slike");
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
	public void loadVehicles(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/vehicles.txt");
			in = new BufferedReader(new FileReader(file));
			String line, id = "", fuelType="", brand = "", model = "", type = "", gearType="", description = "", photo = "", boolString = "";
			int price=0, rentACarId=0, doors=0, consumption=0, maxPeople=0;
			boolean available = true;
			int isDeleted = 0;
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
					isDeleted = Integer.parseInt(st.nextToken().trim());
				}
				if(boolString.equals("true")) {
					available = true;
				}
				else {
					available = false;
				}
				//ako je isDeleted 0 nije obrisan
				vehicles.add(new Vehicle(Integer.parseInt(id), brand, model, price, type, Gearshift.valueOf(gearType), null, 
				rentACarId, consumption, doors, maxPeople, description, Integer.parseInt(photo), available, FuelType.valueOf(fuelType), isDeleted));
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
	private String getPhotoStringById(int id) {
		for(Logo p : photos) {
			if(p.getId() == id) {
				return p.getUrl();
			}
		}
		return "";
	}
	private Logo getPhotoById(int id) {
		for(Logo p : photos) {
			if(p.getId() == id) {
				return p;
			}
		}
		return null;
	}
	private void LinkPhotos() {
		for(Vehicle v: vehicles) {
			v.setPhoto(getPhotoStringById(v.getPhotoId()));
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
				v.getId()+";"+v.getBrand()+";"+v.getModel()+";"+v.getType()+";"+v.getGearshiftType()+";"+v.getDescription()+";"+v.getPhotoId()+";"+
				v.getFuelType()+";"+v.getPrice()+";"+v.getDoors()+";"+v.getConsumption()+";"+v.getMaxPeople()
				+";"+v.getRentACarId()+";"+v.getAvailable()+";"+v.getIsDeleted();
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
	private int getNextPhotoId() {
		int maxId = -1;
		for(Logo p : photos) {
			if(p.getId()>maxId) {
				maxId = p.getId();
			}
		}
		return ++maxId;
	}
	public String save(Vehicle v) {
		if(v.getModel().isBlank() || v.getBrand().isBlank()) {
			return "Can not create this vehicle, not all fields have been filled correctly";
		}
		int photoId = getNextPhotoId();
		Logo photo = new Logo();
		photo.setId(photoId);
		photo.setUrl(v.getPhoto());
		photos.add(photo);
		SavePhotosToFile();
		v.setAvailable(true);
		v.setId(getNextId());
		v.setPhotoId(photoId);
		if(v.getDescription().isBlank()) {
			v.setDescription("/");
		}
		vehicles.add(v);
		SaveToFile();
		return "ok";
	}
	public void updateVehicles() {
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
			if(v.getAvailable() == true && v.getIsDeleted() == 0) {
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
	public void editVehicle(Vehicle newVehicle) {
		Vehicle oldVehicle = getById(newVehicle.getId());
		oldVehicle.setBrand(newVehicle.getBrand());
		oldVehicle.setModel(newVehicle.getModel());
		oldVehicle.setConsumption(newVehicle.getConsumption());
		oldVehicle.setDescription(newVehicle.getDescription());
		oldVehicle.setDoors(newVehicle.getDoors());
		oldVehicle.setFuelType(newVehicle.getFuelType());
		oldVehicle.setGearshiftType(newVehicle.getGearshiftType());
		oldVehicle.setMaxPeople(newVehicle.getMaxPeople());
		oldVehicle.setPrice(newVehicle.getPrice());
		oldVehicle.setType(newVehicle.getType());
		oldVehicle.setPhoto(newVehicle.getPhoto());
		SaveToFile();
		Logo oldPhoto = getPhotoById(oldVehicle.getPhotoId());
		oldPhoto.setUrl(oldVehicle.getPhoto());
		SavePhotosToFile();
	}
	public ArrayList<Vehicle> deleteVehicle(int id) {
		Vehicle v = getById(id);
		v.setIsDeleted(1);
		SaveToFile();
		return vehicles;
	}
	private void SavePhotosToFile() {
		BufferedWriter bw = null;
		try {
			File fout = new File(path + "/vehiclePhotos.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(Logo l : photos) {
				String lineToWrite = 
				l.getId()+"|"+l.getUrl();
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
}
