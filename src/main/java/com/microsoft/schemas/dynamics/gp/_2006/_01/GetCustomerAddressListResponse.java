
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
 *         &lt;element name="GetCustomerAddressListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfCustomerAddressSummary" minOccurs="0"/&gt;
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
    "getCustomerAddressListResult"
})
@XmlRootElement(name = "GetCustomerAddressListResponse")
public class GetCustomerAddressListResponse {

    @XmlElement(name = "GetCustomerAddressListResult")
    protected ArrayOfCustomerAddressSummary getCustomerAddressListResult;

    /**
     * Gets the value of the getCustomerAddressListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCustomerAddressSummary }
     *     
     */
    public ArrayOfCustomerAddressSummary getGetCustomerAddressListResult() {
        return getCustomerAddressListResult;
    }

    /**
     * Sets the value of the getCustomerAddressListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCustomerAddressSummary }
     *     
     */
    public void setGetCustomerAddressListResult(ArrayOfCustomerAddressSummary value) {
        this.getCustomerAddressListResult = value;
    }

}
