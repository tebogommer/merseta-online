
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
 *         &lt;element name="GetReceivablesFinanceChargeByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesFinanceCharge" minOccurs="0"/&gt;
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
    "getReceivablesFinanceChargeByKeyResult"
})
@XmlRootElement(name = "GetReceivablesFinanceChargeByKeyResponse")
public class GetReceivablesFinanceChargeByKeyResponse {

    @XmlElement(name = "GetReceivablesFinanceChargeByKeyResult")
    protected ReceivablesFinanceCharge getReceivablesFinanceChargeByKeyResult;

    /**
     * Gets the value of the getReceivablesFinanceChargeByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivablesFinanceCharge }
     *     
     */
    public ReceivablesFinanceCharge getGetReceivablesFinanceChargeByKeyResult() {
        return getReceivablesFinanceChargeByKeyResult;
    }

    /**
     * Sets the value of the getReceivablesFinanceChargeByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesFinanceCharge }
     *     
     */
    public void setGetReceivablesFinanceChargeByKeyResult(ReceivablesFinanceCharge value) {
        this.getReceivablesFinanceChargeByKeyResult = value;
    }

}
