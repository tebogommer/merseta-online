
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceivablesDocumentCriteriaBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceivablesDocumentCriteriaBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfReceivablesTransactionState" minOccurs="0"/&gt;
 *         &lt;element name="DocumentId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="CustomerId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="BatchId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="SalespersonId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesDocumentScope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceivablesDocumentCriteriaBase", propOrder = {
    "transactionState",
    "documentId",
    "date",
    "customerId",
    "batchId",
    "salespersonId",
    "modifiedDate",
    "scope"
})
@XmlSeeAlso({
    ReceivablesDocumentCriteria.class,
    ReceivablesInvoiceCriteria.class,
    ReceivablesDebitMemoCriteria.class,
    ReceivablesServiceRepairCriteria.class,
    ReceivablesFinanceChargeCriteria.class,
    ReceivablesWarrantyCriteria.class,
    ReceivablesCreditMemoCriteria.class,
    ReceivablesReturnCriteria.class
})
public abstract class ReceivablesDocumentCriteriaBase
    extends Criteria
{

    @XmlElement(name = "TransactionState")
    protected ListRestrictionOfNullableOfReceivablesTransactionState transactionState;
    @XmlElement(name = "DocumentId")
    protected LikeRestrictionOfString documentId;
    @XmlElement(name = "Date")
    protected BetweenRestrictionOfNullableOfDateTime date;
    @XmlElement(name = "CustomerId")
    protected LikeRestrictionOfString customerId;
    @XmlElement(name = "BatchId")
    protected LikeRestrictionOfString batchId;
    @XmlElement(name = "SalespersonId")
    protected LikeRestrictionOfString salespersonId;
    @XmlElement(name = "ModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime modifiedDate;
    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected ReceivablesDocumentScope scope;

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
     * Gets the value of the documentId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getDocumentId() {
        return documentId;
    }

    /**
     * Sets the value of the documentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setDocumentId(LikeRestrictionOfString value) {
        this.documentId = value;
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
     * Gets the value of the salespersonId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getSalespersonId() {
        return salespersonId;
    }

    /**
     * Sets the value of the salespersonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setSalespersonId(LikeRestrictionOfString value) {
        this.salespersonId = value;
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
     *     {@link ReceivablesDocumentScope }
     *     
     */
    public ReceivablesDocumentScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesDocumentScope }
     *     
     */
    public void setScope(ReceivablesDocumentScope value) {
        this.scope = value;
    }

}
