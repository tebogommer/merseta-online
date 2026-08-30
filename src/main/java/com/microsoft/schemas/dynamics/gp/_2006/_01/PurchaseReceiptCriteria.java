
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseReceiptCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseReceiptCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfPurchaseTransactionState" minOccurs="0"/&gt;
 *         &lt;element name="Id" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="VendorId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="BatchId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseReceiptCriteria", propOrder = {
    "transactionState",
    "id",
    "vendorId",
    "batchId",
    "date",
    "modifiedDate",
    "createdDate"
})
public class PurchaseReceiptCriteria
    extends Criteria
{

    @XmlElement(name = "TransactionState")
    protected ListRestrictionOfNullableOfPurchaseTransactionState transactionState;
    @XmlElement(name = "Id")
    protected LikeRestrictionOfString id;
    @XmlElement(name = "VendorId")
    protected LikeRestrictionOfString vendorId;
    @XmlElement(name = "BatchId")
    protected LikeRestrictionOfString batchId;
    @XmlElement(name = "Date")
    protected BetweenRestrictionOfNullableOfDateTime date;
    @XmlElement(name = "ModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime modifiedDate;
    @XmlElement(name = "CreatedDate")
    protected BetweenRestrictionOfNullableOfDateTime createdDate;

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfPurchaseTransactionState }
     *     
     */
    public ListRestrictionOfNullableOfPurchaseTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfPurchaseTransactionState }
     *     
     */
    public void setTransactionState(ListRestrictionOfNullableOfPurchaseTransactionState value) {
        this.transactionState = value;
    }

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
     * Gets the value of the vendorId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getVendorId() {
        return vendorId;
    }

    /**
     * Sets the value of the vendorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setVendorId(LikeRestrictionOfString value) {
        this.vendorId = value;
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
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setCreatedDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.createdDate = value;
    }

}
