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


public class BlockedUserDAO {
	private ArrayList<Integer> blocked = new ArrayList<>();
	private String path;
	public BlockedUserDAO(String contextPath) {
		path = contextPath;
		loadBlocked(contextPath);
	}
	public void loadBlocked(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/blockedUsers.txt");
			in = new BufferedReader(new FileReader(file));
			String line, usersId = "";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					usersId = st.nextToken().trim();
				}
				blocked.add(Integer.parseInt(usersId));
				
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
	public ArrayList<Integer> getAll(){
		return blocked;
	}
	public void SaveToFile() {
		BufferedWriter bw = null;
		try {
			File fout = new File(path + "/blockedUsers.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(Integer b : blocked) {
				String lineToWrite = b + ";";
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
	public void save(int b) {
		blocked.add(b);
		SaveToFile();
	}
}
