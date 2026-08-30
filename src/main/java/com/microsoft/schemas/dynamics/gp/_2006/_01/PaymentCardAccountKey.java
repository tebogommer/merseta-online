
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentCardAccountKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentCardAccountKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PaymentCardTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentCardTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentCardAccountKey", propOrder = {
    "paymentCardTypeKey",
    "number"
})
public class PaymentCardAccountKey
    extends ReferenceKey
{

    @XmlElement(name = "PaymentCardTypeKey")
    protected PaymentCardTypeKey paymentCardTypeKey;
    @XmlElement(name = "Number")
    protected String number;

    /**
     * Gets the value of the paymentCardTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentCardTypeKey }
     *     
     */
    public PaymentCardTypeKey getPaymentCardTypeKey() {
        return paymentCardTypeKey;
    }

    /**
     * Sets the value of the paymentCardTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentCardTypeKey }
     *     
     */
    public void setPaymentCardTypeKey(PaymentCardTypeKey value) {
        this.paymentCardTypeKey = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

}
