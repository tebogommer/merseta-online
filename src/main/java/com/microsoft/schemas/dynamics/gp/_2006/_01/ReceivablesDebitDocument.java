
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceivablesDebitDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceivablesDebitDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Payment" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesPayment" minOccurs="0"/&gt;
 *         &lt;element name="Terms" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesTerms" minOccurs="0"/&gt;
 *         &lt;element name="WriteoffAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="GSTDiscountAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PaymentTermsKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentTermsKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceivablesDebitDocument", propOrder = {
    "payment",
    "terms",
    "writeoffAmount",
    "gstDiscountAmount",
    "paymentTermsKey"
})
@XmlSeeAlso({
    ReceivablesInvoice.class,
    ReceivablesDebitMemo.class,
    ReceivablesServiceRepair.class,
    ReceivablesFinanceCharge.class
})
public abstract class ReceivablesDebitDocument
    extends ReceivablesDocument
{

    @XmlElement(name = "Payment")
    protected ReceivablesPayment payment;
    @XmlElement(name = "Terms")
    protected ReceivablesTerms terms;
    @XmlElement(name = "WriteoffAmount")
    protected MoneyAmount writeoffAmount;
    @XmlElement(name = "GSTDiscountAmount")
    protected MoneyAmount gstDiscountAmount;
    @XmlElement(name = "PaymentTermsKey")
    protected PaymentTermsKey paymentTermsKey;

    /**
     * Gets the value of the payment property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivablesPayment }
     *     
     */
    public ReceivablesPayment getPayment() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesPayment }
     *     
     */
    public void setPayment(ReceivablesPayment value) {
        this.payment = value;
    }

    /**
     * Gets the value of the terms property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivablesTerms }
     *     
     */
    public ReceivablesTerms getTerms() {
        return terms;
    }

    /**
     * Sets the value of the terms property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesTerms }
     *     
     */
    public void setTerms(ReceivablesTerms value) {
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
     * Gets the value of the gstDiscountAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getGSTDiscountAmount() {
        return gstDiscountAmount;
    }

    /**
     * Sets the value of the gstDiscountAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setGSTDiscountAmount(MoneyAmount value) {
        this.gstDiscountAmount = value;
    }

    /**
     * Gets the value of the paymentTermsKey property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsKey }
     *     
     */
    public PaymentTermsKey getPaymentTermsKey() {
        return paymentTermsKey;
    }

    /**
     * Sets the value of the paymentTermsKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsKey }
     *     
     */
    public void setPaymentTermsKey(PaymentTermsKey value) {
        this.paymentTermsKey = value;
    }

}
