package services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
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

import dao.CustomerTypeDAO;
import dao.PurchaseDAO;
import dao.UserDAO;
import dao.VehicleDAO;
import models.Customer;
import models.Manager;
import models.Purchase;
import models.PurchaseStatus;
import models.Role;
import models.ShoppingCart;
import models.SubPurchase;
import models.User;
import models.Vehicle;

@Path("/users")
public class UserService {
	@Context
	ServletContext context;
	public UserService() {
		
	}
	@PostConstruct
	public void init() {
		if(context.getAttribute("userDAO") == null) {
			String contextPath = context.getRealPath("");
			context.setAttribute("userDAO", new UserDAO(contextPath));
		}
		if(context.getAttribute("customerTypeDAO") == null) {
			String contextPath = context.getRealPath("");
			context.setAttribute("customerTypeDAO", new CustomerTypeDAO(contextPath));
		}
		if(context.getAttribute("purchaseDAO") == null) {
			String contextPath = context.getRealPath("");
			context.setAttribute("purchaseDAO", new PurchaseDAO(contextPath));
		}
	}
	@GET
	@Path("/cart")
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingCart getCart(@Context HttpServletRequest request){
		User u = (User) request.getSession().getAttribute("currentUser");
		Customer c = (Customer)u;
		return c.getShoppingCart();
	}
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAll(){
		UserDAO dao = (UserDAO) context.getAttribute("userDAO");
		return dao.getAll();
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getById(@PathParam("id") Integer id){
		UserDAO dao = (UserDAO) context.getAttribute("userDAO");
		return dao.getById(id);
	}
	@POST
	@Path("/addToCart/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addToCart(Purchase purchase, @PathParam("id") Integer id, @Context HttpServletRequest request){
		User u = (User) request.getSession().getAttribute("currentUser");
		VehicleDAO vDAO = (VehicleDAO) context.getAttribute("vehicleDAO");
		Vehicle v = vDAO.getById(id);
		if(v != null) {
			Customer c = (Customer)u;
			c.getShoppingCart().addVehicle(v);
			c.getShoppingCart().addPrice(v.getPrice());
			for(Purchase p : c.getShoppingCart().getPrepairedPurchases()) {
				if(p.getStartDateTime().equals(purchase.getStartDateTime()) && p.getEndDateTime().equals(purchase.getEndDateTime())) {
					p.getVehicleIds().add(v.getId());
					p.getVehicles().add(v);
					p.addPrice(v.getPrice());
					if(!p.getRentACars().contains(v.getRentACar())) {
						p.getRentACars().add(v.getRentACar());
						p.getSubPurchases().add(new SubPurchase(p.getId(), v.getRentACarId(), p.getDuration(),
							p.getStartDateTime(), p.getStatus()));
					}
					return Response.status(200).build();
				}
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(purchase.getStartDateTime(), formatter);
			purchase.setStart(dateTime);
			dateTime = LocalDateTime.parse(purchase.getEndDateTime(), formatter);
			purchase.setEnd(dateTime);
			Duration d = Duration.between(purchase.getStart(), purchase.getEnd());
			purchase.setDuration((int) (d.getSeconds()/3600));
			purchase.setPrice(v.getPrice());
			purchase.setStatus(PurchaseStatus.pending);
			purchase.setCustomerId(c.getId());
			purchase.setCustomer(c);
			purchase.getRentACars().add(v.getRentACar());
			purchase.getVehicleIds().add(v.getId());
			purchase.getVehicles().add(v);
			purchase.getSubPurchases().add(new SubPurchase(purchase.getId(), v.getRentACarId(), purchase.getDuration(),
					purchase.getStartDateTime(), purchase.getStatus()));
			c.getShoppingCart().getPrepairedPurchases().add(purchase);
			return Response.status(200).build();
		}
		return Response.status(400).entity("error").build();
	}
	@POST
	@Path("/rent")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response rent(@Context HttpServletRequest request){
		PurchaseDAO pdao = (PurchaseDAO) context.getAttribute("purchaseDAO");
		User u = (User) request.getSession().getAttribute("currentUser");
		Customer c = (Customer)u;
		for(Purchase p : c.getShoppingCart().getPrepairedPurchases()) {
			pdao.save(p);
		}
		c.setShoppingCart(new ShoppingCart());
		c.getShoppingCart().setUser(c);
		return Response.status(200).build();
	}
	@DELETE
	@Path("/removeFromCart/{id}")
	public Response removeFromCart(@PathParam("id") Integer id, @Context HttpServletRequest request){
		User u = (User) request.getSession().getAttribute("currentUser");
		VehicleDAO vDAO = (VehicleDAO) context.getAttribute("vehicleDAO");
		Vehicle v = vDAO.getById(id);
		if(v != null) {
			Customer c = (Customer)u;
			c.getShoppingCart().removeVehicle(v);
			c.getShoppingCart().removePrice(v.getPrice());
			ArrayList<Purchase> forRemoving = new ArrayList<>();
			for(Purchase p : c.getShoppingCart().getPrepairedPurchases()){
				if(p.getVehicles().contains(v)) {
					forRemoving.add(p);
				}
			}
			for(Purchase p : forRemoving){
				c.getShoppingCart().getPrepairedPurchases().remove(p);
			}
		}
		if(v == null) {
			return Response.status(400).entity("error").build();
		}else {
			return Response.status(200).build();
		}
	}
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(User c, @Context HttpServletRequest request){
		UserDAO dao = (UserDAO) context.getAttribute("userDAO");
		String message = dao.saveCustomer(c);
		if(!message.equals("ok")) {
			return Response.status(400).entity(message).build();
		}else {
			return Response.status(200).build();
		}
	}
	@PUT
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response edit(User c){
		UserDAO dao = (UserDAO) context.getAttribute("userDAO");
		String message = dao.editCustomer(c);
		if(!message.equals("ok")) {
			return Response.status(400).entity(message).build();
		}else {
			return Response.status(200).build();
		}
	}
	@PUT
	@Path("/changepassword/{id}/{oldPassword}/{newPassword}")
	public boolean changePassword(@PathParam("id") Integer id, @PathParam("oldPassword") String oldPassword, @PathParam("newPassword") String newPassword){
		UserDAO dao = (UserDAO) context.getAttribute("userDAO");
		return dao.changePassword(id, oldPassword, newPassword);
	}
	
	@GET
	@Path("/freeManagers")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manager> getFreeManagers(){
		UserDAO dao = (UserDAO) context.getAttribute("userDAO");
		return dao.getFreeManagers();
	}
}
