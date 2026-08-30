
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessObjectSummaryCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessObjectSummaryCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BusinessObjectKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="DisplayId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessObjectSummaryCriteria", propOrder = {
    "businessObjectKey",
    "displayId"
})
public class BusinessObjectSummaryCriteria
    extends Criteria
{

    @XmlElement(name = "BusinessObjectKey")
    protected RestrictionOfString businessObjectKey;
    @XmlElement(name = "DisplayId")
    protected LikeRestrictionOfString displayId;

    /**
     * Gets the value of the businessObjectKey property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfString }
     *     
     */
    public RestrictionOfString getBusinessObjectKey() {
        return businessObjectKey;
    }

    /**
     * Sets the value of the businessObjectKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfString }
     *     
     */
    public void setBusinessObjectKey(RestrictionOfString value) {
        this.businessObjectKey = value;
    }

    /**
     * Gets the value of the displayId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getDisplayId() {
        return displayId;
    }

    /**
     * Sets the value of the displayId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setDisplayId(LikeRestrictionOfString value) {
        this.displayId = value;
    }

}
