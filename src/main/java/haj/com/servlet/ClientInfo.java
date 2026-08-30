package haj.com.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import haj.com.dao.UsersDAO;
import haj.com.entity.Tasks;
import haj.com.entity.UserBrowserInformation;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;

public class ClientInfo extends AbstractService {

	/** The doc service. */
	private static ClientInfo clientInfo = null;
	private UsersDAO dao = new UsersDAO();

	/**
	 * Instance.
	 *
	 * @return the doc service
	 */
	public static synchronized ClientInfo instance() {
		if (clientInfo == null) {
			clientInfo = new ClientInfo();
		}
		return clientInfo;
	}

	public void storeClientInfo(HttpServletRequest request, Users users, Tasks tasks) {
		try {
			String referer = getReferer(request);
			String fullURL = getFullURL(request);
			String clientIpAddr = getClientIpAddr(request);
			String clientOS = getClientOS(request);
			String clientBrowser = getClientBrowser(request);
			String userAgent = getUserAgent(request);
			UserBrowserInformation ubi = new UserBrowserInformation(userAgent, clientOS, clientBrowser, clientIpAddr, fullURL, referer, users, tasks);
			getLatLong(request, ubi);
			StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
			for (int i = 1; i < stElements.length; i++) {
				StackTraceElement ste = stElements[i];
				if (ste.getClassName().indexOf("haj.com.ui") == 0) {
					ubi.setCallingClass(ste.getClassName());
					ubi.setCallingMethod(ste.getMethodName());
					break;
				}
			}
			dao.create(ubi);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < stElements.length; i++) {
			StackTraceElement ste = stElements[i];
			if (ste.getClassName().indexOf("java.lang.Thread") != 0) {
				return ste.getClassName();
			}
		}
		return null;
	}

	public void printClientInfo(HttpServletRequest request) {
		final String referer = getReferer(request);
		final String fullURL = getFullURL(request);
		final String clientIpAddr = getClientIpAddr(request);
		final String clientOS = getClientOS(request);
		final String clientBrowser = getClientBrowser(request);
		final String userAgent = getUserAgent(request);

		System.out.println("\n" + "User Agent \t" + userAgent + "\n" + "Operating System\t" + clientOS + "\n" + "Browser Name\t" + clientBrowser + "\n" + "IP Address\t" + clientIpAddr + "\n" + "Full URL\t" + fullURL + "\n" + "Referrer\t" + referer);
	}

	public String getReferer(HttpServletRequest request) {
		final String referer = request.getHeader("referer");
		return referer;
	}

	public String getFullURL(HttpServletRequest request) {
		final StringBuffer requestURL = request.getRequestURL();
		final String queryString = request.getQueryString();

		final String result = queryString == null ? requestURL.toString() : requestURL.append('?').append(queryString).toString();

		return result;
	}

	// http://stackoverflow.com/a/18030465/1845894
	public String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	// http://stackoverflow.com/a/18030465/1845894
	public String getClientOS(HttpServletRequest request) {
		final String browserDetails = request.getHeader("User-Agent");

		// =================OS=======================
		final String lowerCaseBrowser = browserDetails.toLowerCase();
		if (lowerCaseBrowser.contains("windows")) {
			return "Windows";
		} else if (lowerCaseBrowser.contains("mac")) {
			return "Mac";
		} else if (lowerCaseBrowser.contains("x11")) {
			return "Unix";
		} else if (lowerCaseBrowser.contains("android")) {
			return "Android";
		} else if (lowerCaseBrowser.contains("iphone")) {
			return "IPhone";
		} else {
			return "UnKnown, More-Info: " + browserDetails;
		}
	}

	// http://stackoverflow.com/a/18030465/1845894
	public String getClientBrowser(HttpServletRequest request) {
		final String browserDetails = request.getHeader("User-Agent");
		final String user = browserDetails.toLowerCase();

		String browser = "";

		// ===============Browser===========================
		if (user.contains("msie")) {
			String substring = browserDetails.substring(browserDetails.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			browser = (browserDetails.substring(browserDetails.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-" + (browserDetails.substring(browserDetails.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera")) browser = (browserDetails.substring(browserDetails.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-" + (browserDetails.substring(browserDetails.indexOf("Version")).split(" ")[0]).split("/")[1];
			else if (user.contains("opr")) browser = ((browserDetails.substring(browserDetails.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
		} else if (user.contains("chrome")) {
			browser = (browserDetails.substring(browserDetails.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
			// browser=(userAgent.substring(userAgent.indexOf("MSIE")).split("
			// ")[0]).replace("/", "-");
			browser = "Netscape-?";

		} else if (user.contains("firefox")) {
			browser = (browserDetails.substring(browserDetails.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			browser = "IE";
		} else {
			browser = "UnKnown, More-Info: " + browserDetails;
		}

		return browser;
	}

	public String getUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	public void getLatLong(HttpServletRequest request, UserBrowserInformation ubi) {
		HttpSession session = request.getSession();
		Object ses = session.getAttribute("sessionUI");
		if (ses != null && ses instanceof haj.com.ui.SessionUI) {
			haj.com.ui.SessionUI jsfSession = (haj.com.ui.SessionUI) ses;
			ubi.setLatitude(jsfSession.getLatitude());
			ubi.setLongitude(jsfSession.getLongitude());
		}
	}
}
