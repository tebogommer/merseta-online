package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.SiteVisitReportStatusEnum;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "site_visit_report_new")
public class SiteVisitReport implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static long serialVersionUID = 1L;

	/** Unique Id of SiteVisitReport. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private Users users;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "site_visit_report_date", length = 19)
	private Date visitDate;

	@Enumerated()
	@Column(name = "site_visit_report_status")
	private SiteVisitReportStatusEnum siteVisitReportStatusEnum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_approved", length = 19)
	private Date dateApproved;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "revisit_date", length = 19)
	private Date revisitDate;
	
	@Transient
	@Column(name="sites_list")
	private String sitesList;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site", nullable = true)
	private Sites site;
	
	// Equipment and tools
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_yes_no_one", nullable = true)
	private YesNoLookup yesNoEquipmentOne;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "equipment_one_revisit_date", length = 19)
	private Date equipmentOneRevisitDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_yes_no_two", nullable = true)
	private YesNoLookup yesNoEquipmentTwo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "equipment_two_revisit_date", length = 19)
	private Date equipmentTwoRevisitDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_yes_no_three", nullable = true)
	private YesNoLookup yesNoEquipmentThree;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "equipment_three_revisit_date", length = 19)
	private Date equipmentThreeRevisitDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_yes_no_four", nullable = true)
	private YesNoLookup yesNoEquipmentFour;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "equipment_four_revisit_date", length = 19)
	private Date equipmentFourRevisitDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_yes_no_five", nullable = true)
	private YesNoLookup yesNoEquipmentFive;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "equipment_five_revisit_date", length = 19)
	private Date equipmentFiveRevisitDate;
	
	// Record keeping and Mentoring
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "record_yes_no_one", nullable = true)
	private YesNoLookup yesNoRecordOne;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "record_one_revisit_date", length = 19)
	private Date recordOneRevisitDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "record_yes_no_two", nullable = true)
	private YesNoLookup yesNoRecordTwo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "record_two_revisit_date", length = 19)
	private Date recordTwoRevisitDate;
	
	// Safety
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "safety_yes_no_one", nullable = true)
	private YesNoLookup yesNoSafetyOne;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "safety_one_revisit_date", length = 19)
	private Date safetyOneRevisitDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "safety_yes_no_two", nullable = true)
	private YesNoLookup yesNoSafetyTwo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "safety_two_revisit_date", length = 19)
	private Date safetyTwoRevisitDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "safety_yes_no_three", nullable = true)
	private YesNoLookup yesNoSafetyThree;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "safety_three_revisit_date", length = 19)
	private Date safetyThreeRevisitDate;
	
	@Column(name = "comments", columnDefinition="LONGTEXT")
	private String comments;
	
	@Column(name = "gps_details", columnDefinition = "LONGTEXT")
	private String gpsDetails;
	
	@Transient
	private List<SiteVisitReportDispute> siteVisitReportDisputes;
	
	@Transient
	private List<Doc> docs;
	
	@Transient
	private List<SiteVisitReportSME> siteVisitReportSMEs;
	
	@Transient
	private List<Signoff> signOffs;

	public SiteVisitReport() {
		super();
	}

	public SiteVisitReport(Company company) {
		super();
	}

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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SiteVisitReport other = (SiteVisitReport) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
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
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	
	public Date getDateApproved() {
		return dateApproved;
	}

	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Date getRevisitDate() {
		return revisitDate;
	}

	public void setRevisitDate(Date revisitDate) {
		this.revisitDate = revisitDate;
	}	
	
	public Date getSafetyThreeRevisitDate() {
		return safetyThreeRevisitDate;
	}

	public void setSafetyThreeRevisitDate(Date safetyThreeRevisitDate) {
		this.safetyThreeRevisitDate = safetyThreeRevisitDate;
	}	

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSitesList() {
		return sitesList;
	}

	public void setSitesList(String sitesList) {
		this.sitesList = sitesList;
	}

	public Sites getSite() {
		return site;
	}

	public void setSite(Sites site) {
		this.site = site;
	}
	
	public SiteVisitReportStatusEnum getSiteVisitReportStatusEnum() {
		return siteVisitReportStatusEnum;
	}

	public void setSiteVisitReportStatusEnum(SiteVisitReportStatusEnum siteVisitReportStatusEnum) {
		this.siteVisitReportStatusEnum = siteVisitReportStatusEnum;
	}

	public List<Signoff> getSignOffs() {
		return signOffs;
	}

	public void setSignOffs(List<Signoff> signOffs) {
		this.signOffs = signOffs;
	}
	
	public String getGpsDetails() {
		return gpsDetails;
	}

	public void setGpsDetails(String gpsDetails) {
		this.gpsDetails = gpsDetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	public YesNoLookup getYesNoEquipmentOne() {
		return yesNoEquipmentOne;
	}

	public void setYesNoEquipmentOne(YesNoLookup yesNoEquipmentOne) {
		this.yesNoEquipmentOne = yesNoEquipmentOne;
	}

	public Date getEquipmentOneRevisitDate() {
		return equipmentOneRevisitDate;
	}

	public void setEquipmentOneRevisitDate(Date equipmentOneRevisitDate) {
		this.equipmentOneRevisitDate = equipmentOneRevisitDate;
	}

	public YesNoLookup getYesNoEquipmentTwo() {
		return yesNoEquipmentTwo;
	}

	public void setYesNoEquipmentTwo(YesNoLookup yesNoEquipmentTwo) {
		this.yesNoEquipmentTwo = yesNoEquipmentTwo;
	}

	public Date getEquipmentTwoRevisitDate() {
		return equipmentTwoRevisitDate;
	}

	public void setEquipmentTwoRevisitDate(Date equipmentTwoRevisitDate) {
		this.equipmentTwoRevisitDate = equipmentTwoRevisitDate;
	}

	public YesNoLookup getYesNoEquipmentThree() {
		return yesNoEquipmentThree;
	}

	public void setYesNoEquipmentThree(YesNoLookup yesNoEquipmentThree) {
		this.yesNoEquipmentThree = yesNoEquipmentThree;
	}

	public Date getEquipmentThreeRevisitDate() {
		return equipmentThreeRevisitDate;
	}

	public void setEquipmentThreeRevisitDate(Date equipmentThreeRevisitDate) {
		this.equipmentThreeRevisitDate = equipmentThreeRevisitDate;
	}

	public YesNoLookup getYesNoEquipmentFour() {
		return yesNoEquipmentFour;
	}

	public void setYesNoEquipmentFour(YesNoLookup yesNoEquipmentFour) {
		this.yesNoEquipmentFour = yesNoEquipmentFour;
	}

	public Date getEquipmentFourRevisitDate() {
		return equipmentFourRevisitDate;
	}

	public void setEquipmentFourRevisitDate(Date equipmentFourRevisitDate) {
		this.equipmentFourRevisitDate = equipmentFourRevisitDate;
	}

	public YesNoLookup getYesNoEquipmentFive() {
		return yesNoEquipmentFive;
	}

	public void setYesNoEquipmentFive(YesNoLookup yesNoEquipmentFive) {
		this.yesNoEquipmentFive = yesNoEquipmentFive;
	}

	public Date getEquipmentFiveRevisitDate() {
		return equipmentFiveRevisitDate;
	}

	public void setEquipmentFiveRevisitDate(Date equipmentFiveRevisitDate) {
		this.equipmentFiveRevisitDate = equipmentFiveRevisitDate;
	}

	public YesNoLookup getYesNoRecordOne() {
		return yesNoRecordOne;
	}

	public void setYesNoRecordOne(YesNoLookup yesNoRecordOne) {
		this.yesNoRecordOne = yesNoRecordOne;
	}

	public Date getRecordOneRevisitDate() {
		return recordOneRevisitDate;
	}

	public void setRecordOneRevisitDate(Date recordOneRevisitDate) {
		this.recordOneRevisitDate = recordOneRevisitDate;
	}

	public YesNoLookup getYesNoRecordTwo() {
		return yesNoRecordTwo;
	}

	public void setYesNoRecordTwo(YesNoLookup yesNoRecordTwo) {
		this.yesNoRecordTwo = yesNoRecordTwo;
	}

	public Date getRecordTwoRevisitDate() {
		return recordTwoRevisitDate;
	}

	public void setRecordTwoRevisitDate(Date recordTwoRevisitDate) {
		this.recordTwoRevisitDate = recordTwoRevisitDate;
	}

	public YesNoLookup getYesNoSafetyOne() {
		return yesNoSafetyOne;
	}

	public void setYesNoSafetyOne(YesNoLookup yesNoSafetyOne) {
		this.yesNoSafetyOne = yesNoSafetyOne;
	}

	public Date getSafetyOneRevisitDate() {
		return safetyOneRevisitDate;
	}

	public void setSafetyOneRevisitDate(Date safetyOneRevisitDate) {
		this.safetyOneRevisitDate = safetyOneRevisitDate;
	}

	public YesNoLookup getYesNoSafetyTwo() {
		return yesNoSafetyTwo;
	}

	public void setYesNoSafetyTwo(YesNoLookup yesNoSafetyTwo) {
		this.yesNoSafetyTwo = yesNoSafetyTwo;
	}

	public Date getSafetyTwoRevisitDate() {
		return safetyTwoRevisitDate;
	}

	public void setSafetyTwoRevisitDate(Date safetyTwoRevisitDate) {
		this.safetyTwoRevisitDate = safetyTwoRevisitDate;
	}

	public YesNoLookup getYesNoSafetyThree() {
		return yesNoSafetyThree;
	}

	public void setYesNoSafetyThree(YesNoLookup yesNoSafetyThree) {
		this.yesNoSafetyThree = yesNoSafetyThree;
	}

	public List<SiteVisitReportSME> getSiteVisitReportSMEs() {
		return siteVisitReportSMEs;
	}

	public void setSiteVisitReportSMEs(List<SiteVisitReportSME> siteVisitReportSMEs) {
		this.siteVisitReportSMEs = siteVisitReportSMEs;
	}

	public List<SiteVisitReportDispute> getSiteVisitReportDisputes() {
		return siteVisitReportDisputes;
	}

	public void setSiteVisitReportDisputes(List<SiteVisitReportDispute> siteVisitReportDisputes) {
		this.siteVisitReportDisputes = siteVisitReportDisputes;
	}
}
