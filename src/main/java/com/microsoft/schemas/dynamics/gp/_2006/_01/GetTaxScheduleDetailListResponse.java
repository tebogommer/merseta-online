
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
 *         &lt;element name="GetTaxScheduleDetailListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfTaxScheduleDetail" minOccurs="0"/&gt;
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
    "getTaxScheduleDetailListResult"
})
@XmlRootElement(name = "GetTaxScheduleDetailListResponse")
public class GetTaxScheduleDetailListResponse {

    @XmlElement(name = "GetTaxScheduleDetailListResult")
    protected ArrayOfTaxScheduleDetail getTaxScheduleDetailListResult;

    /**
     * Gets the value of the getTaxScheduleDetailListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTaxScheduleDetail }
     *     
     */
    public ArrayOfTaxScheduleDetail getGetTaxScheduleDetailListResult() {
        return getTaxScheduleDetailListResult;
    }

    /**
     * Sets the value of the getTaxScheduleDetailListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTaxScheduleDetail }
     *     
     */
    public void setGetTaxScheduleDetailListResult(ArrayOfTaxScheduleDetail value) {
        this.getTaxScheduleDetailListResult = value;
    }

}
