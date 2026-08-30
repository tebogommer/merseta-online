package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.framework.AbstractLookup;

/**
 * @author Techfinium
 */
@Entity
@Table(name = "designated_trade_level")
@AuditTable(value = "designated_trade_level_hist")
@Audited
public class DesignatedTradeLevel extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of bank. */
	@Column(name = "description", length = 500)
	private String description;
	
	@Column(name = "level", nullable = false)
	private Long level;

	@Column(name = "minweeks", nullable = false)
	private Long minweeks;

	@Column(name = "maxweeks", nullable = false)
	private Long maxweeks;
	
	@Column(name = "total_modules_assigned", nullable = false)
	private Integer totalModulesAssigned;
	
	@Column(name = "extension")
	private Boolean extension;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@OneToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "designated_trade_type_id", nullable = true)
	private DesignatedTradeType designatedTradeType;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@OneToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "designated_trade_id", nullable = true)
	private DesignatedTrade designatedTrade;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@OneToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;
	
	@Column(name = "max_attempts_amount", nullable = false)
	private Integer maxAttemptsAmount;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateLegacyAttempts", length = 19)
	private Date dateLegacyAttempts;
	
	@Column(name = "legacy_max_attempts_amount", nullable = true)
	private Integer legacyMaxAttemptsAmount;
	
	@Column(name = "no_order", columnDefinition = "BIT default false")
	private Boolean noOrder;

	@Column(name = "report_download_name", length = 500)
	private String reportDownloadName;
	
	@Column(name = "report_display_name", length = 500)
	private String reportDisplayName;
	
	@Column(name = "document_title", length = 500)
	private String documentTitle;
	
	@Column(name = "document_number", length = 500)
	private String documentNumber;
	
	@Transient
	private List<DesignatedTradeLevelItems> designatedTradeLevelItemsList;
	
	@Transient
	private Boolean canSelected;
	
	public DesignatedTradeLevel() {
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
		DesignatedTradeLevel other = (DesignatedTradeLevel) obj;
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

	public Long getMinweeks() {
		return minweeks;
	}

	public void setMinweeks(Long minweeks) {
		this.minweeks = minweeks;
	}

	public Long getMaxweeks() {
		return maxweeks;
	}

	public void setMaxweeks(Long maxweeks) {
		this.maxweeks = maxweeks;
	}

	public DesignatedTradeType getDesignatedTradeType() {
		return designatedTradeType;
	}

	public void setDesignatedTradeType(DesignatedTradeType designatedTradeType) {
		this.designatedTradeType = designatedTradeType;
	}

	public DesignatedTrade getDesignatedTrade() {
		return designatedTrade;
	}

	public void setDesignatedTrade(DesignatedTrade designatedTrade) {
		this.designatedTrade = designatedTrade;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Boolean getExtension() {
		return extension;
	}

	public void setExtension(Boolean extension) {
		this.extension = extension;
	}

	public Integer getMaxAttemptsAmount() {
		return maxAttemptsAmount;
	}

	public void setMaxAttemptsAmount(Integer maxAttemptsAmount) {
		this.maxAttemptsAmount = maxAttemptsAmount;
	}

	public Integer getLegacyMaxAttemptsAmount() {
		return legacyMaxAttemptsAmount;
	}

	public void setLegacyMaxAttemptsAmount(Integer legacyMaxAttemptsAmount) {
		this.legacyMaxAttemptsAmount = legacyMaxAttemptsAmount;
	}

	public Date getDateLegacyAttempts() {
		return dateLegacyAttempts;
	}

	public void setDateLegacyAttempts(Date dateLegacyAttempts) {
		this.dateLegacyAttempts = dateLegacyAttempts;
	}

	public Boolean getNoOrder() {
		return noOrder;
	}

	public void setNoOrder(Boolean noOrder) {
		this.noOrder = noOrder;
	}

	public String getReportDownloadName() {
		return reportDownloadName;
	}

	public void setReportDownloadName(String reportDownloadName) {
		this.reportDownloadName = reportDownloadName;
	}

	public String getReportDisplayName() {
		return reportDisplayName;
	}

	public void setReportDisplayName(String reportDisplayName) {
		this.reportDisplayName = reportDisplayName;
	}

	public List<DesignatedTradeLevelItems> getDesignatedTradeLevelItemsList() {
		return designatedTradeLevelItemsList;
	}

	public void setDesignatedTradeLevelItemsList(List<DesignatedTradeLevelItems> designatedTradeLevelItemsList) {
		this.designatedTradeLevelItemsList = designatedTradeLevelItemsList;
	}

	public Boolean getCanSelected() {
		return canSelected;
	}

	public void setCanSelected(Boolean canSelected) {
		this.canSelected = canSelected;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Integer getTotalModulesAssigned() {
		return totalModulesAssigned;
	}

	public void setTotalModulesAssigned(Integer totalModulesAssigned) {
		this.totalModulesAssigned = totalModulesAssigned;
	}

}
