package haj.com.entity;

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

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "legacy_reporting_params")
public class LegacyReportingParams implements IDataEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "param_name")
	private String paramName;

	@Column(name = "field_name")
	private String fieldName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legacy_reporting_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private LegacyReporting legacyReporting;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public LegacyReporting getLegacyReporting() {
		return legacyReporting;
	}

	public void setLegacyReporting(LegacyReporting legacyReporting) {
		this.legacyReporting = legacyReporting;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
}
