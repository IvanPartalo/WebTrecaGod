package dao;

import java.util.Collection;
import java.util.HashMap;

import models.Location;
import models.RentACar;
import models.Status;

public class RentACarDAO {
	private HashMap<Integer, RentACar> rentACars = new HashMap<>();
	private String path;
	public RentACarDAO(String contextPath) {
		path = contextPath;
		Location location1 = new Location(123, 431.2, "Beograd, Srbija");
		RentACar r1 = new RentACar(1, "neki rent", 8, 0, 18, 0, Status.work, 
		"https://logos-world.net/wp-content/uploads/2020/04/Barcelona-Logo.png",
		7, location1);
		RentACar r2 = new RentACar(2, "rent madrid", 7, 30, 15, 0, Status.work, 
				"https://upload.wikimedia.org/wikipedia/en/thumb/5/56/Real_Madrid_CF.svg/1200px-Real_Madrid_CF.svg.png",
				8, location1);
		rentACars.put(r1.getId(), r1);
		rentACars.put(r2.getId(), r2);
	}
	public Collection<RentACar> getAll(){
		return rentACars.values();
	}
}
