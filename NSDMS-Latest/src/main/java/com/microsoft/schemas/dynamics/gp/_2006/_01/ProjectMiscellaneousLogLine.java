
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectMiscellaneousLogLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectMiscellaneousLogLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectLineBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectMiscellaneousLogLineKey" minOccurs="0"/&gt;
 *         &lt;element name="Billing" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBillingFull" minOccurs="0"/&gt;
 *         &lt;element name="ProjectChangeOrderKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectMiscellaneousLogLine", propOrder = {
    "key",
    "billing",
    "projectChangeOrderKey"
})
public class ProjectMiscellaneousLogLine
    extends ProjectLineBase
{

    @XmlElement(name = "Key")
    protected ProjectMiscellaneousLogLineKey key;
    @XmlElement(name = "Billing")
    protected ProjectBillingFull billing;
    @XmlElement(name = "ProjectChangeOrderKey")
    protected ProjectChangeOrderKey projectChangeOrderKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectMiscellaneousLogLineKey }
     *     
     */
    public ProjectMiscellaneousLogLineKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectMiscellaneousLogLineKey }
     *     
     */
    public void setKey(ProjectMiscellaneousLogLineKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the billing property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBillingFull }
     *     
     */
    public ProjectBillingFull getBilling() {
        return billing;
    }

    /**
     * Sets the value of the billing property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBillingFull }
     *     
     */
    public void setBilling(ProjectBillingFull value) {
        this.billing = value;
    }

    /**
     * Gets the value of the projectChangeOrderKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderKey }
     *     
     */
    public ProjectChangeOrderKey getProjectChangeOrderKey() {
        return projectChangeOrderKey;
    }

    /**
     * Sets the value of the projectChangeOrderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderKey }
     *     
     */
    public void setProjectChangeOrderKey(ProjectChangeOrderKey value) {
        this.projectChangeOrderKey = value;
    }

}
