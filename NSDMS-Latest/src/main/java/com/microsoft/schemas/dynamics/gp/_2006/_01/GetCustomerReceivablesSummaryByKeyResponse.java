
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
 *         &lt;element name="GetCustomerReceivablesSummaryByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerReceivablesSummary" minOccurs="0"/&gt;
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
    "getCustomerReceivablesSummaryByKeyResult"
})
@XmlRootElement(name = "GetCustomerReceivablesSummaryByKeyResponse")
public class GetCustomerReceivablesSummaryByKeyResponse {

    @XmlElement(name = "GetCustomerReceivablesSummaryByKeyResult")
    protected CustomerReceivablesSummary getCustomerReceivablesSummaryByKeyResult;

    /**
     * Gets the value of the getCustomerReceivablesSummaryByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerReceivablesSummary }
     *     
     */
    public CustomerReceivablesSummary getGetCustomerReceivablesSummaryByKeyResult() {
        return getCustomerReceivablesSummaryByKeyResult;
    }

    /**
     * Sets the value of the getCustomerReceivablesSummaryByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerReceivablesSummary }
     *     
     */
    public void setGetCustomerReceivablesSummaryByKeyResult(CustomerReceivablesSummary value) {
        this.getCustomerReceivablesSummaryByKeyResult = value;
    }

}
