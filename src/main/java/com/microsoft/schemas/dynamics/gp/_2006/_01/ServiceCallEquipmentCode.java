
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceCallEquipmentCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceCallEquipmentCode"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentCode"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PerformedPreventiveMaintenance" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceCallEquipmentCode", propOrder = {
    "performedPreventiveMaintenance"
})
public class ServiceCallEquipmentCode
    extends ServiceEquipmentCode
{

    @XmlElement(name = "PerformedPreventiveMaintenance", required = true, type = Boolean.class, nillable = true)
    protected Boolean performedPreventiveMaintenance;

    /**
     * Gets the value of the performedPreventiveMaintenance property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPerformedPreventiveMaintenance() {
        return performedPreventiveMaintenance;
    }

    /**
     * Sets the value of the performedPreventiveMaintenance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPerformedPreventiveMaintenance(Boolean value) {
        this.performedPreventiveMaintenance = value;
    }

}
