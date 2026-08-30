
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.Key;


/**
 * <p>Java class for ProjectChangeOrderBudgetKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectChangeOrderBudgetKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/2006/01}Key"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectChangeOrderKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderKey" minOccurs="0"/&gt;
 *         &lt;element name="LineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectChangeOrderBudgetKey", propOrder = {
    "projectChangeOrderKey",
    "lineSequenceNumber"
})
public class ProjectChangeOrderBudgetKey
    extends Key
{

    @XmlElement(name = "ProjectChangeOrderKey")
    protected ProjectChangeOrderKey projectChangeOrderKey;
    @XmlElement(name = "LineSequenceNumber")
    protected int lineSequenceNumber;

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

}
