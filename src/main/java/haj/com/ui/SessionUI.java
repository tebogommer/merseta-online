package haj.com.ui;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import haj.com.constants.HAJConstants;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompany;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.lookup.Roles;
import haj.com.json.Weather;
import haj.com.sars.SarsFiles;

// TODO: Auto-generated Javadoc
/**
 * The Class SessionUI.
 */
@ManagedBean(name = "sessionUI")
@SessionScoped
/**
 * This is the only object that should ever exist in session, anything required
 * on the session object should be defined as Serializable and access modifiers
 * to instance given here.
 *
 *
 */
public class SessionUI implements Serializable {

	/** The user. */
	private Users user;

	/** The active user. */
	private Users activeUser;

	/** The wsp session. */
	private Wsp wspSession;

	/** The latitude. */
	private Double latitude;

	/** The longitude. */
	private Double longitude;

	/** The weather. */
	private Weather weather;

	/** The hosting company. */
	private HostingCompany hostingCompany;

	/** The sel doc. */
	private Doc selDoc;

	/** The upload heading. */
	private String uploadHeading;

	/** The task. */
	private Tasks task;

	/** The role. */
	private Roles role;

	/** The roles. */
	private List<Roles> roles;

	/** The dashboard. */
	private String dashboard;

	/** The dashboard for reporting. */
	private String dashboardReporting;

	/** The external party. */
	private boolean externalParty;

	/** The external party. */
	private boolean allowableInternalUser;

	/** The sars files. */
	private SarsFiles sarsFiles;

	/** The active index. */
	private int     activeIndex;
	private boolean acceptPopi;
	private boolean finance;
	private boolean admin;
	private boolean employee;
	private boolean sdf;
	private boolean learner;
	private boolean contact;
	private boolean trainingProvider;
	private boolean assMod;
	private boolean nonSetaCompany;
	private boolean mentorregistration;
	
	// private boolean hideETQA = true;
	private boolean hideETQA = "P".equals(HAJConstants.DEV_TEST_PROD);

	private String wsUrl;
	private String externalSessionId;

	private Boolean emailSupport;

	private String validationErrors = "";

	/**
	 * Should be executed before log off and after login, reset bean to default
	 * values.
	 */
	public void clear() {
		setUser(null);
		setActiveUser(null);
		setHostingCompany(null);
		setSelDoc(null);
		setUploadHeading(null);
		setTask(null);
		setRole(null);
		setRoles(null);
		setDashboard(null);
		setDashboardReporting(null);
		setExternalParty(false);
		setWeather(null);
		setEmployee(false);
		setAdmin(false);
		setFinance(false);
		setSdf(false);
		setLearner(false);
		setContact(false);
		setTrainingProvider(false);
		setAssMod(false);
		setNonSetaCompany(false);
		setMentorregistration(false);
	}

	public void resetValidationErrors() {
		if (isNewRequest()) {
			validationErrors = "";
		}
	}

	public boolean isNewRequest() {
		final FacesContext fc               = FacesContext.getCurrentInstance();
		final boolean      getMethod        = ((HttpServletRequest) fc.getExternalContext().getRequest()).getMethod().equals("GET");
		final boolean      ajaxRequest      = fc.getPartialViewContext().isAjaxRequest();
		final boolean      validationFailed = fc.isValidationFailed();
		return getMethod && !ajaxRequest && !validationFailed;
	}

	/**
	 * This is vital for audit logging, any new items added to the SessionUI that
	 * may be required in the audit logging should be included into the map below.
	 *
	 * @return the map
	 */
	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<String, Object>();

		// logged in user
		Long userId = null;
		/*
		 * if (user != null) userId = user.getUid(); map.put("userId", userId);
		 */
		return map;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getMap().toString();
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * Gets the now.
	 *
	 * @return the now
	 */
	public Date getNow() {

		return new Date();
	}

	/**
	 * Gets the activeUser.
	 *
	 * @return the activeUser
	 */
	public Users getActiveUser() {
		return activeUser;
	}

