package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.CustomerDAO;
import dao.RentACarDAO;
import dto.RentACarDTO;
import models.Customer;
import models.RentACar;

@Path("/rentacar")
public class RentACarService {
	@Context
	ServletContext ctx;
	public RentACarService() {
		
	}
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("rentACarDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("rentACarDAO", new RentACarDAO(contextPath));
		}
	}
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<RentACar> getAll(){
		RentACarDAO dao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		return dao.getAll();
	}
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public void save(RentACarDTO rentACarDTO){
		System.out.println(rentACarDTO.getBeginWorkTime());
		System.out.println(rentACarDTO.getEndWorkTime());
		RentACarDAO dao = (RentACarDAO) ctx.getAttribute("rentACarDAO");
		dao.save(rentACarDTO);
	}
}
