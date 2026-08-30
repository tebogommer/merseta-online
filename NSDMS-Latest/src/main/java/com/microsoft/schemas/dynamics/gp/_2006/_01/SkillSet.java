
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SkillSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SkillSet"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AvailableSkills" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="SkillSetKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SkillSetKey" minOccurs="0"/&gt;
 *         &lt;element name="Skills" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSkill" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillSet", propOrder = {
    "availableSkills",
    "skillSetKey",
    "skills"
})
public class SkillSet
    extends BusinessObject
{

    @XmlElement(name = "AvailableSkills", required = true, type = Integer.class, nillable = true)
    protected Integer availableSkills;
    @XmlElement(name = "SkillSetKey")
    protected SkillSetKey skillSetKey;
    @XmlElement(name = "Skills")
    protected ArrayOfSkill skills;

    /**
     * Gets the value of the availableSkills property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAvailableSkills() {
        return availableSkills;
    }

    /**
     * Sets the value of the availableSkills property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAvailableSkills(Integer value) {
        this.availableSkills = value;
    }

    /**
     * Gets the value of the skillSetKey property.
     * 
     * @return
     *     possible object is
     *     {@link SkillSetKey }
     *     
     */
    public SkillSetKey getSkillSetKey() {
        return skillSetKey;
    }

    /**
     * Sets the value of the skillSetKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SkillSetKey }
     *     
     */
    public void setSkillSetKey(SkillSetKey value) {
        this.skillSetKey = value;
    }

    /**
     * Gets the value of the skills property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSkill }
     *     
     */
    public ArrayOfSkill getSkills() {
        return skills;
    }

    /**
     * Sets the value of the skills property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSkill }
     *     
     */
    public void setSkills(ArrayOfSkill value) {
        this.skills = value;
    }

}
