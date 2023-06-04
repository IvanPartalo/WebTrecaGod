package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import models.Customer;
import models.Gender;
import models.Location;
import models.RentACar;
import models.Role;
import models.Status;

public class RentACarDAO {
	private ArrayList<RentACar> rentACars = new ArrayList<>();
	private String path;
	public RentACarDAO(String contextPath) {
		path = contextPath;
		loadRentACars(contextPath);
	}
	public void loadRentACars(String contextPath) {
		ArrayList<RentACar> tmpRentACars = new ArrayList<>();
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/rentacars.txt");
			in = new BufferedReader(new FileReader(file));
			String line, id = "", name = "", logo = "", grade="";
			int startMinute=0, endMinute=0, startHour=0, endHour=0;
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
				}
				Location location1 = new Location(123, 431.2, "Beograd, Srbija");
				Status status = getWorkStatus(startHour, startMinute, endHour, endMinute);
				tmpRentACars.add(new RentACar(Integer.parseInt(id), name, startHour, startMinute, endHour, endMinute, status, logo, Double.parseDouble(grade), location1));
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
	public ArrayList<RentACar> getAll(){
		return rentACars;
	}
}
