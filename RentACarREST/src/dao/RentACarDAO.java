package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.StringTokenizer;

import dto.RentACarDTO;
import models.Location;
import models.Manager;
import models.RentACar;
import models.Status;

public class RentACarDAO {
	private ArrayList<RentACar> rentACars = new ArrayList<>();
	private LocationDAO locDAO;
	private ManagerDAO managerDAO;
	private String path;
	private RentACar newRent;
	public RentACarDAO(String contextPath) {
		System.out.println("doso?");
		path = contextPath;
		locDAO = new LocationDAO(contextPath);
		managerDAO = new ManagerDAO(contextPath);
		loadRentACars(contextPath);
		linkWithLocations();
	}
	public void loadRentACars(String contextPath) {
		ArrayList<RentACar> tmpRentACars = new ArrayList<>();
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/rentacars.txt");
			in = new BufferedReader(new FileReader(file));
			String line, id = "", name = "", logo = "", grade="";
			int startMinute=0, endMinute=0, startHour=0, endHour=0, locationId=0;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					name = st.nextToken().trim();
					startHour = Integer.parseInt(st.nextToken().trim());
					startMinute = Integer.parseInt(st.nextToken().trim());
					endHour = Integer.parseInt(st.nextToken().trim());
					endMinute = Integer.parseInt(st.nextToken().trim());
					logo = st.nextToken().trim();
					grade = st.nextToken().trim();
					locationId = Integer.parseInt(st.nextToken().trim());
				}
				Status status = getWorkStatus(startHour, startMinute, endHour, endMinute);
				tmpRentACars.add(new RentACar(Integer.parseInt(id), locationId, name, startHour, startMinute, endHour, endMinute, status, logo, Double.parseDouble(grade)));
			}
			sort(tmpRentACars);
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
	private void sort(ArrayList<RentACar> tmpRentACars) {
		for(RentACar r : tmpRentACars) {
			if(r.getStatus() == Status.working) {
				rentACars.add(r);
			}
		}
		for(RentACar r : tmpRentACars) {
			if(r.getStatus() == Status.notWorking) {
				rentACars.add(r);
			}
		}
		System.out.println(rentACars);
	}
	private Status getWorkStatus(int startHour, int startMinute, int endHour, int endMinute) {
		LocalTime time = LocalTime.now();
		if(startHour > time.getHour()) {
			return Status.notWorking;
		}
		else if(startHour == time.getHour() && startMinute > time.getMinute()) {
			return Status.notWorking;
		}
		else if(endHour < time.getHour()) {
			return Status.notWorking;
		}
		else if(endHour == time.getHour() && endMinute < time.getMinute()) {
			return Status.notWorking;
		}
		else {
			return Status.working;
		}
	}
	private void linkWithLocations() {
		for(RentACar rent : rentACars) {
			rent.setLocation(locDAO.getById(rent.getLocationId()));
		}
	}
	private int getNextId() {
		int maxId = -1;
		for(RentACar rent : rentACars) {
			if(rent.getId()>maxId) {
				maxId = rent.getId();
			}
		}
		return maxId++;
	}
	public ArrayList<RentACar> getAll(){
		return rentACars;
	}
	public void createRentACar(RentACarDTO rentACarDTO) {
		int locId = locDAO.getNextId() + 1;
		Location location = new Location(locId, rentACarDTO.getLatitude(), rentACarDTO.getLongitude(), rentACarDTO.getAddress());
		locDAO.add(location);
		locDAO.SaveToFile();
		int startHour, startMinute, endHour, endMinute;
		int array[] = getTime(rentACarDTO.getBeginWorkTime());
		startHour = array[0];
		startMinute = array[1];
		array = getTime(rentACarDTO.getEndWorkTime());
		endHour = array[0];
		endMinute = array[1];
		Status status = getWorkStatus(startHour, startMinute, endHour, endMinute);
		int id = getNextId()+1;
		newRent = new RentACar(id, locId, rentACarDTO.getName(), startHour, startMinute, endHour, endMinute,
				status, "asdf", 0.0);
		newRent.setLocation(location);
	}
	public void saveWithNewManager(Manager manager) {
		manager.setRentACarId(newRent.getId());
		manager.setRentACar(newRent);
		managerDAO.addNewManager(manager);
		rentACars.add(newRent);
		SaveToFile();
	}
	public void save(RentACarDTO rentACarDTO, int managerId) {
		createRentACar(rentACarDTO);
		rentACars.add(newRent);
		managerDAO.updateManager(managerId, newRent.getId());
		SaveToFile();
	}
	private int[] getTime(String time) {
		String splittedTime[] = time.split(":");
		int hour, minute;
		int array[] = {0,0};
		if(splittedTime[0].charAt(0) == '0') {
			hour=Character.getNumericValue(splittedTime[0].charAt(1));
		}
		else {
			hour=Integer.parseInt(splittedTime[0]);
		}
		if(splittedTime[1].charAt(0) == '0') {
			minute=Character.getNumericValue(splittedTime[1].charAt(1));
		}
		else {
			minute=Integer.parseInt(splittedTime[1]);
		}
		array[0] = hour;
		array[1] = minute;
		return array;
	}
	private void SaveToFile() {
		BufferedWriter bw = null;
		try {
			File fout = new File(path + "/rentacars.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(RentACar r : rentACars) {
				String lineToWrite = 
				r.getId()+";"+r.getName()+";"+r.getStartHour()+";"+r.getStartMinute()+";"+r.getEndHour()+";"+r.getEndMinute()+
				";"+r.getLogoImg()+";"+r.getGrade()+";"+r.getLocationId();
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