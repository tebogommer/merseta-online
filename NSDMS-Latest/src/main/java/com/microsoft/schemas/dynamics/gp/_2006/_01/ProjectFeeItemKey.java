
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.Key;


/**
 * <p>Java class for ProjectFeeItemKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectFeeItemKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/2006/01}Key"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectKey" minOccurs="0"/&gt;
 *         &lt;element name="LineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ProjectFeeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectFeeKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectFeeItemKey", propOrder = {
    "projectKey",
    "lineSequenceNumber",
    "projectFeeKey"
})
public class ProjectFeeItemKey
    extends Key
{

    @XmlElement(name = "ProjectKey")
    protected ProjectKey projectKey;
    @XmlElement(name = "LineSequenceNumber")
    protected int lineSequenceNumber;
    @XmlElement(name = "ProjectFeeKey")
    protected ProjectFeeKey projectFeeKey;

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
     * Gets the value of the lineSequenceNumber property.
     * 
     */
    public int getLineSequenceNumber() {
        return lineSequenceNumber;
    }

    /**
     * Sets the value of the lineSequenceNumber property.
     * 
     */
    public void setLineSequenceNumber(int value) {
        this.lineSequenceNumber = value;
    }

    /**
     * Gets the value of the projectFeeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectFeeKey }
     *     
     */
    public ProjectFeeKey getProjectFeeKey() {
        return projectFeeKey;
    }

    /**
     * Sets the value of the projectFeeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectFeeKey }
     *     
     */
    public void setProjectFeeKey(ProjectFeeKey value) {
        this.projectFeeKey = value;
    }

}
