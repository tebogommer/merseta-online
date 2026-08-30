package haj.com.entity.lookup;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.entity.Users;
import haj.com.entity.enums.ReportPropertiesEnum;
import haj.com.framework.AbstractLookup;

@Entity
@Table(name = "report_generation_properties")
@AuditTable(value = "report_generation_properties_hist")
@Audited
public class ReportGenerationProperties extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "description", length = 500)
	private String description;
	
	@Enumerated
	@Column(name = "reportProperty")
	private ReportPropertiesEnum reportProperty;
	
	@Column(name = "generation_underway")
	private Boolean generationUnderway;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_generation_started", nullable = true)
	private Date dateGenerationStarted;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_started_generation_id", nullable = true)
	private Users userStartedGeneration;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_last_completed", nullable = true)
	private Date dateLastCompleted;

	/**
	 * Instantiates a new abet band.
	 */
	public ReportGenerationProperties() {
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
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ReportGenerationProperties other = (ReportGenerationProperties) obj;
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

	public Boolean getGenerationUnderway() {
		return generationUnderway;
	}

	public void setGenerationUnderway(Boolean generationUnderway) {
		this.generationUnderway = generationUnderway;
	}

	public Date getDateGenerationStarted() {
		return dateGenerationStarted;
	}

	public void setDateGenerationStarted(Date dateGenerationStarted) {
		this.dateGenerationStarted = dateGenerationStarted;
	}

	public Date getDateLastCompleted() {
		return dateLastCompleted;
	}

	public void setDateLastCompleted(Date dateLastCompleted) {
		this.dateLastCompleted = dateLastCompleted;
	}

	public ReportPropertiesEnum getReportProperty() {
		return reportProperty;
	}

	public void setReportProperty(ReportPropertiesEnum reportProperty) {
		this.reportProperty = reportProperty;
	}

	public Users getUserStartedGeneration() {
		return userStartedGeneration;
	}

	public void setUserStartedGeneration(Users userStartedGeneration) {
		this.userStartedGeneration = userStartedGeneration;
	}

}
