
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
 *         &lt;element name="GetBatchListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfBatch" minOccurs="0"/&gt;
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
    "getBatchListResult"
})
@XmlRootElement(name = "GetBatchListResponse")
public class GetBatchListResponse {

    @XmlElement(name = "GetBatchListResult")
    protected ArrayOfBatch getBatchListResult;

    /**
     * Gets the value of the getBatchListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBatch }
     *     
     */
    public ArrayOfBatch getGetBatchListResult() {
        return getBatchListResult;
    }

    /**
     * Sets the value of the getBatchListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBatch }
     *     
     */
    public void setGetBatchListResult(ArrayOfBatch value) {
        this.getBatchListResult = value;
    }

}
