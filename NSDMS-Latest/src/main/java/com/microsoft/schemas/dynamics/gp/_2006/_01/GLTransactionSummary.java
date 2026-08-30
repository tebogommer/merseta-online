
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GLTransactionSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLTransactionSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLTransactionKey" minOccurs="0"/&gt;
 *         &lt;element name="BatchKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BatchKey" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Reference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SourceDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SourceDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLTransactionState"/&gt;
 *         &lt;element name="LedgerType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLLedgerType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLTransactionSummary", propOrder = {
    "key",
    "batchKey",
    "currencyKey",
    "modifiedDate",
    "reference",
    "sourceDocumentKey",
    "transactionState",
    "ledgerType"
})
public class GLTransactionSummary {

    @XmlElement(name = "Key")
    protected GLTransactionKey key;
    @XmlElement(name = "BatchKey")
    protected BatchKey batchKey;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "Reference")
    protected String reference;
    @XmlElement(name = "SourceDocumentKey")
    protected SourceDocumentKey sourceDocumentKey;
    @XmlElement(name = "TransactionState", required = true)
    @XmlSchemaType(name = "string")
    protected GLTransactionState transactionState;
    @XmlElement(name = "LedgerType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected GLLedgerType ledgerType;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link GLTransactionKey }
     *     
     */
    public GLTransactionKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLTransactionKey }
     *     
     */
    public void setKey(GLTransactionKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the batchKey property.
     * 
     * @return
     *     possible object is
     *     {@link BatchKey }
     *     
     */
    public BatchKey getBatchKey() {
        return batchKey;
    }

    /**
     * Sets the value of the batchKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchKey }
     *     
     */
    public void setBatchKey(BatchKey value) {
        this.batchKey = value;
    }

    /**
     * Gets the value of the currencyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyKey }
     *     
     */
    public CurrencyKey getCurrencyKey() {
        return currencyKey;
    }

    /**
     * Sets the value of the currencyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyKey }
     *     
     */
    public void setCurrencyKey(CurrencyKey value) {
        this.currencyKey = value;
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

    /**
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * Gets the value of the sourceDocumentKey property.
     * 
     * @return
     *     possible object is
     *     {@link SourceDocumentKey }
     *     
     */
    public SourceDocumentKey getSourceDocumentKey() {
        return sourceDocumentKey;
    }

    /**
     * Sets the value of the sourceDocumentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceDocumentKey }
     *     
     */
    public void setSourceDocumentKey(SourceDocumentKey value) {
        this.sourceDocumentKey = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link GLTransactionState }
     *     
     */
    public GLTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLTransactionState }
     *     
     */
    public void setTransactionState(GLTransactionState value) {
        this.transactionState = value;
    }

    /**
     * Gets the value of the ledgerType property.
     * 
     * @return
     *     possible object is
     *     {@link GLLedgerType }
     *     
     */
    public GLLedgerType getLedgerType() {
        return ledgerType;
    }

    /**
     * Sets the value of the ledgerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLLedgerType }
     *     
     */
    public void setLedgerType(GLLedgerType value) {
        this.ledgerType = value;
    }

}
