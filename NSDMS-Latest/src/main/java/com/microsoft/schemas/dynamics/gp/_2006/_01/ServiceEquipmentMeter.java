
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceEquipmentMeter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEquipmentMeter"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CurrentReading" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Replaced" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceEquipmentMeter", propOrder = {
    "currentReading",
    "replaced"
})
@XmlSeeAlso({
    ServiceCallEquipmentMeter.class
})
public class ServiceEquipmentMeter
    extends BusinessObject
{

    @XmlElement(name = "CurrentReading", required = true, type = Integer.class, nillable = true)
    protected Integer currentReading;
    @XmlElement(name = "Replaced", required = true, type = Boolean.class, nillable = true)
    protected Boolean replaced;

    /**
     * Gets the value of the currentReading property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCurrentReading() {
        return currentReading;
    }

    /**
     * Sets the value of the currentReading property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCurrentReading(Integer value) {
        this.currentReading = value;
    }

    /**
     * Gets the value of the replaced property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReplaced() {
        return replaced;
    }

    /**
     * Sets the value of the replaced property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReplaced(Boolean value) {
        this.replaced = value;
    }

}
