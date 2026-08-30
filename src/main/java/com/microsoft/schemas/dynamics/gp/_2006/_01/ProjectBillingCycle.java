
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectBillingCycle complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectBillingCycle"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBillingCycleKey" minOccurs="0"/&gt;
 *         &lt;element name="BillingFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectBillingCycle", propOrder = {
    "key",
    "billingFormat"
})
public class ProjectBillingCycle
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ProjectBillingCycleKey key;
    @XmlElement(name = "BillingFormat")
    protected String billingFormat;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBillingCycleKey }
     *     
     */
    public ProjectBillingCycleKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBillingCycleKey }
     *     
     */
    public void setKey(ProjectBillingCycleKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the billingFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingFormat() {
        return billingFormat;
    }

    /**
     * Sets the value of the billingFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingFormat(String value) {
        this.billingFormat = value;
    }

}
