package services;

import java.util.ArrayList;
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

import dao.ManagerDAO;
import models.Manager;
import models.User;

@Path("/managers")
public class ManagerService {
	@Context
	ServletContext context;
	public ManagerService() {
		
	}
	@PostConstruct
	public void init() {
		if(context.getAttribute("managerDAO") == null) {
			String contextPath = context.getRealPath("");
			context.setAttribute("managerDAO", new ManagerDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manager> getAll(){
		ManagerDAO dao = (ManagerDAO) context.getAttribute("managerDAO");
		return dao.getFreeManagers();
	}
	
}
