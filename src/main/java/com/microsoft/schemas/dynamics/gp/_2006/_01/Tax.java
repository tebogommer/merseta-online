
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Tax complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Tax"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxableAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="IsBackoutTax" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tax", propOrder = {
    "taxAmount",
    "totalAmount",
    "taxableAmount",
    "isBackoutTax"
})
@XmlSeeAlso({
    PayablesTax.class,
    ReceivablesTax.class,
    SalesTax.class
})
public abstract class Tax
    extends BusinessObject
{

    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "TotalAmount")
    protected MoneyAmount totalAmount;
    @XmlElement(name = "TaxableAmount")
    protected MoneyAmount taxableAmount;
    @XmlElement(name = "IsBackoutTax", required = true, type = Boolean.class, nillable = true)
    protected Boolean isBackoutTax;

    /**
     * Gets the value of the taxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxAmount(MoneyAmount value) {
        this.taxAmount = value;
    }

    /**
     * Gets the value of the totalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalAmount(MoneyAmount value) {
        this.totalAmount = value;
    }

    /**
     * Gets the value of the taxableAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxableAmount() {
        return taxableAmount;
    }

    /**
     * Sets the value of the taxableAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxableAmount(MoneyAmount value) {
        this.taxableAmount = value;
    }

    /**
     * Gets the value of the isBackoutTax property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBackoutTax() {
        return isBackoutTax;
    }

    /**
     * Sets the value of the isBackoutTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBackoutTax(Boolean value) {
        this.isBackoutTax = value;
    }

}
