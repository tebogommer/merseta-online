
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
 *         &lt;element name="GetEmployeeAddressListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfEmployeeAddressSummary" minOccurs="0"/&gt;
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
    "getEmployeeAddressListResult"
})
@XmlRootElement(name = "GetEmployeeAddressListResponse")
public class GetEmployeeAddressListResponse {

    @XmlElement(name = "GetEmployeeAddressListResult")
    protected ArrayOfEmployeeAddressSummary getEmployeeAddressListResult;

    /**
     * Gets the value of the getEmployeeAddressListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEmployeeAddressSummary }
     *     
     */
    public ArrayOfEmployeeAddressSummary getGetEmployeeAddressListResult() {
        return getEmployeeAddressListResult;
    }

    /**
     * Sets the value of the getEmployeeAddressListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEmployeeAddressSummary }
     *     
     */
    public void setGetEmployeeAddressListResult(ArrayOfEmployeeAddressSummary value) {
        this.getEmployeeAddressListResult = value;
    }

}
