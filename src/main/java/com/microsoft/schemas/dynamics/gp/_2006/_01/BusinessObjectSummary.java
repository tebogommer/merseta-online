
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessObjectSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessObjectSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BusinessObjectKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DisplayId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessObjectSummary", propOrder = {
    "businessObjectKey",
    "displayId",
    "displayName"
})
@XmlSeeAlso({
    HRRequisitionSummary.class,
    SkillSetSummary.class,
    PlannedOrderSummary.class,
    ManufacturingOrderDocumentSummary.class
})
public class BusinessObjectSummary {

    @XmlElement(name = "BusinessObjectKey")
    protected String businessObjectKey;
    @XmlElement(name = "DisplayId")
    protected String displayId;
    @XmlElement(name = "DisplayName")
    protected String displayName;

    /**
     * Gets the value of the businessObjectKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessObjectKey() {
        return businessObjectKey;
    }

    /**
     * Sets the value of the businessObjectKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessObjectKey(String value) {
        this.businessObjectKey = value;
    }

    /**
     * Gets the value of the displayId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayId() {
        return displayId;
    }

    /**
     * Sets the value of the displayId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayId(String value) {
        this.displayId = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

}
