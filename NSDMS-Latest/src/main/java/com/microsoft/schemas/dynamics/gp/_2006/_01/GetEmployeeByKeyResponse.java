
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
 *         &lt;element name="GetEmployeeByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Employee" minOccurs="0"/&gt;
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
    "getEmployeeByKeyResult"
})
@XmlRootElement(name = "GetEmployeeByKeyResponse")
public class GetEmployeeByKeyResponse {

    @XmlElement(name = "GetEmployeeByKeyResult")
    protected Employee getEmployeeByKeyResult;

    /**
     * Gets the value of the getEmployeeByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getGetEmployeeByKeyResult() {
        return getEmployeeByKeyResult;
    }

    /**
     * Sets the value of the getEmployeeByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setGetEmployeeByKeyResult(Employee value) {
        this.getEmployeeByKeyResult = value;
    }

}
