
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Pricing complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Pricing"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PricingKey" minOccurs="0"/&gt;
 *         &lt;element name="Round" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RoundingType" minOccurs="0"/&gt;
 *         &lt;element name="UofMSalesOption" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}UofMSalesAvailable"/&gt;
 *         &lt;element name="Details" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPricingDetail" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pricing", propOrder = {
    "key",
    "round",
    "uofMSalesOption",
    "details",
    "lastModifiedDate",
    "lastModifiedBy"
})
public class Pricing
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected PricingKey key;
    @XmlElement(name = "Round")
    protected RoundingType round;
    @XmlElement(name = "UofMSalesOption", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected UofMSalesAvailable uofMSalesOption;
    @XmlElement(name = "Details")
    protected ArrayOfPricingDetail details;
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
     *     {@link PricingKey }
     *     
     */
    public PricingKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PricingKey }
     *     
     */
    public void setKey(PricingKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the round property.
     * 
     * @return
     *     possible object is
     *     {@link RoundingType }
     *     
     */
    public RoundingType getRound() {
        return round;
    }

    /**
     * Sets the value of the round property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoundingType }
     *     
     */
    public void setRound(RoundingType value) {
        this.round = value;
    }

    /**
     * Gets the value of the uofMSalesOption property.
     * 
     * @return
     *     possible object is
     *     {@link UofMSalesAvailable }
     *     
     */
    public UofMSalesAvailable getUofMSalesOption() {
        return uofMSalesOption;
    }

    /**
     * Sets the value of the uofMSalesOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link UofMSalesAvailable }
     *     
     */
    public void setUofMSalesOption(UofMSalesAvailable value) {
        this.uofMSalesOption = value;
    }

    /**
     * Gets the value of the details property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPricingDetail }
     *     
     */
    public ArrayOfPricingDetail getDetails() {
        return details;
    }

    /**
     * Sets the value of the details property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPricingDetail }
     *     
     */
    public void setDetails(ArrayOfPricingDetail value) {
        this.details = value;
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
