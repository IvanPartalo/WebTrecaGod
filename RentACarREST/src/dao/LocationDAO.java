package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import models.Location;
import models.RentACar;
import models.Status;

public class LocationDAO {
	private ArrayList<Location> locations = new ArrayList<>();
	private String path;
	public LocationDAO(String contextPath) {
		path = contextPath;
		loadLocations(contextPath);
	}
	public void loadLocations(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/locations.txt");
			in = new BufferedReader(new FileReader(file));
			String line, id = "", address = "", longitude = "", latitude = "";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					address = st.nextToken().trim();
					longitude = (st.nextToken().trim());
					latitude = (st.nextToken().trim());
				}
				Location location = new Location(Integer.parseInt(id), Double.parseDouble(latitude), Double.parseDouble(longitude), address); 
				formatLocation(location);
				locations.add(location);
				
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
	public void formatLocation(Location location) {
		String[] splitAddress = location.getAddress().split("@", 2);
		location.setStreetNumber(splitAddress[0]);
		location.setPlaceZipCode(splitAddress[1]);
	}
	public int getNextId() {
		int maxId = -1;
		for(Location loc : locations) {
			if(loc.getId()>maxId) {
				maxId = loc.getId();
			}
		}
		return maxId++;
	}
	public Location getById(int id) {
		for(Location loc : locations) {
			if(loc.getId() == id) {
				return loc;
			}
		}
		return null;
	}
	public void add(Location location) {
		locations.add(location);
	}
	public void SaveToFile() {
		BufferedWriter bw = null;
		try {
			File fout = new File(path + "/locations.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(Location l : locations) {
				String lineToWrite = 
				l.getId()+";"+l.getAddress()+";"+l.getLongitude()+";"+l.getLatitude();
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