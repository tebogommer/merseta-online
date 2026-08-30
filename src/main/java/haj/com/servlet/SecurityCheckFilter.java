package haj.com.servlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import haj.com.annotations.PageAccessAnnotation;
import haj.com.entity.lookup.Roles;
import haj.com.utils.ReflectionUtils;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityCheckFilter.
 */
public class SecurityCheckFilter implements Filter {

	private final static String FILTER_APPLIED = "_security_filter_applied";

	private static final NSDMSLogger logger = NSDMSLogger.getLogger(SecurityCheckFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (request.getAttribute(FILTER_APPLIED) != null) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletRequest hreq = (HttpServletRequest) request;
		HttpServletResponse hres = (HttpServletResponse) response;
		HttpSession session = hreq.getSession();
		String contextPath = hreq.getContextPath();
		String checkPage = hreq.getRequestURI();

		hres.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		hres.setHeader("Pragma", "no-cache");
		hres.setDateHeader("Expires", 0);

		if (session.getAttribute("logMeOff") != null) {
			if ((Boolean) session.getAttribute("logMeOff")) {
				session.setAttribute("uobj", null);
				session.setAttribute("logMeOff", null);
			}
		}

		if (request.getAttribute(FILTER_APPLIED) != null) {
			chain.doFilter(request, response);
			return;
		}

		if ((request.getAttribute(FILTER_APPLIED) == null) && (!checkPage.endsWith("login.jsf")) && (!checkPage.endsWith("welcome.jsf")) && (checkPage.endsWith(".jsf"))) {
			request.setAttribute(FILTER_APPLIED, Boolean.TRUE);

			Object user = null;
			user = session.getAttribute("uobj");

			if (user == null) {
				if ("partial/ajax".equals(hreq.getHeader("Faces-Request"))) {
					hres.setContentType("text/xml");
					hres.getWriter().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>", contextPath + "/login.jsf");
					return;
				} else {
					logger.debug("authorization breeched");
					if (checkPage.contains("mobile")) hres.sendRedirect(contextPath + "/mobile/index.jsp");
					else hres.sendRedirect(contextPath + "/login.jsf");
					return;
				}
			} else {
				Object ses = null;
				ses = session.getAttribute("sessionUI");
				if (ses != null && ses instanceof haj.com.ui.SessionUI) {
					haj.com.ui.SessionUI jsfSession = (haj.com.ui.SessionUI) ses;
					if (checkPage.contains("/admin/") && !jsfSession.isAdmin() && !jsfSession.isFinance()) {
						redirectDashboard(hres, contextPath, jsfSession);
					} else if (!jsfSession.isAdmin()) {
						List<Field> fields = ReflectionUtils.getFieldsWithAnnotations(Roles.class.getDeclaredFields(), PageAccessAnnotation.class);
						String page = checkPage.replaceAll(contextPath, "");
						for (Field field : fields) {
							try {
								field.setAccessible(true);
								PageAccessAnnotation testerInfo = (PageAccessAnnotation) field.getAnnotation(PageAccessAnnotation.class);
								if (page.equals(testerInfo.page())) {
									if (!jsfSession.isEmployee() || !(Boolean) field.get(jsfSession.getRole())) {
										redirectDashboard(hres, contextPath, jsfSession);
										break;
									}
								}
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						}

					}
				}
			}
		}
		chain.doFilter(request, response);
	}

	public void redirectDashboard(HttpServletResponse hres, String contextPath, haj.com.ui.SessionUI jsfSession) throws IOException {
		if (jsfSession.getDashboard() != null) hres.sendRedirect(contextPath + jsfSession.getDashboard());
		else hres.sendRedirect(contextPath + "/login.jsf");
	}

}
