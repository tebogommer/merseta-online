
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
 *         &lt;element name="GetPricingListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPricingSummary" minOccurs="0"/&gt;
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
    "getPricingListResult"
})
@XmlRootElement(name = "GetPricingListResponse")
public class GetPricingListResponse {

    @XmlElement(name = "GetPricingListResult")
    protected ArrayOfPricingSummary getPricingListResult;

    /**
     * Gets the value of the getPricingListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPricingSummary }
     *     
     */
    public ArrayOfPricingSummary getGetPricingListResult() {
        return getPricingListResult;
    }

    /**
     * Sets the value of the getPricingListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPricingSummary }
     *     
     */
    public void setGetPricingListResult(ArrayOfPricingSummary value) {
        this.getPricingListResult = value;
    }

}
