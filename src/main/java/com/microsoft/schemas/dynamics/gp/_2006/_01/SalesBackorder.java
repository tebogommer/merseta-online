
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesBackorder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesBackorder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PaymentAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="DepositAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesBackorderLine" minOccurs="0"/&gt;
 *         &lt;element name="Payments" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesPayment" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesBackorder", propOrder = {
    "paymentAmount",
    "depositAmount",
    "lines",
    "payments"
})
public class SalesBackorder
    extends SalesDocument
{

    @XmlElement(name = "PaymentAmount")
    protected MoneyAmount paymentAmount;
    @XmlElement(name = "DepositAmount")
    protected MoneyAmount depositAmount;
    @XmlElement(name = "Lines")
    protected ArrayOfSalesBackorderLine lines;
    @XmlElement(name = "Payments")
    protected ArrayOfSalesPayment payments;

    /**
     * Gets the value of the paymentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the value of the paymentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPaymentAmount(MoneyAmount value) {
        this.paymentAmount = value;
    }

    /**
     * Gets the value of the depositAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDepositAmount() {
        return depositAmount;
    }

    /**
     * Sets the value of the depositAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDepositAmount(MoneyAmount value) {
        this.depositAmount = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesBackorderLine }
     *     
     */
    public ArrayOfSalesBackorderLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesBackorderLine }
     *     
     */
    public void setLines(ArrayOfSalesBackorderLine value) {
        this.lines = value;
    }

    /**
     * Gets the value of the payments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesPayment }
     *     
     */
    public ArrayOfSalesPayment getPayments() {
        return payments;
    }

    /**
     * Sets the value of the payments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesPayment }
     *     
     */
    public void setPayments(ArrayOfSalesPayment value) {
        this.payments = value;
    }

}
