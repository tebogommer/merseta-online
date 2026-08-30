
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
 *         &lt;element name="GetVendorListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfVendorSummary" minOccurs="0"/&gt;
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
    "getVendorListResult"
})
@XmlRootElement(name = "GetVendorListResponse")
public class GetVendorListResponse {

    @XmlElement(name = "GetVendorListResult")
    protected ArrayOfVendorSummary getVendorListResult;

    /**
     * Gets the value of the getVendorListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVendorSummary }
     *     
     */
    public ArrayOfVendorSummary getGetVendorListResult() {
        return getVendorListResult;
    }

    /**
     * Sets the value of the getVendorListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVendorSummary }
     *     
     */
    public void setGetVendorListResult(ArrayOfVendorSummary value) {
        this.getVendorListResult = value;
    }

}
