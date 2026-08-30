
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;


/**
 * <p>Java class for DynamicsOnlineConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DynamicsOnlineConfiguration"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CustomerNumber" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="CompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CompanyKey" type="{http://schemas.microsoft.com/dynamics/2006/01}CompanyKey" minOccurs="0"/&gt;
 *         &lt;element name="OnlineCommerceAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OnlineCommerceOrganizationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ECommOrderType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="ECommInvoiceType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="IsOnlineCommerceActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsPaymentServicesActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PaymentServicesId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DynamicsOnlineCardType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentCardTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="OnlineCertificate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DynamicsOnlineConfiguration", propOrder = {
    "customerNumber",
    "companyName",
    "companyKey",
    "onlineCommerceAccountNumber",
    "onlineCommerceOrganizationID",
    "eCommOrderType",
    "eCommInvoiceType",
    "isOnlineCommerceActive",
    "isPaymentServicesActive",
    "paymentServicesId",
    "dynamicsOnlineCardType",
    "onlineCertificate"
})
public class DynamicsOnlineConfiguration
    extends BusinessObject
{

    @XmlElement(name = "CustomerNumber")
    protected CustomerKey customerNumber;
    @XmlElement(name = "CompanyName")
    protected String companyName;
    @XmlElement(name = "CompanyKey")
    protected CompanyKey companyKey;
    @XmlElement(name = "OnlineCommerceAccountNumber")
    protected String onlineCommerceAccountNumber;
    @XmlElement(name = "OnlineCommerceOrganizationID")
    protected String onlineCommerceOrganizationID;
    @XmlElement(name = "ECommOrderType")
    protected SalesDocumentKey eCommOrderType;
    @XmlElement(name = "ECommInvoiceType")
    protected SalesDocumentKey eCommInvoiceType;
    @XmlElement(name = "IsOnlineCommerceActive", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOnlineCommerceActive;
    @XmlElement(name = "IsPaymentServicesActive", required = true, type = Boolean.class, nillable = true)
    protected Boolean isPaymentServicesActive;
    @XmlElement(name = "PaymentServicesId")
    protected String paymentServicesId;
    @XmlElement(name = "DynamicsOnlineCardType")
    protected PaymentCardTypeKey dynamicsOnlineCardType;
    @XmlElement(name = "OnlineCertificate")
    protected String onlineCertificate;

    /**
     * Gets the value of the customerNumber property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getCustomerNumber() {
        return customerNumber;
    }

    /**
     * Sets the value of the customerNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setCustomerNumber(CustomerKey value) {
        this.customerNumber = value;
    }

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the companyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyKey }
     *     
     */
    public CompanyKey getCompanyKey() {
        return companyKey;
    }

    /**
     * Sets the value of the companyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyKey }
     *     
     */
    public void setCompanyKey(CompanyKey value) {
        this.companyKey = value;
    }

    /**
     * Gets the value of the onlineCommerceAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnlineCommerceAccountNumber() {
        return onlineCommerceAccountNumber;
    }

    /**
     * Sets the value of the onlineCommerceAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnlineCommerceAccountNumber(String value) {
        this.onlineCommerceAccountNumber = value;
    }

    /**
     * Gets the value of the onlineCommerceOrganizationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnlineCommerceOrganizationID() {
        return onlineCommerceOrganizationID;
    }

    /**
     * Sets the value of the onlineCommerceOrganizationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnlineCommerceOrganizationID(String value) {
        this.onlineCommerceOrganizationID = value;
    }

    /**
     * Gets the value of the eCommOrderType property.
     * 
     * @return
     *     possible object is
     *     {@link SalesDocumentKey }
     *     
     */
    public SalesDocumentKey getECommOrderType() {
        return eCommOrderType;
    }

    /**
     * Sets the value of the eCommOrderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesDocumentKey }
     *     
     */
    public void setECommOrderType(SalesDocumentKey value) {
        this.eCommOrderType = value;
    }

    /**
     * Gets the value of the eCommInvoiceType property.
     * 
     * @return
     *     possible object is
     *     {@link SalesDocumentKey }
     *     
     */
    public SalesDocumentKey getECommInvoiceType() {
        return eCommInvoiceType;
    }

    /**
     * Sets the value of the eCommInvoiceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesDocumentKey }
     *     
     */
    public void setECommInvoiceType(SalesDocumentKey value) {
        this.eCommInvoiceType = value;
    }

    /**
     * Gets the value of the isOnlineCommerceActive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOnlineCommerceActive() {
        return isOnlineCommerceActive;
    }

    /**
     * Sets the value of the isOnlineCommerceActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOnlineCommerceActive(Boolean value) {
        this.isOnlineCommerceActive = value;
    }

    /**
     * Gets the value of the isPaymentServicesActive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsPaymentServicesActive() {
        return isPaymentServicesActive;
    }

    /**
     * Sets the value of the isPaymentServicesActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPaymentServicesActive(Boolean value) {
        this.isPaymentServicesActive = value;
    }

    /**
     * Gets the value of the paymentServicesId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentServicesId() {
        return paymentServicesId;
    }

    /**
     * Sets the value of the paymentServicesId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentServicesId(String value) {
        this.paymentServicesId = value;
    }

    /**
     * Gets the value of the dynamicsOnlineCardType property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentCardTypeKey }
     *     
     */
    public PaymentCardTypeKey getDynamicsOnlineCardType() {
        return dynamicsOnlineCardType;
    }

    /**
     * Sets the value of the dynamicsOnlineCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentCardTypeKey }
     *     
     */
    public void setDynamicsOnlineCardType(PaymentCardTypeKey value) {
        this.dynamicsOnlineCardType = value;
    }

    /**
     * Gets the value of the onlineCertificate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnlineCertificate() {
        return onlineCertificate;
    }

    /**
     * Sets the value of the onlineCertificate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnlineCertificate(String value) {
        this.onlineCertificate = value;
    }

}
