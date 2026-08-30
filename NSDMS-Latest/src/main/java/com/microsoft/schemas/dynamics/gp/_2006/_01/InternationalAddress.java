
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InternationalAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InternationalAddress"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Address"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CountryRegionCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CountryRegionCodeKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InternationalAddress", propOrder = {
    "countryRegionCodeKey"
})
@XmlSeeAlso({
    EmployeeAddress.class,
    EmployeeAddressSummary.class,
    BusinessAddress.class
})
public class InternationalAddress
    extends Address
{

    @XmlElement(name = "CountryRegionCodeKey")
    protected CountryRegionCodeKey countryRegionCodeKey;

    /**
     * Gets the value of the countryRegionCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link CountryRegionCodeKey }
     *     
     */
    public CountryRegionCodeKey getCountryRegionCodeKey() {
        return countryRegionCodeKey;
    }

    /**
     * Sets the value of the countryRegionCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryRegionCodeKey }
     *     
     */
    public void setCountryRegionCodeKey(CountryRegionCodeKey value) {
        this.countryRegionCodeKey = value;
    }

}
