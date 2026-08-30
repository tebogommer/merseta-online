
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
 *         &lt;element name="GetFeeListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfFeeSummary" minOccurs="0"/&gt;
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
    "getFeeListResult"
})
@XmlRootElement(name = "GetFeeListResponse")
public class GetFeeListResponse {

    @XmlElement(name = "GetFeeListResult")
    protected ArrayOfFeeSummary getFeeListResult;

    /**
     * Gets the value of the getFeeListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFeeSummary }
     *     
     */
    public ArrayOfFeeSummary getGetFeeListResult() {
        return getFeeListResult;
    }

    /**
     * Sets the value of the getFeeListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFeeSummary }
     *     
     */
    public void setGetFeeListResult(ArrayOfFeeSummary value) {
        this.getFeeListResult = value;
    }

}
