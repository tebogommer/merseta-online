
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GLTransaction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLTransaction"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLTransactionKey" minOccurs="0"/&gt;
 *         &lt;element name="BatchKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BatchKey" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfGLTransactionLine" minOccurs="0"/&gt;
 *         &lt;element name="Reference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SourceDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SourceDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="ExchangeRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ExchangeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LedgerType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLLedgerType"/&gt;
 *         &lt;element name="AuditTrailCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Intercompany" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLIntercompany" minOccurs="0"/&gt;
 *         &lt;element name="IsVoided" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="OriginatingDocument" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLOriginatingDocument" minOccurs="0"/&gt;
 *         &lt;element name="PostedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLTransactionState"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLTransaction", propOrder = {
    "key",
    "batchKey",
    "currencyKey",
    "lines",
    "reference",
    "sourceDocumentKey",
    "exchangeRate",
    "exchangeDate",
    "ledgerType",
    "auditTrailCode",
    "intercompany",
    "isVoided",
    "modifiedBy",
    "modifiedDate",
    "originatingDocument",
    "postedBy",
    "transactionState"
})
public class GLTransaction
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected GLTransactionKey key;
    @XmlElement(name = "BatchKey")
    protected BatchKey batchKey;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "Lines")
    protected ArrayOfGLTransactionLine lines;
    @XmlElement(name = "Reference")
    protected String reference;
    @XmlElement(name = "SourceDocumentKey")
    protected SourceDocumentKey sourceDocumentKey;
    @XmlElement(name = "ExchangeRate", required = true, nillable = true)
    protected BigDecimal exchangeRate;
    @XmlElement(name = "ExchangeDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar exchangeDate;
    @XmlElement(name = "LedgerType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected GLLedgerType ledgerType;
    @XmlElement(name = "AuditTrailCode")
    protected String auditTrailCode;
    @XmlElement(name = "Intercompany")
    protected GLIntercompany intercompany;
    @XmlElement(name = "IsVoided", required = true, type = Boolean.class, nillable = true)
    protected Boolean isVoided;
    @XmlElement(name = "ModifiedBy")
    protected String modifiedBy;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "OriginatingDocument")
    protected GLOriginatingDocument originatingDocument;
    @XmlElement(name = "PostedBy")
    protected String postedBy;
    @XmlElement(name = "TransactionState", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected GLTransactionState transactionState;

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
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGLTransactionLine }
     *     
     */
    public ArrayOfGLTransactionLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGLTransactionLine }
     *     
     */
    public void setLines(ArrayOfGLTransactionLine value) {
        this.lines = value;
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
     * Gets the value of the exchangeRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Sets the value of the exchangeRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExchangeRate(BigDecimal value) {
        this.exchangeRate = value;
    }

    /**
     * Gets the value of the exchangeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExchangeDate() {
        return exchangeDate;
    }

    /**
     * Sets the value of the exchangeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExchangeDate(XMLGregorianCalendar value) {
        this.exchangeDate = value;
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

    /**
     * Gets the value of the auditTrailCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditTrailCode() {
        return auditTrailCode;
    }

    /**
     * Sets the value of the auditTrailCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditTrailCode(String value) {
        this.auditTrailCode = value;
    }

    /**
     * Gets the value of the intercompany property.
     * 
     * @return
     *     possible object is
     *     {@link GLIntercompany }
     *     
     */
    public GLIntercompany getIntercompany() {
        return intercompany;
    }

    /**
     * Sets the value of the intercompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLIntercompany }
     *     
     */
    public void setIntercompany(GLIntercompany value) {
        this.intercompany = value;
    }

    /**
     * Gets the value of the isVoided property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsVoided() {
        return isVoided;
    }

    /**
     * Sets the value of the isVoided property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsVoided(Boolean value) {
        this.isVoided = value;
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
     * Gets the value of the originatingDocument property.
     * 
     * @return
     *     possible object is
     *     {@link GLOriginatingDocument }
     *     
     */
    public GLOriginatingDocument getOriginatingDocument() {
        return originatingDocument;
    }

    /**
     * Sets the value of the originatingDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLOriginatingDocument }
     *     
     */
    public void setOriginatingDocument(GLOriginatingDocument value) {
        this.originatingDocument = value;
    }

    /**
     * Gets the value of the postedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostedBy() {
        return postedBy;
    }

    /**
     * Sets the value of the postedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostedBy(String value) {
        this.postedBy = value;
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

}
