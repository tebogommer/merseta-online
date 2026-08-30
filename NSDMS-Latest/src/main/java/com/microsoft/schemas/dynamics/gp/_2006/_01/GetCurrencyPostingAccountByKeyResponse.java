
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
 *         &lt;element name="GetCurrencyPostingAccountByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyPostingAccount" minOccurs="0"/&gt;
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
    "getCurrencyPostingAccountByKeyResult"
})
@XmlRootElement(name = "GetCurrencyPostingAccountByKeyResponse")
public class GetCurrencyPostingAccountByKeyResponse {

    @XmlElement(name = "GetCurrencyPostingAccountByKeyResult")
    protected CurrencyPostingAccount getCurrencyPostingAccountByKeyResult;

    /**
     * Gets the value of the getCurrencyPostingAccountByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyPostingAccount }
     *     
     */
    public CurrencyPostingAccount getGetCurrencyPostingAccountByKeyResult() {
        return getCurrencyPostingAccountByKeyResult;
    }

    /**
     * Sets the value of the getCurrencyPostingAccountByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyPostingAccount }
     *     
     */
    public void setGetCurrencyPostingAccountByKeyResult(CurrencyPostingAccount value) {
        this.getCurrencyPostingAccountByKeyResult = value;
    }

}
