
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetSkillListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSkill" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getSkillListResult"
})
@XmlRootElement(name = "GetSkillListResponse")
public class GetSkillListResponse {

    @XmlElement(name = "GetSkillListResult")
    protected ArrayOfSkill getSkillListResult;

    /**
     * Gets the value of the getSkillListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSkill }
     *     
     */
    public ArrayOfSkill getGetSkillListResult() {
        return getSkillListResult;
    }

    /**
     * Sets the value of the getSkillListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSkill }
     *     
     */
    public void setGetSkillListResult(ArrayOfSkill value) {
        this.getSkillListResult = value;
    }

}
