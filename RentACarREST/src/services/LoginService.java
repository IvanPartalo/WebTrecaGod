package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.CustomerTypeDAO;
import dao.UserDAO;
import models.Customer;
import models.Role;
import models.User;

@Path("")
public class LoginService {
	@Context
	ServletContext ctx;
	
	public LoginService() {
		
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("userDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new UserDAO(contextPath));
		}
		if(ctx.getAttribute("customerTypeDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("customerTypeDAO", new CustomerTypeDAO(contextPath));
		}
		linkCustomerTypes();
	}
	private void linkCustomerTypes() {
		UserDAO udao = (UserDAO) ctx.getAttribute("userDAO");
		CustomerTypeDAO cTdao = (CustomerTypeDAO) ctx.getAttribute("customerTypeDAO");
		for(User u : udao.getAll()) {
			if(u.getRole() == Role.customer) {
				Customer c = (Customer)u;
				c.setCustomerType(cTdao.getByPoints(c.getCollectedPoints()));
			}
		}
	}
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user, @Context HttpServletRequest request) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		User loggedUser = userDAO.find(user.getUsername(), user.getPassword());
		if(loggedUser == null) {
			return Response.status(400).entity("Wrong username or password").build();
		}
		request.getSession().setAttribute("currentUser", loggedUser);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/logout")
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	@GET
	@Path("/currentUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("currentUser");
	}
}
