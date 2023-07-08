package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.RentACarDAO;
import dao.VehicleDAO;
import models.RentACar;
import models.Vehicle;


@Path("/vehicles")
public class VehicleService {
	@Context
	ServletContext ctx;
	
	public VehicleService() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("vehicleDAO") == null ) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("vehicleDAO", new VehicleDAO(contextPath));
		}
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Vehicle getById(@PathParam("id") int id){
		VehicleDAO dao = (VehicleDAO) ctx.getAttribute("vehicleDAO");
		return dao.getById(id);
	}
	@PUT
	@Path("/")
	public void editVehicle(Vehicle v){
		VehicleDAO dao = (VehicleDAO) ctx.getAttribute("vehicleDAO");
		dao.editVehicle(v);
	}
	@DELETE
	@Path("/{id}")
	public ArrayList<Vehicle> deleteVehicle(@PathParam("id") int id){
		VehicleDAO dao = (VehicleDAO) ctx.getAttribute("vehicleDAO");
		RentACarDAO rdao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		Vehicle veh = dao.getById(id);
		RentACar rent = rdao.getById(veh.getRentACarId());
		for (Vehicle v : rent.getVehicles()) {
			if(v.getId() == veh.getId()) {
				v.setIsDeleted(1);
				break;
			}
		}
		return dao.deleteVehicle(id);
	}
}
