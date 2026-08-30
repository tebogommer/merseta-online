
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
 *         &lt;element name="GetReturnMaterialAuthorizationByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorization" minOccurs="0"/&gt;
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
    "getReturnMaterialAuthorizationByKeyResult"
})
@XmlRootElement(name = "GetReturnMaterialAuthorizationByKeyResponse")
public class GetReturnMaterialAuthorizationByKeyResponse {

    @XmlElement(name = "GetReturnMaterialAuthorizationByKeyResult")
    protected ReturnMaterialAuthorization getReturnMaterialAuthorizationByKeyResult;

    /**
     * Gets the value of the getReturnMaterialAuthorizationByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorization }
     *     
     */
    public ReturnMaterialAuthorization getGetReturnMaterialAuthorizationByKeyResult() {
        return getReturnMaterialAuthorizationByKeyResult;
    }

    /**
     * Sets the value of the getReturnMaterialAuthorizationByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorization }
     *     
     */
    public void setGetReturnMaterialAuthorizationByKeyResult(ReturnMaterialAuthorization value) {
        this.getReturnMaterialAuthorizationByKeyResult = value;
    }

}
