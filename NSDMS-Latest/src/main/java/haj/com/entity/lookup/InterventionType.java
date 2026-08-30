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
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.QmrTypeSelectionEnum;
import haj.com.entity.enums.QualificationTypeSelectionEnum;
import haj.com.entity.enums.TrancheRuleEnum;
import haj.com.framework.AbstractLookup;

/**
 * The Class InterventionType.
 */
@Entity
@Table(name = "intervention_type")
@AuditTable(value = "intervention_type_hist")
@Audited
public class InterventionType extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The description. */
	@Column(name = "description", length = 100)
	private String description;
	
	@Column(name = "short_description", length = 10)
	private String shortDescription;

	/** The description. */
	@Column(name = "gov_description", length = 100)
	private String govDescription;

	@Column(name = "tranch_intervals")
	private Integer tranchintervals;

	/** The pivot non pivot. */
	@Enumerated
	@Column(name = "pivot_nonpivot")
	private PivotNonPivotEnum pivotNonPivot;
	
	@Enumerated
	@Column(name = "tranche_rule")
	private TrancheRuleEnum trancheRuleEnum;

	/** Description of AbetBand. */
	@Column(name = "basic_grant_amount")
	private Double basicGrantAmount;

	@Column(name = "duration")
	private Integer duration;

	@Column(name = "prioritisation_scale")
	private Integer prioritisationScale;

	/** Description of AbetBand. */
	@Column(name = "disability_grant_amount")
	private Double disabilityGrantAmount;

	/** Description of AbetBand. */
	@Column(name = "other_grant_amount")
	private Double otherGrantAmount;

	@Column(name = "form")
	private String form;

	@Enumerated
	@Column(name = "intervention_type_enum")
	private InterventionTypeEnum interventionTypeEnum;

	@Column(name = "workplace_approval_required")
	private Boolean workplaceApprovalRequired;

	@Column(name = "bursaries_documents_required")
	private Boolean bursariesDocumentsRequired;

	@Column(name = "extension_request")
	private Boolean extensionRequest;

	@Column(name = "request_verification_of_assessments")
	private Boolean requestVerificationOfAssesments;

	@Column(name = "dhet")
	private String dhet;

	@Column(name = "for_trade_test")
	private Boolean forTradeTest;

	@Column(name = "busary")
	private Boolean busary;

	@Column(name = "workplace_based_learning")
	private Boolean workplaceBasedLearning;
	
	@Column(name = "for_sdp_accreditation")
	private Boolean forSdpAccreditaion;
	
	@Column(name = "completion_letter")
	private Boolean completionLetter;
	
	@Column(name = "registration_by_non_merseta")
	private Boolean registrationByNonMerseta;

	@Column(name = "arpl")
	private Boolean arpl;
	
	@Column(name = "part_of_id_string")
	private String partOfIdString;
	
	@Column(name = "wsp_election", columnDefinition = "BIT default false")
	private Boolean wspSelection;
	
	@Column(name = "atr_selection", columnDefinition = "BIT default false")
	private Boolean atrSelection;
	
	@Column(name = "can_select", columnDefinition = "BIT default true")
	private Boolean canSelect;
	
	@Column(name = "institution", columnDefinition = "BIT default false")
	private Boolean institution;
	
	@Column(name = "onesidedtermination", columnDefinition = "BIT default false")
	private Boolean onesidedtermination;
	
	@Enumerated
	@Column(name = "qmr_type_selection")
	private QmrTypeSelectionEnum qmrTypeSelection;
	
	@Enumerated
	@Column(name = "qualification_type_selection")
	private QualificationTypeSelectionEnum qualificationTypeSelection;
	
	@Column(name = "qmr_tvet_report", columnDefinition = "BIT default false")
	private Boolean qmrTvetReport;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "aet_programme_level_id", nullable = true)
	private AetProgrammeLevel aetProgrammeLevel;
	
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
		InterventionType other = (InterventionType) obj;
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
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractLookup#getCreateDate()
	 */
	@Override
	public Date getCreateDate() {
		return createDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractLookup#setCreateDate(java.util.Date)
	 */
	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the pivot non pivot.
	 *
	 * @return the pivot non pivot
	 */
	public PivotNonPivotEnum getPivotNonPivot() {
		return pivotNonPivot;
	}

	/**
	 * Sets the pivot non pivot.
	 *
	 * @param pivotNonPivot
	 *            the new pivot non pivot
	 */
	public void setPivotNonPivot(PivotNonPivotEnum pivotNonPivot) {
		this.pivotNonPivot = pivotNonPivot;
	}

	public Double getBasicGrantAmount() {
		return basicGrantAmount;
	}

	public void setBasicGrantAmount(Double basicGrantAmount) {
		this.basicGrantAmount = basicGrantAmount;
	}

	public Double getDisabilityGrantAmount() {
		return disabilityGrantAmount;
	}

	public void setDisabilityGrantAmount(Double disabilityGrantAmount) {
		this.disabilityGrantAmount = disabilityGrantAmount;
	}

	public Double getOtherGrantAmount() {
		return otherGrantAmount;
	}

	public void setOtherGrantAmount(Double otherGrantAmount) {
		this.otherGrantAmount = otherGrantAmount;
	}

	public Integer getPrioritisationScale() {
		return prioritisationScale;
	}

	public void setPrioritisationScale(Integer prioritisationScale) {
		this.prioritisationScale = prioritisationScale;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public InterventionTypeEnum getInterventionTypeEnum() {
		return interventionTypeEnum;
	}

	public void setInterventionTypeEnum(InterventionTypeEnum interventionTypeEnum) {
		this.interventionTypeEnum = interventionTypeEnum;
	}

	public Boolean getWorkplaceApprovalRequired() {
		return workplaceApprovalRequired;
	}

	public void setWorkplaceApprovalRequired(Boolean workplaceApprovalRequired) {
		this.workplaceApprovalRequired = workplaceApprovalRequired;
	}

	/**
	 * @return the tranchintervals
	 */
	public Integer getTranchintervals() {
		return tranchintervals;
	}

	/**
	 * @param tranchintervals
	 *            the tranchintervals to set
	 */
	public void setTranchintervals(Integer tranchintervals) {
		this.tranchintervals = tranchintervals;
	}

	public Boolean getBursariesDocumentsRequired() {
		return bursariesDocumentsRequired;
	}

	public void setBursariesDocumentsRequired(Boolean bursariesDocumentsRequired) {
		this.bursariesDocumentsRequired = bursariesDocumentsRequired;
	}

	public Boolean getExtensionRequest() {
		return extensionRequest;
	}

	public void setExtensionRequest(Boolean extensionRequest) {
		this.extensionRequest = extensionRequest;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getGovDescription() {
		return govDescription;
	}

	public void setGovDescription(String govDescription) {
		this.govDescription = govDescription;
	}

	public Boolean getRequestVerificationOfAssesments() {
		return requestVerificationOfAssesments;
	}

	public void setRequestVerificationOfAssesments(Boolean requestVerificationOfAssesments) {
		this.requestVerificationOfAssesments = requestVerificationOfAssesments;
	}

	public String getDhet() {
		return dhet;
	}

	public void setDhet(String dhet) {
		this.dhet = dhet;
	}

	public Boolean getForTradeTest() {
		return forTradeTest;
	}

	public void setForTradeTest(Boolean forTradeTest) {
		this.forTradeTest = forTradeTest;
	}

	public Boolean getBusary() {
		return busary;
	}

	public void setBusary(Boolean busary) {
		this.busary = busary;
	}

	public Boolean getWorkplaceBasedLearning() {
		return workplaceBasedLearning;
	}

	public void setWorkplaceBasedLearning(Boolean workplaceBasedLearning) {
		this.workplaceBasedLearning = workplaceBasedLearning;
	}

	public Boolean getForSdpAccreditaion() {
		return forSdpAccreditaion;
	}

	public void setForSdpAccreditaion(Boolean forSdpAccreditaion) {
		this.forSdpAccreditaion = forSdpAccreditaion;
	}

	public Boolean getCompletionLetter() {
		return completionLetter;
	}

	public void setCompletionLetter(Boolean completionLetter) {
		this.completionLetter = completionLetter;
	}

	public Boolean getRegistrationByNonMerseta() {
		return registrationByNonMerseta;
	}

	public void setRegistrationByNonMerseta(Boolean registrationByNonMerseta) {
		this.registrationByNonMerseta = registrationByNonMerseta;
	}

	public Boolean getArpl() {
		return arpl;
	}

	public void setArpl(Boolean arpl) {
		this.arpl = arpl;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getPartOfIdString() {
		return partOfIdString;
	}

	public void setPartOfIdString(String partOfIdString) {
		this.partOfIdString = partOfIdString;
	}

	public Boolean getWspSelection() {
		return wspSelection;
	}

	public void setWspSelection(Boolean wspSelection) {
		this.wspSelection = wspSelection;
	}

	public Boolean getAtrSelection() {
		return atrSelection;
	}

	public void setAtrSelection(Boolean atrSelection) {
		this.atrSelection = atrSelection;
	}

	public Boolean getCanSelect() {
		return canSelect;
	}

	public void setCanSelect(Boolean canSelect) {
		this.canSelect = canSelect;
	}

	public QmrTypeSelectionEnum getQmrTypeSelection() {
		return qmrTypeSelection;
	}

	public void setQmrTypeSelection(QmrTypeSelectionEnum qmrTypeSelection) {
		this.qmrTypeSelection = qmrTypeSelection;
	}

	public QualificationTypeSelectionEnum getQualificationTypeSelection() {
		return qualificationTypeSelection;
	}

	public void setQualificationTypeSelection(QualificationTypeSelectionEnum qualificationTypeSelection) {
		this.qualificationTypeSelection = qualificationTypeSelection;
	}

	public Boolean getInstitution() {
		return institution;
	}

	public void setInstitution(Boolean institution) {
		this.institution = institution;
	}

	public AetProgrammeLevel getAetProgrammeLevel() {
		return aetProgrammeLevel;
	}

	public void setAetProgrammeLevel(AetProgrammeLevel aetProgrammeLevel) {
		this.aetProgrammeLevel = aetProgrammeLevel;
	}

	public Boolean getOnesidedtermination() {
		return onesidedtermination;
	}

	public void setOnesidedtermination(Boolean onesidedtermination) {
		this.onesidedtermination = onesidedtermination;
	}

	public TrancheRuleEnum getTrancheRuleEnum() {
		return trancheRuleEnum;
	}

	public void setTrancheRuleEnum(TrancheRuleEnum trancheRuleEnum) {
		this.trancheRuleEnum = trancheRuleEnum;
	}

	public Boolean getQmrTvetReport() {
		return qmrTvetReport;
	}

	public void setQmrTvetReport(Boolean qmrTvetReport) {
		this.qmrTvetReport = qmrTvetReport;
	}
}
