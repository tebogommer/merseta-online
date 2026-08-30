
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
 *         &lt;element name="GetInventoryVarianceListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfInventoryVarianceSummary" minOccurs="0"/&gt;
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
    "getInventoryVarianceListResult"
})
@XmlRootElement(name = "GetInventoryVarianceListResponse")
public class GetInventoryVarianceListResponse {

    @XmlElement(name = "GetInventoryVarianceListResult")
    protected ArrayOfInventoryVarianceSummary getInventoryVarianceListResult;

    /**
     * Gets the value of the getInventoryVarianceListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInventoryVarianceSummary }
     *     
     */
    public ArrayOfInventoryVarianceSummary getGetInventoryVarianceListResult() {
        return getInventoryVarianceListResult;
    }

    /**
     * Sets the value of the getInventoryVarianceListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInventoryVarianceSummary }
     *     
     */
    public void setGetInventoryVarianceListResult(ArrayOfInventoryVarianceSummary value) {
        this.getInventoryVarianceListResult = value;
    }

}
