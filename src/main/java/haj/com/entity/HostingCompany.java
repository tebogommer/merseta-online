package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.constants.HAJConstants;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompany.
 */
@Entity
@Table(name = "hosting_company")
public class HostingCompany implements IDataEntity {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The id.s
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/**
	 * The company name.
	 * This field is required
	 * Field may not start with a space
	 * Field length 70
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._,`'-
	 * Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA 
	 */
	@NotEmpty(message="Enter company name")
	@Size(min=1, max=70, message="Company name can't be more than 70 characters")
	@Column(name = "company_name", length = 70, nullable = false)
	private String companyName;

	/**
	 * The company reg number.
	 * This filed is required
	 * Field may not start with a space
	 * Field length 20
	 * Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\:._,`'- 
	 */
	@NotEmpty(message="Enter company registration number")
	@Size(min=1, max=20, message="Company registration number can't be more than 20 characters" )
	@Column(name = "company_reg_number", length = 20, nullable = false)
	private String companyRegNumber;

	/**
	 * The vat number.
	 */
	@Column(name = "vat_number", length = 100, nullable = true)
	private String vatNumber;

	/**
	 * The income tax number.
	 */
	@Column(name = "income_tax_number", length = 100, nullable = true)
	private String incomeTaxNumber;

	/**
	 * The theme.
	 */
	@Column(name = "theme", length = 100, nullable = true)
	private String theme;

	/**
	 * The title.
	 */
	@Column(name = "title", length = 200, nullable = true)
	private String title;

	/**
	 * The logo.
	 */
	@Column(name = "logo", length = 200, nullable = true)
	private String logo;

	/**
	 * The create date.
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/**
	 * The ar take on.
	 */
	@Column(name = "ar_take_on")
	private Boolean arTakeOn;

	/** The host company profile image. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "profile_image_id", nullable = true)
	private Images profileImage;

	/**
	 * Instantiates a new hosting company.
	 */
	public HostingCompany() {
		super();
	}

	/**
	 * Instantiates a new hosting company.
	 *
	 * @param id
	 *            the id
	 */
	public HostingCompany(Long id) {
		super();
		this.id = id;
	}

	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the company name.
	 *
	 * @param companyName
	 *            the new company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * Gets the company reg number.
	 *
	 * @return the company reg number
	 */
	public String getCompanyRegNumber() {
		return companyRegNumber;
	}

	/**
	 * Sets the company reg number.
	 *
	 * @param companyRegNumber
	 *            the new company reg number
	 */
	public void setCompanyRegNumber(String companyRegNumber) {
		this.companyRegNumber = companyRegNumber;
	}

	/**
	 * Gets the vat number.
	 *
	 * @return the vat number
	 */
	public String getVatNumber() {
		return vatNumber;
	}

	/**
	 * Sets the vat number.
	 *
	 * @param vatNumber
	 *            the new vat number
	 */
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	/**
	 * Gets the income tax number.
	 *
	 * @return the income tax number
	 */
	public String getIncomeTaxNumber() {
		return incomeTaxNumber;
	}

	/**
	 * Sets the income tax number.
	 *
	 * @param incomeTaxNumber
	 *            the new income tax number
	 */
	public void setIncomeTaxNumber(String incomeTaxNumber) {
		this.incomeTaxNumber = incomeTaxNumber;
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
	 * @param createDate
	 *            the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	/**
	 * Gets the theme.
	 *
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * Sets the theme.
	 *
	 * @param theme
	 *            the new theme
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the logo.
	 *
	 * @return the logo
	 */
	public String getLogo() {
		if (logo != null)
			logo = logo.trim();
		return logo;
	}

	/**
	 * Sets the logo.
	 *
	 * @param logo
	 *            the new logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
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
		HostingCompany other = (HostingCompany) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Gets the ar take on.
	 *
	 * @return the ar take on
	 */
	public Boolean getArTakeOn() {
		return arTakeOn;
	}

	/**
	 * Sets the ar take on.
	 *
	 * @param arTakeOn
	 *            the new ar take on
	 */
	public void setArTakeOn(Boolean arTakeOn) {
		this.arTakeOn = arTakeOn;
	}

	/**
	 * Gets the profile image.
	 *
	 * @return the profile image
	 */
	public Images getProfileImage() {
		return profileImage;
	}

	/**
	 * Sets the profile image.
	 *
	 * @param profileImage the new profile image
	 */
	public void setProfileImage(Images profileImage) {
		this.profileImage = profileImage;
	}

	
	/**
	 * Gets the profile image for view.
	 *
	 * @return the profile image for view
	 */
	public String getProfileImageForView() {
		if (this.profileImage != null)
		return HAJConstants.DOC_SERVER+ (this.profileImage.getNewFname());
		else return null;
	}
	
	/**
	 * Gets the profile image small.
	 *
	 * @return the profile image small
	 */
	public String getProfileImageSmall() {
		if (this.getProfileImage()!=null && this.getProfileImage().getSmallFileName()!=null) {
		  try{	return HAJConstants.DOC_SERVER+this.getProfileImage().getSmallFileName(); } catch(Exception e) {}
		}
		return null;
	}
}
