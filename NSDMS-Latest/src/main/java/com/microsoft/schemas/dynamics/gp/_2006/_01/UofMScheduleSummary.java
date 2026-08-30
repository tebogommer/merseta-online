
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for UofMScheduleSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UofMScheduleSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}UofMScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BaseUofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UofMDecimalPlacesQuantities" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}QuantityDecimalPlaces"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UofMScheduleSummary", propOrder = {
    "key",
    "description",
    "baseUofM",
    "uofMDecimalPlacesQuantities",
    "lastModifiedDate",
    "lastModifiedBy"
})
public class UofMScheduleSummary {

    @XmlElement(name = "Key")
    protected UofMScheduleKey key;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "BaseUofM")
    protected String baseUofM;
    @XmlElement(name = "UofMDecimalPlacesQuantities", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected QuantityDecimalPlaces uofMDecimalPlacesQuantities;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "LastModifiedBy")
    protected String lastModifiedBy;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link UofMScheduleKey }
     *     
     */
    public UofMScheduleKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link UofMScheduleKey }
     *     
     */
    public void setKey(UofMScheduleKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the baseUofM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseUofM() {
        return baseUofM;
    }

    /**
     * Sets the value of the baseUofM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseUofM(String value) {
        this.baseUofM = value;
    }

    /**
     * Gets the value of the uofMDecimalPlacesQuantities property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityDecimalPlaces }
     *     
     */
    public QuantityDecimalPlaces getUofMDecimalPlacesQuantities() {
        return uofMDecimalPlacesQuantities;
    }

    /**
     * Sets the value of the uofMDecimalPlacesQuantities property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityDecimalPlaces }
     *     
     */
    public void setUofMDecimalPlacesQuantities(QuantityDecimalPlaces value) {
        this.uofMDecimalPlacesQuantities = value;
    }

    /**
     * Gets the value of the lastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the value of the lastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModifiedDate(XMLGregorianCalendar value) {
        this.lastModifiedDate = value;
    }

    /**
     * Gets the value of the lastModifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Sets the value of the lastModifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastModifiedBy(String value) {
        this.lastModifiedBy = value;
    }

}