	/**
	 * Sets the activeUser.
	 *
	 * @param activeUser
	 *            the new active user
	 */
	public void setActiveUser(Users activeUser) {
		this.activeUser = activeUser;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude
	 *            the new latitude
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude
	 *            the new longitude
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets the weather.
	 *
	 * @return the weather
	 */
	public Weather getWeather() {
		return weather;
	}

	/**
	 * Sets the weather.
	 *
	 * @param weather
	 *            the new weather
	 */
	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	/**
	 * Gets the hosting company.
	 *
	 * @return the hosting company
	 */
	public HostingCompany getHostingCompany() {
		return hostingCompany;
	}

	/**
	 * Sets the hosting company.
	 *
	 * @param hostingCompany
	 *            the new hosting company
	 */
	public void setHostingCompany(HostingCompany hostingCompany) {
		this.hostingCompany = hostingCompany;
	}

	/**
	 * Gets the sel doc.
	 *
	 * @return the sel doc
	 */
	public Doc getSelDoc() {
		return selDoc;
	}

	/**
	 * Sets the sel doc.
	 *
	 * @param selDoc
	 *            the new sel doc
	 */
	public void setSelDoc(Doc selDoc) {
		this.selDoc = selDoc;
	}

	/**
	 * Gets the upload heading.
	 *
	 * @return the upload heading
	 */
	public String getUploadHeading() {
		return uploadHeading;
	}

	/**
	 * Sets the upload heading.
	 *
	 * @param uploadHeading
	 *            the new upload heading
	 */
	public void setUploadHeading(String uploadHeading) {
		this.uploadHeading = uploadHeading;
	}

	/**
	 * Gets the task.
	 *
	 * @return the task
	 */
	public Tasks getTask() {
		return task;
	}

	/**
	 * Sets the task.
	 *
	 * @param task
	 *            the new task
	 */
	public void setTask(Tasks task) {
		this.task = task;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public Roles getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role
	 *            the new role
	 */
	public void setRole(Roles role) {
		this.role = role;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<Roles> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	/**
	 * Gets the dashboard.
	 *
	 * @return the dashboard
	 */
	public String getDashboard() {
		return dashboard;
	}

	/**
	 * Sets the dashboard.
	 *
	 * @param dashboard
	 *            the new dashboard
	 */
	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}

	/**
	 * Checks if is external party.
	 *
	 * @return true, if is external party
	 */
	public boolean isExternalParty() {
		return externalParty;
	}

	/**
	 * Sets the external party.
	 *
	 * @param externalParty
	 *            the new external party
	 */
	public void setExternalParty(boolean externalParty) {
		this.externalParty = externalParty;
	}

	/**
	 * Checks if is external party.
	 *
	 * @return true, if is external party
	 */
	public boolean isAllowableInternalUser() {
		return allowableInternalUser;
	}

	/**
	 * Sets the external party.
	 *
	 * @param externalParty
	 *            the new external party
	 */
	public void setllowableInternalUser(boolean allowableInternalUser) {
		this.allowableInternalUser = allowableInternalUser;
	}

	/**
	 * Gets the wsp session.
	 *
	 * @return the wsp session
	 */
	public Wsp getWspSession() {
		return wspSession;
	}

	/**
	 * Sets the wsp session.
	 *
	 * @param wspSession
	 *            the new wsp session
	 */
	public void setWspSession(Wsp wspSession) {
		this.wspSession = wspSession;
	}

	/**
	 * Gets the sars files.
	 *
	 * @return the sars files
	 */
	public SarsFiles getSarsFiles() {
		return sarsFiles;
	}

	/**
	 * Sets the sars files.
	 *
	 * @param sarsFiles
	 *            the new sars files
	 */
	public void setSarsFiles(SarsFiles sarsFiles) {
		this.sarsFiles = sarsFiles;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public String getTsAndCsLink() {
		return HAJConstants.DOC_SERVER + "grants_policy.pdf";
	}

	public boolean isAcceptPopi() {
		return acceptPopi;
	}

	public void setAcceptPopi(boolean acceptPopi) {
		this.acceptPopi = acceptPopi;
	}

	public boolean isEmployee() {
		return employee;
	}

	public void setEmployee(boolean employee) {
		this.employee = employee;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isFinance() {
		return finance;
	}

	public void setFinance(boolean finance) {
		this.finance = finance;
	}

	public boolean isSdf() {
		return sdf;
	}

	public void setSdf(boolean sdf) {
		this.sdf = sdf;
	}

	public boolean isTrainingProvider() {
		return trainingProvider;
	}

	public void setTrainingProvider(boolean trainingProvider) {
		this.trainingProvider = trainingProvider;
	}

	public boolean isAssMod() {
		return assMod;
	}

	public void setAssMod(boolean assMod) {
		this.assMod = assMod;
	}

	public boolean isCheckLearner() {
		return !assMod && !sdf && !trainingProvider && !contact;
	}

	public boolean isLearner() {
		return learner;
	}

	public void setLearner(boolean learner) {
		this.learner = learner;
	}

	public String getWsUrl() {
		return wsUrl;
	}

	public void setWsUrl(String wsUrl) {
		this.wsUrl = wsUrl;
	}

	public String getExternalSessionId() {
		return externalSessionId;
	}

	public void setExternalSessionId(String externalSessionId) {
		this.externalSessionId = externalSessionId;
	}

	/**
	 * @return the hideETQA
	 */
	public boolean isHideETQA() {
		return hideETQA;
	}

	/**
	 * @param hideETQA
	 *            the hideETQA to set
	 */
	public void setHideETQA(boolean hideETQA) {
		this.hideETQA = hideETQA;
	}

	public boolean isNonSetaCompany() {
		return nonSetaCompany;
	}

	public void setNonSetaCompany(boolean nonSetaCompany) {
		this.nonSetaCompany = nonSetaCompany;
	}

	public String getDashboardReporting() {
		return dashboardReporting;
	}

	public void setDashboardReporting(String dashboardReporting) {
		this.dashboardReporting = dashboardReporting;
	}

	public Boolean getEmailSupport() {
		return emailSupport;
	}

	public void setEmailSupport(Boolean emailSupport) {
		this.emailSupport = emailSupport;
	}

	public boolean isContact() {
		return contact;
	}

	public void setContact(boolean contact) {
		this.contact = contact;
	}

	public String getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(String validationErrors) {
		this.validationErrors = validationErrors;
	}

	public boolean isMentorregistration() {
		return mentorregistration;
	}

	public void setMentorregistration(boolean mentorregistration) {
		this.mentorregistration = mentorregistration;
	}

}
