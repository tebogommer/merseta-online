
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VendorPlannedOrderCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VendorPlannedOrderCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}PlannedOrderCriteriaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorPlannedOrderScope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VendorPlannedOrderCriteria", propOrder = {
    "scope"
})
public class VendorPlannedOrderCriteria
    extends PlannedOrderCriteriaBase
{

    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected VendorPlannedOrderScope scope;

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link VendorPlannedOrderScope }
     *     
     */
    public VendorPlannedOrderScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorPlannedOrderScope }
     *     
     */
    public void setScope(VendorPlannedOrderScope value) {
        this.scope = value;
    }

}
