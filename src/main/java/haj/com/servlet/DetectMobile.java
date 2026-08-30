package haj.com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class DetectMobile.
 */
public class DetectMobile extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		// undeploy used parsers cleanly
		UADetectorServiceFactory.getOnlineUpdatingParser().shutdown();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		

		PrintWriter out = response.getWriter();
		out.println("<h1>Hello Servlet</h1>");
		out.println("session=" + request.getSession(true).getId());
		out.println("<br>");
		out.println("<br>");

		// Get an UserAgentStringParser and analyze the requesting client
		UserAgentStringParser parser = UADetectorServiceFactory.getOnlineUpdatingParser();
		ReadableUserAgent agent = parser.parse(request.getHeader("User-Agent"));

		out.append("You're a <em>");
		out.append(agent.getName());
		out.append("</em> on <em>");
		out.append(agent.getOperatingSystem().getName());
		
		out.append("</em>!");
		out.append("<h1>"+agent.getDeviceCategory().getCategory().ordinal()+ "&nbsp; = &nbsp;"+agent.getDeviceCategory().getName()+"</h1>");
	*/
		String page = request.getParameter("page");
		UserAgentStringParser parser = UADetectorServiceFactory.getOnlineUpdatingParser();
		ReadableUserAgent agent = parser.parse(request.getHeader("User-Agent"));
		System.out.println(request.getContextPath());
		
		String prefix = request.getContextPath()+"";
		if(!response.isCommitted()) {	
		 if (agent.getDeviceCategory().getCategory().ordinal() == 5) {
			prefix += "/mobile";
		 }
		 if ("L".equals(page)) response.sendRedirect(prefix+"/logon.jsf"); 
		 else if ("R".equals(page)) response.sendRedirect(prefix+"/register.jsf"); 
		 else response.sendRedirect(prefix+"/index.jsp");
		}
	}
	
}
