package haj.com.bean;

import java.io.Serializable;

import haj.com.entity.enums.ConfigDocProcessEnum;


public class MISReportBean implements Serializable {

	private String description;
	private String descriptionTwo;
	private String descriptionThree;
	private String descriptionFour;
	private Long count;
	private Long count2;
	private Boolean active;

	private Long id;
	private Double total;
	private ConfigDocProcessEnum configDocProcessEnum;


	public MISReportBean() {
		super();
	}


	public MISReportBean(String description, Long count, Long count2) {
		super();
		this.description = description;
		this.count = count;
		this.count2 = count2;
	}


	public MISReportBean(String description, Long id,  String descriptionTwo, java.math.BigDecimal total) {
		super();
		this.description = description;
		this.id = id;
		this.descriptionTwo = descriptionTwo;
		this.total = total==null?0.0:total.doubleValue();
	}


	public MISReportBean( Long id, String description,  java.math.BigDecimal total) {
		super();
		this.description = description;
		this.id = id;
		this.total = total==null?0.0:total.doubleValue();
	}

	public MISReportBean(String description, java.math.BigDecimal total) {
		super();
		this.description = description;
		this.total = total==null?0.0:total.doubleValue();
	}
	public MISReportBean(String description,  Long count) {
		super();
		this.description = description;
		this.count = count;
	}


	public MISReportBean(String description,  Long count, Double total) {
		super();
		this.description = description;
		this.count = count;
		this.total = total;
	}
	public MISReportBean(String description,  Long count,  Double total, Long count2) {
		super();
		this.description = description;
		this.count = count;
		this.total = total;
		this.count2 = count2;
	}

	public MISReportBean(String description, String descriptionTwo, Long count) {
		super();
		this.description = description;
		this.descriptionTwo = descriptionTwo;
		this.count = count;
	}

	public MISReportBean(String description, String descriptionTwo, String descriptionThree, String descriptionFour) {
		super();
		this.description = description;
		this.descriptionTwo = descriptionTwo;
		this.description = descriptionThree;
		this.descriptionTwo = descriptionFour;
	}



	public MISReportBean(String description, Boolean active, Long count) {
		super();
		this.description = description;
		this.active = active;
		this.count = count;
	}

	public MISReportBean(ConfigDocProcessEnum configDocProcessEnum,Long count) {
		super();
		this.count = count;
		this.configDocProcessEnum = configDocProcessEnum;
	}


	public MISReportBean( Boolean active,Long count) {
		super();
		this.active = active;
		this.count = count;
	}


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionTwo() {
		return descriptionTwo;
	}
	public void setDescriptionTwo(String descriptionTwo) {
		this.descriptionTwo = descriptionTwo;
	}



	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}



	public ConfigDocProcessEnum getConfigDocProcessEnum() {
		return configDocProcessEnum;
	}



	public void setConfigDocProcessEnum(ConfigDocProcessEnum configDocProcessEnum) {
		this.configDocProcessEnum = configDocProcessEnum;
	}


	public String getDescriptionThree() {
		return descriptionThree;
	}

	public void setDescriptionThree(String descriptionThree) {
		this.descriptionThree = descriptionThree;
	}

	public String getDescriptionFour() {
		return descriptionFour;
	}

	public void setDescriptionFour(String descriptionFour) {
		this.descriptionFour = descriptionFour;
	}
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MISReportBean other = (MISReportBean) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Long getCount2() {
		return count2;
	}

	public void setCount2(Long count2) {
		this.count2 = count2;
	}



}
