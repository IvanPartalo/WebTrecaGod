package services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.Response;

import dao.UserDAO;
import dao.VehicleDAO;
import dao.CommentDAO;
import dao.ManagerDAO;
import dao.PurchaseDAO;
import dao.RentACarDAO;
import dto.RentACarDTO;
import models.Comment;
import models.Customer;
import models.Manager;
import models.Purchase;
import models.PurchaseStatus;
import models.RentACar;
import models.Role;
import models.SubPurchase;
import models.User;
import models.Vehicle;

@Path("/rentacar")
public class RentACarService {
	@Context
	ServletContext ctx;
	public RentACarService() {
		
	}
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("rentACarDAO") == null ) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("rentACarDAO", new RentACarDAO(contextPath));
		}
		if (ctx.getAttribute("vehicleDAO") == null ) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("vehicleDAO", new VehicleDAO(contextPath));
		}
		if(ctx.getAttribute("userDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new UserDAO(contextPath));
		}
		if(ctx.getAttribute("purchaseDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("purchaseDAO", new PurchaseDAO(contextPath));
		}
		if(ctx.getAttribute("commentDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("commentDAO", new CommentDAO(contextPath));
		}
		linkManagers();
		linkCars();
		linkPurchasesRentACar();
		linkPurchasesVehicles();
		linkComments();
	}
	private void linkComments() {
		RentACarDAO rdao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		CommentDAO cdao = (CommentDAO) ctx.getAttribute("commentDAO");
		UserDAO udao = (UserDAO) ctx.getAttribute("userDAO");
		for(RentACar r : rdao.getAll()) {
			r.setSumGradesCountZero();
		}
		for(Comment c : cdao.getAll()) {
			User u = udao.getById(c.getCustomerId());
			Customer customer = (Customer)u;
			c.setCustomer(customer);
			RentACar r = rdao.getById(c.getRentACarId());
			c.setRentACar(r);
			if(c.getApproved()) {
				r.addGrade(c.getGrade());
			}
		}
	}
	private void linkPurchasesVehicles() {
		VehicleDAO vdao = (VehicleDAO) ctx.getAttribute("vehicleDAO");
		PurchaseDAO pdao = (PurchaseDAO) ctx.getAttribute("purchaseDAO");
		for(Purchase p : pdao.getAll()) {
			p.getVehicles().clear();
			for(Integer vId : p.getVehicleIds()){
				Vehicle v = vdao.getById(vId);
				p.getVehicles().add(v);
			}
		}
	}
	private void linkPurchasesRentACar() {
		RentACarDAO rdao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		PurchaseDAO pdao = (PurchaseDAO) ctx.getAttribute("purchaseDAO");
		VehicleDAO vdao = (VehicleDAO) ctx.getAttribute("vehicleDAO");
		for(Purchase p : pdao.getAll()) {
			for(Integer id : p.getVehicleIds()) {
				p.getRentACars().add(rdao.getById(vdao.getById(id).getRentACar().getId()));
				rdao.getById(vdao.getById(id).getRentACar().getId()).getRentings().add(p);
			}
			for(SubPurchase sp : p.getSubPurchases()) {
				sp.setRentACar(rdao.getById(sp.getRentACarId()));
			}
		}
	}
	public void linkManagers() {
		UserDAO udao = (UserDAO) ctx.getAttribute("userDAO");
		RentACarDAO rdao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		for(Manager m : udao.getManagers()) {
			RentACar rent = rdao.getById(m.getRentACarId());
			m.setRentACar(rent);
		}
	}
	public void linkCars() {
		VehicleDAO vdao = (VehicleDAO) ctx.getAttribute("vehicleDAO");
		RentACarDAO rdao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		for(RentACar r : rdao.getAll()) {
			r.getVehicles().clear();
		}
		for(Vehicle v : vdao.getAll()) {
			RentACar rent = rdao.getById(v.getRentACarId());
			v.setRentACar(rent);
			rent.getVehicles().add(v);
		}
	}
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<RentACar> getAll(){
		RentACarDAO dao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		return dao.getNotDeleted();
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RentACar getById(@PathParam("id") int id){
		RentACarDAO dao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		return dao.getById(id);
	}

	@GET
	@Path("/manager/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RentACar getByManagerId(@PathParam("id") int id){
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		Manager m = (Manager)dao.getById(id);
		return m.getRentACar();
	}

	@POST
	@Path("/vehicles")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Vehicle> getAvailableVehicles(Purchase purchase, @Context HttpServletRequest request){
		PurchaseDAO pdao = (PurchaseDAO) ctx.getAttribute("purchaseDAO");
		VehicleDAO dao = (VehicleDAO) ctx.getAttribute("vehicleDAO");
		ArrayList<Vehicle> result = dao.getAvailable();
		User u = (User) request.getSession().getAttribute("currentUser");
		Customer c = (Customer)u;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime start = LocalDateTime.parse(purchase.getStartDateTime(), formatter);
		LocalDateTime end = LocalDateTime.parse(purchase.getEndDateTime(), formatter);
		for(Purchase p : c.getShoppingCart().getPrepairedPurchases()) {
			if(purchase.getStartDateTime().equals(p.getStartDateTime()) && purchase.getEndDateTime().equals(p.getEndDateTime())) {
				for(Vehicle v : p.getVehicles()) {
					result.remove(v);
				}
			}
		}
		for(Purchase p : c.getShoppingCart().getPrepairedPurchases()) {
			if( (end.isBefore(p.getEnd()) && end.isAfter(p.getStart())) || (start.isBefore(p.getEnd()) && start.isAfter(p.getStart())) ||
					(end.isAfter(p.getEnd()) && start.isBefore(p.getStart())) || (end.isBefore(p.getEnd()) && start.isAfter(p.getStart())) ||
					(end.isAfter(p.getEnd()) && start.isEqual(p.getStart())) || (end.isEqual(p.getEnd()) && start.isAfter(p.getStart())) ||
					(end.isEqual(p.getEnd()) && start.isBefore(p.getStart())) || (end.isBefore(p.getEnd()) && start.isEqual(p.getStart())) ||
					(end.isEqual(p.getEnd()) && start.isEqual(p.getStart())) ) {
				for(Vehicle v : p.getVehicles()) {
					result.remove(v);
				}
			}
		}
		for(Purchase p : pdao.getAll()) {
			if( (end.isBefore(p.getEnd()) && end.isAfter(p.getStart())) || (start.isBefore(p.getEnd()) && start.isAfter(p.getStart())) ||
					(end.isAfter(p.getEnd()) && start.isBefore(p.getStart())) || (end.isBefore(p.getEnd()) && start.isAfter(p.getStart())) ||
					(end.isAfter(p.getEnd()) && start.isEqual(p.getStart())) || (end.isEqual(p.getEnd()) && start.isAfter(p.getStart())) ||
					(end.isEqual(p.getEnd()) && start.isBefore(p.getStart())) || (end.isBefore(p.getEnd()) && start.isEqual(p.getStart())) ||
					(end.isEqual(p.getEnd()) && start.isEqual(p.getStart())) ) {
				if(p.getStatus() != PurchaseStatus.canceled && p.getStatus() != PurchaseStatus.declined) {
					for(Vehicle v : p.getVehicles()) {
						result.remove(v);
					}
				}
			}
		}
		return result;
	}

	@GET
	@Path("/vehicles/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Vehicle> getVehiclesFromRentACar(@PathParam("id") int id){
		UserDAO uDao = (UserDAO) ctx.getAttribute("userDAO");
		Manager m = (Manager)uDao.getById(id);
		RentACarDAO rDao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		return rDao.getFromRentACar(m.getRentACarId());
	}
	@GET
	@Path("/comments/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getRentACarComments(@PathParam("id") int id){
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.getApprovedRentACarComments(id);
	}
	@GET
	@Path("/allcomments/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getAllRentACarComments(@PathParam("id") int id){
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.getRentACarComments(id);
	}
	@POST
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void save(@PathParam("id") int id, RentACarDTO rentACarDTO){
		RentACarDAO dao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		int rentacarId = dao.save(rentACarDTO, id);
		UserDAO uDao = (UserDAO) ctx.getAttribute("userDAO");
		uDao.updateManager(id, rentacarId);
		linkManagers();
	}
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(RentACarDTO rentACarDTO) {
		RentACarDAO dao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		String message = dao.createRentACar(rentACarDTO);
		if(!message.equals("ok")) {
			return Response.status(400).entity(message).build();
		}else {
			return Response.status(200).build();
		}
	}
	@POST
	@Path("/newmanager")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveManager(Manager manager){
		RentACarDAO dao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		int rentacarId = dao.getNewId();
		UserDAO uDao = (UserDAO) ctx.getAttribute("userDAO");
		String message = uDao.saveManager(manager, rentacarId);
		if(!message.equals("ok")) {
			return Response.status(400).entity(message).build();
		}else {
			return Response.status(200).build();
		}
	}
	@PUT
	@Path("/commentapproval/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void approveComment(@PathParam("id") int id) {
		CommentDAO cdao = (CommentDAO) ctx.getAttribute("commentDAO");
		RentACarDAO rdao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		Comment c = cdao.getById(id);
		RentACar r = rdao.getById(c.getRentACarId());
		r.addGrade(c.getGrade());
		cdao.approveComment(id);
		rdao.update();
	}
	@POST
	@Path("/vehicle")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createVehicle(Vehicle vehicle, @Context HttpServletRequest request) {
		Manager manager = (Manager) request.getSession().getAttribute("currentUser");
		VehicleDAO vDao = (VehicleDAO) ctx.getAttribute("vehicleDAO");
		vehicle.setRentACarId(manager.getRentACarId());
		vehicle.setRentACar(manager.getRentACar());
		String message = vDao.save(vehicle);
		if(!message.equals("ok")) {
			return Response.status(400).entity(message).build();
		}else {
			return Response.status(200).build();
		}
	}
	@DELETE
	@Path("/deleterent/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeRent(@PathParam("id") int id) {
		RentACarDAO rdao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		VehicleDAO vDao = (VehicleDAO) ctx.getAttribute("vehicleDAO");
		UserDAO uDao = (UserDAO) ctx.getAttribute("userDAO");
		rdao.deleteRentACar(id);
		RentACar rent = rdao.getById(id);
		for(Vehicle v : rent.getVehicles()) {
			vDao.deleteVehicle(v.getId());
		}
		uDao.removeManagersRent(id);
	}
}
