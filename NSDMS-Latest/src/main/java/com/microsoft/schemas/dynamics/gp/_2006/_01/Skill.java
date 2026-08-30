
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Skill complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Skill"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Compensation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Compensation" minOccurs="0"/&gt;
 *         &lt;element name="SkillKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SkillKey" minOccurs="0"/&gt;
 *         &lt;element name="DeleteOnUpdate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Skill", propOrder = {
    "compensation",
    "skillKey",
    "deleteOnUpdate"
})
public class Skill
    extends BusinessObject
{

    @XmlElement(name = "Compensation")
    protected Compensation compensation;
    @XmlElement(name = "SkillKey")
    protected SkillKey skillKey;
    @XmlElement(name = "DeleteOnUpdate", required = true, type = Boolean.class, nillable = true)
    protected Boolean deleteOnUpdate;

    /**
     * Gets the value of the compensation property.
     * 
     * @return
     *     possible object is
     *     {@link Compensation }
     *     
     */
    public Compensation getCompensation() {
        return compensation;
    }

    /**
     * Sets the value of the compensation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Compensation }
     *     
     */
    public void setCompensation(Compensation value) {
        this.compensation = value;
    }

    /**
     * Gets the value of the skillKey property.
     * 
     * @return
     *     possible object is
     *     {@link SkillKey }
     *     
     */
    public SkillKey getSkillKey() {
        return skillKey;
    }

    /**
     * Sets the value of the skillKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SkillKey }
     *     
     */
    public void setSkillKey(SkillKey value) {
        this.skillKey = value;
    }

    /**
     * Gets the value of the deleteOnUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeleteOnUpdate() {
        return deleteOnUpdate;
    }

    /**
     * Sets the value of the deleteOnUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeleteOnUpdate(Boolean value) {
        this.deleteOnUpdate = value;
    }

}
