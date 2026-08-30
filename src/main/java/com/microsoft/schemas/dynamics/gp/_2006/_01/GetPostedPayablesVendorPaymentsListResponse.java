
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
 *         &lt;element name="GetPostedPayablesVendorPaymentsListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPostedPayablesVendorPaymentSummary" minOccurs="0"/&gt;
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
    "getPostedPayablesVendorPaymentsListResult"
})
@XmlRootElement(name = "GetPostedPayablesVendorPaymentsListResponse")
public class GetPostedPayablesVendorPaymentsListResponse {

    @XmlElement(name = "GetPostedPayablesVendorPaymentsListResult")
    protected ArrayOfPostedPayablesVendorPaymentSummary getPostedPayablesVendorPaymentsListResult;

    /**
     * Gets the value of the getPostedPayablesVendorPaymentsListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPostedPayablesVendorPaymentSummary }
     *     
     */
    public ArrayOfPostedPayablesVendorPaymentSummary getGetPostedPayablesVendorPaymentsListResult() {
        return getPostedPayablesVendorPaymentsListResult;
    }

    /**
     * Sets the value of the getPostedPayablesVendorPaymentsListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPostedPayablesVendorPaymentSummary }
     *     
     */
    public void setGetPostedPayablesVendorPaymentsListResult(ArrayOfPostedPayablesVendorPaymentSummary value) {
        this.getPostedPayablesVendorPaymentsListResult = value;
    }

}
