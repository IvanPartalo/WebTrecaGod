package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import models.Comment;
import models.Location;
import models.Purchase;

public class CommentDAO {
	private ArrayList<Comment> comments = new ArrayList<>();
	private String path;
	public CommentDAO(String contextPath) {
		path = contextPath;
		loadComments(contextPath);
	}
	public void loadComments(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/comments.txt");
			in = new BufferedReader(new FileReader(file));
			String line, id = "", customerId = "", rentACarId = "", commentText = "", grade = "", approved = "";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					customerId = st.nextToken().trim();
					rentACarId = (st.nextToken().trim());
					commentText = (st.nextToken().trim());
					grade = (st.nextToken().trim());
					approved = (st.nextToken().trim());
				}
				Comment c = new Comment(Integer.parseInt(id), Integer.parseInt(customerId), Integer.parseInt(rentACarId),
						commentText, Integer.parseInt(grade), Boolean.parseBoolean(approved)); 
				comments.add(c);
				
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
	public int getNextId() {
		int maxId = -1;
		for(Comment c : comments) {
			if(c.getId()>maxId) {
				maxId = c.getId();
			}
		}
		return maxId++;
	}
	public ArrayList<Comment> getAll(){
		return comments;
	}
	public ArrayList<Comment> getApprovedRentACarComments(int rentACarId){
		ArrayList<Comment> approvedComments = new ArrayList<>();
		for(Comment c : comments) {
			if(c.getApproved() && c.getRentACarId() == rentACarId) {
				approvedComments.add(c);
			}
		}
		return approvedComments;
	}
	public ArrayList<Comment> getRentACarComments(int rentACarId){
		ArrayList<Comment> rentComments = new ArrayList<>();
		for(Comment c : comments) {
			if(c.getRentACarId() == rentACarId) {
				rentComments.add(c);
			}
		}
		return rentComments;
	}
	public Comment getById(int id){
		for(Comment c : comments) {
			if(c.getId() == id) {
				return c;
			}
		}
		return null;
	}
	public void approveComment(int id) {
		Comment comment = getById(id);
		comment.setApproved(true);
		SaveToFile();
	}
	public void SaveToFile() {
		BufferedWriter bw = null;
		try {
			File fout = new File(path + "/comments.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(Comment c : comments) {
				String lineToWrite = 
				c.getId()+";"+c.getCustomerId()+";"+c.getRentACarId()+";"+c.getCommentText()+";"+c.getGrade()+";"+c.getApproved()+";";
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
	public void save(Comment c) {
		c.setId(getNextId());
		comments.add(c);
		SaveToFile();
	}
}
