package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * SdpType.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "sdp_type")
@AuditTable(value = "sdp_type_hist")
@Audited
public class SdpType extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of SdpType. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "description", length = 500)
	private String description;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id", nullable = true)
	private Designation designation;
	
	@Column(name = "view_sdp_information", columnDefinition = "BIT default false")
	private Boolean viewSdpInformation;
	
	@Column(name = "action_sdp_information", columnDefinition = "BIT default false")
	private Boolean actionSdpInformation;
	
	@Column(name = "view_learners", columnDefinition = "BIT default false")
	private Boolean viewLearners;
	
	@Column(name = "register_learners", columnDefinition = "BIT default false")
	private Boolean registerLearners;
	
	@Column(name = "action_learners", columnDefinition = "BIT default false")
	private Boolean actionLearners;
	
	@Column(name = "view_trade_test_centre_assessors", columnDefinition = "BIT default false")
	private Boolean viewTradeTestCentreAssessors;
	
	@Column(name = "action_trade_test_centre_assessors", columnDefinition = "BIT default false")
	private Boolean actionTradeTestCentreAssessors;
	
	@Column(name = "alter_site_info", columnDefinition = "BIT default false")
	private Boolean alterSiteInfo;
	
	@Column(name = "alter_sdps_linked", columnDefinition = "BIT default false")
	private Boolean alterSdpsLinked;
	
	@Column(name = "alter_ass_mod_linked", columnDefinition = "BIT default false")
	private Boolean alterAssModLinked;

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
		SdpType other = (SdpType) obj;
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
	 * @param id            the id to set
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
	 * @param description            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getRegisterLearners() {
		return registerLearners;
	}

	public void setRegisterLearners(Boolean registerLearners) {
		this.registerLearners = registerLearners;
	}

	public Boolean getViewSdpInformation() {
		return viewSdpInformation;
	}

	public void setViewSdpInformation(Boolean viewSdpInformation) {
		this.viewSdpInformation = viewSdpInformation;
	}

	public Boolean getActionSdpInformation() {
		return actionSdpInformation;
	}

	public void setActionSdpInformation(Boolean actionSdpInformation) {
		this.actionSdpInformation = actionSdpInformation;
	}

	public Boolean getViewLearners() {
		return viewLearners;
	}

	public void setViewLearners(Boolean viewLearners) {
		this.viewLearners = viewLearners;
	}

	public Boolean getActionLearners() {
		return actionLearners;
	}

	public void setActionLearners(Boolean actionLearners) {
		this.actionLearners = actionLearners;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public Boolean getViewTradeTestCentreAssessors() {
		return viewTradeTestCentreAssessors;
	}

	public void setViewTradeTestCentreAssessors(Boolean viewTradeTestCentreAssessors) {
		this.viewTradeTestCentreAssessors = viewTradeTestCentreAssessors;
	}

	public Boolean getActionTradeTestCentreAssessors() {
		return actionTradeTestCentreAssessors;
	}

	public void setActionTradeTestCentreAssessors(Boolean actionTradeTestCentreAssessors) {
		this.actionTradeTestCentreAssessors = actionTradeTestCentreAssessors;
	}

	public Boolean getAlterSiteInfo() {
		return alterSiteInfo;
	}

	public void setAlterSiteInfo(Boolean alterSiteInfo) {
		this.alterSiteInfo = alterSiteInfo;
	}

	public Boolean getAlterSdpsLinked() {
		return alterSdpsLinked;
	}

	public void setAlterSdpsLinked(Boolean alterSdpsLinked) {
		this.alterSdpsLinked = alterSdpsLinked;
	}

	public Boolean getAlterAssModLinked() {
		return alterAssModLinked;
	}

	public void setAlterAssModLinked(Boolean alterAssModLinked) {
		this.alterAssModLinked = alterAssModLinked;
	}
}
