package haj.com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class HAJProperties.
 */
@Entity
@Table(name = "haj_properties")
public class HAJProperties  implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

  
  /** The e property id. */
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="e_property_id")
  private Long ePropertyId;
  
  /** The e property. */
  @Column(name="e_property", length=100, nullable=false)
  private String eProperty;

  /** The e value. */
  @Column(name="e_value", length=100, nullable=true)
  private String eValue;
  
  
  /**
   * Instantiates a new HAJ properties.
   */
  public HAJProperties() {}


/**
 * Gets the e property id.
 *
 * @return the e property id
 */
public Long getePropertyId() {
	return ePropertyId;
}


/**
 * Sets the e property id.
 *
 * @param ePropertyId the new e property id
 */
public void setePropertyId(Long ePropertyId) {
	this.ePropertyId = ePropertyId;
}


/**
 * Gets the e property.
 *
 * @return the e property
 */
public String geteProperty() {
	if (eProperty==null) eProperty ="";
	else eProperty = eProperty.trim();
	return eProperty;
}


/**
 * Sets the e property.
 *
 * @param eProperty the new e property
 */
public void seteProperty(String eProperty) {
	this.eProperty = eProperty;
}


/**
 * Gets the e value.
 *
 * @return the e value
 */
public String geteValue() {
	if (eValue==null) eValue ="";
	else eValue = eValue.trim();
	return eValue;
}


/**
 * Sets the e value.
 *
 * @param eValue the new e value
 */
public void seteValue(String eValue) {
	this.eValue = eValue;
}

 
  
  
}
