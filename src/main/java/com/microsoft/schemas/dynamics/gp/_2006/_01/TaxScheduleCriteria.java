
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxScheduleCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxScheduleCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxScheduleKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="TaxScheduleDescription" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxScheduleCriteria", propOrder = {
    "taxScheduleKeyId",
    "taxScheduleDescription"
})
public class TaxScheduleCriteria
    extends Criteria
{

    @XmlElement(name = "TaxScheduleKeyId")
    protected LikeRestrictionOfString taxScheduleKeyId;
    @XmlElement(name = "TaxScheduleDescription")
    protected LikeRestrictionOfString taxScheduleDescription;

    /**
     * Gets the value of the taxScheduleKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getTaxScheduleKeyId() {
        return taxScheduleKeyId;
    }

    /**
     * Sets the value of the taxScheduleKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setTaxScheduleKeyId(LikeRestrictionOfString value) {
        this.taxScheduleKeyId = value;
    }

    /**
     * Gets the value of the taxScheduleDescription property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getTaxScheduleDescription() {
        return taxScheduleDescription;
    }

    /**
     * Sets the value of the taxScheduleDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setTaxScheduleDescription(LikeRestrictionOfString value) {
        this.taxScheduleDescription = value;
    }

}
