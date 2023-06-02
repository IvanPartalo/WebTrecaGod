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

import dao.CustomerDAO;
import models.Customer;
import models.User;

@Path("/customers")
public class CustomerService {
	@Context
	ServletContext context;
	public CustomerService() {
		
	}
	@PostConstruct
	public void init() {
		if(context.getAttribute("customerDAO") == null) {
			String contextPath = context.getRealPath("");
			context.setAttribute("customerDAO", new CustomerDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAll(){
		CustomerDAO dao = (CustomerDAO) context.getAttribute("customerDAO");
		return dao.getAll();
	}
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getById(@PathParam("id") Integer id){
		CustomerDAO dao = (CustomerDAO) context.getAttribute("customerDAO");
		return dao.getById(id);
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(Customer c, @Context HttpServletRequest request){
		CustomerDAO dao = (CustomerDAO) context.getAttribute("customerDAO");
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
		CustomerDAO dao = (CustomerDAO) context.getAttribute("customerDAO");
		String message = dao.editCustomer(c);
		if(!message.equals("ok")) {
			return Response.status(400).entity(message).build();
		}else {
			return Response.status(200).build();
		}
	}
}
