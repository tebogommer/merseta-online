
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesDebitDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayablesDebitDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Terms" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesTerms" minOccurs="0"/&gt;
 *         &lt;element name="WriteoffAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Payment" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesPayment" minOccurs="0"/&gt;
 *         &lt;element name="TotalPayments" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayablesDebitDocument", propOrder = {
    "terms",
    "writeoffAmount",
    "payment",
    "totalPayments"
})
@XmlSeeAlso({
    PayablesInvoice.class,
    PayablesFinanceCharge.class,
    PayablesMiscellaneousCharge.class,
    PayablesVendorPayment.class
})
public abstract class PayablesDebitDocument
    extends PayablesDocument
{

    @XmlElement(name = "Terms")
    protected PayablesTerms terms;
    @XmlElement(name = "WriteoffAmount")
    protected MoneyAmount writeoffAmount;
    @XmlElement(name = "Payment")
    protected PayablesPayment payment;
    @XmlElement(name = "TotalPayments")
    protected MoneyAmount totalPayments;

    /**
     * Gets the value of the terms property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesTerms }
     *     
     */
    public PayablesTerms getTerms() {
        return terms;
    }

    /**
     * Sets the value of the terms property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesTerms }
     *     
     */
    public void setTerms(PayablesTerms value) {
        this.terms = value;
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

}
