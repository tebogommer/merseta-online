package haj.com.rest;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



// TODO: Auto-generated Javadoc
/**
 * The Class TechFiniumRest.
 */
@Path("/techfinium")
public class TechFiniumRest implements Serializable {

	
	/** The logger. */
	protected final Log logger = LogFactory.getLog(this.getClass());
//	private static RestService service = new RestService();		
	/**
 * Testing .
 *
 * @return the string
 */
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "Techfinium REST" + "</title>"
				+ "<body><h1>" + "Welcome to Techfinium REST Services" + "</body></h1>" + "</html> ";
	}
	
/*
	
	@POST
	@Path("/logon")
	@Produces(MediaType.APPLICATION_JSON)
	public Users logon(
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("phoneId") String phoneId,
			@FormParam("iosAndroid") String iosAndroid){ 
	    logger.info("/logon email: "+email + " phoneId: "+phoneId + " iosAndroid: "+iosAndroid  );
	    return service.logon(email, password, phoneId, iosAndroid);
	}
	
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Users register(
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("phoneId") String phoneId,
			@FormParam("iosAndroid") String iosAndroid,
			@FormParam("company") String company,
			@FormParam("site") String site)
	{ 
	    logger.info("/register email: "+email +"firstName: "+firstName+  "lastName: "+lastName+" phoneId: "+phoneId + " iosAndroid: "+iosAndroid+" company: "+company + " site: "+site   );
      
	    return service.register(  firstName,  lastName,  email,  password,  company,  site,  iosAndroid, phoneId);
	}
	
	
	@POST
	@Path("/allCrushers")
	@Produces(MediaType.APPLICATION_JSON)
	public AllCrushersBean allCrushers()
	{ 
	    logger.info("/allCrushers"  );
	    return service.allCrushers();
	}
	
	
	@POST
	@Path("/order")
	@Produces(MediaType.APPLICATION_JSON)
	public Orders order(
			@FormParam("json") String json) { 
	    logger.info("/logon json: "+json );
	    return service.order(json);
	}
	*/
}
