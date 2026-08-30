package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.enums.BugReportType;
import haj.com.framework.IDataEntity;


// TODO: Auto-generated Javadoc
/**
 * The Class BugReport.
 */
@Entity
@Table(name = "bug_report")
public class BugReport implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

    /** The create date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_date", length=19)
    private Date createDate;
    
    /** The user. */
    @ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="user_id", nullable=true)
    @Fetch(FetchMode.JOIN )
   	private Users user;
   
    /** The user. */
    @ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="company_users_id", nullable=true)
    @Fetch(FetchMode.JOIN )
    private CompanyUsers companyUsers;
    
    @Enumerated
    @Column(name="report_type")
    private BugReportType reportType;
    
    /** The bug title. */
    @Column(name = "bug_title", columnDefinition="LONGTEXT")
	private String bugTitle;
    
    @Column(name = "comment", columnDefinition="LONGTEXT")
	private String comment;
    
    /** The bug report. */
    @Column(name = "bug_report", columnDefinition="LONGTEXT")
	private String bugReport;
    
    
	/** The issue closed. */
	@Column(name = "issue_closed", columnDefinition = "BIT default false")
	private Boolean issueClosed;    
    
    /** The bug image. */
    @Transient
	private Images bugImage;

    
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		BugReport other = (BugReport) obj;
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
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 * @param user the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * Gets the bug title.
	 *
	 * @return the bug title
	 */
	public String getBugTitle() {
		return bugTitle;
	}

	/**
	 * Sets the bug title.
	 *
	 * @param bugTitle the new bug title
	 */
	public void setBugTitle(String bugTitle) {
		this.bugTitle = bugTitle;
	}

	/**
	 * Gets the bug report.
	 *
	 * @return the bug report
	 */
	public String getBugReport() {
		return bugReport;
	}

	/**
	 * Sets the bug report.
	 *
	 * @param bugReport the new bug report
	 */
	public void setBugReport(String bugReport) {
		this.bugReport = bugReport;
	}

	/**
	 * Gets the bug image.
	 *
	 * @return the bug image
	 */
	public Images getBugImage() {
		return bugImage;
	}

	/**
	 * Sets the bug image.
	 *
	 * @param bugImage the new bug image
	 */
	public void setBugImage(Images bugImage) {
		this.bugImage = bugImage;
	}

	/**
	 * Gets the issue closed.
	 *
	 * @return the issue closed
	 */
	public Boolean getIssueClosed() {
		return issueClosed;
	}

	/**
	 * Sets the issue closed.
	 *
	 * @param issueClosed the new issue closed
	 */
	public void setIssueClosed(Boolean issueClosed) {
		this.issueClosed = issueClosed;
	}

	public BugReportType getReportType() {
		return reportType;
	}

	public void setReportType(BugReportType reportType) {
		this.reportType = reportType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public CompanyUsers getCompanyUsers() {
		return companyUsers;
	}

	public void setCompanyUsers(CompanyUsers companyUsers) {
		this.companyUsers = companyUsers;
	}
}
