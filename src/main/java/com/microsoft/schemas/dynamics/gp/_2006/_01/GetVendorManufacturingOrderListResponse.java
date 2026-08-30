
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
 *         &lt;element name="GetVendorManufacturingOrderListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfVendorManufacturingOrderSummary" minOccurs="0"/&gt;
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
    "getVendorManufacturingOrderListResult"
})
@XmlRootElement(name = "GetVendorManufacturingOrderListResponse")
public class GetVendorManufacturingOrderListResponse {

    @XmlElement(name = "GetVendorManufacturingOrderListResult")
    protected ArrayOfVendorManufacturingOrderSummary getVendorManufacturingOrderListResult;

    /**
     * Gets the value of the getVendorManufacturingOrderListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVendorManufacturingOrderSummary }
     *     
     */
    public ArrayOfVendorManufacturingOrderSummary getGetVendorManufacturingOrderListResult() {
        return getVendorManufacturingOrderListResult;
    }

    /**
     * Sets the value of the getVendorManufacturingOrderListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVendorManufacturingOrderSummary }
     *     
     */
    public void setGetVendorManufacturingOrderListResult(ArrayOfVendorManufacturingOrderSummary value) {
        this.getVendorManufacturingOrderListResult = value;
    }

}
