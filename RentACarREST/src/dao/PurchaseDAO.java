package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import models.Purchase;
import models.PurchaseStatus;
import models.RentACar;
import models.SubPurchase;
import models.Vehicle;


public class PurchaseDAO {
	private ArrayList<Purchase> purchases = new ArrayList<>();
	private String path;
	public PurchaseDAO(String contextPath) {
		path = contextPath;
		loadPurchases(contextPath);
		loadPurchasedVehicles(contextPath);
		loadSubPurchases(contextPath);
	}
	public void loadSubPurchases(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/subPurchases.txt");
			in = new BufferedReader(new FileReader(file));
			String line, purchaseId = "", rentACarId = "", startDateTime = "", duration = "", status = "";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					purchaseId = st.nextToken().trim();
					rentACarId = st.nextToken().trim();
					startDateTime = st.nextToken().trim();
					duration = st.nextToken().trim();
					status = st.nextToken().trim();
				}
				for(Purchase p : purchases) {
					if(p.getId().equals(purchaseId)) {
						p.getSubPurchases().add(new SubPurchase(purchaseId, Integer.parseInt(rentACarId), Integer.parseInt(duration),
								startDateTime, PurchaseStatus.valueOf(status)));
						break;
					}
				}
				}
		} catch (Exception e) {
			System.out.println("Error loading");
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
	public void loadPurchasedVehicles(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/purchasedVehicles.txt");
			in = new BufferedReader(new FileReader(file));
			String line, id = "", vehicleId="";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					vehicleId = st.nextToken().trim();
				}
				for(Purchase p : purchases) {
					if(p.getId().equals(id)) {
						p.getVehicleIds().add(Integer.parseInt(vehicleId));
						break;
					}
				}
				}
		} catch (Exception e) {
			System.out.println("Error loading");
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
	public void loadPurchases(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/purchases.txt");
			in = new BufferedReader(new FileReader(file));
			String line, id = "", price = "", duration = "", startDateTime = "", endDateTime = "", status = "", customerId = "", decliningReason = "";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					price = st.nextToken().trim();
					duration = st.nextToken().trim();
					startDateTime = st.nextToken().trim();
					endDateTime = st.nextToken().trim();
					status = st.nextToken().trim();
					customerId = st.nextToken().trim();
					decliningReason = st.nextToken().trim();
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
				LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
				LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);
				if(decliningReason.equals("nothing")) {
					 purchases.add(new Purchase(id, Integer.parseInt(price), start, end, Integer.parseInt(duration), 
							 startDateTime, endDateTime, PurchaseStatus.valueOf(status), Integer.parseInt(customerId), ""));
				}else {
					purchases.add(new Purchase(id, Integer.parseInt(price), start, end, Integer.parseInt(duration), 
							startDateTime, endDateTime, PurchaseStatus.valueOf(status), Integer.parseInt(customerId), decliningReason));
					
				}
			}
		} catch (Exception e) {
			System.out.println("Error loading");
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
	private void SavePurchasesToFile() {
		BufferedWriter bw = null;
		try {
			File fout = new File(path + "/purchases.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(Purchase p : purchases) {
				String lineToWrite = "";
				if(p.getDecliningReason().isEmpty()) {
					lineToWrite = p.getId()+";" + p.getPrice()+ ";" + p.getDuration()+ ";" + p.getStartDateTime()+ ";" 
							+ p.getEndDateTime()+ ";" + p.getStatus()+ ";" + p.getCustomerId()+ ";" + "nothing" + ";";
				}else {
					lineToWrite = p.getId()+";" + p.getPrice()+ ";" + p.getDuration()+ ";" + p.getStartDateTime()+ ";" 
							+ p.getEndDateTime()+ ";" + p.getStatus()+ ";" + p.getCustomerId()+ ";" + p.getDecliningReason()+ ";";
				}
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
	private void SavePurchasedVehiclesToFile() {
		BufferedWriter bw = null;
		try {
			File fout = new File(path + "/purchasedVehicles.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(Purchase p : purchases) {
				for(Vehicle v : p.getVehicles()) {
					String lineToWrite = p.getId()+";" + v.getId()+";";
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
	private void SaveSubPurchasesToFile() {
		BufferedWriter bw = null;
		try {
			File fout = new File(path + "/subPurchases.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(Purchase p : purchases) {
				for(SubPurchase s : p.getSubPurchases()) {
					String lineToWrite = p.getId()+";" + s.getRentACarId() + ";" + s.getStartDateTime() + ";" + s.getDuration() + ";" + s.getStatus();
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
	public void save(Purchase p) {
		p.setId(generateNextId());
		p.setDecliningReason("nothing");
		purchases.add(p);
		SavePurchasesToFile();
		SavePurchasedVehiclesToFile();
		SaveSubPurchasesToFile();
	}
	public void updatePurchases() {
		SavePurchasesToFile();
		SavePurchasedVehiclesToFile();
		SaveSubPurchasesToFile();
	}
	public String generateNextId() {
		boolean isUnique = true;
		boolean isFistTry = true;
		String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder s = new StringBuilder(10);
		while(isUnique == false || isFistTry == true) {
			isFistTry = false;
			for (int i=0; i<10; i++) {
				int ch = (int)(possibleChars.length() * Math.random());
				s.append(possibleChars.charAt(ch));
			}
			for(Purchase p : purchases) {
				if(p.getId().equals(s.toString())) {
					isUnique = false;
					break;
				}
			}
			System.out.println(s.toString());
		}
		return s.toString();
	}
	public ArrayList<Purchase> getByCustomerId(Integer id) {
		ArrayList<Purchase> result = new ArrayList<>();
		for(Purchase p : purchases) {
			if(p.getCustomerId() == id) {
				result.add(p);
			}
		}
		return result;
	}
	public ArrayList<Purchase> getAll(){
		return purchases;
	}
	public ArrayList<Purchase> getByRentingsId(RentACar car) {
		ArrayList<Purchase> result = new ArrayList<>();
		for(Purchase p : purchases) {
			if(p.getRentACars().contains(car)) {
				result.add(p);
			}
		}
		return result;
	}
}
