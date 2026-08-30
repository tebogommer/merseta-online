
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayablesReturn"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesCreditDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DiscountReturnedAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="WriteoffAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalPayments" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Payment" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesPayment" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayablesReturn", propOrder = {
    "discountReturnedAmount",
    "writeoffAmount",
    "totalPayments",
    "payment"
})
public class PayablesReturn
    extends PayablesCreditDocument
{

    @XmlElement(name = "DiscountReturnedAmount")
    protected MoneyAmount discountReturnedAmount;
    @XmlElement(name = "WriteoffAmount")
    protected MoneyAmount writeoffAmount;
    @XmlElement(name = "TotalPayments")
    protected MoneyAmount totalPayments;
    @XmlElement(name = "Payment")
    protected PayablesPayment payment;

    /**
     * Gets the value of the discountReturnedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDiscountReturnedAmount() {
        return discountReturnedAmount;
    }

    /**
     * Sets the value of the discountReturnedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDiscountReturnedAmount(MoneyAmount value) {
        this.discountReturnedAmount = value;
    }

    /**
     * Gets the value of the writeoffAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getWriteoffAmount() {
        return writeoffAmount;
    }

    /**
     * Sets the value of the writeoffAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setWriteoffAmount(MoneyAmount value) {
        this.writeoffAmount = value;
    }

    /**
     * Gets the value of the totalPayments property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalPayments() {
        return totalPayments;
    }

    /**
     * Sets the value of the totalPayments property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalPayments(MoneyAmount value) {
        this.totalPayments = value;
    }

    /**
     * Gets the value of the payment property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesPayment }
     *     
     */
    public PayablesPayment getPayment() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesPayment }
     *     
     */
    public void setPayment(PayablesPayment value) {
        this.payment = value;
    }

}
