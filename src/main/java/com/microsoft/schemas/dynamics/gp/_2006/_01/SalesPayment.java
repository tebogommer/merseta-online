
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SalesPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesPayment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesPaymentKey" minOccurs="0"/&gt;
 *         &lt;element name="IsProcessElectronically" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PaymentAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BankAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BankAccountKey" minOccurs="0"/&gt;
 *         &lt;element name="PaymentCardTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentCardTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="CheckNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PaymentCardNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AuthorizationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ExpirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesPaymentType"/&gt;
 *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DeleteOnUpdate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AuditTrailCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesPayment", propOrder = {
    "key",
    "isProcessElectronically",
    "date",
    "paymentAmount",
    "bankAccountKey",
    "paymentCardTypeKey",
    "checkNumber",
    "paymentCardNumber",
    "authorizationCode",
    "expirationDate",
    "type",
    "number",
    "deleteOnUpdate",
    "auditTrailCode",
    "currencyKey"
})
public class SalesPayment
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected SalesPaymentKey key;
    @XmlElement(name = "IsProcessElectronically", required = true, type = Boolean.class, nillable = true)
    protected Boolean isProcessElectronically;
    @XmlElement(name = "Date", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "PaymentAmount")
    protected MoneyAmount paymentAmount;
    @XmlElement(name = "BankAccountKey")
    protected BankAccountKey bankAccountKey;
    @XmlElement(name = "PaymentCardTypeKey")
    protected PaymentCardTypeKey paymentCardTypeKey;
    @XmlElement(name = "CheckNumber")
    protected String checkNumber;
    @XmlElement(name = "PaymentCardNumber")
    protected String paymentCardNumber;
    @XmlElement(name = "AuthorizationCode")
    protected String authorizationCode;
    @XmlElement(name = "ExpirationDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;
    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesPaymentType type;
    @XmlElement(name = "Number")
    protected String number;
    @XmlElement(name = "DeleteOnUpdate", required = true, type = Boolean.class, nillable = true)
    protected Boolean deleteOnUpdate;
    @XmlElement(name = "AuditTrailCode")
    protected String auditTrailCode;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link SalesPaymentKey }
     *     
     */
    public SalesPaymentKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesPaymentKey }
     *     
     */
    public void setKey(SalesPaymentKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the isProcessElectronically property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsProcessElectronically() {
        return isProcessElectronically;
    }

    /**
     * Sets the value of the isProcessElectronically property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsProcessElectronically(Boolean value) {
        this.isProcessElectronically = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the paymentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the value of the paymentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPaymentAmount(MoneyAmount value) {
        this.paymentAmount = value;
    }

    /**
     * Gets the value of the bankAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link BankAccountKey }
     *     
     */
    public BankAccountKey getBankAccountKey() {
        return bankAccountKey;
    }

    /**
     * Sets the value of the bankAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAccountKey }
     *     
     */
    public void setBankAccountKey(BankAccountKey value) {
        this.bankAccountKey = value;
    }

    /**
     * Gets the value of the paymentCardTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentCardTypeKey }
     *     
     */
    public PaymentCardTypeKey getPaymentCardTypeKey() {
        return paymentCardTypeKey;
    }

    /**
     * Sets the value of the paymentCardTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentCardTypeKey }
     *     
     */
    public void setPaymentCardTypeKey(PaymentCardTypeKey value) {
        this.paymentCardTypeKey = value;
    }

    /**
     * Gets the value of the checkNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckNumber() {
        return checkNumber;
    }

    /**
     * Sets the value of the checkNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckNumber(String value) {
        this.checkNumber = value;
    }

    /**
     * Gets the value of the paymentCardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }

    /**
     * Sets the value of the paymentCardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentCardNumber(String value) {
        this.paymentCardNumber = value;
    }

    /**
     * Gets the value of the authorizationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * Sets the value of the authorizationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationCode(String value) {
        this.authorizationCode = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link SalesPaymentType }
     *     
     */
    public SalesPaymentType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesPaymentType }
     *     
     */
    public void setType(SalesPaymentType value) {
        this.type = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the deleteOnUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeleteOnUpdate() {
        return deleteOnUpdate;
    }

    /**
     * Sets the value of the deleteOnUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeleteOnUpdate(Boolean value) {
        this.deleteOnUpdate = value;
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

}
