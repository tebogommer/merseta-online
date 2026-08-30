
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceivablesPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceivablesPayment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Cash" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CashDetail" minOccurs="0"/&gt;
 *         &lt;element name="Check" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CheckDetail" minOccurs="0"/&gt;
 *         &lt;element name="PaymentCard" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesPaymentCardDetail" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceivablesPayment", propOrder = {
    "cash",
    "check",
    "paymentCard"
})
public class ReceivablesPayment {

    @XmlElement(name = "Cash")
    protected CashDetail cash;
    @XmlElement(name = "Check")
    protected CheckDetail check;
    @XmlElement(name = "PaymentCard")
    protected ReceivablesPaymentCardDetail paymentCard;

    /**
     * Gets the value of the cash property.
     * 
     * @return
     *     possible object is
     *     {@link CashDetail }
     *     
     */
    public CashDetail getCash() {
        return cash;
    }

    /**
     * Sets the value of the cash property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashDetail }
     *     
     */
    public void setCash(CashDetail value) {
        this.cash = value;
    }

    /**
     * Gets the value of the check property.
     * 
     * @return
     *     possible object is
     *     {@link CheckDetail }
     *     
     */
    public CheckDetail getCheck() {
        return check;
    }

    /**
     * Sets the value of the check property.
     * 
     * @param value
     *     allowed object is
     *     {@link CheckDetail }
     *     
     */
    public void setCheck(CheckDetail value) {
        this.check = value;
    }

    /**
     * Gets the value of the paymentCard property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivablesPaymentCardDetail }
     *     
     */
    public ReceivablesPaymentCardDetail getPaymentCard() {
        return paymentCard;
    }

    /**
     * Sets the value of the paymentCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesPaymentCardDetail }
     *     
     */
    public void setPaymentCard(ReceivablesPaymentCardDetail value) {
        this.paymentCard = value;
    }

}
