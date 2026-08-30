
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DynamicsOnlineConfigurationCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DynamicsOnlineConfigurationCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsCommerceServicesActive" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *         &lt;element name="IsPaymentServicesActive" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DynamicsOnlineConfigurationCriteria", propOrder = {
    "isCommerceServicesActive",
    "isPaymentServicesActive"
})
public class DynamicsOnlineConfigurationCriteria
    extends Criteria
{

    @XmlElement(name = "IsCommerceServicesActive")
    protected RestrictionOfNullableOfBoolean isCommerceServicesActive;
    @XmlElement(name = "IsPaymentServicesActive")
    protected RestrictionOfNullableOfBoolean isPaymentServicesActive;

    /**
     * Gets the value of the isCommerceServicesActive property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getIsCommerceServicesActive() {
        return isCommerceServicesActive;
    }

    /**
     * Sets the value of the isCommerceServicesActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setIsCommerceServicesActive(RestrictionOfNullableOfBoolean value) {
        this.isCommerceServicesActive = value;
    }

    /**
     * Gets the value of the isPaymentServicesActive property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getIsPaymentServicesActive() {
        return isPaymentServicesActive;
    }

    /**
     * Sets the value of the isPaymentServicesActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setIsPaymentServicesActive(RestrictionOfNullableOfBoolean value) {
        this.isPaymentServicesActive = value;
    }

}
