
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ServiceDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CustomerPONumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EntryDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EstimatedArrivalDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="FrontOfficeIntegrationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OfficeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}OfficeKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ShipToAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceTransactionState"/&gt;
 *         &lt;element name="UserDefined01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BillTo" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceBillTo" minOccurs="0"/&gt;
 *         &lt;element name="ExchangeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ExchangeRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="RateTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RateTypeKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceDocument", propOrder = {
    "key",
    "currencyKey",
    "customerKey",
    "customerName",
    "customerPONumber",
    "entryDateTime",
    "estimatedArrivalDateTime",
    "frontOfficeIntegrationId",
    "note",
    "officeKey",
    "shipToAddressKey",
    "transactionState",
    "userDefined01",
    "userDefined02",
    "userDefined03",
    "userDefined04",
    "userDefined05",
    "billTo",
    "exchangeDate",
    "exchangeRate",
    "rateTypeKey"
})
@XmlSeeAlso({
    ReturnMaterialAuthorization.class,
    ServiceCallDocument.class
})
public abstract class ServiceDocument
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ServiceDocumentKey key;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "CustomerName")
    protected String customerName;
    @XmlElement(name = "CustomerPONumber")
    protected String customerPONumber;
    @XmlElement(name = "EntryDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar entryDateTime;
    @XmlElement(name = "EstimatedArrivalDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar estimatedArrivalDateTime;
    @XmlElement(name = "FrontOfficeIntegrationId")
    protected String frontOfficeIntegrationId;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "OfficeKey")
    protected OfficeKey officeKey;
    @XmlElement(name = "ShipToAddressKey")
    protected ShipToAddressKey shipToAddressKey;
    @XmlElement(name = "TransactionState", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ServiceTransactionState transactionState;
    @XmlElement(name = "UserDefined01")
    protected String userDefined01;
    @XmlElement(name = "UserDefined02")
    protected String userDefined02;
    @XmlElement(name = "UserDefined03")
    protected String userDefined03;
    @XmlElement(name = "UserDefined04")
    protected String userDefined04;
    @XmlElement(name = "UserDefined05")
    protected String userDefined05;
    @XmlElement(name = "BillTo")
    protected ServiceBillTo billTo;
    @XmlElement(name = "ExchangeDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar exchangeDate;
    @XmlElement(name = "ExchangeRate", required = true, nillable = true)
    protected BigDecimal exchangeRate;
    @XmlElement(name = "RateTypeKey")
    protected RateTypeKey rateTypeKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceDocumentKey }
     *     
     */
    public ServiceDocumentKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceDocumentKey }
     *     
     */
    public void setKey(ServiceDocumentKey value) {
        this.key = value;
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
     * Gets the value of the customerKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getCustomerKey() {
        return customerKey;
    }

    /**
     * Sets the value of the customerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setCustomerKey(CustomerKey value) {
        this.customerKey = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the customerPONumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerPONumber() {
        return customerPONumber;
    }

    /**
     * Sets the value of the customerPONumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerPONumber(String value) {
        this.customerPONumber = value;
    }

    /**
     * Gets the value of the entryDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEntryDateTime() {
        return entryDateTime;
    }

    /**
     * Sets the value of the entryDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEntryDateTime(XMLGregorianCalendar value) {
        this.entryDateTime = value;
    }

    /**
     * Gets the value of the estimatedArrivalDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEstimatedArrivalDateTime() {
        return estimatedArrivalDateTime;
    }

    /**
     * Sets the value of the estimatedArrivalDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEstimatedArrivalDateTime(XMLGregorianCalendar value) {
        this.estimatedArrivalDateTime = value;
    }

    /**
     * Gets the value of the frontOfficeIntegrationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrontOfficeIntegrationId() {
        return frontOfficeIntegrationId;
    }

    /**
     * Sets the value of the frontOfficeIntegrationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrontOfficeIntegrationId(String value) {
        this.frontOfficeIntegrationId = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the officeKey property.
     * 
     * @return
     *     possible object is
     *     {@link OfficeKey }
     *     
     */
    public OfficeKey getOfficeKey() {
        return officeKey;
    }

    /**
     * Sets the value of the officeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfficeKey }
     *     
     */
    public void setOfficeKey(OfficeKey value) {
        this.officeKey = value;
    }

    /**
     * Gets the value of the shipToAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link ShipToAddressKey }
     *     
     */
    public ShipToAddressKey getShipToAddressKey() {
        return shipToAddressKey;
    }

    /**
     * Sets the value of the shipToAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipToAddressKey }
     *     
     */
    public void setShipToAddressKey(ShipToAddressKey value) {
        this.shipToAddressKey = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTransactionState }
     *     
     */
    public ServiceTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTransactionState }
     *     
     */
    public void setTransactionState(ServiceTransactionState value) {
        this.transactionState = value;
    }

    /**
     * Gets the value of the userDefined01 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined01() {
        return userDefined01;
    }

    /**
     * Sets the value of the userDefined01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined01(String value) {
        this.userDefined01 = value;
    }

    /**
     * Gets the value of the userDefined02 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined02() {
        return userDefined02;
    }

    /**
     * Sets the value of the userDefined02 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined02(String value) {
        this.userDefined02 = value;
    }

    /**
     * Gets the value of the userDefined03 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined03() {
        return userDefined03;
    }

    /**
     * Sets the value of the userDefined03 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined03(String value) {
        this.userDefined03 = value;
    }

    /**
     * Gets the value of the userDefined04 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined04() {
        return userDefined04;
    }

    /**
     * Sets the value of the userDefined04 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined04(String value) {
        this.userDefined04 = value;
    }

    /**
     * Gets the value of the userDefined05 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined05() {
        return userDefined05;
    }

    /**
     * Sets the value of the userDefined05 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined05(String value) {
        this.userDefined05 = value;
    }

    /**
     * Gets the value of the billTo property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceBillTo }
     *     
     */
    public ServiceBillTo getBillTo() {
        return billTo;
    }

    /**
     * Sets the value of the billTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceBillTo }
     *     
     */
    public void setBillTo(ServiceBillTo value) {
        this.billTo = value;
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
     * Gets the value of the rateTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link RateTypeKey }
     *     
     */
    public RateTypeKey getRateTypeKey() {
        return rateTypeKey;
    }

    /**
     * Sets the value of the rateTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateTypeKey }
     *     
     */
    public void setRateTypeKey(RateTypeKey value) {
        this.rateTypeKey = value;
    }

}
