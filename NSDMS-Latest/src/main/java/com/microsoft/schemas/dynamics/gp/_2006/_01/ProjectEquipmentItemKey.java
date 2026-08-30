
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.Key;


/**
 * <p>Java class for ProjectEquipmentItemKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectEquipmentItemKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/2006/01}Key"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectEquipmentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectEquipmentKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectEquipmentItemKey", propOrder = {
    "projectKey",
    "projectEquipmentKey"
})
public class ProjectEquipmentItemKey
    extends Key
{

    @XmlElement(name = "ProjectKey")
    protected ProjectKey projectKey;
    @XmlElement(name = "ProjectEquipmentKey")
    protected ProjectEquipmentKey projectEquipmentKey;

    /**
     * Gets the value of the projectKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectKey }
     *     
     */
    public ProjectKey getProjectKey() {
        return projectKey;
    }

    /**
     * Sets the value of the projectKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectKey }
     *     
     */
    public void setProjectKey(ProjectKey value) {
        this.projectKey = value;
    }

    /**
     * Gets the value of the projectEquipmentKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectEquipmentKey }
     *     
     */
    public ProjectEquipmentKey getProjectEquipmentKey() {
        return projectEquipmentKey;
    }

    /**
     * Sets the value of the projectEquipmentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectEquipmentKey }
     *     
     */
    public void setProjectEquipmentKey(ProjectEquipmentKey value) {
        this.projectEquipmentKey = value;
    }

}
