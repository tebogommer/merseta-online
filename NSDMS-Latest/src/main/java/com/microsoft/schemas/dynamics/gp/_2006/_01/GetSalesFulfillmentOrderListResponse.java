
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
 *         &lt;element name="GetSalesFulfillmentOrderListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesFulfillmentOrderSummary" minOccurs="0"/&gt;
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
    "getSalesFulfillmentOrderListResult"
})
@XmlRootElement(name = "GetSalesFulfillmentOrderListResponse")
public class GetSalesFulfillmentOrderListResponse {

    @XmlElement(name = "GetSalesFulfillmentOrderListResult")
    protected ArrayOfSalesFulfillmentOrderSummary getSalesFulfillmentOrderListResult;

    /**
     * Gets the value of the getSalesFulfillmentOrderListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesFulfillmentOrderSummary }
     *     
     */
    public ArrayOfSalesFulfillmentOrderSummary getGetSalesFulfillmentOrderListResult() {
        return getSalesFulfillmentOrderListResult;
    }

    /**
     * Sets the value of the getSalesFulfillmentOrderListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesFulfillmentOrderSummary }
     *     
     */
    public void setGetSalesFulfillmentOrderListResult(ArrayOfSalesFulfillmentOrderSummary value) {
        this.getSalesFulfillmentOrderListResult = value;
    }

}
