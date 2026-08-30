
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
 *         &lt;element name="GetGLTransactionByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLTransaction" minOccurs="0"/&gt;
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
    "getGLTransactionByKeyResult"
})
@XmlRootElement(name = "GetGLTransactionByKeyResponse")
public class GetGLTransactionByKeyResponse {

    @XmlElement(name = "GetGLTransactionByKeyResult")
    protected GLTransaction getGLTransactionByKeyResult;

    /**
     * Gets the value of the getGLTransactionByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link GLTransaction }
     *     
     */
    public GLTransaction getGetGLTransactionByKeyResult() {
        return getGLTransactionByKeyResult;
    }

    /**
     * Sets the value of the getGLTransactionByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLTransaction }
     *     
     */
    public void setGetGLTransactionByKeyResult(GLTransaction value) {
        this.getGLTransactionByKeyResult = value;
    }

}
