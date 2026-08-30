
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CashReceiptCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CashReceiptCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CustomerId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="BatchId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyISOCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfReceivablesTransactionState" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CashReceiptScope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashReceiptCriteria", propOrder = {
    "id",
    "customerId",
    "batchId",
    "currencyISOCode",
    "transactionState",
    "date",
    "modifiedDate",
    "scope"
})
public class CashReceiptCriteria
    extends Criteria
{

    @XmlElement(name = "Id")
    protected LikeRestrictionOfString id;
    @XmlElement(name = "CustomerId")
    protected LikeRestrictionOfString customerId;
    @XmlElement(name = "BatchId")
    protected LikeRestrictionOfString batchId;
    @XmlElement(name = "CurrencyISOCode")
    protected ListRestrictionOfString currencyISOCode;
    @XmlElement(name = "TransactionState")
    protected ListRestrictionOfNullableOfReceivablesTransactionState transactionState;
    @XmlElement(name = "Date")
    protected BetweenRestrictionOfNullableOfDateTime date;
    @XmlElement(name = "ModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime modifiedDate;
    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected CashReceiptScope scope;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setId(LikeRestrictionOfString value) {
        this.id = value;
    }

    /**
     * Gets the value of the customerId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCustomerId(LikeRestrictionOfString value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the batchId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getBatchId() {
        return batchId;
    }

    /**
     * Sets the value of the batchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setBatchId(LikeRestrictionOfString value) {
        this.batchId = value;
    }

    /**
     * Gets the value of the currencyISOCode property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfString }
     *     
     */
    public ListRestrictionOfString getCurrencyISOCode() {
        return currencyISOCode;
    }

    /**
     * Sets the value of the currencyISOCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfString }
     *     
     */
    public void setCurrencyISOCode(ListRestrictionOfString value) {
        this.currencyISOCode = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfReceivablesTransactionState }
     *     
     */
    public ListRestrictionOfNullableOfReceivablesTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfReceivablesTransactionState }
     *     
     */
    public void setTransactionState(ListRestrictionOfNullableOfReceivablesTransactionState value) {
        this.transactionState = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.date = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setModifiedDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.modifiedDate = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link CashReceiptScope }
     *     
     */
    public CashReceiptScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashReceiptScope }
     *     
     */
    public void setScope(CashReceiptScope value) {
        this.scope = value;
    }

}
