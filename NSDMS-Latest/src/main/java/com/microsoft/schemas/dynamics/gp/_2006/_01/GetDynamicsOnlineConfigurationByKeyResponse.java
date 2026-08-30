
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
 *         &lt;element name="GetDynamicsOnlineConfigurationByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DynamicsOnlineConfiguration" minOccurs="0"/&gt;
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
    "getDynamicsOnlineConfigurationByKeyResult"
})
@XmlRootElement(name = "GetDynamicsOnlineConfigurationByKeyResponse")
public class GetDynamicsOnlineConfigurationByKeyResponse {

    @XmlElement(name = "GetDynamicsOnlineConfigurationByKeyResult")
    protected DynamicsOnlineConfiguration getDynamicsOnlineConfigurationByKeyResult;

    /**
     * Gets the value of the getDynamicsOnlineConfigurationByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link DynamicsOnlineConfiguration }
     *     
     */
    public DynamicsOnlineConfiguration getGetDynamicsOnlineConfigurationByKeyResult() {
        return getDynamicsOnlineConfigurationByKeyResult;
    }

    /**
     * Sets the value of the getDynamicsOnlineConfigurationByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DynamicsOnlineConfiguration }
     *     
     */
    public void setGetDynamicsOnlineConfigurationByKeyResult(DynamicsOnlineConfiguration value) {
        this.getDynamicsOnlineConfigurationByKeyResult = value;
    }

}
