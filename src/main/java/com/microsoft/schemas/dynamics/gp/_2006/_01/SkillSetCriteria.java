
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SkillSetCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SkillSetCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SkillSetKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillSetCriteria", propOrder = {
    "skillSetKeyId"
})
public class SkillSetCriteria
    extends Criteria
{

    @XmlElement(name = "SkillSetKeyId")
    protected LikeRestrictionOfString skillSetKeyId;

    /**
     * Gets the value of the skillSetKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getSkillSetKeyId() {
        return skillSetKeyId;
    }

    /**
     * Sets the value of the skillSetKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setSkillSetKeyId(LikeRestrictionOfString value) {
        this.skillSetKeyId = value;
    }

}
