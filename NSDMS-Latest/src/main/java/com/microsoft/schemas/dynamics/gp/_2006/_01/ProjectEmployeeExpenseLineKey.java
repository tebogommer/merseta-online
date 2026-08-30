
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.Key;


/**
 * <p>Java class for ProjectEmployeeExpenseLineKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectEmployeeExpenseLineKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/2006/01}Key"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectEmployeeExpenseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectEmployeeExpenseKey" minOccurs="0"/&gt;
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
@XmlType(name = "ProjectEmployeeExpenseLineKey", propOrder = {
    "projectEmployeeExpenseKey",
    "sequenceNumber"
})
public class ProjectEmployeeExpenseLineKey
    extends Key
{

    @XmlElement(name = "ProjectEmployeeExpenseKey")
    protected ProjectEmployeeExpenseKey projectEmployeeExpenseKey;
    @XmlElement(name = "SequenceNumber")
    protected int sequenceNumber;

    /**
     * Gets the value of the projectEmployeeExpenseKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectEmployeeExpenseKey }
     *     
     */
    public ProjectEmployeeExpenseKey getProjectEmployeeExpenseKey() {
        return projectEmployeeExpenseKey;
    }

    /**
     * Sets the value of the projectEmployeeExpenseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectEmployeeExpenseKey }
     *     
     */
    public void setProjectEmployeeExpenseKey(ProjectEmployeeExpenseKey value) {
        this.projectEmployeeExpenseKey = value;
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
