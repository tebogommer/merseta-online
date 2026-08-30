
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectBillingCycleKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectBillingCycleKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectKey" minOccurs="0"/&gt;
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectBillingCycleKey", propOrder = {
    "projectKey",
    "id"
})
public class ProjectBillingCycleKey
    extends ReferenceKey
{

    @XmlElement(name = "ProjectKey")
    protected ProjectKey projectKey;
    @XmlElement(name = "Id")
    protected String id;

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
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
