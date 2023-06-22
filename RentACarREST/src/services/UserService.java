package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
import models.Customer;
import models.User;

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
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAll(){
		UserDAO dao = (UserDAO) context.getAttribute("userDAO");
		return dao.getAll();
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getById(@PathParam("id") Integer id){
		UserDAO dao = (UserDAO) context.getAttribute("userDAO");
		return dao.getById(id);
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(Customer c, @Context HttpServletRequest request){
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
	public Response edit(Customer c){
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
}
