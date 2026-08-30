
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Terms complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Terms"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Discount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyPercentChoice" minOccurs="0"/&gt;
 *         &lt;element name="DiscountAvailableAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="DueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DiscountDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Terms", propOrder = {
    "discount",
    "discountAvailableAmount",
    "dueDate",
    "discountDate"
})
@XmlSeeAlso({
    PayablesTerms.class,
    ReceivablesTerms.class,
    SalesTerms.class
})
public class Terms {

    @XmlElement(name = "Discount")
    protected MoneyPercentChoice discount;
    @XmlElement(name = "DiscountAvailableAmount")
    protected MoneyAmount discountAvailableAmount;
    @XmlElement(name = "DueDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dueDate;
    @XmlElement(name = "DiscountDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar discountDate;

    /**
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public MoneyPercentChoice getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public void setDiscount(MoneyPercentChoice value) {
        this.discount = value;
    }

    /**
     * Gets the value of the discountAvailableAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDiscountAvailableAmount() {
        return discountAvailableAmount;
    }

    /**
     * Sets the value of the discountAvailableAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDiscountAvailableAmount(MoneyAmount value) {
        this.discountAvailableAmount = value;
    }

    /**
     * Gets the value of the dueDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDueDate() {
        return dueDate;
    }

    /**
     * Sets the value of the dueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDueDate(XMLGregorianCalendar value) {
        this.dueDate = value;
    }

    /**
     * Gets the value of the discountDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDiscountDate() {
        return discountDate;
    }

    /**
     * Sets the value of the discountDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDiscountDate(XMLGregorianCalendar value) {
        this.discountDate = value;
    }

}
