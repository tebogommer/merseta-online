
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
 *         &lt;element name="GetChangedPricingKeyListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfChangedPricingKey" minOccurs="0"/&gt;
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
    "getChangedPricingKeyListResult"
})
@XmlRootElement(name = "GetChangedPricingKeyListResponse")
public class GetChangedPricingKeyListResponse {

    @XmlElement(name = "GetChangedPricingKeyListResult")
    protected ArrayOfChangedPricingKey getChangedPricingKeyListResult;

    /**
     * Gets the value of the getChangedPricingKeyListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChangedPricingKey }
     *     
     */
    public ArrayOfChangedPricingKey getGetChangedPricingKeyListResult() {
        return getChangedPricingKeyListResult;
    }

    /**
     * Sets the value of the getChangedPricingKeyListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChangedPricingKey }
     *     
     */
    public void setGetChangedPricingKeyListResult(ArrayOfChangedPricingKey value) {
        this.getChangedPricingKeyListResult = value;
    }

}
