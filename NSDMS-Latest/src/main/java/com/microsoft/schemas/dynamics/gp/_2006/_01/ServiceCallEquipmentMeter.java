
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceCallEquipmentMeter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceCallEquipmentMeter"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentMeter"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InternalUsage" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceCallEquipmentMeter", propOrder = {
    "internalUsage"
})
public class ServiceCallEquipmentMeter
    extends ServiceEquipmentMeter
{

    @XmlElement(name = "InternalUsage", required = true, type = Integer.class, nillable = true)
    protected Integer internalUsage;

    /**
     * Gets the value of the internalUsage property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInternalUsage() {
        return internalUsage;
    }

    /**
     * Sets the value of the internalUsage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInternalUsage(Integer value) {
        this.internalUsage = value;
    }

}
