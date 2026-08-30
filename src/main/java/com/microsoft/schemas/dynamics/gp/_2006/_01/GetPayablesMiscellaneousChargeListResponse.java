
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
 *         &lt;element name="GetPayablesMiscellaneousChargeListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPayablesMiscellaneousChargeSummary" minOccurs="0"/&gt;
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
    "getPayablesMiscellaneousChargeListResult"
})
@XmlRootElement(name = "GetPayablesMiscellaneousChargeListResponse")
public class GetPayablesMiscellaneousChargeListResponse {

    @XmlElement(name = "GetPayablesMiscellaneousChargeListResult")
    protected ArrayOfPayablesMiscellaneousChargeSummary getPayablesMiscellaneousChargeListResult;

    /**
     * Gets the value of the getPayablesMiscellaneousChargeListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPayablesMiscellaneousChargeSummary }
     *     
     */
    public ArrayOfPayablesMiscellaneousChargeSummary getGetPayablesMiscellaneousChargeListResult() {
        return getPayablesMiscellaneousChargeListResult;
    }

    /**
     * Sets the value of the getPayablesMiscellaneousChargeListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPayablesMiscellaneousChargeSummary }
     *     
     */
    public void setGetPayablesMiscellaneousChargeListResult(ArrayOfPayablesMiscellaneousChargeSummary value) {
        this.getPayablesMiscellaneousChargeListResult = value;
    }

}
