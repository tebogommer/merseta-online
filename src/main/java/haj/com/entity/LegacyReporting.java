package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "legacy_reporting")
public class LegacyReporting implements IDataEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "query")
	private String query;

	@Column(name = "map_key")
	private String key;

	@Column(name = "run_as_native")
	private Boolean runAsNative;

	@Column(name = "single_result")
	private Boolean singleResult;

	@Column(name = "return_type")
	private String returnType;

	@Column(name = "for_class")
	private String forClass;

	@Transient
	private List<LegacyReportingParams> legacyReportingParams;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Boolean getRunAsNative() {
		return runAsNative;
	}

	public void setRunAsNative(Boolean runAsNative) {
		this.runAsNative = runAsNative;
	}

	public Boolean getSingleResult() {
		return singleResult;
	}

	public void setSingleResult(Boolean singleResult) {
		this.singleResult = singleResult;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
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
		LegacyReporting other = (LegacyReporting) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public String getForClass() {
		return forClass;
	}

	public void setForClass(String forClass) {
		this.forClass = forClass;
	}

	public List<LegacyReportingParams> getLegacyReportingParams() {
		return legacyReportingParams;
	}

	public void setLegacyReportingParams(List<LegacyReportingParams> legacyReportingParams) {
		this.legacyReportingParams = legacyReportingParams;
	}
}
