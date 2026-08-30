
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PaymentTerms complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentTerms"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentTermsKey" minOccurs="0"/&gt;
 *         &lt;element name="DueDateBasedOn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DueDateBasedOn"/&gt;
 *         &lt;element name="DaysUntilDue" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DiscountDateBasedOn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DiscountDateBasedOn"/&gt;
 *         &lt;element name="DaysDiscountAvailable" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DiscountCalculation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyPercentChoice" minOccurs="0"/&gt;
 *         &lt;element name="UseGracePeriods" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsDiscountCalculatedOnSalePurchase" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsDiscountCalculatedOnTradeDiscount" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsDiscountCalculatedOnFreight" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsDiscountCalculatedOnMiscellaneous" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsDiscountCalculatedOnTax" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="UseValueAddedTax" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTerms", propOrder = {
    "key",
    "dueDateBasedOn",
    "daysUntilDue",
    "discountDateBasedOn",
    "daysDiscountAvailable",
    "discountCalculation",
    "useGracePeriods",
    "isDiscountCalculatedOnSalePurchase",
    "isDiscountCalculatedOnTradeDiscount",
    "isDiscountCalculatedOnFreight",
    "isDiscountCalculatedOnMiscellaneous",
    "isDiscountCalculatedOnTax",
    "useValueAddedTax",
    "modifiedBy",
    "createdDate",
    "modifiedDate"
})
public class PaymentTerms
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected PaymentTermsKey key;
    @XmlElement(name = "DueDateBasedOn", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected DueDateBasedOn dueDateBasedOn;
    @XmlElement(name = "DaysUntilDue", required = true, type = Integer.class, nillable = true)
    protected Integer daysUntilDue;
    @XmlElement(name = "DiscountDateBasedOn", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected DiscountDateBasedOn discountDateBasedOn;
    @XmlElement(name = "DaysDiscountAvailable", required = true, type = Integer.class, nillable = true)
    protected Integer daysDiscountAvailable;
    @XmlElement(name = "DiscountCalculation")
    protected MoneyPercentChoice discountCalculation;
    @XmlElement(name = "UseGracePeriods", required = true, type = Boolean.class, nillable = true)
    protected Boolean useGracePeriods;
    @XmlElement(name = "IsDiscountCalculatedOnSalePurchase", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDiscountCalculatedOnSalePurchase;
    @XmlElement(name = "IsDiscountCalculatedOnTradeDiscount", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDiscountCalculatedOnTradeDiscount;
    @XmlElement(name = "IsDiscountCalculatedOnFreight", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDiscountCalculatedOnFreight;
    @XmlElement(name = "IsDiscountCalculatedOnMiscellaneous", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDiscountCalculatedOnMiscellaneous;
    @XmlElement(name = "IsDiscountCalculatedOnTax", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDiscountCalculatedOnTax;
    @XmlElement(name = "UseValueAddedTax", required = true, type = Boolean.class, nillable = true)
    protected Boolean useValueAddedTax;
    @XmlElement(name = "ModifiedBy")
    protected String modifiedBy;
    @XmlElement(name = "CreatedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsKey }
     *     
     */
    public PaymentTermsKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsKey }
     *     
     */
    public void setKey(PaymentTermsKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the dueDateBasedOn property.
     * 
     * @return
     *     possible object is
     *     {@link DueDateBasedOn }
     *     
     */
    public DueDateBasedOn getDueDateBasedOn() {
        return dueDateBasedOn;
    }

    /**
     * Sets the value of the dueDateBasedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link DueDateBasedOn }
     *     
     */
    public void setDueDateBasedOn(DueDateBasedOn value) {
        this.dueDateBasedOn = value;
    }

    /**
     * Gets the value of the daysUntilDue property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDaysUntilDue() {
        return daysUntilDue;
    }

    /**
     * Sets the value of the daysUntilDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDaysUntilDue(Integer value) {
        this.daysUntilDue = value;
    }

    /**
     * Gets the value of the discountDateBasedOn property.
     * 
     * @return
     *     possible object is
     *     {@link DiscountDateBasedOn }
     *     
     */
    public DiscountDateBasedOn getDiscountDateBasedOn() {
        return discountDateBasedOn;
    }

    /**
     * Sets the value of the discountDateBasedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscountDateBasedOn }
     *     
     */
    public void setDiscountDateBasedOn(DiscountDateBasedOn value) {
        this.discountDateBasedOn = value;
    }

    /**
     * Gets the value of the daysDiscountAvailable property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDaysDiscountAvailable() {
        return daysDiscountAvailable;
    }

    /**
     * Sets the value of the daysDiscountAvailable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDaysDiscountAvailable(Integer value) {
        this.daysDiscountAvailable = value;
    }

    /**
     * Gets the value of the discountCalculation property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public MoneyPercentChoice getDiscountCalculation() {
        return discountCalculation;
    }

    /**
     * Sets the value of the discountCalculation property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public void setDiscountCalculation(MoneyPercentChoice value) {
        this.discountCalculation = value;
    }

    /**
     * Gets the value of the useGracePeriods property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseGracePeriods() {
        return useGracePeriods;
    }

    /**
     * Sets the value of the useGracePeriods property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseGracePeriods(Boolean value) {
        this.useGracePeriods = value;
    }

    /**
     * Gets the value of the isDiscountCalculatedOnSalePurchase property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDiscountCalculatedOnSalePurchase() {
        return isDiscountCalculatedOnSalePurchase;
    }

    /**
     * Sets the value of the isDiscountCalculatedOnSalePurchase property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDiscountCalculatedOnSalePurchase(Boolean value) {
        this.isDiscountCalculatedOnSalePurchase = value;
    }

    /**
     * Gets the value of the isDiscountCalculatedOnTradeDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDiscountCalculatedOnTradeDiscount() {
        return isDiscountCalculatedOnTradeDiscount;
    }

    /**
     * Sets the value of the isDiscountCalculatedOnTradeDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDiscountCalculatedOnTradeDiscount(Boolean value) {
        this.isDiscountCalculatedOnTradeDiscount = value;
    }

    /**
     * Gets the value of the isDiscountCalculatedOnFreight property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDiscountCalculatedOnFreight() {
        return isDiscountCalculatedOnFreight;
    }

    /**
     * Sets the value of the isDiscountCalculatedOnFreight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDiscountCalculatedOnFreight(Boolean value) {
        this.isDiscountCalculatedOnFreight = value;
    }

    /**
     * Gets the value of the isDiscountCalculatedOnMiscellaneous property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDiscountCalculatedOnMiscellaneous() {
        return isDiscountCalculatedOnMiscellaneous;
    }

    /**
     * Sets the value of the isDiscountCalculatedOnMiscellaneous property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDiscountCalculatedOnMiscellaneous(Boolean value) {
        this.isDiscountCalculatedOnMiscellaneous = value;
    }

    /**
     * Gets the value of the isDiscountCalculatedOnTax property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDiscountCalculatedOnTax() {
        return isDiscountCalculatedOnTax;
    }

    /**
     * Sets the value of the isDiscountCalculatedOnTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDiscountCalculatedOnTax(Boolean value) {
        this.isDiscountCalculatedOnTax = value;
    }

    /**
     * Gets the value of the useValueAddedTax property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseValueAddedTax() {
        return useValueAddedTax;
    }

    /**
     * Sets the value of the useValueAddedTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseValueAddedTax(Boolean value) {
        this.useValueAddedTax = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

}
