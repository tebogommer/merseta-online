
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLTransactionCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLTransactionCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CurrencyISOCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Date" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="JournalId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="BatchId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="FiscalYear" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *         &lt;element name="RecurringTransactionSequence" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *         &lt;element name="SourceDocumentId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfGLTransactionState" minOccurs="0"/&gt;
 *         &lt;element name="LedgerType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfGLLedgerType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLTransactionCriteria", propOrder = {
    "currencyISOCode",
    "date",
    "modifiedDate",
    "journalId",
    "batchId",
    "fiscalYear",
    "recurringTransactionSequence",
    "sourceDocumentId",
    "transactionState",
    "ledgerType"
})
public class GLTransactionCriteria
    extends Criteria
{

    @XmlElement(name = "CurrencyISOCode")
    protected LikeRestrictionOfString currencyISOCode;
    @XmlElement(name = "Date")
    protected BetweenRestrictionOfNullableOfDateTime date;
    @XmlElement(name = "ModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime modifiedDate;
    @XmlElement(name = "JournalId")
    protected LikeRestrictionOfString journalId;
    @XmlElement(name = "BatchId")
    protected LikeRestrictionOfString batchId;
    @XmlElement(name = "FiscalYear")
    protected BetweenRestrictionOfNullableOfInt32 fiscalYear;
    @XmlElement(name = "RecurringTransactionSequence")
    protected BetweenRestrictionOfNullableOfInt32 recurringTransactionSequence;
    @XmlElement(name = "SourceDocumentId")
    protected LikeRestrictionOfString sourceDocumentId;
    @XmlElement(name = "TransactionState")
    protected ListRestrictionOfNullableOfGLTransactionState transactionState;
    @XmlElement(name = "LedgerType")
    protected ListRestrictionOfNullableOfGLLedgerType ledgerType;

    /**
     * Gets the value of the currencyISOCode property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCurrencyISOCode() {
        return currencyISOCode;
    }

    /**
     * Sets the value of the currencyISOCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCurrencyISOCode(LikeRestrictionOfString value) {
        this.currencyISOCode = value;
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
     * Gets the value of the journalId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getJournalId() {
        return journalId;
    }

    /**
     * Sets the value of the journalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setJournalId(LikeRestrictionOfString value) {
        this.journalId = value;
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
     * Gets the value of the fiscalYear property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfInt32 }
     *     
     */
    public BetweenRestrictionOfNullableOfInt32 getFiscalYear() {
        return fiscalYear;
    }

    /**
     * Sets the value of the fiscalYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfInt32 }
     *     
     */
    public void setFiscalYear(BetweenRestrictionOfNullableOfInt32 value) {
        this.fiscalYear = value;
    }

    /**
     * Gets the value of the recurringTransactionSequence property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfInt32 }
     *     
     */
    public BetweenRestrictionOfNullableOfInt32 getRecurringTransactionSequence() {
        return recurringTransactionSequence;
    }

    /**
     * Sets the value of the recurringTransactionSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfInt32 }
     *     
     */
    public void setRecurringTransactionSequence(BetweenRestrictionOfNullableOfInt32 value) {
        this.recurringTransactionSequence = value;
    }

    /**
     * Gets the value of the sourceDocumentId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getSourceDocumentId() {
        return sourceDocumentId;
    }

    /**
     * Sets the value of the sourceDocumentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setSourceDocumentId(LikeRestrictionOfString value) {
        this.sourceDocumentId = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfGLTransactionState }
     *     
     */
    public ListRestrictionOfNullableOfGLTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfGLTransactionState }
     *     
     */
    public void setTransactionState(ListRestrictionOfNullableOfGLTransactionState value) {
        this.transactionState = value;
    }

    /**
     * Gets the value of the ledgerType property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfGLLedgerType }
     *     
     */
    public ListRestrictionOfNullableOfGLLedgerType getLedgerType() {
        return ledgerType;
    }

    /**
     * Sets the value of the ledgerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfGLLedgerType }
     *     
     */
    public void setLedgerType(ListRestrictionOfNullableOfGLLedgerType value) {
        this.ledgerType = value;
    }

}
