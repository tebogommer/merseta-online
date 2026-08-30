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

import haj.com.annotations.CSVAnnotation;
import haj.com.entity.Province;
import haj.com.framework.AbstractLookup;

/**
 * PostCodeLink.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "post_code_link")
@AuditTable(value = "post_code_link_hist")
@Audited
public class PostCodeLink extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "description", length = 500)
	private String description;
	
	@CSVAnnotation(name = "post_code", className = String.class)
	@Column(name = "post_code")
	private String postCode;
	
	@CSVAnnotation(name = "area", className = String.class)
	@Column(name = "area")
	private String area;
	
	@CSVAnnotation(name = "province_code", className = String.class)
	@Column(name = "province_code")
	private String provinceCode;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "province_id", nullable = true)
	private Province province;
	
	@Column(name = "post_code_used_for_sars")
	private String postCodeUsedForSars;
	
	@Column(name = "post_code_used_for_sars_number_value")
	private Integer postCodeUsedForSarsNumberValue;
	
	public PostCodeLink() {
		super();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PostCodeLink other = (PostCodeLink) obj;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPostCodeUsedForSars() {
		return postCodeUsedForSars;
	}

	public void setPostCodeUsedForSars(String postCodeUsedForSars) {
		this.postCodeUsedForSars = postCodeUsedForSars;
	}
	
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public Integer getPostCodeUsedForSarsNumberValue() {
		return postCodeUsedForSarsNumberValue;
	}

	public void setPostCodeUsedForSarsNumberValue(Integer postCodeUsedForSarsNumberValue) {
		this.postCodeUsedForSarsNumberValue = postCodeUsedForSarsNumberValue;
	}

}
