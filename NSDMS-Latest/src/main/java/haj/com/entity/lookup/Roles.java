package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.annotations.PageAccessAnnotation;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * AbetBand.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "roles")
public class Roles extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "dashboard", length = 100)
	private String dashboard;
	
	@Column(name = "dashboard_reporting", length = 100)
	private String dashboardReporting;

	@PageAccessAnnotation(page = "/pages/intersetatransfer/transferrequests.jsf")
	@Column(name = "inter_seta_transfer", columnDefinition = "BIT default true")
	private Boolean interSetaTransfer;

	@PageAccessAnnotation(page = "/pages/mgverification.jsf")
	@Column(name = "mg_verification", columnDefinition = "BIT default true")
	private Boolean mgVerification;

	@PageAccessAnnotation(page = "/pages/dgverification.jsf")
	@Column(name = "dgVerification", columnDefinition = "BIT default true")
	private Boolean dgVerification;

	@PageAccessAnnotation(page = "/pages/dgallocation/dgallocation.jsf")
	@Column(name = "dg_allocation", columnDefinition = "BIT default true")
	private Boolean dgAllocation;

	@PageAccessAnnotation(page = "/pages/searchcompany.jsf")
	@Column(name = "companies", columnDefinition = "BIT default true")
	private Boolean companies;

	@PageAccessAnnotation(page = "/pages/sdf/users.jsf")
	@Column(name = "sdfs", columnDefinition = "BIT default true")
	private Boolean sdfs;

	@PageAccessAnnotation(page = "/pages/orgchart.jsf")
	@Column(name = "org_chart", columnDefinition = "BIT default true")
	private Boolean orgChart;

	@PageAccessAnnotation(page = "/pages/clo/sitevisit.jsf")
	@Column(name = "site_visit", columnDefinition = "BIT default false")
	private Boolean siteVisit;
	
	@PageAccessAnnotation(page = "/pages/clo/sitevisitreport.jsf")
	@Column(name = "site_visit_report", columnDefinition = "BIT default false")
	private Boolean siteVisitReport;

	@PageAccessAnnotation(page = "/pages/tp/coursewareDistibutionReport.jsf")
	@Column(name = "courseware_distro", columnDefinition = "BIT default false")
	private Boolean coursewareDistro;
	
	//@PageAccessAnnotation(page = "/pages/includes/coursewaredistreportinclude.xhtml")
	@Column(name = "courseware_distro_sub", columnDefinition = "BIT default false")
	private Boolean coursewareDistroSub;

	@PageAccessAnnotation(page = "/pages/tp/providermnitoring.jsf")
	@Column(name = "provider_monitoring", columnDefinition = "BIT default false")
	private Boolean providerMonitoring;
	
	@Column(name = "workplace_monitoring_access", columnDefinition = "BIT default false")
	private Boolean workplaceMonitoringAccess;
	
	@Column(name = "initiate_workplace_monitoring", columnDefinition = "BIT default false")
	private Boolean initiateWorkplaceMonitoring;
	
	@Column(name = "clo_crm_reporting", columnDefinition = "BIT default false")
	private Boolean cloCrmReporting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Roles other = (Roles) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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

	public Boolean getInterSetaTransfer() {
		return interSetaTransfer;
	}

	public void setInterSetaTransfer(Boolean interSetaTransfer) {
		this.interSetaTransfer = interSetaTransfer;
	}

	public Boolean getMgVerification() {
		return mgVerification;
	}

	public void setMgVerification(Boolean mgVerification) {
		this.mgVerification = mgVerification;
	}

	public Boolean getDgVerification() {
		return dgVerification;
	}

	public void setDgVerification(Boolean dgVerification) {
		this.dgVerification = dgVerification;
	}

	public Boolean getDgAllocation() {
		return dgAllocation;
	}

	public void setDgAllocation(Boolean dgAllocation) {
		this.dgAllocation = dgAllocation;
	}

	public Boolean getCompanies() {
		return companies;
	}

	public void setCompanies(Boolean companies) {
		this.companies = companies;
	}

	public Boolean getSdfs() {
		return sdfs;
	}

	public void setSdfs(Boolean sdfs) {
		this.sdfs = sdfs;
	}

	public Boolean getSiteVisit() {
		return siteVisit;
	}

	public void setSiteVisit(Boolean siteVisit) {
		this.siteVisit = siteVisit;
	}
	
	public Boolean getSiteVisitReport() {
		return siteVisit;
	}

	public void setSiteVisitReport(Boolean siteVisitReport) {
		this.siteVisitReport = siteVisitReport;
	}

	public Boolean getOrgChart() {
		return orgChart;
	}

	public void setOrgChart(Boolean orgChart) {
		this.orgChart = orgChart;
	}

	public Boolean getCoursewareDistro() {
		return coursewareDistro;
	}

	public void setCoursewareDistro(Boolean coursewareDistro) {
		this.coursewareDistro = coursewareDistro;
	}

	public Boolean getProviderMonitoring() {
		return providerMonitoring;
	}

	public void setProviderMonitoring(Boolean providerMonitoring) {
		this.providerMonitoring = providerMonitoring;
	}

	public String getDashboardReporting() {
		return dashboardReporting;
	}

	public void setDashboardReporting(String dashboardReporting) {
		this.dashboardReporting = dashboardReporting;
	}

	public Boolean getCoursewareDistroSub() {
		return coursewareDistroSub;
	}

	public void setCoursewareDistroSub(Boolean coursewareDistroSub) {
		this.coursewareDistroSub = coursewareDistroSub;
	}

	public Boolean getWorkplaceMonitoringAccess() {
		return workplaceMonitoringAccess;
	}

	public void setWorkplaceMonitoringAccess(Boolean workplaceMonitoringAccess) {
		this.workplaceMonitoringAccess = workplaceMonitoringAccess;
	}

	public Boolean getInitiateWorkplaceMonitoring() {
		return initiateWorkplaceMonitoring;
	}

	public void setInitiateWorkplaceMonitoring(Boolean initiateWorkplaceMonitoring) {
		this.initiateWorkplaceMonitoring = initiateWorkplaceMonitoring;
	}

	public Boolean getCloCrmReporting() {
		return cloCrmReporting;
	}

	public void setCloCrmReporting(Boolean cloCrmReporting) {
		this.cloCrmReporting = cloCrmReporting;
	}

}
