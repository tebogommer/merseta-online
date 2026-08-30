
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayablesPayment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Cash" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesCashDetail" minOccurs="0"/&gt;
 *         &lt;element name="Check" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CheckDetail" minOccurs="0"/&gt;
 *         &lt;element name="PaymentCard" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentCardDetail" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayablesPayment", propOrder = {
    "cash",
    "check",
    "paymentCard"
})
public class PayablesPayment {

    @XmlElement(name = "Cash")
    protected PayablesCashDetail cash;
    @XmlElement(name = "Check")
    protected CheckDetail check;
    @XmlElement(name = "PaymentCard")
    protected PaymentCardDetail paymentCard;

    /**
     * Gets the value of the cash property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesCashDetail }
     *     
     */
    public PayablesCashDetail getCash() {
        return cash;
    }

    /**
     * Sets the value of the cash property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesCashDetail }
     *     
     */
    public void setCash(PayablesCashDetail value) {
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
     *     {@link PaymentCardDetail }
     *     
     */
    public PaymentCardDetail getPaymentCard() {
        return paymentCard;
    }

    /**
     * Sets the value of the paymentCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentCardDetail }
     *     
     */
    public void setPaymentCard(PaymentCardDetail value) {
        this.paymentCard = value;
    }

}
