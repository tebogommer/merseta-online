
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.Key;


/**
 * <p>Java class for ProjectTimesheetDistributionKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectTimesheetDistributionKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/2006/01}Key"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectTimesheetKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTimesheetKey" minOccurs="0"/&gt;
 *         &lt;element name="ControlType" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectTimesheetDistributionKey", propOrder = {
    "projectTimesheetKey",
    "controlType",
    "sequenceNumber"
})
public class ProjectTimesheetDistributionKey
    extends Key
{

    @XmlElement(name = "ProjectTimesheetKey")
    protected ProjectTimesheetKey projectTimesheetKey;
    @XmlElement(name = "ControlType")
    protected int controlType;
    @XmlElement(name = "SequenceNumber")
    protected int sequenceNumber;

    /**
     * Gets the value of the projectTimesheetKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectTimesheetKey }
     *     
     */
    public ProjectTimesheetKey getProjectTimesheetKey() {
        return projectTimesheetKey;
    }

    /**
     * Sets the value of the projectTimesheetKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectTimesheetKey }
     *     
     */
    public void setProjectTimesheetKey(ProjectTimesheetKey value) {
        this.projectTimesheetKey = value;
    }

    /**
     * Gets the value of the controlType property.
     * 
     */
    public int getControlType() {
        return controlType;
    }

    /**
     * Sets the value of the controlType property.
     * 
     */
    public void setControlType(int value) {
        this.controlType = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     */
    public void setSequenceNumber(int value) {
        this.sequenceNumber = value;
    }

}
