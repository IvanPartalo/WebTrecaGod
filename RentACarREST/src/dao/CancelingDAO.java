package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import models.Canceling;

public class CancelingDAO {
	private ArrayList<Canceling> cancelings = new ArrayList<>();
	private String path;
	public CancelingDAO(String contextPath) {
		path = contextPath;
		loadCancelings(contextPath);
	}
	public void loadCancelings(String contextPath) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/cancelings.txt");
			in = new BufferedReader(new FileReader(file));
			String line, customerId = "", date = "";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					customerId = st.nextToken().trim();
					date = st.nextToken().trim();
				}
				Canceling c = new Canceling(Integer.parseInt(customerId), formatter.parse(date));
				cancelings.add(c);
				
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
	public ArrayList<Canceling> getAll(){
		return cancelings;
	}
	@SuppressWarnings("deprecation")
	public Boolean isSuspecious(int id) {
		int counter = 0;
		Date date = new Date();
		date.setMonth(date.getMonth()-1);
		for(Canceling c : cancelings) {
			if(c.getCustomerId() == id && c.getDate().after(date)) {
				counter++;
			}
		}
		if(counter>=5) {
			return true;
		}else {
			return false;
		}
	}
	public void SaveToFile() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		BufferedWriter bw = null;
		try {
			File fout = new File(path + "/cancelings.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(Canceling c : cancelings) {
				String lineToWrite = 
				c.getCustomerId()+";"+formatter.format(c.getDate())+";";
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
	public void save(Canceling c) {
		cancelings.add(c);
		SaveToFile();
	}
}
