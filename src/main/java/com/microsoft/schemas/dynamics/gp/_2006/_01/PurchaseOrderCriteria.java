
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseOrderCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseOrderCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfPurchaseTransactionState" minOccurs="0"/&gt;
 *         &lt;element name="Id" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="VendorId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="BuyerId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfPurchaseOrderStatus" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorScope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseOrderCriteria", propOrder = {
    "transactionState",
    "id",
    "vendorId",
    "buyerId",
    "date",
    "status",
    "modifiedDate",
    "createdDate",
    "scope"
})
public class PurchaseOrderCriteria
    extends Criteria
{

    @XmlElement(name = "TransactionState")
    protected ListRestrictionOfNullableOfPurchaseTransactionState transactionState;
    @XmlElement(name = "Id")
    protected LikeRestrictionOfString id;
    @XmlElement(name = "VendorId")
    protected LikeRestrictionOfString vendorId;
    @XmlElement(name = "BuyerId")
    protected LikeRestrictionOfString buyerId;
    @XmlElement(name = "Date")
    protected BetweenRestrictionOfNullableOfDateTime date;
    @XmlElement(name = "Status")
    protected ListRestrictionOfNullableOfPurchaseOrderStatus status;
    @XmlElement(name = "ModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime modifiedDate;
    @XmlElement(name = "CreatedDate")
    protected BetweenRestrictionOfNullableOfDateTime createdDate;
    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected VendorScope scope;

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
     * Gets the value of the buyerId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getBuyerId() {
        return buyerId;
    }

    /**
     * Sets the value of the buyerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setBuyerId(LikeRestrictionOfString value) {
        this.buyerId = value;
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfPurchaseOrderStatus }
     *     
     */
    public ListRestrictionOfNullableOfPurchaseOrderStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfPurchaseOrderStatus }
     *     
     */
    public void setStatus(ListRestrictionOfNullableOfPurchaseOrderStatus value) {
        this.status = value;
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

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link VendorScope }
     *     
     */
    public VendorScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorScope }
     *     
     */
    public void setScope(VendorScope value) {
        this.scope = value;
    }

}
