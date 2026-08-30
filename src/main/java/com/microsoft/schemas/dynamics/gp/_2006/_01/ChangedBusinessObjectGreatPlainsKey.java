
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;


/**
 * <p>Java class for ChangedBusinessObjectGreatPlainsKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChangedBusinessObjectGreatPlainsKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ChangedBusinessObjectKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CompanyKey" type="{http://schemas.microsoft.com/dynamics/2006/01}CompanyKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangedBusinessObjectGreatPlainsKey", propOrder = {
    "companyKey"
})
@XmlSeeAlso({
    ChangedCurrencyKey.class,
    ChangedInternetAddressKey.class,
    ChangedUofMScheduleKey.class,
    ChangedPriceLevelKey.class,
    ChangedPricingKey.class,
    ChangedItemKey.class,
    ChangedSalespersonKey.class,
    ChangedCustomerAddressKey.class,
    ChangedCustomerKey.class,
    BaseChangedSalesDocument.class
})
public abstract class ChangedBusinessObjectGreatPlainsKey
    extends ChangedBusinessObjectKey
{

    @XmlElement(name = "CompanyKey")
    protected CompanyKey companyKey;

    /**
     * Gets the value of the companyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyKey }
     *     
     */
    public CompanyKey getCompanyKey() {
        return companyKey;
    }

    /**
     * Sets the value of the companyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyKey }
     *     
     */
    public void setCompanyKey(CompanyKey value) {
        this.companyKey = value;
    }

}
