
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetCountryRegionCodeByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CountryRegionCode" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getCountryRegionCodeByKeyResult"
})
@XmlRootElement(name = "GetCountryRegionCodeByKeyResponse")
public class GetCountryRegionCodeByKeyResponse {

    @XmlElement(name = "GetCountryRegionCodeByKeyResult")
    protected CountryRegionCode getCountryRegionCodeByKeyResult;

    /**
     * Gets the value of the getCountryRegionCodeByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link CountryRegionCode }
     *     
     */
    public CountryRegionCode getGetCountryRegionCodeByKeyResult() {
        return getCountryRegionCodeByKeyResult;
    }

    /**
     * Sets the value of the getCountryRegionCodeByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryRegionCode }
     *     
     */
    public void setGetCountryRegionCodeByKeyResult(CountryRegionCode value) {
        this.getCountryRegionCodeByKeyResult = value;
    }

}
