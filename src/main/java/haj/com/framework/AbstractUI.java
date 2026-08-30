package haj.com.framework;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;

import haj.com.entity.Users;
import haj.com.service.TasksService;
import haj.com.servlet.ClientInfo;
import haj.com.ui.SessionUI;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractUI.
 */
public abstract class AbstractUI implements AbstractUIInterface {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The session UI. */
	@ManagedProperty(value = "#{sessionUI}")
	private SessionUI sessionUI;

	/** The Constant LANDING_PAGE. */
	protected static final String LANDING_PAGE = "/pages/selectPortal.jsf";

	/** The logger. */
	protected final NSDMSLogger logger = NSDMSLogger.getLogger(this.getClass());

	@ManagedProperty("#{request.requestURI}")
	private String uri;

	private String validationErrors = "";
	
	// workflow permissions
	private boolean canEditAbstract = false;
	private boolean canSignOffAbstract = false;
	private boolean canUploadAbstract = false;
	private boolean canCompleteTaskAbstract = false;
	private boolean canApproveTaskAbstract = false;
	private boolean canFinalApproveTaskAbstract = false;

	/**
	 * Return the FacexContext.
	 *
	 * @return the context
	 */
	protected FacesContext getContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context == null) context = OfflineContext.getCurrentInstance();
		return context;
	}

	/**
	 * Validate that a password is long enough and contains both letters and digits.
	 *
	 * @param pwd
	 *            the pwd
	 * @param displayErrors
	 *            If true, FacesMessage objects will be set with any validation
	 *            failures.
	 * @return true, if successful
	 */
	protected boolean validatePassword(String pwd, boolean displayErrors) {
		if (pwd == null || pwd.length() < 6) {
			if (displayErrors) addErrorMessage("Your new password must be at least 6 characters long.");
			return false;
		}

		if (!pwd.matches(".*[0-9].*") || !pwd.matches(".*[a-zA-Z].*")) {
			if (displayErrors) addErrorMessage("Your new password must contain both letters and numbers.");
			return false;
		}

		return true;
	}

	/**
	 * Set an error FacesMessage for specified message.
	 *
	 * @param message
	 *            the message
	 */
	protected void addErrorMessage(String message) {
		addFacesMessage(null, FacesMessage.SEVERITY_ERROR, message);
	}

	/**
	 * Set an error Faces message for specified message as well as logging to error
	 * log.
	 *
	 * @param message
	 *            the message
	 * @param thrown
	 *            the thrown
	 */
	protected void addErrorMessage(String message, Throwable thrown) {
		addFacesMessage(null, FacesMessage.SEVERITY_ERROR, message);
		logger.fatal(message, thrown);
	}

	/**
	 * Set a warning FacesMessage for specified message.
	 *
	 * @param message
	 *            the message
	 */
	protected void addWarningMessage(String message) {
		addFacesMessage(null, FacesMessage.SEVERITY_WARN, message);
	}

	/**
	 * Set an info FacesMessage for specified message.
	 *
	 * @param message
	 *            the message
	 */
	protected void addInfoMessage(String message) {
		addFacesMessage(null, FacesMessage.SEVERITY_INFO, message);
	}

	/**
	 * Set specified key/value.
	 *
	 * @param key
	 *            Key of parameter to set.
	 * @param value
	 *            Value of parameter to set.
	 * @param inSession
	 *            Set parameter on HttpSession if true, else on ServletRequest
	 */
	protected void setParameter(String key, Object value, boolean inSession) {
		FacesContext facesContext = getContext();
		if (facesContext != null && facesContext.getExternalContext() != null) {
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
			if (inSession) request.getSession().setAttribute(key, value);
			else request.setAttribute(key, value);
		}
	}

	public void storeClientInfo() {
		try {
			FacesContext facesContext = getContext();
			if (facesContext != null && facesContext.getExternalContext() != null) {
				HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
				ClientInfo.instance().storeClientInfo(request, getSessionUI().getActiveUser(), getSessionUI().getTask());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void storeClientInfo(Users users) {
		FacesContext facesContext = getContext();
		if (facesContext != null && facesContext.getExternalContext() != null) {
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
			ClientInfo.instance().storeClientInfo(request, users, getSessionUI().getTask());
		}
	}

	/**
	 * Retrieve the current value of parameter.
	 *
	 * @param key
	 *            Key of parameter to retrieve.
	 * @param inSession
	 *            Return parameter from HttpSession if true, else from
	 *            ServletRequest
	 * @return the parameter
	 */
	protected Object getParameter(String key, boolean inSession) {
		FacesContext facesContext = getContext();
		if (facesContext != null && facesContext.getExternalContext() != null) {
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
			if (inSession) return request.getSession().getAttribute(key);
			else return request.getParameter(key);
		}
		return null;
	}

	/**
	 * Adds the faces message.
	 *
	 * @param clientId
	 *            the client id
	 * @param severity
	 *            the severity
	 * @param message
	 *            the message
	 */
	private void addFacesMessage(String clientId, FacesMessage.Severity severity, String message) {
		getContext().addMessage(clientId, new FacesMessage(severity, message, null));
	}

	/**
	 * Gets the servlet request.
	 *
	 * @return the servlet request
	 */
	private HttpServletRequest getServletRequest() {
		return (HttpServletRequest) getContext().getExternalContext().getRequest();
	}

	/**
	 * Redirect to specified page.
	 *
	 * @param outcome
	 *            the outcome
	 */
	protected void ajaxRedirect(String outcome) {
		FacesContext    ctx        = getContext();
		ExternalContext extContext = ctx.getExternalContext();
		String          url        = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, outcome));
		try {
			extContext.redirect(url);
		} catch (IOException ioe) {
			throw new FacesException(ioe);
		}
	}

	/**
	 * Return the originating IP address of specified request.
	 *
	 * @param request
	 *            the request
	 * @return the requesting IP
	 */
	protected String getRequestingIP(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	/**
	 * Write current event to the event log.
	 */
	protected void log() {
		log((Object) null);
	}

	/**
	 * Write the current event to the event log.
	 *
	 * @param additionalInfo
	 *            String,Object pairs of any additional/override data to store on
	 *            the event.
	 */
	protected void log(Object... additionalInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("ip", getRequestingIP(getServletRequest()));

			if (getSessionUI() != null) map.putAll(getSessionUI().getMap());

			StackTraceElement[] elements         = new Throwable().getStackTrace();
			int                 counter          = 0;
			String              callerMethodName = null;
			String              callerClassName  = null;
			do {
				callerMethodName = elements[counter].getMethodName();
				callerClassName = elements[counter].getClassName();
				counter++;
			} while (callerClassName.equalsIgnoreCase(AbstractUI.class.getName()));

			map.put("callingClass", callerClassName);
			map.put("callingMethod", callerMethodName);

			if (additionalInfo != null && additionalInfo.length > 0) for (int i = 1; i < additionalInfo.length; i += 2)
				if (additionalInfo[i - 1] != null) map.put(additionalInfo[i - 1].toString(), additionalInfo[i]);

			// AuditService auditService = new AuditService();
			// auditService.auditEvent(map);
		} catch (Exception e) {
			logger.error("Failed to log event", e);
		}
	}

	/**
	 * Return the SessionUI for this Session.
	 *
	 * @return the session UI
	 */
	public SessionUI getSessionUI() {
		return sessionUI;
	}

	/**
	 * For JSF impl to use to set the Managed Property. DO NOT set this manually.
	 *
	 * @param sessionUI
	 *            the new session UI
	 */
	public void setSessionUI(SessionUI sessionUI) {
		this.sessionUI = sessionUI;
	}

	/**
	 * Add a callback parameter to current Request.
	 *
	 * @param parm
	 *            the parm
	 * @param value
	 *            the value
	 */
	public static void addCallBackParm(String parm, Object value) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam(parm, value);
	}

	/**
	 * Removes the char.
	 *
	 * @param s
	 *            the s
	 * @param c
	 *            the c
	 * @return the string
	 */
	public static String removeChar(String s, char c) {
		StringBuffer r = new StringBuffer(s.length());
		r.setLength(s.length());
		int current = 0;
		for (int i = 0; i < s.length(); i++) {
			char cur = s.charAt(i);
			if (cur != c) r.setCharAt(current++, cur);
		}
		return r.toString();
	}

	/**
	 * Gets the time stamp.
	 *
	 * @return the time stamp
	 */
	public String getTimeStamp() {
		Date today = new Date();
		System.out.println("time stamp is " + new Timestamp(today.getTime()));
		String timeStamp = "" + new Timestamp(today.getTime());

		timeStamp = AbstractUI.removeChar(timeStamp, ':');
		timeStamp = AbstractUI.removeChar(timeStamp, '-');
		timeStamp = AbstractUI.removeChar(timeStamp, '.');
		timeStamp = AbstractUI.removeChar(timeStamp, ' ');

		String last3 = "" + Math.random();

		return timeStamp.substring(0, 17) + last3.substring(3, 6);
	}

	/**
	 * Gets the time zone string.
	 *
	 * @return the time zone string
	 */
	/*
	 * returns the time zone of the server
	 */
	public String getTimeZoneString() {

		return java.util.TimeZone.getDefault().getID();
	}

	/**
	 * Run client side execute.
	 *
	 * @param widgetVar
	 *            the widget var
	 */
	public void runClientSideExecute(String widgetVar) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute(widgetVar);
	}

	/**
	 * Run client side update.
	 *
	 * @param id
	 *            the id
	 */
	public void runClientSideUpdate(String id) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.update(id);
	}

	/**
	 * Removes the parm.
	 *
	 * @param key
	 *            the key
	 * @param session
	 *            the session
	 */
	public void removeParm(String key, boolean session) {
		FacesContext       facesContext = getAppContext();
		HttpServletRequest request      = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		if (session) request.getSession().removeAttribute(key);
		else request.removeAttribute(key);
	}

	/**
	 * Gets the app context.
	 *
	 * @return the app context
	 */
	public static FacesContext getAppContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context == null) context = OfflineContext.getCurrentInstance();

		return context;
	}

	/**
	 * Redirect.
	 *
	 * @param outcome
	 *            the outcome
	 */
	public void redirect(String outcome) {
		FacesContext        facesContext = getAppContext();
		HttpServletResponse response     = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		HttpServletRequest  request      = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		try {
			response.sendRedirect(request.getContextPath() + outcome);
		} catch (Exception ex) {
			logger.fatal(ex);
		}
	}

	/**
	 * Gets the bean.
	 *
	 * @param beanName
	 *            the bean name
	 * @return the bean
	 */
	public static Object getBean(String beanName) {
		Object       bean = null;
		FacesContext fc   = FacesContext.getCurrentInstance();
		if (fc != null) {
			ELContext elContext = fc.getELContext();
			bean = elContext.getELResolver().getValue(elContext, null, beanName);
		}

		return bean;
	}

	/*
	 * public boolean isBilling() { return BillingConstants.BILLING_ENABLED; }
	 */

	/**
	 * Gets the entry language.
	 *
	 * @param key
	 *            the key
	 * @return the entry language
	 */
	public static String getEntryLanguage(String key) {
		String         value  = "";
		ResourceBundle bundle = getResourceBundle();
		try {
			value = bundle.getString(key);
		} catch (MissingResourceException missingException) {
			// logger.fatal("The Key Message doesn't exist in any resource file key=" +
			// key);
			missingException.printStackTrace();
		}
		return value;
	}

	/**
	 * Gets the entry language with params.
	 *
	 * @param key
	 *            the key
	 * @return the entry language
	 */
	public static String getEntryLanguage(String key, Object... params) {
		String         value  = "";
		ResourceBundle bundle = getResourceBundle();
		try {
			value = bundle.getString(key);
			MessageFormat formatter = new MessageFormat(value, FacesContext.getCurrentInstance().getViewRoot().getLocale());
			value = formatter.format(params);
		} catch (MissingResourceException missingException) {
			missingException.printStackTrace();
		}
		return value;
	}

	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle("techfinium.lang.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());
	}

	public void checkPermission(String page) {
		if (!sessionUI.isEmployee()) {
			if (sessionUI.getDashboard() != null) ajaxRedirect(sessionUI.getDashboard());
			else ajaxRedirect("/pages/dashboard.jsf");
		} else {
			switch (page) {
				case "":

					break;

				default:
					break;
			}
		}
	}

	/**
	 * @param divName
	 * @throws Exception
	 */
	public void pageFocusRun(String divName) throws Exception {

//		System.out.println("pageFocusRun ran with divName : " + divName + " !");

		String js = "		setTimeout(function() { " + "			$('html,body').animate({ " + "				scrollTop : $('#" + divName + "').offset().top " + "			}, 1000); " + "		}, 0);";

		this.runClientSideExecute(js);

		divName = null;
	}

	public void ajaxRedirectToDashboard() {
		getSessionUI().setTask(null);
		if (getSessionUI().getDashboard() != null && !getSessionUI().getDashboard().isEmpty()) {
			ajaxRedirect(getSessionUI().getDashboard());
		} else {
			ajaxRedirect("/pages/externalparty/dashboard.jsf");
		}

	}

	public void redirectToDashboard() {
		getSessionUI().setTask(null);
		if (getSessionUI().getDashboard() != null && !getSessionUI().getDashboard().isEmpty()) {
			redirect(getSessionUI().getDashboard());
		} else {
			redirect("/pages/externalparty/dashboard.jsf");
		}

	}

	/**
	 * Gets the now.
	 *
	 * @return the now
	 */
	public Date getNow() {

		return new Date();
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getExternalSessionId() {
		String id = "";
		try {
			FacesContext facesContext = getContext();
			if (facesContext != null && facesContext.getExternalContext() != null) {
				HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
				id = request.getSession().getId();
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		return id;
	}

	public void extractValidationErrors(javax.validation.ConstraintViolationException e) {
		validationErrors = "";
		e.getConstraintViolations().forEach(elt -> {
			validationErrors += elt.getMessage() + "<br/>";
		});
		getSessionUI().setValidationErrors(validationErrors);
		runClientSideUpdate("validationErrorForm");
	}
	
	public String extractValidationErrorsReturnString(javax.validation.ConstraintViolationException e) {
		validationErrors = "";
		e.getConstraintViolations().forEach(elt -> {
			validationErrors += elt.getMessage() + "<br/>";
		});
		return validationErrors;
	}

	public String getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(String validationErrors) {
		this.validationErrors = validationErrors;
	}
	
	// Determines user actions on work flow tasks
	public void determainUserActions() throws Exception{
		if (getSessionUI() != null && getSessionUI().getTask() != null && getSessionUI().getTask().getProcessRole() != null && getSessionUI().getTask().getProcessRole().getRolePermission() != null) {
			canEditAbstract = TasksService.instance().canEditBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
			canUploadAbstract = TasksService.instance().canUploadBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
			canCompleteTaskAbstract = TasksService.instance().canCompleteTaskBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
			canApproveTaskAbstract = TasksService.instance().canApproveTaskBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
			canFinalApproveTaskAbstract = TasksService.instance().canFinalApproveTaskBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
			canSignOffAbstract = TasksService.instance().canSignOffTaskBasedOnRolePermission(getSessionUI().getTask().getProcessRole().getRolePermission());
		} else {
			canEditAbstract = false;
			canUploadAbstract = false;
			canCompleteTaskAbstract = false;
			canApproveTaskAbstract = false;
			canFinalApproveTaskAbstract = false;
			canSignOffAbstract = false;
		}
	}

	public boolean isCanEditAbstract() {
		return canEditAbstract;
	}

	public void setCanEditAbstract(boolean canEditAbstract) {
		this.canEditAbstract = canEditAbstract;
	}

	public boolean isCanUploadAbstract() {
		return canUploadAbstract;
	}

	public void setCanUploadAbstract(boolean canUploadAbstract) {
		this.canUploadAbstract = canUploadAbstract;
	}

	public boolean isCanCompleteTaskAbstract() {
		return canCompleteTaskAbstract;
	}

	public void setCanCompleteTaskAbstract(boolean canCompleteTaskAbstract) {
		this.canCompleteTaskAbstract = canCompleteTaskAbstract;
	}

	public boolean isCanApproveTaskAbstract() {
		return canApproveTaskAbstract;
	}

	public void setCanApproveTaskAbstract(boolean canApproveTaskAbstract) {
		this.canApproveTaskAbstract = canApproveTaskAbstract;
	}

	public boolean isCanFinalApproveTaskAbstract() {
		return canFinalApproveTaskAbstract;
	}

	public void setCanFinalApproveTaskAbstract(boolean canFinalApproveTaskAbstract) {
		this.canFinalApproveTaskAbstract = canFinalApproveTaskAbstract;
	}

	public boolean isCanSignOffAbstract() {
		return canSignOffAbstract;
	}

	public void setCanSignOffAbstract(boolean canSignOffAbstract) {
		this.canSignOffAbstract = canSignOffAbstract;
	}


}
