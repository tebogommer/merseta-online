
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SkillCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SkillCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SkillKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CompensationPeriod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfCompensationPeriod" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillCriteria", propOrder = {
    "skillKeyId",
    "compensationPeriod"
})
public class SkillCriteria
    extends Criteria
{

    @XmlElement(name = "SkillKeyId")
    protected LikeRestrictionOfString skillKeyId;
    @XmlElement(name = "CompensationPeriod")
    protected ListRestrictionOfNullableOfCompensationPeriod compensationPeriod;

    /**
     * Gets the value of the skillKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getSkillKeyId() {
        return skillKeyId;
    }

    /**
     * Sets the value of the skillKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setSkillKeyId(LikeRestrictionOfString value) {
        this.skillKeyId = value;
    }

    /**
     * Gets the value of the compensationPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfCompensationPeriod }
     *     
     */
    public ListRestrictionOfNullableOfCompensationPeriod getCompensationPeriod() {
        return compensationPeriod;
    }

    /**
     * Sets the value of the compensationPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfCompensationPeriod }
     *     
     */
    public void setCompensationPeriod(ListRestrictionOfNullableOfCompensationPeriod value) {
        this.compensationPeriod = value;
    }

}
